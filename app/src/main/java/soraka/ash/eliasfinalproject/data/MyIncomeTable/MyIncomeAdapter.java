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

public class MyIncomeAdapter extends ArrayAdapter<MyIncome> {
    public final int itemLayout;
    public MyIncomeAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.itemLayout=resource;
    }
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
        //קבלת הנתון (עצם) הנוכחי
        MyIncome current=getItem(position);
        //הצגת הנתונים על שדות הרכיב הגרפי
        tvTitle.setText(current.getShortTitle());
        tvText.setText(String.valueOf(current.getCategoryId()));
        tvImportance.setText("Importance:"+current.getAmount());
        return vitem;
    }
}
