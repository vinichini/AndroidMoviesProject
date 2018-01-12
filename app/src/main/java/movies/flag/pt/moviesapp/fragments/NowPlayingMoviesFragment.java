package movies.flag.pt.moviesapp.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.Views.MoviesApp;
import movies.flag.pt.moviesapp.adapters.MovieAdapter;
import movies.flag.pt.moviesapp.adapters.MovieDbAdapter;
import movies.flag.pt.moviesapp.database.entities.MovieDbEntity;
import movies.flag.pt.moviesapp.helpers.NetworkHelper;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.MoviesResponse;
//import movies.flag.pt.moviesapp.http.receivers.NetworkChangeBroadcastReceiver;
import movies.flag.pt.moviesapp.http.receivers.NetworkChangeBroadcastReceiver;
import movies.flag.pt.moviesapp.http.requests.GetNowPlayingMoviesAsyncTask;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Created by tiago on 12/10/2017.
 */

public class NowPlayingMoviesFragment extends BaseFragment{

    private ListView nowMoviesListView;
    private Button buttonGetMore;
    private List<Movie> movieList;
    private MovieDbEntity movieDb;
    private List<MovieDbEntity> movieDbList;
    private int currentPage=1;
    private MovieAdapter movieAdapter;
    private MovieDbAdapter movieDbAdapter;
    private SwipeRefreshLayout refresh;
    private TextView header;
    private TextView noConnection;
    private TextView page;
    private final android.os.Handler handler = new android.os.Handler();
    private final android.os.Handler imageRefresh = new android.os.Handler();
    private NetworkChangeBroadcastReceiver networkChangeBroadcastReceiver;
    private ResumeBroadcastReceiver resumeBroadcastReceiver;



    public static NowPlayingMoviesFragment newInstance() {
        Bundle args = new Bundle();
        NowPlayingMoviesFragment fragment = new NowPlayingMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.now_playing_movies_fragment, container, false);
        findViews(v);
        executeNowPlayingMoviesRequest();
        addListeners();
        refresh();



        /*final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        networkChangeBroadcastReceiver = new NetworkChangeBroadcastReceiver();
        getActivity().registerReceiver(networkChangeBroadcastReceiver, intentFilter);
        resumeBroadcastReceiver = new resumeBroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                noConnection.setText("received!!");
                checkNetwork();
            }
        };

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(resumeBroadcastReceiver,
                new IntentFilter("MoviesApp.ConnectivityGained"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(resumeBroadcastReceiver,
                new IntentFilter("MoviesApp.ConnectivityGained"));*/



        return v;
    }

    @SuppressLint("StaticFieldLeak")
    private void executeNowPlayingMoviesRequest() {

        new GetNowPlayingMoviesAsyncTask(getActivity(), String.valueOf(currentPage), getResources().getString(R.string.language)) {


            @Override
            protected void onResponseSuccess(MoviesResponse moviesResponse) {
                DLog.d(tag, "onResponseSuccess " + moviesResponse);
                time();
                imageRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);

                    }
                }, 1500);
                noConnection.setText("");
                //checkNetwork();


                if (movieAdapter != null) {
                    //Se já houver uma lista preenchida, adicionar à mesma uma nova página que se pediu ao servidor
                    List<Movie> movies = moviesResponse.getMovies();

                    for (int i = 0; i < movies.size(); i++) {
                        movieList.add(movies.get(i));
                        MovieDbEntity moviesDb = new MovieDbEntity(movieList.get(i));
                        moviesDb.save();
                    }
                    movieAdapter.notifyDataSetChanged();

                }
                //Se a lista estiver vazia
                else {
                    movieList = moviesResponse.getMovies();
                    movieAdapter = new MovieAdapter(getActivity(), movieList);
                    nowMoviesListView.setAdapter(movieAdapter);
                    for (int i = 0; i < movieList.size(); i++) {
                        MovieDbEntity moviesDb = new MovieDbEntity(movieList.get(i));
                        moviesDb.save();
                    }
                }
            }

            @Override
            protected void onNetworkError () {
                DLog.d(tag, "onNetworkError ");
                refresh.setRefreshing( false );
                checkNetwork();
                noConnection.setText(getResources().getString(R.string.net_error_message)+"! ");
                List<MovieDbEntity> movieDbEntityList = SugarRecord.listAll(MovieDbEntity.class);
                movieDbAdapter=new MovieDbAdapter(getActivity(), movieDbEntityList);
                nowMoviesListView.setAdapter(movieDbAdapter);
            }

        }.execute();


    }


    private void addListeners() {

        buttonGetMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = currentPage + 1;
                page.setText((getResources().getString(R.string.list_pages))+": "+currentPage);
                try {
                    executeNowPlayingMoviesRequest();
                } catch (Exception e) {
                    noConnection.setText(getResources().getString(R.string.catch_error_message));
                }
            }
        });


        //Fazer com que se possa mover a list sem accionar o "refresh" (este só é accionado no header.
        nowMoviesListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (nowMoviesListView == null || nowMoviesListView.getChildCount() == 0) ?
                                0 : nowMoviesListView.getChildAt(0).getTop();
                refresh.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });
    }

    private void refresh() {
        refresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        movieAdapter = null;
                        currentPage = 1;
                        page.setText((getResources().getString(R.string.list_pages))+": "+currentPage);
                        try {
                            executeNowPlayingMoviesRequest();
                        } catch (Exception e) {
                            noConnection.setText(getResources().getString(R.string.catch_error_message));
                        }

                    }
                });
    }
    public void autoRefresh() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                movieAdapter=null;
                currentPage=1;
                page.setText((getResources().getString(R.string.list_pages))+": "+currentPage);
                try {
                    executeNowPlayingMoviesRequest();
                } catch (Exception e) {
                    noConnection.setText(getResources().getString(R.string.catch_error_message));
                }


            }
        }, 10000);


    }


    public void checkNetwork() {
        if (NetworkHelper.networkIsAvailable(getActivity())) {
            try {
                autoRefresh();
            } catch (Exception e) {
                noConnection.setText(getResources().getString(R.string.catch_error_message));
            }
        } else {
            noConnection.setText(getResources().getString(R.string.net_error_message));
        }
    }



    public void time (){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDate = sdf.format(new Date());
        header.setText(getResources().getString(R.string.update_message)+": "+currentDate);
    }
    /* private void registerReceivers() {
         newResumeBroadcastReceiver = new NewResumeBroadcastReceiver();
         IntentFilter intentFilter = new IntentFilter(BroadcastActions.NEW_IMAGES_DOWNLOADED_ACTION);
         LocalBroadcastManager.getInstance(this).registerReceiver(newImagesBroadcastReceiver, intentFilter);

         networkChangeBroadcastReceiver = new NetworkChangeBroadcastReceiver();
         registerReceiver(networkChangeBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
     }*/
    private class ResumeBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            checkNetwork();
        }
    }

    private void findViews(View v) {
        nowMoviesListView = (ListView) v.findViewById(R.id.now_playing_movies_list_view);
        buttonGetMore = (Button) v.findViewById(R.id.get_more_button_now_playing_movies);
        refresh =  (SwipeRefreshLayout) v.findViewById(R.id.now_playing_movies_refresh);
        header = (TextView)v.findViewById(R.id.now_playing_movies_header);
        noConnection = (TextView)v.findViewById(R.id.now_playing_movies_failed);
        page =(TextView)v.findViewById(R.id.now_playing_movies_page);
    }
}
