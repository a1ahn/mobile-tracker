package com.example.hyunju.icon_mobileproject;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by hyunju on 2019-01-23.
 */

public class BlockAPITask extends AsyncTask<Void, Void, ArrayList> {

    @Override
    public ArrayList doInBackground(Void... params) {

        OpenWeatherAPIClient client = new OpenWeatherAPIClient();
        // API 호출
        ArrayList<String> b = client.getBlock();
        return b;
    }
}




