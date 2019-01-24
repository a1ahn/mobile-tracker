package com.example.hyunju.icon_mobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

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
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    String message = "abc";
                    intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}


