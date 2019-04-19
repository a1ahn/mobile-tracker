package lkw1120.com.simpletracker.Task;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import lkw1120.com.simpletracker.Database.BlockDB;
import lkw1120.com.simpletracker.Database.Block;

public class InsertBlockTask extends AsyncTask<Block, Integer, Boolean> {

    private Context context;

    public InsertBlockTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Boolean doInBackground(Block... block) {
        BlockDB blockDB = BlockDB.getInstance(context);
        if(null == blockDB.blockDao().blockByHash(block[0].getBlockHash())) {
            blockDB.blockDao().insertBlock(block[0]);
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
    }
}