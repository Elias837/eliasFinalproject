package soraka.ash.eliasfinalproject.data;

import android.util.Log;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
//import com.google.common.util.concurrent.FutureCallback;
//import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Helper class for Gemini AI integration.
 */
public class GeminiHelper {
    private static final String TAG = "GeminiHelper";
    // WARNING: Replace with your actual Gemini API Key from Google AI Studio
    private static final String GEMINI_API_KEY = "YOUR_GEMINI_API_KEY_HERE";
    private static GeminiHelper instance;
    private final GenerativeModelFutures model;
    private final Executor executor;

    private GeminiHelper() {
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", GEMINI_API_KEY);
        model = GenerativeModelFutures.from(gm);
        executor = Executors.newSingleThreadExecutor();
    }

    public static synchronized GeminiHelper getInstance() {
        if (instance == null) {
            instance = new GeminiHelper();
        }
        return instance;
    }

    public void sendMessage(String message, ResponseCallback callback) {
        if (GEMINI_API_KEY.equals("YOUR_GEMINI_API_KEY_HERE")) {
            callback.onError(new Exception("API Key not configured. Please add your Gemini API Key in GeminiHelper.java"));
            return;
        }

        Content content = new Content.Builder()
                .addText(message)
                .build();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
//
//        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
//            @Override
//            public void onSuccess(GenerateContentResponse result) {
//                String resultText = result.getText();
//                callback.onResponse(resultText);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.e(TAG, "Error sending message to Gemini", t);
//                callback.onError(t);
//            }
//        }, executor);
    }
}
