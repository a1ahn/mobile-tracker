package com.example.hansseop.hanseopshin_mobile_tracker.models.request;

import com.example.hansseop.hanseopshin_mobile_tracker.models.request.SubClass.Params;

import java.io.Serializable;

public class GetBlockByHeight implements Serializable {
    String jsonrpc;
    String method;
    int id;
    Params params;

    public GetBlockByHeight(String method, int id, int height) {
        this.jsonrpc = "2.0";
        this.method = method;
        this.id = id;
        this.params = new Params(height);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }
}

