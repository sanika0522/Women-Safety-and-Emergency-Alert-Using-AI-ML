package com.example.shecure;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class SOSActivity extends AppCompatActivity implements SensorEventListener {

    private MaterialButton btnSOS, btnAddContact;
    private TextView tvContacts;

    private List<ContactManager.Contact> emergencyContacts;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastShakeTime = 0;

    private ActivityResultLauncher<Intent> addContactLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        btnSOS = findViewById(R.id.btnSOS);
        btnAddContact = findViewById(R.id.btnAddContact);
        tvContacts = findViewById(R.id.tvContacts);

        displayContacts();

        // ✅ Activity result launcher
        addContactLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        displayContacts();
                    }
                }
        );

        btnSOS.setOnClickListener(v -> checkPermissions());

        btnAddContact.setOnClickListener(v -> {
            addContactLauncher.launch(new Intent(this, AddContactActivity.class));
        });

        // Shake setup
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void displayContacts() {
        emergencyContacts = ContactManager.getContacts(this);

        if (emergencyContacts.isEmpty()) {
            tvContacts.setText("No contacts added");
            return;
        }

        StringBuilder sb = new StringBuilder("Saved Contacts:\n\n");

        for (ContactManager.Contact c : emergencyContacts) {
            sb.append("• ").append(c.name).append(" - ").append(c.number).append("\n");
        }

        tvContacts.setText(sb.toString());
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, 1);

        } else {
            sendSOS();
        }
    }

    private void sendSOS() {
        if (emergencyContacts.isEmpty()) {
            Toast.makeText(this, "No contacts!", Toast.LENGTH_SHORT).show();
            return;
        }

        SmsManager sms = SmsManager.getDefault();

        for (ContactManager.Contact c : emergencyContacts) {
            sms.sendTextMessage(c.number, null, "🚨 SOS! Help me!", null, null);
        }

        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:112")));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float acc = (float) Math.sqrt(
                event.values[0]*event.values[0] +
                        event.values[1]*event.values[1] +
                        event.values[2]*event.values[2]);

        if (acc > 12 && System.currentTimeMillis() - lastShakeTime > 2000) {
            lastShakeTime = System.currentTimeMillis();
            checkPermissions();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}