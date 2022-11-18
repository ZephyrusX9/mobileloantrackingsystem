package com.example.loanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public Toast mToast = null;
    public TextView username, email;
    public Button changepasswordbtn;

    public DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email_address);

        changepasswordbtn = findViewById(R.id.changepasswordbtn);
        changepasswordbtn.setOnClickListener(this);


        ArrayList<String> list = new ArrayList<>();

        FirebaseAuth  mauth = FirebaseAuth.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").getRef().child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                username.setText(user.username);
                email.setText(user.email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("All Database Data", list.toString());


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
            case R.id.item_passwrod:
                startActivity(new Intent(this, ChangePassword.class));
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

    public void navigateChangePassword(){
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.changepasswordbtn:
                navigateChangePassword();
        }
    }
}