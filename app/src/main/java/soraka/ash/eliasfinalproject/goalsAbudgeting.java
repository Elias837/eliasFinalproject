package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import soraka.ash.eliasfinalproject.adapter.BudgetAdapter;
import soraka.ash.eliasfinalproject.data.FirebaseHelper;
import soraka.ash.eliasfinalproject.model.BudgetItem;
import soraka.ash.eliasfinalproject.models.FinancialGoal;

public class goalsAbudgeting extends AppCompatActivity {
    private FloatingActionButton addGoalFab;
    private RecyclerView goalsRecyclerView;
    private BudgetAdapter budgetAdapter;
    private List<FinancialGoal> budgetItemList;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_budgeting);

        firebaseHelper = new FirebaseHelper();

        addGoalFab = findViewById(R.id.addGoalFab);
        goalsRecyclerView = findViewById(R.id.goalsRecyclerView);

        budgetItemList = new ArrayList<>();
        budgetAdapter = new BudgetAdapter(this, budgetItemList);
        
        if (goalsRecyclerView != null) {
            goalsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            goalsRecyclerView.setAdapter(budgetAdapter);
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
        FirebaseDatabase.getInstance().getReference().child(FirebaseHelper.GOALS_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                budgetItemList.clear();
                for (DataSnapshot goalSnapshot : snapshot.getChildren()) {
                    FinancialGoal goal = goalSnapshot.getValue(FinancialGoal.class);
                    if (goal != null) {
                        budgetItemList.add(goal);
                    }
                }
                budgetAdapter.updateBudgetItems(budgetItemList);
                budgetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("goalsAbudgeting", "Failed to load goals from Firebase", error.toException());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGoalsFromFirebase();
    }
}
