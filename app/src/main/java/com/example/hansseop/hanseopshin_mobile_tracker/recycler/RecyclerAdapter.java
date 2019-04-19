package com.example.hansseop.hanseopshin_mobile_tracker.recycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hansseop.hanseopshin_mobile_tracker.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    private ArrayList<RecyclerItem> mItems;
    private Context context;
    boolean flag;

    public RecyclerAdapter(Context context) {
        this.mItems = new ArrayList<>();
        this.context = context;
        flag = false;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new ItemViewHolder(view);
    }

    public void addItem(RecyclerItem item) {
        this.mItems.add(item);
        notifyDataSetChanged();
    }

    public void addItems(List<RecyclerItem> itemList) {
        this.mItems.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        if (this.mItems.get(position).isClicked() == false) {
            holder.checkBox.setVisibility(View.GONE);
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
        }
        holder.mNameTv.setText(this.mItems.get(position).getResponse().getResult().getBlock_hash());

        holder.total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("response", mItems.get(position).getResponse());
                context.startActivity(intent);
            }
        });

        holder.total.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                if (flag == false) {
                    for (int i = 0; i < getItemCount(); i++) {
                        mItems.get(i).setClicked(true);
                    }
                    ((Activity)context).findViewById(R.id.confirmBtn).setVisibility(View.VISIBLE);
                    flag = true;
                } else {

                    for (int i = 0; i < getItemCount(); i++) {
                        mItems.get(i).setClicked(false);
                    }
                    ((Activity)context).findViewById(R.id.confirmBtn).setVisibility(View.GONE);
                    flag = false;
                }
                notifyDataSetChanged();
                return true;
            }
        });

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(mItems.get(position).isChecked);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mItems.get(position).setChecked(isChecked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameTv;
        private LinearLayout total;
        private CheckBox checkBox;

        ItemViewHolder(View itemView) {
            super(itemView);
            mNameTv = itemView.findViewById(R.id.itemNameTv);
            total = itemView.findViewById(R.id.recycler);
            checkBox = itemView.findViewById(R.id.checked);
        }
    }

    public RecyclerItem getItem(int position) {
        return mItems.get(position);
    }

    public boolean isFlag() {
        return flag;
    }

}
