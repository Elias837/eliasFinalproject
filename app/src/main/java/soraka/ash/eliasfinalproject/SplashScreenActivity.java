package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000; // 2 seconds
    private TextView tv2;
    private ImageView iv1;
    private ProgressBar pb1;
    private TextView tv7;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, 4000); // 3 seconds
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
