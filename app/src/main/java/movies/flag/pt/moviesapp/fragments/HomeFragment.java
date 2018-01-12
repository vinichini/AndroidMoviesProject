package movies.flag.pt.moviesapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.adapters.MovieAdapter;
import movies.flag.pt.moviesapp.adapters.TvShowAdapter;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.MoviesResponse;
import movies.flag.pt.moviesapp.http.entities.TvShow;
import movies.flag.pt.moviesapp.http.entities.TvShowsResponse;
import movies.flag.pt.moviesapp.http.requests.GetLatestMoviesAsyncTask;
import movies.flag.pt.moviesapp.http.requests.GetLatestTvShowsAsyncTask;
import movies.flag.pt.moviesapp.http.requests.GetNowPlayingMoviesAsyncTask;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Created by tiago on 13/10/2017.
 */

public class HomeFragment extends BaseFragment{

    private ListView listLatestMovies;
    private ListView listLatestTvShows;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;
    private List<TvShow> tvShowList;
    private TvShowAdapter tvShowAdapter;


    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        findViews(v);
        executeLatestMoviesRequest();
        executeLatestTvShowsRequest();

        return v;
    }

    @SuppressLint("StaticFieldLeak")
    private void executeLatestMoviesRequest() {
        // Example to request get now playing movies
        new GetLatestMoviesAsyncTask(getActivity(),getResources().getString(R.string.language)){

            @Override
            protected void onResponseSuccess(MoviesResponse moviesResponse) {
                DLog.d(tag, "onResponseSuccess " + moviesResponse);
                // Here i can create the adapter :)
                    movieList = moviesResponse.getMovies();
                    movieAdapter = new MovieAdapter(getActivity(), movieList);
                    listLatestMovies.setAdapter(movieAdapter);
            }

            @Override
            protected void onNetworkError() {
                DLog.d(tag, "onNetworkError ");
                // Here i now that some error occur when processing the request,
                // possible my internet connection if turned off

            }
        }.execute();
    }
    @SuppressLint("StaticFieldLeak")
    private void executeLatestTvShowsRequest() {

        new GetLatestTvShowsAsyncTask(getActivity(),getResources().getString(R.string.language)){

            @Override
            protected void onResponseSuccess(TvShowsResponse tvShowsResponse) {
                DLog.d(tag, "onResponseSuccess " + tvShowsResponse);

                    tvShowList = tvShowsResponse.getTvShows();
                    tvShowAdapter = new TvShowAdapter(getActivity(), tvShowList);
                    listLatestTvShows.setAdapter(tvShowAdapter);
            }

            @Override
            protected void onNetworkError() {
                DLog.d(tag, "onNetworkError ");


            }
        }.execute();
    }

    private void findViews(View v) {
        listLatestMovies = (ListView) v.findViewById(R.id.latest_movies_list_view);
        listLatestTvShows = (ListView) v.findViewById(R.id.latest_tv_shows_list_view);
    }



}
