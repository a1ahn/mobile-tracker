package me.myds.g2u.mobiletracker.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {BlockEntity.class}, version = 1)
public abstract class BlocksDB extends RoomDatabase {
    public abstract BlockDAO blockDAO();
}
