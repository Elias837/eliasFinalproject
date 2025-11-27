package soraka.ash.eliasfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {
    private MaterialCardView btnAcc;
    private MaterialCardView btnGoals;
    private MaterialCardView btnAI;
    private MaterialCardView btnStatistics;

    private Button btnCalendar;
    private Button btnCompass;
    private Button btnToday;
    private Button btnSettings;
    private ImageButton IV2;
    private ImageButton IV3;
    private ImageButton IV4;
    private ImageButton IV5;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnAcc =findViewById(R.id.btnAcc);
        btnGoals =findViewById(R.id.btnGoals);
        btnAI =findViewById(R.id.btnAI);
        btnStatistics =findViewById(R.id.btnStatistics);
        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(MainActivity.this, accountsAndPay.class);
                startActivity(intent) ;
            }}
        );
        btnGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(MainActivity.this, goalsAbudgeting.class);
                startActivity(intent) ;
            }}
        );
        btnAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(MainActivity.this, AIinsights.class);
                startActivity(intent) ;
            }}
        );
        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(MainActivity.this, statistics.class);
                startActivity(intent) ;
            }}
        );



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}