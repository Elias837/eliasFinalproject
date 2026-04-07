package soraka.ash.eliasfinalproject;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import soraka.ash.eliasfinalproject.models.MyUser;

public class UserProfileActivity extends AppCompatActivity {

    private TextInputEditText etProfileName, etProfileEmail;
    private MaterialButton btnUpdateProfile;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = findViewById(R.id.profileToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        etProfileName = findViewById(R.id.etProfileName);
        etProfileEmail = findViewById(R.id.etProfileEmail);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            userId = user.getUid();
            etProfileEmail.setText(user.getEmail());
            loadUserProfile();
        }

        btnUpdateProfile.setOnClickListener(v -> updateProfile());
    }

    private void loadUserProfile() {
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MyUser myUser = snapshot.getValue(MyUser.class);
                if (myUser != null) {
                    etProfileName.setText(myUser.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile() {
        String name = etProfileName.getText().toString().trim();
        if (name.isEmpty()) {
            etProfileName.setError("Name is required");
            return;
        }

        mDatabase.child("users").child(userId).child("name").setValue(name)
                .addOnSuccessListener(aVoid -> Toast.makeText(UserProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(UserProfileActivity.this, "Update failed", Toast.LENGTH_SHORT).show());
    }
}
