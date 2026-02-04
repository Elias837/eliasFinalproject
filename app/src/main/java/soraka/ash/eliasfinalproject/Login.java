package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Modern login activity with Firebase authentication and Material Design.
 * Provides secure user authentication with email/password and social login options.
 * Features clean UI with proper validation and error handling.
 *
 * نشاط تسجيل دخول حديث مع مصادقة Firebase وتصميم Material.
 * يوفر مصادقة مستخدم آمنة مع البريد الإلكتروني/كلمة المرور وخيارات تسجيل الدخول الاجتماعي.
 * يتميز بواجهة مستخدم نظيفة مع التحقق الصحيح ومعالجة الأخطاء.
 */
public class Login extends AppCompatActivity {
    private Button bt_login;
    private Button btnSignup;
    private Button btnGoogle;
    private Button btnFacebook;
    private FirebaseAuth mAuth;
    
    // Modern UI elements
    private TextInputLayout emailLayout, passwordLayout;
    private EditText emailEditText, passwordEditText;
    private ProgressBar progressBar;
    private TextView titleText, subtitleText, forgotPasswordText;

    /**
     * Called when the activity is first created. Initializes the activity,
     * sets up edge-to-edge display, and configures click listeners for login and sign up buttons.
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle)
     * 
     * يُستدعى عند إنشاء النشاط لأول مرة. يهيئ النشاط،
     * ويقوم بإعداد العرض من حافة إلى حافة، ويكوين مستمعي النقر لأزرار تسجيل الدخول والتسجيل.
     * @param savedInstanceState إذا كان النشاط يعاد تهيئته بعد إيقاف تشغيله مسبقاً
     *                           فإن هذا Bundle يحتوي على البيانات التي قدمها مؤخراً
     *                           في onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Check if user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
            return;
        }

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        // Traditional buttons
        bt_login = findViewById(R.id.bt_login);
        btnSignup = findViewById(R.id.btnSignup);

        // Modern UI elements (if they exist in layout)
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        progressBar = findViewById(R.id.progressBar);
        titleText = findViewById(R.id.titleText);
        subtitleText = findViewById(R.id.subtitleText);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);
    }

    private void setupClickListeners() {
        // Modern login with Firebase
        if (bt_login != null && emailEditText != null && passwordEditText != null) {
            bt_login.setOnClickListener(v -> attemptModernLogin());
        } else if (bt_login != null) {
            // Fallback to traditional navigation
            bt_login.setOnClickListener(v -> {
                Intent intent = new Intent(Login.this, SignInActivity.class);
                startActivity(intent);
            });
        }

        if (btnSignup != null) {
            btnSignup.setOnClickListener(v -> {
                Intent intent = new Intent(Login.this, SignUpActivity.class);
                startActivity(intent);
            });
        }

        // Social login buttons
        if (btnGoogle != null) {
            btnGoogle.setOnClickListener(v -> {
                // TODO: Implement Google Sign-In
                Toast.makeText(this, "Google Sign-In coming soon", Toast.LENGTH_SHORT).show();
            });
        }

        if (btnFacebook != null) {
            btnFacebook.setOnClickListener(v -> {
                // TODO: Implement Facebook Sign-In
                Toast.makeText(this, "Facebook Sign-In coming soon", Toast.LENGTH_SHORT).show();
            });
        }

        // Forgot password
        if (forgotPasswordText != null) {
            forgotPasswordText.setOnClickListener(v -> {
                String email = emailEditText != null ? emailEditText.getText().toString().trim() : "";
                if (!email.isEmpty()) {
                    mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Login.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                            }
                        });
                } else {
                    Toast.makeText(Login.this, "Enter your email address", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void attemptModernLogin() {
        if (emailEditText == null || passwordEditText == null) {
            Toast.makeText(this, "UI elements not initialized", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Reset errors
        if (emailLayout != null) emailLayout.setError(null);
        if (passwordLayout != null) passwordLayout.setError(null);

        // Validate input
        if (email.isEmpty()) {
            if (emailLayout != null) emailLayout.setError("Email is required");
            else Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            if (passwordLayout != null) passwordLayout.setError("Password is required");
            else Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if (bt_login != null) bt_login.setEnabled(false);

        // Authenticate with Firebase
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                if (bt_login != null) bt_login.setEnabled(true);
                
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(Login.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }

    // Removed onStart() method to prevent auto-navigation
    // Users should manually navigate through authentication flow
}