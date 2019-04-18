package me.myds.g2u.mobiletracker.viewholder;

import androidx.annotation.NonNull;

import android.view.View;
import android.widget.TextView;

import me.myds.g2u.mobiletracker.data.Block;
import me.myds.g2u.mobiletracker.R;

public class BlockViewHolder extends BaseRecyclerViewHolder<Block> {
    public TextView txtBlockHash;

    public BlockViewHolder(@NonNull View itemView) {
        super(itemView);
        txtBlockHash = itemView.findViewById(R.id.txt_block_hash);
    }

    @Override
    public void bindData(Block block) {
        this.txtBlockHash.setText(block.getPrevBlockHash());
    }
}
