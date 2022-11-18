package com.example.loanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    public EditText email;
    public FirebaseAuth mauth;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.email_address);
        mauth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
    }

    public void NavigateLogin(View view){
        startActivity(new Intent(this,  MainActivity.class));
        finish();
    }


    public void ResetPassword(View view){
        String theemail = email.getText().toString().trim();

        if(theemail.isEmpty()){
            email.setError("This is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(theemail).matches()){
            email.setError("Please provide valid email address");
            email.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mauth.sendPasswordResetEmail(theemail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "Email has been sent", Toast.LENGTH_SHORT).show();
                    email.getText().clear();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}