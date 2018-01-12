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

import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.adapters.MovieAdapter;
import movies.flag.pt.moviesapp.adapters.MovieDbAdapter;
import movies.flag.pt.moviesapp.adapters.TvShowAdapter;
import movies.flag.pt.moviesapp.adapters.TvShowDbAdapter;
import movies.flag.pt.moviesapp.database.entities.MovieDbEntity;
import movies.flag.pt.moviesapp.database.entities.TvShowDbEntity;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.TvShow;
import movies.flag.pt.moviesapp.http.entities.TvShowsResponse;
import movies.flag.pt.moviesapp.http.requests.GetPopularTvShowsAsyncTask;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Created by tiago on 13/10/2017.
 */

public class PopularTvShowsFragment extends BaseFragment{

    private ListView popularTvShowsListView;
    private List<TvShow> tvShowList;
    private TvShowAdapter tvShowAdapter;
    private TvShowDbAdapter tvShowDbAdapter;
    private Button buttonGetMore;
    private int currentPage=1;
    private SwipeRefreshLayout refresh;
    private TextView header;
    private TextView noConnection;
    private TextView page;
    private final android.os.Handler handler = new android.os.Handler();

    public static PopularTvShowsFragment newInstance() {
        Bundle args = new Bundle();
        PopularTvShowsFragment fragment = new PopularTvShowsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popular_tv_shows_fragment, container, false);
        findViews(v);
        addListeners();
        refresh();
        executePopularTvShowsRequest();
        page.setText((getResources().getString(R.string.list_pages))+": "+currentPage);

        return v;
    }

    @SuppressLint("StaticFieldLeak")
    private void executePopularTvShowsRequest() {

        new GetPopularTvShowsAsyncTask(getActivity(), String.valueOf(currentPage), getResources().getString(R.string.language)){

            @Override
            protected void onResponseSuccess(TvShowsResponse tvShowsResponse) {
                DLog.d(tag, "onResponseSuccess " + tvShowsResponse);

                if (tvShowAdapter != null) {
                    //Se já houver uma lista preenchida, adicionar à mesma uma nova página que se pediu ao servidor
                    List<TvShow> tvshows = tvShowsResponse.getTvShows();
                    //String title= movies.getOriginalTitle;
                    for (int i = 0; i < tvshows.size(); i++) {
                        tvShowList.add(tvshows.get(i));
                        TvShowDbEntity tvShowDb = new TvShowDbEntity(tvshows.get(i));
                        tvShowDb.save();
                    }
                    tvShowAdapter.notifyDataSetChanged();
                }
                //Se a lista estiver vazia
                else {
                    tvShowList = tvShowsResponse.getTvShows();
                    tvShowAdapter = new TvShowAdapter(getActivity(), tvShowList);
                    popularTvShowsListView.setAdapter(tvShowAdapter);
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
                refresh.setRefreshing( false );
                noConnection.setText(getResources().getString(R.string.net_error_message)+"! ");
                List<TvShowDbEntity> tvShowDbEntityList = SugarRecord.listAll(TvShowDbEntity.class);
                tvShowDbAdapter=new TvShowDbAdapter(getActivity(), tvShowDbEntityList);
                popularTvShowsListView.setAdapter(tvShowDbAdapter);
                //MovieDbEntity offlineMovies = new MovieDbEntity(getActivity(),Movie.getOriginalTitle());
                // MovieDbEntity offlineMovies = offlineMovies.findById(offLineMovies.class,1)

            }

        }.execute();


    }



    private void addListeners() {
        buttonGetMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage=currentPage+1;
                page.setText((getResources().getString(R.string.list_pages))+": "+currentPage);
                executePopularTvShowsRequest();
            }
        });
        //Fazer com que se possa mover a list sem accionar o "refresh" (este só é accionado no header.
        popularTvShowsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (popularTvShowsListView == null || popularTvShowsListView.getChildCount() == 0) ?
                                0 : popularTvShowsListView.getChildAt(0).getTop();
                refresh.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });

    }
    private void refresh() {
        refresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        tvShowAdapter = null;
                        currentPage = 1;
                        page.setText((getResources().getString(R.string.list_pages))+": "+currentPage);
                        executePopularTvShowsRequest();
                        //restartAutoRefresh();
                    }
                });
    }
    private void autoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                tvShowAdapter=null;
                currentPage=1;
                page.setText((getResources().getString(R.string.list_pages))+": "+currentPage);
                executePopularTvShowsRequest();
            }
        }, 10000);

    }
    public void restartAutoRefresh() {

        handler.removeCallbacks(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                tvShowAdapter=null;
                currentPage=1;
                page.setText((getResources().getString(R.string.list_pages))+": "+currentPage);
                executePopularTvShowsRequest();
            }
        },10000);
   /* handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            movieAdapter=null;
            currentPage=1;
            executeNowPlayingMoviesRequest();
            refresh.setRefreshing(true);
        }
        },10000);*/
    }

    public void time (){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDate = sdf.format(new Date());
        header.setText(getResources().getString(R.string.update_message)+": "+currentDate);
    }
    private void findViews(View v) {
        popularTvShowsListView = (ListView) v.findViewById(R.id.popular_tv_shows_list_view);
        buttonGetMore = (Button) v.findViewById(R.id.get_more_button_popular_tv_shows);
        refresh =  (SwipeRefreshLayout) v.findViewById(R.id.popular_tv_shows_refresh);
        header = (TextView)v.findViewById(R.id.popular_tv_shows_header);
        noConnection = (TextView)v.findViewById(R.id.popular_tv_shows_failed);
        page =(TextView)v.findViewById(R.id.popular_tv_shows_page);
    }


}
