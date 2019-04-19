package com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class;

import java.io.Serializable;

public class Data implements Serializable {
    String method;
    ResponseParams params;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ResponseParams getParams() {
        return params;
    }

    public void setParams(ResponseParams params) {
        this.params = params;
    }

    public Data(String method, ResponseParams params) {

        this.method = method;
        this.params = params;
    }
}
