package soraka.ash.eliasfinalproject;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText fullNameEditText, emailEditText, budgetEditText, passwordEditText, confirmPasswordEditText;
    private TextInputLayout fullNameLayout, emailLayout, budgetLayout, passwordLayout, confirmPasswordLayout;

    private Button signUpButton;
    private ImageView logoImage;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView titleText, subtitleText, loginText;
    private static final int MIN_PASSWORD_LENGTH = 8;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
            return;
        }

        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        budgetEditText = findViewById(R.id.budgetEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        
        fullNameLayout = findViewById(R.id.fullNameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        budgetLayout = findViewById(R.id.budgetLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);
        signUpButton = findViewById(R.id.signUpButton);
        logoImage = findViewById(R.id.logoImage);
        
        progressBar = findViewById(R.id.progressBar);
        titleText = findViewById(R.id.titleText);
        subtitleText = findViewById(R.id.subtitleText);
        loginText = findViewById(R.id.loginRedirect);

        if (signUpButton != null) {
            signUpButton.setOnClickListener(v -> attemptSignUp());
        }
        
        if (loginText != null) {
            loginText.setOnClickListener(v -> {
                startActivity(new Intent(SignUpActivity.this, Login.class));
                finish();
            });
        }
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void attemptSignUp() {
        if (fullNameLayout != null) fullNameLayout.setError(null);
        if (emailLayout != null) emailLayout.setError(null);
        if (budgetLayout != null) budgetLayout.setError(null);
        if (passwordLayout != null) passwordLayout.setError(null);
        if (confirmPasswordLayout != null) confirmPasswordLayout.setError(null);

        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String budgetStr = budgetEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        boolean isValid = true;

        if (TextUtils.isEmpty(fullName)) {
            if (fullNameLayout != null) fullNameLayout.setError("Full name is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(email)) {
            if (emailLayout != null) emailLayout.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (emailLayout != null) emailLayout.setError("Please enter a valid email");
            isValid = false;
        }

        if (TextUtils.isEmpty(budgetStr)) {
            if (budgetLayout != null) budgetLayout.setError("Monthly budget is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {
            if (passwordLayout != null) passwordLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            if (passwordLayout != null) passwordLayout.setError("Password must be at least " + MIN_PASSWORD_LENGTH + " characters");
            isValid = false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            if (confirmPasswordLayout != null) confirmPasswordLayout.setError("Please confirm your password");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            if (confirmPasswordLayout != null) confirmPasswordLayout.setError("Passwords don't match");
            isValid = false;
        }

        if (isValid) {
            if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
            if (signUpButton != null) signUpButton.setEnabled(false);
            
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    if (signUpButton != null) signUpButton.setEnabled(true);
                    
                    if (task.isSuccessful()) {
                        // Save user data locally
                        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("user_name", fullName);
                        editor.putString("monthly_budget", budgetStr);
                        editor.apply();

                        scheduleMonthlyReminder();
                        
                        Toast.makeText(SignUpActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        }
    }

    private void scheduleMonthlyReminder() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, BudgetReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 10);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.MONTH, 1);
        }

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY * 30,
                    pendingIntent
            );
        }
    }
}
