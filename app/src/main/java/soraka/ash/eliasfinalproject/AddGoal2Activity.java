package soraka.ash.eliasfinalproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import soraka.ash.eliasfinalproject.models.FinancialGoal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
import java.util.Locale;

public class AddGoal2Activity extends AppCompatActivity {

    private TextInputEditText goalNameEditText, targetAmountEditText, targetDateEditText, notesEditText;
    private MaterialButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal2);

        goalNameEditText = findViewById(R.id.goalNameEditText);
        targetAmountEditText = findViewById(R.id.targetAmountEditText);
        targetDateEditText = findViewById(R.id.targetDateEditText);
        notesEditText = findViewById(R.id.notesEditText);
        saveButton = findViewById(R.id.saveButton);
        
        if (targetDateEditText != null) {
            targetDateEditText.setOnClickListener(v -> showDatePickerDialog());
        }

        if (saveButton != null) {
            saveButton.setOnClickListener(v -> saveGoal());
        }
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    targetDateEditText.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
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
        if (userId == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        // Saving directly to the user-specific path without using FirebaseHelper
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users")
                .child(userId)
                .child("goals");
                
        String goalId = ref.push().getKey();
        FinancialGoal goal = new FinancialGoal(userId, title, amount, date, notes);
        
        if (goalId != null) {
            goal.setGoalId(goalId);
            ref.child(goalId).setValue(goal).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Goal Saved Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to save goal", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
