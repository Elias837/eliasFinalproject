package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class goalsAbudgeting extends AppCompatActivity {
    private FloatingActionButton addGoalFab;
    private RecyclerView goalsRecyclerView;
    private BudgetAdapter budgetAdapter;
    private List<FinancialGoal> budgetItemList;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_budgeting);

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        addGoalFab = findViewById(R.id.addGoalFab);
        goalsRecyclerView = findViewById(R.id.goalsRecyclerView);

        budgetItemList = new ArrayList<>();
        budgetAdapter = new BudgetAdapter(this, budgetItemList);
        
        if (goalsRecyclerView != null) {
            goalsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            goalsRecyclerView.setAdapter(budgetAdapter);
            // This is important for ScrollView interaction
            goalsRecyclerView.setNestedScrollingEnabled(false);
        }

        if (addGoalFab != null) {
            addGoalFab.setOnClickListener(v -> {
                Intent intent = new Intent(goalsAbudgeting.this, AddGoal2Activity.class);
                startActivity(intent);
            });
        }
        
        loadGoalsFromFirebase();
    }

    private void loadGoalsFromFirebase() {
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) {
            Log.e("goalsAbudgeting", "User ID is null");
            return;
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(FirebaseHelper.USERS_NODE)
                .child(userId)
                .child(FirebaseHelper.GOALS_NODE);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                budgetItemList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot goalSnapshot : snapshot.getChildren()) {
                        FinancialGoal goal = goalSnapshot.getValue(FinancialGoal.class);
                        if (goal != null) {
                            budgetItemList.add(goal);
                        }
                    }
                } else {
                    Log.d("goalsAbudgeting", "No goals found in database for user: " + userId);
                }
                budgetAdapter.updateBudgetItems(new ArrayList<>(budgetItemList));
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
