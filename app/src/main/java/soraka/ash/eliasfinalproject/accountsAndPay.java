package soraka.ash.eliasfinalproject;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.card.MaterialCardView;

/**
 * Accounts and payment management activity.
 * Provides access to transaction management and financial overview.
 */
public class accountsAndPay extends AppCompatActivity {
    private FloatingActionButton addTransactionFab;
    private MaterialCardView incomeCard, expenseCard, balanceCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accounts_and_pay);
        
        initializeViews();
        setupClickListeners();
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        addTransactionFab = findViewById(R.id.addTransactionFab);
        incomeCard = findViewById(R.id.incomeCard);
        expenseCard = findViewById(R.id.expenseCard);
        balanceCard = findViewById(R.id.balanceCard);
    }

    private void setupClickListeners() {
        if (addTransactionFab != null) {
            addTransactionFab.setOnClickListener(v -> {
                Intent intent = new Intent(accountsAndPay.this, AddTransactionActivity.class);
                startActivity(intent);
            });
        }

        if (incomeCard != null) {
            incomeCard.setOnClickListener(v -> {
                // TODO: Navigate to income transactions list
            });
        }

        if (expenseCard != null) {
            expenseCard.setOnClickListener(v -> {
                // TODO: Navigate to expense transactions list
            });
        }

        if (balanceCard != null) {
            balanceCard.setOnClickListener(v -> {
                // TODO: Navigate to balance overview
            });
        }
    }
}
