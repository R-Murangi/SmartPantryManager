package za.co.student.smartpantry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Switch expirySwitch = findViewById(R.id.expirySwitch);
        Button back = findViewById(R.id.backButton);
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        expirySwitch.setChecked(prefs.getBoolean("expiry_reminder", false));
        expirySwitch.setOnCheckedChangeListener((button, checked) ->
                prefs.edit().putBoolean("expiry_reminder", checked).apply());
        back.setOnClickListener(v -> finish());
    }
}
