package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import enums.Endpoints;
import io.restassured.response.Response;
import pojoResponseModel.AuthenticateResponseModel;
import restWrappers.Params;
import restWrappers.Wrappers;

public class AuthenticateUtils {
    public static String getSessionId(String username, String password) throws JsonProcessingException{
        return Wrappers.restPost(Endpoints.AUTHENTICATE, Params.parseParams(username, password), AuthenticateResponseModel.class).session_id;
    }

    public static Response getUnknownEndpoint(String endPoint) throws JsonProcessingException{
        return Wrappers.restPostUnknownEndpoint(endPoint);
    }

    public static Response getWrongAuth(String username, String password) throws JsonProcessingException{
        return Wrappers.restPost(Endpoints.AUTHENTICATE, Params.parseParams(username, password));
    }
}
