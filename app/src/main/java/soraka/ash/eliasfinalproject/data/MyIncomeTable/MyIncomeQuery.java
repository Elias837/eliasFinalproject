package soraka.ash.eliasfinalproject.data.MyIncomeTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Data Access Object (DAO) for the MyIncome table.
 * Defines the database operations for managing income records.
 * <p>
 * كائن الوصول إلى البيانات (DAO) لجدول MyIncome.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"}) // إخفاء التنبيهات لجعل اللون طبيعياً ونشطاً
@Dao
public interface MyIncomeQuery {

    /**
     * Inserts a new income record into the database.
     * يدخل سجل دخل جديد في قاعدة البيانات.
     */
    @Insert
    void insert(MyIncome income);

    /**
     * Updates an existing income record.
     * يحدث سجل دخل موجود.
     */
    @Update
    void update(MyIncome income);

    /**
     * Deletes a specific income record.
     * يحذف سجل دخل معين.
     */
    @Delete
    void delete(MyIncome income);

    /**
     * Retrieves all income records from the database.
     * يسترجع جميع سجلات الدخل من قاعدة البيانات.
     */
    @Query("SELECT * FROM MyIncome")
    List<MyIncome> getAllIncomes();

    /**
     * Retrieves a single income record by its ID.
     * يسترجع سجل دخل واحد بواسطة معرّفه.
     */
    @Query("SELECT * FROM MyIncome WHERE transactionId = :incomeId LIMIT 1")
    MyIncome getIncomeById(int incomeId);

    /**
     * Retrieves all income records associated with a specific user.
     * يسترجع جميع سجلات الدخل المرتبطة بمستخدم معين.
     */
    @Query("SELECT * FROM MyIncome WHERE userId = :userId")
    List<MyIncome> getIncomesByUserId(int userId);

    /**
     * Retrieves all income records within a specific category.
     * يسترجع جميع سجلات الدخل ضمن فئة معينة.
     */
    @Query("SELECT * FROM MyIncome WHERE categoryId = :categoryId")
    List<MyIncome> getIncomesByCategoryId(int categoryId);

    /**
     * Retrieves all completed income records for a specific user.
     * يسترجع جميع سجلات الدخل المكتملة لمستخدم معين.
     */
    @Query("SELECT * FROM MyIncome WHERE userId = :userId AND isCompleted = 1")
    List<MyIncome> getCompletedIncomesByUserId(int userId);

    /**
     * Retrieves all pending (non-completed) income records for a specific user.
     * يسترجع جميع سجلات الدخل المعلقة (غير المكتملة) لمستخدم معين.
     */
    @Query("SELECT * FROM MyIncome WHERE userId = :userId AND isCompleted = 0")
    List<MyIncome> getNonCompletedIncomesByUserId(int userId);
}
