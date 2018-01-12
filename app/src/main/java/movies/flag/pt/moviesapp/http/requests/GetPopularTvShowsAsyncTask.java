package movies.flag.pt.moviesapp.http.requests;

import android.content.Context;
import android.widget.ProgressBar;

import movies.flag.pt.moviesapp.http.entities.MoviesResponse;
import movies.flag.pt.moviesapp.http.entities.TvShowsResponse;

/**
 * Created by Ricardo Neves on 19/10/2016.
 *
 * Example for getting now playing movies
 */

public abstract class GetPopularTvShowsAsyncTask extends ExecuteRequestAsyncTask<TvShowsResponse> {

    private static final String PATH = "/tv/popular";
    private static final String LANGUAGE_KEY = "language";
    private static final String PAGE_KEY = "page";
    private String page;
    private String language;
    private ProgressBar progressBar;

    public GetPopularTvShowsAsyncTask(Context context, String page, String language) {
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
    protected Class<TvShowsResponse> getResponseEntityClass() {
        return TvShowsResponse.class;
    }
}
