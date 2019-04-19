package com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class;

import java.io.Serializable;

public class ResponseParams implements Serializable {
    String _value;
    String _to;

    public ResponseParams(String _value, String _to) {
        this._value = _value;
        this._to = _to;
    }

    public String get_value() {
        return _value;
    }

    public void set_value(String _value) {
        this._value = _value;
    }

    public String get_to() {
        return _to;
    }

    public void set_to(String _to) {
        this._to = _to;
    }
}
