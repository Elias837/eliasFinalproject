package soraka.ash.eliasfinalproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import soraka.ash.eliasfinalproject.R;
import soraka.ash.eliasfinalproject.models.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactionList;

    public TransactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_goals_budgeting, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.title.setText(transaction.getDescription());
        holder.amount.setText(transaction.getFormattedAmount());
        
        // Change color based on type
        if (transaction.isIncome()) {
            holder.amount.setTextColor(0xFF10B981); // Green
        } else {
            holder.amount.setTextColor(0xFFEF4444); // Red
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public void updateList(List<Transaction> newList) {
        this.transactionList = newList;
        notifyDataSetChanged();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount;
        ImageView icon;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvItmTitle);
            amount = itemView.findViewById(R.id.tvItmImportance);
            icon = itemView.findViewById(R.id.imgVitm);
        }
    }
}
