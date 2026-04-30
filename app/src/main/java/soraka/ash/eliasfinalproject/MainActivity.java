package soraka.ash.eliasfinalproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.ai.FirebaseAI;
import com.google.firebase.ai.java.GenerativeModelFutures;
import com.google.firebase.ai.type.GenerativeBackend;

import java.util.ArrayList;
import java.util.List;

/**
 * The main dashboard activity of the application.
 * Serves as the central navigation hub to different financial tools and settings.
 *
 * نشاط اللوحة الرئيسية للتطبيق.
 * يعمل كمركز تنقل رئيسي لمختلف الأدوات المالية والإعدادات.
 */
public class MainActivity extends AppCompatActivity {

    /** TextView to display the personalized greeting. */
    /** نص لعرض الترحيب الشخصي. */
    private TextView welcomeText;

    /** Request code for permissions. */
    /** رمز طلب الأذونات. */
    private static final int PERMISSION_REQUEST_CODE = 123;

    /**
     * Initializes the dashboard, sets up navigation cards and requests permissions.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down.
     *
     * يقوم بتهيئة لوحة التحكم، وإعداد بطاقات التنقل وطلب الأذونات.
     * @param savedInstanceState إذا تم إعادة تهيئة النشاط بعد إغلاقه سابقاً.
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        requestAppPermissions();

        welcomeText = findViewById(R.id.welcomeText);
        updateGreeting();

        MaterialCardView btnAcc = findViewById(R.id.btnAcc);
        MaterialCardView btnGoals = findViewById(R.id.btnGoals);
        MaterialCardView btnAI = findViewById(R.id.btnAI);
        MaterialCardView btnStatistics = findViewById(R.id.btnStatistics);
        ImageButton btnSettings = findViewById(R.id.btnSettings);

        if (btnAcc != null) {
            btnAcc.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, budgetSummary.class)));
        }

        if (btnGoals != null) {
            btnGoals.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, goalsAbudgeting.class)));
        }

        if (btnAI != null) {
            btnAI.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AiInsights.class)));
        }

        if (btnStatistics != null) {
            btnStatistics.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, statistics.class)));
        }

        if (btnSettings != null) {
            btnSettings.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            });
        }

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }

    /**
     * Fetches the user's name from SharedPreferences and updates the greeting text.
     * Defaults to "User" if no name is found.
     *
     * يجلب اسم المستخدم من SharedPreferences ويحدث نص الترحيب.
     * القيمة الافتراضية هي "User" إذا لم يتم العثور على اسم.
     */
    private void updateGreeting() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String name = prefs.getString("user_name", "User");
        if (welcomeText != null) {
            welcomeText.setText("Hello, " + name + "!");
        }
    }

    /**
     * Checks and requests necessary application permissions (Camera, Media, Notifications).
     *
     * يتحقق ويطلب أذونات التطبيق الضرورية (الكاميرا، الوسائط، الإشعارات).
     */
    private void requestAppPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionsNeeded = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.CAMERA);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    permissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES);
                }
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    permissionsNeeded.add(Manifest.permission.POST_NOTIFICATIONS);
                }
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            }
            if (!permissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[0]), PERMISSION_REQUEST_CODE);
            }
        }
    }

    /**
     * Callback for the result from requesting permissions.
     * @param requestCode The request code passed in requestPermissions.
     * @param permissions The requested permissions.
     * @param grantResults The grant results for the corresponding permissions.
     *
     * استدعاء لنتيجة طلب الأذونات.
     * @param requestCode رمز الطلب الذي تم تمريره في requestPermissions.
     * @param permissions الأذونات المطلوبة.
     * @param grantResults نتائج المنح للأذونات المقابلة.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
