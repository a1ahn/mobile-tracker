package com.example.showblocks.model;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestGetLastBlock {
    private String jsonrpc;
    private String method;
    private String id;

    public RequestGetLastBlock(String jsonrpc, String method, String id) {
        this.jsonrpc = jsonrpc;
        this.method = method;
        this.id = id;
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

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("jsonrpc", jsonrpc);
            object.put("method", method);
            object.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
