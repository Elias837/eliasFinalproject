package soraka.ash.eliasfinalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import soraka.ash.eliasfinalproject.data.GeminiHelper;
import soraka.ash.eliasfinalproject.data.ResponseCallback;

public class AiInsights extends AppCompatActivity {
    private EditText inputEditText;
    private Button sendButton;
    private Button financialAdviceButton;
    private Button budgetAnalysisButton;
    private TextView responseTextView;
    private GeminiHelper geminiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_insights);

        inputEditText = findViewById(R.id.inputEditText);
        sendButton = findViewById(R.id.sendButton);
        financialAdviceButton = findViewById(R.id.financialAdviceButton);
        budgetAnalysisButton = findViewById(R.id.budgetAnalysisButton);
        responseTextView = findViewById(R.id.responseTextView);

        geminiHelper = GeminiHelper.getInstance();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = inputEditText.getText().toString().trim();
                if (!userInput.isEmpty()) {
                    generateResponse(userInput);
                } else {
                    Toast.makeText(AiInsights.this, "Please enter text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        financialAdviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prompt = "Provide personalized financial advice for someone looking to improve their financial situation. Include budgeting tips, savings strategies, and investment basics.";
                generateResponse(prompt);
            }
        });

        budgetAnalysisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prompt = "Explain how to analyze personal budgets effectively. What are the key metrics to track, common spending categories to monitor, and red flags to watch for?";
                generateResponse(prompt);
            }
        });
    }

    private void generateResponse(String input) {
        sendButton.setEnabled(false);
        financialAdviceButton.setEnabled(false);
        budgetAnalysisButton.setEnabled(false);
        responseTextView.setText("Generating response...");

        geminiHelper.sendMessage(input, new ResponseCallback() {
            @Override
            public void onResponse(String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        responseTextView.setText(response);
                        sendButton.setEnabled(true);
                        financialAdviceButton.setEnabled(true);
                        budgetAnalysisButton.setEnabled(true);
                    }
                });
            }

            @Override
            public void onError(Throwable error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        responseTextView.setText("Error: " + error.getMessage());
                        sendButton.setEnabled(true);
                        financialAdviceButton.setEnabled(true);
                        budgetAnalysisButton.setEnabled(true);
                    }
                });
            }
        });
    }
}
