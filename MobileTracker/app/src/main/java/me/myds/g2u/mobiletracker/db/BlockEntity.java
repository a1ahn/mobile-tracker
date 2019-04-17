package me.myds.g2u.mobiletracker.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(indices = @Index("block_hash"))
public class BlockEntity {

    @PrimaryKey
    @NotNull
    private String block_hash;
    public String getBlock_hash() { return block_hash; }
    public void setBlock_hash(String block_hash) { this.block_hash = block_hash; }

    long time_stamp;
    public long getTime_stamp() { return time_stamp; }
    public void setTime_stamp(long time_stamp) { this.time_stamp = time_stamp; }

    String body;
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}
