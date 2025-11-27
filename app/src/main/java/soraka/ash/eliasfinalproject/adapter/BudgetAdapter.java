package soraka.ash.eliasfinalproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import soraka.ash.eliasfinalproject.R;
import soraka.ash.eliasfinalproject.model.BudgetItem;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {
    private List<BudgetItem> budgetItems;

    public BudgetAdapter(List<BudgetItem> budgetItems) {
        this.budgetItems = budgetItems;
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_budget, parent, false);
        return new BudgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        BudgetItem item = budgetItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return budgetItems.size();
    }

    public void updateBudgetItems(List<BudgetItem> newItems) {
        this.budgetItems = newItems;
        notifyDataSetChanged();
    }

    static class BudgetViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryText;
        private final TextView amountText;
        private final ProgressBar progressBar;

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.budgetCategory);
            amountText = itemView.findViewById(R.id.budgetAmount);
            progressBar = itemView.findViewById(R.id.budgetProgressBar);
        }

        public void bind(BudgetItem item) {
            categoryText.setText(item.getCategory());
            String amountTextStr = String.format("$%.2f / $%.2f", 
                item.getCurrentAmount(), 
                item.getTotalAmount());
            amountText.setText(amountTextStr);
            progressBar.setProgress(item.getProgress());
        }
    }
}
