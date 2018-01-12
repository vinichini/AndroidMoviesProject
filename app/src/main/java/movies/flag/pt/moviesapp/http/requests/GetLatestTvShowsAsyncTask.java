package movies.flag.pt.moviesapp.http.requests;

import android.content.Context;

import movies.flag.pt.moviesapp.http.entities.MoviesResponse;
import movies.flag.pt.moviesapp.http.entities.TvShow;
import movies.flag.pt.moviesapp.http.entities.TvShowsResponse;

/**
 * Created by Ricardo Neves on 19/10/2016.
 *
 * Example for getting now playing movies
 */

public abstract class GetLatestTvShowsAsyncTask extends ExecuteRequestAsyncTask<TvShowsResponse> {

    private static final String PATH = "/tv/latest";
    private static final String LANGUAGE_KEY = "language";
    private String language;

    public GetLatestTvShowsAsyncTask(Context context, String language) {
        super(context);
        this.language=language;
    }

    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected void addQueryParams(StringBuilder sb) {
        addQueryParam(sb, LANGUAGE_KEY, language);
    }

    @Override
    protected Class<TvShowsResponse> getResponseEntityClass() {
        return TvShowsResponse.class;
    }
}
