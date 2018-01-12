package movies.flag.pt.moviesapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.TvShow;
import movies.flag.pt.moviesapp.http.requests.DownloadImageAsyncTask;

/**
 * Created by tiago on 12/10/2017.
 */

public class TvShowDetailsScreen extends Screen {
    private TextView tvShowName;
    private ImageView tvShowImage;
    private TextView tvShowDescription;
    private RatingBar tvShowRatingBar;
    private TextView tvShowRatingValue;
    private ProgressBar progressBar;
    private ImageView share;
    private String avRating;
    private Double rating;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_show_details_item);
        findViews();
        Intent intent = getIntent();
        TvShow item=intent.getParcelableExtra("TvShow");
        String detailsTvShowName = item.getName();
        String detailsTvShowImage = item.getPosterPath();
        String detailsTvShowOverview = item.getOverview();
        Double detailsTvShowVoteAverage= item.getVoteAverage();

        tvShowName.setText(detailsTvShowName);
        String url = "https://image.tmdb.org/t/p/w150/"+detailsTvShowImage;
        DownloadImageAsyncTask task
                = new DownloadImageAsyncTask(progressBar, tvShowImage);
        task.execute(url);
        tvShowDescription.setText(detailsTvShowOverview);
        rating=item.getVoteAverage();//variável a utilizar na partilha
        RatingAvaliation(rating);//método para inserir valor para avaliar na partilha
        //barRating-transformar numa string para poder utilizar no parseFloat(String)
        //Transformar em float para poder utilizar no setRating(float)
        String avRating = Double.toString(detailsTvShowVoteAverage);
        tvShowRatingValue.setText(avRating);
        float rating = Float.parseFloat(avRating); //put in float
        tvShowRatingBar.setRating(rating); //setRating(float)

    }

    public void SHARE(View view) {

        String shareTitle = tvShowName.getText().toString();
        String shareRating = tvShowRatingValue.getText().toString();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "\n\n");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                (getResources().getString(R.string.tv_show_share_message_item))+" "+shareTitle+" "+(getResources().getString(R.string.tv_show_share_message_rating))+" "+shareRating+". "
                        + avRating);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_app)));
    }


    //Adicionar uma frase a comentar o filme dependendo do rating
    public void RatingAvaliation(Double movieRating){
        if (movieRating<5.0){avRating=getResources().getString(R.string.tv_show_share_message_bad);

        }
        if (movieRating>=5.0 && movieRating<6.5){avRating=getResources().getString(R.string.tv_show_share_message_medium);

        }
        if (movieRating>=6.5 && movieRating<8.0){avRating=getResources().getString(R.string.tv_show_share_message_good);

        }
        if (movieRating>=8.0){avRating=getResources().getString(R.string.tv_show_share_message_amazing);
        }

    }

    private void findViews() {
        tvShowName=(TextView) findViewById(R.id.tv_show_details_title);
        tvShowImage = (ImageView) findViewById(R.id.tv_show_details_image);
        tvShowDescription=  (TextView) findViewById(R.id.tv_show_details_description);
        tvShowRatingBar = (RatingBar) findViewById(R.id.tv_show_details_ratingBar);
        tvShowRatingValue = (TextView) findViewById(R.id.tv_show_details_rating_value);
        progressBar = (ProgressBar) findViewById(R.id.tv_show_details_loader);
        share = (ImageView) findViewById(R.id.tv_show_details_button_share);
    }

}


