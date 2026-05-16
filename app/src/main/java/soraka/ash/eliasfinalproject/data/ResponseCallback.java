package soraka.ash.eliasfinalproject.data;

/**
 * هي عبارة عن interface (واجهة برمجية). وظيفتها أن تعمل كـ "جهاز لاسلكي" أو "نظام تنبيه" بين الكلاس الذي يجلب البيانات (مثل GeminiHelper) وبين الشاشة التي تعرض البيانات (مثل GeminiActivity).
 * <p>
 * واجهة للتعامل مع الاستجابات غير المتزامنة من الخدمات الخارجية (مثل ذكاء Gemini الاصطناعي).
 */
public interface ResponseCallback {
    /**
     * Called when a successful response is received.
     * <p>
     * تُستدعى عند استلام رد ناجح.
     *
     * @param response The response text. نص الرد.
     */
    void onResponse(String response);

    /**
     * Called when an error occurs during the request.
     * <p>
     * تُستدعى عند حدوث خطأ أثناء الطلب.
     *
     * @param error The error or exception encountered. الخطأ أو الاستثناء الذي حدث.
     */
    void onError(Throwable error);
}
