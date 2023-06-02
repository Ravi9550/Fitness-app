package com.example.myregistration;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class FocusActivity extends AppCompatActivity {
    private EditText hoursEditText, minutesEditText, secondsEditText;
    MediaPlayer mySong;

    TextView countDownText;

    private Button start, pause, end, reset;
    private CountDownTimer timer;

    int startTime;

    int hoursLeft, minutesLeft, secondsLeft;
    TextView hoursLeftText, minutesLeftText, secondsLeftText;

    int totalSecondsLeft;

    boolean isPaused = false;

    private void finishTimer(String message) {
        countDownText.setText(message);
        start.setEnabled(true);
        pause.setEnabled(false);
        end.setEnabled(false);
    }

    private void setupEditTexts() {
        hoursEditText = findViewById(R.id.hours);
        minutesEditText = findViewById(R.id.minutes);
        secondsEditText = findViewById(R.id.seconds);

        hoursEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2) {
                    minutesEditText.requestFocus();
                }
            }
        });

        minutesEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2) {
                    secondsEditText.requestFocus();
                }
            }
        });
    }

    private void updateTimeRemaining(long millisUntilFinished) {
        totalSecondsLeft = (int) millisUntilFinished / 1000;
        hoursLeft = totalSecondsLeft / 3600;
        minutesLeft = (totalSecondsLeft % 3600) / 60;
        secondsLeft = totalSecondsLeft % 60;
        hoursLeftText.setText(String.format("%02d", hoursLeft));
        minutesLeftText.setText(String.format("%02d", minutesLeft));
        secondsLeftText.setText(String.format("%02d", secondsLeft));
        countDownText.setText("Count down in progress");
    }

    private void setupButtons() {
        start = findViewById(R.id.start_button);
        reset = findViewById(R.id.reset_button);
        pause = findViewById(R.id.pause_button);
        end = findViewById(R.id.end_button);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] songIds = {R.raw.medit, R.raw.focus,R.raw.me1,R.raw.me2,R.raw.me3,R.raw.me4,R.raw.me5,R.raw.me6,R.raw.me7,R.raw.me8,R.raw.gy9};


// Generate a random number within the range of the songIds array size
                Random rand = new Random();
                int randomIndex = rand.nextInt(songIds.length);

// Retrieve the song at the index of the random number generated
                int selectedSongId = songIds[randomIndex];

// Play the selected song using the media player in Android Studio
                mySong = MediaPlayer.create(FocusActivity.this, selectedSongId);

                startTime = 0;
                startTime += Integer.parseInt(secondsEditText.getText().toString()) * 1000;
                startTime += Integer.parseInt(minutesEditText.getText().toString()) * 60 * 1000;
                startTime += Integer.parseInt(hoursEditText.getText().toString()) * 60 * 60 * 1000;

                start.setEnabled(false);
                reset.setEnabled(true);
                pause.setEnabled(true);
                end.setEnabled(true);

                timer = new CountDownTimer(startTime, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        updateTimeRemaining(millisUntilFinished);
                    }

                    @Override
                    public void onFinish() {
                        finishTimer("Count down complete");
                        mySong.release();
                    }
                }.start();
                mySong.start();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start.setEnabled(false);
                reset.setEnabled(true);
                pause.setEnabled(true);
                pause.setText("Pause");
                isPaused = false;
                end.setEnabled(true);

                timer.cancel();
                timer = new CountDownTimer(startTime, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        updateTimeRemaining(millisUntilFinished);
                    }

                    @Override
                    public void onFinish() {
                        finishTimer("Count down complete");
                        mySong.release();
                    }
                }.start();
                mySong.release();
                int[] songIds = {R.raw.medit, R.raw.focus,R.raw.me1,R.raw.me2,R.raw.me3,R.raw.me4,R.raw.me5,R.raw.me6,R.raw.me7,R.raw.me8,R.raw.gy9};

                Random rand = new Random();
                int randomIndex = rand.nextInt(songIds.length);
                int selectedSongId = songIds[randomIndex];
                mySong = MediaPlayer.create(FocusActivity.this, selectedSongId);

                mySong.start();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPaused = !isPaused;
                if (isPaused) {
                    pause.setText("Resume");
                    timer.cancel();
                    countDownText.setText("Count down paused");
                    mySong.pause();
                } else {
                    pause.setText("Pause");

                    timer = new CountDownTimer(totalSecondsLeft * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            updateTimeRemaining(millisUntilFinished);
                        }
                        @Override
                        public void onFinish() {
                            finishTimer("Count down complete");
                            mySong.release();

                        }
                    }.start();
                    mySong.start();
                }

            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                finishTimer("Count down cancelled");
                mySong.release();
            }
        });

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        setupEditTexts();

        countDownText = findViewById(R.id.countDownText);
        hoursLeftText = findViewById(R.id.hoursLeftText);
        minutesLeftText = findViewById(R.id.minutesLeftText);
        secondsLeftText = findViewById(R.id.secondsLeftText);










        setupButtons();
    }
}