package me.myds.g2u.mobiletracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import me.myds.g2u.mobiletracker.IconRPC.rpcConnection;
import me.myds.g2u.mobiletracker.IconRPC.rpcRequest;
import me.myds.g2u.mobiletracker.IconRPC.rpcResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new rpcConnection(new rpcRequest(rpcRequest.ICX_GET_LAST_BLOCK), new rpcConnection.OnResponse() {
                    @Override
                    public void onResponse(rpcResponse response) {
                        Log.i("result", response.toString());
                    }
                });
            }
        });
    }
}
