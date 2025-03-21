package tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/featureFiles", // Path to your feature files
        glue = "stepDefinitions", // Package containing your step definitions
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-html-report.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml"
        },
        monochrome = true, // Readable console output
        //tags = "@Regression", // Optional: Run specific tags
        //dryRun = true, //Optional: checks for undefined step definitions without executing the steps
        //strict = true //Optional: fail test if there are undefined or pending steps
        publish = true //publish the report to cucumber.io
)
public class TestRunner {
    // This class remains empty, it's used to launch the cucumber tests
}