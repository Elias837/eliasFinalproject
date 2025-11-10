package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class signUp extends AppCompatActivity {
    private TextView titleText;
    private TextView subtitleText;
    private Button btnLogin;
    private Button signUpButton;
    private Button btnGoogle;
    private Button btnFacebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        titleText = findViewById(R.id.titleText);
        subtitleText = findViewById(R.id.subtitleText);
        btnLogin = findViewById(R.id.btnLogin);

       // btnGoogle = findViewById(R.id.btnGoogle);
       // btnFacebook = findViewById(R.id.btnFacebook);
        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          Intent   intent = new Intent(signUp.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(signUp.this, Login.class);
            startActivity(intent);
        });
    }

}




