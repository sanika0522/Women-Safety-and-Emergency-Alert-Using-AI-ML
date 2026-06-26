package com.example.shecure;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class ContactManager {

    private static final String PREF_NAME = "SOS_PREF";
    private static final String KEY_CONTACTS = "contacts";

    public static class Contact {
        public String name;
        public String number;

        public Contact(String name, String number) {
            this.name = name;
            this.number = number;
        }
    }

    public static void saveContacts(Context context, List<Contact> contacts) {

        StringBuilder data = new StringBuilder();

        for (Contact c : contacts) {
            data.append(c.name).append(":").append(c.number).append(",");
        }

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_CONTACTS, data.toString()).apply();
    }

    public static List<Contact> getContacts(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String data = prefs.getString(KEY_CONTACTS, "");

        List<Contact> list = new ArrayList<>();

        if (data == null || data.isEmpty()) return list;

        String[] entries = data.split(",");

        for (String entry : entries) {
            if (entry.contains(":")) {
                String[] parts = entry.split(":");
                list.add(new Contact(parts[0], parts[1]));
            }
        }

        return list;
    }
}