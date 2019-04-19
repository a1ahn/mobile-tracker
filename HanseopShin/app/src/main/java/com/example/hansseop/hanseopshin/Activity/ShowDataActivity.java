package com.example.hansseop.hanseopshin.Activity;

import android.arch.lifecycle.Observer;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.hansseop.hanseopshin.R;
import com.example.hansseop.hanseopshin.RoomDB.Word;
import com.example.hansseop.hanseopshin.RoomDB.WordListAdapter;
import com.example.hansseop.hanseopshin.RoomDB.WordViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

public class ShowDataActivity extends AppCompatActivity {

    ActivityShowDataBinding binding;
    private WordViewModel mWordViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getStringExtra("data") != null) {
            Gson gson = new Gson();
            JsonObject[] arr = gson.fromJson(getIntent().getStringExtra("data"), JsonObject[].class);

            for(int i=0;i<arr.length;i++) {
                String temp=arr[i].toString();
                System.out.println("temp is "+temp);
                Word word = new Word(temp);
                mWordViewModel.insert(word);
            }
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_data);
        final WordListAdapter adapter=new WordListAdapter(this);
        binding.dbList.setAdapter(adapter);
        binding.dbList.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });
    }


}

