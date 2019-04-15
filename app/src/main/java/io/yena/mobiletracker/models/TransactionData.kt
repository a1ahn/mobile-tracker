package io.yena.mobiletracker.models

import android.util.Log
import org.json.JSONObject

class TransactionData(
    var version: String,
    var from: String,
    var to: String,
    var value: String,
    var stepLimit: String,
    var timestamp: String,
    var nid: String,
    var nonce: String,
    var txHash: String,
    var txIndex: String,
    var blockHeight: String,
    var blockHash: String,
    var signature: String,
    var dataType: String,
    var data: String
) {
    constructor(): this("", "", "", "", "", "", "", "", "",
        "", "", "", "", "", "")

    fun parseTransaction(jsonString: String): TransactionData {
        val jsonObj = JSONObject(jsonString)
        val result = TransactionData()

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
}

