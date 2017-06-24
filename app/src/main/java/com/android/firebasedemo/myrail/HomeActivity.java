package com.android.firebasedemo.myrail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    public static final int LOG_IN_REQUEST=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this,FileComplaintActivity.class));
        } else {
            startActivityForResult(new Intent(this, PhoneAuthActivity.class),LOG_IN_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOG_IN_REQUEST && resultCode == RESULT_OK){
            Toast.makeText(this, "Log in success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,FileComplaintActivity.class));
        }
        else{
            Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show();
        }
    }
}
