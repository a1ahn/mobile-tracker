package com.example.hyunju.icon_mobileproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class Main2Activity extends AppCompatActivity {
    TextView txt_title, tx1, tx2,tx3,tx4,tx5,tx6,tx7,tx8,tx9,tx10,tx11;
    BlockDetailAPITask apiTask = new BlockDetailAPITask();
    BlockInfo blockInfo = new BlockInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txt_title = (TextView) findViewById(R.id.txt_hash);
        tx1 = (TextView) findViewById(R.id.textView1);
        tx2 = (TextView) findViewById(R.id.textView2);
        tx3 = (TextView) findViewById(R.id.textView3);
        tx4 = (TextView) findViewById(R.id.textView4);
        tx5 = (TextView) findViewById(R.id.textView5);
        tx6 = (TextView) findViewById(R.id.textView6);
        tx7 = (TextView) findViewById(R.id.textView7);
        tx8 = (TextView) findViewById(R.id.textView8);
        tx9 = (TextView) findViewById(R.id.textView9);
        tx10 = (TextView) findViewById(R.id.textView10);
        tx11 = (TextView) findViewById(R.id.textView11);

        Intent intent = getIntent();
        String block_title = intent.getExtras().getString("block_hash");
        txt_title.setText("Block Hash Title :  " + block_title);


        try {
            blockInfo = apiTask.execute(block_title).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        tx1.setText("version : "+blockInfo.getVersion());
        tx2.setText("from : "+blockInfo.getFrom());
        tx3.setText("to : "+blockInfo.getTo());
        //tx4.setText("value : "+blockInfo.getValue());
        tx5.setText("stepLimit : "+blockInfo.getStepLimit());
        tx6.setText("timestamp : "+blockInfo.getTimestamp());
        tx7.setText("nid : "+blockInfo.getNid());
        tx8.setText("nonce : "+blockInfo.getNonce());
        tx9.setText("signature : "+blockInfo.getSignature());
        tx10.setText("txHash : "+blockInfo.getTxHash());
        tx11.setText("dataType : "+blockInfo.getDataType());

    }
}
