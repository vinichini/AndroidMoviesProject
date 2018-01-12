package movies.flag.pt.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.TvShow;
import movies.flag.pt.moviesapp.screens.MovieDetailsScreen;
import movies.flag.pt.moviesapp.screens.TvShowDetailsScreen;

/**
 * Created by tiago on 15/10/2017.
 */

public class TvShowAdapter extends ArrayAdapter<TvShow> {

    public TvShowAdapter(@NonNull Context context, @NonNull List<TvShow> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v;
        ViewHolder holder;
        final TvShow item = getItem(position);


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.tv_show_item, parent, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        }
        else{
            v = convertView;
            holder = (ViewHolder) v.getTag();
        }

        holder.tvShowName.setText(item.getName());
        String avRating = Double.toString(item.getVoteAverage());
        holder.tvShowRating.setText(avRating);

//Clicar num item da lista para ver o detalhe

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TvShowDetailsScreen.class);
                intent.putExtra("TvShow", item);
                getContext().startActivity(intent);
            }
        });

        return v;
    }

    static class ViewHolder{
        private TextView tvShowName;
        private TextView tvShowRating;


        private ViewHolder(View v){
            tvShowName = (TextView) v.findViewById(R.id.tv_show_item_name);
            tvShowRating = (TextView) v.findViewById(R.id.tv_show_item_rating_value);

        }
    }
}
