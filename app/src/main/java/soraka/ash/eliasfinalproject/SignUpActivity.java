package soraka.ash.eliasfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Modern user registration activity with Material Design and Firebase integration.
 * Provides comprehensive form validation, social signup options, and smooth user experience.
 * Features clean UI with proper error handling and navigation flow.
 *
 * نشاط تسجيل مستخدم حديث مع تصميم Material وتكامل Firebase.
 * يوفر تحقق شامل من النماذج، خيارات التسجيل الاجتماعي، وتجربة مستخدم سلسة.
 * يتميز بواجهة مستخدم نظيفة مع معالجة أخطاء مناسبة وتدفق تنقل.
 */
public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private TextInputLayout firstNameLayout, lastNameLayout, emailLayout, passwordLayout, confirmPasswordLayout;

    private Button signUpButton;
    private ImageView logoImage;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView titleText, subtitleText, loginText;
    private static final int MIN_PASSWORD_LENGTH = 8;

    /**
     * Called when the activity is first created. Initializes all UI components,
     * sets up click listeners, and hides the action bar for full-screen design.
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle)
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Check if user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already signed in, go to MainActivity
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
            return;
        }

        // Initialize views
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        
        firstNameLayout = findViewById(R.id.firstNameLayout);
        lastNameLayout = findViewById(R.id.lastNameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);
        signUpButton = findViewById(R.id.signUpButton);
        logoImage = findViewById(R.id.logoImage);
        
        // Modern UI elements
        progressBar = findViewById(R.id.progressBar);
        titleText = findViewById(R.id.titleText);
        subtitleText = findViewById(R.id.subtitleText);
        loginText = findViewById(R.id.loginText);

        // Set up click listeners
        if (signUpButton != null) {
            signUpButton.setOnClickListener(v -> attemptSignUp());
        }
        
        // Login redirect
        if (loginText != null) {
            loginText.setOnClickListener(v -> {
                startActivity(new Intent(SignUpActivity.this, Login.class));
                finish();
            });
        }
        
        // Set up login button click
//        Button loginButton = findViewById(R.id.loginButton);
//        loginButton.setOnClickListener(v -> {
//            // Navigate to LoginActivity
//            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish(); // Optional: Close the current activity
//        });
        
        // Hide the action bar for full-screen design
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    
    /**
     * Handles the toolbar back navigation button press.
     * Calls onBackPressed() to provide consistent navigation behavior.
     * @return true if the event was handled, false otherwise
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Attempts to sign up the user by validating all form fields.
     * Performs comprehensive validation including name requirements, email format,
     * password strength, and password confirmation matching.
     * Shows appropriate error messages for invalid inputs.
     */
    private void attemptSignUp() {
        // Check if EditText fields are initialized
        if (firstNameEditText == null || lastNameEditText == null || 
            emailEditText == null || passwordEditText == null || confirmPasswordEditText == null) {
            Toast.makeText(this, "UI elements not initialized", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reset errors
        if (firstNameLayout != null) firstNameLayout.setError(null);
        if (lastNameLayout != null) lastNameLayout.setError(null);
        if (emailLayout != null) emailLayout.setError(null);
        if (passwordLayout != null) passwordLayout.setError(null);
        if (confirmPasswordLayout != null) confirmPasswordLayout.setError(null);

        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        boolean isValid = true;

        // First name validation
        if (TextUtils.isEmpty(firstName)) {
            if (firstNameLayout != null) firstNameLayout.setError("First name is required");
            isValid = false;
        }

        // Last name validation
        if (TextUtils.isEmpty(lastName)) {
            if (lastNameLayout != null) lastNameLayout.setError("Last name is required");
            isValid = false;
        }

        // Email validation
        if (TextUtils.isEmpty(email)) {
            if (emailLayout != null) emailLayout.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (emailLayout != null) emailLayout.setError("Please enter a valid email");
            isValid = false;
        }

        // Password validation
        if (TextUtils.isEmpty(password)) {
            if (passwordLayout != null) passwordLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            if (passwordLayout != null) passwordLayout.setError("Password must be at least " + MIN_PASSWORD_LENGTH + " characters");
            isValid = false;
        }

        // Confirm password validation
        if (TextUtils.isEmpty(confirmPassword)) {
            if (confirmPasswordLayout != null) confirmPasswordLayout.setError("Please confirm your password");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            if (confirmPasswordLayout != null) confirmPasswordLayout.setError("Passwords don't match");
            isValid = false;
        }

        if (isValid) {
            // Show progress
            if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
            if (signUpButton != null) signUpButton.setEnabled(false);
            
            // Create user with Firebase
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    if (signUpButton != null) signUpButton.setEnabled(true);
                    
                    if (task.isSuccessful()) {
                        // Sign up success, update UI and navigate to main
                        showSuccessMessage("Account created successfully!");
                        FirebaseUser user = mAuth.getCurrentUser();
                        
                        // Navigate directly to MainActivity after successful signup
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    } else {
                        // If sign up fails, display a message to the user
                        showError("Sign up failed: " + task.getException().getMessage());
                    }
                });
        }
    }

    /**
     * Shows a success message to the user.
     * @param message The success message to display
     */
    private void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Shows an error message with custom styling for better visibility.
     * Uses the default toast frame for consistent appearance.
     * @param message The error message to display
     */
    private void showError(String message) {
        // Show error in a more visible way
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(android.R.drawable.toast_frame);
        toast.show();
    }
    
//    @Override
//    public void onBackPressed() {
//        // Handle back button press to navigate to login
//        startActivity(new Intent(this, Login.class)
//            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//        finish();
//        super.onBackPressed();
//    }
}
