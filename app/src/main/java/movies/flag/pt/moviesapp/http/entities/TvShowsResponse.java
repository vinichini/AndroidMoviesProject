package movies.flag.pt.moviesapp.http.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiago on 14/10/2017.
 */

public class TvShowsResponse {
    @SerializedName("page")
    private Integer page;

    @SerializedName("results")
    private List<TvShow> tvShows = new ArrayList<>();

    @SerializedName("total_pages")
    private Integer totalPages;

    public Integer getPage() {
        return page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<TvShow> getTvShows() {
        return tvShows;
    }
}
