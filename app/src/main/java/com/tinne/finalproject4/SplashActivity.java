package com.tinne.finalproject4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tinne.finalproject4.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivitySplashBinding splashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(splashBinding.getRoot());

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUser();
    }

    private void checkUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        SharedPreferences mPrefs = getSharedPreferences("lastIntent",0);
//        String lastIntent = mPrefs.getString("intent","");
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (currentUser == null) {
                startActivity(new Intent(this,LoginActivity.class));
                finish();
            } else {
                startActivity(new Intent(this,MainActivity.class));
                finish();
            }
        },3000);
    }
}