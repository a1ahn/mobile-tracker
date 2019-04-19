package com.example.hansseop.hanseopshin_mobile_tracker.RoomDB;

import android.os.AsyncTask;

public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final WordDao mDao;

    PopulateDbAsync(WordRoomDatabase db) {
        mDao = db.wordDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {
//        mDao.deleteAll();
//        Word word = new Word();
//        mDao.insert(word);
//        word = new Word("World");
//        mDao.insert(word);
        return null;
    }
}
