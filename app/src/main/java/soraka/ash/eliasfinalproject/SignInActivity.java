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

/**
 * Advanced sign-in activity with comprehensive email and password validation.
 * Provides secure user authentication with proper input validation,
 * error handling, and navigation to main features.
 * 
 * نشاط تسجيل دخول متقدم مع تحقق شامل من البريد الإلكتروني وكلمة المرور.
 * يوفر مصادقة مستخدم آمنة مع التحقق الصحيح من الإدخال،
 * ومعالجة الأخطاء، والتنقل إلى الميزات الرئيسية.
 */
public class SignInActivity extends AppCompatActivity {

    private TextInputLayout emailLayout, passwordLayout;
    private TextInputEditText emailEditText, passwordEditText;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    /**
     * Called when the activity is first created. Initializes all UI components,
     * sets up click listeners for sign-in button and sign-up redirect.
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle)
     * 
     * يُستدعى عند إنشاء النشاط لأول مرة. يهيئ جميع مكونات واجهة المستخدم،
     * ويقوم بإعداد مستمعي النقر لزر تسجيل الدخول وإعادة التوجيه إلى التسجيل.
     * @param savedInstanceState إذا كان النشاط يعاد تهيئته بعد إيقاف تشغيله مسبقاً
     *                           فإن هذا Bundle يحتوي على البيانات التي قدمها مؤخراً
     *                           في onSaveInstanceState(Bundle)
     */
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

    /**
     * Attempts to sign in the user by validating email and password inputs.
     * Performs comprehensive validation including email format and password length checks.
     * Shows appropriate error messages for invalid inputs and navigates to MainActivity on success.
     * 
     * يحاول تسجيل دخول المستخدم عن طريق التحقق من إدخالات البريد الإلكتروني وكلمة المرور.
     * يقوم بالتحقق الشامل بما في ذلك التحقق من تنسيق البريد الإلكتروني وفحص طول كلمة المرور.
     * يعرض رسائل خطأ مناسبة للإدخالات غير الصالحة وينتقل إلى MainActivity عند النجاح.
     */
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

    /**
     * Validates the email format using a regex pattern.
     * @param email The email string to validate
     * @return true if the email format is valid, false otherwise
     * 
     * يتحقق من تنسيق البريد الإلكتروني باستخدام نمط regex.
     * @param email سلسلة البريد الإلكتروني للتحقق من صحتها
     * @return true إذا كان تنسيق البريد الإلكتروني صالحاً، false خلاف ذلك
     */
    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
