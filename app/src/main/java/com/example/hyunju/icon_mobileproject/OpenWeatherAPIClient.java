//package com.example.hyunju.icon_mobileproject;
//
///**
// * Created by hyunju on 2019-01-23.
// */
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//public class OpenWeatherAPIClient {
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
