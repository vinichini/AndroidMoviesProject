package movies.flag.pt.moviesapp.http.requests;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.http.entities.MoviesResponse;

import static android.R.attr.label;

/**
 * Created by Ricardo Neves on 19/10/2016.
 *
 * Example for getting now playing movies
 */

public abstract class GetNowPlayingMoviesAsyncTask extends ExecuteRequestAsyncTask<MoviesResponse> {

    private static final String PATH = "/movie/now_playing";
    private static final String LANGUAGE_KEY = "language";
    private static final String PAGE_KEY = "page";
    private String page;
    private String language;
    private ProgressBar progressBar;

    public GetNowPlayingMoviesAsyncTask(Context context, String page, String language) {
        super(context);
        this.page=page;
        this.language=language;
    }

    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected void addQueryParams(StringBuilder sb) {
        addQueryParam(sb, LANGUAGE_KEY, language);
        addQueryParam(sb, PAGE_KEY, page);
    }

    @Override
    protected Class<MoviesResponse> getResponseEntityClass() {
        return MoviesResponse.class;
    }

}
