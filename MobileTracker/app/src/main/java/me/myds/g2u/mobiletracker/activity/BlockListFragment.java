package me.myds.g2u.mobiletracker.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import me.myds.g2u.mobiletracker.LoadingDialog;
import me.myds.g2u.mobiletracker.R;
import me.myds.g2u.mobiletracker.activity.BlockDetailActivity;
import me.myds.g2u.mobiletracker.adapter.MultiSelectableAdpater;
import me.myds.g2u.mobiletracker.data.Block;
import me.myds.g2u.mobiletracker.data.BlockExchanger;
import me.myds.g2u.mobiletracker.data.Transaction;
import me.myds.g2u.mobiletracker.db.BlockEntity;
import me.myds.g2u.mobiletracker.db.BlocksDB;
import me.myds.g2u.mobiletracker.exception.rpcRequestException;
import me.myds.g2u.mobiletracker.exception.rpcResponseException;
import me.myds.g2u.mobiletracker.icon_rpc.rpcConnection;
import me.myds.g2u.mobiletracker.icon_rpc.rpcRequest;
import me.myds.g2u.mobiletracker.icon_rpc.rpcResponse;
import me.myds.g2u.mobiletracker.viewholder.BlockViewHolder;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockListFragment extends Fragment {

    public View itemView;

    private TextView txtIndicate;
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mBlockListView;
    private LinearLayoutManager mLayoutMgr;
    public MultiSelectableAdpater<Block, BlockViewHolder> mAdpater;

    public BlockExchanger exchanger;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_block_list, container, false);

        txtIndicate = itemView.findViewById(R.id.txtIndicate);
        txtIndicate.setText("0 block loaded");

        mSwipe = itemView.findViewById(R.id.swipe);
        mSwipe.setOnRefreshListener(() -> {
            exchanger.loadBlock(10, mAdpater.list.get(mAdpater.list.size()-1));
        });

        mBlockListView = itemView.findViewById(R.id.block_list);
        mLayoutMgr = new LinearLayoutManager(getContext());
        mAdpater = new MultiSelectableAdpater<Block, BlockViewHolder>(
                R.layout.item_block, BlockViewHolder.class
        ) {
            @Override
            public void onCreateAfterViewHolder(BlockViewHolder holder) {
                holder.itemView.setOnClickListener(v -> {
                    Block block = holder.getItemData(mAdpater);
                    if (!mAdpater.isSelectable()) {
                        ArrayList<Transaction> transactions = block.getConfirmedTransactionList();
                        Intent intent = new Intent(getContext(), BlockDetailActivity.class);
                        intent.putParcelableArrayListExtra(BlockDetailActivity.PARAM_TRANSACTION_LIST, transactions);
                        startActivity(intent);
                    } else {
                        if (!mAdpater.isDisabled(block)) {
                            mAdpater.setSelect(holder.getLayoutPosition(), !mAdpater.isSelected(block));
                            mAdpater.notifyItemChanged(holder.getLayoutPosition());
                        }
                    }
                });

                holder.itemView.setOnLongClickListener(v -> {
                    if (!mAdpater.isSelectable()) {
                        setSelectable(true);
                        return true;
                    }
                    return false;
                });
            }

            @Override
            public void dataConvertViewHolder(BlockViewHolder holder, Block data) {
                holder.bindData(data);
                if (!mAdpater.isSelectable())
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                else {
                    if (mAdpater.isDisabled(data))
                        holder.itemView.setBackgroundColor(Color.LTGRAY);
                    else if (mAdpater.isSelected(data))
                        holder.itemView.setBackgroundColor(Color.GREEN);
                    else
                        holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        };
        mAdpater.setOnChangeSelectableListener(selectable -> {
            if (selectable) {
                exchanger.loadSavedBlock(mAdpater.list);
            } else {
                mAdpater.notifyDataSetChanged();
            }

            if (changeSelectableListener != null)
                changeSelectableListener.onChanageSelectable(selectable);
        });

        mBlockListView.setLayoutManager(mLayoutMgr);
        mBlockListView.setAdapter(mAdpater);


        LoadingDialog loadingDialog = new LoadingDialog(getContext());
        loadingDialog.show();
        exchanger = new BlockExchanger();
        exchanger.setOnLoadRemoteBlocks(blocks -> {
            mAdpater.list.addAll(blocks);
            int length = mAdpater.list.size();
            mSwipe.setRefreshing(false);
            loadingDialog.dismiss();
            mAdpater.notifyItemRangeInserted(length, blocks.size());
            txtIndicate.setText(length + " block loaded");
        });
        exchanger.setOnLoadLocalBlocks(blocks -> {
            mAdpater.setDisabled(blocks);
            mAdpater.notifyDataSetChanged();
        });
        exchanger.setOnSaveLocalBlocks(() -> {
            mAdpater.setSelectable(false);
        });
        exchanger.loadBlock(10, null);

        return itemView;
    }

    private MultiSelectableAdpater.OnChangeSelectableListener changeSelectableListener;
    public void setOnChangeSelectableListener(MultiSelectableAdpater.OnChangeSelectableListener listener) {
        this.changeSelectableListener = listener;
    }
}
