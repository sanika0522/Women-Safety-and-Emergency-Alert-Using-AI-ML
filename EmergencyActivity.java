package com.example.shecure;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

public class EmergencyActivity extends AppCompatActivity {

    MaterialButton btnSOS, btnGoHome;
    private static final int SMS_PERMISSION_CODE = 101;

    // ⚠️ Replace with real numbers
    String familyNumber = "9876543210";
    String policeNumber = "100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        btnSOS = findViewById(R.id.btnSOS);
        btnGoHome = findViewById(R.id.btnGoHome);

        btnSOS.setOnClickListener(v -> checkPermissionAndSend());

        btnGoHome.setOnClickListener(v -> {
            startActivity(new Intent(EmergencyActivity.this, HomeActivity.class));
            finish();
        });
    }

    private void checkPermissionAndSend() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    SMS_PERMISSION_CODE);
        } else {
            sendSOSMessage();
        }
    }

    private void sendSOSMessage() {

        if (familyNumber.isEmpty()) {
            Toast.makeText(this, "Family number not set", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String message = "🚨 SOS Alert! I need help. Please check on me immediately.";

            SmsManager smsManager;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                smsManager = getSystemService(SmsManager.class);
            } else {
                smsManager = SmsManager.getDefault();
            }

            smsManager.sendTextMessage(familyNumber, null, message, null, null);
            smsManager.sendTextMessage(policeNumber, null, message, null, null);

            Toast.makeText(this, "SOS Sent Successfully", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SOS", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    // Permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                sendSOSMessage();

            } else {
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}