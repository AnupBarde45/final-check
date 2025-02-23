package com.example.myapplication1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NotificationActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "NotificationPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ListView listView = findViewById(R.id.notification_list);
        List<String> notifications = getSavedNotifications();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notifications);
        listView.setAdapter(adapter);
    }

    private List<String> getSavedNotifications() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> notificationsSet = prefs.getStringSet("notifications", null);

        if (notificationsSet != null) {
            return new ArrayList<>(notificationsSet);
        } else {
            List<String> emptyList = new ArrayList<>();
            emptyList.add("No notifications received yet.");
            return emptyList;
        }
    }
}