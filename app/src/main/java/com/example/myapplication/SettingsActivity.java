package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private EditText etRadius;
    private Spinner spinnerSounds;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etRadius = findViewById(R.id.etRadius);
        spinnerSounds = findViewById(R.id.spinnerSounds);
        btnSave = findViewById(R.id.btnSave);

        // Load saved settings
        SharedPreferences prefs = getSharedPreferences("LocationAlertPrefs", MODE_PRIVATE);
        float radius = prefs.getFloat("radius", 100);
        String soundUri = prefs.getString("soundUri", "");

        etRadius.setText(String.valueOf(radius));

        // Populate sounds spinner
        List<String> sounds = new ArrayList<>();
        sounds.add("Default");
        sounds.add("Custom sound..."); // Option to select custom sound

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sounds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSounds.setAdapter(adapter);

        // Set saved sound selection
        if (!soundUri.isEmpty()) {
            spinnerSounds.setSelection(1);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
    }

    private void saveSettings() {
        float radius = Float.parseFloat(etRadius.getText().toString());

        SharedPreferences.Editor editor = getSharedPreferences("LocationAlertPrefs", MODE_PRIVATE).edit();
        editor.putFloat("radius", radius);

        // Save sound selection
        if (spinnerSounds.getSelectedItemPosition() == 1) {
            // Here you would implement custom sound selection
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            editor.putString("soundUri", defaultSoundUri.toString());
        } else {
            editor.putString("soundUri", "");
        }

        editor.apply();

        // Return to main activity
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}