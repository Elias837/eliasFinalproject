package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.button.MaterialButton;

/**
 * Activity for managing application settings and user session.
 * Provides options for notifications, privacy, help, and logging out.
 *
 * نشاط لإدارة إعدادات التطبيق وجلسة المستخدم.
 * يوفر خيارات للإشعارات، الخصوصية، المساعدة، وتسجيل الخروج.
 */
public class SettingsActivity extends AppCompatActivity {

    /** Firebase Auth instance for handling logout operations. */
    /** نسخة Firebase Auth للتعامل مع عمليات تسجيل الخروج. */
    private FirebaseAuth mAuth;

    /**
     * Initializes the settings activity and its UI components.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down.
     *
     * يقوم بتهيئة نشاط الإعدادات ومكونات واجهة المستخدم الخاصة به.
     * @param savedInstanceState إذا تم إعادة تهيئة النشاط بعد إغلاقه سابقاً.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();

        MaterialToolbar toolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        MaterialButton btnLogout = findViewById(R.id.btnLogout);

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                mAuth.signOut();
                Intent intent = new Intent(SettingsActivity.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
    }
}
