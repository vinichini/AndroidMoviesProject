package movies.flag.pt.moviesapp.screens;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.Locale;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.fragments.BaseFragment;
import movies.flag.pt.moviesapp.fragments.HomeFragment;
import movies.flag.pt.moviesapp.fragments.NowPlayingMoviesFragment;
import movies.flag.pt.moviesapp.fragments.MovieSearchFragment;
import movies.flag.pt.moviesapp.fragments.PopularTvShowsFragment;

/**
 * Created by tiago on 12/10/2017.
 */

public class MainScreen extends Screen {
    private static final int FRAGMENT_CONTAINER_ID = R.id.main_screen_container;
    private DrawerLayout drawerLayout;
    private TextView menuHome;
    private TextView menuMovies;
    private TextView menuTvShows;
    private TextView mainsScreenTitle;
    private EditText inputMovieSearch;
    private Button buttonMovieSearch;
    private View getMenu;
    private ProgressBar progressBar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        findViews();
        initialFragment();
        addListeners();



    }


    private void initialFragment() {
        addFragment(HomeFragment.newInstance());
    }

    private void addListeners() {
        menuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainsScreenTitle.setText(getResources().getString(R.string.home));
                addFragment(HomeFragment.newInstance());
                closeMenu();
            }
        });

        menuMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainsScreenTitle.setText(getResources().getString(R.string.movies));
                addFragment(NowPlayingMoviesFragment.newInstance());
                closeMenu();
            }
        });

        menuTvShows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainsScreenTitle.setText(getResources().getString(R.string.tv_shows));
                addFragment(PopularTvShowsFragment.newInstance());
                closeMenu();
            }
        });

        buttonMovieSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputMovieSearch.getText().toString();
                mainsScreenTitle.setText(getResources().getString(R.string.search_input_results)+" "+ '"'+text+'"');
                addFragment(MovieSearchFragment.newInstance(text));
                closeMenu();
                hideKeyboard();
            }
        });

        getMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu();
            }
        });

    }


    private void closeMenu() {
        drawerLayout.closeDrawer(Gravity.START);
    }

    private void openMenu() {
        drawerLayout.openDrawer(Gravity.START);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputMovieSearch.getWindowToken(), 0);
    }

    private void addFragment(BaseFragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(FRAGMENT_CONTAINER_ID, fragment);
        ft.commit();
    }

    private void findViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.main_screen_drawer);
        menuHome = (TextView) findViewById(R.id.main_screen_menu_home);
        menuMovies = (TextView) findViewById(R.id.main_screen_menu_movies);
        menuTvShows = (TextView) findViewById(R.id.main_screen_menu_tv_shows);
        inputMovieSearch = (EditText) findViewById(R.id.main_screen_input_search);
        buttonMovieSearch = (Button) findViewById(R.id.main_screen_button_search);
        mainsScreenTitle = (TextView) findViewById(R.id.main_screen_title);
        getMenu =  findViewById(R.id.main_screen_menu_icon);
        progressBar = (ProgressBar) findViewById(R.id.main_screen_loader);


    }
}


