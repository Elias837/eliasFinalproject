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
 * هذا الكلاس يمثل "بوابة الذكاء" في المشروع. هو المسؤول عن التحدث مع موديول Gemini من جوجل.
 * يدير الاتصال والطلبات الموجهة إلى واجهة برمجة تطبيقات Google AI Studio Gemini.
 */
public class GeminiHelper {
    private static final String TAG = "GeminiHelper";
    // WARNING: Replace with your actual Gemini API Key from Google AI Studio
    private static final String GEMINI_API_KEY = "YOUR_GEMINI_API_KEY_HERE";
    private static GeminiHelper instance;
    private final GenerativeModelFutures model;
    //لماذا نستخدم Executor؟ لأن عملية التحدث مع الذكاء الاصطناعي قد تستغرق ثانية أو ثانيتين. نستخدم الـ Executor لكي تتم هذه العملية في "الخلفية" لكي لا تتجمد شاشة التطبيق ويظن المستخدم أنه تعطل.
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
     *
     * ستخدمتُ كلاس GeminiHelper كطبقة خدمات (Service Layer) لتبسيط التعامل مع محرك Gemini AI. هذا التصميم يسمح بفصل منطق الاتصال (Business Logic) عن واجهة المستخدم (UI)، مما يسهل صيانة الكود ويسمح باستخدام ميزات الذكاء الاصطناعي في شاشات أخرى مستقبلاً دون تكرار الكود.
     * <p>
     * يرسل رسالة نصية إلى ذكاء Gemini الاصطناعي ويتعامل مع الرد عبر استدعاء راجع (callback).
     *
     *الـ GeminiActivity: وظيفتها العرض (UI) فقط. هي المسؤولة عن شكل الأزرار، حركة الـ ProgressBar، وعرض النص على الشاشة. هي "الوجه" الذي يراه المستخدم.•الـ GeminiHelper: وظيفته المنطق (Logic) والاتصال. هو الذي يعرف "سر" التحدث مع جوجل، ويملك مفتاح الـ API، ويدير الاتصال بالإنترنت. هو "المحرك" المخفي.
     *
     * @param message The user prompt. موجه المستخدم.
     * @param callback The callback to handle success or error. الاستدعاء الراجع للتعامل مع النجاح أو الخطأ.
     *                 "استخدمت نمط الـ Singleton لضمان وجود Single Source of Truth (مصدر واحد للحقيقة) وللمحافظة على موارد الجهاز. عمليات فتح قاعدة البيانات أو تهيئة محرك الذكاء الاصطناعي هي عمليات 'مكلفة برمجياً' (Resource Intensive)، لذا قمت بتصميم الكلاس بحيث يتم إنشاؤه مرة واحدة فقط عند الحاجة الأولى له، وإعادة استخدام نفس النسخة في باقي أجزاء التطبيق لضمان الأداء السلس ومنع تضارب البيانات."
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
