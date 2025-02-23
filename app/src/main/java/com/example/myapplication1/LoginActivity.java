package com.example.myapplication1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "FCM";
    private static final String PREFS_NAME = "UserPreferences";
    private static final String PREF_STATE = "user_state";

    private FirebaseFirestore db;
    // private String userId = "DhxVYBrly2hCGXKfvzpt"; // 🔹 Hardcoded User ID (Commented Out)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Fetch user’s state from Firestore dynamically
        fetchUserState();
    }

    private void fetchUserState() {
        // 🔹 Get the currently logged-in user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid(); // 🔹 Retrieve the user's unique ID
            fetchUserStateFromFirestore(userId);
        } else {
            Log.w(TAG, "No logged-in user found.");
        }
    }

    private void fetchUserStateFromFirestore(String userId) {
        DocumentReference userRef = db.collection("users").document(userId);

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String userState = documentSnapshot.getString("state");

                if (userState != null && !userState.isEmpty()) {
                    saveStateToPreferences(userState);
                    subscribeToStateTopic(userState);
                    Log.d(TAG, "User subscribed to state: " + userState);
                } else {
                    Log.w(TAG, "User state is empty or null.");
                }
            } else {
                Log.w(TAG, "User document does not exist.");
            }
        }).addOnFailureListener(e -> Log.e(TAG, "Error fetching user state: ", e));
    }

    private void saveStateToPreferences(@NonNull String state) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_STATE, state);
        editor.apply();
        Log.d(TAG, "State saved to preferences: " + state);
    }

    private void subscribeToStateTopic(@NonNull String state) {
        FirebaseMessaging.getInstance().subscribeToTopic(state)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Subscribed to " + state + " alerts", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Successfully subscribed to topic: " + state);
                    } else {
                        Log.e(TAG, "Subscription to topic failed", task.getException());
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_notifications) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}