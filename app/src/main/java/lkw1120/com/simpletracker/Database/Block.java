package lkw1120.com.simpletracker.Database;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="Block")
public class Block {

    @NonNull
    @PrimaryKey
    private String blockHash;
    private String height;
    private String result;
    @Ignore
    private boolean isSelected = false;

    @NonNull
    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(@NonNull String blockHash) {
        this.blockHash = blockHash;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
