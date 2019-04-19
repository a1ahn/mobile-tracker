package com.example.hansseop.hanseopshin_mobile_tracker.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hansseop.hanseopshin_mobile_tracker.R;
import com.example.hansseop.hanseopshin_mobile_tracker.databinding.ActivityDetailBinding;
import com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class.Response;
import com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class.Transaction;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;

    Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        response = (Response) getIntent().getSerializableExtra("response");
        final Transaction t=response.getResult().getConfirmed_transaction_list().get(0);

        binding.titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),TxHashActivity.class);
                System.out.println("getTxHash : "+t.getTxHash());
                intent.putExtra("txHash",t.getTxHash());
                startActivity(intent);
            }
        });

        binding.fromText.setText(t.getTo());
        binding.toText.setText(t.getFrom());
        binding.txHashText.setText(t.getTxHash());


        binding.stepLimitText.setText(t.getStepLimit());
        binding.signatureText.setText(t.getSignature());
        binding.dataTypeText.setText(t.getDataType());
        binding.nidText.setText(t.getNid());
        binding.fromText2.setText(t.getFrom());
        binding.toText2.setText(t.getTo());
        binding.versionText.setText(t.getVersion());
        binding.nonceText.setText(t.getNonce());
        binding.timestampText.setText(t.getTimestamp());
        binding.txHashText2.setText(t.getTxHash());
    }
}
