package me.myds.g2u.mobiletracker.block_db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {BlockEntity.class}, version = 1)
public abstract class BlocksDB extends RoomDatabase {
    public abstract BlockDAO blockDAO();
}
