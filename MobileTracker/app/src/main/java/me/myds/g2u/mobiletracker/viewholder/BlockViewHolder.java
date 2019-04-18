package me.myds.g2u.mobiletracker.viewholder;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import me.myds.g2u.mobiletracker.HashImage;
import me.myds.g2u.mobiletracker.data.Block;
import me.myds.g2u.mobiletracker.R;

public class BlockViewHolder extends BaseRecyclerViewHolder<Block> {

    public TextView txtBlockHash;
    public HashImage hashImage;
    public TextView txtTime;
    public ImageView is_saved;

    public BlockViewHolder(@NonNull View itemView) {
        super(itemView);
        txtBlockHash = itemView.findViewById(R.id.txt_block_hash);
        hashImage = itemView.findViewById(R.id.imgHash);
        txtTime = itemView.findViewById(R.id.txtTime);
        is_saved = itemView.findViewById(R.id.is_saved);
    }

    @Override
    public void bindData(Block block) {
        txtBlockHash.setText(block.getPrevBlockHash());
        hashImage.setHashString(block.getPrevBlockHash());

//        long unixTime = System.currentTimeMillis();
//        Log.e("block", "" +block.getTimeStamp() );
//        Log.e("cuurn", "" + unixTime);
//        long min = (new Date().getTime() - (block.getTimeStamp() / 1000)) / 6;
////        long min = diff / 60000;
////        long hour = min / 60;
////        min -= hour * 60;
//
        long unixTime = System.currentTimeMillis();
        long timestamp = block.getTimeStamp();


        txtTime.setText("");
    }

    public void setSaved(boolean save) {
        is_saved.setVisibility(save ? View.VISIBLE : View.INVISIBLE);
    }
}
