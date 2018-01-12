package movies.flag.pt.moviesapp.http.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import movies.flag.pt.moviesapp.Services.AutoRefresh;
import movies.flag.pt.moviesapp.Views.MoviesApp;
import movies.flag.pt.moviesapp.fragments.BaseFragment;
import movies.flag.pt.moviesapp.fragments.NowPlayingMoviesFragment;
import movies.flag.pt.moviesapp.helpers.NetworkHelper;

/**
 * Created by tiago on 16/10/2017.
 */

public class NetworkChangeBroadcastReceiver extends BroadcastReceiver {

    public NetworkChangeBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            boolean isVisible = MoviesApp.isActivityVisible();// Check if
            // activity
            // is
            // visible
            // or not
            Log.i("Activity is Visible ", "Is activity visible : " + isVisible);

            // If it is visible then trigger the task else do nothing
            if (isVisible == true) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager
                        .getActiveNetworkInfo();

                // Check internet connection and accrding to state change the
                // text of activity by calling method
                if (networkInfo != null && networkInfo.isConnected()) {

                    Intent serviceIntent = new Intent(context, BaseFragment.class);
                    context.startService(serviceIntent);
                } /*else {
                    new NowPlayingMoviesFragment();
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*@Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                Log.d("Network", "Internet YAY");
            } else if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
                Log.d("Network", "No internet :(");
            }
        }
    }*/
/*
   private static final String TAG = NetworkChangeBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(NetworkHelper.networkIsAvailable(context)){
            Log.d(TAG, "wifi is available");
            Log.d(TAG, "Start AutoRefresh");
            Intent serviceIntent = new Intent(context, NowPlayingMoviesFragment.class);
            context.startService(serviceIntent);
        }
        else{
            Log.d(TAG, "wifi is not available");
        }
    }*/

}
