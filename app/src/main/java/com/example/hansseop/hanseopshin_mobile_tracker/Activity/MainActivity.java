package com.example.hansseop.hanseopshin_mobile_tracker.Activity;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hansseop.hanseopshin_mobile_tracker.Http.RequestHttpURLConnection;
import com.example.hansseop.hanseopshin_mobile_tracker.R;
import com.example.hansseop.hanseopshin_mobile_tracker.databinding.ActivityMainBinding;
import com.example.hansseop.hanseopshin_mobile_tracker.models.request.GetBlockByHeight;
import com.example.hansseop.hanseopshin_mobile_tracker.models.request.GetLastBlock;
import com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class.Response;
import com.example.hansseop.hanseopshin_mobile_tracker.recycler.RecyclerAdapter;
import com.example.hansseop.hanseopshin_mobile_tracker.recycler.RecyclerItem;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private Gson gson;
    private String url = "https://bicon.net.solidwallet.io/api/v3";
    private Response[] resultArr;
    private int height;
    private RecyclerAdapter adapter;

    public static final String WIFI_STATE = "WIFE";
    public static final String MOBILE_STATE = "MOBILE";
    public static final String NONE_STATE = "NONE";
    private boolean newtwork = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        resultArr = new Response[10];
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerAdapter(this);
        binding.view.setHasFixedSize(true);
        binding.view.setLayoutManager(mLayoutManager);
        binding.view.setAdapter(adapter);

        binding.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Response> tempList = new ArrayList<>();
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    if (adapter.getItem(i).isChecked()) {
                        tempList.add(adapter.getItem(i).getResponse());
                    }
                }
                if (tempList.size() != 0) {
                    Intent intent = new Intent(getApplicationContext(), ShowDataActivity.class);
                    String obj = gson.toJson(tempList);
                    intent.putExtra("data", obj);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "데이터를 선택해 주세요!", Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                binding.confirmLayout.setVisibility(View.VISIBLE);
                return true;
            }
        });

        checkNetwork();

        binding.connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNetwork();
            }
        });

        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = Objects.requireNonNull(layoutManager).getItemCount();
                int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisible >= totalItemCount - 1) {
                    for (int i = 0; i < 10; i++) {
                        String getNetwork = getWhatKindOfNetwork(getApplicationContext());

                        if (getNetwork.equals("NONE")) {
                            binding.disConnect.setVisibility(View.VISIBLE);
                            binding.view.setVisibility(View.GONE);
                            newtwork = false;
                            break;
                        } else {
                            binding.disConnect.setVisibility(View.GONE);
                            binding.view.setVisibility(View.VISIBLE);
                            GetBlockByHeight block = new GetBlockByHeight("icx_getBlockByHeight", 1234, height--);
                            String param = gson.toJson(block);
                            ListTask listTask = new ListTask(url, param, i);
                            listTask.execute();
                        }

                    }
                }
            }
        };
        binding.view.addOnScrollListener(onScrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Intent intent = new Intent(this, ShowDataActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public static String getWhatKindOfNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return WIFI_STATE;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return MOBILE_STATE;
            }
        }
        return NONE_STATE;
    }

    public void checkNetwork() {
        String getNetwork = getWhatKindOfNetwork(this);

        if (getNetwork.equals("NONE")) {
            binding.disConnect.setVisibility(View.VISIBLE);
            binding.view.setVisibility(View.GONE);
            newtwork = false;
        } else {
            binding.disConnect.setVisibility(View.GONE);
            binding.view.setVisibility(View.VISIBLE);
            GetLastBlock lastBlockJson = new GetLastBlock("icx_getLastBlock", 1234);
            String param = gson.toJson(lastBlockJson);
            GetLastBlockTask getLastBlockTask = new GetLastBlockTask(url, param);
            getLastBlockTask.execute();
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class GetLastBlockTask extends AsyncTask<Void, Void, String> {

        private String url;
        private String values;

        GetLastBlockTask(String url, String values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            s.replaceAll("&quot;", "\"");

            JsonParser jsonParser = new JsonParser();
            JsonObject jobj = (JsonObject) jsonParser.parse(s);
            JsonObject result = (JsonObject) jobj.get("result");

            height = Integer.parseInt(result.get("height").toString());

            for (int i = 0; i < 10; i++) {
                GetBlockByHeight block = new GetBlockByHeight("icx_getBlockByHeight", 1234, height--);
                String param = gson.toJson(block);
                ListTask listTask = new ListTask(url, param, i);
                listTask.execute();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class ListTask extends AsyncTask<Void, Void, String> {

        private String url;
        private String values;
        private int idx;

        ListTask(String url, String values, int idx) {
            this.url = url;
            this.values = values;
            this.idx = idx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {

            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Response response = gson.fromJson(s, Response.class);

            height = response.getResult().getHeight();
            resultArr[idx] = response;
            if (idx == 9) {
                DisplayTask t = new DisplayTask();
                t.execute();
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    public class DisplayTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            for (Response aResultArr : resultArr) {
                if (adapter.isFlag()) {
                    RecyclerItem r=new RecyclerItem(aResultArr);
                    r.setClicked(true);
                    adapter.addItem(r);
                } else{
                    adapter.addItem(new RecyclerItem(aResultArr));
                }
            }
            binding.progress.setVisibility(View.GONE);
        }
    }
}