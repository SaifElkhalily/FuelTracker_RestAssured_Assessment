package jsonAssert;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.*;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.skyscreamer.jsonassert.Customization.customization;

public class JsonAssert {
    public static void validateJsonResponses(String expectedJsonFileName, Response response,
                                             String... fieldsToCompareByRegex)
            throws IOException, JSONException {
        String responsesBasePath = "src/main/java/responses";
        String expectedJson = responsesBasePath.concat("/" + expectedJsonFileName + ".json");
        expectedJson = new String(Files.readAllBytes(Paths.get(expectedJson)));

        String actualJson = response.getBody().asString();

        Customization[] customizations = new Customization[fieldsToCompareByRegex.length];
        customization("createdon", // json path you want to customize
                new ValueMatcher() {
                    @Override
                    public boolean equal(Object o1, Object o2) {
                        return true; // in your case just ignore the values and return true
                    }
                });
        customization("modifiedon", // json path you want to customize
                new ValueMatcher() {
                    @Override
                    public boolean equal(Object o1, Object o2) {
                        return true; // in your case just ignore the values and return true
                    }
                });
        for (int i = 0; i < fieldsToCompareByRegex.length; i++) {
            customizations[i] = new Customization(fieldsToCompareByRegex[i], new RegularExpressionValueMatcher<>());
        }
        CustomComparator customComparator = new CustomComparator(JSONCompareMode.STRICT, customizations);
        JSONAssert.assertEquals(expectedJson, actualJson, customComparator);
    }
}
