package io.yena.mobiletracker.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import io.yena.mobiletracker.BlockViewModel
import io.yena.mobiletracker.R
import io.yena.mobiletracker.db.Block
import io.yena.mobiletracker.ui.adapter.BlockAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BlockAdapter.BlockClickListener {

    private lateinit var viewModel: BlockViewModel

    private var blockAdapter: BlockAdapter? = null
    private var lm: LinearLayoutManager? = null
    private var toast: Toast? = null
    private lateinit var menuList: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerView()

        // scroll & load 를 위한 변수
        var lastItem: Int
        var totalItems: Int
        var startHash = ""

        main_recyclerview.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                // 저장 모드가 아닐 때 && 세로 스크롤시
                if (blockAdapter?.saveMode == false && dy > 0) {
                    totalItems = lm!!.itemCount - 1 // 마지막 position 가져오기위해 -1
                    lastItem = lm!!.findLastCompletelyVisibleItemPosition()

                    if (lastItem >= totalItems) { // 현재가 마지막-1 item이라면 더 불러오기
                        startHash = blockAdapter!!.getLastItemHash()
                        startLoadingBlocksFromApi(startHash)
                    }
                }
            }
        })

        viewModel = ViewModelProviders.of(this).get(BlockViewModel::class.java)
        viewModel.getCurrentBlocks().observe(this, Observer<List<Block>> {
            blockAdapter!!.setBlocks(it!!)
            main_loading.visibility = View.GONE

        })

        viewModel.getToastMsg().observe(this, Observer<String> { toastMsg ->
            if (!toastMsg.isNullOrEmpty()) {
                if (toast != null) toast?.cancel()
                toast = Toast.makeText(this@MainActivity, toastMsg, Toast.LENGTH_SHORT)
                toast?.show()
                viewModel.clearToastMsg()
            }
        })

        startLoadingBlocksFromApi(startHash)
    }

    private fun setRecyclerView() {
        lm = LinearLayoutManager(this)
        blockAdapter = BlockAdapter(this)
        main_recyclerview.apply {
            this.layoutManager = lm
            this.adapter = blockAdapter
            setHasFixedSize(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_save_menu, menu)
        menuList = menu!!
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_cancel -> {
                main_recyclerview.stopScroll()
                blockAdapter?.clearSelectedPosition()
                isSaveMode(false)
                runOnUiThread { blockAdapter?.notifyDataSetChanged() }
                return true
            }

            R.id.menu_save -> {
                main_recyclerview.stopScroll()
                if (blockAdapter?.saveMode == false) {
                    isSaveMode(true)
                    return true

                } else {
                    if (!blockAdapter!!.getSelectedPositions().isEmpty()) {
                        saveBlocksInPosition(blockAdapter!!.getSelectedPositions())
                    }
                    isSaveMode(false)
                    return true
                }
            }

            R.id.menu_storage -> {
                val intent = Intent(this, StorageActivity::class.java)
                startActivity(intent)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun saveBlocksInPosition(positions: List<Int>) {
        viewModel.saveBlocksInPosition(positions) {
            blockAdapter?.clearSelectedPosition()
            blockAdapter?.saveMode = false
            runOnUiThread { blockAdapter?.notifyDataSetChanged() }
        }
    }

    private fun startLoadingBlocksFromApi(startHash: String) {
        main_loading.visibility = View.VISIBLE
        viewModel.getBlocksFromApi(startHash)
    }

    private fun isSaveMode(b: Boolean) {
        val closeItem = menuList.findItem(R.id.menu_cancel)
        val storageItem = menuList.findItem(R.id.menu_storage)
        closeItem.isVisible = b
        storageItem.isVisible = !b

        if (b) {
            blockAdapter?.saveMode = true
            main_top_textview.text = "저장할 블럭을 선택해주세요."
            main_top_textview.visibility = View.VISIBLE
        } else {
            blockAdapter?.saveMode = false
            main_top_textview.text = ""
            main_top_textview.visibility = View.GONE
        }
    }

    override fun blockItemClick(block: Block) {
        val intent = Intent(this@MainActivity, TxListActivity::class.java)
        intent.putExtra(INTENT_EXTRA_BLOCK_INFO, block.parseResult().confirmed_transaction_list)
        startActivity(intent)
    }

    companion object {
        const val INTENT_EXTRA_BLOCK_INFO = "INTENT_EXTRA_BLOCK_INFO"
    }
}
