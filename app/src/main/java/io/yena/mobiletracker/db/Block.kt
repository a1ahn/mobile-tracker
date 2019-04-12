package io.yena.mobiletracker.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.util.Log
import io.yena.mobiletracker.models.BlockResult
import org.json.JSONObject

@Entity
data class Block(

    var jsonrpc: String,
    @PrimaryKey var id: Int,
    var result: String
) {
    constructor(): this("", -1, "")

    fun parseResult(): BlockResult {
        val jsonObj = JSONObject(result)
        val blockResult = BlockResult()

        try {
            with(jsonObj) {
                blockResult.version = getString("version")
                blockResult.prev_block_hash = getString("prev_block_hash")
                blockResult.merkle_tree_root_hash = getString("merkle_tree_root_hash")
                blockResult.time_stamp = getLong("time_stamp")
                blockResult.confirmed_transaction_list = getString("confirmed_transaction_list")
                blockResult.block_hash = getString("block_hash")
                blockResult.height = getString("height")
                blockResult.peer_id = getString("peer_id")
                blockResult.signature = getString("signature")
            }
        } catch (e: Exception) {
            Log.d("MY_TAG", "error, parseResult - $e")
            e.printStackTrace()
        }

        return blockResult
    }


}
