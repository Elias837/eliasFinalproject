package soraka.ash.eliasfinalproject.data;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import soraka.ash.eliasfinalproject.models.FinancialGoal;
import soraka.ash.eliasfinalproject.models.Transaction;

/**
 * Firebase helper class for managing financial data operations.
 * Provides methods for CRUD operations on financial goals and transactions.
 */
public class FirebaseHelper {
    public static final String TAG = "FirebaseHelper";
    public static final String USERS_NODE = "users";
    public static final String GOALS_NODE = "goals";
    public static final String TRANSACTIONS_NODE = "transactions";
    
    public static DatabaseReference database;
    public static FirebaseAuth mAuth;

    public FirebaseHelper() {
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void get(String transactions, ValueEventListener valueEventListener) {
    }

//    /**
//     * Gets the current user ID or null if not authenticated
//     */
//    public String getCurrentUserId() {
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        return currentUser != null ? currentUser.getUid() : null;
//    }
//
//    /**
//     * Generic get method to retrieve data from a specific node for the current user.
//     */
//    public void get(String nodeName, ValueEventListener listener) {
//        String userId = getCurrentUserId();
//        if (userId == null) {
//            Log.e(TAG, "User not authenticated");
//            return;
//        }
//        database.child(USERS_NODE).child(userId).child(nodeName).addValueEventListener(listener);
//    }
//
//    /**
//     * Saves a financial goal to Firebase
//     */
//    public void saveFinancialGoal(FinancialGoal goal, OnSuccessListener<Void> onSuccessListener,
//                                 OnFailureListener onFailureListener) {
//        String userId = getCurrentUserId();
//        if (userId == null) {
//            onFailureListener.onFailure(new Exception("User not authenticated"));
//            return;
//        }
//
//        goal.setUserId(userId);
//        DatabaseReference goalRef = database.child(USERS_NODE).child(userId).child(GOALS_NODE).push();
//        goal.setGoalId(goalRef.getKey());
//
//        goalRef.setValue(goal)
//                .addOnSuccessListener(onSuccessListener)
//                .addOnFailureListener(onFailureListener);
//    }
//
//    /**
//     * Updates an existing financial goal
//     */
//    public void updateFinancialGoal(FinancialGoal goal, OnSuccessListener<Void> onSuccessListener,
//                                   OnFailureListener onFailureListener) {
//        String userId = getCurrentUserId();
//        if (userId == null) {
//            onFailureListener.onFailure(new Exception("User not authenticated"));
//            return;
//        }
//
//        goal.setUpdatedAt(System.currentTimeMillis());
//        database.child(USERS_NODE).child(userId).child(GOALS_NODE).child(goal.getGoalId())
//                .setValue(goal)
//                .addOnSuccessListener(onSuccessListener)
//                .addOnFailureListener(onFailureListener);
//    }
//
//    /**
//     * Deletes a financial goal
//     */
//    public void deleteFinancialGoal(String goalId, OnSuccessListener<Void> onSuccessListener,
//                                   OnFailureListener onFailureListener) {
//        String userId = getCurrentUserId();
//        if (userId == null) {
//            onFailureListener.onFailure(new Exception("User not authenticated"));
//            return;
//        }
//
//        database.child(USERS_NODE).child(userId).child(GOALS_NODE).child(goalId)
//                .removeValue()
//                .addOnSuccessListener(onSuccessListener)
//                .addOnFailureListener(onFailureListener);
//    }
//
//    /**
//     * Gets all financial goals for the current user
//     */
//    public void getAllFinancialGoals(ValueEventListener listener) {
//        get(GOALS_NODE, listener);
//    }
//
//    /**
//     * Saves a transaction to Firebase
//     */
//    public void saveTransaction(Transaction transaction, OnSuccessListener<Void> onSuccessListener,
//                               OnFailureListener onFailureListener) {
//        String userId = getCurrentUserId();
//        if (userId == null) {
//            onFailureListener.onFailure(new Exception("User not authenticated"));
//            return;
//        }
//
//        transaction.setUserId(userId);
//        DatabaseReference transactionRef = database.child(USERS_NODE).child(userId).child(TRANSACTIONS_NODE).push();
//        transaction.setTransactionId(transactionRef.getKey());
//
//        transactionRef.setValue(transaction)
//                .addOnSuccessListener(onSuccessListener)
//                .addOnFailureListener(onFailureListener);
//    }
//
//    /**
//     * Gets all transactions for the current user
//     */
//    public void getAllTransactions(ValueEventListener listener) {
//        get(TRANSACTIONS_NODE, listener);
//    }
//
//    /**
//     * Gets transactions filtered by type (income/expense)
//     */
//    public void getTransactionsByType(String type, ValueEventListener listener) {
//        String userId = getCurrentUserId();
//        if (userId == null) {
//            Log.e(TAG, "User not authenticated");
//            return;
//        }
//
//        database.child(USERS_NODE).child(userId).child(TRANSACTIONS_NODE)
//                .orderByChild("type")
//                .equalTo(type)
//                .addValueEventListener(listener);
//    }
//
//    /**
//     * Updates budget item progress for a goal
//     */
//    public void updateGoalProgress(String goalId, double amount, OnSuccessListener<Void> onSuccessListener,
//                                  OnFailureListener onFailureListener) {
//        String userId = getCurrentUserId();
//        if (userId == null) {
//            onFailureListener.onFailure(new Exception("User not authenticated"));
//            return;
//        }
//
//        database.child(USERS_NODE).child(userId).child(GOALS_NODE).child(goalId)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        FinancialGoal goal = dataSnapshot.getValue(FinancialGoal.class);
//                        if (goal != null) {
//                            goal.addProgress(amount);
//                            updateFinancialGoal(goal, onSuccessListener, onFailureListener);
//                        } else {
//                            onFailureListener.onFailure(new Exception("Goal not found"));
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        onFailureListener.onFailure(databaseError.toException());
//                    }
//                });
//    }
//
//    /**
//     * Gets financial summary data (total income, expenses, goals progress)
//     */
//    public void getFinancialSummary(ValueEventListener listener) {
//        String userId = getCurrentUserId();
//        if (userId == null) {
//            Log.e(TAG, "User not authenticated");
//            return;
//        }
//
//        DatabaseReference userRef = database.child(USERS_NODE).child(userId);
//        userRef.addValueEventListener(listener);
//    }
//
//    public void save(String goals, FinancialGoal goal) {
//    }
}
