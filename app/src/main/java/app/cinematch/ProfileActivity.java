package app.cinematch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    Button mButtonCerrarSesion;
    Button btnSur;

    FirebaseAuth mAuth;

    TextView textViewRespuesta;
    TextView txtActSel;
    TextView txtDirSel;
    TextView txtGenSel;


    ImageView imgView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("CineMatch");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String userId = acct.getId();

        mAuth = FirebaseAuth.getInstance();
        mButtonCerrarSesion = findViewById(R.id.btnLogOut);
        btnSur = findViewById(R.id.btnSur);

        textViewRespuesta = findViewById(R.id.textViewBien);
        txtActSel = findViewById(R.id.txtActPre);
        txtDirSel = findViewById(R.id.txtDirPre);
        txtGenSel = findViewById(R.id.txtGenPre);

        getActorsUser(userId);

        getDirectorsUser(userId);

        getGenresUser(userId);

        imgView = findViewById(R.id.imageView);

        String personGivenName = acct.getGivenName();
        Uri personPhoto = acct.getPhotoUrl();

        textViewRespuesta.setText(String.format(getString(R.string.hello), personGivenName));
        Glide.with(this)
                .load(personPhoto).override(400,400)
                .into(imgView);

        mButtonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btnSur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SurveyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getGenresUser(String userId) {
        DatabaseReference genresRef = myRef.child(userId).child("Genres");
        genresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> genres = new ArrayList<>();
                for (DataSnapshot genreSnapshot : dataSnapshot.getChildren()) {
                    String genre = genreSnapshot.getValue(String.class);
                    genres.add(genre);
                }
                for (String genre : genres) {
                    txtGenSel.append(" | " + genre + " | ");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Maneja el error aquí
            }
        });
    }

    private void getDirectorsUser(String userId) {
        DatabaseReference directorsRef = myRef.child(userId).child("Directors");
        directorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> directors = new ArrayList<>();
                for (DataSnapshot directorSnapshot : dataSnapshot.getChildren()) {
                    String director = directorSnapshot.getValue(String.class);
                    directors.add(director);
                }
                for (String director : directors) {
                    txtDirSel.append(" | " + director + " | ");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Maneja el error aquí
            }
        });
    }

    private void getActorsUser(String userId) {
        DatabaseReference actorsRef = myRef.child(userId).child("Actors");
        actorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> actors = new ArrayList<>();
                for (DataSnapshot actorSnapshot : dataSnapshot.getChildren()) {
                    String actor = actorSnapshot.getValue(String.class);
                    actors.add(actor);
                }
                for (String actor : actors) {
                    txtActSel.append(" | " + actor + " | ");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Maneja el error aquí
            }
        });
    }

    private void logout() {
        mAuth.signOut();
        MainActivity.mGoogleSignInClient.signOut();
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
