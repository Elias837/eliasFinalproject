package soraka.ash.eliasfinalproject.models;

/**
 * Model class representing a budget category item with progress tracking.
 * Encapsulates budget data including category name, current amount spent,
 * total budget amount, and calculated progress percentage.
 * <p>
 * فئة نموذجية تمثل عنصر فئة الميزانية مع تتبع التقدم.
 * تغلف بيانات الميزانية بما في ذلك اسم الفئة والمبلغ المنفق حالياً،
 * المبلغ الإجمالي للميزانية، ونسبة التقدم المحسوبة.
 */
@SuppressWarnings("unused")
public class BudgetItem {
    private String category;
    private double currentAmount;
    private double totalAmount;
    private int progress;

    /** Default constructor required by Firebase. المنشئ الافتراضي المطلوب بواسطة Firebase. */
    public BudgetItem() {
    }

    /**
     * Constructs a new BudgetItem and calculates progress.
     * @param category The category name. اسم الفئة.
     * @param currentAmount Amount spent. المبلغ المنفق.
     * @param totalAmount Total budget. الميزانية الإجمالية.
     */
    public BudgetItem(String category, double currentAmount, double totalAmount) {
        this.category = category;
        this.currentAmount = currentAmount;
        this.totalAmount = totalAmount;
        if (totalAmount > 0) {
            this.progress = (int) ((currentAmount / totalAmount) * 100);
        } else {
            this.progress = 0;
        }
    }

    /**
     * @return The category name as a String. اسم الفئة كـ String.
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return The current amount as a double. المبلغ الحالي كـ double.
     */
    public double getCurrentAmount() {
        return currentAmount;
    }

    /**
     * @return The total amount as a double. المبلغ الإجمالي كـ double.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * @return The progress percentage as an integer. نسبة التقدم كـ integer.
     */
    public int getProgress() {
        return progress;
    }

    /**
     * Updates the current amount and recalculates progress.
     * @param amount The new current amount. المبلغ الحالي الجديد.
     */
    public void setCurrentAmount(double amount) {
        this.currentAmount = amount;
        if (totalAmount > 0) {
            this.progress = (int) ((currentAmount / totalAmount) * 100);
        } else {
            this.progress = 0;
        }
    }
}
