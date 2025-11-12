package soraka.ash.eliasfinalproject.data.MyProfileTable;

import androidx.room.Dao;

import java.util.List;

@Dao//لتحديد ان الواجهة تحتوي استعلامات على قاعدة بيانات
public interface MyProfileQuery
{   //استخراج جميع المستعملين

    List<MyProfile> getAll();
    // استخراج مستعمل حسب رقم المميز لهid

    List<MyProfile> loadAllByIds(int[] userIds);
    //هل المستعمل موجود حسب الايميل وكلمة السر

    MyProfile checkEmailPassw(String myEmail, String myPassw);
    //فحص هل الايميل موجود من قبل

    MyProfile checkEmail(String myEmail);
    // اضافة مستعمل او مجموعة مستعملين

    void insertAll(MyProfile... users);
    // حذف
    void delete(MyProfile user);
    //حذف حسب الرقم المميز id

    void delete(int id);
    //اضافة مستعمل واحد

    void insert(MyProfile myUser);
    //تعديل مستعمل او قائمة مستعملين

    void update(MyProfile...values);
}
