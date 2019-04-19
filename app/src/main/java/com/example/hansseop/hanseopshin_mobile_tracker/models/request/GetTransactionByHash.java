package com.example.hansseop.hanseopshin_mobile_tracker.models.request;

import com.example.hansseop.hanseopshin_mobile_tracker.models.request.SubClass.TxHashParams;

import java.io.Serializable;

public class GetTransactionByHash implements Serializable {
    String jsonrpc;
    String method;
    int id;
    TxHashParams params;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TxHashParams getParams() {
        return params;
    }

    public void setParams(TxHashParams params) {
        this.params = params;
    }

    public GetTransactionByHash(String method, int id, TxHashParams params) {

        this.jsonrpc = "2.0";
        this.method = method;
        this.id = id;
        this.params = params;
    }
}
