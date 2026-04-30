package soraka.ash.eliasfinalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.ai.FirebaseAI;
import com.google.firebase.ai.GenerativeModel;
import com.google.firebase.ai.java.GenerativeModelFutures;
import com.google.firebase.ai.type.Content;
import com.google.firebase.ai.type.GenerateContentResponse;
import com.google.firebase.ai.type.GenerativeBackend;

import java.util.concurrent.Executor;

public class GeminiActivity extends AppCompatActivity {

    private GenerativeModelFutures model;
    private View pbLoading;
    private TextView tvAiResponse;
    private Button btnSuggestSteps;
    private String topic;
    private String aiResponse;

    private Executor executor;
    private FutureCallback<GenerateContentResponse> callback;
    private Toast toast;
    private ListenableFuture<GenerateContentResponse> response;
    private GenerateContentResponse generateContentResponse;
    private Content prompt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.gemini_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the Gemini Developer API backend service
        // Create a `GenerativeModel` instance with a model that supports your use case
        GenerativeModel geminiModel = FirebaseAI.getInstance(GenerativeBackend.googleAI())
                .generativeModel("gemini-2.5-flash-lite");


        // Use the GenerativeModelFutures Java compatibility layer which offers
        // support for ListenableFuture and Publisher APIs
        model = GenerativeModelFutures.from(geminiModel);
        
        // Find views
        pbLoading = findViewById(R.id.pbLoading);
        tvAiResponse = findViewById(R.id.tvAiResponse);
        btnSuggestSteps = findViewById(R.id.btnSuggestSteps);
        
        // Note: The logic to trigger askFirebaseAiGeminiForSteps from btnSuggestSteps is missing here.
        // I'll add a simple listener for demonstration if the views exist.
        if (btnSuggestSteps != null) {
            btnSuggestSteps.setOnClickListener(v -> {
                android.widget.EditText etTaskTopic = findViewById(R.id.etTaskTopic);
                if (etTaskTopic != null) {
                    String task = etTaskTopic.getText().toString().trim();
                    if (!task.isEmpty()) {
                        askFirebaseAiGeminiForSteps(task);
                    }
                }
            });
        }
    }

    /**
     * @param topic the topic to ask Gemini about
     */
    private void askFirebaseAiGeminiForSteps(String topic) {
            if (pbLoading != null) pbLoading.setVisibility(View.VISIBLE);
            if (tvAiResponse != null) tvAiResponse.setText("");
            if (btnSuggestSteps != null) btnSuggestSteps.setEnabled(false);


            String promptStr = "i am using a money management app. i just spent money on: '" + topic + "'. " +
                    "as a financial advisor, can you give me 3 quick professional tips to manage my budget better for this specific item and tell me if this is usually considered a 'need' or a 'want' or a 'saving' or a 'spending';";


            Content promptContent = new Content.Builder()
                    .addText(promptStr)
                    .build();


            ListenableFuture<GenerateContentResponse> responseFuture = model.generateContent(promptContent);
            Executor mainExecutor = this::runOnUiThread;
            Futures.addCallback(responseFuture, new FutureCallback<GenerateContentResponse>() {
                @Override
                public void onSuccess(GenerateContentResponse result) {
                    if (pbLoading != null) pbLoading.setVisibility(View.GONE);
                    if (btnSuggestSteps != null) btnSuggestSteps.setEnabled(true);
                    if (tvAiResponse != null) tvAiResponse.setText(result.getText());
                }


                @Override
                public void onFailure(Throwable t) {
                    if (pbLoading != null) pbLoading.setVisibility(View.GONE);
                    if (btnSuggestSteps != null) btnSuggestSteps.setEnabled(true);
                    Toast.makeText(GeminiActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }, mainExecutor);
    }
}
