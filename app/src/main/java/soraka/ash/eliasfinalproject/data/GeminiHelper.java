package soraka.ash.eliasfinalproject.data;

import android.util.Log;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Helper class for Gemini AI integration.
 * Manages the connection and requests to the Google AI Studio Gemini API.
 * <p>
 * كلاس مساعد للتكامل مع ذكاء Gemini الاصطناعي.
 * يدير الاتصال والطلبات الموجهة إلى واجهة برمجة تطبيقات Google AI Studio Gemini.
 */
public class GeminiHelper {
    private static final String TAG = "GeminiHelper";
    // WARNING: Replace with your actual Gemini API Key from Google AI Studio
    private static final String GEMINI_API_KEY = "YOUR_GEMINI_API_KEY_HERE";
    private static GeminiHelper instance;
    private final GenerativeModelFutures model;
    private final Executor executor;

    /**
     * Private constructor to initialize the Gemini model and executor.
     * <p>
     * منشئ خاص لتهيئة نموذج Gemini والمنفذ.
     */
    private GeminiHelper() {
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", GEMINI_API_KEY);
        model = GenerativeModelFutures.from(gm);
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Returns the singleton instance of GeminiHelper.
     * <p>
     * يعيد النسخة الوحيدة (Singleton) من GeminiHelper.
     *
     * @return The GeminiHelper instance. نسخة GeminiHelper.
     */
    public static synchronized GeminiHelper getInstance() {
        if (instance == null) {
            instance = new GeminiHelper();
        }
        return instance;
    }

    /**
     * Sends a text message to the Gemini AI and handles the response via a callback.
     * <p>
     * يرسل رسالة نصية إلى ذكاء Gemini الاصطناعي ويتعامل مع الرد عبر استدعاء راجع (callback).
     *
     * @param message The user prompt. موجه المستخدم.
     * @param callback The callback to handle success or error. الاستدعاء الراجع للتعامل مع النجاح أو الخطأ.
     */
    public void sendMessage(String message, ResponseCallback callback) {
        if (GEMINI_API_KEY.equals("YOUR_GEMINI_API_KEY_HERE")) {
            callback.onError(new Exception("API Key not configured. Please add your Gemini API Key in GeminiHelper.java"));
            return;
        }

        Content content = new Content.Builder()
                .addText(message)
                .build();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        // Note: Callback implementation is currently commented out in original file.
    }
}
