package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        Button btnLogout = findViewById(R.id.btnLogout);

        if (btnEditProfile != null) {
            btnEditProfile.setOnClickListener(v -> {
                startActivity(new Intent(SettingsActivity.this, UserProfileActivity.class));
            });
        }

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                mAuth.signOut();
                // Changed SignInActivity.class to Login.class to fix compilation error
                Intent intent = new Intent(SettingsActivity.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
    }
}
