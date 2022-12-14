package com.tinne.finalproject4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.tinne.finalproject4.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding loginBinding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        setContentView(loginBinding.getRoot());

        loginBinding.btnSignUp.setOnClickListener(v -> {
            Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intentRegister);
        });

        // Validasi login
        loginBinding.btnLogin.setOnClickListener(v -> {
            String email = loginBinding.edEmail.getText().toString().trim();
            String password = loginBinding.edPassword.getText().toString().trim();
            if (email.isEmpty()) {
                loginBinding.edEmail.setError("Email tidak boleh kososng");
                Toast.makeText(this, "Email Kosong", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                loginBinding.edPassword.setError("Password tidak boleh kosong");
                Toast.makeText(this, "Password Kosong", Toast.LENGTH_SHORT).show();
            } else if (loginBinding.edEmail.getError() != null || loginBinding.edPassword.getError() != null) {

            } else {
                logIn(email,password);
            }
        });
    }

    // Method logIn dari Firebase
    private void logIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Log.w("emailPassword",task.getException());
                                Toast.makeText(this, "Login failed!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
    }
}