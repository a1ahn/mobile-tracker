package lkw1120.com.simpletracker.Adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import lkw1120.com.simpletracker.R;

public class BlockViewHolder extends RecyclerView.ViewHolder implements Checkable{

    public TextView blockHash;
    public boolean isChecked;

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
        if(isChecked) {
            this.itemView.setBackgroundColor(Color.GREEN);
        }
        else {
            this.itemView.setBackgroundColor(Color.WHITE);
        }
    }
    @Override
    public boolean isChecked(){
        return isChecked;
    }

    @Override
    public void toggle(){
        setChecked(!isChecked);
    }

    public BlockViewHolder(View view) {
        super(view);
        blockHash = view.findViewById(R.id.blockitem_textview_blockhash);
    }
}
