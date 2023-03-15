package app.cinematch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class Survey extends AppCompatActivity {
    Spinner spnAct;
    Spinner spnDir;
    Spinner spnGen;

    TextView txtActSel;
    TextView txtDirSel;
    TextView txtGenSel;

    Button btnResAct;
    Button btnResDir;
    Button btnResGen;
    Button btnSend;

    String[] actors;
    ArrayList<String> resActors = new ArrayList<>();
    String[] directors;
    ArrayList<String> resDirectors = new ArrayList<>();
    String[] genres;
    ArrayList<String> resGenres = new ArrayList<>();
    String resAct;
    String resDir;
    String resGen;

    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("CineMatch");
    String userId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        mAuth = FirebaseAuth.getInstance();

        actors = getResources().getStringArray(R.array.famous_actors);
        directors = getResources().getStringArray(R.array.famous_directors);
        genres = getResources().getStringArray(R.array.genres);

        spnAct = findViewById(R.id.spnAct);
        spnDir = findViewById(R.id.spnDir);
        spnGen = findViewById(R.id.spnGen);

        txtActSel = findViewById(R.id.txtActSel);
        txtDirSel = findViewById(R.id.txtDirSel);
        txtGenSel = findViewById(R.id.txtGenSel);

        btnResAct = findViewById(R.id.btnResAct);
        btnResDir = findViewById(R.id.btnResDir);
        btnResGen = findViewById(R.id.btnResGen);
        btnSend = findViewById(R.id.btnSend);



        spnAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String actSel = spnAct.getSelectedItem().toString();
                resActors.add(actSel);
                resAct = "";
                for (String resActor : resActors) {
                    resAct += resActor + " ";
                    txtActSel.setText(resAct);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        spnDir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dirSel = spnDir.getSelectedItem().toString();
                resDirectors.add(dirSel);
                resDir = "";
                for (String resDirector : resDirectors) {
                    resDir += resDirector + " ";
                    txtDirSel.setText(resDir);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnGen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String genSel = spnGen.getSelectedItem().toString();
                resGenres.add(genSel);
                resGen = "";
                for (String resGenre : resGenres) {
                    resGen += resGenre + " ";
                    txtGenSel.setText(resGen);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnResAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resActors.clear();
                resAct = "";
                spnAct = null;
            }
        });

        btnResDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resDirectors.clear();
                resDir = "";
                spnDir.setSelection(0);
            }
        });

        btnResGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resGenres.clear();
                resGen = "";
                spnGen.setSelection(0);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resActors.size() < 3 || resDirectors.size() < 3 || resGenres.size() < 3) {
                    Toast.makeText(Survey.this, R.string.incorrect_input, Toast.LENGTH_SHORT).show();
                }
                else {
                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Survey.this);
                        userId = acct.getId();
                        myRef.child(userId).child("Actors").setValue(resActors);
                        myRef.child(userId).child("Directors").setValue(resDirectors);
                        myRef.child(userId).child("Genres").setValue(resGenres);
                }
            }
        });
    }

    private void signOut() {
        mAuth.signOut();
        MainActivity.mGoogleSignInClient.signOut();
        Intent intent = new Intent(Survey.this, MainActivity.class);
        startActivity(intent);
    }


}