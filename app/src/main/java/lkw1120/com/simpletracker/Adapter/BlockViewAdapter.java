package lkw1120.com.simpletracker.Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import lkw1120.com.simpletracker.Database.Block;
import lkw1120.com.simpletracker.R;
import lkw1120.com.simpletracker.Task.GetLastBlockTask;
import lkw1120.com.simpletracker.Task.GetPrevBlockTask;
import lkw1120.com.simpletracker.Task.InsertBlockTask;
import lkw1120.com.simpletracker.Task.ReadBlockAllTask;
import lkw1120.com.simpletracker.TransactionActivity;

public class BlockViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final static int NORMAL = 0;
    private final static int DATABASE = 1;

    private ArrayList<Block> blockList = new ArrayList<>();

    private Context context;
    private int VIEW_MODE;
    private boolean checkable = false;

    public BlockViewAdapter(Context context, int mode) {
        this.context = context;
        VIEW_MODE = mode;
    }

    public boolean isSaveMode() {
        return checkable;
    }

    public void setSaveMode(boolean checked) {
        checkable = checked;
    }

    public void toggleSaveMode() {
        checkable = !checkable;
    }

    public void saveBlockList() {
        for(Block block:blockList) {
            if(block.isSelected()) {
                InsertBlockTask insertBlockTask = new InsertBlockTask(context);
                insertBlockTask.execute(block);
                block.setSelected(false);
            }
        }
    }

    public void setBlockList() {
        try {
            if(VIEW_MODE == NORMAL) {
                for (int i = 0; i < 10; i++) {
                    Block block;
                    if (blockList.size() == 0) {
                        GetLastBlockTask getLastBlockTask = new GetLastBlockTask();
                        block = getLastBlockTask.execute().get();
                    } else {
                        JSONObject jsonObject = new JSONObject(blockList.get(blockList.size() - 1).getResult());
                        String prevHash = jsonObject.getString("prev_block_hash");
                        GetPrevBlockTask getPrevBlockTask = new GetPrevBlockTask();
                        block = getPrevBlockTask.execute(prevHash).get();
                    }
                    blockList.add(block);
                }
            }
            if(VIEW_MODE == DATABASE){
                ReadBlockAllTask readBlockAllTask = new ReadBlockAllTask(context);
                blockList.addAll(readBlockAllTask.execute().get());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_block, parent, false);
        return new BlockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Log.d("Position", ""+ position);
        final Block block = blockList.get(position);
        final BlockViewHolder blockViewHolder = (BlockViewHolder) holder;
        blockViewHolder.blockHash.setText(block.getBlockHash());
        if(!isSaveMode()) {
            blockViewHolder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            if(block.isSelected()) {
                blockViewHolder.setChecked(true);
            }
            else {
                blockViewHolder.setChecked(false);
            }
        }
        blockViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                toggleSaveMode();
                notifyDataSetChanged();
                return true;
            }
        });
        blockViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSaveMode()) {
                    if(!block.isSelected()) {
                        blockViewHolder.setChecked(true);
                        block.setSelected(true);
                    }
                    else {
                        blockViewHolder.setChecked(false);
                        block.setSelected(false);
                    }
                }
                else {
                    try {
                        Intent intent = new Intent(view.getContext(), TransactionActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("result", block.getResult());
                        intent.putExtra("bundle", bundle);
                        ContextCompat.startActivity(view.getContext(),intent,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return blockList.size();
    }
}