package soraka.ash.eliasfinalproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import soraka.ash.eliasfinalproject.models.MyUser;

/**
 * Modern Add User Activity with Material Design
 * 
 * This activity demonstrates the teacher's code pattern with modern UI/UX:
 * 1. UI initialization with findViewById()
 * 2. Input validation and MyUser creation
 * 3. Firebase integration with proper error handling
 * 4. Modern Material Design components
 * 5. Smooth animations and user feedback
 */
public class AddUserActivity extends AppCompatActivity {
    
    // UI Components - Modern Material Design
    private TextInputEditText nameEditText, emailEditText;
    private TextInputLayout nameLayout, emailLayout;
    private MaterialButton addButton;
    private MaterialCardView mainCard;
    private CircularProgressIndicator progressBar;
    
    // Constants
    private static final String TAG = "AddUserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        initializeViews();
        setupClickListeners();
        setupAnimations();
    }

    /**
     * Initialize all UI components using findViewById()
     * This is the FIRST part of teacher's code pattern
     */
    private void initializeViews() {
        // Modern Material Design components
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        nameLayout = findViewById(R.id.nameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        addButton = findViewById(R.id.addButton);
        mainCard = findViewById(R.id.mainCard);
        progressBar = findViewById(R.id.progressBar);
        
        Log.d(TAG, "Views initialized successfully");
    }

    /**
     * Set up click listeners and user interactions
     * This is the SECOND part of teacher's code pattern
     */
    private void setupClickListeners() {
        addButton.setOnClickListener(v -> {
            // Get user input from EditText fields
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();

            // Validate input fields
            if (!name.isEmpty() && !email.isEmpty()) {
                // Input is valid - proceed with user creation
                if (isValidEmail(email)) {
                    createUserAndSave(name, email);
                } else {
                    emailLayout.setError("Please enter a valid email address");
                }
            } else {
                // Input validation failed - show errors
                Log.w(TAG, "الرجاء إدخال الاسم والبريد الإلكتروني.");
                
                if (name.isEmpty()) {
                    nameLayout.setError("Name is required");
                } else {
                    nameLayout.setError(null);
                }
                
                if (email.isEmpty()) {
                    emailLayout.setError("Email is required");
                } else {
                    emailLayout.setError(null);
                }
            }
        });

        // Clear errors when user starts typing
        nameEditText.setOnKeyListener((v, keyCode, event) -> {
            nameLayout.setError(null);
            return false;
        });

        emailEditText.setOnKeyListener((v, keyCode, event) -> {
            emailLayout.setError(null);
            return false;
        });
    }

    /**
     * Setup entrance animations for modern feel
     */
    private void setupAnimations() {
        // Start with card invisible
        mainCard.setAlpha(0f);
        mainCard.setScaleX(0.8f);
        mainCard.setScaleY(0.8f);

        // Animate card entrance
        mainCard.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(300)
            .setInterpolator(new android.view.animation.OvershootInterpolator())
            .start();
    }

    /**
     * Create MyUser object and save to Firebase
     * This combines object creation with Firebase operations
     */
    private void createUserAndSave(String name, String email) {
        // Show loading state
        showLoading(true);
        
        // Create MyUser object using your existing model class
        MyUser newUser = new MyUser(name, email);
        Log.d(TAG, "Created MyUser object: " + name + ", " + email);

        // Save user to Firebase
        saveUserToFirebase(newUser);
    }

    /**
     * Save user data to Firebase Realtime Database
     * Modern implementation with proper error handling and user feedback
     */
    private void saveUserToFirebase(MyUser user) {
        // Get Firebase database reference
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = database.child("users");
        
        // Create unique key for new user
        DatabaseReference newUserRef = usersRef.push();
        
        // Set user ID in the MyUser object
        user.setUserId(newUserRef.getKey());
        
        // Save user data to Firebase with modern callbacks
        newUserRef.setValue(user)
            .addOnSuccessListener(aVoid -> {
                // Success - update UI and clear fields
                Log.d(TAG, "User saved successfully: " + user.getUserId());
                handleSuccess();
            })
            .addOnFailureListener(e -> {
                // Error - show user feedback
                Log.e(TAG, "Error saving user: " + e.getMessage(), e);
                handleError("Failed to save user: " + e.getMessage());
            });
    }

    /**
     * Handle successful user creation
     * Clear fields and show success feedback
     */
    private void handleSuccess() {
        // Clear input fields (from teacher's example)
        nameEditText.setText("");
        emailEditText.setText("");
        
        // Clear any error states
        nameLayout.setError(null);
        emailLayout.setError(null);
        
        // Hide loading
        showLoading(false);
        
        // Show success message
        Toast.makeText(this, "User added successfully!", Toast.LENGTH_SHORT).show();
        
        // Animate success
        animateSuccess();
    }

    /**
     * Handle errors during user creation
     */
    private void handleError(String errorMessage) {
        showLoading(false);
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    /**
     * Show/hide loading state
     */
    private void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            addButton.setEnabled(false);
            addButton.setText("Adding...");
        } else {
            progressBar.setVisibility(View.GONE);
            addButton.setEnabled(true);
            addButton.setText("Add User");
        }
    }

    /**
     * Animate success state
     */
    private void animateSuccess() {
        mainCard.animate()
            .scaleX(1.05f)
            .scaleY(1.05f)
            .setDuration(100)
            .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mainCard.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start();
                }
            })
            .start();
    }

    /**
     * Simple email validation
     */
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
