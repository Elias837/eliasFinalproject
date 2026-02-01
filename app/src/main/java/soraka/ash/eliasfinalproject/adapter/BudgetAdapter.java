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

/**
 * RecyclerView adapter for displaying budget items in a list.
 * Binds BudgetItem data to views and handles view creation and recycling.
 * Provides methods to update the budget items list dynamically.
 */
public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {
    private List<BudgetItem> budgetItems;

    /**
     * Constructor that initializes the adapter with a list of budget items.
     * @param budgetItems List of BudgetItem objects to display
     */
    public BudgetAdapter(List<BudgetItem> budgetItems) {
        this.budgetItems = budgetItems;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * Inflates the item layout and creates a new BudgetViewHolder.
     * @param parent The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View
     * @return A new BudgetViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_budget, parent, false);
        return new BudgetViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * Updates the contents of the ViewHolder to reflect the item at the given position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     * @param position The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        BudgetItem item = budgetItems.get(position);
        holder.bind(item);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter
     */
    @Override
    public int getItemCount() {
        return budgetItems.size();
    }

    /**
     * Updates the budget items list and notifies the adapter to refresh the display.
     * @param newItems The new list of BudgetItem objects to display
     */
    public void updateBudgetItems(List<BudgetItem> newItems) {
        this.budgetItems = newItems;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class that holds references to the views for each budget item.
     * Extends RecyclerView.ViewHolder and provides binding functionality.
     */
    static class BudgetViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryText;
        private final TextView amountText;
        private final ProgressBar progressBar;

        /**
         * Constructor that initializes the ViewHolder and finds all view references.
         * @param itemView The view that will be held by this ViewHolder
         */
        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.budgetCategory);
            amountText = itemView.findViewById(R.id.budgetAmount);
            progressBar = itemView.findViewById(R.id.budgetProgressBar);
        }

        /**
         * Binds BudgetItem data to the ViewHolder's views.
         * Sets the category text, amount text, and progress bar values.
         * @param item The BudgetItem object containing the data to display
         */
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
