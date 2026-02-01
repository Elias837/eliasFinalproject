/**
 * Room DAO interface for user profile database operations.
 * Provides methods for CRUD operations on user profile data.
 * Includes authentication methods for email/password validation.
 * 
 * واجهة DAO للوصول إلى قاعدة بيانات ملفات تعريف المستخدمين.
 * توفر طرق لعمليات CRUD على بيانات ملفات تعريف المستخدمين.
 * تشمل طرق مصادقة للتحقق من البريد الإلكتروني وكلمة المرور.
 */
package soraka.ash.eliasfinalproject.data.MyProfileTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao//لتحديد ان الواجهة تحتوي استعلامات على قاعدة بيانات
public interface MyProfileQuery
{    /**
     * Retrieves all user profiles from the database.
     * @return List of all MyProfile objects
     * 
     * يسترجع جميع ملفات تعريف المستخدمين من قاعدة البيانات.
     * @return قائمة بجميع كائنات MyProfile
     */
    @Query("SELECT * FROM MyProfile")
    List<MyProfile> getAll();
    /**
     * Retrieves user profiles by their IDs.
     * @param userIds Array of user IDs to search for
     * @return List of MyProfile objects matching the given IDs
     * 
     * يسترجع ملفات تعريف المستخدمين حسب معرفاتهم.
     * @param userIds مصفوفة من معرفات المستخدمين للبحث عنها
     * @return قائمة من كائنات MyProfile المطابقة للمعرفات المحددة
     */
    @Query("SELECT * FROM MyProfile WHERE keyid IN (:userIds)")
    List<MyProfile> loadAllByIds(int[] userIds);
    /**
     * Authenticates a user by checking email and password combination.
     * @param myEmail The user's email address
     * @param myPassw The user's password
     * @return MyProfile object if authentication succeeds, null otherwise
     * 
     * يصادق المستخدم عن طريق التحقق من مزيج البريد الإلكتروني وكلمة المرور.
     * @param myEmail عنوان البريد الإلكتروني للمستخدم
     * @param myPassw كلمة مرور المستخدم
     * @return كائن MyProfile إذا نجحت المصادقة، null خلاف ذلك
     */
    @Query("SELECT * FROM MyProfile WHERE email = :myEmail AND passw = :myPassw")
    MyProfile checkEmailPassw(String myEmail, String myPassw);
    /**
     * Checks if an email address already exists in the database.
     * @param myEmail The email address to check
     * @return MyProfile object if email exists, null otherwise
     * 
     * يتحقق مما إذا كان عنوان البريد الإلكتروني موجوداً بالفعل في قاعدة البيانات.
     * @param myEmail عنوان البريد الإلكتروني للتحقق منه
     * @return كائن MyProfile إذا كان البريد الإلكتروني موجوداً، null خلاف ذلك
     */
    @Query("SELECT * FROM MyProfile WHERE email = :myEmail LIMIT 1")
    MyProfile checkEmail(String myEmail);
    /**
     * Inserts multiple user profiles into the database.
     * @param users Variable number of MyProfile objects to insert
     * 
     * يدرج ملفات تعريف مستخدمين متعددة في قاعدة البيانات.
     * @param users عدد متغير من كائنات MyProfile لإدراجها
     */
    @Insert
    void insertAll(MyProfile... users);
    /**
     * Deletes a user profile from the database.
     * @param user The MyProfile object to delete
     * 
     * يحذف ملف تعريف المستخدم من قاعدة البيانات.
     * @param user كائن MyProfile المراد حذفه
     */
    @Delete
    void delete(MyProfile user);
    /**
     * Deletes a user profile by their ID.
     * @param id The ID of the user to delete
     * 
     * يحذف ملف تعريف المستخدم حسب معرفه.
     * @param id معرف المستخدم المراد حذفه
     */
    @Query("Delete From MyProfile WHERE keyid=:id ")
    void delete(int id);
    /**
     * Inserts a single user profile into the database.
     * @param myUser The MyProfile object to insert
     * 
     * يدرج ملف تعريف مستخدم واحد في قاعدة البيانات.
     * @param myUser كائن MyProfile المراد إدراجه
     */
    @Insert
    void insert(MyProfile myUser);
    /**
     * Updates one or more user profiles in the database.
     * @param values Variable number of MyProfile objects to update
     * 
     * يحدث ملفاً أو أكثر من ملفات تعريف المستخدمين في قاعدة البيانات.
     * @param values عدد متغير من كائنات MyProfile لتحديثها
     */
    @Update
    void update(MyProfile...values);
}
