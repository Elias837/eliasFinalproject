package soraka.ash.eliasfinalproject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import soraka.ash.eliasfinalproject.data.FirebaseHelper;
import soraka.ash.eliasfinalproject.models.FinancialGoal;

/**
 * Activity for creating and saving financial goals with comprehensive validation.
 * Allows users to set goal names, target amounts, target dates, and additional notes.
 * Features a date picker with minimum date validation and input validation.
 * 
 * نشاط لإنشاء وحفظ الأهداف المالية مع التحقق الشامل.
 * يسمح للمستخدمين بتعيين أسماء الأهداف والمبالغ المستهدفة والتواريخ المستهدفة وملاحظات إضافية.
 * يتميز منتقي التواريخ مع التحقق من التاريخ الأدنى والتحقق من الإدخال.
 */
public class AddGoal2Activity extends AppCompatActivity {
    private TextInputEditText goalNameEditText;
    private TextInputEditText targetAmountEditText;
    private TextInputEditText targetDateEditText;
    private TextInputEditText notesEditText;
    private MaterialButton saveButton;
    private Calendar calendar;
    private CircularProgressIndicator progressBar;
    private FirebaseHelper firebaseHelper;

    /**
     * Called when the activity is first created. Initializes all UI components,
     * sets up the toolbar with back navigation, configures the date picker,
     * and sets up the save button functionality.
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle)
     * 
     * يُستدعى عند إنشاء النشاط لأول مرة. يهيئ جميع مكونات واجهة المستخدم،
     * ويقوم بإعداد شريط الأدوات مع التنقل للخلف، ويكوين منتقي التواريخ،
     * ويقوم بإعداد وظيفة زر الحفظ.
     * @param savedInstanceState إذا كان النشاط يعاد تهيئته بعد إيقاف تشغيله مسبقاً
     *                           فإن هذا Bundle يحتوي على البيانات التي قدمها مؤخراً
     *                           في onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal2);

        // Initialize Firebase helper
        firebaseHelper = new FirebaseHelper();

        // Initialize views
        initializeViews();
        setupToolbar();
        setupDatePicker();
        setupSaveButton();
    }

    /**
     * Initializes all UI components by finding them in the layout.
     * Sets up references to EditText fields, buttons, and calendar instance.
     * 
     * يهيئ جميع مكونات واجهة المستخدم عن طريق العثور عليها في التخطيط.
     * يقوم بإعداد مراجع لحقول EditText والأزرار ومثيل التقويم.
     */
    private void initializeViews() {
        goalNameEditText = findViewById(R.id.goalNameEditText);
        targetAmountEditText = findViewById(R.id.targetAmountEditText);
        targetDateEditText = findViewById(R.id.targetDateEditText);
        notesEditText = findViewById(R.id.notesEditText);
        saveButton = findViewById(R.id.saveButton);
        progressBar = findViewById(R.id.progressBar);
        calendar = Calendar.getInstance();
    }

    /**
     * Sets up the toolbar with back navigation functionality.
     * Configures the navigation icon to close the activity when clicked.
     * 
     * يقوم بإعداد شريط الأدوات مع وظيفة التنقل للخلف.
     * يكوين أيقونة التنقل لإغلاق النشاط عند النقر عليها.
     */
    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    /**
     * Configures the date picker functionality for the target date input.
     * Sets default date to 1 month from current date and sets up click listeners
     * for the date input field and icons.
     * 
     * يكوين وظيفة منتقي التواريخ لإدخال التاريخ المستهدف.
     * يحدد التاريخ الافتراضي إلى شهر واحد من التاريخ الحالي ويقوم بإعداد مستمعي النقر
     * لحقل إدخال التاريخ والأيقونات.
     */
    private void setupDatePicker() {
        TextInputLayout dateLayout = findViewById(R.id.targetDateLayout);
        targetDateEditText = (TextInputEditText) dateLayout.getEditText();
        
        // Set default date to 1 month from now
        calendar.add(Calendar.MONTH, 1);
        updateDateInView();

        dateLayout.setEndIconOnClickListener(v -> showDatePicker());
        dateLayout.setStartIconOnClickListener(v -> showDatePicker());
        targetDateEditText.setOnClickListener(v -> showDatePicker());
    }

