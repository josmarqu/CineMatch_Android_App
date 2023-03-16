package app.cinematch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import java.util.ArrayList;

import app.cinematch.api.APIRestService;
import app.cinematch.api.RetrofitClient;
import app.cinematch.entities.Movie;
import app.cinematch.entities.Theater;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TheaterActivity extends AppCompatActivity {
private ArrayList<Movie> movies = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater);
        setData();
    }



    private void setData() {
        Retrofit retrofit = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService apiRestService = retrofit.create(APIRestService.class);
        Call<Theater> call;
        call = apiRestService.getTheater("en-US", 1, "US");
        call.enqueue(new Callback<Theater>() {
            @Override
            public void onResponse(Call<Theater> call, Response<Theater> response) {
                Theater theater = response.body();
                for (Movie movie : theater.getMovies()) {
                    movies.add(movie);
                }
                RcvAdapter adapter = new RcvAdapter(movies);
                RecyclerView rcvMovies = findViewById(R.id.rcvMovies);
                rcvMovies.setAdapter(adapter);
                adapter.setOnClickListener(v -> {
                    Movie movie = movies.get(rcvMovies.getChildAdapterPosition(v));
                    Intent intent = new Intent(TheaterActivity.this, DetailsActivity.class);
                    intent.putExtra("movie", movie);
                    startActivity(intent);
                });
            }

            @Override
            public void onFailure(Call<Theater> call, Throwable t) {
                System.out.println("Call failed");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem switchItem = menu.findItem(R.id.app_bar_switch);
        View actionView = switchItem.getActionView();
        Switch switchWidget = actionView.findViewById(R.id.switchWidget);
        switchWidget.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                System.out.println("Switch is on");
            } else {
                System.out.println("Switch is off");
            }
        });
        MenuItem profileItem = menu.findItem(R.id.app_bar_profile);
        profileItem.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(TheaterActivity.this, Profile.class);
            startActivity(intent);
            return true;
        });
        return true;
    }
}