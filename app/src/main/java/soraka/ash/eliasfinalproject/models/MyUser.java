package soraka.ash.eliasfinalproject.models;

/**
 * User model class for Firebase Realtime Database.
 * Represents a user node in the users collection.
 * <p>
 * فئة نموذج المستخدم لقاعدة بيانات Firebase Realtime.
 * تمثل عقدة مستخدم في مجموعة المستخدمين.
 */
public class MyUser {
    /** Unique identifier for the user. معرف فريد للمستخدم. */
    private String userId;
    /** The display name of the user. اسم المستخدم. */
    private String name;
    /** The email address of the user. البريد الإلكتروني للمستخدم. */
    private String email;

    /**
     * Default constructor required by Firebase for deserialization.
     * <p>
     * منشئ افتراضي مطلوب بواسطة Firebase لإعادة بناء الكائن من البيانات.
     */
    public MyUser() {}

    /**
     * Constructs a MyUser instance with name and email.
     * <p>
     * ينشئ نسخة من MyUser مع الاسم والبريد الإلكتروني.
     *
     * @param name The user's name. اسم المستخدم.
     * @param email The user's email. البريد الإلكتروني للمستخدم.
     */
    public MyUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /** @return The user's ID. معرف المستخدم. */
    public String getUserId() { return userId; }
    /** @param userId Sets the user's ID. تعيين معرف المستخدم. */
    public void setUserId(String userId) { this.userId = userId; }

    /** @return The user's name. اسم المستخدم. */
    public String getName() { return name; }
    /** @param name Sets the user's name. تعيين اسم المستخدم. */
    public void setName(String name) { this.name = name; }

    /** @return The user's email. البريد الإلكتروني للمستخدم. */
    public String getEmail() { return email; }
    /** @param email Sets the user's email. تعيين البريد الإلكتروني للمستخدم. */
    public void setEmail(String email) { this.email = email; }
}
