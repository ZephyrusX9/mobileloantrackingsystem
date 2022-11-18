package com.example.loanapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends ArrayAdapter<DocumentSnapshot> {


    public Context mcontext;
    int mResource;


    public StudentListAdapter(@NonNull Context context, int resource, @NonNull List<DocumentSnapshot> objects) {
        super(context, resource, objects);
        mcontext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getString("mname");
        String loan = getItem(position).getString("loan");

        Student student = new Student(name, loan);

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvname = convertView.findViewById(R.id.name);
        TextView tvloan = convertView.findViewById(R.id.loan);
        TextView number = convertView.findViewById(R.id.number);

        number.setText(String.valueOf(position));
        tvname.setText(name);
        tvloan.setText(loan);

        return convertView;
    }
}
