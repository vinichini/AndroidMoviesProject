package movies.flag.pt.moviesapp.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.orm.SugarRecord;

import java.util.List;

import movies.flag.pt.moviesapp.R;


/**
 * Created by tiago on 20/10/2017.
 */

public class OfflineScreen extends Screen {

  /*  private Button buttonGetMore;
    private ListView offlineMovieList;

    private List<OfflineMovieDbEntity> offlineMovie;
    private OfflineMovieAdapter offlineMovieAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.offline_movies_screen);

        findViews();
        getOfflineMovieList();
    }

    private void findViews() {
        offlineMovieList = (ListView) findViewById(R.id.offline_movies_screen_list_view);
        buttonGetMore = (Button) findViewById(R.id.get_more_button_offline_movies_screen);
    }

    private void getOfflineMovieList() {

        // Query DB for all clients
        offlineMovie =
                SugarRecord.listAll(OfflineMovieDbEntity.class);

        offlineMovieAdapter = new OfflineMovieAdapter(this, offlineMovie);
        offlineMovieList.setAdapter(offlineMovieAdapter);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();

                if(name.isEmpty()){
                    Snackbar.make(nameInput, R.string.username_required_field, Snackbar.LENGTH_SHORT).show();
                    return;
                }

                String ageString = ageInput.getText().toString();

                if(ageString.isEmpty()){
                    Snackbar.make(nameInput, R.string.age_required_field, Snackbar.LENGTH_SHORT).show();
                    return;
                }

                int age = Integer.parseInt(ageString);

                ClientDbEntity client = new ClientDbEntity(name, age);
                client.save();
                clients.add(client);
                clientsAdapter.notifyDataSetChanged();
            }
        });
    }*/
}
