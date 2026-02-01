package soraka.ash.eliasfinalproject.data.MyProfileTable;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import soraka.ash.eliasfinalproject.data.MyIncomeTable.MyIncome;
import soraka.ash.eliasfinalproject.data.MyIncomeTable.MyIncomeQuery;

/**
 * Room database class for application data persistence.
 * Provides singleton access to the app database and DAO interfaces.
 * Manages both user profiles and income/expense transactions.
 * 
 * فئة قاعدة بيانات Room لاستمرارية بيانات التطبيق.
 * توفر وصولاً منفرداً إلى قاعدة بيانات التطبيق وواجهات DAO.
 * تدير كل من ملفات تعريف المستخدمين ومعاملات الدخل/المصروفات.
 */
@Database(entities = {MyProfile.class, MyIncome.class}, version = 1, exportSchema = false)
public abstract class AppDataBaseE extends RoomDatabase {
    private static final String DATABASE_NAME = "elias_final_project_db";
    private static AppDataBaseE instance;

    // DAO declaration
    public abstract MyProfileQuery myProfileDao();
    public abstract MyIncomeQuery myIncomeDao();

    // Singleton pattern to prevent multiple instances of database
    /**
     * Returns the singleton instance of the database.
     * Uses double-checked locking pattern to ensure thread safety.
     * @param context The application context for database creation
     * @return The singleton AppDataBaseE instance
     * 
     * يرجع المثيل المنفرد لقاعدة البيانات.
     * يستخدم نمط التحقق المزدوج لضمان سلامة الخيط.
     * @param context سياق التطبيق لإنشاء قاعدة البيانات
     * @return مثيل AppDataBaseE المنفرد
     */
    public static synchronized AppDataBaseE getInstance(Context context) {
        // Create new database instance if none exists
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDataBaseE.class,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration() // Allow database recreation on schema changes
                    .build();
        }
        return instance;
    }
}
