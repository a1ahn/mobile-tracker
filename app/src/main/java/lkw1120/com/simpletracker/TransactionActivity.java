package lkw1120.com.simpletracker;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lkw1120.com.simpletracker.Adapter.TransactionViewAdapter;

public class TransactionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TransactionViewAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        try {
            String result = getIntent().getBundleExtra("bundle").getString("result");
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("confirmed_transaction_list");
            recyclerView = findViewById(R.id.transactionactivity_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(TransactionActivity.this));
            viewAdapter = new TransactionViewAdapter();
            viewAdapter.setTransactionList(jsonArray);
            recyclerView.setAdapter(viewAdapter);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
