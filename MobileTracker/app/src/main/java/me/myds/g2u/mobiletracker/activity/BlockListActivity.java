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

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.myds.g2u.mobiletracker.adapter.MultiSelectableAdpater;
import me.myds.g2u.mobiletracker.data.Block;
import me.myds.g2u.mobiletracker.data.Transaction;
import me.myds.g2u.mobiletracker.icon_rpc.rpcConnection;
import me.myds.g2u.mobiletracker.icon_rpc.rpcRequest;
import me.myds.g2u.mobiletracker.exception.rpcRequestException;
import me.myds.g2u.mobiletracker.icon_rpc.rpcResponse;
import me.myds.g2u.mobiletracker.exception.rpcResponseException;
import me.myds.g2u.mobiletracker.R;
import me.myds.g2u.mobiletracker.db.BlockEntity;
import me.myds.g2u.mobiletracker.db.BlocksDB;
import me.myds.g2u.mobiletracker.viewholder.BlockViewHolder;

public class BlockListActivity extends AppCompatActivity {

    private BlockExchanger exchanger;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private TextView txtIndicate;
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mBlockListView;
    private LinearLayoutManager mLayoutMgr;
    private MultiSelectableAdpater<Block, BlockViewHolder> mAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_list);

        BlocksDB.init(this);
        exchanger = new BlockExchanger(mHandler);

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
            exchanger.loadBlock(10, mAdpater.list.get(mAdpater.list.size()-1));
        });

        mBlockListView = findViewById(R.id.block_list);
        mLayoutMgr = new LinearLayoutManager(this);
        mAdpater = new MultiSelectableAdpater<Block, BlockViewHolder>(
                R.layout.item_block, BlockViewHolder.class
        ) {
            @Override
            public void onCreateAfterViewHolder(BlockViewHolder holder) {
                holder.itemView.setOnClickListener(v -> {
                    Block block = holder.getItemData(mAdpater);
                    if (!mAdpater.isSelectable()) {
                        ArrayList<Transaction> transactions = block.getConfirmedTransactionList();
                        Intent intent = new Intent(BlockListActivity.this, BlockDetailActivity.class);
                        intent.putParcelableArrayListExtra(BlockDetailActivity.PARAM_TRANSACTION_LIST, transactions);
                        startActivity(intent);
                    } else {
                        if (!mAdpater.isDisabled(block)) {
                            mAdpater.setSelect(block);
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
                toolbar.getMenu().findItem(R.id.btn_save_blocks).setVisible(false);
                toolbar.getMenu().findItem(R.id.btn_save_blocks).setEnabled(false);
                mAdpater.notifyDataSetChanged();
            }
        });

        mBlockListView.setLayoutManager(mLayoutMgr);
        mBlockListView.setAdapter(mAdpater);

        exchanger.loadBlock(10, null);
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NotNull Message msg) {
            switch (msg.what) {
                case BlockExchanger.COMPLETE_LOAD_BLOCKS:
                    List<Block> list = (List<Block>) msg.obj;
                    int length = mAdpater.list.size();
                    mAdpater.list.addAll(list);
                    mSwipe.setRefreshing(false);
                    mAdpater.notifyItemRangeInserted(length, list.size());
                    txtIndicate.setText(length + " block loaded");
                    break;
                case BlockExchanger.COMPLETE_LOAD_SAVED_BLOCKS:
                    toolbar.getMenu().findItem(R.id.btn_save_blocks).setVisible(true);
                    toolbar.getMenu().findItem(R.id.btn_save_blocks).setEnabled(true);
                    mAdpater.setDisabled((List<Block>) msg.obj);
                    mAdpater.notifyDataSetChanged();
                    break;
                case BlockExchanger.COMPLETE_SAVE_BLOCKS:
                    mAdpater.setSelectable(false);
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.on_select_blocks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_save_blocks:
                exchanger.saveSelectedBlocks(mAdpater.selected);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (mAdpater.isSelectable()) {
            mAdpater.setSelectable(false);
        } else {
            super.onBackPressed();
        }
    }
}

class BlockExchanger {
    static final int COMPLETE_LOAD_BLOCKS = 22;
    static final int COMPLETE_LOAD_SAVED_BLOCKS = 33;
    static final int COMPLETE_SAVE_BLOCKS = 44;

    private Handler mHandler;

    BlockExchanger (Handler handler) {
        this.mHandler = handler;
    }

    void loadBlock(int count, Block lastBlock) {
        new Thread(()->{
            ArrayList<Block> blocks = new ArrayList<>();
            JSONObject requestParams;
            rpcRequest request;
            rpcResponse response;
            Block block;
            String prevBlockHash;
            int i = 0;

            try {
                if (lastBlock == null) {
                    request = new rpcRequest(rpcRequest.ICX_GET_LAST_BLOCK);
                    response = rpcConnection.connect(request);
                    block = new Block(((JSONObject) response.result));
                    prevBlockHash = block.getPrevBlockHash();
                    blocks.add(block);
                    i++;
                }
                else prevBlockHash = lastBlock.getPrevBlockHash();

                for (; count > i; i++) {
                    requestParams = new JSONObject();
                    requestParams.put("hash", "0x" + prevBlockHash);
                    request = new rpcRequest(rpcRequest.ICX_GET_BLOCK_BY_HASH, requestParams);
                    response = rpcConnection.connect(request);
                    block = new Block(((JSONObject) response.result));
                    prevBlockHash = block.getPrevBlockHash();
                    blocks.add(block);
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
                msg.obj = blocks;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    void loadSavedBlock (List<Block> loadedBlocks) {
        new Thread(() -> {
            ArrayList<Block> blocks = new ArrayList<>();
            HashSet<String> hashes = new HashSet<>();
            for (Block block : loadedBlocks) {
                hashes.add(block.getBlockHash());
            }
            List<BlockEntity> list = BlocksDB.run().list(hashes.toArray(new String[hashes.size()]));
            for (BlockEntity blockEntity : list) {
                blocks.add(new Block(blockEntity.body));
            }
            Message msg = new Message();
            msg.what = COMPLETE_LOAD_SAVED_BLOCKS;
            msg.obj = blocks;
            mHandler.sendMessage(msg);
        }).start();
    }

    void saveSelectedBlocks (Set<Block> selected) {
        new Thread(() -> {
            ArrayList<BlockEntity> blockEntities = new ArrayList<>();
            for (Block block : selected) {
                blockEntities.add(new BlockEntity(block));
            }
            BlocksDB.run().insert(blockEntities.toArray(new BlockEntity[blockEntities.size()]));
            mHandler.sendEmptyMessage(COMPLETE_SAVE_BLOCKS);
        }).start();
    }
}
