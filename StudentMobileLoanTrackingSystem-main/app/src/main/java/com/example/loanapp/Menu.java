package com.example.loanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {


    public TextView id;
    public FirebaseDatabase mfirebaseDatabase;
    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    public DatabaseReference myRef;

    private String userID;
    private ListView listView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        id = findViewById(R.id.id_no);
        listView = findViewById(R.id.listview);

        mAuth = FirebaseAuth.getInstance();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mfirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d("FirebaseAuth", "Auth SignIn: " + user.getUid());
                    displayToast("Successfully Signed In with "+ user.getEmail());
                }else{
                    Log.d("FirebaseAuth", "Auth SignOut: " + user.getUid());
                    displayToast("Successfully Signed Out");
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showData(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void showData(DataSnapshot snapshot){
        for(DataSnapshot ds: snapshot.getChildren()){
            UserInformation info = new UserInformation();
            info.setId_no(ds.child(userID).getValue(UserInformation.class).getId_no());
            info.setEmail(ds.child(userID).getValue(UserInformation.class).getEmail());
            info.setUsername(ds.child(userID).getValue(UserInformation.class).getUsername());
            info.setPassword(ds.child(userID).getValue(UserInformation.class).getPassword());

            Log.d(null, "ShowData: id_no" + info.getId_no());
            Log.d(null, "ShowData: Email" + info.getEmail());
            Log.d(null, "ShowData: Username" + info.getUsername());
            Log.d(null, "ShowData: password" + info.getPassword());

            ArrayList<String> array = new ArrayList<String>();

            array.add(userID);
            array.add(info.getId_no());
            array.add(info.getEmail());
            array.add(info.getUsername());
            array.add(info.getPassword());

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,array);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void displayToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}