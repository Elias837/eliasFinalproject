package soraka.ash.eliasfinalproject;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import soraka.ash.eliasfinalproject.data.FirebaseHelper;
import soraka.ash.eliasfinalproject.models.Transaction;
import soraka.ash.eliasfinalproject.adapter.TransactionAdapter;

public class TransactionHistoryActivity extends AppCompatActivity {

    private RecyclerView rvTransactionHistory;
    private FirebaseHelper firebaseHelper;
    private TransactionAdapter adapter;
    private List<Transaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        Toolbar toolbar = findViewById(R.id.historyToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        rvTransactionHistory = findViewById(R.id.rvTransactionHistory);
        rvTransactionHistory.setLayoutManager(new LinearLayoutManager(this));

        // Setup adapter
        transactionList = new ArrayList<>();
        adapter = new TransactionAdapter(transactionList);
        rvTransactionHistory.setAdapter(adapter);

        firebaseHelper = new FirebaseHelper();
        loadTransactions();
    }

    private void loadTransactions() {
        // Using the new simple generic get method
        firebaseHelper.get("transactions", new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Transaction transaction = dataSnapshot.getValue(Transaction.class);
                    if (transaction != null) {
                        transactionList.add(transaction);
                    }
                }
                
                if (transactionList.isEmpty()) {
                    Toast.makeText(TransactionHistoryActivity.this, "No transactions found", Toast.LENGTH_SHORT).show();
                }

                // Update the adapter to show the new data
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TransactionHistoryActivity.this, "Failed to load transactions", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
