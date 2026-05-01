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

import java.util.ArrayList;
import java.util.List;

/**
 * The main dashboard activity of the application.
 * Serves as the central navigation hub to different financial tools and settings.
 * <p>
 * نشاط اللوحة الرئيسية للتطبيق.
 * يعمل كمركز تنقل رئيسي لمختلف الأدوات المالية والإعدادات.
 */
public class MainActivity extends AppCompatActivity {

    /** 
     * TextView to display the personalized greeting to the user.
     * نص لعرض الترحيب الشخصي للمستخدم.
     */
    private TextView welcomeText;

    /** 
     * Request code used for handling multiple application permissions.
     * رمز الطلب المستخدم للتعامل مع أذونات التطبيق المتعددة.
     */
    private static final int PERMISSION_REQUEST_CODE = 123;

    /**
     * Called when the activity is starting. Initializes the UI and navigation.
     * <p>
     * تُستدعى عند بدء النشاط. تقوم بتهيئة واجهة المستخدم والتنقل.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down.
     *                           إذا تم إعادة تهيئة النشاط بعد إغلاقه سابقاً.
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

        // Setting up navigation listeners
        if (btnAcc != null) {
            btnAcc.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, budgetSummary.class)));
        }

        if (btnGoals != null) {
            btnGoals.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, goalsAbudgeting.class)));
        }

        if (btnAI != null) {
            btnAI.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GeminiActivity.class)));
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
     * If no name is found, it defaults to "User".
     * <p>
     * يجلب اسم المستخدم من SharedPreferences ويحدث نص الترحيب.
     * في حال عدم العثور على اسم، يتم استخدام "User" كقيمة افتراضية.
     */
    private void updateGreeting() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String name = prefs.getString("user_name", "User");
        if (welcomeText != null) {
            welcomeText.setText("Hello, " + name + "!");
        }
    }

    /**
     * Checks and requests necessary application permissions such as Camera, Storage, and Notifications.
     * Handles differences between Android versions.
     * <p>
     * يتحقق ويطلب أذونات التطبيق الضرورية مثل الكاميرا، التخزين، والإشعارات.
     * يتعامل مع الاختلافات بين إصدارات الأندرويد.
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
     * Callback method for the result from requesting permissions.
     * Displays a toast if all requested permissions are granted.
     * <p>
     * طريقة استدعاء لنتيجة طلب الأذونات.
     * تعرض رسالة (Toast) إذا تم منح جميع الأذونات المطلوبة.
     *
     * @param requestCode The request code passed in requestPermissions. رمز الطلب الممرر.
     * @param permissions The requested permissions. الأذونات المطلوبة.
     * @param grantResults The grant results for the corresponding permissions. نتائج المنح.
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
