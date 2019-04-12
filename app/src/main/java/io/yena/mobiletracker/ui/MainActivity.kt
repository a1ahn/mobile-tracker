package io.yena.mobiletracker.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import io.yena.mobiletracker.BlockViewModel
import io.yena.mobiletracker.R
import io.yena.mobiletracker.db.Block
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BlockViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = BlockAdapter()
        val lm = LinearLayoutManager(this)
        with(main_recyclerview) {
            this.adapter = adapter
            layoutManager = lm
            setHasFixedSize(true)
        }

        viewModel = ViewModelProviders.of(this).get(BlockViewModel::class.java)
        viewModel.getAllBlocks().observe(this, Observer<List<Block>> {
            if (it != null) {
                adapter.setBlocks(it)
            }
        })

        viewModel.loadBlocks()
    }
}
