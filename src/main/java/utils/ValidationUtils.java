package utils;

import io.restassured.response.Response;
import jsonAssert.JsonAssert;
import org.json.JSONException;

import java.io.IOException;

public class ValidationUtils {
    public static void validateResponseBody(String fileName, Response response) throws IOException, JSONException {
        JsonAssert.validateJsonResponses(fileName, response);
    }

    public static void validateResponseBodyKeysOnly(String fileName, Response response) throws IOException, JSONException {
        JsonAssert.validateJsonResponses(fileName, response);
    }
}
