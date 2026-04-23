package soraka.ash.eliasfinalproject;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import soraka.ash.eliasfinalproject.data.FirebaseHelper;
import soraka.ash.eliasfinalproject.models.FinancialGoal;

public class EditGoalActivity extends AppCompatActivity {

    private TextInputEditText etEditGoalName, etEditGoalAmount;
    private MaterialButton btnUpdateGoal, btnDeleteGoal;
    private FirebaseHelper firebaseHelper;
    private FinancialGoal currentGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);

        Toolbar toolbar = findViewById(R.id.editGoalToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        etEditGoalName = findViewById(R.id.etEditGoalName);
        etEditGoalAmount = findViewById(R.id.etEditGoalAmount);
        btnUpdateGoal = findViewById(R.id.btnUpdateGoal);
        btnDeleteGoal = findViewById(R.id.btnDeleteGoal);

        firebaseHelper = new FirebaseHelper();

        if (getIntent().hasExtra("goal_data")) {
            currentGoal = (FinancialGoal) getIntent().getSerializableExtra("goal_data");
            if (currentGoal != null) {
                // Fixed: Changed getTitle() to getGoalName()
                etEditGoalName.setText(currentGoal.getGoalName());
                etEditGoalAmount.setText(String.valueOf(currentGoal.getTargetAmount()));
            }
        }

        btnUpdateGoal.setOnClickListener(v -> updateGoal());
        btnDeleteGoal.setOnClickListener(v -> deleteGoal());
    }

    private void updateGoal() {
        if (currentGoal == null) return;
        
        String name = etEditGoalName.getText() != null ? etEditGoalName.getText().toString().trim() : "";
        String amountStr = etEditGoalAmount.getText() != null ? etEditGoalAmount.getText().toString().trim() : "";

        if (name.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Fixed: Changed setTitle() to setGoalName()
        currentGoal.setGoalName(name);
        try {
            currentGoal.setTargetAmount(Double.parseDouble(amountStr));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            return;
        }

       updateGoalToFirebase(currentGoal);
        Toast.makeText(this, "Goal Updated", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteGoal() {
        if (currentGoal == null) return;
       deleteGoalToFirebase(currentGoal);

    }
    private void updateGoalToFirebase(FinancialGoal goal) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FirebaseHelper.GOALS_NODE);

        ref.child(goal.getGoalId()).setValue(goal).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Goal updated!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update goal", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deleteGoalToFirebase(FinancialGoal goal) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FirebaseHelper.GOALS_NODE);

        ref.child(goal.getGoalId()).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Goal deleted!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to delete goal", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
