package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Simple login activity that provides basic user authentication functionality.
 * Allows users to log in to the main application or navigate to sign up.
 * Features buttons for login, sign up, and placeholders for social media login.
 * 
 * نشاط تسجيل دخول بسيط يوفر وظيفة مصادقة المستخدم الأساسية.
 * يسمح للمستخدمين بتسجيل الدخول إلى التطبيق الرئيسي أو الانتقال إلى التسجيل.
 * يتميز بأزرار لتسجيل الدخول والتسجيل ومساحات لتسجيل الدخول عبر وسائل التواصل الاجتماعي.
 */
public class Login extends AppCompatActivity {
    private Button bt_login;
    private Button btnSignup;
    private Button btnGoogle;
    private Button btnFacebook;

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
        bt_login = findViewById(R.id.bt_login);
        btnSignup = findViewById(R.id.btnSignup);
        // Set up click listener for login button - navigates to MainActivity
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent   intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //btnGoogle = findViewById(R.id.btnGoogle);

        // btnFacebook = findViewById(R.id.btnFacebook);
        // Set up click listener for sign up button - navigates to SignUpActivity
        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}