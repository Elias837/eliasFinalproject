package soraka.ash.eliasfinalproject.data.MyProfileTable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class MyProfile {
    @PrimaryKey(autoGenerate = true)//تحديد الصفة كمفتاح رئيسي والذي يُنتجح بشكل تلقائي
    public long keyid;
    @ColumnInfo
    public String FirstName;
    public String LastName;

    public String email;//بحالة لم يتم اعطاء اسم للعامود يكون اسم الصفه هو اسم العامود

    public String passw;

    @Override
    public String toString() {
        return "MyProfile{" +
                "keyid=" + keyid +
                ", fullName='" + FirstName+LastName + '\'' +
                ", email='" + email + '\'' +
                ", passw='" + passw + '\'' +
                '}';
    }

    public long getKeyid() {
        return keyid;
    }

    public void setKeyid(long keyid) {
        this.keyid = keyid;
    }
}

