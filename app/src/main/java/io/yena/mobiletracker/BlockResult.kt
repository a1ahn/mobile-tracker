package io.yena.mobiletracker

class BlockResult(
    var version: String,
    var prev_block_hash: String,
    var merkle_tree_root_hash: String,
    var time_stamp: Long,
    var confirmed_transaction_list: String,
    var block_hash: String,
    var height: String,
    var peer_id: String,
    var signature: String
) {
    constructor(): this("", "", "", 0, "", "",
        "", "", "")
}