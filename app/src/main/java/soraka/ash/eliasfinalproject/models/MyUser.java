package soraka.ash.eliasfinalproject.models;

/**
 * User model class for Firebase Firestore.
 * Represents a user document in the users collection.
 * 
 * This class is used to serialize/deserialize user data
 * when working with Firebase Firestore database operations.
 */
public class MyUser {
    private String userId; // معرف فريد للمستخدم(يمكن أن يكون فارغًا في البداية)
    private String name;
    private String email;

    // دالة إنشاء افتراضية (مطلوبة بواسطة Firebase)
    public MyUser() {}

    public MyUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
