package com.example.hansseop.hanseopshin_mobile_tracker.RoomDB;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hansseop.hanseopshin_mobile_tracker.R;
import com.example.hansseop.hanseopshin_mobile_tracker.recycler.DetailActivity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private Context context;

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;
        private final LinearLayout mLayout;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.dbTextView);
            mLayout = itemView.findViewById(R.id.dbListRecycler);
        }
    }

    private final LayoutInflater mInflater;
    private List<Word> mWords; // Cached copy of words

    public WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.db_list, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, final int position) {
        if (mWords != null) {
            Word current = mWords.get(position);
            holder.wordItemView.setText(current.getWord().getResult().getBlock_hash());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("response", mWords.get(position).getWord());
                context.startActivity(intent);
            }
        });
    }

    public void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }

    public List<Word> getAllItem() {
        return mWords;
    }
}