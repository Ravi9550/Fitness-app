package com.example.myregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
//    public static String PREFS_NAME ="MyPrefsFile";



    TextView btn;
    EditText email;

    EditText password;
    Button btnlogin;
    String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Button btnGoogle;
    ProgressBar progressBar;
    private static final String TAG = "LoginActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn = findViewById(R.id.signuptextview);

        email =findViewById(R.id.email);
        password = findViewById(R.id.passwords);
        btnlogin = findViewById(R.id.btnlogin);
        btnGoogle = findViewById(R.id.btngoogle);
        progressBar =findViewById(R.id.progress);
        ImageView img=findViewById(R.id.imageView2);
        img.setImageResource(R.drawable.ic_hide_pwd);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
                {
                    // if password is visible then hide it
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    // change icon
                    img.setImageResource(R.drawable.ic_hide_pwd);
                }
                else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img.setImageResource(R.drawable.ic_show_pwd);
                }
            }

        });


        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkCredentials();
//                finish();

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,GoogleSign.class);
                startActivity(intent);


            }
        });
    }
    private void checkCredentials() {
        String Email = email.getText().toString();
        String Password = password.getText().toString();
//        if (!Email.matches(email_pattern))
        if (Email.isEmpty() )
        {
            ShowError(email,"Email field can't be empty");
            email.requestFocus();



            if (Password.isEmpty()) {
                ShowError(password, "password can't be empty");
                password.requestFocus();

                if (password.length() < 7) {
                    ShowError(password, "password must be 7 character");
                    password.requestFocus();

                }
            }



        }


        else {
            progressDialog.setMessage("Please Wait while Login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();

                        sendUserToNextActivity();

                        Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        try{
                            throw task.getException();
                        }
                        catch(FirebaseAuthInvalidUserException e)
                        {
                            email.setError("User does not exit or no longer valid. register again");
                            email.requestFocus();
                        }
                        catch(FirebaseAuthInvalidCredentialsException e)
                        {
                            email.setError("Invalid credentials .check and re-enter");
                            email.requestFocus();

                        }
                        catch (Exception e)
                        {
                            Log.e(TAG,e.getMessage());
                            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                        }


                    }
                   progressDialog.hide();

                }
            });
        }



    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(LoginActivity.this,HomeActivtyFirst.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void ShowError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();

    }
}