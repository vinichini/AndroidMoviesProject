package movies.flag.pt.moviesapp.database.entities;

import com.orm.SugarRecord;

import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.TvShow;

/**
 * Created by tiago on 13/10/2017.
 */

public class TvShowDbEntity extends SugarRecord{
    private String name;
    private Double rating;


    public TvShowDbEntity(TvShow tvShows) {

        this.name = tvShows.getOriginalName();
        this.rating = tvShows.getVoteAverage();
    }


    public String getName() {
        return name;
    }


    public String setName() {
        return name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
