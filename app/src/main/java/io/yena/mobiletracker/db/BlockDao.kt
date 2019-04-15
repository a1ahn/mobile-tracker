package io.yena.mobiletracker.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface BlockDao {

    @Query("SELECT * FROM block")
    fun getAll(): LiveData<List<Block>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(block: Block)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(blocks: List<Block>)

    @Query("SELECT * FROM block WHERE block_hash IS :hash")
    fun getByHash(hash: String): Block

    @Query("SELECT block_hash FROM block")
    fun getAllBlockHashes(): List<String>
}