package me.myds.g2u.mobiletracker.IconRPC;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class TransactionResult {
    public JSONObject json_data;

    public TransactionResult (JSONObject json_data) {
        this.json_data = json_data;
    }

    @NonNull
    @Override
    public String toString() {
        try {
            return this.json_data.toString(2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
