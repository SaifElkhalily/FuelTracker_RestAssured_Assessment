package stepDefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.Assert;
import utils.AuthenticateUtils;
import utils.FetchCarsUtils;
import utils.ValidationUtils;

import java.io.IOException;

public class FuelTrackerSD {
    String sessionID;
    Response response;

    @Given("User Authenticate with valid Username {string} and Password {string} get the sessionID")
    public void setSessionID(String username, String password) throws JsonProcessingException {
        sessionID = AuthenticateUtils.getSessionId(username, password);
    }

    @Given("User uses the sessionID to Fetch the Car Details")
    public void fetchCarDetails() {
        response = FetchCarsUtils.fetchCars(sessionID);
    }

    @Given("Validate on the response body {string}")
    public void validateResponseBodyForUser1(String jsonFile) throws IOException, JSONException {
        ValidationUtils.validateResponseBody(jsonFile, response);
    }

    @Given("User enters Invalid Endpoint {string} to Authenticate")
    public void authenticateWithInvalidEndpoint(String endpoint) throws JsonProcessingException {
        response = AuthenticateUtils.getUnknownEndpoint(endpoint);
    }

    @Given("User enters Invalid Username {string} and Password {string} to Authenticate")
    public void invalidAuth(String username, String password) throws JsonProcessingException {
        response = AuthenticateUtils.getWrongAuth(username, password);
    }


    @Given("User enters Invalid sessionID {string}")
    public void getCarDetailsWithInvalidSessionID(String invalidSessionID) {
        response = FetchCarsUtils.fetchCars(invalidSessionID);
    }

    @Given("Verify the response status code {int}")
    public void verifyStatusCode(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }
}
