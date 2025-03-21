package restWrappers;

import java.util.HashMap;

public class Params {
    public static HashMap<String, String> parseParams(String username, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        return params;
    }
}
