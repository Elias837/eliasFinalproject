package soraka.ash.eliasfinalproject.models;

/**
 * Transaction model class for Firebase Realtime Database.
 * Represents a financial transaction with amount, category, and type.
 * <p>
 * فئة نموذج المعاملة لقاعدة بيانات Firebase Realtime.
 * تمثل معاملة مالية مع المبلغ، الفئة، والنوع.
 */
public class Transaction {
    /** Constant representing an income transaction type. ثابت يمثل نوع معاملة الدخل. */
    public static final String TYPE_INCOME = "income";
    /** Constant representing an expense transaction type. ثابت يمثل نوع معاملة المصروفات. */
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

    /**
     * Default constructor required by Firebase. Initializes the timestamp to current time.
     * <p>
     * المنشئ الافتراضي المطلوب بواسطة Firebase. يقوم بتهيئة الطابع الزمني للوقت الحالي.
     */
    public Transaction() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Constructs a new Transaction with specified details.
     * <p>
     * إنشاء معاملة جديدة بالتفاصيل المحددة.
     *
     * @param userId The ID of the user who made the transaction. معرف المستخدم الذي أجرى المعاملة.
     * @param description A brief description of the transaction. وصف موجز للمعاملة.
     * @param amount The monetary value of the transaction. القيمة المالية للمعاملة.
     * @param category The category of the transaction (e.g., Food, Salary). فئة المعاملة (مثل الطعام، الراتب).
     * @param type The type of transaction (income or expense). نوع المعاملة (دخل أو مصروف).
     * @param notes Optional additional notes. ملاحظات إضافية اختيارية.
     */
    public Transaction(String userId, String description, double amount, String category, String type, String notes) {
        this();
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.notes = notes;
    }

    /** @return The unique ID of the transaction. المعرف الفريد للمعاملة. */
    public String getTransactionId() { return transactionId; }
    /** @param transactionId Sets the transaction ID. تعيين معرف المعاملة. */
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    /** @return The user ID associated with this transaction. معرف المستخدم المرتبط بهذه المعاملة. */
    public String getUserId() { return userId; }
    /** @param userId Sets the user ID. تعيين معرف المستخدم. */
    public void setUserId(String userId) { this.userId = userId; }

    /** @return The description of the transaction. وصف المعاملة. */
    public String getDescription() { return description; }
    /** @param description Sets the transaction description. تعيين وصف المعاملة. */
    public void setDescription(String description) { this.description = description; }

    /** @return The amount of the transaction. مبلغ المعاملة. */
    public double getAmount() { return amount; }
    /** @param amount Sets the transaction amount. تعيين مبلغ المعاملة. */
    public void setAmount(double amount) { this.amount = amount; }

    /** @return The category of the transaction. فئة المعاملة. */
    public String getCategory() { return category; }
    /** @param category Sets the transaction category. تعيين فئة المعاملة. */
    public void setCategory(String category) { this.category = category; }

    /** @return The type of the transaction (income/expense). نوع المعاملة (دخل/مصروف). */
    public String getType() { return type; }
    /** @param type Sets the transaction type. تعيين نوع المعاملة. */
    public void setType(String type) { this.type = type; }

    /** @return The timestamp when the transaction occurred. الطابع الزمني لوقت حدوث المعاملة. */
    public long getTimestamp() { return timestamp; }
    /** @param timestamp Sets the transaction timestamp. تعيين الطابع الزمني للمعاملة. */
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    /** @return Additional notes for the transaction. ملاحظات إضافية للمعاملة. */
    public String getNotes() { return notes; }
    /** @param notes Sets the transaction notes. تعيين ملاحظات المعاملة. */
    public void setNotes(String notes) { this.notes = notes; }

    /** @return The linked goal ID, if any. معرف الهدف المرتبط، إن وجد. */
    public String getGoalId() { return goalId; }
    /** @param goalId Sets the linked goal ID. تعيين معرف الهدف المرتبط. */
    public void setGoalId(String goalId) { this.goalId = goalId; }

    /**
     * Checks if the transaction is of type income.
     * <p>
     * يتحقق مما إذا كانت المعاملة من نوع الدخل.
     *
     * @return True if income, false otherwise. صحيح إذا كانت دخلاً، خطأ خلاف ذلك.
     */
    public boolean isIncome() {
        return TYPE_INCOME.equals(type);
    }

    /**
     * Checks if the transaction is of type expense.
     * <p>
     * يتحقق مما إذا كانت المعاملة من نوع المصروفات.
     *
     * @return True if expense, false otherwise. صحيح إذا كانت مصروفات، خطأ خلاف ذلك.
     */
    public boolean isExpense() {
        return TYPE_EXPENSE.equals(type);
    }

    /**
     * Returns the amount formatted as a string with a sign (+/-).
     * <p>
     * يرجع المبلغ منسقاً كسلسلة نصية مع إشارة (+/-).
     *
     * @return Formatted amount string. سلسلة نصية للمبلغ المنسق.
     */
    public String getFormattedAmount() {
        return (isIncome() ? "+" : "-") + String.format("%.2f", amount);
    }
}
