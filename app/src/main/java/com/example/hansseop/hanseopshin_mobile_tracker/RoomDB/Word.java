package com.example.hansseop.hanseopshin_mobile_tracker.RoomDB;

import com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class.Response;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    @NonNull
    @TypeConverters(DataConverter.class)
    @ColumnInfo(name = "word")
    private Response mWord;

    public Word(Response word) {
        this.mWord = word;
    }

    public Response getWord() {
        return this.mWord;
    }
}