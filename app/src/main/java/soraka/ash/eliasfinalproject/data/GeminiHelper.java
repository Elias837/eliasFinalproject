package soraka.ash.eliasfinalproject.data;

import androidx.annotation.NonNull;
import android.util.Log;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class GeminiHelper {
    public static final String GEMINI_Version = "gemini-2.0-flash";
    private static String GEMINI_API_KEY = "AIzaSyDummyKeyForTesting123456789";
    private static GeminiHelper instance;
    private GenerativeModel gemini;

    private GeminiHelper() {
        if (GEMINI_API_KEY == null || GEMINI_API_KEY.equals("YOUR_GEMINI_API_KEY_HERE") || GEMINI_API_KEY.contains("DummyKey") || GEMINI_API_KEY.trim().isEmpty()) {
            Log.w("GeminiHelper", "API key not configured. Please set a valid Gemini API key in GeminiHelper.java");
            gemini = null;
            return;
        }
        
        try {
            gemini = new GenerativeModel(
                    GEMINI_Version,
                    GEMINI_API_KEY
            );
            Log.d("GeminiHelper", "Gemini model initialized successfully");
        } catch (Exception e) {
            Log.e("GeminiHelper", "Failed to initialize Gemini model: " + e.getMessage(), e);
            gemini = null;
        }
    }

    public static synchronized GeminiHelper getInstance() {
        if (instance == null) {
            instance = new GeminiHelper();
        }
        return instance;
    }

    public void sendMessage(String prompt, ResponseCallback callback) {
        if (prompt == null || prompt.trim().isEmpty()) {
            callback.onError(new IllegalArgumentException("Prompt cannot be empty"));
            return;
        }
        
        if (gemini == null) {
            callback.onError(new IllegalStateException("Gemini model not initialized. Please check API key configuration."));
            return;
        }
        
        Log.d("GeminiHelper", "Sending message to Gemini: " + prompt.substring(0, Math.min(50, prompt.length())) + "...");
        
        try {
            gemini.generateContent(prompt,
                    new Continuation<GenerateContentResponse>() {
                        @NonNull
                        @Override
                        public CoroutineContext getContext() {
                            return EmptyCoroutineContext.INSTANCE;
                        }

                        @Override
                        public void resumeWith(@NonNull Object result) {
                            if (result instanceof Result.Failure) {
                                Throwable throwable = ((Result.Failure) result).exception;
                                Log.e("GeminiHelper", "Gemini API call failed: " + throwable.getMessage(), throwable);
                                callback.onError(throwable);
                            } else {
                                String response = ((GenerateContentResponse) result).getText();
                                Log.d("GeminiHelper", "Gemini response received successfully");
                                callback.onResponse(response);
                            }
                        }
                    }
            );
        } catch (Exception e) {
            Log.e("GeminiHelper", "Exception during Gemini API call: " + e.getMessage(), e);
            callback.onError(e);
        }
    }
}
