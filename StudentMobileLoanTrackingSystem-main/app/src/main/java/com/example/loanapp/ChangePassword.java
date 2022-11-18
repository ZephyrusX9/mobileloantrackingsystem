package com.example.loanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    public EditText oldpass, newpass, confirmpass;
    public Button changepasswordbtn;

    public Toast mToast = null;

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldpass = findViewById(R.id.old_password);
        newpass = findViewById(R.id.newpassword);
        confirmpass = findViewById(R.id.confirm_password);

        changepasswordbtn = findViewById(R.id.changepasswordbtn);
        progressBar = findViewById(R.id.progressbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.right_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_list:
                Intent intent = new Intent(this, StudentList.class);
                startActivity(intent);
                return true;
            case R.id.item_form:
                startActivity(new Intent(this, ApplicationForm.class));
                return true;
            case R.id.item_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                displayToast("Goodbye :)");
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

    }

    public void changePassword(View view){
        String old = oldpass.getText().toString().trim();
        String newp = newpass.getText().toString().trim();
        String conpass = confirmpass.getText().toString().trim();

        if(old.isEmpty()){
            oldpass.setError("This is required");
            oldpass.requestFocus();
            return;
        }

        if(newp.isEmpty()){
            newpass.setError("This is required");
            newpass.requestFocus();
            return;
        }

        if(conpass.isEmpty()){
            confirmpass.setError("This is required");
            confirmpass.requestFocus();
            return;
        }

        if(!newp.equals(conpass)){
            oldpass.setError("Fields are not same plase check credentials");
            oldpass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        FirebaseAuth mauth = FirebaseAuth.getInstance();

        FirebaseUser user = mauth.getCurrentUser();
        String email = user.getEmail();

        AuthCredential credential = EmailAuthProvider.getCredential(email, old);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(newp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                displayToast("Successfully Change Password");
                                resetFields();
                                progressBar.setVisibility(View.GONE);
                            }else{
                                displayToast("Failed to Change password");
                                resetFields();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });



    }

    public void resetFields(){
        oldpass.getText().clear();
        newpass.getText().clear();
        confirmpass.getText().clear();
    }

    public void displayToast(String message){
        if(mToast!=null){
            mToast.cancel();
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            mToast.show();
        }else if(mToast==null){
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

}