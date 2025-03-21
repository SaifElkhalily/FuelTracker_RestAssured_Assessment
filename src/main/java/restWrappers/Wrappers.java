package restWrappers;

import enums.Endpoints;
import io.restassured.response.Response;
import java.util.Map;
import java.util.Properties;
import static helpers.PropertiesLoader.readPropertyFile;
import static io.restassured.RestAssured.given;

public class Wrappers {
    private static final Properties url = readPropertyFile("config/application.properties");
    public static <T> T restPost(Endpoints endpoint, Map<String, String> formParams, Class<T> responseClass) {
        return given().relaxedHTTPSValidation().formParams(formParams).when().post(System.getProperty("Authenticate",url.getProperty("Authenticate")).concat(endpoint.getValue())).then().assertThat().statusCode(200).log().all().extract().as(responseClass);
    }
    public static Response restPost(Endpoints endpoint, Map<String, String> formParams) {
        return given().relaxedHTTPSValidation().formParams(formParams).when().post(System.getProperty("Authenticate",url.getProperty("Authenticate")).concat(endpoint.getValue())).then().assertThat().statusCode(200).log().all().extract().response();
    }

    public static Response restGet(Map<String, String> headers, Endpoints endpoint){
        return given().relaxedHTTPSValidation().headers(headers).when().get(System.getProperty("FetchCars",url.getProperty("FetchCars")).concat(endpoint.getValue())).then().assertThat().statusCode(200).log().all().extract().response();
    }

    public static Response restPostUnknownEndpoint(String endpoint){
        return given().relaxedHTTPSValidation().when().post(System.getProperty("Authenticate",url.getProperty("Authenticate")).concat(endpoint)).then().assertThat().statusCode(200).log().all().extract().response();
    }
}
