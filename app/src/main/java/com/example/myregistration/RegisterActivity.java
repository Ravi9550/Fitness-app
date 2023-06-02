package com.example.myregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    TextView btn;

    private EditText username,password,email,confirmpassword;
    Button btnRegister;
    String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    private static final String TAG = "RegisterActivity";

   FirebaseAuth mAuth;
   FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn = findViewById(R.id.already);
        username = findViewById(R.id.passwords);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);

        btnRegister = findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        progressBar=findViewById(R.id.progressBar);

        ImageView img2=findViewById(R.id.imageView3);
        img2.setImageResource(R.drawable.ic_hide_pwd);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
                {
                    // if password is visible then hide it
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    // change icon
                    img2.setImageResource(R.drawable.ic_hide_pwd);
                }
                else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img2.setImageResource(R.drawable.ic_show_pwd);
                }
            }

        });

        ImageView img3=findViewById(R.id.imageView4);
        img3.setImageResource(R.drawable.ic_hide_pwd);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
                {
                    // if password is visible then hide it
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    // change icon
                    img3.setImageResource(R.drawable.ic_hide_pwd);
                }
                else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img3.setImageResource(R.drawable.ic_show_pwd);
                }
                if(confirmpassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
                {
                    // if password is visible then hide it
                    confirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    // change icon
                    img3.setImageResource(R.drawable.ic_hide_pwd);
                }
                else {
                    confirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img3.setImageResource(R.drawable.ic_show_pwd);
                }

            }

        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegister.setBackgroundResource(R.drawable.register_bg);
                checkCredentials();

            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

            }
        });

    }
    private void checkCredentials() {
        String Username = username.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String ConfirmPassword = confirmpassword.getText().toString();

        if (Username.isEmpty() || Username.length()<7)
        {

            ShowError(username ,"Username is Invalid");
        }
        else if (Email.isEmpty())
        {
             ShowError(email,"Email is Invalid");
        }

        else if (Password.isEmpty() || Password.length()<7)
        {
            ShowError(password,"password must be 7 character");
        }
        else if (ConfirmPassword.isEmpty() || !ConfirmPassword.equals(Password))
        {
            ShowError(confirmpassword,"password not match ");
        }
        else {
            progressDialog.setMessage("Please Wait while Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                    Toast.makeText(RegisterActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();


                }
                else {
                    try {
                        throw task.getException();
                    }catch(FirebaseAuthWeakPasswordException e)

                    {
                          password.setError("Your password is too weak");
                          password.requestFocus();
                    }
                    catch(FirebaseAuthInvalidCredentialsException e)
                    {
                        email.setError("Your email is invalid or already in use");
                        email.requestFocus();
                    }
                    catch(FirebaseAuthUserCollisionException e)

                    {
                        email.setError("User in already registered with this email");
                        email.requestFocus();
                    }
                    catch(Exception e)
                    {
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();




                    }






                }
                    progressDialog.hide();
                }
            });

        }

    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegisterActivity.this,HomeActivtyFirst.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    private void ShowError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();

    }


}
