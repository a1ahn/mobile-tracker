package io.yena.mobiletracker.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.yena.mobiletracker.R
import io.yena.mobiletracker.models.TransactionData
import kotlinx.android.synthetic.main.activity_txdetail.*

class TxDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_txdetail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (intent != null && intent.hasExtra(TxListActivity.INTENT_EXTRA_TX_DETAIL)) {
            val transaction = intent.getParcelableExtra<TransactionData>(TxListActivity.INTENT_EXTRA_TX_DETAIL)

            txdetail_txhash_textview.text = transaction.txHash
            txdetail_from_textview.text = transaction.from
            txdetail_to_textview.text = transaction.to
            txdetail_timestamp_textview.text = transaction.timestamp
            txdetail_steplimit_textview.text = transaction.stepLimit
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
