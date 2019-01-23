package com.example.hyunju.icon_mobileproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    TextView tv ;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tv=(TextView)findViewById(R.id.textView1);
        listview = (ListView)findViewById(R.id.listView1);
        BlockAPITask t = new BlockAPITask();
        try {
            ArrayList arr= t.execute().get();
            //tv.setText(b.getCurBlockHash());
            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);


            listview.setAdapter(itemsAdapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}


