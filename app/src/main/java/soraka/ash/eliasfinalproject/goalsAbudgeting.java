package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import soraka.ash.eliasfinalproject.adapter.BudgetAdapter;
import soraka.ash.eliasfinalproject.data.FirebaseHelper;
import soraka.ash.eliasfinalproject.model.BudgetItem;
import soraka.ash.eliasfinalproject.models.FinancialGoal;

/**
 * Goals and budgeting management activity with RecyclerView support.
 * Allows users to view existing goals and create new ones via a floating action button.
 * Provides navigation to the AddGoal2Activity for goal creation.
 */
public class goalsAbudgeting extends AppCompatActivity {
    private FloatingActionButton addGoalFab;
    private RecyclerView goalsRecyclerView;
    private BudgetAdapter budgetAdapter;
    private List<BudgetItem> budgetItemList;
    private FirebaseHelper firebaseHelper;

    /**
     * Called when the activity is first created. Initializes the floating action button
     * and sets up its click listener to navigate to the goal creation activity.
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_budgeting);

        // Initialize Firebase helper
        firebaseHelper = new FirebaseHelper();

        // Initialize views
        initializeViews();
        setupRecyclerView();
        setupFabClickListener();
        
        // Load goals from Firebase
        loadGoalsFromFirebase();
    }

    /**
     * Initialize all UI components
     */
    private void initializeViews() {
        addGoalFab = findViewById(R.id.addGoalFab);
        goalsRecyclerView = findViewById(R.id.goalsRecyclerView);
    }

    /**
     * Set up the RecyclerView with adapter
     */
    private void setupRecyclerView() {
        budgetItemList = new ArrayList<>();
        budgetAdapter = new BudgetAdapter(this, budgetItemList);
        
        if (goalsRecyclerView != null) {
            goalsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            goalsRecyclerView.setAdapter(budgetAdapter);
        }
    }

    /**
     * Set up the FAB click listener
     */
    private void setupFabClickListener() {
        if (addGoalFab != null) {
            addGoalFab.setOnClickListener(v -> {
                Intent intent = new Intent(goalsAbudgeting.this, AddGoal2Activity.class);
                startActivity(intent);
            });
        }
    }

    /**
     * Load financial goals from Firebase and convert to BudgetItems
     */
    private void loadGoalsFromFirebase() {
        firebaseHelper.getAllFinancialGoals(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                budgetItemList.clear();
                
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FinancialGoal goal = snapshot.getValue(FinancialGoal.class);
                    if (goal != null) {
                        // Convert FinancialGoal to BudgetItem for display
                        BudgetItem budgetItem = new BudgetItem(
                            goal.getGoalName(),
                            goal.getCurrentAmount(),
                            goal.getTargetAmount()
                        );
                        budgetItemList.add(budgetItem);
                    }
                }
                
                budgetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                android.util.Log.e("goalsAbudgeting", "Error loading goals: " + databaseError.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload goals when activity resumes (in case new goals were added)
        loadGoalsFromFirebase();
    }
}
