package com.android.firebasedemo.myrail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {

    public static final int LOG_IN_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openHomeScreen();
            }
        }, 1500);
    }

    private void openHomeScreen() {
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            openComplaintsListScreen();
        } else {
            startActivityForResult(new Intent(this, PhoneAuthActivity.class), LOG_IN_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOG_IN_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(this, "Log in success", Toast.LENGTH_SHORT).show();
            openComplaintsListScreen();
        } else {
            Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void openComplaintsListScreen() {
        Intent intent = new Intent(this, ListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
