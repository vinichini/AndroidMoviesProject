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
import movies.flag.pt.moviesapp.database.entities.TvShowDbEntity;
import movies.flag.pt.moviesapp.screens.MovieDetailsScreen;

/**
 * Created by Tiago Vinagre on 31/10/2017.
 */

public class TvShowDbAdapter extends ArrayAdapter<TvShowDbEntity> {

    public TvShowDbAdapter(@NonNull Context context, @NonNull List<TvShowDbEntity> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v;
        TvShowDbAdapter.ViewHolder holder;



        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.tv_show_item, parent, false);
            holder = new TvShowDbAdapter.ViewHolder(v);
            v.setTag(holder);
        } else {
            v = convertView;
            holder = (TvShowDbAdapter.ViewHolder) v.getTag();
        }
        final TvShowDbEntity item = getItem(position);
        holder.tvShowName.setText(String.valueOf(item.getName()));
        String avRating = Double.toString(item.getRating());
        holder.tvShowRating.setText(avRating);

        //Clicar num item da lista para ver o detalhe

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieDetailsScreen.class);
                intent.putExtra("TvShow", (Parcelable) item);
                getContext().startActivity(intent);
            }
        });


        return v;
    }

    static class ViewHolder {
        private TextView tvShowName;
        private TextView tvShowRating;


        public ViewHolder(View v) {
            tvShowName = (TextView) v.findViewById(R.id.tv_show_item_name);
            tvShowRating = (TextView) v.findViewById(R.id.tv_show_item_rating_value);

        }
    }

}
