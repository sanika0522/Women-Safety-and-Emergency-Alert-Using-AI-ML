package com.example.shecure;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    MaterialButton btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetStarted = findViewById(R.id.btnGetStarted);

        if (btnGetStarted != null) {
            btnGetStarted.setOnClickListener(v -> {

                Toast.makeText(MainActivity.this,
                        "Get Started Clicked!",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, SOSActivity.class);
                startActivity(intent);

                // Optional animation
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            });
        }
    }
}