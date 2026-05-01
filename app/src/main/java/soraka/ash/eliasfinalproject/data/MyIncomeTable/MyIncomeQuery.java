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
 * يحدد عمليات قاعدة البيانات لإدارة سجلات الدخل.
 */
@Dao
public interface MyIncomeQuery {

    /**
     * Inserts a new income record into the database.
     * <p>
     * يدخل سجل دخل جديد في قاعدة البيانات.
     *
     * @param income The income object to insert. كائن الدخل المراد إدخاله.
     */
    @Insert
    void insert(MyIncome income);

    /**
     * Updates an existing income record.
     * <p>
     * يحدث سجل دخل موجود.
     *
     * @param income The income object with updated data. كائن الدخل مع البيانات المحدثة.
     */
    @Update
    void update(MyIncome income);

    /**
     * Deletes a specific income record.
     * <p>
     * يحذف سجل دخل معين.
     *
     * @param income The income object to delete. كائن الدخل المراد حذفه.
     */
    @Delete
    void delete(MyIncome income);

    /**
     * Retrieves all income records from the database.
     * <p>
     * يسترجع جميع سجلات الدخل من قاعدة البيانات.
     *
     * @return List of all MyIncome records. قائمة بجميع سجلات الدخل.
     */
    @Query("SELECT * FROM MyIncome")
    List<MyIncome> getAllIncomes();

    /**
     * Retrieves a single income record by its ID.
     * <p>
     * يسترجع سجل دخل واحد بواسطة معرّفه.
     *
     * @param incomeId The ID of the transaction. معرف العملية.
     * @return The MyIncome object if found. كائن MyIncome إذا وُجد.
     */
    @Query("SELECT * FROM MyIncome WHERE transactionId = :incomeId LIMIT 1")
    MyIncome getIncomeById(int incomeId);

    /**
     * Retrieves all income records associated with a specific user.
     * <p>
     * يسترجع جميع سجلات الدخل المرتبطة بمستخدم معين.
     *
     * @param userId The ID of the user. معرف المستخدم.
     * @return List of income records for the user. قائمة سجلات الدخل للمستخدم.
     */
    @Query("SELECT * FROM MyIncome WHERE userId = :userId")
    List<MyIncome> getIncomesByUserId(int userId);

    /**
     * Retrieves all income records within a specific category.
     * <p>
     * يسترجع جميع سجلات الدخل ضمن فئة معينة.
     *
     * @param categoryId The ID of the category. معرف الفئة.
     * @return List of income records in the category. قائمة سجلات الدخل في الفئة.
     */
    @Query("SELECT * FROM MyIncome WHERE categoryId = :categoryId")
    List<MyIncome> getIncomesByCategoryId(int categoryId);

    /**
     * Retrieves all completed income records for a specific user.
     * <p>
     * يسترجع جميع سجلات الدخل المكتملة لمستخدم معين.
     *
     * @param userId The ID of the user. معرف المستخدم.
     * @return List of completed income records. قائمة سجلات الدخل المكتملة.
     */
    @Query("SELECT * FROM MyIncome WHERE userId = :userId AND isCompleted = 1")
    List<MyIncome> getCompletedIncomesByUserId(int userId);

    /**
     * Retrieves all pending (non-completed) income records for a specific user.
     * <p>
     * يسترجع جميع سجلات الدخل المعلقة (غير المكتملة) لمستخدم معين.
     *
     * @param userId The ID of the user. معرف المستخدم.
     * @return List of non-completed income records. قائمة سجلات الدخل غير المكتملة.
     */
    @Query("SELECT * FROM MyIncome WHERE userId = :userId AND isCompleted = 0")
    List<MyIncome> getNonCompletedIncomesByUserId(int userId);
}
