package soraka.ash.eliasfinalproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

import soraka.ash.eliasfinalproject.models.FinancialGoal;

public class EditGoalActivity extends AppCompatActivity {

    private TextInputEditText etName, etAmount, etDate, etNotes;
    private MaterialButton btnSave, btnDelete;
    private String goalId;
    private DatabaseReference goalRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);

        goalId = getIntent().getStringExtra("GOAL_ID");
        String userId = FirebaseAuth.getInstance().getUid();

        if (goalId == null || userId == null) {
            Toast.makeText(this, "Error: Goal not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        goalRef = FirebaseDatabase.getInstance().getReference("users")
                .child(userId)
                .child("goals")
                .child(goalId);

        initViews();
        loadGoalData();
    }

    private void initViews() {
        etName = findViewById(R.id.etEditGoalName);
        etAmount = findViewById(R.id.etEditGoalAmount);
        etDate = findViewById(R.id.etEditGoalDate);
        etNotes = findViewById(R.id.etEditGoalNotes);
        btnSave = findViewById(R.id.btnUpdateGoal);
        btnDelete = findViewById(R.id.btnDeleteGoal);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.editGoalToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        etDate.setOnClickListener(v -> showDatePicker());
        btnSave.setOnClickListener(v -> updateGoal());
        btnDelete.setOnClickListener(v -> deleteGoal());
    }

    private void loadGoalData() {
        goalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FinancialGoal goal = snapshot.getValue(FinancialGoal.class);
                if (goal != null) {
                    etName.setText(goal.getGoalName());
                    etAmount.setText(String.valueOf(goal.getTargetAmount()));
                    etDate.setText(goal.getTargetDate());
                    etNotes.setText(goal.getNotes());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditGoalActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String date = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
            etDate.setText(date);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateGoal() {
        String name = etName.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(amountStr)) {
            Toast.makeText(this, "Name and Amount are required", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        goalRef.child("goalName").setValue(name);
        goalRef.child("targetAmount").setValue(amount);
        goalRef.child("targetDate").setValue(date);
        goalRef.child("notes").setValue(notes).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditGoalActivity.this, "Goal Updated!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void deleteGoal() {
        goalRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditGoalActivity.this, "Goal Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
