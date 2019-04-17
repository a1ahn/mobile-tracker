package me.myds.g2u.mobiletracker.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import me.myds.g2u.mobiletracker.IconRPC.Block;
import me.myds.g2u.mobiletracker.IconRPC.Transaction;
import me.myds.g2u.mobiletracker.IconRPC.rpcConnection;
import me.myds.g2u.mobiletracker.IconRPC.rpcRequest;
import me.myds.g2u.mobiletracker.IconRPC.rpcRequestException;
import me.myds.g2u.mobiletracker.IconRPC.rpcResponse;
import me.myds.g2u.mobiletracker.IconRPC.rpcResponseException;
import me.myds.g2u.mobiletracker.R;
import me.myds.g2u.mobiletracker.db.BlockEntity;
import me.myds.g2u.mobiletracker.db.BlocksDB;
import me.myds.g2u.mobiletracker.utill.BaseRecyclerAdapter;
import me.myds.g2u.mobiletracker.utill.BlockViewHolder;

public class BlockListActivity extends AppCompatActivity {

    private static final String TAG = "BlockListActivity";
    private static final int COMPLETE_LOAD_BLOCKS = 22;
    private static final int COMPLETE_LOAD_SAVED_BLOCKS = 33;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private TextView txtIndicate;
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mBlockListView;
    private LinearLayoutManager mLayoutMgr;
    private BaseRecyclerAdapter<Block, BlockViewHolder> mBlockListAdpater;

    private boolean selectable = false;
    private HashSet<String> savedBlocks = new HashSet<>();
    private HashSet<String> selectedBlocks = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_list);

        BlocksDB.init(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_saved_blocks:{
                    Intent intent = new Intent(BlockListActivity.this, SavedBlockActivity.class);
                    startActivity(intent);
                }break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

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

                    if (!   selectable) {
                        ArrayList<Transaction> transactions = block.getConfirmedTransactionList();
                        Intent intent = new Intent(BlockListActivity.this, BlockDetailActivity.class);
                        intent.putParcelableArrayListExtra(BlockDetailActivity.PARAM_TRANSACTION_LIST, transactions);
                        startActivity(intent);
                    } else {
                        String block_hash = block.getBlockHash();
                        if (!savedBlocks.contains(block_hash)){
                            if (selectedBlocks.contains(block_hash)) {
                                selectedBlocks.remove(block_hash);
                            } else {
                                selectedBlocks.add(block_hash);
                            }
                            mBlockListAdpater.notifyItemChanged(itemPosition);
                        }
                    }
                });
                holder.itemView.setOnLongClickListener(v -> {
                    if (!selectable) {
                        int itemPosition = holder.getLayoutPosition();
                        Block block = mBlockListAdpater.dataList.get(itemPosition);
                        selectedBlocks.add(block.getBlockHash());
                        setSelectMode(true);
                        return true;
                    }
                    return false;
                });
            }

            @Override
            public void dataConvertViewHolder(BlockViewHolder holder, Block data) {
                holder.bindData(data);
                boolean saved = savedBlocks.contains(data.getBlockHash());
                boolean selected = selectedBlocks.contains(data.getBlockHash());
                if (!selectable) holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                else {
                    if (saved) holder.itemView.setBackgroundColor(Color.LTGRAY);
                    else if (selected) holder.itemView.setBackgroundColor(Color.GREEN);
                    else holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                }

            }
        };

        mBlockListView.setLayoutManager(mLayoutMgr);
        mBlockListView.setAdapter(mBlockListAdpater);

        loadBlock(10);
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

    void loadSavedBlock () {
        new Thread(() -> {
            HashSet<String> hashes = new HashSet<>();
            for (Block block : mBlockListAdpater.dataList) {
                hashes.add(block.getBlockHash());
            }
            List<BlockEntity> list = BlocksDB.run().list(hashes.toArray(new String[hashes.size()]));
            for (BlockEntity blockEntity : list) {
                savedBlocks.add(blockEntity.block_hash);
            }
            mHandler.sendEmptyMessage(COMPLETE_LOAD_SAVED_BLOCKS);
        }).start();
    }

    void setSelectMode (boolean on) {
        if (selectable == on) return;
        if (on) {
            selectable = true;
            loadSavedBlock();
        } else {
            selectable = true;
            savedBlocks.clear();
            selectedBlocks.clear();
            mBlockListAdpater.notifyDataSetChanged();
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NotNull Message msg) {
            switch (msg.what) {
                case COMPLETE_LOAD_BLOCKS:
                    int successLoadCount = msg.arg1;
                    int length = mBlockListAdpater.dataList.size();
                    mSwipe.setRefreshing(false);
                    txtIndicate.setText(length + " block loaded");
                    mBlockListAdpater.notifyItemRangeInserted(length, successLoadCount);
                    break;
                case COMPLETE_LOAD_SAVED_BLOCKS:
                    mBlockListAdpater.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (selectable) {
          setSelectMode(false);
        } else {
            super.onBackPressed();
        }
    }
}
