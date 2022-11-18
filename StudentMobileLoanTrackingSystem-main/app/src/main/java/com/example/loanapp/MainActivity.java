package com.example.loanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    public EditText username, password;
    public Button loginbtn;
    public TextView registerbtn, forgotbtn;
    public ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        registerbtn = findViewById(R.id.register_btn);
        forgotbtn = findViewById(R.id.forgotbtn);

        loginbtn = findViewById(R.id.login_btn);

        loginbtn.setOnClickListener(this);

        progressBar = findViewById(R.id.progressbar);

        Toast.makeText(this, "Successfully Connected to Firebase", Toast.LENGTH_SHORT).show();

    }


    public void userLogin(){
        String email = username.getText().toString().trim();
        String thepassword = password.getText().toString().trim();

        if(email.isEmpty()){
            username.setError("Email is Required");
            username.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            username.setError("Please enter valid email");
            username.requestFocus();
            return;
        }

        if(thepassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(thepassword.length() < 6){
            password.setError("Min password length is 6 characters!");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, thepassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            showToast("Sucessfully Signin");
                            startActivity(new Intent(MainActivity.this, StudentList.class));
                            progressBar.setVisibility(View.GONE);

                        }else{
                            showToast("Failed to login! please check your credentials!");
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void NavigateForgot(View view){
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    public void NavigateRegister(View view){
        startActivity(new Intent(this, RegisterActivity.class));

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_btn:
                userLogin();
                break;


        }
    }


}