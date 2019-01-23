package com.example.hyunju.icon_mobileproject;

/**
 * Created by hyunju on 2019-01-23.
 */

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

public class OpenWeatherAPIClient {
    BlockInfo blockInfo = new BlockInfo();
    ArrayList<String> arrayList = new ArrayList<String>();
    public BlockInfo getBlock() {
     try {
        HttpURLConnection conn;
        final String openWeatherURL = "https://bicon.net.solidwallet.io/api/v3";
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        URL url = null;


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
                obj.put("hash", "0x"+arrayList.get(i-1));
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

            arrayList.add(prev_block_hash);
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
        for (int i = 0; i<arrayList.size(); i++) {
        System.out.println(i + " array: " + arrayList.get(i)); //Class 알아볼 때
    }


        return blockInfo;
}


}

//    final static String openWeatherURL = "https://bicon.net.solidwallet.io/api/v3";
//    public Weather getWeather(int lat, int lon) {
//       Weather w = new Weather();
//        //String urlString = openWeatherURL + "?lat=" + lat + "&lon=" + lon + "&appid=b6907d289e10d714a6e88b30761fae22";
//        try {
//            // call API by using HTTPURLConnection
//            URL url = new URL(openWeatherURL);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//            JSONObject json = new JSONObject(getStringFromInputStream(in));
//            json.put("jsonrpc","2.0");
//            json.put("method","icx_getLastBlock");
//            json.put("id","1234");
//
//            // parse JSON
//            w = parseJSON(json);
//            w.setIon(lon);
//            w.setLat(lat);
//        } catch (MalformedURLException e) {
//            System.err.println("Malformed URL");
//            e.printStackTrace();
//            return null;
//        } catch (JSONException e) {
//            System.err.println("JSON parsing error");
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            System.err.println("URL Connection failed");
//            e.printStackTrace();
//            return null;
//        }
//        // set Weather Object
//        return w;
//    }
//    private Weather parseJSON(JSONObject json) throws JSONException {
//       Weather w = new Weather();
//        w.setTemprature(json.getJSONObject("main").getString("result"));
//        //w.setCity(json.getString("result"));
//        //w.setCloudy();
//        return w;
//    }
//    private static String getStringFromInputStream(InputStream is) {
//        BufferedReader br = null;
//        StringBuilder sb = new StringBuilder();
//        String line;
//        try {
//            br = new BufferedReader(new InputStreamReader(is));
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return sb.toString();
//    }
//}
//
//
//
