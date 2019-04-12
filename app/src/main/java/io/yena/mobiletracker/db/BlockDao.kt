package io.yena.mobiletracker.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface BlockDao {

    @Query("SELECT * FROM block")
    fun getAll(): LiveData<List<Block>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(block: Block)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(blocks: List<Block>)

}