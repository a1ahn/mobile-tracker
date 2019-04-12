package io.yena.mobiletracker.models

class TransactionByHashResult(
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
}

