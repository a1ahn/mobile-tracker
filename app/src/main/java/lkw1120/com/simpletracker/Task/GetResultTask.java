package lkw1120.com.simpletracker.Task;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetResultTask  extends AsyncTask<String, Integer, String> {

    private final String API_URL = "https://bicon.net.solidwallet.io/api/v3";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... values) {
        String result = null;
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject requestJSON = new JSONObject();
            requestJSON.put("jsonrpc","2.0");
            requestJSON.put("method","icx_getTransactionResult");
            requestJSON.put("id","1000");
            JSONObject obj = new JSONObject();
            obj.put("txHash", values[0]);
            requestJSON.put("params", obj);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            bw.write(requestJSON.toString());
            bw.flush();
            bw.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                for(String line = br.readLine();line != null;line = br.readLine()) {
                    sb.append(line);
                }
                String response = sb.toString();
                JSONObject responseJSON = new JSONObject(response);
                result = responseJSON.getJSONObject("result").toString();
            }
            conn.disconnect();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}