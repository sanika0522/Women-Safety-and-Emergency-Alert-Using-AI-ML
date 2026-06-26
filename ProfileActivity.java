package com.example.shecure;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvPhone;
    MaterialButton btnEdit, btnHome;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);

        btnEdit = findViewById(R.id.btnEdit);
        btnHome = findViewById(R.id.btnHome);

        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);

        loadProfile();

        btnEdit.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class))
        );

        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
            finish();
        });
    }

    private void loadProfile() {

        String name = sharedPreferences.getString("name", "Not set");
        String email = sharedPreferences.getString("email", "Not set");
        String phone = sharedPreferences.getString("phone", "Not set");

        tvName.setText("👤 Name: " + name);
        tvEmail.setText("📧 Email: " + email);
        tvPhone.setText("📱 Phone: " + phone);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfile(); // refresh after edit
    }
}