package me.myds.g2u.mobiletracker;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.myds.g2u.mobiletracker.IconRPC.Block;
import me.myds.g2u.mobiletracker.IconRPC.Transaction;
import me.myds.g2u.mobiletracker.IconRPC.rpcConnection;
import me.myds.g2u.mobiletracker.IconRPC.rpcRequest;
import me.myds.g2u.mobiletracker.IconRPC.rpcRequestException;
import me.myds.g2u.mobiletracker.IconRPC.rpcResponse;
import me.myds.g2u.mobiletracker.IconRPC.rpcResponseException;
import me.myds.g2u.mobiletracker.utill.BaseRecyclerAdapter;
import me.myds.g2u.mobiletracker.utill.BlockViewHolder;

public class BlockListActivity extends AppCompatActivity {

    private static final String TAG = "BlockListActivity";
    private static final int COMPLETE_LOAD_BLOCKS = 22;

    private TextView txtIndicate;
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mBlockListView;
    private LinearLayoutManager mLayoutMgr;
    private BaseRecyclerAdapter<Block, BlockViewHolder> mBlockListAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_list);

        txtIndicate = findViewById(R.id.txtIndicate);
        txtIndicate.setText("0 block loaded");

        mSwipe = findViewById(R.id.swipe);
        mSwipe.setOnRefreshListener(() -> {
            loadBlock(10);
        });

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
                    Intent intent = new Intent(BlockListActivity.this, BlockDetail.class);
                    intent.putParcelableArrayListExtra(BlockDetail.PARAM_TRANSACTION_LIST, transactions);
                    startActivity(intent);
                });
            }

            @Override
            public void dataConvertViewHolder(BlockViewHolder holder, Block data) {
                holder.bindData(data);
            }
        };
        mBlockListView.setLayoutManager(mLayoutMgr);
        mBlockListView.setAdapter(mBlockListAdpater);
    }

    void loadBlock(int count) {
        new Thread(()->{
            JSONObject requestParams;
            rpcRequest request;
            rpcResponse response;
            Block block;
            String prevBlockHash;
            int i = 0;

            try {
                if (mBlockListAdpater.dataList.size() == 0) {
                    request = new rpcRequest(rpcRequest.ICX_GET_LAST_BLOCK);
                    response = rpcConnection.connect(request);
                    block = new Block(((JSONObject) response.result));
                    prevBlockHash = block.getPrevBlockHash();
                    mBlockListAdpater.dataList.add(block);
                    i++;
                }
                else {
                    int length = mBlockListAdpater.dataList.size();
                    block = mBlockListAdpater.dataList.get(length -1);
                    prevBlockHash = block.getPrevBlockHash();
                }

                for (; count > i; i++) {
                    requestParams = new JSONObject();
                    requestParams.put("hash", "0x" + prevBlockHash);
                    request = new rpcRequest(rpcRequest.ICX_GET_BLOCK_BY_HASH, requestParams);
                    response = rpcConnection.connect(request);
                    block = new Block(((JSONObject) response.result));
                    prevBlockHash = block.getPrevBlockHash();
                    mBlockListAdpater.dataList.add(block);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (rpcRequestException e) {
                e.printStackTrace();
            } catch (rpcResponseException e) {
                e.printStackTrace();
            } finally {
                Message msg = new Message();
                msg.what = COMPLETE_LOAD_BLOCKS;
                msg.arg1 = i;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NotNull Message msg) {
            if(msg.what == COMPLETE_LOAD_BLOCKS) {
                int successLoadCount = msg.arg1;
                int length = mBlockListAdpater.dataList.size();
                mSwipe.setRefreshing(false);
                txtIndicate.setText(length + " block loaded");
                mBlockListAdpater.notifyItemRangeInserted(length, successLoadCount);
            }
        }
    };
}
