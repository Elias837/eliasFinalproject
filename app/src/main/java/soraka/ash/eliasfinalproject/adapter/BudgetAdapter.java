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

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {
    private List<FinancialGoal> budgetItems;
    private Context context;

    public BudgetAdapter(Context context, List<FinancialGoal> budgetItems) {
        this.context = context;
        this.budgetItems = budgetItems;
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goal, parent, false);
        return new BudgetViewHolder(view);
    }

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

    @Override
    public int getItemCount() {
        return budgetItems.size();
    }

    public void updateBudgetItems(List<FinancialGoal> newItems) {
        this.budgetItems = newItems;
        notifyDataSetChanged();
    }

    static class BudgetViewHolder extends RecyclerView.ViewHolder {
        private final TextView goalName;
        private final TextView goalDate;
        private final TextView amountProgress;
        private final TextView percentText;
        private final LinearProgressIndicator goalProgressBar;
        private final ImageButton btnEditGoal;

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            goalName = itemView.findViewById(R.id.goalName);
            goalDate = itemView.findViewById(R.id.goalDate);
            amountProgress = itemView.findViewById(R.id.amountProgress);
            percentText = itemView.findViewById(R.id.percentText);
            goalProgressBar = itemView.findViewById(R.id.goalProgressBar);
            btnEditGoal = itemView.findViewById(R.id.btnEditGoal);
        }

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
