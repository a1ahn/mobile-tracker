package me.myds.g2u.mobiletracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import me.myds.g2u.mobiletracker.IconRPC.Transaction;
import me.myds.g2u.mobiletracker.R;
import me.myds.g2u.mobiletracker.utill.BaseRecyclerAdapter;
import me.myds.g2u.mobiletracker.utill.TransactionViewHolder;

public class BlockDetailActivity extends AppCompatActivity {
    public static final String PARAM_TRANSACTION_LIST = "transaction list";

    private TextView txtIndicate;
    private RecyclerView mTransactionistView;
    private LinearLayoutManager mLayoutMgr;
    private BaseRecyclerAdapter<Transaction, TransactionViewHolder> mTransactionListAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_detail);

        Intent intent = getIntent();
        ArrayList<Transaction> transactions = intent.getParcelableArrayListExtra(PARAM_TRANSACTION_LIST);

        txtIndicate = findViewById(R.id.txtIndicate);
        txtIndicate.setText(transactions.size() + " transactions");

        mTransactionistView = findViewById(R.id.transaction_list);
        mLayoutMgr = new LinearLayoutManager(this);
        mTransactionListAdpater = new BaseRecyclerAdapter<Transaction, TransactionViewHolder>(
                R.layout.item_transaction, TransactionViewHolder.class
        ) {
            @Override
            public void onCreateAfterViewHolder(TransactionViewHolder holder) {
                holder.itemView.setOnClickListener((v)->{
                    int itemPosition = holder.getLayoutPosition();
                    Transaction transaction = mTransactionListAdpater.dataList.get(itemPosition);
                    Intent intent = new Intent(BlockDetailActivity.this, TransactionResultActivity.class);
                    intent.putExtra(TransactionResultActivity.PARAM_TRANSACTION, transaction);
                    startActivity(intent);
                });
            }

            @Override
            public void dataConvertViewHolder(TransactionViewHolder holder, Transaction data) {
                holder.dataBind(data);
            }
        };
        mTransactionListAdpater.dataList.addAll(transactions);
        mTransactionistView.setLayoutManager(mLayoutMgr);
        mTransactionistView.setAdapter(mTransactionListAdpater);
    }
}
