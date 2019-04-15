package io.yena.mobiletracker.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.yena.mobiletracker.R
import io.yena.mobiletracker.models.TransactionData
import kotlinx.android.synthetic.main.activity_transaction.*
import org.json.JSONArray

class TransactionActivity : AppCompatActivity(), TransactionAdapter.TransactionClickListener {

    private var transactionAdapter: TransactionAdapter? = null
    private var lm: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
        // TODO - TxHash 로 트랜잭션값 받아와서 보여주기
        Log.d("MY_TAG", "// TODO - TxHash 로 트랜잭션값 받아와서 보여주기")
    }
}
