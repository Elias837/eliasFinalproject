package soraka.ash.eliasfinalproject.data.MyIncomeTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import soraka.ash.eliasfinalproject.R;

/**
 * ArrayAdapter for displaying income/expense items in a list or spinner.
 * Extends ArrayAdapter to provide custom view binding for MyIncome objects.
 * Binds transaction data to various UI components including images, text, and buttons.
 */
public class MyIncomeAdapter extends ArrayAdapter<MyIncome> {
    public final int itemLayout;
    /**
     * Constructor that initializes the adapter with context and layout resource.
     * @param context The context for the adapter
     * @param resource The layout resource to use for each item
     */
    public MyIncomeAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.itemLayout=resource;
    }
    /**
     * Gets a View that displays the data at the specified position in the data set.
     * Creates or recycles views and binds MyIncome data to the UI components.
     * @param position The position of the item within the adapter's data set
     * @param convertView The old view to reuse, if possible
     * @param parent The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vitem= convertView;
        if(vitem==null)
            vitem= LayoutInflater.from(getContext()).inflate(itemLayout,parent,false);
        ImageView imageView=vitem.findViewById(R.id.imgVitm);
        TextView tvTitle=vitem.findViewById(R.id.tvItmTitle);
        TextView tvText=vitem.findViewById(R.id.tvItmText);
        TextView tvImportance=vitem.findViewById(R.id.tvItmImportance);
        ImageButton btnSendSMS=vitem.findViewById(R.id.imgBtnSendSmsitm);
        ImageButton btnEdit=vitem.findViewById(R.id.imgBtnEdititm);
        ImageButton btnCall=vitem.findViewById(R.id.imgBtnCallitm);
        ImageButton btnDel=vitem.findViewById(R.id.imgBtnDeleteitm);
        // Get the current MyIncome object at this position
        MyIncome current = getItem(position);
        // Bind the data to the UI components
        tvTitle.setText(current.getShortTitle());
        tvText.setText(String.valueOf(current.getCategoryId()));
        tvImportance.setText("Importance:" + current.getAmount());
        return vitem;
    }
}
