package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    private Button bt_login;
    private Button btnSignup;
    private Button btnGoogle;
    private Button btnFacebook;

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
        //btnGoogle = findViewById(R.id.btnGoogle);

        // btnFacebook = findViewById(R.id.btnFacebook);
        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, signUp.class);
            startActivity(intent);
        });
    }
}