package com.example.showblocks;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.showblocks.Global.MySingleton;
import com.example.showblocks.Model.RequestGetLastBlock;
import com.example.showblocks.Model.Result;
import com.example.showblocks.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final String TAG = MainActivity.class.getName();
    private Result[] blocks = new Result[10];
    private static SuccessListener successListener;
    private static FailListener failListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        RequestGetLastBlock requestGetLastBlock = new RequestGetLastBlock(
                getResources().getString(R.string.json_rpc),
                getResources().getString(R.string.method_get_last),
                "0"
        );
        successListener = new SuccessListener();
        failListener = new FailListener();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                getResources().getString(R.string.api_url),
                requestGetLastBlock.toJSON(),
                successListener,
                failListener
        );
        MySingleton.getInstance(this).addToRequestQueue(request);
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

    class SuccessListener implements Response.Listener<JSONObject> {
        @Override
        public void onResponse(JSONObject response) {
            try {
                int id = Integer.parseInt(response.get("id").toString());
                JSONObject result = (JSONObject) response.get("result");
                blocks[id] = parseJSONResultToPOJO(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, blocks[0].toString());
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
}
