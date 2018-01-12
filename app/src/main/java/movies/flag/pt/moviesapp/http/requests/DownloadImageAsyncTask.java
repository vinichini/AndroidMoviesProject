package movies.flag.pt.moviesapp.http.requests;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import movies.flag.pt.moviesapp.helpers.DownloadsHelper;

/**
 * Created by tiago on 19/10/2017.
 */

public class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = DownloadImageAsyncTask.class.getSimpleName();

    private ProgressBar progressBar;
    private ImageView imageView;

    public DownloadImageAsyncTask(ProgressBar progressBar,
                                  ImageView imageView){
        this.imageView = imageView;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute() thread id = " + Thread.currentThread().getId());
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Log.d(TAG, "doInBackground() thread id = " + Thread.currentThread().getId());

        if(urls == null || urls.length == 0){
            return null;
        }

        String url = urls[0];

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = DownloadsHelper.downloadImage(url);

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        progressBar.setVisibility(View.GONE);
        imageView.setImageBitmap(bitmap);
    }


}
