package com.example.hansseop.hanseopshin_mobile_tracker.RoomDB;

import com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class.Response;
import com.google.gson.Gson;

import java.io.Serializable;

import androidx.room.TypeConverter;

public class DataConverter implements Serializable {

    @TypeConverter // note this annotation
    public String fromResponse(Response response) {
        if (response == null) {
            return (null);
        }
        Gson gson = new Gson();
        String json = gson.toJson(response, Response.class);
        return json;
    }

    @TypeConverter // note this annotation
    public Response toResponse(String responseString) {
        if (responseString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Response response = gson.fromJson(responseString, Response.class);
        return response;
    }

}
