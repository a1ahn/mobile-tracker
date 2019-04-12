package io.yena.mobiletracker.ui

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.yena.mobiletracker.R
import io.yena.mobiletracker.db.Block

class BlockAdapter: RecyclerView.Adapter<BlockAdapter.ViewHolder>() {
    private var blocks: List<Block> = listOf()
    private val checkStateArray = SparseBooleanArray()

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
            itemView.setOnClickListener {
                if (!checkStateArray.get(adapterPosition, false)) {
                    // view 색 바꾸기 (선택됨)
                    checkStateArray.put(adapterPosition, true)
                } else {
                    // view 색 바꾸기 (선택 해제됨)
                    checkStateArray.put(adapterPosition, false)
                }
            }
        }
    }


    fun getSelectedPositions(): List<Int> {
        val selectedPositions = arrayListOf<Int>()
        for (i in 0 until checkStateArray.size()) {
            if (checkStateArray.valueAt(i)) {
                selectedPositions.add(checkStateArray.keyAt(i))
            }
        }
        Log.d("MY_TAG", "positions = $selectedPositions")
        return selectedPositions
    }

    fun setBlocks(blocks: List<Block>) {
        this.blocks = blocks
        notifyDataSetChanged()
    }

    fun getLastItemHash(): String {
        return blocks.last().parseResult().block_hash
    }
}