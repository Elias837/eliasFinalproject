package soraka.ash.eliasfinalproject.data.MyIncomeTable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.w3c.dom.Text;

@Entity
public class MyIncome
{
    /**
     * فئة تمثل عملية مالية داخل التطبيق
     */
        @PrimaryKey(autoGenerate = true)
        /** رقم العملية */
        @ColumnInfo
        public long transactionId;

        /** المبلغ المالي للعملية */

        public double amount;

        /** نوع العملية: دخل أو مصروف */

        public String type;

        /** عنوان قصير للعملية (مثلاً: راتب، مطعم، فواتير...) */
        public String shortTitle;

        /** وصف تفصيلي للعملية المالية */
        public String description;

        /** تاريخ تنفيذ العملية (بالميلي ثانية) */
        public long date;

        /** طريقة الدفع (نقداً، بطاقة، تحويل بنكي، إلخ) */

        public String paymentMethod;

        /** اسم الجهة أو الشخص المرتبط بالعملية */

        public String counterpart;

        /** هل تم تنفيذ العملية فعلياً أم لا */

        public boolean isCompleted;

        /** رقم التصنيف المالي (مثلاً: طعام، سكن، ترفيه...) */

        public long categoryId;

        /** المبلغ المتبقي بعد العملية */

        public double remainingBalance;

        /** الموقع الجغرافي أو المتجر الذي تمت فيه العملية */

        public String location;

        /** هل العملية متكررة شهرياً مثلاً */

        public boolean isRecurring;

        /** رقم المستخدم الذي قام بالعملية */

        public long userId;

        // ------------------ Getters ------------------

        public long getTransactionId() {
            return transactionId;
        }

        public double getAmount() {
            return amount;
        }

        public String getType() {
            return type;
        }

        public String getShortTitle() {
            return shortTitle;
        }

        public String getDescription() {
            return description;
        }

        public long getDate() {
            return date;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public String getCounterpart() {
            return counterpart;
        }

        public boolean isCompleted() {
            return isCompleted;
        }

        public long getCategoryId() {
            return categoryId;
        }

        public double getRemainingBalance() {
            return remainingBalance;
        }

        public String getLocation() {
            return location;
        }

        public boolean isRecurring() {
            return isRecurring;
        }

        public long getUserId() {
            return userId;
        }


        // ------------------ Setters ------------------

        public void setTransactionId(long transactionId) {
            this.transactionId = transactionId;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setShortTitle(String shortTitle) {
            this.shortTitle = shortTitle;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public void setCounterpart(String counterpart) {
            this.counterpart = counterpart;
        }

        public void setCompleted(boolean completed) {
            isCompleted = completed;
        }

        public void setCategoryId(long categoryId) {
            this.categoryId = categoryId;
        }

        public void setRemainingBalance(double remainingBalance) {
            this.remainingBalance = remainingBalance;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setRecurring(boolean recurring) {
            isRecurring = recurring;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        // ------------------ toString ------------------

        @Override
        public String toString() {
            return "MyTransaction{" +
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






