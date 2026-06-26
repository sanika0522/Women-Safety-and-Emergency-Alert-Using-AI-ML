package com.example.shecure;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    ListView listNews;
    MaterialButton btnHome;

    ArrayList<String> newsList = new ArrayList<>();
    NewsAdapter adapter;

    String RSS_URL = "https://www.indiatoday.in/rss/home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        listNews = findViewById(R.id.listNews);
        btnHome = findViewById(R.id.btnHome);

        adapter = new NewsAdapter(this, newsList);
        listNews.setAdapter(adapter);

        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(NewsActivity.this, HomeActivity.class));
            finish();
        });

        fetchRSSNews();
    }

    private void fetchRSSNews() {

        new Thread(() -> {
            HttpURLConnection connection = null;

            try {
                URL url = new URL(RSS_URL);
                connection = (HttpURLConnection) url.openConnection();

                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                InputStream inputStream = connection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(inputStream, null);

                boolean insideItem = false;
                String title;

                newsList.clear();

                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    String tagName = parser.getName();

                    if (eventType == XmlPullParser.START_TAG) {

                        if ("item".equalsIgnoreCase(tagName)) {
                            insideItem = true;
                        }
                        else if (insideItem && "title".equalsIgnoreCase(tagName)) {
                            title = parser.nextText();
                            if (title != null && !title.trim().isEmpty()) {
                                newsList.add(title);
                            }
                        }
                    }
                    else if (eventType == XmlPullParser.END_TAG) {

                        if ("item".equalsIgnoreCase(tagName)) {
                            insideItem = false;
                        }
                    }

                    eventType = parser.next();
                }

                runOnUiThread(() -> adapter.notifyDataSetChanged());

            } catch (Exception e) {
                Log.e("RSS_ERROR", e.toString());

                runOnUiThread(() ->
                        Toast.makeText(NewsActivity.this,
                                "Failed to load news. Check internet.",
                                Toast.LENGTH_LONG).show()
                );

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }
}