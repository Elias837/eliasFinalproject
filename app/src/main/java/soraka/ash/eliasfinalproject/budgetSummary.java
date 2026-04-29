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
 * Activity that provides a detailed summary of the user's budget, income, expenses, and balance.
 * It displays the monthly budget target and calculates the remaining amount.
 *
 * نشاط يوفر ملخصاً مفصلاً لميزانية المستخدم، والدخل، والمصاريف، والرصيد.
 * يعرض الهدف الشهري للميزانية ويحسب المبلغ المتبقي.
 */
public class budgetSummary extends AppCompatActivity {
    /** Cards for displaying different financial metrics. */
    /** بطاقات لعرض المقاييس المالية المختلفة. */
    private MaterialCardView budgetCard, incomeCard, expenseCard, balanceCard;

    /** TextViews for displaying the amounts of budget, balance, income, and expenses. */
    /** نصوص لعرض مبالغ الميزانية، والرصيد، والدخل، والمصاريف. */
    private TextView tvBudgetAmount, tvRemainingBudget, tvTotalBalance, tvIncomeAmount, tvExpenseAmount;

    /**
     * Initializes the activity and sets up the user interface.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down.
     *
     * يقوم بتهيئة النشاط وإعداد واجهة المستخدم.
     * @param savedInstanceState إذا تم إعادة تهيئة النشاط بعد إغلاقه سابقاً.
     */
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

    /**
     * Finds and initializes all view components from the layout.
     *
     * يبحث عن جميع مكونات الواجهة في التخطيط ويقوم بتهيئتها.
     */
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

    /**
     * Loads the saved monthly budget from SharedPreferences and updates the UI.
     *
     * يحمل الميزانية الشهرية المحفوظة من SharedPreferences ويحدث واجهة المستخدم.
     */
    private void loadBudgetData() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String budget = prefs.getString("monthly_budget", "0.00");
        
        if (tvBudgetAmount != null) {
            tvBudgetAmount.setText("$" + budget);
        }
        
        // Initial logic: Remaining is Budget - Expenses (placeholder logic)
        // منطق أولي: المتبقي هو الميزانية ناقص المصاريف
        if (tvRemainingBudget != null) {
            tvRemainingBudget.setText("Remaining: $" + budget);
        }
    }

    /**
     * Sets up click listeners for interactive cards like the budget and balance cards.
     *
     * يجهز مستمعي النقر للبطاقات التفاعلية مثل بطاقات الميزانية والرصيد.
     */
    private void setupClickListeners() {
        if (budgetCard != null) {
            budgetCard.setOnClickListener(v -> {
                // Optional: Allow user to edit their monthly budget
                // اختياري: السماح للمستخدم بتعديل ميزانيته الشهرية
            });
        }

        if (balanceCard != null) {
            balanceCard.setOnClickListener(v -> {
                // Navigate to balance update screen
                // الانتقال إلى شاشة تحديث الرصيد
            });
        }
    }
}
