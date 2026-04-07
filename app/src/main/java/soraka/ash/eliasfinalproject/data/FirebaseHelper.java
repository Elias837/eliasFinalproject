package soraka.ash.eliasfinalproject.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Optimized Firebase Helper using Generics to reduce code duplication.
 */
public class FirebaseHelper {
    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private final String uid = FirebaseAuth.getInstance().getUid();

    // 1. Generic Save Method (The "3-liner" your teacher wanted)
    public <T> void save(String node, T object) {
        if (uid != null) db.child("users").child(uid).child(node).push().setValue(object);
    }

    // 2. Generic Update/Delete Method
    public <T> void update(String node, String id, T object) {
        if (uid != null) db.child("users").child(uid).child(node).child(id).setValue(object);
    }

    // 3. Generic Get Method
    public void get(String node, ValueEventListener listener) {
        if (uid != null) db.child("users").child(uid).child(node).addValueEventListener(listener);
    }
}
