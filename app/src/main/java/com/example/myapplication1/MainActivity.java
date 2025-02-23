package com.example.myapplication1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_PHONE = "phoneNumber";
    private FirebaseFirestore db;
    private TextView userName, userPhone, userState;

    private static final int PERMISSION_REQUEST_SEND_SMS = 1;
    private static final int PERMISSION_REQUEST_LOCATION = 2;
    private final String[] phoneNumbers = {
            "+918208784113",
            "+919422853174",
            "+919552606179",
            "+917588554607"
    };

    private LocationManager locationManager;
    private String currentLocation = null;
    private LocationListener locationListener;
    private boolean isLocationReceived = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        // Find Views
        userName = findViewById(R.id.user_name);
        userPhone = findViewById(R.id.user_phone);
        userState = findViewById(R.id.user_state);
        Button logoutButton = findViewById(R.id.logout_button);

        // Get stored phone number from SharedPreferences
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String phone = preferences.getString(KEY_PHONE, null);

        if (phone != null) {
            fetchUserData(phone);
        } else {
            Toast.makeText(this, "No user logged in!", Toast.LENGTH_SHORT).show();
            logout();
        }

        logoutButton.setOnClickListener(v -> logout());


        ImageButton sosButton = findViewById(R.id.sosButton);
        Button emergencyContactsButton = findViewById(R.id.emergencyContactsButton);
        Button videoCallButton = findViewById(R.id.videoCallButton);
        Button btnGuidelines = findViewById(R.id.btnGuidelines);
        sosButton.setOnClickListener(v -> showConfirmationDialog());

        emergencyContactsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EmergencyContactsActivity.class);
            startActivity(intent);
        });

        videoCallButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VideoCallActivity.class);
            startActivity(intent);
        });

        btnGuidelines.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GuidelinesActivity.class);
            startActivity(intent);
        });

    }


    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Send SOS")
                .setMessage("Are you sure you want to send an SOS alert?")
                .setPositiveButton("Yes", (dialog, which) -> checkLocationEnabled())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }

    private void fetchUserData(String phone) {
        db.collection("users").document(phone).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("fullName");
                        String state = documentSnapshot.getString("state");

                        userName.setText("Name: " + name);
                        userPhone.setText("Phone: " + phone);
                        userState.setText("State: " + state);
                    } else {
                        Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
                        logout();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error fetching data!", Toast.LENGTH_SHORT).show());
    }


    private void checkLocationEnabled() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showLocationEnableDialog();
        } else {
            requestLocationAndSendSOS();
        }
    }

    private void showLocationEnableDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Enable Location")
                .setMessage("Your location is turned off. Please enable it for SOS to work.")
                .setPositiveButton("Go to Settings", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    Toast.makeText(this, "❌ SOS not sent. Location required.", Toast.LENGTH_SHORT).show();
                })
                .setCancelable(false)
                .show();
    }

    private void requestLocationAndSendSOS() {
        isLocationReceived = false;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getLocationAndSendSMS();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_LOCATION);
        }
    }

    private void getLocationAndSendSMS() {
        if (locationManager == null) {
            Toast.makeText(this, "❌ Location unavailable. SOS not sent.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "❌ Location permission denied. SOS not sent.", Toast.LENGTH_SHORT).show();
            return;
        }

        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null) {
            updateAndSendLocation(lastKnownLocation);
            return;
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                if (!isLocationReceived) {
                    isLocationReceived = true;
                    updateAndSendLocation(location);
                }
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!isLocationReceived) {
                Toast.makeText(this, "❌ Location not found. SOS not sent.", Toast.LENGTH_SHORT).show();
                removeLocationUpdatesSafely();
            }
        }, 10000);
    }

    private void updateAndSendLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        currentLocation = "⚠️ SOS Alert! I need help. Location: \nhttps://maps.google.com/?q=" + latitude + "," + longitude;
        removeLocationUpdatesSafely();
        sendSOSMessage();
    }

    private void removeLocationUpdatesSafely() {
        if (locationManager != null && locationListener != null) {
            try {
                locationManager.removeUpdates(locationListener);
                Log.d("LocationUpdate", "Location updates removed successfully.");
            } catch (Exception e) {
                Log.e("LocationUpdate", "Error removing location updates: " + e.getMessage());
            }
        }
    }

    private void sendSOSMessage() {
        if (currentLocation == null) {
            Toast.makeText(this, "❌ Location unavailable. SOS not sent.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            for (String phoneNumber : phoneNumbers) {
                try {
                    ArrayList<String> parts = smsManager.divideMessage(currentLocation);
                    smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
                    Toast.makeText(this, "✅ SOS sent to: " + phoneNumber, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "❌ Failed to send SOS to: " + phoneNumber, Toast.LENGTH_SHORT).show();
                    Log.e("SMS", "Error sending SMS: " + e.getMessage());
                }
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_REQUEST_SEND_SMS);
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocationAndSendSMS();
            } else {
                Toast.makeText(this, "❌ Location permission denied! SOS not sent.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PERMISSION_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSOSMessage();
            } else {
                Toast.makeText(this, "❌ SMS permission denied! Cannot send SOS.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
