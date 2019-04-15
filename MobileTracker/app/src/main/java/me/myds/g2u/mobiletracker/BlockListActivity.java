package me.myds.g2u.mobiletracker;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.myds.g2u.mobiletracker.IconRPC.Block;
import me.myds.g2u.mobiletracker.IconRPC.rpcConnection;
import me.myds.g2u.mobiletracker.IconRPC.rpcRequest;
import me.myds.g2u.mobiletracker.IconRPC.rpcResponse;

public class BlockListActivity extends AppCompatActivity {
    private static final String TAG = "BlockListActivity";
    private static final int COMPLETE_LOAD_BLOCKS = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastBlock(10);
            }
        });
    }

    ArrayList<Block> blocks = new ArrayList<>();
    void getLastBlock(final int count) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                rpcResponse response = rpcConnection.connect(new rpcRequest(rpcRequest.ICX_GET_LAST_BLOCK));
                Block block = new Block((JSONObject) response.result);
                String prevBlockHash = "0x" + block.getPrevBlockHash();
                blocks.add(block);
                for (int i = 1; count > i; i++){
                    JSONObject requestParams = new JSONObject();
                    try {
                        requestParams.put("hash", prevBlockHash);
                        rpcRequest request = new rpcRequest(rpcRequest.ICX_GET_BLOCK_BY_HASH, requestParams);
                        response = rpcConnection.connect(request);
                        if (response == null) break;
                        block = new Block((JSONObject) response.result);
                        prevBlockHash = "0x" + block.getPrevBlockHash();
                        blocks.add(block);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(COMPLETE_LOAD_BLOCKS);
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "block count: " + blocks.size());
            if(msg.what == COMPLETE_LOAD_BLOCKS) {
                for (Block b : blocks) {
                    Log.d(TAG, "block hash: " + b.getPrevBlockHash());
                }
            }
        }
    };
}
