package io.yena.mobiletracker.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import io.yena.mobiletracker.BlockViewModel
import io.yena.mobiletracker.R
import io.yena.mobiletracker.db.Block
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // TODO - Repo에서 에러 처리하고 toast 띄우기
    // TODO - item 레이아웃 꾸미기
    // TODO - 로딩창 만들고 띄우기
    // TODO - 저장할 아이템 선택 UI

    /**
     * Scene #2
    항목을 선택할 경우 해당 항목의 confirmed_transaction_list의 내용들을 상세히 보여주세요.
    confirmed_transaction_list의 from, to, txHash를 title로 화면에 출력합니다.

    Scene #3
    항목을 선택할 경우 해당 항목의 txHash로 transaction 결과를 받아오고 화면에 출력합니다.

    Scene #4
    DB에 저장된 내용을 확인합니다.
     *
     */


    private lateinit var viewModel: BlockViewModel

    private var blockAdapter: BlockAdapter? = null
    private var lm: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lm = LinearLayoutManager(this)
        blockAdapter = BlockAdapter()
        main_recyclerview.apply {
            this.layoutManager = lm
            this.adapter = blockAdapter
            setHasFixedSize(true)
        }

        // scroll & load 를 위한 변수
        var lastItem: Int
        var totalItems: Int
        var startHash = ""

        main_recyclerview.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    totalItems = lm!!.itemCount - 1 // 마지막 position 가져오기위해 -1
                    lastItem = lm!!.findLastCompletelyVisibleItemPosition()

                    if (lastItem >= totalItems) { // 현재가 마지막 item이라면 더 불러오기
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

        startLoadingBlocksFromApi(startHash)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_save -> {
                saveBlocksInPosition(blockAdapter!!.getSelectedPositions())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveBlocksInPosition(positions: List<Int>) {
        viewModel.saveBlocksInPosition(positions)
    }

    private fun startLoadingBlocksFromApi(startHash: String) {
        main_loading.visibility = View.VISIBLE
        viewModel.getBlocksFromApi(startHash)
    }
}
