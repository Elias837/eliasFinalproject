package soraka.ash.eliasfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import soraka.ash.eliasfinalproject.adapter.BudgetAdapter;
import soraka.ash.eliasfinalproject.model.BudgetItem;

/**
 * Goals and budgeting management activity with RecyclerView support.
 * Allows users to view existing goals and create new ones via a floating action button.
 * Provides navigation to the AddGoal2Activity for goal creation.
 */
public class goalsAbudgeting extends AppCompatActivity {
    private FloatingActionButton addGoalFab;

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

        // Initialize the Floating Action Button for adding new goals
        addGoalFab = findViewById(R.id.addGoalFab);
        // Set up click listener to navigate to AddGoal2Activity when FAB is clicked
        addGoalFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to AddGoal2Activity
                Intent intent = new Intent(goalsAbudgeting.this, AddGoal2Activity.class);
                startActivity(intent);
            }
        });
    }
}
