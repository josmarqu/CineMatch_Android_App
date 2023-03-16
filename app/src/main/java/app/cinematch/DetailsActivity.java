package app.cinematch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import app.cinematch.api.APIRestService;
import app.cinematch.api.RetrofitClient;
import app.cinematch.entities.Cast;
import app.cinematch.entities.Movie;
import app.cinematch.entities.MovieDetails;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import retrofit2.Call;
import retrofit2.Retrofit;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Movie movie = (Movie) getIntent().getParcelableExtra("movie");
        ConstraintLayout bgDetails = findViewById(R.id.bgDetails);
        Uri url = Uri.parse("https://image.tmdb.org/t/p/w500" + movie.getPosterPath());
        Glide.with(this)
                .load(url)
                .centerCrop()
                .transform(new BlurTransformation( 3, 3),
                        new ColorFilterTransformation(Color.argb(150, 0, 0, 0)))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        bgDetails.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(movie.getTitle());
        TextView tvReleaseDate = findViewById(R.id.tvReleaseDate);
        tvReleaseDate.setText(getString(R.string.release_date) + movie.getReleaseDate());
        TextView tvOverview = findViewById(R.id.tvOverview);
        tvOverview.setText(movie.getOverview());
        Retrofit retrofit = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService apiRestService = retrofit.create(APIRestService.class);
        Call<MovieDetails> call = apiRestService.getMovieDetails(movie.getMovieId(), "en-US");
        call.enqueue(new retrofit2.Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, retrofit2.Response<MovieDetails> response) {
                MovieDetails movieDetails = response.body();
                TextView tvCtry = findViewById(R.id.tvCtry);
                tvCtry.setText(getString(R.string.prod_ctry) + movieDetails.getProdCtries().get(0).getName());
                TextView tvGenres = findViewById(R.id.tvGenres);
                tvGenres.setText(movieDetails.getGenres().get(0).getName());
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                System.out.println("Call failed");
            }
        });

        Call<Cast> callCast = apiRestService.getMovieCast(movie.getMovieId(), "en-US");
        callCast.enqueue(new retrofit2.Callback<Cast>() {
            @Override
            public void onResponse(Call<Cast> call, retrofit2.Response<Cast> response) {
                Cast cast = response.body();
                TextView tvDirector = findViewById(R.id.tvDirector);
                String directors = "";
                for (int i = 0; i < cast.getCrew().size(); i++) {
                    if (cast.getCrew().get(i).getJob().equals("Director")) {
                        directors += cast.getCrew().get(i).getName();
                        if (cast.getCrew().size() > 1 && i < cast.getCrew().size() - 1) {
                            directors += ", ";
                        }
                    }
                }
                tvDirector.setText(directors);
                TextView tvCast = findViewById(R.id.tvCast);
                String actors = "";
                for (int i = 0; i < cast.getActors().size(); i++) {
                    actors += cast.getActors().get(i).getName();
                    if (cast.getActors().size() > 1 && i < cast.getActors().size() - 1) {
                        actors += ", ";
                    }
                }
                tvCast.setText(actors);
            }

            @Override
            public void onFailure(Call<Cast> call, Throwable t) {
                System.out.println("Call failed");
            }
        });

    }

}