package com.example.shecure;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class EditProfileActivity extends AppCompatActivity {

    EditText etName, etEmail, etPhone;
    MaterialButton btnSave;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);

        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);

        // Load saved data
        etName.setText(sharedPreferences.getString("name", ""));
        etEmail.setText(sharedPreferences.getString("email", ""));
        etPhone.setText(sharedPreferences.getString("phone", ""));

        btnSave.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            // 🔥 Validation
            if (TextUtils.isEmpty(name)) {
                etName.setError("Enter Name");
                return;
            }

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Enter Email");
                return;
            }

            if (phone.length() != 10) {
                etPhone.setError("Enter valid 10-digit phone");
                return;
            }

            // Save data
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.putString("email", email);
            editor.putString("phone", phone);
            editor.apply();

            Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();

            // Navigate back
            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });
    }
}