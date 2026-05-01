package soraka.ash.eliasfinalproject;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import soraka.ash.eliasfinalproject.models.Transaction;

/**
 * Activity that displays the financial summary of the user.
 * Shows balance, total income, total expenses, and remaining budget.
 * <p>
 * نشاط يعرض الملخص المالي للمستخدم.
 * يظهر الرصيد، إجمالي الدخل، إجمالي المصروفات، والميزانية المتبقية.
 */
public class budgetSummary extends AppCompatActivity {
    private MaterialCardView budgetCard, incomeCard, expenseCard, balanceCard;
    private TextView tvBudgetAmount, tvRemainingBudget, tvTotalBalance, tvIncomeAmount, tvExpenseAmount;
    private Button btnResetAll;
    
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userId;

    /**
     * Called when the activity is starting. Initializes Firebase and UI.
     * <p>
     * تُستدعى عند بدء النشاط. تهيئ Firebase وواجهة المستخدم.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budgetsummary);
        
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = mAuth.getUid();

        initializeViews();
        loadBudgetData();
        setupClickListeners();
        listenForFinancialUpdates();
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Finds and initializes view references from the layout.
     * <p>
     * يبحث عن مراجع الواجهات ويهيئها من التخطيط.
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
        btnResetAll = findViewById(R.id.btnResetAll);
    }

    /**
     * Loads the saved budget target from local storage.
     * <p>
     * يحمل هدف الميزانية المحفوظ من التخزين المحلي.
     */
    private void loadBudgetData() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String budget = prefs.getString("monthly_budget", "0.00");
        if (tvBudgetAmount != null) tvBudgetAmount.setText("$" + budget);
    }

    /**
     * Configures event listeners for the interactive UI cards and buttons.
     * <p>
     * يضبط مستمعي الأحداث للبطاقات والأزرار التفاعلية.
     */
    private void setupClickListeners() {
        if (balanceCard != null) {
            balanceCard.setOnClickListener(v -> showUpdateBalanceDialog());
        }

        if (expenseCard != null) {
            expenseCard.setOnClickListener(v -> showAddTransactionDialog(Transaction.TYPE_EXPENSE));
        }

        if (incomeCard != null) {
            incomeCard.setOnClickListener(v -> showAddTransactionDialog(Transaction.TYPE_INCOME));
        }

        if (btnResetAll != null) {
            btnResetAll.setOnClickListener(v -> showResetAllDialog());
        }
        
        if (budgetCard != null) {
            budgetCard.setOnClickListener(v -> showUpdateBudgetTargetDialog());
        }
    }

    /**
     * Shows a confirmation dialog before wiping all user financial data.
     * <p>
     * يظهر نافذة تأكيد قبل مسح جميع البيانات المالية للمستخدم.
     */
    private void showResetAllDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Reset Everything?")
                .setMessage("This will set your Balance, Transactions, and Monthly Budget Target to zero. This action cannot be undone.")
                .setPositiveButton("Reset All", (dialog, which) -> resetEverything())
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Resets balance, transactions in Firebase, and local budget target to zero.
     * <p>
     * يصفر الرصيد والمعاملات في Firebase وهدف الميزانية محلياً.
     */
    private void resetEverything() {
        if (userId == null) return;

        mDatabase.child("users").child(userId).child("current_balance").setValue(0);
        mDatabase.child("users").child(userId).child("transactions").removeValue();

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("monthly_budget", "0.00");
        editor.apply();

        loadBudgetData();
        Toast.makeText(this, "All data has been reset to zero", Toast.LENGTH_LONG).show();
    }

    /**
     * Displays a dialog to modify the monthly budget goal.
     * <p>
     * يعرض نافذة لتعديل هدف الميزانية الشهري.
     */
    private void showUpdateBudgetTargetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Monthly Budget Target");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        input.setText(prefs.getString("monthly_budget", "0.00"));
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String val = input.getText().toString();
            if (!val.isEmpty()) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("monthly_budget", val);
                editor.apply();
                loadBudgetData();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    /**
     * Displays a dialog to manually set the current account balance.
     * <p>
     * يعرض نافذة لتعيين رصيد الحساب الحالي يدوياً.
     */
    private void showUpdateBalanceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Current Balance");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setHint("Enter amount");
        builder.setView(input);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String val = input.getText().toString();
            if (!val.isEmpty()) {
                updateBalanceInFirebase(Double.parseDouble(val));
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    /**
     * Shows a list of categories based on transaction type (Income/Expense).
     * <p>
     * يعرض قائمة بالفئات بناءً على نوع المعاملة (دخل/مصروف).
     *
     * @param type The type of transaction. نوع المعاملة.
     */
    private void showAddTransactionDialog(String type) {
        String[] categories = type.equals(Transaction.TYPE_EXPENSE) 
                ? new String[]{"Food", "Rent", "Transport", "Shopping", "Entertainment", "Other"}
                : new String[]{"Salary", "Bonus", "Gift", "Other"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Category");
        builder.setItems(categories, (dialog, which) -> {
            String category = categories[which];
            showAmountInputDialog(type, category);
        });
        builder.show();
    }

    /**
     * Displays a dialog to input the amount for a new transaction.
     * <p>
     * يعرض نافذة لإدخال مبلغ معاملة جديدة.
     *
     * @param type The transaction type. نوع المعاملة.
     * @param category The selected category. الفئة المختارة.
     */
    private void showAmountInputDialog(String type, String category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter " + type + " Amount");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String amountStr = input.getText().toString();
            if (!amountStr.isEmpty()) {
                saveTransaction(type, category, Double.parseDouble(amountStr));
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    /**
     * Updates the user's current balance in Firebase.
     * <p>
     * يحدث الرصيد الحالي للمستخدم في Firebase.
     *
     * @param newBalance The updated balance value. قيمة الرصيد المحدثة.
     */
    private void updateBalanceInFirebase(double newBalance) {
        if (userId == null) return;
        mDatabase.child("users").child(userId).child("current_balance").setValue(newBalance)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Balance updated", Toast.LENGTH_SHORT).show());
    }

    /**
     * Saves a new transaction record to Firebase and triggers balance adjustment.
     * <p>
     * يحفظ سجل معاملة جديد في Firebase ويقوم بتعديل الرصيد.
     *
     * @param type Income or Expense. دخل أم مصروف.
     * @param category Category of spending/earning. فئة الإنفاق أو الكسب.
     * @param amount The monetary value. القيمة المالية.
     */
    private void saveTransaction(String type, String category, double amount) {
        if (userId == null) return;
        String transactionId = mDatabase.child("users").child(userId).child("transactions").push().getKey();
        
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("type", type);
        transaction.put("category", category);
        transaction.put("amount", amount);
        transaction.put("timestamp", System.currentTimeMillis());

        mDatabase.child("users").child(userId).child("transactions").child(transactionId).setValue(transaction)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Transaction saved", Toast.LENGTH_SHORT).show();
                    adjustBalance(type, amount);
                });
    }

    /**
     * Adjusts the total balance in Firebase based on a new transaction.
     * <p>
     * يعدل إجمالي الرصيد في Firebase بناءً على معاملة جديدة.
     *
     * @param type Income or Expense. نوع المعاملة.
     * @param amount The amount to add or subtract. المبلغ المراد إضافته أو طرحه.
     */
    private void adjustBalance(String type, double amount) {
        mDatabase.child("users").child(userId).child("current_balance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double currentBalance = snapshot.exists() ? snapshot.getValue(Double.class) : 0.0;
                double updatedBalance = type.equals(Transaction.TYPE_INCOME) ? currentBalance + amount : currentBalance - amount;
                mDatabase.child("users").child(userId).child("current_balance").setValue(updatedBalance);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    /**
     * Attaches Firebase listeners to update UI automatically when data changes.
     * <p>
     * يربط مستمعي Firebase لتحديث الواجهة تلقائياً عند تغير البيانات.
     */
    private void listenForFinancialUpdates() {
        if (userId == null) return;

        mDatabase.child("users").child(userId).child("current_balance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tvTotalBalance.setText("$" + String.format("%.2f", snapshot.getValue(Double.class)));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        mDatabase.child("users").child(userId).child("transactions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double totalIncome = 0;
                double totalExpense = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String type = ds.child("type").getValue(String.class);
                    Double amount = ds.child("amount").getValue(Double.class);
                    if (amount != null && type != null) {
                        if (type.equals(Transaction.TYPE_INCOME)) totalIncome += amount;
                        else totalExpense += amount;
                    }
                }
                tvIncomeAmount.setText("+$" + String.format("%.2f", totalIncome));
                tvExpenseAmount.setText("-$" + String.format("%.2f", totalExpense));
                
                SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                double budget = Double.parseDouble(prefs.getString("monthly_budget", "0"));
                tvRemainingBudget.setText("Remaining: $" + String.format("%.2f", budget - totalExpense));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
