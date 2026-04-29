package soraka.ash.eliasfinalproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;
import java.util.Locale;

import soraka.ash.eliasfinalproject.EditGoalActivity;
import soraka.ash.eliasfinalproject.R;
import soraka.ash.eliasfinalproject.models.FinancialGoal;

/**
 * RecyclerView adapter for displaying financial goals in a list.
 * Manages the data binding between the goal objects and the card views.
 *
 * محول (Adapter) للعرض التدويري لعرض الأهداف المالية في قائمة.
 * يدير ربط البيانات بين كائنات الأهداف وعروض البطاقات.
 */
public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {
    /** The list of financial goals to display. */
    /** قائمة الأهداف المالية للعرض. */
    private List<FinancialGoal> budgetItems;
    
    /** Context used for layout inflation and navigation. */
    /** السياق المستخدم لإنشاء التخطيط والتنقل. */
    private Context context;

    /**
     * Initializes the adapter with context and a data list.
     * @param context The context of the calling activity.
     * @param budgetItems The initial list of goals.
     *
     * يقوم بتهيئة المحول بالسياق وقائمة البيانات.
     * @param context سياق النشاط المستدعي.
     * @param budgetItems القائمة الأولية للأهداف.
     */
    public BudgetAdapter(Context context, List<FinancialGoal> budgetItems) {
        this.context = context;
        this.budgetItems = budgetItems;
    }

    /**
     * Creates a new ViewHolder by inflating the item_goal layout.
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new BudgetViewHolder.
     *
     * ينشئ ViewHolder جديداً عن طريق إنشاء تخطيط item_goal.
     */
    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goal, parent, false);
        return new BudgetViewHolder(view);
    }

    /**
     * Binds data to the ViewHolder and sets click listeners for editing.
     * @param holder The ViewHolder to update.
     * @param position The position of the item in the list.
     *
     * يربط البيانات بـ ViewHolder ويضبط مستمعي النقر للتعديل.
     */
    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        FinancialGoal item = budgetItems.get(position);
        holder.bind(item);

        if (holder.btnEditGoal != null) {
            holder.btnEditGoal.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditGoalActivity.class);
                intent.putExtra("GOAL_ID", item.getGoalId());
                context.startActivity(intent);
            });
        }
    }

    /**
     * Returns the total number of items in the list.
     * @return The size of budgetItems.
     *
     * يعيد العدد الإجمالي للعناصر في القائمة.
     */
    @Override
    public int getItemCount() {
        return budgetItems.size();
    }

    /**
     * Replaces the current data with a new list and refreshes the UI.
     * @param newItems The new list of financial goals.
     *
     * يستبدل البيانات الحالية بقائمة جديدة ويحدث واجهة المستخدم.
     */
    public void updateBudgetItems(List<FinancialGoal> newItems) {
        this.budgetItems = newItems;
        notifyDataSetChanged();
    }

    /**
     * Inner class representing the visual layout for a single goal item.
     *
     * فئة داخلية تمثل التخطيط المرئي لعنصر هدف واحد.
     */
    static class BudgetViewHolder extends RecyclerView.ViewHolder {
        private final TextView goalName;
        private final TextView goalDate;
        private final TextView amountProgress;
        private final TextView percentText;
        private final LinearProgressIndicator goalProgressBar;
        private final ImageButton btnEditGoal;

        /**
         * Finds UI elements within the item view.
         * @param itemView The root view of the item layout.
         *
         * يبحث عن عناصر واجهة المستخدم داخل عرض العنصر.
         */
        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            goalName = itemView.findViewById(R.id.goalName);
            goalDate = itemView.findViewById(R.id.goalDate);
            amountProgress = itemView.findViewById(R.id.amountProgress);
            percentText = itemView.findViewById(R.id.percentText);
            goalProgressBar = itemView.findViewById(R.id.goalProgressBar);
            btnEditGoal = itemView.findViewById(R.id.btnEditGoal);
        }

        /**
         * Fills the view components with data from a FinancialGoal object.
         * @param item The goal data to display.
         *
         * يملأ مكونات العرض بالبيانات من كائن FinancialGoal.
         */
        public void bind(FinancialGoal item) {
            goalName.setText(item.getGoalName());
            goalDate.setText("Target: " + (item.getTargetDate() != null ? item.getTargetDate() : "N/A"));
            
            String progressStr = String.format(Locale.getDefault(), "$%.2f / $%.2f", 
                item.getCurrentAmount(),
                item.getTargetAmount());
            amountProgress.setText(progressStr);
            
            double percentage = item.getProgressPercentage();
            percentText.setText(String.format(Locale.getDefault(), "%.0f%%", percentage));
            goalProgressBar.setProgress((int) percentage);
        }
    }
}
