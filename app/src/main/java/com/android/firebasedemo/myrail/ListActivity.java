package com.android.firebasedemo.myrail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.firebasedemo.myrail.model.Complain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    ArrayList<String> arr;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("complaints");
        arr = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                collectPhoneNumbers((Map<String, Object>) dataSnapshot.getValue());
                adapter.notifyDataSetChanged();
                //makes the ListView realtime
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println(databaseError.toException());
                // ...
            }
        };
        mDatabase.addValueEventListener(listener);

        adapter = new ArrayAdapter<String>(this,
                                           android.R.layout.simple_list_item_1, android.R.id.text1, arr);

        ListView listView = (ListView) findViewById(R.id.lv_complain);
        listView.setAdapter(adapter);

    }

    private void collectPhoneNumbers(Map<String, Object> users) {

        ArrayList<Complain> phoneNumbers = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            arr.add("pnr -> " + singleUser.get("pnr") + "\n" +
                            "complain -> " + singleUser.get("complain") + "\n"+
                    "category -> "+ singleUser.get("category") +"\n"

            );


        }

    }

}
