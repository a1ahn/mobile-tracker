package me.myds.g2u.mobiletracker.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.myds.g2u.mobiletracker.IconRPC.Block;
import me.myds.g2u.mobiletracker.IconRPC.Transaction;
import me.myds.g2u.mobiletracker.R;
import me.myds.g2u.mobiletracker.block_db.BlockEntity;
import me.myds.g2u.mobiletracker.block_db.LocalBlocks;
import me.myds.g2u.mobiletracker.utill.BaseRecyclerAdapter;
import me.myds.g2u.mobiletracker.utill.BlockViewHolder;

public class SavedBlockActivity extends AppCompatActivity {

    private TextView txtIndicate;
    private RecyclerView mBlockListView;
    private LinearLayoutManager mLayoutMgr;
    private BaseRecyclerAdapter<Block, BlockViewHolder> mBlockListAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_block);

        mBlockListView = findViewById(R.id.block_list);
        mLayoutMgr = new LinearLayoutManager(this);
        mBlockListAdpater = new BaseRecyclerAdapter<Block, BlockViewHolder>(
                R.layout.item_block, BlockViewHolder.class) {
            @Override
            public void onCreateAfterViewHolder(BlockViewHolder holder) {
                holder.itemView.setOnClickListener((v)->{
                    int itemPosition = holder.getLayoutPosition();
                    Block block = mBlockListAdpater.dataList.get(itemPosition);
                    ArrayList<Transaction> transactions = block.getConfirmedTransactionList();
                    Intent intent = new Intent(SavedBlockActivity.this, BlockDetailActivity.class);
                    intent.putParcelableArrayListExtra(BlockDetailActivity.PARAM_TRANSACTION_LIST, transactions);
                    startActivity(intent);
                });
            }

            @Override
            public void dataConvertViewHolder(BlockViewHolder holder, Block data) {
                holder.bindData(data);
            }
        };
        List<BlockEntity> blockEntities = LocalBlocks.run().getAll();
        mBlockListAdpater.dataList.addAll(new ArrayList<Block>(){{
            for (BlockEntity entity : blockEntities) {
                add(new Block(entity.getBody()));
            }
        }});
        mBlockListView.setLayoutManager(mLayoutMgr);
        mBlockListView.setAdapter(mBlockListAdpater);
    }
}
