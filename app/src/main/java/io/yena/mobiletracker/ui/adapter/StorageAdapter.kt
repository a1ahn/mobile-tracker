package io.yena.mobiletracker.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.yena.mobiletracker.R
import io.yena.mobiletracker.db.Block

class StorageAdapter: RecyclerView.Adapter<StorageAdapter.ViewHolder>() {
    private var blocks: List<Block> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_storage, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return blocks.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(blocks[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val heightTextView = itemView.findViewById<TextView>(R.id.item_storage_height_textview)
        private val hashTextView = itemView.findViewById<TextView>(R.id.item_storage_block_hash)
        private val timeTextView = itemView.findViewById<TextView>(R.id.item_storage_timestamp)
        private val prevHashTextView = itemView.findViewById<TextView>(R.id.item_storage_prev_hash)

        fun bind(block: Block) {
            val result = block.parseResult()
            heightTextView.text = result.height
            hashTextView.text = result.block_hash
            timeTextView.text = result.time_stamp
            prevHashTextView.text = result.prev_block_hash

        }
    }

    fun setBlocks(blocks: List<Block>) {
        this.blocks = blocks
        notifyDataSetChanged()
    }

}