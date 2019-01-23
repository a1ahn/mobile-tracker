package com.example.hyunju.icon_mobileproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    TextView tv ;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.textView1);
        listview = (ListView)findViewById(R.id.listView1);
        BlockAPITask t = new BlockAPITask();
        try {
            BlockInfo b= t.execute().get();
            tv.setText(b.getCurBlockHash());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    // MapView 참고 http://seuny.tistory.com/14
//    public void getWeather(View view)
//    {
//        EditText tvLon = (EditText) findViewById(R.id.lon);
//        String strLon = tvLon.getText().toString();
//        int lon = Integer.parseInt(strLon);
//        EditText tvLat = (EditText) findViewById(R.id.lat);
//        String strLat = tvLat.getText().toString();
//        int lat = Integer.parseInt(strLat);
//
//        // 날씨를 읽어오는 API 호출
//
//        BlockAPITask t = new BlockAPITask();
//        try {
//            //Weather w = t.execute(lon, lat).get();
//            Weather w = t.execute().get();
//            System.out.println("Temp :" + w.getTemprature());
//            TextView tem = (TextView) findViewById(R.id.tem);
//            String temperature = String.valueOf(w.getTemprature());
//            tem.setText(temperature);
//            //w.getTemprature());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//
//        }
//
//    }
//
//}
//
//

