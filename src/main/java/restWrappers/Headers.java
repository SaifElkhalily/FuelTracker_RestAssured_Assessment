package restWrappers;

import java.util.HashMap;

public class Headers {
    public static HashMap<String, String> getHeaders(String sessionId) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Session-Id", sessionId);
        return headers;
    }
}
