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

/**
 * Activity that integrates with Firebase AI (Gemini) to provide financial advice.
 * Users can enter a spending topic and receive AI-generated suggestions.
 * <p>
 * نشاط يتكامل مع ذكاء Firebase الاصطناعي (Gemini) لتقديم نصائح مالية.
 * يمكن للمستخدمين إدخال موضوع إنفاق والحصول على اقتراحات مولدة بواسطة الذكاء الاصطناعي.
 */
public class GeminiActivity extends AppCompatActivity {

    private GenerativeModelFutures model;
    private View pbLoading;
    private TextView tvAiResponse;
    private Button btnSuggestSteps;

    /**
     * Initializes the activity, sets up the Gemini model, and UI listeners.
     * <p>
     * يقوم بتهيئة النشاط، وإعداد نموذج Gemini، ومستمعي واجهة المستخدم.
     *
     * @param savedInstanceState Saved state. الحالة المحفوظة.
     */
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

        GenerativeModel geminiModel = FirebaseAI.getInstance(GenerativeBackend.googleAI())
                .generativeModel("gemini-2.5-flash-lite");

        model = GenerativeModelFutures.from(geminiModel);
        
        pbLoading = findViewById(R.id.pbLoading);
        tvAiResponse = findViewById(R.id.tvAiResponse);
        btnSuggestSteps = findViewById(R.id.btnSuggestSteps);
        
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
     * Sends a prompt to the Gemini model and displays the response.
     * <p>
     * يرسل طلباً إلى نموذج Gemini ويعرض الرد.
     *
     * @param topic The spending topic to ask about. موضوع الإنفاق للاستفسار عنه.
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
