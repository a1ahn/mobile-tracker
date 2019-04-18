package me.myds.g2u.mobiletracker.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import me.myds.g2u.mobiletracker.data.Transaction;
import me.myds.g2u.mobiletracker.R;
import me.myds.g2u.mobiletracker.adapter.BaseRecyclerAdapter;
import me.myds.g2u.mobiletracker.viewholder.TransactionViewHolder;

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
                holder.cardView.setOnClickListener((v)->{
                    int itemPosition = holder.getLayoutPosition();
                    Transaction transaction = mTransactionListAdpater.list.get(itemPosition);
                    Intent intent = new Intent(BlockDetailActivity.this, TransactionResultActivity.class);
                    intent.putExtra(TransactionResultActivity.PARAM_TRANSACTION, transaction);
                    startActivity(intent);
//                    overridePendingTransition(R.anim.anim_slide_in_up, R.anim.anim_slide_out_down);
                });
            }

            @Override
            public void dataConvertViewHolder(TransactionViewHolder holder, Transaction data) {
                holder.bindData(data);
            }
        };
        mTransactionListAdpater.list.addAll(transactions);
        mTransactionistView.setLayoutManager(mLayoutMgr);
        mTransactionistView.setAdapter(mTransactionListAdpater);
    }
}
