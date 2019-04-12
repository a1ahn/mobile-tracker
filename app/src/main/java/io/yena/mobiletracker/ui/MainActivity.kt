package io.yena.mobiletracker.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

        // scroll & load 를 위한 변수
        var lastItem: Int
        var totalItems: Int
        var startHash = ""

        main_recyclerview.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    totalItems = lm.itemCount - 1 // 마지막 position 가져오기위해 -1
                    lastItem = lm.findLastCompletelyVisibleItemPosition()

                    if (lastItem >= totalItems) {
                        startHash = adapter.getLastItemHash()
                        startLoading(startHash)
                    }
                }
            }
        })

        viewModel = ViewModelProviders.of(this).get(BlockViewModel::class.java)
        viewModel.getAllBlocks().observe(this, Observer<List<Block>> {
            if (it != null) {
                adapter.setBlocks(it)
                // TODO - 로딩창 해제하기
            }
        })

        startLoading(startHash)
    }

    fun startLoading(startHash: String) {
        // TODO - 로딩창 보여주기
        viewModel.loadBlocks(startHash)
    }
}
