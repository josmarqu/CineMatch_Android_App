package app.cinematch.api;

import app.cinematch.entities.Cast;
import app.cinematch.entities.MovieDetails;
import app.cinematch.entities.Theater;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIRestService {

    String BASE_URL = "https://api.themoviedb.org/3/movie/";
    String API_KEY = "7974d59e0a6e5f3fcf066d7be78675ea";

    @GET("now_playing?api_key="+API_KEY+"&include_adult=false")
    Call<Theater> getTheater(
            @Query("language") String language,
            @Query("page") int page,
            @Query("region") String region
    );

    @GET("{movie_id}?api_key="+API_KEY)
    Call<MovieDetails> getMovieDetails(
            @Path("movie_id") int movieId,
            @Query("language") String language
    );

    @GET("{movie_id}/credits?api_key="+API_KEY)
    Call<Cast> getMovieCast(
            @Path("movie_id") int movieId,
            @Query("language") String language
    );
}
