package com.example.myapplication1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_PHONE = "phoneNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user is already logged in
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedPhone = preferences.getString(KEY_PHONE, null);
        if (savedPhone == null) {
            // No user logged in, stay in HomeActivity for login/registration
            setContentView(R.layout.activity_home);
        } else {
            // User is already logged in, go to MainActivity
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        // Find the Spinner by ID
        Spinner stateSpinner = findViewById(R.id.state_spinner);

        // List of Indian States
        String[] indianStates = {
                "Select State", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar",
                "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh",
                "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra",
                "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha",
                "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu",
                "Telangana", "Tripura", "Uttar Pradesh",
                "Uttarakhand", "West Bengal"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, indianStates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);

        // Set Spinner item selection listener
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String selectedState = indianStates[position];
                    Toast.makeText(HomeActivity.this,
                            "Selected State: " + selectedState, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> saveUserData());
    }

    private void saveUserData() {
        EditText nameInput = findViewById(R.id.name_input);
        EditText phoneInput = findViewById(R.id.phone_number_input);
        Spinner stateSpinner = findViewById(R.id.state_spinner);

        String name = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String state = stateSpinner.getSelectedItem().toString();

        if (phone.length() != 10) {
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> user = new HashMap<>();
        user.put("fullName", name);
        user.put("phoneNumber", phone);
        user.put("state", state);

        db.collection("users").document(phone)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    // Save phone number in SharedPreferences for session persistence
                    SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString(KEY_PHONE, phone);
                    editor.apply();

                    Toast.makeText(HomeActivity.this, "Data saved!", Toast.LENGTH_SHORT).show();

                    // Navigate to HomeActivity
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close MainActivity
                })
                .addOnFailureListener(e ->
                        Toast.makeText(HomeActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}