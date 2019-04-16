package me.myds.g2u.mobiletracker.block_db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BlockDAO {

    @Query("SELECT * FROM BlockEntity ORDER BY time_stamp DESC")
    List<BlockEntity> getAll();

    @Insert
    void insertAll(BlockEntity ...blockEntities);

    @Delete
    void deleteAll(BlockEntity ...blockEntities);
}
