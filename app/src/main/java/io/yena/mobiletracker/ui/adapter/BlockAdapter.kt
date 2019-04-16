package io.yena.mobiletracker.ui.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.yena.mobiletracker.R
import io.yena.mobiletracker.db.Block

class BlockAdapter(private val listener: BlockClickListener): RecyclerView.Adapter<BlockAdapter.ViewHolder>() {
    private var blocks: List<Block> = listOf()
    private val checkStateArray = SparseBooleanArray()

    var saveMode = false

    interface BlockClickListener {
        fun blockItemClick(block: Block)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_block_hash, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return blocks.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(blocks[position], listener)
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val bg = itemView.findViewById<ConstraintLayout>(R.id.item_block_constraint_layout)
        private val boxTextView = itemView.findViewById<TextView>(R.id.item_block_box_textview)
        private val hashTextView = itemView.findViewById<TextView>(R.id.item_block_txhash)

        fun bind(block: Block, listener: BlockClickListener) {
            if (checkStateArray.get(adapterPosition)) {
                bg.setBackgroundResource(R.color.colorPrimaryLight)
            } else {
                bg.setBackgroundResource(android.R.color.transparent)
            }

            if (block.saved) bg.setBackgroundResource(android.R.color.darker_gray)

            val result = block.parseResult()
            boxTextView.text = result.height
            hashTextView.text = result.block_hash

            itemView.setOnClickListener {
                when (saveMode) {
                    true -> if (!block.saved) {
                        if (checkStateArray.get(adapterPosition)) {
                            checkStateArray.put(adapterPosition, false)
                        } else {
                            checkStateArray.put(adapterPosition, true)
                        }
                    }

                    false -> listener.blockItemClick(block)
                }

                notifyItemChanged(adapterPosition)
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
        return selectedPositions
    }

    fun clearSelectedPosition() {
        checkStateArray.clear()
    }

    fun setBlocks(blocks: List<Block>) {
        this.blocks = blocks
        notifyDataSetChanged()
    }

    fun getLastItemHash(): String {
        return blocks[blocks.size - 1].parseResult().block_hash
    }


}