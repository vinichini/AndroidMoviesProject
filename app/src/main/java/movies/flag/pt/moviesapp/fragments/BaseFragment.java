package movies.flag.pt.moviesapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.Views.MoviesApp;
import movies.flag.pt.moviesapp.screens.IntroScreen;
import movies.flag.pt.moviesapp.screens.MainScreen;

/**
 * Created by tiago on 13/10/2017.
 */

public class BaseFragment extends Fragment{

    protected final String tag = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(tag, "onCreate");

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(tag, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(tag, "onResume");
        MoviesApp.activityResumed();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(tag, "onPause");
        MoviesApp.activityPaused();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(tag, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(tag, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(tag, "onDestroy");
    }
}
