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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Application splash screen that displays on app launch.
 * Shows branding elements and automatically navigates to the appropriate screen after a delay.
 * Provides a smooth user experience during app initialization with authentication check.
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
    private FirebaseAuth mAuth;



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

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        
        // Optional: Force sign out for testing (remove this line in production)
        mAuth.signOut();

        // Set up delayed navigation with authentication check
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    // User is already signed in, go to MainActivity
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // User not signed in, go to SignInActivity
                    Intent intent = new Intent(SplashScreenActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2000); // 2 seconds delay
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
