package com.example.showblocks.model;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestGetBlockByHash {
    private String jsonrpc;
    private String method;
    private String id;
    private String hash;

    public RequestGetBlockByHash(String jsonrpc, String method, String id, String hash) {
        this.jsonrpc = jsonrpc;
        this.method = method;
        this.id = id;
        this.hash = hash;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject(), params = new JSONObject();
        try {
            object.put("jsonrpc", jsonrpc);
            object.put("method", method);
            object.put("id", id);
            params.put("hash", hash);
            object.put("params", params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
