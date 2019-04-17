package me.myds.g2u.mobiletracker.db;

import android.content.Context;

import androidx.room.Room;

public class LocalBlocks {
    private static BlocksDB mDB = null;
    private static BlockDAO mDAO = null;

    public static void init (Context context) {
        mDB = Room.databaseBuilder(context, BlocksDB.class, "local-blocks").build();
        mDAO = mDB.blockDAO();
    }

    public static BlockDAO run() {
        return mDAO;
    }

    private LocalBlocks() {

    }
}
