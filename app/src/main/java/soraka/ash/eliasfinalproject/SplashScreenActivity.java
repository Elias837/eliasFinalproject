package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    
    private static final int SPLASH_DELAY = 2000; // 2 seconds
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        
        // Initialize views
        ImageView logo = findViewById(R.id.logo);
        TextView appName = findViewById(R.id.app_name);
        
        // Load animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation slideUp = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        
        // Set animations
        logo.startAnimation(fadeIn);
        appName.startAnimation(slideUp);
        
        // Delay and navigate to main activity
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        }, SPLASH_DELAY);
    }
}
