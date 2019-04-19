package com.example.mobile_tracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdapter(val context: Context, val blockList: ArrayList<String>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = LayoutInflater.from(context).inflate(R.layout.list_item, null)

        val title = view.findViewById<TextView>(R.id.item_title)

        val block = blockList[position]
        title.text = block

        return view
    }

    override fun getItem(position: Int): Any {
        return blockList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return blockList.size
    }
}