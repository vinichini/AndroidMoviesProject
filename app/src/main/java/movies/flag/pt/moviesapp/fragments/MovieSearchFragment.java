package movies.flag.pt.moviesapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.adapters.MovieAdapter;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.MovieSearchResponse;
import movies.flag.pt.moviesapp.http.entities.MoviesResponse;
import movies.flag.pt.moviesapp.http.requests.GetSearchMovieAsyncTask;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Created by tiago on 12/10/2017.
 */

public class MovieSearchFragment extends BaseFragment {
    private static final String TEXT_ARG = "textInput";
    private ListView movieSearchListView;
    private String currentSearch;
    private Button buttonGetMore;
    private List<Movie> movieList;
    private int currentPage=1;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout refresh;
    private TextView header;
    private TextView noConnection;
    private TextView page;
    private TextView movieSearchTitle;
    private final android.os.Handler handler = new android.os.Handler();

    public static MovieSearchFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(TEXT_ARG, text);
        MovieSearchFragment fragment = new MovieSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            currentSearch = args.getString(TEXT_ARG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie_search_fragment, container, false);
        findViews(v);
        addListeners();
        executeSearchMovieRequest();
        page.setText((getResources().getString(R.string.list_pages))+": "+currentPage);
        refresh();
        return v;
    }


    @SuppressLint("StaticFieldLeak")
    private void executeSearchMovieRequest() {
        // Example to request get now playing movies
        new GetSearchMovieAsyncTask(getActivity(), currentSearch, String.valueOf(currentPage), getResources().getString(R.string.language)) {

            @Override
            protected void onResponseSuccess(MovieSearchResponse movieSearchResponse) {
                DLog.d(tag, "onResponseSuccess " + movieSearchResponse);


                if (movieAdapter != null) {
                    //Se já houver uma lista preenchida, adicionar à mesma uma nova página que se pediu ao servidor
                    List<Movie> movies = movieSearchResponse.getMovieSearch();
                    //String title= movies.getOriginalTitle;
                    for (int i = 0; i < movies.size(); i++) {
                        movieList.add(movies.get(i));
                        //    MovieDbEntity moviesDb = new MovieDbEntity(title, rating)
                    }
                    movieAdapter.notifyDataSetChanged();
                }
                //Se a lista estiver vazia
                else {
                    movieList = movieSearchResponse.getMovieSearch();
                    movieAdapter = new MovieAdapter(getActivity(), movieList);
                    movieSearchListView.setAdapter(movieAdapter);
                }
                time();
                noConnection.setText("");
                try {
                    refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.postDelayed( new Runnable(){
                    @Override
                    public void run() {
                        refresh.setRefreshing( false );


                    }
                }, 2000);



            }

            @Override
            protected void onNetworkError () {
                DLog.d(tag, "onNetworkError ");

                try {
                    autoRefresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.postDelayed( new Runnable(){
                    @Override
                    public void run() {
                        refresh.setRefreshing( false );

                    }
                }, 2000);
                noConnection.setText(getResources().getString(R.string.net_error_message)+"! ");


            }

        }.execute();


    }



    private void addListeners() {
        buttonGetMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = currentPage + 1;
                page.setText((getResources().getString(R.string.list_pages))+": "+currentPage);
                executeSearchMovieRequest();
            }
        });
        //Fazer com que se possa mover a list sem accionar o "refresh" (este só é accionado no header.
        movieSearchListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (movieSearchListView == null || movieSearchListView.getChildCount() == 0) ?
                                0 : movieSearchListView.getChildAt(0).getTop();
                refresh.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });
        refresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        executeSearchMovieRequest();
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
                        executeSearchMovieRequest();
                        //restartAutoRefresh();
                    }
                });
    }
    private void autoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                movieAdapter=null;
                currentPage=1;
                page.setText((getResources().getString(R.string.list_pages))+": "+currentPage);
                executeSearchMovieRequest();
            }
        }, 10000);

    }
    public void restartAutoRefresh() {

        handler.removeCallbacks(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                movieAdapter=null;
                currentPage=1;
                page.setText((getResources().getString(R.string.list_pages))+": "+currentPage);
                executeSearchMovieRequest();
            }
        },10000);

    }

    public void time (){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDate = sdf.format(new Date());
        header.setText(getResources().getString(R.string.update_message)+": "+currentDate);
    }

    private void findViews(View v) {
        movieSearchListView = (ListView) v.findViewById(R.id.movie_search_list_view);
        buttonGetMore = (Button) v.findViewById(R.id.get_more_button_movie_search);
        refresh =  (SwipeRefreshLayout) v.findViewById(R.id.movie_search_refresh);
        header = (TextView)v.findViewById(R.id.movie_search_header);
        noConnection = (TextView)v.findViewById(R.id.movie_search_failed);
        page =(TextView)v.findViewById(R.id.movie_search_page);

    }
}
