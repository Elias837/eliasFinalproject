package soraka.ash.eliasfinalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

/**
 * Activity for displaying financial statistics and reporting.
 * Provides a visual breakdown of income, expenses, and monthly spending trends.
 * <p>
 * نشاط لعرض الإحصائيات المالية والتقارير.
 * يوفر تفصيلاً مرئياً للدخل والمصروفات واتجاهات الإنفاق الشهرية.
 */
public class statistics extends AppCompatActivity {

    /** TextView to display the user's total balance. نص لعرض إجمالي رصيد المستخدم. */
    private TextView tvTotalBalance;
    /** TextView to display the total income amount. نص لعرض إجمالي مبلغ الدخل. */
    private TextView tvStatIncome;
    /** TextView to display the total expense amount. نص لعرض إجمالي مبلغ المصروفات. */
    private TextView tvStatExpense;
    /** Label for the food category breakdown. تسمية لتفصيل فئة الطعام. */
    private TextView tvLabelFood;
    /** Label for the rent category breakdown. تسمية لتفصيل فئة الإيجار. */
    private TextView tvLabelRent;
    
    /** Card view containing the balance summary. بطاقة تحتوي على ملخص الرصيد. */
    private MaterialCardView balanceCard;
    /** Card view containing the graphical trend representations. بطاقة تحتوي على التمثيل البياني للاتجاهات. */
    private MaterialCardView graphCard;
    
    /** Visual bar representing January's financial data. شريط مرئي يمثل بيانات يناير المالية. */
    private View barJan;
    /** Visual bar representing February's financial data. شريط مرئي يمثل بيانات فبراير المالية. */
    private View barFeb;
    /** Visual bar representing March's financial data. شريط مرئي يمثل بيانات مارس المالية. */
    private View barMar;
    /** Visual bar representing April's financial data. شريط مرئي يمثل بيانات أبريل المالية. */
    private View barApr;
    
    /** Progress indicator for food spending. مؤشر تقدم للإنفاق على الطعام. */
    private LinearProgressIndicator progressFood;
    /** Progress indicator for rent spending. مؤشر تقدم للإنفاق على الإيجار. */
    private LinearProgressIndicator progressRent;

    /**
     * Called when the activity is starting. Enables Edge-to-Edge and initializes UI.
     * <p>
     * تُستدعى عند بدء النشاط. تفعل ميزة الحواف المتداخلة (Edge-to-Edge) وتهيئ واجهة المستخدم.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down.
     *                           إذا تم إعادة تهيئة النشاط بعد إغلاقه سابقاً.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);

        initializeViews();

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }

    /**
     * Initializes all UI components by mapping them to their respective layout IDs.
     * <p>
     * تقوم بتهيئة جميع مكونات واجهة المستخدم من خلال ربطها بمعرفات التخطيط الخاصة بها.
     */
    private void initializeViews() {
        balanceCard = findViewById(R.id.balanceCard);
        tvTotalBalance = findViewById(R.id.tvTotalBalance);
        
        tvStatIncome = findViewById(R.id.tvStatIncome);
        tvStatExpense = findViewById(R.id.tvStatExpense);
        
        graphCard = findViewById(R.id.graphCard);
        barJan = findViewById(R.id.barJan);
        barFeb = findViewById(R.id.barFeb);
        barMar = findViewById(R.id.barMar);
        barApr = findViewById(R.id.barApr);
        
        tvLabelFood = findViewById(R.id.tvLabelFood);
        tvLabelRent = findViewById(R.id.tvLabelRent);
        progressFood = findViewById(R.id.progressFood);
        progressRent = findViewById(R.id.progressRent);
    }
}
