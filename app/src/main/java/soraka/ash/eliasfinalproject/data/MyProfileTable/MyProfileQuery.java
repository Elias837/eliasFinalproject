package soraka.ash.eliasfinalproject.data.MyProfileTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Room DAO interface for user profile database operations.
 * Provides methods for CRUD operations on user profile data.
 * <p>
 * واجهة DAO للوصول إلى قاعدة بيانات ملفات تعريف المستخدمين.
 * توفر طرق لعمليات (إضافة، بحث، تعديل، حذف) على بيانات المستخدمين.
 */
@SuppressWarnings("unused") // هذا السطر يجعل الألوان تعود لطبيعتها ويمنع اللون الرمادي
@Dao
public interface MyProfileQuery {

    /**
     * Retrieves all user profiles from the database.
     * يسترجع جميع ملفات تعريف المستخدمين من قاعدة البيانات.
     */
    @Query("SELECT * FROM MyProfile")
    List<MyProfile> getAll();

    /**
     * Retrieves user profiles by their IDs.
     * يسترجع ملفات تعريف المستخدمين حسب معرفاتهم.
     */
    @Query("SELECT * FROM MyProfile WHERE keyid IN (:userIds)")
    List<MyProfile> loadAllByIds(int[] userIds);

    /**
     * Authenticates a user by checking email and password combination.
     * يصادق المستخدم عن طريق التحقق من البريد الإلكتروني وكلمة المرور (دالة تسجيل الدخول).
     */
    @Query("SELECT * FROM MyProfile WHERE email = :myEmail AND passw = :myPassw")
    MyProfile checkEmailPassw(String myEmail, String myPassw);

    /**
     * Checks if an email address already exists in the database.
     * يتحقق مما إذا كان البريد الإلكتروني موجوداً مسبقاً (لمنع التكرار).
     */
    @Query("SELECT * FROM MyProfile WHERE email = :myEmail LIMIT 1")
    MyProfile checkEmail(String myEmail);

    /**
     * Inserts multiple user profiles.
     * إدراج مجموعة من المستخدمين.
     */
    @Insert
    void insertAll(MyProfile... users);

    /**
     * Deletes a user profile.
     * حذف مستخدم من قاعدة البيانات.
     */
    @Delete
    void delete(MyProfile user);

    /**
     * Deletes a user profile by their ID.
     * حذف مستخدم حسب معرفه الخاص.
     */
    @Query("Delete From MyProfile WHERE keyid=:id ")
    void delete(int id);

    /**
     * Inserts a single user profile.
     * إضافة مستخدم واحد (دالة التسجيل).
     */
    @Insert
    void insert(MyProfile myUser);

    /**
     * Updates user profile information.
     * تحديث بيانات المستخدم.
     */
    @Update
    void update(MyProfile... values);
}
