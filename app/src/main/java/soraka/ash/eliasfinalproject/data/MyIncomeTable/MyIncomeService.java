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
     * Required method for bound services. Not used here.
     * <p>
     * طريقة مطلوبة للخدمات المرتبطة. لا تستخدم هنا.
     *
     * @param intent The intent used to bind. الرسالة المستخدمة للارتباط.
     * @return null.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // We are using a Started Service, not a Bound Service
    }
}
