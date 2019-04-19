package com.example.mobile_tracker

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class Request(preBlocks: MutableList<String>): AsyncTask<Unit, Unit, MutableList<String>>() {

    var blockHash = mutableListOf<String>()

    override fun doInBackground(vararg params: Unit?): MutableList<String> {
        val requestAddress = "https://wallet.icon.foundation/api/v3"
        val url = URL(requestAddress)

        var first = true
        var preBlockHash = ""
        repeat((0..9).count()) {
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.connectTimeout = 8000
            conn.readTimeout = 8000
            conn.doOutput = true
            conn.doInput = true

            var postData: String
            if (first) {
                postData = JSONObject().apply {
                    accumulate("jsonrpc", "2.0")
                    accumulate("method", "icx_getLastBlock")
                    accumulate("id", 1234)
                }.toString()

                first = false
            } else {

                val hash = JSONObject().apply {
                    accumulate("hash", "0x$preBlockHash")
                }

                postData = JSONObject().apply {
                    accumulate("jsonrpc", "2.0")
                    accumulate("method", "icx_getBlockByHash")
                    accumulate("id", 1234)
                    accumulate("params", hash)
                }.toString()
            }

            conn.setRequestProperty("charset", "UTF-8")
            conn.setRequestProperty("Content-length", postData.length.toString())
            conn.setRequestProperty("Content-Type", "application/json")

            val outputStream = DataOutputStream(conn.outputStream)
            outputStream.writeBytes(postData)
            outputStream.flush()

            BufferedReader(InputStreamReader(conn.inputStream, Charset.forName("UTF-8"))).use { reader ->

                val response = reader.readLine()
                val json = JSONObject(response)
                blockHash.add("0x"+json.getJSONObject("result").getString("block_hash").toString())
                preBlockHash = json.getJSONObject("result").getString("prev_block_hash")
                Log.d("123", "$blockHash")

            }

            conn.disconnect()
        }

        return blockHash
    }
}