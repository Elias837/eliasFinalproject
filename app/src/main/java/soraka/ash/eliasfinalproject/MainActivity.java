package soraka.ash.eliasfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Main dashboard activity that serves as the central navigation hub of the application.
 * Provides access to all major features including accounts, goals, AI insights, and statistics.
 * 
 * نشاط لوحة التحكم الرئيسي الذي يخدم كمركز تنقل مركزي للتطبيق.
 * يوفر الوصول إلى جميع الميزات الرئيسية بما في ذلك الحسابات والأهداف ورؤى الذكاء الاصطناعي والإحصائيات.
 */
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
    private Spinner spnrIncome;

    private FloatingActionButton fabAddIncome;


    /**
     * Called when the activity is first created. Initializes all UI components,
     * sets up the income spinner adapter, and configures click listeners for navigation.
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle)
     * 
     * يُستدعى عند إنشاء النشاط لأول مرة. يهيئ جميع مكونات واجهة المستخدم،
     * ويقوم بإعداد محول ال spinner للدخل، ويكوين مستمعي النقر للتنقل.
     * @param savedInstanceState إذا كان النشاق يعاد تهيئته بعد إيقاف تشغيله مسبقاً
     *                           فإن هذا Bundle يحتوي على البيانات التي قدمها مؤخراً
     *                           في onSaveInstanceState(Bundle)
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnAcc =findViewById(R.id.btnAcc);
        btnGoals =findViewById(R.id.btnGoals);
        btnAI =findViewById(R.id.btnAI);
        btnStatistics = findViewById(R.id.btnStatistics);
        
        // Initialize the Spinner for income type selection
        spnrIncome = findViewById(R.id.spnrIncome);
        
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spnrIncome_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spnrIncome.setAdapter(adapter);
        // Set up click listener for Accounts button - navigates to accountsAndPay activity
        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(MainActivity.this, accountsAndPay.class);
                startActivity(intent) ;
            }}
        );
        // Set up click listener for Goals button - navigates to goalsAbudgeting activity
        btnGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(MainActivity.this, goalsAbudgeting.class);
                startActivity(intent) ;
            }}
        );
        // Set up click listener for AI Insights button - navigates to AIinsights activity
        btnAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(MainActivity.this, AIinsights.class);
                startActivity(intent) ;
            }}
        );
        // Set up click listener for Statistics button - navigates to statistics activity
        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(MainActivity.this, statistics.class);
                startActivity(intent) ;
            }}
        );



        // Apply edge-to-edge display settings to handle system bars (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}