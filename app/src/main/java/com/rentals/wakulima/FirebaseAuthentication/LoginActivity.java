package com.rentals.wakulima.FirebaseAuthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.rentals.wakulima.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @SuppressWarnings("FieldCanBeLocal")
    private TextView textRegister;
    private EditText mUserName;
    private EditText mUserEmail;
    private EditText mUserPass;
    private Button mLoginBtn;
    private ProgressBar mProgressBar;

    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserName = findViewById(R.id.userName);
        mUserEmail = findViewById(R.id.userEmail);
        mUserPass = findViewById(R.id.userPass);
        mLoginBtn = findViewById(R.id.loginBtn);
        textRegister = findViewById(R.id.regText);
        mProgressBar = findViewById(R.id.login_progressBar);

        mAuth = FirebaseAuth.getInstance();

        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
