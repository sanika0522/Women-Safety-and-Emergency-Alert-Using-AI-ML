package com.example.shecure;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class HomeActivity extends AppCompatActivity {

    MaterialButton btnSOS, btnSafeRoute, btnProfile, btnLogout;
    MaterialButton btnEmergency, btnNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnSOS = findViewById(R.id.btnSOS);
        btnSafeRoute = findViewById(R.id.btnSafeRoute);
        btnProfile = findViewById(R.id.btnProfile);
        btnLogout = findViewById(R.id.btnLogout);
        btnNews = findViewById(R.id.btnNews);

        btnSOS.setOnClickListener(v -> openActivity(SOSActivity.class));
        btnSafeRoute.setOnClickListener(v -> openActivity(SafeRouteActivity.class));
        btnProfile.setOnClickListener(v -> openActivity(ProfileActivity.class));
        btnNews.setOnClickListener(v -> openActivity(NewsActivity.class));

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void openActivity(Class<?> cls) {
        startActivity(new Intent(HomeActivity.this, cls));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}