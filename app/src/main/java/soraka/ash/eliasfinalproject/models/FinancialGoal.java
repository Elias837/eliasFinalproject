package soraka.ash.eliasfinalproject.models;

/**
 * Financial Goal model class for Firebase Realtime Database.
 * Represents a financial goal with target amount, current progress, and deadline.
 * <p>
 * فئة نموذج الهدف المالي لقاعدة بيانات Firebase Realtime.
 * تمثل هدفاً مالياً مع المبلغ المستهدف، والتقدم الحالي، والموعد النهائي.
 */
public class FinancialGoal {
    private String goalId;
    private String userId;
    private String goalName;
    private double targetAmount;
    private double currentAmount;
    private String targetDate;
    private String notes;
    private long createdAt;
    private long updatedAt;
    private boolean isCompleted;

    /**
     * Default constructor required by Firebase. Initializes timestamps and default values.
     * <p>
     * المنشئ الافتراضي المطلوب بواسطة Firebase. يقوم بتهيئة الطوابع الزمنية والقيم الافتراضية.
     */
    public FinancialGoal() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.currentAmount = 0.0;
        this.isCompleted = false;
    }

    /**
     * Constructs a new FinancialGoal with specified details.
     * <p>
     * إنشاء هدف مالي جديد بالتفاصيل المحددة.
     *
     * @param userId Unique ID of the user owning the goal. معرف المستخدم الفريد صاحب الهدف.
     * @param goalName The name of the financial goal. اسم الهدف المالي.
     * @param targetAmount The total amount intended to be saved. المبلغ الإجمالي المراد توفيره.
     * @param targetDate The deadline for the goal. الموعد النهائي للهدف.
     * @param notes Additional information about the goal. معلومات إضافية حول الهدف.
     */
    public FinancialGoal(String userId, String goalName, double targetAmount, String targetDate, String notes) {
        this();
        this.userId = userId;
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
        this.notes = notes;
    }

    /**
     * @return The unique ID of the goal. المعرف الفريد للهدف.
     */
    public String getGoalId() { return goalId; }
    
    /**
     * @param goalId Sets the unique ID of the goal. تعيين المعرف الفريد للهدف.
     */
    public void setGoalId(String goalId) { this.goalId = goalId; }

    /**
     * @return The user ID associated with this goal. معرف المستخدم المرتبط بهذا الهدف.
     */
    public String getUserId() { return userId; }
    
    /**
     * @param userId Sets the user ID for this goal. تعيين معرف المستخدم لهذا الهدف.
     */
    public void setUserId(String userId) { this.userId = userId; }

    /**
     * @return The name of the goal. اسم الهدف.
     */
    public String getGoalName() { return goalName; }
    
    /**
     * @param goalName Sets the name of the goal. تعيين اسم الهدف.
     */
    public void setGoalName(String goalName) { this.goalName = goalName; }

    /**
     * @return The target amount of the goal. المبلغ المستهدف للهدف.
     */
    public double getTargetAmount() { return targetAmount; }
    
    /**
     * @param targetAmount Sets the target amount of the goal. تعيين المبلغ المستهدف للهدف.
     */
    public void setTargetAmount(double targetAmount) { this.targetAmount = targetAmount; }

    /**
     * @return The current progress amount of the goal. مبلغ التقدم الحالي للهدف.
     */
    public double getCurrentAmount() { return currentAmount; }
    
    /**
     * Sets the current progress amount and updates completion status.
     * <p>
     * يعين مبلغ التقدم الحالي ويحدث حالة الإكمال.
     *
     * @param currentAmount The new progress amount. مبلغ التقدم الجديد.
     */
    public void setCurrentAmount(double currentAmount) { 
        this.currentAmount = currentAmount;
        this.updatedAt = System.currentTimeMillis();
        checkCompletion();
    }

    /**
     * @return The target date for the goal. التاريخ المستهدف للهدف.
     */
    public String getTargetDate() { return targetDate; }
    
    /**
     * @param targetDate Sets the target date for the goal. تعيين التاريخ المستهدف للهدف.
     */
    public void setTargetDate(String targetDate) { this.targetDate = targetDate; }

    /**
     * @return Notes regarding the goal. ملاحظات بخصوص الهدف.
     */
    public String getNotes() { return notes; }
    
    /**
     * @param notes Sets the notes for the goal. تعيين الملاحظات للهدف.
     */
    public void setNotes(String notes) { this.notes = notes; }

    /**
     * @return The creation timestamp in milliseconds. طابع وقت الإنشاء بالملي ثانية.
     */
    public long getCreatedAt() { return createdAt; }
    
    /**
     * @param createdAt Sets the creation timestamp. تعيين طابع وقت الإنشاء.
     */
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    /**
     * @return The last update timestamp in milliseconds. طابع وقت آخر تحديث بالملي ثانية.
     */
    public long getUpdatedAt() { return updatedAt; }
    
    /**
     * @param updatedAt Sets the update timestamp. تعيين طابع وقت التحديث.
     */
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }

    /**
     * @return True if the goal is reached, false otherwise. صحيح إذا تم الوصول للهدف، خطأ خلاف ذلك.
     */
    public boolean isCompleted() { return isCompleted; }
    
    /**
     * @param completed Sets the completion status. تعيين حالة الإكمال.
     */
    public void setCompleted(boolean completed) { isCompleted = completed; }

    /**
     * Calculates the progress percentage of the goal.
     * <p>
     * يحسب النسبة المئوية للتقدم في الهدف.
     *
     * @return Percentage of progress (0 to 100). النسبة المئوية للتقدم (من 0 إلى 100).
     */
    public double getProgressPercentage() {
        if (targetAmount <= 0) return 0;
        return Math.min((currentAmount / targetAmount) * 100, 100);
    }

    /**
     * Calculates the remaining amount needed to reach the target.
     * <p>
     * يحسب المبلغ المتبقي المطلوب للوصول إلى الهدف.
     *
     * @return Remaining amount. المبلغ المتبقي.
     */
    public double getRemainingAmount() {
        return Math.max(targetAmount - currentAmount, 0);
    }

    /**
     * Internal method to check if the current amount meets or exceeds the target.
     * <p>
     * طريقة داخلية للتحقق مما إذا كان المبلغ الحالي يلبي أو يتجاوز الهدف.
     */
    private void checkCompletion() {
        this.isCompleted = currentAmount >= targetAmount;
    }

    /**
     * Adds a specific amount to the current progress.
     * <p>
     * يضيف مبلغاً محدداً إلى التقدم الحالي.
     *
     * @param amount The amount to add. المبلغ المراد إضافته.
     */
    public void addProgress(double amount) {
        setCurrentAmount(currentAmount + amount);
    }
}
