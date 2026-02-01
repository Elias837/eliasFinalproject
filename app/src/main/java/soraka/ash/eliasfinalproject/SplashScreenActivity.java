package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Application splash screen that displays on app launch.
 * Shows branding elements and automatically navigates to the sign-up screen after a delay.
 * Provides a smooth user experience during app initialization.
 */
public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000; // 2 seconds
    private TextView tv2;
    private ImageView iv1;
    private ProgressBar pb1;
    private TextView tv7;
    private Button bt_login;
    private Button bt_google_login;
    private Button bt_facebook_login;



    /**
     * Called when the activity is first created. Sets up the splash screen layout
     * and implements a delayed navigation to the SignUpActivity after 4 seconds.
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Set up delayed navigation to SignUpActivity after 4 seconds
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000); // 4 seconds delay
        // Delay and navigate to main activity
//        new Handler().postDelayed(() -> {
//            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
//            finish();
//        }, 3000); // 3 seconds

        // Delay and navigate to main activity
//        new Handler().postDelayed(() -> {
//            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
//            finish();
//        }, SPLASH_DELAY);
    }
}
