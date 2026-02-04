package soraka.ash.eliasfinalproject.model;

/**
 * Model class representing a budget category item with progress tracking.
 * Encapsulates budget data including category name, current amount spent,
 * total budget amount, and calculated progress percentage.
 * 
 * فئة نموذجية تمثل عنصر فئة الميزانية مع تتبع التقدم.
 * تغلف بيانات الميزانية بما في ذلك اسم الفئة والمبلغ المنفق حالياً،
 * المبلغ الإجمالي للميزانية، ونسبة التقدم المحسوبة.
 */
public class BudgetItem {
    private String category;
    private double currentAmount;
    private double totalAmount;
    private int progress;

    /**
     * Constructor that initializes a BudgetItem with category and amounts.
     * Automatically calculates the progress percentage based on current and total amounts.
     * @param category The name of the budget category
     * @param currentAmount The amount currently spent
     * @param totalAmount The total budget amount
     * 
     * المُنشئ الذي يهيئ BudgetItem بالفئة والمبالغ.
     * يحسب تلقائياً نسبة التقدم بناءً على المبالغ الحالية والإجمالية.
     * @param category اسم فئة الميزانية
     * @param currentAmount المبلغ المنفق حالياً
     * @param totalAmount المبلغ الإجمالي للميزانية
     */
    public BudgetItem(String category, double currentAmount, double totalAmount) {
        this.category = category;
        this.currentAmount = currentAmount;
        this.totalAmount = totalAmount;
        this.progress = (int) ((currentAmount / totalAmount) * 100);
    }

    // Getters and setters
    /**
     * Returns the budget category name.
     * @return The category name as a String
     * 
     * يرجع اسم فئة الميزانية.
     * @return اسم الفئة كـ String
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns the current amount spent.
     * @return The current amount as a double
     * 
     * يرجع المبلغ المنفق حالياً.
     * @return المبلغ الحالي كـ double
     */
    public double getCurrentAmount() {
        return currentAmount;
    }

    /**
     * Returns the total budget amount.
     * @return The total amount as a double
     * 
     * يرجع المبلغ الإجمالي للميزانية.
     * @return المبلغ الإجمالي كـ double
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Returns the progress percentage (0-100).
     * @return The progress percentage as an integer
     * 
     * يرجع نسبة التقدم (0-100).
     * @return نسبة التقدم كـ integer
     */
    public int getProgress() {
        return progress;
    }

    /**
     * Updates the current amount spent and recalculates the progress percentage.
     * @param amount The new current amount to set
     * 
     * يحدث المبلغ المنفق حالياً ويعيد حساب نسبة التقدم.
     * @param amount المبلغ الحالي الجديد للتعيين
     */
    public void setCurrentAmount(double amount) {
        this.currentAmount = amount;
        this.progress = (int) ((currentAmount / totalAmount) * 100);
    }
}
