package com.example.shecure;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {

    EditText etName1, etNumber1, etName2, etNumber2, etName3, etNumber3;
    MaterialButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        etName1 = findViewById(R.id.etName1);
        etNumber1 = findViewById(R.id.etNumber1);

        etName2 = findViewById(R.id.etName2);
        etNumber2 = findViewById(R.id.etNumber2);

        etName3 = findViewById(R.id.etName3);
        etNumber3 = findViewById(R.id.etNumber3);

        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> saveContacts());
    }

    private void saveContacts() {

        List<ContactManager.Contact> list = new ArrayList<>();

        if (!TextUtils.isEmpty(etName1.getText()) && !TextUtils.isEmpty(etNumber1.getText()))
            list.add(new ContactManager.Contact(etName1.getText().toString(), etNumber1.getText().toString()));

        if (!TextUtils.isEmpty(etName2.getText()) && !TextUtils.isEmpty(etNumber2.getText()))
            list.add(new ContactManager.Contact(etName2.getText().toString(), etNumber2.getText().toString()));

        if (!TextUtils.isEmpty(etName3.getText()) && !TextUtils.isEmpty(etNumber3.getText()))
            list.add(new ContactManager.Contact(etName3.getText().toString(), etNumber3.getText().toString()));

        if (list.isEmpty()) {
            Toast.makeText(this, "Enter at least one contact", Toast.LENGTH_SHORT).show();
            return;
        }

        ContactManager.saveContacts(this, list);

        setResult(RESULT_OK); // ✅ IMPORTANT FIX
        finish();
    }
}