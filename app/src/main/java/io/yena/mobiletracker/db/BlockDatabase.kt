package io.yena.mobiletracker.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Block::class], version = 1)
abstract class BlockDatabase: RoomDatabase() {
    abstract fun blockDao(): BlockDao

    companion object {
        private var INSTANCE: BlockDatabase? = null

        fun getInstance(context: Context): BlockDatabase? {
            if (INSTANCE == null) {
                synchronized(BlockDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        BlockDatabase::class.java,
                        "block")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE
        }
    }
}