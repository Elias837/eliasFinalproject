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
 * <p>
 * محول (ArrayAdapter) لعرض عناصر الدخل/المصروفات في قائمة أو قائمة منسدلة.
 * يوسع ArrayAdapter لتوفير ربط عرض مخصص لكائنات MyIncome.
 */
public class MyIncomeAdapter extends ArrayAdapter<MyIncome> {
    
    /** Layout resource ID for the individual items. معرف مورد التخطيط للعناصر الفردية. */
    public final int itemLayout;

    /**
     * Constructor that initializes the adapter with context and layout resource.
     * <p>
     * منشئ يهيئ المحول بالسياق ومورد التخطيط.
     *
     * @param context The context for the adapter. سياق المحول.
     * @param resource The layout resource to use for each item. مورد التخطيط لكل عنصر.
     */
    public MyIncomeAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.itemLayout = resource;
    }

    /**
     * Gets a View that displays the data at the specified position in the data set.
     * <p>
     * يحصل على عرض يعرض البيانات في الموقع المحدد في مجموعة البيانات.
     *
     * @param position The position of the item. موقع العنصر.
     * @param convertView The old view to reuse. العرض القديم لإعادة الاستخدام.
     * @param parent The parent view. العرض الأب.
     * @return A View corresponding to the data. عرض مطابق للبيانات.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vitem = convertView;
        if (vitem == null)
            vitem = LayoutInflater.from(getContext()).inflate(itemLayout, parent, false);
            
        ImageView imageView = vitem.findViewById(R.id.imgVitm);
        TextView tvTitle = vitem.findViewById(R.id.tvItmTitle);
        TextView tvText = vitem.findViewById(R.id.tvItmText);
        TextView tvImportance = vitem.findViewById(R.id.tvItmImportance);
        ImageButton btnSendSMS = vitem.findViewById(R.id.imgBtnSendSmsitm);
        ImageButton btnEdit = vitem.findViewById(R.id.imgBtnEdititm);
        ImageButton btnCall = vitem.findViewById(R.id.imgBtnCallitm);
        ImageButton btnDel = vitem.findViewById(R.id.imgBtnDeleteitm);
        
        MyIncome current = getItem(position);
        if (current != null) {
            tvTitle.setText(current.getShortTitle());
            tvText.setText(String.valueOf(current.getCategoryId()));
            tvImportance.setText("Importance:" + current.getAmount());
        }
        
        return vitem;
    }
}
