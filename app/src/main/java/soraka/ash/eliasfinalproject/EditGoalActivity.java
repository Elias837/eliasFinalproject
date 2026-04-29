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

/**
 * Activity for editing or deleting an existing financial goal.
 * Fetches goal details from Firebase and allows updating specific fields.
 *
 * نشاط لتعديل أو حذف هدف مالي موجود.
 * يجلب تفاصيل الهدف من Firebase ويسمح بتحديث حقول معينة.
 */
public class EditGoalActivity extends AppCompatActivity {

    private TextInputEditText etName, etAmount, etDate, etNotes;
    private MaterialButton btnSave, btnDelete;
    private String goalId;
    private DatabaseReference goalRef;

    /**
     * Called when the activity is starting. Initializes Firebase references and UI components.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down.
     *
     * يتم استدعاؤه عند بدء النشاط. يقوم بتهيئة مراجع Firebase ومكونات واجهة المستخدم.
     * @param savedInstanceState إذا تم إعادة تهيئة النشاط بعد إغلاقه سابقاً.
     */
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

    /**
     * Initializes all UI widgets and sets up their listeners.
     *
     * يقوم بتهيئة جميع عناصر واجهة المستخدم وإعداد مستمعي الأحداث الخاصة بها.
     */
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

    /**
     * Fetches current goal data from Firebase and populates the input fields.
     *
     * يجلب بيانات الهدف الحالية من Firebase ويملأ حقول الإدخال.
     */
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

    /**
     * Shows a DatePickerDialog for selecting a new target date.
     *
     * يظهر نافذة اختيار التاريخ لتحديد تاريخ مستهدف جديد.
     */
    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String date = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
            etDate.setText(date);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Validates and updates the goal information in Firebase Realtime Database.
     *
     * يتحقق من البيانات ويحدث معلومات الهدف في قاعدة بيانات Firebase.
     */
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

    /**
     * Removes the current goal from Firebase Realtime Database.
     *
     * يحذف الهدف الحالي من قاعدة بيانات Firebase.
     */
    private void deleteGoal() {
        goalRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditGoalActivity.this, "Goal Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
