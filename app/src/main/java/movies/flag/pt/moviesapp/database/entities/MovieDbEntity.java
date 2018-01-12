package movies.flag.pt.moviesapp.database.entities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.orm.SugarRecord;

import java.util.List;

import movies.flag.pt.moviesapp.http.entities.Movie;

import static android.R.attr.name;

/**
 * Created by Tiago Vinagre on 13/10/2017.
 */

public class MovieDbEntity extends SugarRecord {


    private String title;
    private Double rating;


    public MovieDbEntity(Movie movies) {

        this.title = movies.getOriginalTitle();
        this.rating = movies.getVoteAverage();
    }


    public String getTitle() {
        return title;
    }


    public String setTitle() {
        return title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}


