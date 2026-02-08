package soraka.ash.eliasfinalproject.models;

/**
 * Transaction model class for Firebase Realtime Database.
 * Represents a financial transaction with amount, category, and type.
 * 
 * This class is used to serialize/deserialize transaction data
 * when working with Firebase Realtime Database operations.
 */
public class Transaction {
    public static final String TYPE_INCOME = "income";
    public static final String TYPE_EXPENSE = "expense";

    private String transactionId;
    private String userId;
    private String description;
    private double amount;
    private String category;
    private String type; // income or expense
    private long timestamp;
    private String notes;
    private String goalId; // Optional: link to a financial goal

    // Default constructor required by Firebase
    public Transaction() {
        this.timestamp = System.currentTimeMillis();
    }

    public Transaction(String userId, String description, double amount, String category, String type, String notes) {
        this();
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.notes = notes;
    }

    // Getters and setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getGoalId() { return goalId; }
    public void setGoalId(String goalId) { this.goalId = goalId; }

    // Utility methods
    public boolean isIncome() {
        return TYPE_INCOME.equals(type);
    }

    public boolean isExpense() {
        return TYPE_EXPENSE.equals(type);
    }

    public String getFormattedAmount() {
        return (isIncome() ? "+" : "-") + String.format("%.2f", amount);
    }
}
