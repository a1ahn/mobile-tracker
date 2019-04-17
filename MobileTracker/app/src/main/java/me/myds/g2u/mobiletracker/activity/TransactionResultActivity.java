package me.myds.g2u.mobiletracker.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import me.myds.g2u.mobiletracker.IconRPC.Transaction;
import me.myds.g2u.mobiletracker.IconRPC.TransactionResult;
import me.myds.g2u.mobiletracker.IconRPC.rpcConnection;
import me.myds.g2u.mobiletracker.IconRPC.rpcRequest;
import me.myds.g2u.mobiletracker.IconRPC.rpcRequestException;
import me.myds.g2u.mobiletracker.IconRPC.rpcResponse;
import me.myds.g2u.mobiletracker.IconRPC.rpcResponseException;
import me.myds.g2u.mobiletracker.R;

public class TransactionResultActivity extends AppCompatActivity {

    public static final String PARAM_TRANSACTION = "transaction";
    private static final int COMPLETE_LOAD_BLOCKS = 22;

    public TextView txtResult;
    public ProgressBar progress;
    TransactionResult transactionResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_result);

        progress = findViewById(R.id.progress);
        txtResult = findViewById(R.id.txtResult);

        Intent intent = getIntent();
        Transaction transaction = intent.getParcelableExtra(PARAM_TRANSACTION);
        loadTransactionResult(transaction.getTxHash());
    }

    void printTransactionResult () {
        if (transactionResult == null) return;

        txtResult.setText(transactionResult.toString());
    }

    void loadTransactionResult (String txHash) {
        new Thread(() -> {
            try {
                JSONObject requestParams = new JSONObject();
                requestParams.put("txHash", txHash);
                rpcRequest request = new rpcRequest(rpcRequest.ICX_GET_TRANSACTION_RESULT, requestParams);
                rpcResponse response = null;
                response = rpcConnection.connect(request);
                transactionResult = new TransactionResult((JSONObject) response.result);

            } catch (rpcRequestException e) {
                e.printStackTrace();
            } catch (rpcResponseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                mHandler.sendEmptyMessage(COMPLETE_LOAD_BLOCKS);
            }
        }).start();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NotNull Message msg) {
            if(msg.what == COMPLETE_LOAD_BLOCKS) {
                progress.setVisibility(View.GONE);
                printTransactionResult();
            }
        }
    };
}
