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

/**
 * Activity for managing financial goals and budgeting.
 * Displays a list of financial goals retrieved from Firebase and allows adding new goals.
 * <p>
 * نشاط لإدارة الأهداف المالية والميزانية.
 * يعرض قائمة بالأهداف المالية المسترجعة من Firebase ويسمح بإضافة أهداف جديدة.
 */
public class goalsAbudgeting extends AppCompatActivity {
    /** 
     * Floating Action Button to navigate to the Add Goal screen.
     * زر عائم للانتقال إلى شاشة إضافة هدف.
     */
    private FloatingActionButton addGoalFab;

    /** 
     * RecyclerView to display the list of financial goals.
     * عرض تدويري (RecyclerView) لعرض قائمة الأهداف المالية.
     */
    private RecyclerView goalsRecyclerView;

    /** 
     * Adapter for the goals RecyclerView to bind goal data to views.
     * محول (Adapter) للعرض التدويري لربط بيانات الأهداف بالواجهات.
     */
    private BudgetAdapter budgetAdapter;

    /** 
     * List containing the financial goals to be displayed in the UI.
     * قائمة تحتوي على الأهداف المالية التي سيتم عرضها في واجهة المستخدم.
     */
    private List<FinancialGoal> budgetItemList;

    /** 
     * Material design toolbar for the activity's header.
     * شريط أدوات (Toolbar) بتصميم Material لرأس النشاط.
     */
    private MaterialToolbar toolbar;

    /**
     * Initializes the activity, sets up the toolbar, RecyclerView, and Floating Action Button.
     * <p>
     * يقوم بتهيئة النشاط، وإعداد شريط الأدوات، والعرض التدويري، والزر العائم.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down.
     *                           إذا تمت إعادة تهيئة النشاط بعد إغلاقه سابقاً.
     */
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
            // Disable nested scrolling for smoother interaction inside a ScrollView
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

    /**
     * Loads the current user's financial goals from Firebase Realtime Database.
     * Listens for real-time updates and refreshes the adapter data whenever data changes.
     * <p>
     * يحمل الأهداف المالية للمستخدم الحالي من قاعدة بيانات Firebase Realtime.
     * يستمع للتحديثات في الوقت الفعلي ويحدث بيانات المحول كلما تغيرت البيانات.
     */
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

    /**
     * Refreshes the goal list from Firebase when the activity is resumed to ensure data consistency.
     * <p>
     * يحدث قائمة الأهداف من Firebase عند استئناف النشاط لضمان اتساق البيانات.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadGoalsFromFirebase();
    }
}
