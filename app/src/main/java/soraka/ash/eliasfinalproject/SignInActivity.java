package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    private TextInputLayout emailLayout, passwordLayout;
    private TextInputEditText emailEditText, passwordEditText;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize views
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        // Set up sign in button click listener
        findViewById(R.id.signInButton).setOnClickListener(v -> attemptSignIn());

        // Set up sign up redirect
        findViewById(R.id.signUpRedirectText).setOnClickListener(v -> {
            // Navigate to SignUpActivity
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            finish();
        });
    }

    private void attemptSignIn() {
        try {
            // Reset errors
            emailLayout.setError(null);
            passwordLayout.setError(null);

            // Get values
            String email = Objects.requireNonNull(emailEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();

            boolean isValid = true;

            // Validate email
            if (email.isEmpty()) {
                emailLayout.setError("Email is required");
                isValid = false;
            } else if (!isValidEmail(email)) {
                emailLayout.setError("Please enter a valid email address");
                isValid = false;
            }

            // Validate password
            if (password.isEmpty()) {
                passwordLayout.setError("Password is required");
                isValid = false;
            } else if (password.length() < 8) {
                passwordLayout.setError("Password must be at least 8 characters");
                isValid = false;
            }

            if (isValid) {
                // Here you would typically validate credentials with your backend
                // For this example, we'll just show a success message and navigate to MainActivity
                Toast.makeText(this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                // Navigate to MainActivity
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
