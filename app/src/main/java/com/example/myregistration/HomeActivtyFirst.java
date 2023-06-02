package com.example.myregistration;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Time;

public class HomeActivtyFirst extends AppCompatActivity {

//    public static String PREFS_NAME ="MyPrefsFile";

    private boolean isBackPressedOnce = false;
    TextView user;
    private FirebaseAuth mAuth;


    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timepass);
        FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();




        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView right_icon=findViewById(R.id.right);

        right_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });







    }
    void showMenu(View v)
    {
        PopupMenu popupMenu = new PopupMenu(HomeActivtyFirst.this,v);
        popupMenu.getMenuInflater().inflate(R.menu.menu_home,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() ==R.id.profile)
                {
//                    Toast.makeText(Timepass.this,"profile",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomeActivtyFirst.this,profile.class);
                    startActivity(intent);

                }
                if(item.getItemId()==R.id.logout)
                {

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("isLoggedIn", false);
                    editor.apply();
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();
                    GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(HomeActivtyFirst.this, gso);
                    mGoogleSignInClient.signOut();
                    mGoogleSignInClient.revokeAccess();
                    startActivity(new Intent(HomeActivtyFirst.this, LoginActivity.class));
                    finish();





                }
                return true;
            }
        });
        popupMenu.show();
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