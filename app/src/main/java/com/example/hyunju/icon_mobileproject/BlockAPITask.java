package com.example.hyunju.icon_mobileproject;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by hyunju on 2019-01-23.
 */

public class BlockAPITask extends AsyncTask<Void, Void, ArrayList> {

    @Override
    public ArrayList doInBackground(Void... params) {

        JSONRPC_APIClient client = new JSONRPC_APIClient();
        // API 호출
        ArrayList<String> b = client.getBlock();
        return b;
    }


}




