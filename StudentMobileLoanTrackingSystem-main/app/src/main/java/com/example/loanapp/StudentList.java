package com.example.loanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity {


    public Toast mToast = null;
    public ListView student_list_view;
    public TextView number, name, loan;

    public List<DocumentSnapshot> snapshotsList = null;
    public ArrayList<String> idList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        student_list_view = findViewById(R.id.listview);

        number = findViewById(R.id.number);
        name = findViewById(R.id.name);
        loan = findViewById(R.id.loan);


        Student student1 = new Student("Example Name", "10000");
        Student student2 = new Student("Example Name", "10000");
        Student student3 = new Student("Example Name", "10000");
        Student student4 = new Student("Example Name", "10000");
        Student student5 = new Student("Example Name", "10000");

        ArrayList<Student> studentlist = new ArrayList<>();
        studentlist.add(student1);
        studentlist.add(student2);
        studentlist.add(student3);
        studentlist.add(student4);
        studentlist.add(student5);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("Forms").document(uid).collection("AcountForms").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            snapshotsList = task.getResult().getDocuments();
                            Log.d("Documents", snapshotsList.toString());
//                            for (DocumentSnapshot snapshot: snapshotsList){
//                            }

                            for(DocumentSnapshot s: snapshotsList){
                                idList.add(s.getId().toString());
                                Log.d("Document ID", s.getId().toString());
                            }

                            StudentListAdapter adapter = new StudentListAdapter(StudentList.this, R.layout.adapter_view_layout, snapshotsList);
                            student_list_view.setAdapter(adapter);

                            student_list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                                @Override
                                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    final int index = i;

                                    new AlertDialog.Builder(StudentList.this)
                                            .setIcon(android.R.drawable.ic_delete)
                                            .setTitle("Are you sure?")
                                            .setMessage("Do you want to delete this item?")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                    db.collection("Forms").document(FirebaseAuth.getInstance().getUid())
                                                            .collection("AcountForms")
                                                            .document(idList.get(index))
                                                            .delete()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        snapshotsList.remove(index);
                                                                        StudentListAdapter adapter = new StudentListAdapter(StudentList.this, R.layout.adapter_view_layout, snapshotsList);
                                                                        student_list_view.setAdapter(adapter);
                                                                        displayToast("Succesfully Deleted");
                                                                        adapter.notifyDataSetChanged();
                                                                    }else{
                                                                        displayToast("Failed to Deleted");
                                                                    }
                                                                }
                                                            });
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }).setNegativeButton("No", null)
                                            .show();
                                    return true;
                                }
                            });
                        }else{
                            displayToast("Documents not successful");
                        }
                    }
                });






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
            case R.id.item_form:
                Intent intent = new Intent(this, ApplicationForm.class);
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
}