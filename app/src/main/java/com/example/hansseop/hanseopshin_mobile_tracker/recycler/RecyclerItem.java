package com.example.hansseop.hanseopshin_mobile_tracker.recycler;

import com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class.Response;

public class RecyclerItem {
    Response response;
    boolean isChecked;
    boolean isClicked;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public RecyclerItem(Response response) {
        this.response = response;
        this.isChecked = false;
        this.isClicked = false;
    }
}
