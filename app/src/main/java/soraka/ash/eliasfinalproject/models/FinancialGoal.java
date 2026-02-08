package soraka.ash.eliasfinalproject.models;

/**
 * Financial Goal model class for Firebase Realtime Database.
 * Represents a financial goal with target amount, current progress, and deadline.
 * 
 * This class is used to serialize/deserialize goal data
 * when working with Firebase Realtime Database operations.
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

    // Default constructor required by Firebase
    public FinancialGoal() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.currentAmount = 0.0;
        this.isCompleted = false;
    }

    public FinancialGoal(String userId, String goalName, double targetAmount, String targetDate, String notes) {
        this();
        this.userId = userId;
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
        this.notes = notes;
    }

    // Getters and setters
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
        this.updatedAt = System.currentTimeMillis();
        checkCompletion();
    }

    public String getTargetDate() { return targetDate; }
    public void setTargetDate(String targetDate) { this.targetDate = targetDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    // Utility methods
    public double getProgressPercentage() {
        if (targetAmount <= 0) return 0;
        return Math.min((currentAmount / targetAmount) * 100, 100);
    }

    public double getRemainingAmount() {
        return Math.max(targetAmount - currentAmount, 0);
    }

    private void checkCompletion() {
        this.isCompleted = currentAmount >= targetAmount;
    }

    public void addProgress(double amount) {
        setCurrentAmount(currentAmount + amount);
    }
}
