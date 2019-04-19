package lkw1120.com.simpletracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textView = findViewById(R.id.resultactivity_textview);


        String result = getIntent().getBundleExtra("bundle").getString("result");
        textView.setText(jsonLineSplit(result));
    }

    private String jsonLineSplit(String json) {
        if( json == null || json.length() < 2 ) {
            return json;
        }
        final int len = json.length();
        final StringBuilder sb = new StringBuilder();
        char c;
        String tab = "";
        boolean beginEnd = true;
        for( int i=0 ; i<len ; i++ ) {
            c = json.charAt(i);
            switch( c ) {
                case '{': case '[':{
                    sb.append( c );
                    if( beginEnd ){
                        tab += "\t";
                        sb.append("\n");
                        sb.append( tab );
                    }
                    break;
                }
                case '}': case ']':{
                    if( beginEnd ){
                        tab = tab.substring(0, tab.length()-1);
                        sb.append("\n");
                        sb.append( tab );
                    }
                    sb.append( c );
                    break;
                }
                case '"':{
                    if( json.charAt(i-1)!='\\' )
                        beginEnd = ! beginEnd;
                    sb.append( c );
                    break;
                }
                case ',':{
                    sb.append( c );
                    if( beginEnd ){
                        sb.append("\n");
                        sb.append( tab );
                    }
                    break;
                }
                default :{
                    sb.append( c );
                }
            }
        }
        if( sb.length() > 0 )
            sb.insert(0, '\n');
        return sb.toString();
    }
}
