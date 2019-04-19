package lkw1120.com.simpletracker.Database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BlockDao {

    @Query("SELECT * FROM Block ORDER BY height DESC")
    List<Block> blockAll();

    @Query("SELECT * FROM Block WHERE blockHash IN (:hash)")
    Block blockByHash(String hash);

    @Insert
    void insertBlock(Block block);

    @Delete
    void deleteBlock(Block block);
}
