package me.myds.g2u.mobiletracker.data;

import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.myds.g2u.mobiletracker.db.BlockEntity;
import me.myds.g2u.mobiletracker.db.BlocksDB;
import me.myds.g2u.mobiletracker.exception.rpcRequestException;
import me.myds.g2u.mobiletracker.exception.rpcResponseException;
import me.myds.g2u.mobiletracker.icon_rpc.rpcConnection;
import me.myds.g2u.mobiletracker.icon_rpc.rpcRequest;
import me.myds.g2u.mobiletracker.icon_rpc.rpcResponse;

public class BlockExchanger {
    public static final int COMPLETE_LOAD_BLOCKS = 22;
    public static final int COMPLETE_LOAD_SAVED_BLOCKS = 33;
    public static final int COMPLETE_SAVE_BLOCKS = 44;

    private Handler mHandler;

    public BlockExchanger(Handler handler) {
        this.mHandler = handler;
    }

    public void loadBlock(int count, Block lastBlock) {
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

    public void loadSavedBlock (List<Block> loadedBlocks) {
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

    public void saveSelectedBlocks (Set<Block> selected) {
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