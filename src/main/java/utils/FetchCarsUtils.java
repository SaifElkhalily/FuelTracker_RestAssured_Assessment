package utils;

import enums.Endpoints;
import io.restassured.response.Response;
import restWrappers.Headers;
import restWrappers.Wrappers;

public class FetchCarsUtils {
    public static Response fetchCars(String sessionId) {
        return Wrappers.restGet(Headers.getHeaders(sessionId), Endpoints.FetchCars);
    }
}
