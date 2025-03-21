import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PetroAppApiTest {

    private static final String BASE_URL = "http://hiring.petroapp.com/api.php";

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testAuthenticateUser_ValidCredentials_ReturnsSessionId() {
        Response response = given()
                .queryParams("endpoint", "authenticate")
                .queryParams("username", "user1")
                .queryParams("password", "password123")
                .when()
                .post()
                .then().log().all().extract().response();

        String sessionId = response.jsonPath().getString("session_id");
        System.out.println("Session ID: " + sessionId);
    }

    @Test
    public void testFetchCars_ValidSessionId_ReturnsCars() {
        // Authenticate to get session ID
        String sessionId = given()
                .queryParams("endpoint", "authenticate")
                .queryParams("username", "user1")
                .queryParams("password", "password123")
                .when()
                .post()
                .then()
                .extract()
                .jsonPath()
                .getString("session_id");

        given()
                .queryParams("endpoint", "fetch_cars")
                .headers("Session-Id", sessionId)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("status", equalTo("success"))
                .body("cars", notNullValue());
    }

    @Test
    public void testAuthenticateUser_InvalidCredentials_ReturnsError() {
        given()
                .param("endpoint", "authenticate")
                .param("username", "invalidUser")
                .param("password", "invalidPassword")
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("status", equalTo("error"))
                .body("message", equalTo("Invalid username or password"));
    }

    @Test
    public void testFetchCars_InvalidSessionId_ReturnsError() {
        given()
                .param("endpoint", "fetch_cars")
                .header("Session-Id", "invalidSessionId")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("status", equalTo("error"))
                .body("message", equalTo("Invalid session ID"));
    }

    @Test
    public void testFetchCars_MissingSessionId_ReturnsError() {
        given()
                .param("endpoint", "fetch_cars")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("status", equalTo("error"))
                .body("message", equalTo("Session ID is required"));
    }
}