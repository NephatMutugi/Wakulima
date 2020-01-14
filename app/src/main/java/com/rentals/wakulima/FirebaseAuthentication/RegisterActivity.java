package com.rentals.wakulima.FirebaseAuthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rentals.wakulima.R;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    // Widgets
    private EditText fName;
    private EditText lName;
    private EditText mEmail;
    private EditText mPass;
    private EditText mConfirmPass;
    private Button mRegBtn;
    private TextView mLoginText;
    private ProgressBar mProgressBar;
    private CoordinatorLayout mSnackbarLayout;


    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Instantiate Widget
        fName = findViewById(R.id.firstName);
        lName = findViewById(R.id.lastName);
        mEmail = findViewById(R.id.email);
        mPass = findViewById(R.id.pass);
        mConfirmPass = findViewById(R.id.confirmPass);
        mRegBtn = findViewById(R.id.regBtn);
        mLoginText = findViewById(R.id.loginLink);
        mProgressBar = findViewById(R.id.progressBar);
        mSnackbarLayout = findViewById(R.id.snackbar_layout);

        //Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();

        mLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name = fName.getText().toString().trim();
                String second_name = lName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPass.getText().toString().trim();
                String confirm_pass = mConfirmPass.getText().toString().trim();

                if (TextUtils.isEmpty(first_name)){
                    fName.setError("First name is required");
                    return;
                }
                if (TextUtils.isEmpty(second_name)){
                    lName.setError("Second name is required");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPass.setError("Password is required");
                    return;
                }
                if (password.length()<6){
                    mPass.setError("Password should be 6 or more characters");
                    return;
                }
                if (!TextUtils.equals(password,confirm_pass)){
                    mConfirmPass.setError("Passwords do not match");
                }  else {

                    mProgressBar.setVisibility(View.VISIBLE);

                    //Register user
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                mProgressBar.setVisibility(View.INVISIBLE);
                                Snackbar snackbar = Snackbar.make(mSnackbarLayout, "Registration successful",Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }else{
                                Snackbar snackbar = Snackbar.make(mSnackbarLayout, "Registration failed. "
                                        +task.getException().getMessage(),Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                mProgressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                }

            }
        });

    }

    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