    /**
     * Displays a DatePickerDialog for selecting the target date.
     * Sets minimum date to tomorrow to prevent past date selection.
     * Updates the date display when a date is selected.
     * 
     * يعرض مربع حوار DatePickerDialog لاختيار التاريخ المستهدف.
     * يحدد التاريخ الأدنى ليوم الغد لمنع اختيار التواريخ الماضية.
     * يحدث عرض التاريخ عند تحديد تاريخ.
     */
    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateInView();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        
        // Set minimum date to tomorrow
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 86400000);
        datePickerDialog.show();
    }

    /**
     * Updates the date display in the EditText field with formatted date.
     * Uses "MMM d, yyyy" format (e.g., "Jan 15, 2024").
     * 
     * يحدث عرض التاريخ في حقل EditText مع التاريخ المنسق.
     * يستخدم تنسيق "MMM d, yyyy" (مثال: "Jan 15, 2024").
     */
    private void updateDateInView() {
        String dateFormat = "MMM d, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        targetDateEditText.setText(sdf.format(calendar.getTime()));
    }

    /**
     * Sets up click listeners for the save button and optional FAB.
     * Both buttons trigger the saveGoal() method when clicked.
     * 
     * يقوم بإعداد مستمعي النقر لزر الحفظ و FAB الاختياري.
     * كلا الزرين يشغلان طريقة saveGoal() عند النقر.
     */
    private void setupSaveButton() {
        saveButton.setOnClickListener(v -> saveGoal());
        
        // Also set up the FAB if you want both buttons to do the same
        View saveFab = findViewById(R.id.saveFab);
        if (saveFab != null) {
            saveFab.setOnClickListener(v -> saveGoal());
        }
    }

    /**
     * Validates all input fields and saves the goal if validation passes.
     * Performs comprehensive validation including required fields,
     * amount format validation, and positive amount checks.
     * Shows appropriate error messages for invalid inputs.
     * 
     * يتحقق من صحة جميع حقول الإدخال ويحفظ الهدف إذا نجح التحقق.
     * يقوم بالتحقق الشامل بما في ذلك الحقول المطلوبة،
     * والتحقق من تنسيق المبلغ، وفحص المبلغ الموجب.
     * يعرض رسائل خطأ مناسبة للإدخالات غير الصالحة.
     */
    private void saveGoal() {
        String goalName = goalNameEditText.getText().toString().trim();
        String targetAmountStr = targetAmountEditText.getText().toString().trim();
        String targetDate = targetDateEditText.getText().toString().trim();
        String notes = notesEditText.getText().toString().trim();

        // Basic validation
        if (goalName.isEmpty()) {
            goalNameEditText.setError("Please enter a goal name");
            goalNameEditText.requestFocus();
            return;
        }

        if (targetAmountStr.isEmpty()) {
            targetAmountEditText.setError("Please enter a target amount");
            targetAmountEditText.requestFocus();
            return;
        }

        if (targetDate.isEmpty()) {
            targetDateEditText.setError("Please select a target date");
            targetDateEditText.requestFocus();
            return;
        }

        try {
            double targetAmount = Double.parseDouble(targetAmountStr);
            if (targetAmount <= 0) {
                targetAmountEditText.setError("Amount must be greater than 0");
                targetAmountEditText.requestFocus();
                return;
            }

            // Show loading state
            showLoading(true);

            // Create financial goal object
            FinancialGoal goal = new FinancialGoal(
                null, // userId will be set in FirebaseHelper
                goalName,
                targetAmount,
                targetDate,
                notes
            );

            // Save to Firebase
            firebaseHelper.saveFinancialGoal(goal,
                aVoid -> {
                    showLoading(false);
                    Toast.makeText(this, "Goal saved successfully!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                },
                e -> {
                    showLoading(false);
                    Toast.makeText(this, "Failed to save goal: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });

        } catch (NumberFormatException e) {
            targetAmountEditText.setError("Please enter a valid amount");
            targetAmountEditText.requestFocus();
        }
    }

    /**
     * Shows or hides the loading state
     */
    private void showLoading(boolean show) {
        if (progressBar != null) {
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
        if (saveButton != null) {
            saveButton.setEnabled(!show);
            saveButton.setText(show ? "Saving..." : "Save Goal");
        }
    }

    /**
     * Handles the back button press event.
     * Shows a warning toast if the user has entered any data
     * that would be discarded by going back.
     * 
     * يتعامل مع حدث ضغط زر الرجوع.
     * يعرض رسالة تحذير إذا أدخل المستخدم أي بيانات
     * سيتم تجاهلها بالرجوع للخلف.
     */
    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        // Optional: Add a confirmation dialog if the user has entered any data
        // Show a dialog or toast to confirm discarding changes
        if (!goalNameEditText.getText().toString().trim().isEmpty() ||
            !targetAmountEditText.getText().toString().trim().isEmpty() ||
            !notesEditText.getText().toString().trim().isEmpty())
            Toast.makeText(this, "Changes will be discarded", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
