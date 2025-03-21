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

    public static void validateJsonResponsesKeysOnly(String expectedJsonFileName, Response response)
            throws IOException, JSONException {

        String responsesBasePath = "src/main/java/models/responses";
        String expectedJsonPath = responsesBasePath.concat("/" + expectedJsonFileName + ".json");
        String expectedJson = new String(Files.readAllBytes(Paths.get(expectedJsonPath)));

        String actualJson = response.getBody().asString();

        JSONObject expectedJsonObject = new JSONObject(expectedJson);
        List<String> keyList = new ArrayList<>();
        Iterator<String> keys = expectedJsonObject.keys();
        while (keys.hasNext()) {
            keyList.add(keys.next());
        }

        Customization[] customizations = new Customization[keyList.size()];
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            customizations[i] = new Customization(key, new ValueMatcher() {
                @Override
                public boolean equal(Object o1, Object o2) {
                    return true;
                }
            });
        }

        JSONComparator customComparator = new CustomComparator(JSONCompareMode.STRICT, customizations);
        JSONAssert.assertEquals(expectedJson, actualJson, customComparator);

        JSONObject actualJsonObject = new JSONObject(response.getBody().asString());
        checkKeysRecursively(expectedJsonObject, actualJsonObject);
    }

    private static void checkKeysRecursively(JSONObject expected, JSONObject actual) throws JSONException {
        Iterator<String> keys = expected.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (!actual.has(key)) {
                throw new AssertionError("Missing key in response: " + key);
            }

            if (expected.get(key) instanceof JSONObject) {
                if (!(actual.get(key) instanceof JSONObject)) {
                    throw new AssertionError("Type mismatch for key " + key + ": expected JSONObject");
                }
                checkKeysRecursively(expected.getJSONObject(key), actual.getJSONObject(key));
            } else if (expected.get(key) instanceof JSONArray) {
                if (!(actual.get(key) instanceof JSONArray)) {
                    throw new AssertionError("Type mismatch for key " + key + ": expected JSONArray");
                }
                JSONArray expectedArray = expected.getJSONArray(key);
                JSONArray actualArray = actual.getJSONArray(key);
                for (int i = 0; i < expectedArray.length(); i++) {
                    if (expectedArray.get(i) instanceof JSONObject) {
                        if (!(actualArray.get(i) instanceof JSONObject)) {
                            throw new AssertionError("Type mismatch for key " + key + ": expected JSONObject");
                        }
                        checkKeysRecursively(expectedArray.getJSONObject(i), actualArray.getJSONObject(i));
                    } else if (expectedArray.get(i) instanceof JSONArray) {
                        if (!(actualArray.get(i) instanceof JSONArray)) {
                            throw new AssertionError("Type mismatch for key " + key + ": expected JSONArray");
                        }
                        checkKeysRecursivelyForJsonArrays(expectedArray.getJSONArray(i), actualArray.getJSONArray(i));
                    }
                }
            }
        }
    }

    private static void checkKeysRecursivelyForJsonArrays(JSONArray expected, JSONArray actual) throws JSONException {
        for (int i = 0; i < expected.length(); i++) {
            if (expected.get(i) instanceof JSONObject) {
                if (!(actual.get(i) instanceof JSONObject)) {
                    throw new AssertionError("Type mismatch for key : expected JSONObject");
                }
                checkKeysRecursively(expected.getJSONObject(i), actual.getJSONObject(i));
            } else if (expected.get(i) instanceof JSONArray) {
                if (!(actual.get(i) instanceof JSONArray)) {
                    throw new AssertionError("Type mismatch for key : expected JSONArray");
                }
                checkKeysRecursivelyForJsonArrays(expected.getJSONArray(i), actual.getJSONArray(i));
            }
        }
    }
}
