package soraka.ash.eliasfinalproject.data;

public interface ResponseCallback {
    /**
     * معالجة جواب الطلب من Gemini
     * @param response جواب الطلب
     */
    void onResponse(String response);

    /**
     * الرد بحالة وجود خطا
     * @param error
     */
    void onError(Throwable error);
}
