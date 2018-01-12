package movies.flag.pt.moviesapp.screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.requests.DownloadImageAsyncTask;

/**
 * Created by tiago on 12/10/2017.
 */

public class MovieDetailsScreen extends Screen {
    private TextView movieTitle;
    private ImageView movieImage;
    private TextView movieDescription;
    private RatingBar movieRatingBar;
    private TextView movieRatingValue;
    private ProgressBar progressBar;
    private ImageView share;
    private String avRating;
    private Double rating;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_item);
        findViews();
        Intent intent = getIntent();
        Movie item = intent.getParcelableExtra("Movie");
        String detailsMovieTitle = item.getTitle();
        String detailsMovieImage = item.getPosterPath();
        String detailsMovieOverview = item.getOverview();
        Double detailsMovieVoteAverage = item.getVoteAverage();
        movieTitle.setText(detailsMovieTitle);
        String url = "https://image.tmdb.org/t/p/w150/" + detailsMovieImage;
        DownloadImageAsyncTask task
                = new DownloadImageAsyncTask(progressBar, movieImage);
        task.execute(url);
        movieDescription.setText(detailsMovieOverview);
        rating=item.getVoteAverage();//variável a utilizar na partilha
        RatingAvaliation(rating);//método para inserir valor para avaliar na partilha
        //barRating-transformar numa string para poder utilizar no parseFloat(String)
        //Transformar em float para poder utilizar no setRating(float)
        String avRating = Double.toString(detailsMovieVoteAverage);
        movieRatingValue.setText(avRating);
        float rating = Float.parseFloat(avRating); //put in float
        movieRatingBar.setRating(rating); //setRating(float)
    }
    public void SHARE(View view) {

                String shareTitle = movieTitle.getText().toString();
                String shareRating = movieRatingValue.getText().toString();
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "\n\n");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                            (getResources().getString(R.string.movie_share_message_item))+" "+shareTitle+" "+(getResources().getString(R.string.movie_share_message_rating))+" "+shareRating+". "
                                    + avRating);
                    startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_app)));
            }


//Adicionar uma frase a comentar o filme dependendo do rating
public void RatingAvaliation(Double movieRating){
    if (movieRating<5.0){avRating=getResources().getString(R.string.movie_share_message_bad);

    }
    if (movieRating>=5.0 && movieRating<6.5){avRating=getResources().getString(R.string.movie_share_message_medium);

    }
    if (movieRating>=6.5 && movieRating<8.0){avRating=getResources().getString(R.string.movie_share_message_good);

    }
    if (movieRating>=8.0){avRating=getResources().getString(R.string.movie_share_message_amazing);
    }

    }
    private void findViews() {
        movieTitle = (TextView) findViewById(R.id.movie_details_title);
        movieImage = (ImageView) findViewById(R.id.movie_details_image);
        movieDescription = (TextView) findViewById(R.id.movie_details_description);
        movieRatingBar = (RatingBar) findViewById(R.id.movie_details_ratingBar);
        movieRatingValue = (TextView) findViewById(R.id.movie_details_rating_value);
        progressBar = (ProgressBar) findViewById(R.id.movie_details_loader);
        share = (ImageView) findViewById(R.id.movie_details_button_share);
    }
}




