package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Application splash screen that displays on app launch.
 * Navigates to SignUpActivity after a short delay.
 * <p>
 * شاشة البداية للتطبيق التي تظهر عند التشغيل.
 * تنتقل إلى نشاط التسجيل بعد تأخير قصير.
 */
public class SplashScreenActivity extends AppCompatActivity {

    /** 
     * Duration of the splash screen visibility in milliseconds.
     * مدة ظهور شاشة البداية بالملي ثانية.
     */
    private static final int SPLASH_DELAY = 2000; // 2 seconds

    /**
     * Called when the activity is starting. Sets the content view and starts the delay timer.
     * <p>
     * يتم استدعاؤه عند بدء النشاط. يحدد الواجهة ويبدأ مؤقت التأخير.
     *
     * @param savedInstanceState Saved state. الحالة المحفوظة.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Simple delayed navigation to SignUpActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }
}
