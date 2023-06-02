package com.example.myregistration;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    TextView Welcome,app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Welcome = findViewById(R.id.welcometextview);
        app = findViewById(R.id.quizapp);

//        Intent intent = new Intent(SplashActivity.this,LoginActivity.class);

        new Handler().postDelayed(() -> {



                    // Check if the user is already logged in
                    SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

                    // Start the appropriate activity
                    Intent intent;
                    if (isLoggedIn) {
                        intent = new Intent(SplashActivity.this, HomeActivtyFirst.class);
                    } else {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                    }
                    startActivity(intent);
                    finish();



        },4000);

        Animation myanimation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.bottom);
        Welcome.startAnimation(myanimation);
        Animation myanimation2 = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.top);
        app.startAnimation(myanimation2);





    }
}