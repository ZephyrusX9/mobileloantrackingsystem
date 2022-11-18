package com.example.loanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ApplicationForm extends AppCompatActivity implements View.OnClickListener {

    public Toast mToast = null;
    public EditText mname, id_no, loan, contact, email, fblink;
    public Button submitbtn, resetbtn;

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form);
        this.setTitle("Application Form");
        mname = findViewById(R.id.name);
        id_no = findViewById(R.id.id_no);
        loan = findViewById(R.id.loan);
        contact = findViewById(R.id.phone);
        email = findViewById(R.id.email_address);
        fblink = findViewById(R.id.fb_link);

        submitbtn = findViewById(R.id.submitbtn);
        submitbtn.setOnClickListener(this);

        resetbtn = findViewById(R.id.resetbtn);
        resetbtn.setOnClickListener(this);

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


            case R.id.item_user:
                startActivity(new Intent(this, ProfileActivity.class));
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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.submitbtn:
                applicationSubmit();
                break;
            case R.id.resetbtn:
                applicationReset();
                break;
        }
    }

    public void applicationSubmit(){

        String data_name = mname.getText().toString().trim();
        String data_id_no = id_no.getText().toString().trim();
        String data_loan = loan.getText().toString().trim();
        String data_contact = contact.getText().toString().trim();
        String data_email = email.getText().toString().trim();
        String data_fblink = fblink.getText().toString().trim();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        if(data_name.isEmpty()){
            mname.setError("This is Required");
            mname.requestFocus();
            return;
        }

        if(data_id_no.isEmpty()){
            id_no.setError("This is Required");
            id_no.requestFocus();
            return;
        }

        if(data_loan.isEmpty()){
            loan.setError("This is Required");
            loan.requestFocus();
            return;
        }

        if(data_contact.isEmpty()){
            contact.setError("This is Required");
            contact.requestFocus();
            return;
        }

        if(data_email.isEmpty()){
            email.setError("This is Required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(data_email).matches()){
            email.setError("Please provide valid email address");
            email.requestFocus();
            return;
        }

        if(data_fblink.isEmpty()){
            fblink.setError("This is Required");
            fblink.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newForm = db.collection("Forms").document(uid).collection("AcountForms").document();


        ApplicationFormHelper form = new ApplicationFormHelper();
        form.setMname(data_name);
        form.setId_no(data_id_no);
        form.setLoan(data_loan);
        form.setContact(data_contact);
        form.setEmail(data_email);
        form.setFblink(data_fblink);

        newForm.set(form).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    displayToast("Successfully Added to Firestore");
                    progressBar.setVisibility(View.GONE);
                    applicationReset();

                }else{
                    displayToast("Problem Has occured");
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }

    public void applicationReset(){
        mname.getText().clear();
        id_no.getText().clear();
        loan.getText().clear();
        contact.getText().clear();
        email.getText().clear();
        fblink.getText().clear();

        displayToast("Content Cleared");
    }
}