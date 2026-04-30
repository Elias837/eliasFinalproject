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
 * Activity that provides a detailed summary of the user's budget, income, expenses, and balance.
 * Allows users to update their balance, add transactions, and reset all financial data.
 */
public class budgetSummary extends AppCompatActivity {
    private MaterialCardView budgetCard, incomeCard, expenseCard, balanceCard;
    private TextView tvBudgetAmount, tvRemainingBudget, tvTotalBalance, tvIncomeAmount, tvExpenseAmount;
    private Button btnResetAll;
    
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userId;

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

    private void loadBudgetData() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String budget = prefs.getString("monthly_budget", "0.00");
        if (tvBudgetAmount != null) tvBudgetAmount.setText("$" + budget);
    }

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

    private void showResetAllDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Reset Everything?")
                .setMessage("This will set your Balance, Transactions, and Monthly Budget Target to zero. This action cannot be undone.")
                .setPositiveButton("Reset All", (dialog, which) -> resetEverything())
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void resetEverything() {
        if (userId == null) return;

        // 1. Reset Firebase Balance
        mDatabase.child("users").child(userId).child("current_balance").setValue(0);

        // 2. Remove Firebase Transactions
        mDatabase.child("users").child(userId).child("transactions").removeValue();

        // 3. Reset Local SharedPreferences Budget Target
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("monthly_budget", "0.00");
        editor.apply();

        // 4. Update UI
        loadBudgetData();
        Toast.makeText(this, "All data has been reset to zero", Toast.LENGTH_LONG).show();
    }

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

    private void updateBalanceInFirebase(double newBalance) {
        if (userId == null) return;
        mDatabase.child("users").child(userId).child("current_balance").setValue(newBalance)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Balance updated", Toast.LENGTH_SHORT).show());
    }

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

    private void listenForFinancialUpdates() {
        if (userId == null) return;

        // Listen for balance
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

        // Listen for all transactions to calculate totals
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
                
                // Update remaining budget
                SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                double budget = Double.parseDouble(prefs.getString("monthly_budget", "0"));
                tvRemainingBudget.setText("Remaining: $" + String.format("%.2f", budget - totalExpense));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
