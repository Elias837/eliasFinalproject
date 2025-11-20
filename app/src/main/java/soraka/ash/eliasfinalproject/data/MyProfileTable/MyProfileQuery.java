package soraka.ash.eliasfinalproject.data.MyProfileTable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao//لتحديد ان الواجهة تحتوي استعلامات على قاعدة بيانات
public interface MyProfileQuery
{   //استخراج جميع المستعملين
    @Query("SELECT * FROM MyProfile")
    List<MyProfile> getAll();
    // استخراج مستعمل حسب رقم المميز لهid
    @Query("SELECT * FROM MyProfile WHERE keyid IN (:userIds)")
    List<MyProfile> loadAllByIds(int[] userIds);
    //هل المستعمل موجود حسب الايميل وكلمة السر
    @Query("SELECT * FROM MyProfile WHERE email = :myEmail AND passw = :myPassw")
    MyProfile checkEmailPassw(String myEmail, String myPassw);
    //فحص هل الايميل موجود من قبل
    @Query("SELECT * FROM MyProfile WHERE email = :myEmail LIMIT 1")
    MyProfile checkEmail(String myEmail);
    // اضافة مستعمل او مجموعة مستعملين
    @Insert
    void insertAll(MyProfile... users);
    // حذف
    @Delete
    void delete(MyProfile user);
    //حذف حسب الرقم المميز id
    @Query("Delete From MyProfile WHERE keyid=:id ")
    void delete(int id);
    //اضافة مستعمل واحد
    @Insert
    void insert(MyProfile myUser);
    //تعديل مستعمل او قائمة مستعملين
    @Update
    void update(MyProfile...values);
}
