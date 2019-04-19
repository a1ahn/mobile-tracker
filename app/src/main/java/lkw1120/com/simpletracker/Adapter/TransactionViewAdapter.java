package lkw1120.com.simpletracker.Adapter;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import lkw1120.com.simpletracker.R;
import lkw1120.com.simpletracker.ResultActivity;
import lkw1120.com.simpletracker.Task.GetResultTask;

public class TransactionViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> transactionList = new ArrayList<>();

    public void setTransactionList(JSONArray txList) {
        try {
            for (int i = 0; i < txList.length(); i++) {
                transactionList.add(txList.getJSONObject(i).toString());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        holder = new TransactionViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {
            final String transaction = transactionList.get(holder.getLayoutPosition());
            final JSONObject jsonObject = new JSONObject(transaction);
            TransactionViewHolder transactionViewHolder = (TransactionViewHolder) holder;
            transactionViewHolder.txHash.setText(jsonObject.getString("txHash"));
            transactionViewHolder.from.setText(jsonObject.getString("from"));
            transactionViewHolder.to.setText(jsonObject.getString("to"));
            transactionViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(view.getContext(), ResultActivity.class);
                        Bundle bundle = new Bundle();
                        GetResultTask getResultTask = new GetResultTask();
                        String result = getResultTask.execute(jsonObject.getString("txHash")).get();
                        bundle.putString("result",result);
                        intent.putExtra("bundle", bundle);
                        ContextCompat.startActivity(view.getContext(),intent,null);
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    private class TransactionViewHolder extends RecyclerView.ViewHolder {

        private TextView txHash;
        private TextView from;
        private TextView to;

        private TransactionViewHolder(View view) {
            super(view);
            txHash = view.findViewById(R.id.transactionitem_textview_txhash);
            from = view.findViewById(R.id.transactionitem_textview_from);
            to = view.findViewById(R.id.transactionitem_textview_to);
        }
    }

}
