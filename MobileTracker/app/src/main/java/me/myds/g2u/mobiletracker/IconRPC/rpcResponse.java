package me.myds.g2u.mobiletracker.IconRPC;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class rpcResponse {

    public String jsonrpc = null;
    public int id = -1;

    public String strResult = null;
    public JSONObject result = null;

    public int error_code = -1;
    public String error_msg = null;

    public JSONObject json_data = null;

    public rpcResponse(String json_string) {
        try {
            this.json_data = new JSONObject(json_string);

            this.jsonrpc = this.json_data.getString("jsonrpc");
            this.id = this.json_data.getInt("id");
            if (!this.json_data.isNull("error")) {
                JSONObject error = this.json_data.getJSONObject("error");
                this.error_code = error.getInt("code");
                this.error_msg = error.getString("message");
            } else {
                Object o = this.json_data.get("result");
                if (o instanceof String) {
                    this.strResult = (String)o;
                } else {
                    this.result = (JSONObject)o;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return this.json_data.toString();
    }
}
