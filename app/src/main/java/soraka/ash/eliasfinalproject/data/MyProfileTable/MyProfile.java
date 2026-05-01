package soraka.ash.eliasfinalproject.data.MyProfileTable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a user profile in the local Room database.
 * <p>
 * فئة تمثل ملف تعريف المستخدم في قاعدة بيانات Room المحلية.
 */
@Entity
public class MyProfile {

    /** 
     * Unique identifier for the profile. 
     * معرف فريد لملف التعريف. 
     */
    @PrimaryKey(autoGenerate = true)
    public long keyid;

    /** 
     * First name of the user. 
     * الاسم الأول للمستخدم. 
     */
    @ColumnInfo
    public String FirstName;

    /** 
     * Last name of the user. 
     * اسم عائلة المستخدم. 
     */
    public String LastName;

    /** 
     * Email address of the user. 
     * البريد الإلكتروني للمستخدم. 
     */
    public String email;

    /** 
     * Password for the user account. 
     * كلمة المرور لحساب المستخدم. 
     */
    public String passw;

    /**
     * Returns a string representation of the profile.
     * <p>
     * يرجع تمثيلاً نصياً لملف التعريف.
     */
    @Override
    public String toString() {
        return "MyProfile{" +
                "keyid=" + keyid +
                ", fullName='" + FirstName + LastName + '\'' +
                ", email='" + email + '\'' +
                ", passw='" + passw + '\'' +
                '}';
    }

    /** 
     * @return The profile ID. معرف ملف التعريف. 
     */
    public long getKeyid() {
        return keyid;
    }

    /** 
     * @param keyid Sets the profile ID. تعيين معرف ملف التعريف. 
     */
    public void setKeyid(long keyid) {
        this.keyid = keyid;
    }
}
