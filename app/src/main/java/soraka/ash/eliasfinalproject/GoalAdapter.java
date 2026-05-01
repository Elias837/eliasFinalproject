package soraka.ash.eliasfinalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Custom Adapter for displaying financial goals in a RecyclerView.
 * This class maps Goal data to the list items shown on screen.
 * <p>
 * محول مخصص (Adapter) لعرض الأهداف المالية في قائمة RecyclerView.
 * يقوم هذا الكلاس بربط بيانات الأهداف بالعناصر المعروضة على الشاشة.
 */
public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

    /** List of goal strings to be displayed. قائمة بنصوص الأهداف المراد عرضها. */
    private List<String> goalList;

    /**
     * Constructor for GoalAdapter.
     * <p>
     * منشئ المحول.
     *
     * @param goalList The list of goals. قائمة الأهداف.
     */
    public GoalAdapter(List<String> goalList) {
        this.goalList = goalList;
    }

    /**
     * Inflates the layout for a single row in the list.
     * <p>
     * يقوم بتجهيز واجهة سطر واحد في القائمة.
     */
    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new GoalViewHolder(view);
    }

    /**
     * Binds the data (the goal text) to the TextView.
     * <p>
     * يربط البيانات (نص الهدف) بمربع النص.
     */
    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        holder.textView.setText(goalList.get(position));
    }

    /**
     * Returns the total number of items in the list.
     * <p>
     * يعيد العدد الإجمالي للعناصر في القائمة.
     */
    @Override
    public int getItemCount() {
        return goalList.size();
    }

    /**
     * ViewHolder class to hold references to the views for each list item.
     * <p>
     * فئة ViewHolder للاحتفاظ بمراجع الواجهات لكل عنصر في القائمة.
     */
    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
