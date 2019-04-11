package io.yena.mobiletracker

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.yena.mobiletracker.db.Block
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.Exception

class BlockRepo {

    private var blocks = MutableLiveData<List<Block>>()
    private val baseUrl = URL("https://bicon.net.solidwallet.io/api/v3")
    lateinit var urlConnection: HttpURLConnection

    fun getBlockHashList(): LiveData<List<Block>> {
        return blocks
    }


    fun getTenBlocks() {
        val currentList = arrayListOf<Block>()
        if (blocks.value != null) { currentList.addAll(blocks.value as ArrayList<Block>) }


        val thread = Thread(Runnable {
            try {
                val firstBlock = getLastBlock()
                currentList.add(firstBlock)
                var blockHash = firstBlock.parseResult().prev_block_hash
                Log.d("MY_TAG", "first hash = $blockHash")

                for (i in 0..8) {
                    Log.d("MY_TAG", "i = $i, blockhash = $blockHash")

                    val nextBlock = getBlockByHash(blockHash)
                    blockHash = nextBlock.parseResult().prev_block_hash
                    currentList.add(nextBlock)
                }
            } catch(fne: FileNotFoundException) {
                Log.d("MY_TAG", "file not found error - $fne")
                fne.printStackTrace()

            } catch (e: Exception) {
                Log.d("MY_TAG", "getTenBlocksError - $e")
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
                blocks.postValue(currentList.toList())
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

    private fun getLastBlock(): Block {
        urlConnection = baseUrl.openConnection() as HttpURLConnection

        with(urlConnection) {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
            doOutput = true
            doInput = true
            connect()
        }

        val jsonParam = JSONObject()
        with(jsonParam) {
            put("jsonrpc", "2.0")
            put("method", "icx_getLastBlock")
            put("id", "3")
            Log.d("MY_TAG", "json - $this")
        }

        val outputStream = DataOutputStream(urlConnection.outputStream)
        outputStream.writeBytes(jsonParam.toString())

        outputStream.flush()
        outputStream.close()

        val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
        val bff = StringBuffer()

        for (line in bufferedReader.readLines()) {
            bff.append(line)
        }

        return convertToBlock(bff.toString())
    }

    private fun getBlockByHash(hashString: String): Block {
        urlConnection = baseUrl.openConnection() as HttpURLConnection
        with(urlConnection) {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
            doOutput = true
            doInput = true
            connect()
        }

        val jsonParam = JSONObject()
        val secondParam = JSONObject().put("hash", "0x$hashString")

        with(jsonParam) {
            put("jsonrpc", "2.0")
            put("id", "3")
            put("method", "icx_getBlockByHash")
            put("params", secondParam)
            Log.d("MY_TAG", "json - $this")
        }

        val outputStream = DataOutputStream(urlConnection.outputStream)
        outputStream.writeBytes(jsonParam.toString())

        outputStream.flush()
        outputStream.close()

        val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
        val bff = StringBuffer()

        for (line in bufferedReader.readLines()) {
            bff.append(line)
        }

        return convertToBlock(bff.toString())
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


//    private fun parseResult(resultString: String): BlockResult {
//        val jsonObj = JSONObject(resultString)
//        val result = BlockResult()
//
//        try {
//            with(jsonObj) {
//                result.version = getString("version")
//                result.prev_block_hash = getString("prev_block_hash")
//                result.merkle_tree_root_hash = getString("merkel_tree_root_hash")
//                result.timestamp = getString("timestamp")
//                result.confirmed_transaction_list = getString("confirmed_transaction_list")
//                result.block_hash = getString("block_hash")
//                result.height = getString("height")
//                result.peer_id = getString("peer_id")
//                result.signature = getString("signature")
//            }
//        } catch (e: Exception) {
//            Log.d("MY_TAG", "error, parseResult - $e")
//            e.printStackTrace()
//        }
//
//        return result
//    }

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