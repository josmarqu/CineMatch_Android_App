package app.cinematch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    ArrayList<String> actores = new ArrayList<>();
    ArrayList<String> directores = new ArrayList<>();
    ArrayList<String> generos = new ArrayList<>();

    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("CineMatch");
    String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        mAuth = FirebaseAuth.getInstance();

        actores.add("Tom Cruise");
        directores.add("Steven Spielberg");
        generos.add("Acción");

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
                actores.add(actSel);
                txtActSel.setText(actores.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnDir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dirSel = spnDir.getSelectedItem().toString();
                directores.add(dirSel);
                txtDirSel.setText(directores.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnGen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String genSel = spnGen.getSelectedItem().toString();
                generos.add(genSel);
                txtGenSel.setText(generos.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnResAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actores.clear();
                txtActSel.setText(actores.toString());
            }
        });

        btnResDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directores.clear();
                txtDirSel.setText(directores.toString());
            }
        });

        btnResGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generos.clear();
                txtGenSel.setText(generos.toString());
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Survey.this, Profile.class);
                startActivity(intent);
                finish();

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Survey.this);
                if (acct != null) {
                    userId = acct.getId();

                    myRef.child(userId).child("Actores").setValue(actores);
                    myRef.child(userId).child("Directores").setValue(directores);
                    myRef.child(userId).child("Generos").setValue(generos);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            irMain();
        }
    }

    private void irMain() {
        Intent intent = new Intent(Survey.this, Main.class);
        startActivity(intent);
        finish();
    }
}
