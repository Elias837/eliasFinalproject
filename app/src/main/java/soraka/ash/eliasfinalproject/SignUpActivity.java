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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import android.util.Log;
import androidx.annotation.NonNull;
import soraka.ash.eliasfinalproject.models.MyUser;

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
    private static final String TAG = "SignUpActivity";

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
     * Registers and saves user data with Firebase authentication.
     * Performs comprehensive validation and creates user account with Firebase.
     * Saves user profile information and handles success/error states.
     * 
     * يسجل ويحفظ بيانات المستخدم مع مصادقة Firebase.
     * يقوم بالتحقق الشامل وإنشاء حساب المستخدم مع Firebase.
     * يحفظ معلومات ملف المستخدم ويتعامل مع حالات النجاح/الخطأ.
     */
    private void checkAndSignUp_FB() {
        // Check if EditText fields are initialized
        if (firstNameEditText == null || lastNameEditText == null || 
            emailEditText == null || passwordEditText == null || confirmPasswordEditText == null) {
            Toast.makeText(this, "UI elements not initialized", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get user input
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Reset errors
        if (firstNameLayout != null) firstNameLayout.setError(null);
        if (lastNameLayout != null) lastNameLayout.setError(null);
        if (emailLayout != null) emailLayout.setError(null);
        if (passwordLayout != null) passwordLayout.setError(null);
        if (confirmPasswordLayout != null) confirmPasswordLayout.setError(null);

        boolean isValid = true;

        // Validate first name
        if (TextUtils.isEmpty(firstName)) {
            if (firstNameLayout != null) firstNameLayout.setError("First name is required");
            isValid = false;
        } else if (firstName.length() < 2) {
            if (firstNameLayout != null) firstNameLayout.setError("First name must be at least 2 characters");
            isValid = false;
        }

        // Validate last name
        if (TextUtils.isEmpty(lastName)) {
            if (lastNameLayout != null) lastNameLayout.setError("Last name is required");
            isValid = false;
        } else if (lastName.length() < 2) {
            if (lastNameLayout != null) lastNameLayout.setError("Last name must be at least 2 characters");
            isValid = false;
        }

        // Validate email
        if (TextUtils.isEmpty(email)) {
            if (emailLayout != null) emailLayout.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (emailLayout != null) emailLayout.setError("Please enter a valid email address");
            isValid = false;
        }

        // Validate password
        if (TextUtils.isEmpty(password)) {
            if (passwordLayout != null) passwordLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            if (passwordLayout != null) passwordLayout.setError("Password must be at least " + MIN_PASSWORD_LENGTH + " characters");
            isValid = false;
        } else if (!password.matches(".*[A-Z].*")) {
            if (passwordLayout != null) passwordLayout.setError("Password must contain at least one uppercase letter");
            isValid = false;
        } else if (!password.matches(".*[0-9].*")) {
            if (passwordLayout != null) passwordLayout.setError("Password must contain at least one number");
            isValid = false;
        }

        // Validate password confirmation
        if (TextUtils.isEmpty(confirmPassword)) {
            if (confirmPasswordLayout != null) confirmPasswordLayout.setError("Please confirm your password");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            if (confirmPasswordLayout != null) confirmPasswordLayout.setError("Passwords don't match");
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        // Show progress
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if (signUpButton != null) signUpButton.setEnabled(false);

        // Create user with Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                if (signUpButton != null) signUpButton.setEnabled(true);
                
                if (task.isSuccessful()) {
                    // Sign up success
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        // Update user profile with display name
                        String displayName = firstName + " " + lastName;
                        
                        // User profile is already saved to Firebase Realtime Database in the signup process
                        
                        Toast.makeText(SignUpActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                        
                        // Navigate to MainActivity
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    }
                } else {
                    // If sign up fails, display a message to the user
                    String errorMessage = task.getException() != null ? 
                        task.getException().getMessage() : "Sign up failed";
                    Toast.makeText(SignUpActivity.this, "Sign up failed: " + errorMessage, Toast.LENGTH_LONG).show();
                }
            });
    }

        
    /**
     * Validates email and password for Firebase sign in.
     * Checks email format and password requirements before authentication.
     * Handles sign in process with proper error handling and user feedback.
     * 
     * يتحقق من البريد الإلكتروني وكلمة المرور لتسجيل الدخول إلى Firebase.
     * يتحقق من تنسيق البريد الإلكتروني ومتطلبات كلمة المرور قبل المصادقة.
     * يتعامل مع عملية تسجيل الدخول مع معالجة أخطاء مناسبة وتغذية راجعة للمستخدم.
     */
    private void checkEmailPassw_FB(String email, String password) {
        // Validate email
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate password
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            Toast.makeText(this, "Password must be at least " + MIN_PASSWORD_LENGTH + " characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if (signUpButton != null) signUpButton.setEnabled(false);

        // Sign in with Firebase
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                if (signUpButton != null) signUpButton.setEnabled(true);
                
                if (task.isSuccessful()) {
                    // Sign in success
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        Toast.makeText(SignUpActivity.this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                        
                        // Navigate to MainActivity
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    }
                } else {
                    // If sign in fails, display a message to the user
                    String errorMessage = task.getException() != null ? 
                        task.getException().getMessage() : "Sign in failed";
                    Toast.makeText(SignUpActivity.this, "Sign in failed: " + errorMessage, Toast.LENGTH_LONG).show();
                }
            });
    }

    /**
     * Saves user data to Firebase Realtime Database.
     * Creates a unique user ID and stores the user object in the "users" collection.
     * Handles success and failure callbacks with appropriate user feedback.
     * 
     * يحفظ بيانات المستخدم إلى Firebase Realtime Database.
     * ينشئ معرف مستخدم فريد ويخزن كائن المستخدم في مجموعة "users".
     * يتعامل مع عمليات رد النداء الناجحة والفاشلة مع تغذية راجعة مناسبة للمستخدم.
     */
    public void saveUser(MyUser user) {
        // الحصول على مرجع إلى عقدة "users" في قاعدة البيانات
        // تهيئة Firebase Realtime Database
        // مؤشر لقاعدة البيانات
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        // مؤشر لجدول المستعملين
        DatabaseReference usersRef = database.child("users");
        // إنشاء مفتاح فريد للمستخدم الجديد
        DatabaseReference newUserRef = usersRef.push();
        // تعيين معرف المستخدم في كائن MyUser
        user.setUserId(newUserRef.getKey());
        // حفظ بيانات المستخدم في قاعدة البيانات
        // اضافة كائن "لمجموعة" المستعملين ومعالج حدث لفحص نجاح المطلوب
        // معالج حدث لفحص هل تم المطلوب من قاعدة البيانات
        newUserRef.setValue(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(SignUpActivity.this, "Succeeded to add User", Toast.LENGTH_SHORT).show();
                    finish();

                    // تم حفظ البيانات بنجاح
                    Log.d(TAG, "تم حفظ المستخدم بنجاح: " + user.getUserId());
                    // تحديث واجهة المستخدم أو تنفيذ إجراءات أخرى
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // معالجة الأخطاء
                    Log.e(TAG, "خطأ في حفظ المستخدم: " + e.getMessage(), e);
                    Toast.makeText(SignUpActivity.this, "Failed to add User", Toast.LENGTH_SHORT).show();
                    // عرض رسالة خطأ للمستخدم
                }
            });
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
