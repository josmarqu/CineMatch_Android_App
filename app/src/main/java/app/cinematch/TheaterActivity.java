package app.cinematch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.cinematch.api.APIRestService;
import app.cinematch.api.RetrofitClient;
import app.cinematch.entities.Cast;
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
        setAllMovies();
    }
    
    private void setAllMovies() {
        Retrofit retrofit = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService apiRestService = retrofit.create(APIRestService.class);
        Call<Theater> call = apiRestService.getTheater("en-US", 1, "US");
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
                setRecommendedMovies();
            } else {
                setAllMovies(); 
            }
        });
        MenuItem profileItem = menu.findItem(R.id.app_bar_profile);
        profileItem.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(TheaterActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        });
        return true;
    }

    private void setRecommendedMovies() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("CineMatch");
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String userId = acct.getId();
        ArrayList<String> userActors = new ArrayList<>();
        ArrayList<String> userDirectors = new ArrayList<>();
        ArrayList<String> userGenres = new ArrayList<>();
        DatabaseReference actorsRef = myRef.child(userId).child("Actors");
        actorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot actor : snapshot.getChildren()) {
                    String actorName = actor.getValue(String.class);
                    userActors.add(actorName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        DatabaseReference directorsRef = myRef.child(userId).child("Directors");
        directorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot director : snapshot.getChildren()) {
                    userDirectors.add(director.getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        DatabaseReference genresRef = myRef.child(userId).child("Genres");
        genresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot genre : snapshot.getChildren()) {
                    userGenres.add(genre.getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        System.out.println(userActors.size());
    }

    @Override
    public void onBackPressed() {
        // No hacer nada
    }
}