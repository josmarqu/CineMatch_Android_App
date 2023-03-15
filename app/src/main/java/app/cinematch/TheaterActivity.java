package app.cinematch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

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
        return true;
    }
}