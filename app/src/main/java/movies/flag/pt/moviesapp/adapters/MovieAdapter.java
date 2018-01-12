package movies.flag.pt.moviesapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import movies.flag.pt.moviesapp.R;

import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.requests.DownloadImageAsyncTask;
import movies.flag.pt.moviesapp.screens.MovieDetailsScreen;

import static android.R.id.input;

/**
 * Created by tiago on 13/10/2017.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(@NonNull Context context, @NonNull List<Movie> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v;
        final ViewHolder holder;
        final Movie item = getItem(position);


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.movie_item, parent, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            v = convertView;
            holder = (ViewHolder) v.getTag();
        }

        holder.movieTitle.setText(item.getTitle());
        String avRating = Double.toString(item.getVoteAverage());
        holder.movieRating.setText(avRating);

        //Clicar num item da lista para ver o detalhe

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieDetailsScreen.class);
                intent.putExtra("Movie", item);
                getContext().startActivity(intent);
            }
        });


        return v;
    }

    static class ViewHolder {
        private TextView movieTitle;
        private TextView movieRating;


        public ViewHolder(View v) {
            movieTitle = (TextView) v.findViewById(R.id.movie_item_title);
            movieRating = (TextView) v.findViewById(R.id.movie_item_rating_value);

        }
    }

}
