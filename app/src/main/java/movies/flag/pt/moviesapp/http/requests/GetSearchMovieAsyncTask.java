package movies.flag.pt.moviesapp.http.requests;

import android.content.Context;
import android.widget.ProgressBar;

import movies.flag.pt.moviesapp.http.entities.MovieSearchResponse;
import movies.flag.pt.moviesapp.http.entities.TvShowsResponse;

/**
 * Created by Ricardo Neves on 19/10/2016.
 *
 * Example for getting now playing movies
 */

public abstract class GetSearchMovieAsyncTask extends ExecuteRequestAsyncTask<MovieSearchResponse> {

    private static final String PATH = "/search/movie";
    private static final String LANGUAGE_KEY = "language";
    private static final String PAGE_KEY = "page";
    private static final String SEARCH_KEY = "query";
    private String search;
    private String page;
    private String language;
    private ProgressBar progressBar;

    public GetSearchMovieAsyncTask(Context context, String search,  String page, String language) {
        super(context);
        this.progressBar = progressBar;
        this.page=page;
        this.language=language;
        this.search=search;
    }

    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected void addQueryParams(StringBuilder sb) {
        addQueryParam(sb, LANGUAGE_KEY, language);
        addQueryParam(sb, PAGE_KEY, page);
        addQueryParam(sb, SEARCH_KEY, search);
    }

    @Override
    protected Class<MovieSearchResponse> getResponseEntityClass() {
        return MovieSearchResponse.class;
    }
}
