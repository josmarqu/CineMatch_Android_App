package app.cinematch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import app.cinematch.api.APIRestService;
import app.cinematch.api.RetrofitClient;
import app.cinematch.entities.Cast;
import app.cinematch.entities.MovieDetails;
import app.cinematch.entities.Theater;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService apiRestService = retrofit.create(APIRestService.class);
        Call<Theater> call = apiRestService.getTheater("en-US", 1, "US");
        call.enqueue(new Callback<Theater>() {
            @Override
            public void onResponse(Call<Theater> call, Response<Theater> response) {
                Theater theater = response.body();
                int id = theater.getMovies()[0].getMovieId();
                Call<MovieDetails> call2 = apiRestService.getMovieDetails(id, "en-US");
                call2.enqueue(new Callback<MovieDetails>() {
                    @Override
                    public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                        MovieDetails movieDetails = response.body();
                        String genre = movieDetails.getGenres().get(0).getName();
                        System.out.println(genre);
                    }

                    @Override
                    public void onFailure(Call<MovieDetails> call, Throwable t) {

                    }
                });
                Call<Cast> call3 = apiRestService.getMovieCast(id, "en-US");
                call3.enqueue(new Callback<Cast>() {
                    @Override
                    public void onResponse(Call<Cast> call, Response<Cast> response) {
                        Cast cast = response.body();
                        String actor = cast.getActors().get(0).getName();
                        String crew = cast.getCrew().get(0).getName();
                        System.out.println(actor);
                        System.out.println(crew);
                    }

                    @Override
                    public void onFailure(Call<Cast> call, Throwable t) {

                    }
                });


            }

            @Override
            public void onFailure(Call<Theater> call, Throwable t) {

            }
        });
    }
}