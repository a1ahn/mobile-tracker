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

/**
 * Created by hyunju on 2019-01-23.
 */

public class BlockAPITask extends AsyncTask<Void, Void, BlockInfo> {
    BlockInfo blockInfo = new BlockInfo();
    @Override
    public BlockInfo doInBackground(Void... params) {

        try {
            HttpURLConnection conn;
            final String openWeatherURL = "https://bicon.net.solidwallet.io/api/v3";
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            URL url = null;


            url = new URL(openWeatherURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject json = new JSONObject();


            json.put("jsonrpc", "2.0");
            json.put("method", "icx_getLastBlock");
            json.put("id", "1234");
            Log.i("json", json.toString());

            //String input = json.toString();
            OutputStream os = conn.getOutputStream();
            os.write(json.toString().getBytes());
            os.flush();

            String response = "";

            int responseCode = 0;


            responseCode = conn.getResponseCode();


            if (responseCode == HttpURLConnection.HTTP_OK) {

                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                response = new String(byteData);
            }
            JSONObject responseJSON = new JSONObject(response);
            Log.i("DATA response = ", response);

            JSONObject resultArray = responseJSON.getJSONObject("result");

            String prev_block_hash = resultArray.getString("prev_block_hash");
            blockInfo.setPrevBlockHash(prev_block_hash);
            Log.i("PREV DATA response = ", prev_block_hash);
            String block_hash = resultArray.getString("block_hash");
            blockInfo.setCurBlockHash(block_hash);
            Log.i("BLOCK  DATA response = ", block_hash);


        } catch (JSONException e) {
            Log.i("json오류", "JSON오류");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("IOEXCEPTION", "JSON오류");
            e.printStackTrace();
        }
        Log.i("blockInfo###",blockInfo.getCurBlockHash());
        return blockInfo;
    }
}


//        OpenWeatherAPIClient client = new OpenWeatherAPIClient();
//        int lat = params[0];
//        int lon = params[1];
//        // API 호출
//        Weather w = client.getWeather(lat, lon);
//        return w;





