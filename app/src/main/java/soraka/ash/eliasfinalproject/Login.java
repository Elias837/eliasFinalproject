package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Activity that handles user authentication.
 * Provides a modern login interface with email/password validation and Firebase integration.
 * <p>
 * نشاط يتعامل مع مصادقة المستخدم.
 * يوفر واجهة تسجيل دخول حديثة مع التحقق من البريد الإلكتروني وكلمة المرور والتكامل مع Firebase.
 */
public class Login extends AppCompatActivity {
    
    /** Buttons for login, signup, and social providers. أزرار تسجيل الدخول والتسجيل والمنصات الاجتماعية. */
    private Button bt_login;
    private Button btnSignup;
    private Button btnGoogle;
    private Button btnFacebook;

    /** Firebase Auth instance for managing user sign-in. نسخة Firebase Auth لإدارة تسجيل دخول المستخدم. */
    private FirebaseAuth mAuth;
    
    /** Input layouts for credentials. تخطيطات الإدخال لبيانات الاعتماد. */
    private TextInputLayout emailLayout, passwordLayout;
    /** Edit fields for credentials. حقول التحرير لبيانات الاعتماد. */
    private TextInputEditText emailEditText, passwordEditText;

    /** Progress indicator. مؤشر التقدم. */
    private ProgressBar progressBar;

    /** UI text elements. عناصر نص واجهة المستخدم. */
    private TextView tv_welcome, tv_subtitle, tv_or;

    /**
     * Initializes the login screen, checks for existing sessions, and sets up window insets.
     * <p>
     * يقوم بتهيئة شاشة تسجيل الدخول، والتحقق من الجلسات الموجودة، وإعداد حواف النافذة.
     *
     * @param savedInstanceState Saved state. الحالة المحفوظة.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
            return;
        }

        initializeViews();
        setupClickListeners();
    }

    /**
     * Links Java objects to their respective view components in the XML layout.
     * <p>
     * يربط كائنات Java بمكونات العرض المقابلة لها في تخطيط XML.
     */
    private void initializeViews() {
        bt_login = findViewById(R.id.bt_login);
        btnSignup = findViewById(R.id.btnSignup);
        btnGoogle = findViewById(R.id.bt_google_login);
        btnFacebook = findViewById(R.id.bt_facebook_login);

        emailLayout = findViewById(R.id.et_email_wrapper);
        passwordLayout = findViewById(R.id.et_password_wrapper);
        emailEditText = findViewById(R.id.et_email);
        passwordEditText = findViewById(R.id.et_password);
        
        tv_welcome = findViewById(R.id.tv_welcome);
        tv_subtitle = findViewById(R.id.tv_subtitle);
        tv_or = findViewById(R.id.tv_or);
    }

    /**
     * Assigns click behavior to buttons, including navigation and authentication triggers.
     * <p>
     * يعين سلوك النقر للأزرار، بما في ذلك التنقل ومحفزات المصادقة.
     */
    private void setupClickListeners() {
        if (bt_login != null) {
            bt_login.setOnClickListener(v -> attemptLogin());
        }

        if (btnSignup != null) {
            btnSignup.setOnClickListener(v -> {
                Intent intent = new Intent(Login.this, SignUpActivity.class);
                startActivity(intent);
            });
        }

        if (btnGoogle != null) {
            btnGoogle.setOnClickListener(v -> Toast.makeText(this, "Google Sign-In coming soon", Toast.LENGTH_SHORT).show());
        }

        if (btnFacebook != null) {
            btnFacebook.setOnClickListener(v -> Toast.makeText(this, "Facebook Sign-In coming soon", Toast.LENGTH_SHORT).show());
        }
    }

    /**
     * Performs credential validation and initiates sign-in with Firebase.
     * Redirects to the Main Dashboard upon successful authentication.
     * <p>
     * يقوم بالتحقق من صحة أوراق الاعتماد ويبدأ تسجيل الدخول مع Firebase.
     * يعيد التوجيه إلى اللوحة الرئيسية عند نجاح المصادقة.
     */
    private void attemptLogin() {
        if (emailEditText == null || passwordEditText == null) return;

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (emailLayout != null) emailLayout.setError(null);
        if (passwordLayout != null) passwordLayout.setError(null);

        if (email.isEmpty()) {
            if (emailLayout != null) emailLayout.setError("Email is required");
            return;
        }

        if (password.isEmpty()) {
            if (passwordLayout != null) passwordLayout.setError("Password is required");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                } else {
                    String error = task.getException() != null ? task.getException().getMessage() : "Authentication failed";
                    Toast.makeText(Login.this, "Login failed: " + error, Toast.LENGTH_LONG).show();
                }
            });
    }
}
