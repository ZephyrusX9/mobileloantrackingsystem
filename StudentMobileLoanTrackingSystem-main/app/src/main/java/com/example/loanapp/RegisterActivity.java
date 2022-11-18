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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{


    public EditText id_no, username, emailaddress, password, confirmpassword;
    public Button loginbtn, registerbtn;
    public ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        id_no = findViewById(R.id.id_no);
        username= findViewById(R.id.username);
        emailaddress = findViewById(R.id.email_address);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirm_password);


        registerbtn = findViewById(R.id.register_btn);
        registerbtn.setOnClickListener(this);

        progressBar = findViewById(R.id.progressbar);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register_btn:
                registerUser();
                break;
        }
    }

    public void Navigatelogin(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void registerUser(){
        String the_id = id_no.getText().toString().trim();
        String theusername = username.getText().toString().trim();
        String theemail = emailaddress.getText().toString().trim();
        String thepassword = password.getText().toString().trim();
        String theconfirmpassword = confirmpassword.getText().toString().trim();

        if(the_id.isEmpty()){
            id_no.setError("This is required");
            id_no.requestFocus();
            return;
        }
        if(theusername.isEmpty()){
            username.setError("This is required");
            username.requestFocus();
            return;
        }
        if(theemail.isEmpty()){
            emailaddress.setError("This is required");
            emailaddress.requestFocus();
            return;
        }
        if(thepassword.isEmpty()){
            password.setError("This is required");
            password.requestFocus();
            return;
        }
        if(theconfirmpassword.isEmpty()){
            confirmpassword.setError("This is required");
            confirmpassword.requestFocus();
            return;
        }

        if(!thepassword.equals(theconfirmpassword)){
            password.setError("The password is not match Please check!");
            password.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(theemail).matches()){
            emailaddress.setError("Please provide valid email address");
            emailaddress.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(theemail, thepassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(the_id, theusername, theemail, thepassword);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "User has been registered Successfully", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));


                                    }else{
                                        Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}