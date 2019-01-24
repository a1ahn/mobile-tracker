package com.example.showblocks.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.showblocks.model.Result;
import com.example.showblocks.databinding.ItemRecyclerviewBinding;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = RecyclerViewAdapter.class.getName();
    private ItemClickListener mClickListener;
    private List<Result> blockList;

    public RecyclerViewAdapter(ItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    void setItem(List<Result> blockList) {
        if (blockList == null) return;

        this.blockList = blockList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecyclerviewBinding binding = ItemRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result block = blockList.get(position);
        holder.bind(block, mClickListener, position);
    }

    @Override
    public int getItemCount() {
        return blockList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRecyclerviewBinding binding;

        public ViewHolder(ItemRecyclerviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Result result, final ItemClickListener listener, final int index) {
            binding.setBlock(result);
            binding.executePendingBindings();
            binding.blockHash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(index);
                }
            });
        }
    }

    public interface ItemClickListener {
        void onItemClick(int index);
    }
}
