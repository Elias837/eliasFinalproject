package soraka.ash.eliasfinalproject.data.MyIncomeTable;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Service that handles background synchronization of income data to Firebase.
 * <p>
 * خدمة تتعامل مع مزامنة بيانات الدخل في الخلفية مع Firebase.
 */
public class MyIncomeService extends Service {

    /**
     * Called when the service is started. Retrieves the income data from the intent.
     * <p>
     * تُستدعى عند بدء الخدمة. تستخرج بيانات الدخل من الرسالة (intent).
     *
     * @param intent The intent containing the task data. الرسالة التي تحتوي على بيانات المهمة.
     * @param flags Additional data about this start request. بيانات إضافية حول طلب البدء.
     * @param startId A unique integer representing this specific request to start. معرف فريد لهذا الطلب.
     * @return The starting mode. وضع البدء.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Read the data received within the intent
        if (intent != null && intent.hasExtra("task_extra")) {
            MyIncome income = (MyIncome) intent.getSerializableExtra("task_extra");
            if (income != null) {
                saveMyTaskToFirebase(income);
            }
        }
        // START_NOT_STICKY means if the system kills the service, don't recreate it automatically
        return START_NOT_STICKY;
    }

    /**
     * Saves the provided income data to Firebase Realtime Database.
     * <p>
     * يحفظ بيانات الدخل المقدمة في قاعدة بيانات Firebase Realtime.
     *
     * @param income The income record to save. سجل الدخل المراد حفظه.
     */
    private void saveMyTaskToFirebase(MyIncome income) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("tasks");
        String key = myRef.push().getKey();
        
        if (key != null) {
            myRef.child(key).setValue(income).addOnCompleteListener(fbTask -> {
                if (fbTask.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Sync Successful", Toast.LENGTH_SHORT).show();
                }
                // Stop the service once the work is done
                stopSelf();
            });
        } else {
            stopSelf();
        }
    }

    /**
     * [Required implementation for Service class]
     * This method is mandatory for any Android Service. However, since we are using 
     * a "Started Service" for background sync, we return null to tell Android that 
     * this service does not support binding with an Activity (No continuous two-way communication).
     * <p>
     * [توثيق إلزامي لفئة الخدمة]
     * هذه الدالة إجبارية في أي خدمة أندرويد. بما أننا نستخدم "Started Service" 
     * للمزامنة في الخلفية، فإننا نرجع null لإخبار النظام أن هذه الخدمة لا تدعم 
     * "الارتباط" (Binding) بالشاشة، بل تعمل بشكل مستقل لتنفيذ مهمة واحدة فقط.
     *
     * @param intent The intent used to bind. الرسالة المستخدمة للارتباط.
     * @return null (because we are not using a Bound Service).
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // We are using a Started Service, not a Bound Service
    }
}
