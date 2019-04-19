package com.example.mobile_tracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val blocks = Request(ArrayList()).execute()

        val listAdapter = ListAdapter(this, ArrayList(blocks.get()))
        blockListView.adapter = listAdapter

        my_swipeRefresh_Layout.setOnRefreshListener {
            Request(blocks.get()).execute()
        }
    }
}
