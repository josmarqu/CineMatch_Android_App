package app.cinematch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {
    Button mButtonCerrarSesion;
    Button btnSur;

    FirebaseAuth mAuth;

    TextView textViewRespuesta;

    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        mButtonCerrarSesion = findViewById(R.id.btnLogOut);
        btnSur = findViewById(R.id.btnSur);

        textViewRespuesta = findViewById(R.id.textViewBien);

        imgView = findViewById(R.id.imageView);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personGivenName = acct.getGivenName();
            Uri personPhoto = acct.getPhotoUrl();

            textViewRespuesta.setText(String.format(getString(R.string.hello), personGivenName));
            Glide.with(this)
                    .load(personPhoto)
                    .into(imgView);
        }

        mButtonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btnSur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Survey.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            irMain();
        }
    }

    private void logout() {
        mAuth.signOut();
        irMain();
    }

    private void irMain() {
        Intent intent = new Intent(Profile.this, Main.class);
        startActivity(intent);
        finish();
    }
}
