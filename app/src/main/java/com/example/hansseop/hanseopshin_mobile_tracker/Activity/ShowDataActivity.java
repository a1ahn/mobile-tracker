package com.example.hansseop.hanseopshin_mobile_tracker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hansseop.hanseopshin_mobile_tracker.R;
import com.example.hansseop.hanseopshin_mobile_tracker.RoomDB.Word;
import com.example.hansseop.hanseopshin_mobile_tracker.RoomDB.WordListAdapter;
import com.example.hansseop.hanseopshin_mobile_tracker.RoomDB.WordViewModel;
import com.example.hansseop.hanseopshin_mobile_tracker.databinding.ActivityShowDataBinding;
import com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ShowDataActivity extends AppCompatActivity {
    ActivityShowDataBinding binding;
    private WordViewModel mWordViewModel;
    List<Word> stored;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_data);
        final WordListAdapter adapter = new WordListAdapter(this);
        binding.dbList.setAdapter(adapter);
        binding.dbList.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                adapter.setWords(words);
            }
        });

        if (getIntent().getStringExtra("data") != null) {
            Gson gson = new Gson();
            JsonObject[] arr = gson.fromJson(getIntent().getStringExtra("data"), JsonObject[].class);
            String dup = "";

            stored = adapter.getAllItem();

            for (int i = 0; i < arr.length; i++) {

                Response response = gson.fromJson(arr[i], Response.class);


                if (stored != null) {

                    System.out.println("123123123");
//                    for(int j=0;i<stored.size();j++){
//                        System.out.println("stored : "+stored.size());
//                        System.out.println("stored : "+stored.get(j).getWord().getResult().getBlock_hash());
//                    }
                    if (!stored.contains(response)) {
                        Word word = new Word(response);
                        mWordViewModel.insert(word);
                    } else if (stored.contains(response)) {
                        dup += response.getResult().getBlock_hash() + "\n";
                    }
                } else {
                    Word word = new Word(response);
                    mWordViewModel.insert(word);
                }
            }
            if (!dup.equals(""))
                Toast.makeText(getApplicationContext(), "중복 되는 데이터!\n" + dup, Toast.LENGTH_LONG);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                mWordViewModel.deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }
}
