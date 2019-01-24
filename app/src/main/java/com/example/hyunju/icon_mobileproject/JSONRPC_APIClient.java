package com.example.hyunju.icon_mobileproject;

/**
 * Created by hyunju on 2019-01-23.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class JSONRPC_APIClient {
    BlockInfo blockInfo = new BlockInfo();
    ArrayList<String> arrayList = new ArrayList<String>();
    HttpURLConnection conn;
    final String openWeatherURL = "https://bicon.net.solidwallet.io/api/v3";
    InputStream is = null;
    ByteArrayOutputStream baos = null;
    URL url = null;


    public ArrayList<String> getBlock() {
        try {
            for (int i = 0; i < 10; i++) {
                url = new URL(openWeatherURL);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject json = new JSONObject();
                if (i == 0) {

                    json.put("jsonrpc", "2.0");
                    json.put("method", "icx_getLastBlock");
                    json.put("id", "1234");

                } else {

                    json.put("jsonrpc", "2.0");
                    json.put("method", "icx_getBlockByHash");
                    json.put("id", "1234");
                    JSONObject obj = new JSONObject();
                    obj.put("hash", "0x" + arrayList.get(i - 1));
                    json.put("params", obj);

                }
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
                if (i == 0) {
                    arrayList.add(block_hash);
                } else {
                    arrayList.add(prev_block_hash);
                }
                Log.i("BLOCK  DATA response = ", block_hash);
            }
        } catch (JSONException e) {
            Log.i("json오류", "JSON오류");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("IOEXCEPTION", "JSON오류");
            e.printStackTrace();
        }
        //Log.i("blockInfo###", blockInfo.getCurBlockHash());
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(i + " array: " + arrayList.get(i)); //Class 알아볼 때
        }


        return arrayList;
    }

    public BlockInfo getBlockDetail(String block_hash) {
        try {
            url = new URL(openWeatherURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject json = new JSONObject();

            json.put("jsonrpc", "2.0");
            json.put("method", "icx_getBlockByHash");
            json.put("id", "1234");
            JSONObject obj = new JSONObject();
            obj.put("hash", "0x" + block_hash);
            json.put("params", obj);


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
            Log.i("DATA2 response  ", response);

            JSONObject resultObj= responseJSON.getJSONObject("result");

            JSONArray transactionArray = resultObj.getJSONArray("confirmed_transaction_list");

            Log.i("transaction DATA2 ", String.valueOf(transactionArray.getJSONObject(0)));

            JSONObject transObj = transactionArray.getJSONObject(0);


            blockInfo.setVersion(transObj.getString("version"));
            blockInfo.setFrom(transObj.getString("from"));
            blockInfo.setTo(transObj.getString("to"));
//            blockInfo.setFrom(transObj.getString("value"));
            blockInfo.setStepLimit(transObj.getString("stepLimit"));
            blockInfo.setTimestamp(transObj.getString("timestamp"));
            blockInfo.setNid(transObj.getString("nid"));
            blockInfo.setNonce(transObj.getString("nonce"));
            blockInfo.setSignature(transObj.getString("signature"));
            blockInfo.setTxHash(transObj.getString("txHash"));
            blockInfo.setDataType(transObj.getString("dataType"));


        } catch (JSONException e) {
            Log.i("json오류", "JSON오류");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("IOEXCEPTION", "IOEXCEPTION오류");
            e.printStackTrace();
        }
        return blockInfo;
    }
}