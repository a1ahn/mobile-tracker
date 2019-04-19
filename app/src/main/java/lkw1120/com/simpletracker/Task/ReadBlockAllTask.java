package lkw1120.com.simpletracker.Task;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import lkw1120.com.simpletracker.Database.BlockDB;
import lkw1120.com.simpletracker.Database.Block;

public class ReadBlockAllTask extends AsyncTask<String, Integer, List<Block>> {

    private Context context;

    public ReadBlockAllTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Block> doInBackground(String... values) {
        BlockDB blockDB = BlockDB.getInstance(context);
        return blockDB.blockDao().blockAll();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<Block> result) {
        super.onPostExecute(result);
    }
}
