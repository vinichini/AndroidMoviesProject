package movies.flag.pt.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.database.entities.MovieDbEntity;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.screens.MovieDetailsScreen;

/**
 * Created by Tiago Vinagre on 31/10/2017.
 */

public class MovieDbAdapter extends ArrayAdapter<MovieDbEntity> {

    public MovieDbAdapter(@NonNull Context context, @NonNull List<MovieDbEntity> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v;
        MovieDbAdapter.ViewHolder holder;



        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.movie_item, parent, false);
            holder = new MovieDbAdapter.ViewHolder(v);
            v.setTag(holder);
        } else {
            v = convertView;
            holder = (MovieDbAdapter.ViewHolder) v.getTag();
        }
        final MovieDbEntity item = getItem(position);
        holder.movieTitle.setText(String.valueOf(item.getTitle()));
        String avRating = Double.toString(item.getRating());
        holder.movieRating.setText(avRating);

        //Clicar num item da lista para ver o detalhe

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieDetailsScreen.class);
                intent.putExtra("Movie", (Parcelable) item);
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
