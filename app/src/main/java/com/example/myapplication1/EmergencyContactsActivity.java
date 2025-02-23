package com.example.myapplication1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class EmergencyContactsActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CALL_PHONE = 1;
    private String selectedPhoneNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        // Create the list of contacts
        ArrayList<EmergencyContact> contacts = new ArrayList<>();
        contacts.add(new EmergencyContact("NDRF", "+919422853174"));
        contacts.add(new EmergencyContact("SDRF", "+918208784113"));
        contacts.add(new EmergencyContact("Police", "100"));
        contacts.add(new EmergencyContact("Fire Brigade", "101"));

        // Prepare the contact names for the dialog
        String[] contactNames = new String[contacts.size()];
        for (int i = 0; i < contacts.size(); i++) {
            contactNames[i] = contacts.get(i).getName();
        }

        // Create and show the AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Emergency Contacts")
                .setItems(contactNames, (dialog, which) -> {
                    // Get the selected contact's phone number
                    selectedPhoneNumber = contacts.get(which).getPhoneNumber();
                    Log.d("CALL_DEBUG", "Selected number: " + selectedPhoneNumber);

                    if (selectedPhoneNumber == null || selectedPhoneNumber.trim().isEmpty()) {
                        Toast.makeText(this, "❌ Invalid phone number!", Toast.LENGTH_SHORT).show();
                    } else {
                        checkCallPermission();
                    }
                })
                .setOnDismissListener(dialog -> finish()) // 💡 Finish the activity when the dialog is dismissed
                .setCancelable(true)
                .show();
    }

    private void checkCallPermission() {
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        Log.d("CALL_DEBUG", "Permission status: " + permissionStatus);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            makeDirectCall();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSION_REQUEST_CALL_PHONE);
        }
    }

    private void makeDirectCall() {
        if (selectedPhoneNumber == null || selectedPhoneNumber.trim().isEmpty()) {
            Toast.makeText(this, "❌ Invalid phone number!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.e("CALL_DEBUG", "Call permission not granted yet!");
            return;
        }

        try {
            Log.d("CALL_DEBUG", "Placing call to: " + selectedPhoneNumber);
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + selectedPhoneNumber));
            startActivity(callIntent);  // ✅ Direct call placement

            // Close the current activity and return to the previous screen (main activity)
            finish();  // This will close EmergencyContactsActivity and return to the previous activity
        } catch (Exception e) {
            Log.e("CALL_ERROR", "Call failed: " + e.getMessage());
            Toast.makeText(this, "❌ Call failed! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // ✅ Automatically calls after permission granted
                makeDirectCall();
            } else {
                Toast.makeText(this, "❌ Call permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
