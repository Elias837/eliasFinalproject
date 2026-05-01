package soraka.ash.eliasfinalproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

/**
 * BroadcastReceiver that handles monthly budget reminder alarms.
 * Triggered by AlarmManager to show a push notification on a scheduled basis (e.g., the 10th of each month).
 * <p>
 * مستقبل بث (BroadcastReceiver) يتعامل مع تنبيهات تذكير الميزانية الشهرية.
 * يتم تشغيله بواسطة AlarmManager لإظهار إشعار دفع في وقت محدد (مثل العاشر من كل شهر).
 */
public class BudgetReminderReceiver extends BroadcastReceiver {
    /** 
     * Unique ID for the notification channel used by this receiver. 
     * معرف فريد لقناة الإشعارات المستخدمة من قبل هذا المستقبل. 
     */
    private static final String CHANNEL_ID = "BUDGET_REMINDER_CHANNEL";

    /**
     * Called when the BroadcastReceiver is receiving an Intent broadcast.
     * <p>
     * يتم استدعاؤه عندما يتلقى مستقبل البث رسالة (Intent).
     *
     * @param context The Context in which the receiver is running. السياق الذي يعمل فيه المستقبل.
     * @param intent The Intent being received. الرسالة (Intent) المستلمة.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }

    /**
     * Creates and displays a push notification to remind the user about their budget.
     * Handles notification channel creation for Android Oreo (API 26) and above.
     * <p>
     * ينشئ ويعرض إشعاراً لتذكير المستخدم بميزانيته.
     * يتعامل مع إنشاء قناة الإشعارات لإصدارات أندرويد Oreo (API 26) فما فوق.
     *
     * @param context The application context used to access system services and build the notification.
     *                سياق التطبيق المستخدم للوصول إلى خدمات النظام وبناء الإشعار.
     */
    private void showNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create the notification channel if necessary (Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Budget Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Notifications for monthly budget updates");
            notificationManager.createNotificationChannel(channel);
        }

        // Set up the intent that will fire when the user taps the notification
        Intent mainIntent = new Intent(context, budgetSummary.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Build the notification content
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Budget Reminder")
                .setContentText("It's the 10th of the month! Don't forget to update your balance and check your spending limits.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true) // Remove notification when tapped
                .setContentIntent(pendingIntent);

        // Trigger the notification
        notificationManager.notify(1, builder.build());
    }
}
