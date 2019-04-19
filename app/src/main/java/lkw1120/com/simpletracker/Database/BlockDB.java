package lkw1120.com.simpletracker.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Block.class}, version = 1, exportSchema = false)
public abstract class BlockDB extends RoomDatabase {
    public abstract BlockDao blockDao();

    private static BlockDB INSTANCE;

    private static final Object sLock = new Object();

    public static BlockDB getInstance(Context context) {
        synchronized (sLock) {
            if(INSTANCE==null) {
                INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                        BlockDB.class, "Blocks.db").build();
            }
            return INSTANCE;
        }
    }

}
