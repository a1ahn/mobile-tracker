package lkw1120.com.simpletracker;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import lkw1120.com.simpletracker.Adapter.BlockViewAdapter;
import lkw1120.com.simpletracker.Database.Block;
import lkw1120.com.simpletracker.Task.InsertBlockTask;

public class BlockActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    BlockViewAdapter viewAdapter;
    FloatingActionButton fab;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);
        context = getApplicationContext();

        recyclerView = findViewById(R.id.blockactivity_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewAdapter = new BlockViewAdapter(context,0);
        viewAdapter.setHasStableIds(true);
        viewAdapter.setBlockList();
        recyclerView.setAdapter(viewAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView reView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewAdapter.setBlockList();
                    viewAdapter.notifyDataSetChanged();
                }
            }
        });
        swipeRefreshLayout = findViewById(R.id.blockactivity_swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!viewAdapter.isSaveMode()) {
                    recyclerView.removeAllViewsInLayout();
                    viewAdapter = new BlockViewAdapter(context, 0);
                    viewAdapter.setBlockList();
                    recyclerView.setAdapter(viewAdapter);
                    viewAdapter.notifyDataSetChanged();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAdapter.saveBlockList();
                viewAdapter.setSaveMode(false);
                Intent intent = new Intent(context,SaveActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        viewAdapter.notifyDataSetChanged();
    }

    public void onBackPressed() {
        if(viewAdapter.isSaveMode()) {
            viewAdapter.toggleSaveMode();
            viewAdapter.notifyDataSetChanged();
        }
        else {
            finish();
        }
    }
}
