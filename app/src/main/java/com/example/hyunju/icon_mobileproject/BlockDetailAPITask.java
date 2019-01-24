package com.example.hyunju.icon_mobileproject;

import android.os.AsyncTask;

public class BlockDetailAPITask extends AsyncTask<String, Void, BlockInfo> {

    @Override
    public BlockInfo doInBackground(String... params) {

        JSONRPC_APIClient client = new JSONRPC_APIClient();
        // API 호출
        BlockInfo blockinfo = client.getBlockDetail(params[0]);
        return blockinfo;
    }
}



