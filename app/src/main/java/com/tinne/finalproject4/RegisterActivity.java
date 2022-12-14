package com.tinne.finalproject4;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.tinne.finalproject4.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding registerBinding;
    private FirebaseAuth mAuth;
    private String email, password, cPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        // Validasi Email tidak kosong
        registerBinding.edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    registerBinding.edEmail.setError("Email tidak boleh kosong");
                } else {
                    registerBinding.edEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Validasi Password tidak kosong
        registerBinding.edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    registerBinding.edPassword.setError("Password tidak boleh kosong");
                } else {
                    registerBinding.edPassword.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Validasi Password dan Confirm Password sama
        registerBinding.edConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String cPassword = registerBinding.edPassword.getText().toString().trim();
                if (!s.toString().trim().equals(cPassword)) {
                    registerBinding.edConfirmPassword.setError("Password tidak cocok");
                } else {
                    registerBinding.edConfirmPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        registerBinding.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

        // Validasi di bagian button
        registerBinding.btnSignUp.setOnClickListener(v -> {
            email = registerBinding.edEmail.getText().toString();
            password = registerBinding.edPassword.getText().toString();
            cPassword = registerBinding.edPassword.getText().toString();

            if (email.isEmpty()) {
                registerBinding.edEmail.setError("Email tidak boleh kosong");
            } else if (password.isEmpty()) {
                registerBinding.edPassword.setError("Password tidak boleh kosong");
            } else if (cPassword.isEmpty()) {
                registerBinding.edConfirmPassword.setError("Masukkan ulang password");
            } else if (registerBinding.edEmail.getError() != null || registerBinding.edPassword.getError() != null || registerBinding.edConfirmPassword.getError() != null) {

            } else {
                signUp(email, cPassword);
            }

        });
    }

    // Method untuk mendaftarkan ke firebase
    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("email password", "CreateUserWithEmail: Success");
                        startActivity(new Intent(this, MainActivity.class));
                    } else {
                        Log.w("emailpassword", task.getException());
                        Toast.makeText(this, "Pendaftaran Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}