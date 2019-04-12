package io.yena.mobiletracker.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.yena.mobiletracker.R
import io.yena.mobiletracker.db.Block

class BlockAdapter: RecyclerView.Adapter<BlockAdapter.ViewHolder>() {
    private var blocks: List<Block> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_block_hash, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return blocks.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(blocks[position])
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val hashTextView = itemView.findViewById<TextView>(R.id.item_block_hash_textview)

        fun bind(block: Block) {
            hashTextView.text = block.parseResult().block_hash
        }
    }

    fun setBlocks(blocks: List<Block>) {
        this.blocks = blocks
        notifyDataSetChanged()
    }

    fun getLastItemHash(): String {
        return blocks.last().parseResult().block_hash
    }
}