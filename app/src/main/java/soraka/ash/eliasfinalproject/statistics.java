package soraka.ash.eliasfinalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

/**
 * Financial statistics and reporting activity.
 * Provides a breakdown of income, expenses, and monthly trends.
 */
public class statistics extends AppCompatActivity {

    // UI Components
    private TextView tvTotalBalance, tvStatIncome, tvStatExpense;
    private TextView tvLabelFood, tvLabelRent;
    private MaterialCardView balanceCard, graphCard;
    private View barJan, barFeb, barMar, barApr;
    private LinearProgressIndicator progressFood, progressRent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge should be enabled before setContentView
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);

        // Initialize Views
        initializeViews();

        // Ensure the ID exists in the layout. 
        // In activity_statistics.xml, the ScrollView has id "@+id/main".
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
     * Connects UI variables with their XML counterparts.
     */
    private void initializeViews() {
        // Summary Card
        balanceCard = findViewById(R.id.balanceCard);
        tvTotalBalance = findViewById(R.id.tvTotalBalance);
        
        // Income & Expense
        tvStatIncome = findViewById(R.id.tvStatIncome);
        tvStatExpense = findViewById(R.id.tvStatExpense);
        
        // Graph Elements
        graphCard = findViewById(R.id.graphCard);
        barJan = findViewById(R.id.barJan);
        barFeb = findViewById(R.id.barFeb);
        barMar = findViewById(R.id.barMar);
        barApr = findViewById(R.id.barApr);
        
        // Spending Breakdown
        tvLabelFood = findViewById(R.id.tvLabelFood);
        tvLabelRent = findViewById(R.id.tvLabelRent);
        progressFood = findViewById(R.id.progressFood);
        progressRent = findViewById(R.id.progressRent);
    }
}
