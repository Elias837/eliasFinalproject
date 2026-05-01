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

/**
 * Adapter for displaying financial transactions in a RecyclerView.
 * Colors amounts based on whether they are income or expense.
 * <p>
 * محول (Adapter) لعرض المعاملات المالية في قائمة RecyclerView.
 * يقوم بتلوين المبالغ بناءً على ما إذا كانت دخلاً أم مصروفاً.
 */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    /** List of transactions to display. قائمة المعاملات المراد عرضها. */
    private List<Transaction> transactionList;

    /**
     * Constructor for TransactionAdapter.
     * <p>
     * منشئ محول المعاملات.
     *
     * @param transactionList The list of transactions. قائمة المعاملات.
     */
    public TransactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    /**
     * Inflates the layout for an individual transaction item.
     * <p>
     * يقوم بتجهيز واجهة عنصر معاملة فردي.
     */
    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_goals_budgeting, parent, false);
        return new TransactionViewHolder(view);
    }

    /**
     * Binds transaction data to the views and applies conditional coloring.
     * <p>
     * يربط بيانات المعاملة بالواجهات ويطبق التلوين الشرطي.
     */
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

    /**
     * Returns the total count of transactions.
     * <p>
     * يعيد العدد الإجمالي للمعاملات.
     */
    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    /**
     * Updates the data set and refreshes the RecyclerView.
     * <p>
     * يحدث مجموعة البيانات ويجدد القائمة.
     *
     * @param newList The new list of transactions. القائمة الجديدة للمعاملات.
     */
    public void updateList(List<Transaction> newList) {
        this.transactionList = newList;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder for transaction items.
     * <p>
     * حامل الواجهات (ViewHolder) لعناصر المعاملات.
     */
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
