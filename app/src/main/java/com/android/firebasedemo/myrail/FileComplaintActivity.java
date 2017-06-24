package com.android.firebasedemo.myrail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.firebasedemo.myrail.model.Complain;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FileComplaintActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Spinner dropdown;
    String[] items;
    EditText tvPnr, tvDescription;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_complaint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("File A Complaint");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();
    }

    private void initViews() {
        tvPnr = (EditText) findViewById(R.id.et_pnr);
        tvDescription = (EditText) findViewById(R.id.user_complaint_et);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dropdown = (Spinner) findViewById(R.id.complaint_category);
        items = new String[]{"Cleanliness", "Catering", "Security", "Staff", "Coach Defect"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    private void fileComplain(String category, String pnr, String complainText) {
        String uid = mDatabase.child("complaints").push().getKey();
        Complain complain = new Complain();
        complain.setCategory(category);
        complain.setComplain(complainText);
        complain.setPnr(pnr);
        complain.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabase.child("complaints").child(uid).setValue(complain);
        Toast.makeText(this, "Complain registered", Toast.LENGTH_SHORT).show();
        tvPnr.setText("");
        tvDescription.setText("");
        dropdown.setSelection(0);
        startActivity(new Intent(this,ListActivity.class));
    }

    public void onClick(View v) {
        String category = items[dropdown.getSelectedItemPosition()];
        String pnr = tvPnr.getText().toString();
        String complain = tvDescription.getText().toString();
        fileComplain(category, pnr, complain);
        trackAnalytics(category,pnr,complain);
    }

    private void trackAnalytics(String category, String pnr, String complain) {
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        bundle.putString("description", complain);
        bundle.putString("pnr", pnr);
        mFirebaseAnalytics.logEvent("complaint_registered", bundle);
    }
}
