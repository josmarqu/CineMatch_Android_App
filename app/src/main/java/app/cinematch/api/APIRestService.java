package app.cinematch.api;

import app.cinematch.entities.Theater;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIRestService {

    String BASE_URL = "https://api.themoviedb.org/3/movie/";

    @GET("now_playing?api_key=7974d59e0a6e5f3fcf066d7be78675ea&include_adult=false")
    Call<Theater> getTheater(
            @Query("language") String language,
            @Query("page") int page,
            @Query("region") String region
    );
}
