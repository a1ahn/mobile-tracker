package com.example.showblocks;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.showblocks.databinding.ActivityDetailBlockBinding;
import com.example.showblocks.model.Transaction;

import org.json.JSONException;
import org.json.JSONObject;


public class DetailBlockActivity extends AppCompatActivity {
    private static final String TAG = DetailBlockActivity.class.getName();
    private ActivityDetailBlockBinding binding;
    private Transaction[] transactions;
    private int transactionIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_block);
        binding.setHandler(this);

        int listSize = getIntent().getIntExtra("listSize", 0);
        binding.btnChange.setVisibility(listSize == 1 ? View.INVISIBLE : View.VISIBLE);
        transactions = new Transaction[listSize];
        try {
            for (int i = 0; i < listSize; i++) {
                JSONObject transaction = new JSONObject(getIntent().getStringExtra("result" + i));
                transactions[i] = new Transaction(
                        transaction.getString("from"),
                        transaction.getString("to"),
                        transaction.getString("version"),
                        transaction.getString("nid"),
                        transaction.getString("stepLimit"),
                        transaction.getString("timestamp"),
                        transaction.getString("nonce"),
                        transaction.getString("dataType"),
                        transaction.getString("data"),
                        transaction.getString("signature"),
                        transaction.getString("txHash")
                );
            }
            binding.setTransaction(transactions[transactionIndex++]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClickedChangeButton(View view) {
        binding.setTransaction(transactions[transactionIndex++]);
    }
}
