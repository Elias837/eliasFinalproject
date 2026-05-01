package soraka.ash.eliasfinalproject.data.MyIncomeTable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing an income transaction in the local Room database.
 * <p>
 * فئة تمثل عملية دخل مالي في قاعدة بيانات Room المحلية.
 */
@Entity
public class MyIncome {

    /** 
     * Unique identifier for the transaction. 
     * معرف فريد للعملية المالية. 
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public long transactionId;

    /** 
     * Monetary amount of the transaction. 
     * المبلغ المالي للعملية. 
     */
    public double amount;

    /** 
     * Type of transaction (e.g., Income, Expense). 
     * نوع العملية (مثل: دخل أو مصروف). 
     */
    public String type;

    /** 
     * A short title for the transaction. 
     * عنوان قصير للعملية. 
     */
    public String shortTitle;

    /** 
     * Detailed description of the transaction. 
     * وصف تفصيلي للعملية المالية. 
     */
    public String description;

    /** 
     * Date of the transaction in milliseconds. 
     * تاريخ تنفيذ العملية بالملي ثانية. 
     */
    public long date;

    /** 
     * Method used for payment (Cash, Card, etc.). 
     * طريقة الدفع (نقداً، بطاقة، إلخ). 
     */
    public String paymentMethod;

    /** 
     * Person or entity related to the transaction. 
     * الجهة أو الشخص المرتبط بالعملية. 
     */
    public String counterpart;

    /** 
     * Flag indicating if the transaction is completed. 
     * حالة إكمال العملية. 
     */
    public boolean isCompleted;

    /** 
     * Category ID for classification (Food, Rent, etc.). 
     * رقم التصنيف المالي. 
     */
    public long categoryId;

    /** 
     * Remaining balance after this transaction. 
     * الرصيد المتبقي بعد العملية. 
     */
    public double remainingBalance;

    /** 
     * Location where the transaction took place. 
     * الموقع الذي تمت فيه العملية. 
     */
    public String location;

    /** 
     * Flag indicating if the transaction repeats monthly. 
     * هل العملية متكررة شهرياً. 
     */
    public boolean isRecurring;

    /** 
     * ID of the user who owns this record. 
     * رقم المستخدم صاحب العملية. 
     */
    public long userId;

    // ------------------ Getters ------------------

    /** @return Transaction ID. معرف العملية. */
    public long getTransactionId() { return transactionId; }

    /** @return Transaction amount. مبلغ العملية. */
    public double getAmount() { return amount; }

    /** @return Transaction type. نوع العملية. */
    public String getType() { return type; }

    /** @return Short title. عنوان قصير. */
    public String getShortTitle() { return shortTitle; }

    /** @return Detailed description. وصف تفصيلي. */
    public String getDescription() { return description; }

    /** @return Transaction date. تاريخ العملية. */
    public long getDate() { return date; }

    /** @return Payment method. طريقة الدفع. */
    public String getPaymentMethod() { return paymentMethod; }

    /** @return Transaction counterpart. الجهة المقابلة. */
    public String getCounterpart() { return counterpart; }

    /** @return Completion status. حالة الإكمال. */
    public boolean isCompleted() { return isCompleted; }

    /** @return Category identifier. رقم التصنيف. */
    public long getCategoryId() { return categoryId; }

    /** @return Remaining balance. الرصيد المتبقي. */
    public double getRemainingBalance() { return remainingBalance; }

    /** @return Location string. الموقع. */
    public String getLocation() { return location; }

    /** @return True if recurring. إذا كانت متكررة. */
    public boolean isRecurring() { return isRecurring; }

    /** @return User ID. رقم المستخدم. */
    public long getUserId() { return userId; }

    // ------------------ Setters ------------------

    /** @param transactionId Sets transaction ID. تعيين معرف العملية. */
    public void setTransactionId(long transactionId) { this.transactionId = transactionId; }

    /** @param amount Sets transaction amount. تعيين مبلغ العملية. */
    public void setAmount(double amount) { this.amount = amount; }

    /** @param type Sets transaction type. تعيين نوع العملية. */
    public void setType(String type) { this.type = type; }

    /** @param shortTitle Sets short title. تعيين عنوان قصير. */
    public void setShortTitle(String shortTitle) { this.shortTitle = shortTitle; }

    /** @param description Sets detailed description. تعيين وصف تفصيلي. */
    public void setDescription(String description) { this.description = description; }

    /** @param date Sets transaction date. تعيين تاريخ العملية. */
    public void setDate(long date) { this.date = date; }

    /** @param paymentMethod Sets payment method. تعيين طريقة الدفع. */
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    /** @param counterpart Sets counterpart entity. تعيين الجهة المقابلة. */
    public void setCounterpart(String counterpart) { this.counterpart = counterpart; }

    /** @param completed Sets completion flag. تعيين حالة الإكمال. */
    public void setCompleted(boolean completed) { isCompleted = completed; }

    /** @param categoryId Sets category ID. تعيين رقم التصنيف. */
    public void setCategoryId(long categoryId) { this.categoryId = categoryId; }

    /** @param remainingBalance Sets remaining balance. تعيين الرصيد المتبقي. */
    public void setRemainingBalance(double remainingBalance) { this.remainingBalance = remainingBalance; }

    /** @param location Sets location. تعيين الموقع. */
    public void setLocation(String location) { this.location = location; }

    /** @param recurring Sets recurring status. تعيين حالة التكرار. */
    public void setRecurring(boolean recurring) { isRecurring = recurring; }

    /** @param userId Sets user ID. تعيين رقم المستخدم. */
    public void setUserId(long userId) { this.userId = userId; }

    /**
     * Returns a string representation of the income object.
     * <p>
     * يرجع تمثيلاً نصياً لكائن الدخل.
     */
    @Override
    public String toString() {
        return "MyIncome{" +
                "transactionId=" + transactionId +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", shortTitle='" + shortTitle + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", counterpart='" + counterpart + '\'' +
                ", isCompleted=" + isCompleted +
                ", categoryId=" + categoryId +
                ", remainingBalance=" + remainingBalance +
                ", location='" + location + '\'' +
                ", isRecurring=" + isRecurring +
                ", userId=" + userId +
                '}';
    }
}
