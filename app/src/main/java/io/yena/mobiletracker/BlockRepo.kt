package io.yena.mobiletracker

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import android.util.Log
import io.yena.mobiletracker.db.Block
import io.yena.mobiletracker.db.BlockDatabase
import io.yena.mobiletracker.utils.ApiConnection
import io.yena.mobiletracker.utils.ApiConnection.ICX_GET_BLOCK_BY_HASH
import io.yena.mobiletracker.utils.ApiConnection.ICX_GET_LAST_BLOCK
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import kotlin.Exception

class BlockRepo(application: Application) {

    private val blockDb = BlockDatabase.getInstance(application)!!
    private val blockDao = blockDb.blockDao()
    private var currentBlocks = MutableLiveData<List<Block>>()
    private var savedBlocks: LiveData<List<Block>> = blockDao.getAll()
    private var mutableToastMessage = MutableLiveData<String>()

    private lateinit var urlConnection: HttpURLConnection

    fun getToastMsg(): LiveData<String> {
        return mutableToastMessage
    }

    fun clearToastMsg() {
        mutableToastMessage.value = ""
    }

    fun getCurrentBlocks(): LiveData<List<Block>> {
        return currentBlocks
    }

    fun getSavedBlocks(): LiveData<List<Block>> {

        return savedBlocks
    }

    fun getBlocksFromApi(startHash: String) {
        getTenBlocks(startHash)
    }

    fun saveBlocksInPosition(positions: List<Int>, saveComplete: () -> Unit) {
        class MyAsync:AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void?): String {
                try {
                    // DB와 비교하기 위해 저장된 블럭들의 hash리스트와 비교
                    val savedHashes = blockDao.getAllBlockHashes()

                    for (i in 0 until positions.size) {
                        with(currentBlocks.value?.get(positions[i])) {
                            if (!savedHashes.contains(this?.parseResult()?.block_hash)) {
                                this?.saved = true
                                this?.let { blockDao.insert(it) }
                            }
                        }
                    }

                    saveComplete()
                    return "저장되었습니다."

                } catch (e: Exception) {
                    e.printStackTrace()
                    saveComplete()
                    return "저장에 실패했습니다."
                }
            }

            override fun onPostExecute(result: String?) {
                mutableToastMessage.value = result
            }
        }

        val async = MyAsync()
        async.execute()
    }

    private fun getTenBlocks(startHash: String) {
        val holdingList = arrayListOf<Block>() // 더 로드하기 전에, 이전에 가지고 있던 리스트 임시 저장
        if (currentBlocks.value != null) { holdingList.addAll(currentBlocks.value as ArrayList<Block>) }

        var firstBlock: Block?
        val thread = Thread(Runnable {
            try {

                // 파라미터 정보가 없으면 첫블록, 있으면 그 hash값의 블록부터 다운로드
                firstBlock = if (startHash.isEmpty()) {
                    getLastBlock()
                } else {
                    getBlockByHash(startHash)
                }

                // 블럭 정보를 가져오지 못하면 return, 가져왔으면 리스트에 추가
                if (firstBlock == null) {
                    mutableToastMessage.value = "데이터 가져오기에 실패했습니다."
                    return@Runnable
                } else {
                    if (!holdingList.contains(firstBlock!!)) holdingList.add(firstBlock!!)
                }

                var blockHash = firstBlock!!.parseResult().prev_block_hash

                // 나머지 9개 블럭은 getBlockByHash 로드
                for (i in 0..8) {
                    val nextBlock = getBlockByHash(blockHash)

                    if (nextBlock == null) {
                        mutableToastMessage.value = "데이터 가져오기에 실패했습니다."
                        return@Runnable

                    } else {
                        blockHash = nextBlock.parseResult().prev_block_hash
                        if (!holdingList.contains(nextBlock)) holdingList.add(nextBlock)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                currentBlocks.postValue(holdingList.toList())
            }
        })
        thread.start()
    }

    private fun getLastBlock(): Block? {
        urlConnection = ApiConnection.getUrlConnection()
        urlConnection.connect()

        val jsonParam = ApiConnection.getJSONObject(ICX_GET_LAST_BLOCK)

        val outputStream = DataOutputStream(urlConnection.outputStream)
        outputStream.writeBytes(jsonParam.toString())

        outputStream.flush()
        outputStream.close()

        if (urlConnection.responseCode == 200) {
            val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val bff = StringBuffer()

            for (line in bufferedReader.readLines()) { bff.append(line) }

            urlConnection.disconnect()
            return convertToBlock(bff.toString())

        } else {
            urlConnection.disconnect()
            return null
        }

    }

    private fun getBlockByHash(hashString: String): Block? {
        urlConnection = ApiConnection.getUrlConnection()
        urlConnection.connect()

        val secondParam = JSONObject().put("hash", "0x$hashString")
        val jsonParam = ApiConnection.getJSONObject(ICX_GET_BLOCK_BY_HASH, secondParam)

        val outputStream = DataOutputStream(urlConnection.outputStream)
        outputStream.writeBytes(jsonParam.toString())

        outputStream.flush()
        outputStream.close()

        if (urlConnection.responseCode == 200) {
            val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val bff = StringBuffer()

            for (line in bufferedReader.readLines()) { bff.append(line) }

            urlConnection.disconnect()
            return convertToBlock(bff.toString())

        } else {
            urlConnection.disconnect()
            return null
        }
    }


    private fun convertToBlock(string: String): Block {
        val jsonObj = JSONObject(string)
        val block = Block()

        try {
            with(jsonObj) {
                block.jsonrpc = getString("jsonrpc")
                block.id = getInt("id")
                block.result = getString("result")
            }

            block.block_hash = block.parseResult().block_hash

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return block
    }
}