package com.example.hansseop.hanseopshin_mobile_tracker.models.request.SubClass;

import java.io.Serializable;

public class Params implements Serializable {
    String height;

    public Params(int height) {
        this.height = "0x" + Integer.toHexString(height);
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = "0x" + Integer.toHexString(height);
    }
}
