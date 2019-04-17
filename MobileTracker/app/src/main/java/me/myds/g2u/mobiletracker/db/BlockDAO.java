package me.myds.g2u.mobiletracker.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BlockDAO {

    @Query("SELECT * FROM BlockEntity ORDER BY time_stamp DESC")
    List<BlockEntity> getAll();

    @Insert
    void insertAll(BlockEntity ...blockEntities);

    @Delete
    void deleteAll(BlockEntity ...blockEntities);
}
