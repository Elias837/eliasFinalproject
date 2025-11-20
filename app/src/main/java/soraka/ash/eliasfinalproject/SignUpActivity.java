package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private TextInputLayout firstNameLayout, lastNameLayout, emailLayout, passwordLayout, confirmPasswordLayout;

    private Button signUpButton;
    private ImageView logoImage;
    private static final int MIN_PASSWORD_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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

        // Set up click listeners
        findViewById(R.id.signUpButton).setOnClickListener(v -> attemptSignUp());
        
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
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void attemptSignUp() {
        // Reset errors
        firstNameLayout.setError(null);
        lastNameLayout.setError(null);
        emailLayout.setError(null);
        passwordLayout.setError(null);
        confirmPasswordLayout.setError(null);

        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        boolean isValid = true;

        // First name validation
        if (TextUtils.isEmpty(firstName)) {
            firstNameLayout.setError("First name is required");
            isValid = false;
        }

        // Last name validation
        if (TextUtils.isEmpty(lastName)) {
            lastNameLayout.setError("Last name is required");
            isValid = false;
        }

        // Email validation
        if (TextUtils.isEmpty(email)) {
            emailLayout.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Please enter a valid email");
            isValid = false;
        }

        // Password validation
        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            passwordLayout.setError("Password must be at least " + MIN_PASSWORD_LENGTH + " characters");
            isValid = false;
        }

        // Confirm password validation
        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordLayout.setError("Please confirm your password");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError("Passwords don't match");
            isValid = false;
        }

        if (isValid) {
            // All validations passed - proceed with sign up
            showSuccessMessage("Account created successfully!");
            // Here you would typically make an API call to register the user
            // For example: registerUser(firstName, lastName, email, password);
        }
    }

    private void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        
        // After successful signup, navigate to the login screen
        startActivity(new Intent(this, Login.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
    
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
