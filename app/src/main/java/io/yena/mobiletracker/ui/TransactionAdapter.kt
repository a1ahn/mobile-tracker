package io.yena.mobiletracker.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.yena.mobiletracker.R
import io.yena.mobiletracker.models.TransactionData

class TransactionAdapter(val listener: TransactionClickListener): RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    private var transactions: List<TransactionData> = listOf()

    interface TransactionClickListener {
        fun transactionItemClick(transaction: TransactionData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(transactions[position], listener)
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val txHashTextView = itemView.findViewById<TextView>(R.id.item_transaction_txhash)
        private val fromTextView = itemView.findViewById<TextView>(R.id.item_transaction_from_textview)
        private val toTextView = itemView.findViewById<TextView>(R.id.item_transaction_to_textview)

        fun bind(transaction: TransactionData, listener: TransactionClickListener) {
            txHashTextView.text = transaction.txHash
            fromTextView.text = transaction.from
            toTextView.text = transaction.to

            itemView.setOnClickListener {
                listener.transactionItemClick(transaction)
            }
        }
    }

    fun setTransactions(transactions: List<TransactionData>) {
        this.transactions = transactions
    }

}