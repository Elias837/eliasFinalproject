package soraka.ash.eliasfinalproject.data.MyIncomeTable;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class MyIncomeService implements Serializable {
    public MyIncomeService() {
    }
        public class TaskSyncService extends Service
        {

            @Override
            public int onStartCommand(Intent intent, int flags, int startId) {
                //read the data that received within the intent
                if (intent != null && intent.hasExtra("task_extra")) {
                    MyIncome income = (MyIncome) intent.getSerializableExtra("task_extra");
                    saveMyTaskToFirebase(income);
                }
                // START_NOT_STICKY means if the system kills the service, don't recreate it automatically
                return START_NOT_STICKY;
            }


            private void saveMyTaskToFirebase(MyIncome income) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("tasks");
                String key = myRef.push().getKey();
                MyIncome.setUsersId(key);


                myRef.child(key).setValue(income).addOnCompleteListener(fbTask -> {
                    if (fbTask.isSuccessful()) {
                        // In a service, use context from getApplicationContext() for Toasts
                        Toast.makeText(getApplicationContext(), "Sync Successful", Toast.LENGTH_SHORT).show();
                    }
                    // Stop the service once the work is done to save battery/RAM
                    stopSelf();
                });
            }


            @Nullable
            @Override
            public IBinder onBind(Intent intent) {
                return null; // We are using a Started Service, not a Bound Service
            }
        }




    }



