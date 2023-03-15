package app.cinematch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import androidx.gridlayout.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

public class Survey extends AppCompatActivity {
    GridLayout gdlytAct;
    GridLayout gdlytDir;
    GridLayout gdlytGen;

    Button btnResetAll;
    Button btnSend;

    String[] actors;
    ArrayList<String> listTextActors = new ArrayList<>();
    String[] directors;
    ArrayList<String> listTextDir = new ArrayList<>();
    String[] genres;
    ArrayList<String> listTextGenre = new ArrayList<>();

    int maxcheckedAct = 5;
    int checkContAct = 0;
    int maxcheckedDir = 5;
    int checkContDir = 0;
    int maxcheckedGen = 5;
    int checkContGen = 0;

    
    

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


        btnResetAll = findViewById(R.id.btnResetAll);
        btnSend = findViewById(R.id.btnSend);

        gdlytAct = findViewById(R.id.gridActors);
        gdlytDir = findViewById(R.id.gdDir);
        gdlytGen = findViewById(R.id.gdGenre);


        String[] actorsList = getResources().getStringArray(R.array.famous_actors);
        for (int i = 0; i < actorsList.length; i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(actorsList[i]);
            checkBox.setId(i);
            gdlytAct.setColumnCount(2);
            gdlytAct.setRowCount(actorsList.length/2);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setGravity(Gravity.CENTER);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            checkBox.setLayoutParams(params);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if(isChecked) {
                            checkContAct++;
                            if(checkContAct > maxcheckedAct) {
                                checkBox.setChecked(false);
                                checkContAct--;
                            } else {
                                listTextActors.add(checkBox.getText().toString());
                            }
                        } else {
                            checkContAct--;
                            listTextActors.remove(checkBox.getText().toString());
                        }

                    }
                });

            gdlytAct.addView(checkBox);
        }

        String[] directorsList = getResources().getStringArray(R.array.famous_directors);
        for (int i = 1; i < directorsList.length; i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(directorsList[i]);
            checkBox.setId(i);
            gdlytDir.setColumnCount(2);
            gdlytDir.setRowCount(directorsList.length/2);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setGravity(Gravity.CENTER);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            checkBox.setLayoutParams(params);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked) {
                        checkContDir++;
                        if(checkContAct > maxcheckedDir) {
                            checkBox.setChecked(false);
                            checkContDir--;
                        } else {
                            listTextDir.add(checkBox.getText().toString());
                        }
                    } else {
                        checkContDir--;
                        listTextDir.remove(checkBox.getText().toString());
                    }

                }
            });
            gdlytDir.addView(checkBox);
        }

        String[] genreList = getResources().getStringArray(R.array.genres);
        for (int i = 1; i < genreList.length; i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(genreList[i]);
            checkBox.setId(i);
            gdlytGen.setColumnCount(2);
            gdlytGen .setRowCount(genreList.length/2);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setGravity(Gravity.CENTER);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            checkBox.setLayoutParams(params);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked) {
                        checkContGen++;
                        if(checkContAct > maxcheckedGen) {
                            checkBox.setChecked(false);
                            checkContGen--;
                        } else {
                            listTextGenre.add(checkBox.getText().toString());
                        }
                    } else {
                        checkContGen--;
                        listTextGenre.remove(checkBox.getText().toString());
                    }

                }
            });

            gdlytGen.addView(checkBox);
        }



        btnResetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < gdlytAct.getChildCount(); i++) {
                    CheckBox checkBox = (CheckBox) gdlytAct.getChildAt(i);
                    checkBox.setChecked(false);
                }
                for (int i = 0; i < gdlytDir.getChildCount(); i++) {
                    CheckBox checkBox = (CheckBox) gdlytDir.getChildAt(i);
                    checkBox.setChecked(false);
                }
                for (int i = 0; i < gdlytGen.getChildCount(); i++) {
                    CheckBox checkBox = (CheckBox) gdlytGen.getChildAt(i);
                    checkBox.setChecked(false);
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listTextActors.size() < 1 || listTextDir.size() < 1 || listTextGenre.size() < 1) {
                    Toast.makeText(Survey.this, "Please select at least one actor, director and genre", Toast.LENGTH_SHORT).show();
                } else {
                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Survey.this);
                    userId = acct.getId();

                    myRef.child(userId).child("Actors").setValue(listTextActors);
                    myRef.child(userId).child("Directors").setValue(listTextDir);
                    myRef.child(userId).child("Genres").setValue(listTextGenre);
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