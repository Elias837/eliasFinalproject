package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import soraka.ash.eliasfinalproject.adapter.BudgetAdapter;
import soraka.ash.eliasfinalproject.data.FirebaseHelper;
import soraka.ash.eliasfinalproject.models.FinancialGoal;

/**
 * Activity for managing financial goals and budgeting.
 * Displays a list of financial goals and allows adding new ones or performing quick deposits.
 */
public class goalsAbudgeting extends AppCompatActivity {
    private FloatingActionButton addGoalFab;
    private RecyclerView goalsRecyclerView;
    private BudgetAdapter budgetAdapter;
    private List<FinancialGoal> budgetItemList;
    private MaterialToolbar toolbar;
    private TextInputEditText etSearch;

    // Quick Deposit Views
    private Spinner spinnerGoalSelect;
    private EditText etDepositAmount;
    private Button btnDeposit;
    private List<String> goalNamesList;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_budgeting);

        initViews();
        setupToolbar();
        setupRecyclerView();
        setupFab();
        setupSearchListener();
        setupQuickDeposit();
        loadGoalsFromFirebase();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        addGoalFab = findViewById(R.id.addGoalFab);
        goalsRecyclerView = findViewById(R.id.goalsRecyclerView);
        etSearch = findViewById(R.id.etSearch);
        spinnerGoalSelect = findViewById(R.id.spinnerGoalSelect);
        etDepositAmount = findViewById(R.id.etDepositAmount);
        btnDeposit = findViewById(R.id.btnDeposit);
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    private void setupRecyclerView() {
        budgetItemList = new ArrayList<>();
        budgetAdapter = new BudgetAdapter(this, budgetItemList);
        if (goalsRecyclerView != null) {
            goalsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            goalsRecyclerView.setAdapter(budgetAdapter);
            goalsRecyclerView.setNestedScrollingEnabled(false);
        }
    }

    private void setupFab() {
        if (addGoalFab != null) {
            addGoalFab.setOnClickListener(v -> {
                Intent intent = new Intent(goalsAbudgeting.this, AddGoal2Activity.class);
                startActivity(intent);
            });
        }
    }

    private void setupQuickDeposit() {
        goalNamesList = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, goalNamesList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinnerGoalSelect != null) {
            spinnerGoalSelect.setAdapter(spinnerAdapter);
        }

        if (btnDeposit != null) {
            btnDeposit.setOnClickListener(v -> handleQuickDeposit());
        }
    }

    /**
     * Handles the logic for adding an amount to the selected financial goal.
     */
    private void handleQuickDeposit() {
        if (spinnerGoalSelect == null || spinnerGoalSelect.getSelectedItem() == null) {
            Toast.makeText(this, "Please select a goal", Toast.LENGTH_SHORT).show();
            return;
        }

        String amountStr = etDepositAmount.getText().toString();
        if (TextUtils.isEmpty(amountStr)) {
            etDepositAmount.setError("Required");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            int selectedIndex = spinnerGoalSelect.getSelectedItemPosition();
            
            // Check if list is not empty and index is valid
            if (budgetItemList != null && !budgetItemList.isEmpty() && selectedIndex < budgetItemList.size()) {
                FinancialGoal selectedGoal = budgetItemList.get(selectedIndex);
                
                // CORRECT METHOD CALL: Use addProgress instead of getProgressPercentage
                selectedGoal.addProgress(amount);
                
                // Save to Firebase
                saveGoalProgress(selectedGoal);
            }
        } catch (NumberFormatException e) {
            etDepositAmount.setError("Invalid amount");
        }
    }

    private void saveGoalProgress(FinancialGoal goal) {
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) return;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(FirebaseHelper.USERS_NODE)
                .child(userId)
                .child(FirebaseHelper.GOALS_NODE)
                .child(goal.getGoalId());

        ref.setValue(goal).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(goalsAbudgeting.this, "Deposit successful!", Toast.LENGTH_SHORT).show();
                etDepositAmount.setText("");
                // The RecyclerView will update automatically because of the ValueEventListener in loadGoalsFromFirebase()
            } else {
                Toast.makeText(goalsAbudgeting.this, "Failed to update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSearchListener() {
        if (etSearch != null) {
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterGoals(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void filterGoals(String query) {
        List<FinancialGoal> filteredList = new ArrayList<>();
        for (FinancialGoal goal : budgetItemList) {
            if (goal.getGoalName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(goal);
            }
        }
        budgetAdapter.updateBudgetItems(filteredList);
    }

    private void loadGoalsFromFirebase() {
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) return;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(FirebaseHelper.USERS_NODE)
                .child(userId)
                .child(FirebaseHelper.GOALS_NODE);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                budgetItemList.clear();
                goalNamesList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot goalSnapshot : snapshot.getChildren()) {
                        FinancialGoal goal = goalSnapshot.getValue(FinancialGoal.class);
                        if (goal != null) {
                            budgetItemList.add(goal);
                            goalNamesList.add(goal.getGoalName());
                        }
                    }
                }
                
                if (spinnerAdapter != null) {
                    spinnerAdapter.notifyDataSetChanged();
                }
                
                if (etSearch != null && etSearch.getText() != null && !etSearch.getText().toString().isEmpty()) {
                    filterGoals(etSearch.getText().toString());
                } else {
                    budgetAdapter.updateBudgetItems(new ArrayList<>(budgetItemList));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("goalsAbudgeting", "Failed to load goals: " + error.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGoalsFromFirebase();
    }
}
