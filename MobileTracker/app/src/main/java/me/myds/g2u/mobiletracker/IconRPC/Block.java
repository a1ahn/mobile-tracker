package me.myds.g2u.mobiletracker.IconRPC;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Block {
    public JSONObject json_data;

    public Block(JSONObject json_data) {
        this.json_data = json_data;
    }
    public Block(String json_data) {
        try {
            this.json_data = new JSONObject(json_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getPrevBlockHash() {
        String prev_block_hash = null;
        try {
            prev_block_hash = this.json_data.getString("prev_block_hash");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return prev_block_hash;
    }

    public ArrayList<Transaction> getConfirmedTransactionList () {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            JSONArray jsonArray = json_data.getJSONArray("confirmed_transaction_list");
            for (int i = 0; jsonArray.length() > i; i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                transactions.add(new Transaction(jsonObject));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
