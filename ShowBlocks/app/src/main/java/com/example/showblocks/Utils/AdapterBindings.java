package com.example.showblocks.Utils;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;

import com.example.showblocks.model.Result;

public class AdapterBindings {
    @BindingAdapter({"item"})
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<Result> blockList) {
        RecyclerViewAdapter adapter = (RecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(blockList);
        }
    }
}
