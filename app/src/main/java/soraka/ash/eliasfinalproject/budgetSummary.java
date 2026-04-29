package soraka.ash.eliasfinalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

/**
 * Budget Management Activity.
 * Displays the monthly budget set at signup and tracks spending.
 */
public class budgetSummary extends AppCompatActivity {
    private MaterialCardView budgetCard, incomeCard, expenseCard, balanceCard;
    private TextView tvBudgetAmount, tvRemainingBudget, tvTotalBalance, tvIncomeAmount, tvExpenseAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accounts_and_pay);
        
        initializeViews();
        loadBudgetData();
        setupClickListeners();
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        budgetCard = findViewById(R.id.budgetCard);
        incomeCard = findViewById(R.id.incomeCard);
        expenseCard = findViewById(R.id.expenseCard);
        balanceCard = findViewById(R.id.balanceCard);

        tvBudgetAmount = findViewById(R.id.tvBudgetAmount);
        tvRemainingBudget = findViewById(R.id.tvRemainingBudget);
        tvTotalBalance = findViewById(R.id.tvTotalBalance);
        tvIncomeAmount = findViewById(R.id.tvIncomeAmount);
        tvExpenseAmount = findViewById(R.id.tvExpenseAmount);
    }

    private void loadBudgetData() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String budget = prefs.getString("monthly_budget", "0.00");
        
        if (tvBudgetAmount != null) {
            tvBudgetAmount.setText("$" + budget);
        }
        
        // Initial logic: Remaining is Budget - Expenses (placeholder logic)
        if (tvRemainingBudget != null) {
            tvRemainingBudget.setText("Remaining: $" + budget);
        }
    }

    private void setupClickListeners() {
        if (budgetCard != null) {
            budgetCard.setOnClickListener(v -> {
                // Optional: Allow user to edit their monthly budget
            });
        }

        if (balanceCard != null) {
            balanceCard.setOnClickListener(v -> {
                // Navigate to balance update screen
            });
        }
    }
}
