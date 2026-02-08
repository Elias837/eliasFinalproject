package soraka.ash.eliasfinalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import soraka.ash.eliasfinalproject.data.FirebaseHelper;
import soraka.ash.eliasfinalproject.models.Transaction;

/**
 * Activity for adding financial transactions (income/expenses)
 * Allows users to record transactions with categories and notes
 */
public class AddTransactionActivity extends AppCompatActivity {
    private TextInputLayout descriptionLayout, amountLayout, categoryLayout, notesLayout;
    private TextInputEditText descriptionEditText, amountEditText, notesEditText;
    private MaterialButton saveButton;
    private CircularProgressIndicator progressBar;
    private FirebaseHelper firebaseHelper;

    private static final String[] INCOME_CATEGORIES = {"Salary", "Freelance", "Investment", "Business", "Other Income"};
    private static final String[] EXPENSE_CATEGORIES = {"Food", "Transport", "Shopping", "Bills", "Entertainment", "Health", "Other Expenses"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        firebaseHelper = new FirebaseHelper();
        initializeViews();
        setupToolbar();
        setupSaveButton();
    }

    private void initializeViews() {
        descriptionEditText = findViewById(R.id.descriptionEditText);
        amountEditText = findViewById(R.id.amountEditText);
        notesEditText = findViewById(R.id.notesEditText);
        saveButton = findViewById(R.id.saveButton);
        progressBar = findViewById(R.id.progressBar);

        descriptionLayout = findViewById(R.id.descriptionLayout);
        amountLayout = findViewById(R.id.amountLayout);
        categoryLayout = findViewById(R.id.categoryLayout);
        notesLayout = findViewById(R.id.notesLayout);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupSaveButton() {
        saveButton.setOnClickListener(v -> saveTransaction());
    }

    private void saveTransaction() {
        String description = descriptionEditText.getText().toString().trim();
        String amountStr = amountEditText.getText().toString().trim();
        String notes = notesEditText.getText().toString().trim();

        // Validation
        if (description.isEmpty()) {
            descriptionLayout.setError("Description is required");
            descriptionEditText.requestFocus();
            return;
        }

        if (amountStr.isEmpty()) {
            amountLayout.setError("Amount is required");
            amountEditText.requestFocus();
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                amountLayout.setError("Amount must be greater than 0");
                amountEditText.requestFocus();
                return;
            }

            // For simplicity, default to expense type
            String type = Transaction.TYPE_EXPENSE;
            String category = "General"; // Default category

            showLoading(true);

            Transaction transaction = new Transaction(
                null, // userId will be set in FirebaseHelper
                description,
                amount,
                category,
                type,
                notes
            );

            firebaseHelper.saveTransaction(transaction,
                aVoid -> {
                    showLoading(false);
                    Toast.makeText(this, "Transaction saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                },
                e -> {
                    showLoading(false);
                    Toast.makeText(this, "Failed to save transaction: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });

        } catch (NumberFormatException e) {
            amountLayout.setError("Please enter a valid amount");
            amountEditText.requestFocus();
        }
    }

    private void showLoading(boolean show) {
        if (progressBar != null) {
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
        if (saveButton != null) {
            saveButton.setEnabled(!show);
            saveButton.setText(show ? "Saving..." : "Save Transaction");
        }
    }
}
