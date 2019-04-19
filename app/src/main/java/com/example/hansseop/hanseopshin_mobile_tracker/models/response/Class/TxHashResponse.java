package com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class;

import java.io.Serializable;

public class TxHashResponse implements Serializable {
    String jsonrpc;
    TxHashResult result;
    String id;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public TxHashResult getResult() {
        return result;
    }

    public void setResult(TxHashResult result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TxHashResponse(String jsonrpc, TxHashResult result, String id) {

        this.jsonrpc = jsonrpc;
        this.result = result;
        this.id = id;
    }
}
