package io.yena.mobiletracker.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.yena.mobiletracker.BlockViewModel
import io.yena.mobiletracker.R
import io.yena.mobiletracker.db.Block
import io.yena.mobiletracker.ui.adapter.StorageAdapter
import kotlinx.android.synthetic.main.activity_saved.*

class StorageActivity : AppCompatActivity() {

    private lateinit var viewModel: BlockViewModel
    private var lm: LinearLayoutManager? = null
    private var storageAdapter: StorageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setRecyclerView()

        viewModel = ViewModelProviders.of(this).get(BlockViewModel::class.java)
        viewModel.getSavedBlocks().observe(this, Observer<List<Block>> {
            storageAdapter!!.setBlocks(it!!)
        })
    }

    private fun setRecyclerView() {
        lm = LinearLayoutManager(this)
        storageAdapter = StorageAdapter()
        storage_recyclerview.apply {
            this.layoutManager = lm
            this.adapter = storageAdapter
            setHasFixedSize(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
