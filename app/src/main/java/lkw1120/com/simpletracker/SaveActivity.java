package lkw1120.com.simpletracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lkw1120.com.simpletracker.Adapter.BlockViewAdapter;

import android.content.Context;
import android.os.Bundle;

public class SaveActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BlockViewAdapter viewAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        context = getApplicationContext();

        recyclerView = findViewById(R.id.saveactivity_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewAdapter = new BlockViewAdapter(context,1);
        viewAdapter.setHasStableIds(true);
        viewAdapter.setBlockList();
        recyclerView.setAdapter(viewAdapter);
    }
}
