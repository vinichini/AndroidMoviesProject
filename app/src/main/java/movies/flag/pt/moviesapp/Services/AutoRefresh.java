package movies.flag.pt.moviesapp.Services;

import android.app.IntentService;
import android.content.Intent;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

import movies.flag.pt.moviesapp.fragments.NowPlayingMoviesFragment;

/**
 * Created by Tiago Vinagre on 01/11/2017.
 */

public class AutoRefresh extends IntentService {

private static final String TAG = AutoRefresh.class.getSimpleName();

    public AutoRefresh() {
        super("AutoRefreshThread");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent newIntent = new Intent(this, NowPlayingMoviesFragment.class);
        startService(newIntent);
    }}