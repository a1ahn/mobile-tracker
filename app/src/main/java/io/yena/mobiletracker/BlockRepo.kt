package io.yena.mobiletracker

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.yena.mobiletracker.db.Block
import io.yena.mobiletracker.db.BlockDatabase
import io.yena.mobiletracker.models.TransactionByHashResult
import io.yena.mobiletracker.utils.ApiConnection
import io.yena.mobiletracker.utils.ApiConnection.ICX_GET_BLOCK_BY_HASH
import io.yena.mobiletracker.utils.ApiConnection.ICX_GET_LAST_BLOCK
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.Exception

class BlockRepo(application: Application) {

    private val blockDb = BlockDatabase.getInstance(application)!!
    private val blockDao = blockDb.blockDao()
    private var currentBlocks = MutableLiveData<List<Block>>()
    private var savedBlocks = MutableLiveData<List<Block>>()

//    private val baseUrl = URL("https://bicon.net.solidwallet.io/api/v3")
    private lateinit var urlConnection: HttpURLConnection

    fun getCurrentBlocks(): LiveData<List<Block>> {
        return currentBlocks
    }

    fun getSavedBlocks(): LiveData<List<Block>> {
        return savedBlocks
    }

    fun getBlocksFromApi(startHash: String) {
        getTenBlocks(startHash)
    }

    fun saveBlocksInPosition(positions: List<Int>) {
        val thread = Thread(Runnable {
            try {
                for (i in 0 until positions.size) {
                    currentBlocks.value?.get(positions[i])?.let { block ->
                        blockDao.insert(block)
                        Log.d("MY_TAG", "block ${positions[i]}, ${block.parseResult().block_hash}")
                    }
                }
            } catch (e: Exception) {
                Log.d("MY_TAG", "save error - $e")
                e.printStackTrace()
            }
        })
        thread.start()
    }

    private fun getTenBlocks(startHash: String) {
        val holdingList = arrayListOf<Block>() // 더 로드하기 전에, 이전에 가지고 있던 리스트 임시 저장
        if (currentBlocks.value != null) { holdingList.addAll(currentBlocks.value as ArrayList<Block>) }

        var firstBlock: Block
        val thread = Thread(Runnable {
            try {
                // 파라미터 startHash가 ""면 첫번째는 getLastBlock, startHash가 있으면 getBlockByHash
                val result: String

                if (startHash.isEmpty()) {
                    result = getLastBlockResult()
                    Log.d("MY_TAG", "result = $result")
                    if (result != "OK") {
                        return@Runnable
                    }

                } else {
                    result = getBlockByHashResult(startHash)
                }

                firstBlock = convertToBlock(result)

                holdingList.add(firstBlock)
                var blockHash = firstBlock.parseResult().prev_block_hash

                // 나머지 9개 Block은 getBlockByHash로 로드
                for (i in 0..8) {
                    Log.d("MY_TAG", "i = $i, prev hash = $blockHash")
                    val nextBlock = convertToBlock(getBlockByHashResult(blockHash))
                    blockHash = nextBlock.parseResult().prev_block_hash
                    holdingList.add(nextBlock)
                }
            } catch (e: Exception) {
                Log.d("MY_TAG", "getTenBlocksError - $e")
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
                currentBlocks.postValue(holdingList.toList())
            }
        })
        thread.start()
    }


//    class DownloadAsyncTask : AsyncTask<String, Unit, String>() {
//
//
//        override fun doInBackground(vararg params: String?): String {
//            val hashString = params[0]
//            Log.d("MY_TAG", "hashString - $hashString")
//            try {
//                urlConnection = baseUrl.openConnection() as HttpURLConnection
//                with(urlConnection) {
//                    requestMethod = "POST"
//                    setRequestProperty("Content-Type", "application/json")
//                    setRequestProperty("Accept", "application/json")
//                    doOutput = true
//                    doInput = true
//                    connect()
//                }
//
//                val jsonParam = JSONObject()
//                with(jsonParam) {
//                    put("jsonrpc", "2.0")
//                    put("method", "icx_getLastBlock")
//                    put("id", "3")
//                    Log.d("MY_TAG", "json - $this")
//                }
//
//                val outputStream = DataOutputStream(urlConnection.outputStream)
//                outputStream.writeBytes(jsonParam.toString())
//
//                outputStream.flush()
//                outputStream.close()
//
//                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
//
//                for (line in bufferedReader.readLines()) {
//                    BlockRepo.currentBlockStringList.add(line)
//                    Log.d("MY_TAG", "response - $line")
//                }
//
//                return "RESULT_SUCCESS"
//            } catch (e: Exception) {
//                Log.d("MY_TAG", "error - $e")
//                e.printStackTrace()
//                return "RESULT_FAIL"
//            }
//
//        }
//
//        override fun onPostExecute(result: String?) {
//            Log.d("MY_TAG", "onPost, ${currentBlockStringList[0]}")
//            if (!result.isNullOrEmpty()) resultValue = result
//        }
//    }

//    companion object {
//        var currentBlockStringList = arrayListOf<String>()
//    }

    private fun getLastBlockResult(): String {
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

            return bff.toString()

        } else {
            urlConnection.disconnect()
            return urlConnection.responseMessage
        }

    }

    private fun getBlockByHashResult(hashString: String): String {
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

            for (line in bufferedReader.readLines()) {
                bff.append(line)
            }

            return bff.toString()

        } else {
            urlConnection.disconnect()
            return urlConnection.responseMessage
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
        } catch (e: Exception) {
            Log.d("MY_TAG", "error, convertToBlock - $e")
            e.printStackTrace()
        }

        return block
    }

    // transaction 데이터 받아올 때 사용/수정
    private fun parseTransactionByHashResult(resultString: String): TransactionByHashResult {
        val jsonObj = JSONObject(resultString)
        val result = TransactionByHashResult()

        try {
            with(jsonObj) {
                result.version = getString("version")
                result.from = getString("from")
                result.to = getString("to")
                result.value = getString("value")
                result.stepLimit = getString("stepLimit")
                result.timestamp = getString("timestamp")
                result.nid = getString("nid")
                result.nonce = getString("nonce")
                result.txHash = getString("txHash")
                result.txIndex = getString("txIndex")
                result.blockHeight = getString("blockHeight")
                result.blockHash = getString("blockHash")
                result.signature = getString("signature")
                result.dataType = getString("dataType")
                result.data = getString("data")
            }
        } catch (e: Exception) {
            Log.d("MY_TAG", "error, parseTransactionByHashResult - $e")
            e.printStackTrace()
        }

        return result
    }

    private fun blockToString(block: Block): String {
        return ""
    }
}