package me.myds.g2u.mobiletracker.IconRPC;

import org.json.JSONException;
import org.json.JSONObject;

public class Block {
    public JSONObject json_data;

    public Block(JSONObject json_data) {
        this.json_data = json_data;
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
}
