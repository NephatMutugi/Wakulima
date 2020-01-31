package com.rentals.wakulima.FirebaseAuthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rentals.wakulima.MainActivity;
import com.rentals.wakulima.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @SuppressWarnings("FieldCanBeLocal")
    private TextView textRegister;
    private EditText mUserName;
    private EditText mUserEmail;
    private EditText mUserPass;
    private Button mLoginBtn;
    private ProgressBar mProgressBar;
    private CoordinatorLayout mLoginSnack;

    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mUserEmail = findViewById(R.id.userEmail);
        mUserPass = findViewById(R.id.userPass);
        mLoginBtn = findViewById(R.id.loginBtn);
        textRegister = findViewById(R.id.regText);
        mProgressBar = findViewById(R.id.login_progressBar);
        mLoginSnack = findViewById(R.id.login_snack);

        mAuth = FirebaseAuth.getInstance();

        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);

                String email = mUserEmail.getText().toString();
                String password = mUserPass.getText().toString();

                if (!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password)){
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        mProgressBar.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        mProgressBar.setVisibility(View.INVISIBLE);
                                        Snackbar snackbar = Snackbar.make(mLoginSnack, "Login Failed "
                                                + Objects.requireNonNull(task.getException()).getMessage(), Snackbar.LENGTH_SHORT);
                                        snackbar.show();
                                    }
                                }
                            }
                    );
                } else if (TextUtils.isEmpty(email)){
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mUserEmail.setError("Field cannot be empty");
                }
                else if (TextUtils.isEmpty(password)){
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mUserPass.setError("Field cannot be empty");
                }
            }
        });
    }
}
