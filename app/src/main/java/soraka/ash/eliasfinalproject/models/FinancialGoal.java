package soraka.ash.eliasfinalproject.models;

import com.google.firebase.database.Exclude;

/**
 * Financial Goal model class for Firebase Realtime Database.
 */
@SuppressWarnings("unused")
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

    public FinancialGoal() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    public FinancialGoal(String userId, String goalName, double targetAmount, String targetDate, String notes) {
        this();
        this.userId = userId;
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
        this.notes = notes;
        this.currentAmount = 0.0;
    }

    public String getGoalId() { return goalId; }
    public void setGoalId(String goalId) { this.goalId = goalId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getGoalName() { return goalName; }
    public void setGoalName(String goalName) { this.goalName = goalName; }

    public double getTargetAmount() { return targetAmount; }
    public void setTargetAmount(double targetAmount) { this.targetAmount = targetAmount; }

    public double getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(double currentAmount) { 
        this.currentAmount = currentAmount; 
        checkCompletion();
    }

    public String getTargetDate() { return targetDate; }
    public void setTargetDate(String targetDate) { this.targetDate = targetDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    /**
     * Adds an amount to the current progress.
     * يضيف مبلغاً إلى التقدم الحالي للهدف.
     */
    public void addProgress(double amount) {
        this.currentAmount += amount;
        this.updatedAt = System.currentTimeMillis();
        checkCompletion();
    }

    @Exclude
    public int getProgressPercentage() {
        if (targetAmount <= 0) return 0;
        double calc = (currentAmount / targetAmount) * 100;
        return (int) Math.min(calc, 100);
    }

    private void checkCompletion() {
        this.isCompleted = currentAmount >= targetAmount;
    }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}
