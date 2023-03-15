package app.cinematch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    ArrayList<String> actores;
    ArrayList<String> directores;
    ArrayList<String> generos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

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
            }
        });
    }
}
