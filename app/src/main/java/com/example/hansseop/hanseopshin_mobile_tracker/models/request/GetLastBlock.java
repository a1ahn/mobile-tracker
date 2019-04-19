package com.example.hansseop.hanseopshin_mobile_tracker.models.request;

import java.io.Serializable;

public class GetLastBlock implements Serializable {
    String jsonrpc;
    String method;
    int id;

    public GetLastBlock(String method, int id) {
        jsonrpc = "2.0";
        this.method = method;
        this.id = id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public int getId() {
        return id;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setId(int id) {
        this.id = id;
    }
}
