package app.cinematch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import app.cinematch.api.APIRestService;
import app.cinematch.api.RetrofitClient;
import app.cinematch.entities.Theater;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

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
                System.out.println(theater.getMovies()[0].getTitle());
            }

            @Override
            public void onFailure(Call<Theater> call, Throwable t) {

            }
        });
    }
}