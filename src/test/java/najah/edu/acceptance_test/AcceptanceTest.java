package najah.edu.acceptance_test;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "MyFeatures",
        plugin = {"html:target/cucumber-report/report.html"},
        monochrome = true,
        glue = "najah.edu.acceptance_test"

)

public class AcceptanceTest {
}