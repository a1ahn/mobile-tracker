package com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class;

import java.io.Serializable;

public class Response implements Serializable {
    String jsonrpc;
    Result result;
    int id;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Response(String jsonrpc, Result result, int id) {

        this.jsonrpc = jsonrpc;
        this.result = result;
        this.id = id;
    }
}
