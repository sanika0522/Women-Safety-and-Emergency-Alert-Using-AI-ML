package com.example.shecure;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    MaterialButton btnLogin, btnRegister;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // SharedPreferences (same name as RegisterActivity)
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        // Login button click
        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // 🔥 Validation
            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Enter email");
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Invalid email");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Enter password");
                return;
            }

            // 🔐 Get saved data
            String savedEmail = sharedPreferences.getString("email", "");
            String savedPassword = sharedPreferences.getString("password", "");

            // ✅ Check credentials
            if (email.equals(savedEmail) && password.equals(savedPassword)) {

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                // Go to Home screen
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            }
        });

        // Register button click
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}