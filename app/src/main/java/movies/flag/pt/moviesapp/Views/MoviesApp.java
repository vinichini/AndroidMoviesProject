package movies.flag.pt.moviesapp.Views;

import android.app.Application;
import android.util.Log;

import com.orm.SugarContext;

import movies.flag.pt.moviesapp.helpers.SharedPreferencesHelper;
import movies.flag.pt.moviesapp.screens.Screen;

/**
 * Created by Tiago Vinagre on 28/10/2017.
 */

public class MoviesApp extends Application {

    private static final String TAG = MoviesApp.class.getSimpleName();

    private static MoviesApp instance;

    public static MoviesApp getInstance(){
        return instance;
    }

    private int numberOfResumedScreens;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        instance = this;
        SharedPreferencesHelper.init(this);
        SugarContext.init(this);
    }

    public void addOnResumeScreen(){
        numberOfResumedScreens++;
    }

    public void removeOnResumeScreen(){
        numberOfResumedScreens--;
    }

    public boolean applicationIsInBackground(){
        return numberOfResumedScreens == 0;
    }
    // Gloabl declaration of variable to use in whole app

    public static boolean activityVisible; // Variable that will check the
    // current activity state

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }

    public static void activityResumed() {
        activityVisible = true;// this will set true when activity resumed

    }

    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused

    }


}
