package soraka.ash.eliasfinalproject.data.MyProfileTable;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import soraka.ash.eliasfinalproject.data.MyIncomeTable.MyIncome;
import soraka.ash.eliasfinalproject.data.MyIncomeTable.MyIncomeQuery;

@Database(entities = {MyProfile.class, MyIncome.class}, version = 1, exportSchema = false)
public abstract class AppDataBaseE extends RoomDatabase {
    private static final String DATABASE_NAME = "elias_final_project_db";
    private static AppDataBaseE instance;

    // DAO declaration
    public abstract MyProfileQuery myProfileDao();
    public abstract MyIncomeQuery myIncomeDao();

    // Singleton pattern to prevent multiple instances of database
    public static synchronized AppDataBaseE getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDataBaseE.class,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
