package com.example.hansseop.hanseopshin_mobile_tracker.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.hansseop.hanseopshin_mobile_tracker.Http.RequestHttpURLConnection;
import com.example.hansseop.hanseopshin_mobile_tracker.R;
import com.example.hansseop.hanseopshin_mobile_tracker.databinding.ActivityTxHashBinding;
import com.example.hansseop.hanseopshin_mobile_tracker.models.request.GetTransactionByHash;
import com.example.hansseop.hanseopshin_mobile_tracker.models.request.SubClass.TxHashParams;
import com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class.TxHashResponse;
import com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class.TxHashResult;
import com.google.gson.Gson;

public class TxHashActivity extends AppCompatActivity {

    ActivityTxHashBinding binding;
    private String url = "https://bicon.net.solidwallet.io/api/v3";
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tx_hash);
        gson = new Gson();
        String txHash = getIntent().getStringExtra("txHash");

        GetTransactionByHash transactionByHashJson = new GetTransactionByHash("icx_getTransactionByHash", 1234, new TxHashParams(txHash));
        String param = gson.toJson(transactionByHashJson);
        GetTransactionByHashTask getLastBlockTask = new GetTransactionByHashTask(url, param);
        getLastBlockTask.execute();
    }

    public class GetTransactionByHashTask extends AsyncTask<Void, Void, String> {

        private String url;
        private String values;

        GetTransactionByHashTask(String url, String values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.progress2.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            TxHashResponse txHashResponse = gson.fromJson(s, TxHashResponse.class);
            TxHashResult result = txHashResponse.getResult();

            binding.txStepLimitText.setText(result.getStepLimit());
            binding.txSignatureText.setText(result.getSignature());
            binding.txDataTypeText.setText(result.getDataType());
            binding.txNidText.setText(result.getNid());
            binding.txFromText.setText(result.getFrom());
            binding.txToText.setText(result.getTo());
            binding.txVersionText.setText(result.getVersion());
            binding.txNonceText.setText(result.getNonce());
            binding.txTimestampText.setText(result.getTimestamp());
            binding.txTxHashText.setText(result.getTxHash());
            binding.txTxIndexText.setText(result.getTxIndex());
            binding.txBlockHeightText.setText(result.getBlockHeight());
            binding.txBlockHashText.setText(result.getBlockHash());
            binding.progress2.setVisibility(View.GONE);
        }
    }
}
