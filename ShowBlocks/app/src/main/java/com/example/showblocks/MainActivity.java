package com.example.showblocks;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.showblocks.Event.FetchedBlockEvent;
import com.example.showblocks.Global.MySingleton;
import com.example.showblocks.Model.RequestGetBlockByHash;
import com.example.showblocks.Model.RequestGetLastBlock;
import com.example.showblocks.Model.Result;
import com.example.showblocks.databinding.ActivityMainBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final String TAG = MainActivity.class.getName();
    private static Result[] blocks = new Result[10];
    private static SuccessListener successListener;
    private static FailListener failListener;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        binding.currentTime.setVisibility(View.INVISIBLE);
        binding.recyclerView.setVisibility(View.INVISIBLE);
        binding.relativeLayout.getLayoutParams().height = RecyclerView.LayoutParams.MATCH_PARENT;
        binding.spinner.setVisibility(View.VISIBLE);

        layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        successListener = new SuccessListener();
        failListener = new FailListener();
        getLastBlock();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_reload) {
            Log.d(TAG, "selected");
        }
        return super.onOptionsItemSelected(item);
    }

    void getLastBlock() {
        RequestGetLastBlock requestGetLastBlock = new RequestGetLastBlock(
                getResources().getString(R.string.json_rpc),
                getResources().getString(R.string.method_get_last),
                "0"
        );
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                getResources().getString(R.string.api_url),
                requestGetLastBlock.toJSON(),
                successListener,
                failListener
        );
        request.setTag("0");
        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFetchedBlockEvent(FetchedBlockEvent event) {
        int previousId = Integer.parseInt(event.getId());
        if (previousId < 9) {
            getBlockByHash(previousId + 1);
        } else {
            Date today = Calendar.getInstance().getTime();
            binding.currentTime.setText("fetch 시점 : " + DateFormat.getDateTimeInstance().format(today));
            binding.currentTime.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.relativeLayout.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
            binding.spinner.setVisibility(View.INVISIBLE);
        }
        Log.d(TAG, "event Bus deliver : " + event.getId());
    }

    void getBlockByHash(int id) {
        RequestGetBlockByHash requestGetBlockByHash = new RequestGetBlockByHash(
                getResources().getString(R.string.json_rpc),
                getResources().getString(R.string.method_get_by_hash),
                String.valueOf(id),
                "0x" + blocks[id - 1].getPrevBlockHash()
        );
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                getResources().getString(R.string.api_url),
                requestGetBlockByHash.toJSON(),
                successListener,
                failListener
        );
        request.setTag(String.valueOf(id));
        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    class SuccessListener implements Response.Listener<JSONObject> {
        @Override
        public void onResponse(JSONObject response) {
            try {
                int id = Integer.parseInt(response.get("id").toString());
                JSONObject result = (JSONObject) response.get("result");
                blocks[id] = parseJSONResultToPOJO(result);

                for (int i = 0; i <= id; i++) {
                    Log.d(TAG, i + " : " + blocks[i].toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public Result parseJSONResultToPOJO(JSONObject o) {
            Result r = null;
            try {
                r = new Result(
                        o.get("version").toString(),
                        o.get("prev_block_hash").toString(),
                        o.get("merkle_tree_root_hash").toString(),
                        Long.parseLong(o.get("time_stamp").toString()),
                        (JSONArray) o.get("confirmed_transaction_list"),
                        o.get("block_hash").toString(),
                        Integer.parseInt(o.get("height").toString()),
                        o.get("peer_id").toString(),
                        o.get("signature").toString()
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return r;
        }
    }

    class FailListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, error.toString());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
