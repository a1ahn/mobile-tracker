package io.yena.mobiletracker.utils

import android.util.Log
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object ApiConnection {

    const val ICX_GET_LAST_BLOCK = "icx_getLastBlock"
    const val ICX_GET_BLOCK_BY_HASH = "icx_getBlockByHash"
    const val ICX_GET_BLOCK = ""

    private val baseUrl = URL("https://bicon.net.solidwallet.io/api/v3")

    fun getUrlConnection(): HttpURLConnection {
        val urlConnection = baseUrl.openConnection() as HttpURLConnection

        with(urlConnection) {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json")
            doOutput = true
            doInput = true
            connectTimeout = 10000
        }
        return urlConnection
    }

    fun getJSONObject(method: String): JSONObject {
        val jsonParam = JSONObject()
        with(jsonParam) {
            put("jsonrpc", "2.0")
            put("method", method)
            put("id", "3")
        }
        return jsonParam
    }

    fun getJSONObject(method: String, params: JSONObject): JSONObject {
        val jsonParam = JSONObject()
        with(jsonParam) {
            put("jsonrpc", "2.0")
            put("method", method)
            put("id", "3")
            put("params", params)
        }
        return jsonParam
    }


}