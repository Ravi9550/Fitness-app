package com.example.myregistration;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class profile extends AppCompatActivity {
    TextView user;

    ImageView img;
    ImageView pen;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = findViewById(R.id.currentuser);
        img=findViewById(R.id.img);
        pen=findViewById(R.id.imageView);
        TextView user_logout = findViewById(R.id.profile_log);
        img.setImageResource(R.drawable.profile);
        user.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        user.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());


        user_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(profile.this, gso);
                mGoogleSignInClient.signOut();
                mGoogleSignInClient.revokeAccess();
                startActivity(new Intent(profile.this, LoginActivity.class));
                finish();
            }
        });

        pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });


    }
    public void onFacebookClicked(View view) {
        String facebookUrl = "https://www.facebook.com/example";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
        startActivity(intent);
    }

    /**
     * Opens the Twitter link.
     */
    public void onTwitterClicked(View view) {
        String twitterUrl = "https://twitter.com/example";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl));
        startActivity(intent);
    }

    /**
     * Opens the Instagram link.
     */
    public void onInstagramClicked(View view) {
        String instagramUrl = "https://www.instagram.com/example";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl));
        startActivity(intent);
    }

    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        img.setImageBitmap(
                                selectedImageBitmap);
                    }
                }
            });

}
