package io.yena.mobiletracker.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import io.yena.mobiletracker.R
import io.yena.mobiletracker.models.TransactionData
import io.yena.mobiletracker.ui.adapter.TransactionAdapter
import kotlinx.android.synthetic.main.activity_txlist.*
import org.json.JSONArray

class TxListActivity : AppCompatActivity(), TransactionAdapter.TransactionClickListener {

    private var transactionAdapter: TransactionAdapter? = null
    private var lm: LinearLayoutManager? = null
    private var txList = arrayListOf<TransactionData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_txlist)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val txList = arrayListOf<TransactionData>()

        if (intent != null && intent.hasExtra(MainActivity.INTENT_EXTRA_BLOCK_INFO)) {
            try {
                val jsonArray = JSONArray(intent.getStringExtra(MainActivity.INTENT_EXTRA_BLOCK_INFO))
                for (i in 0 until jsonArray.length()) {
                    val tx = TransactionData().parseTransaction(jsonArray[i].toString())
                    txList.add(tx)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        transaction_top_textview.text = "총 ${txList.size}개의 트랜잭션이 존재합니다."

        lm = LinearLayoutManager(this)
        transactionAdapter = TransactionAdapter(this)
        transactionAdapter?.setTransactions(txList)
        transaction_recyclerview.apply {
            this.layoutManager = lm
            this.adapter = transactionAdapter
            setHasFixedSize(true)
        }
    }

    override fun transactionItemClick(transaction: TransactionData) {
        val intent = Intent(this, TxDetailActivity::class.java)
        intent.putExtra(INTENT_EXTRA_TX_DETAIL, transaction)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val INTENT_EXTRA_TX_DETAIL = "INTENT_EXTRA_TX_DETAIL"
    }
}
