package soraka.ash.eliasfinalproject;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import soraka.ash.eliasfinalproject.data.FirebaseHelper;
import soraka.ash.eliasfinalproject.models.FinancialGoal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddGoal2Activity extends AppCompatActivity {

    private TextInputEditText goalNameEditText, targetAmountEditText, targetDateEditText, notesEditText;
    private MaterialButton saveButton;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal2);

        // Fixing mismatched IDs between Java and XML
        goalNameEditText = findViewById(R.id.goalNameEditText);
        targetAmountEditText = findViewById(R.id.targetAmountEditText);
        targetDateEditText = findViewById(R.id.targetDateEditText);
        notesEditText = findViewById(R.id.notesEditText);
        saveButton = findViewById(R.id.saveButton);
        
        firebaseHelper = new FirebaseHelper();

        if (saveButton != null) {
            saveButton.setOnClickListener(v -> saveGoal());
        }
    }

    private void saveGoal() {
        if (goalNameEditText == null || targetAmountEditText == null) return;

        String title = goalNameEditText.getText() != null ? goalNameEditText.getText().toString().trim() : "";
        String amountStr = targetAmountEditText.getText() != null ? targetAmountEditText.getText().toString().trim() : "";
        String date = targetDateEditText != null && targetDateEditText.getText() != null ? targetDateEditText.getText().toString().trim() : "";
        String notes = notesEditText != null && notesEditText.getText() != null ? notesEditText.getText().toString().trim() : "";

        if (title.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount format", Toast.LENGTH_SHORT).show();
            return;
        }
        String userId = FirebaseAuth.getInstance().getUid();
        FinancialGoal goal = new FinancialGoal(userId, title, amount, date, notes);

        saveGoalToFirebase(goal);
        
        Toast.makeText(this, "Goal Saved!", Toast.LENGTH_SHORT).show();
        finish();
    }


    private void saveGoalToFirebase(FinancialGoal goal) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FirebaseHelper.GOALS_NODE);

        ref.child(goal.getGoalId()).setValue(goal).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Goal Saved!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to save goal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
