package com.example.myregistration;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class FirstLayout extends AppCompatActivity {

//    public static String PREFS_NAME ="MyPrefsFile";

private boolean isBackPressedOnce = false;
    TextView user;
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_layout);

        FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

//        user.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(FirstLayout.this, gso);
                mGoogleSignInClient.signOut();
                mGoogleSignInClient.revokeAccess();
                startActivity(new Intent(FirstLayout.this, LoginActivity.class));


            }
        });




        getWindow().setBackgroundDrawableResource(R.drawable.lastback);
    }





    // Handle click for gym container
    public void openGymActivity(View view) {
        Intent intent = new Intent(this, GymActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


        startActivity(intent);
    }

    // Handle click for meditation container
    public void openMeditationActivity(View view) {
        Intent intent = new Intent(this, MeditationActivity.class);
        startActivity(intent);
    }

    // Handle click for focus container
    public void openFocusActivity(View view) {
        Intent intent = new Intent(this, FocusActivity.class);
        startActivity(intent);
    }

}