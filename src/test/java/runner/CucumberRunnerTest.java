package runner;

/*
 * @author : Dipak.Satao
 */

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", tags = { "@OverviewVBM1" }, glue = { "steps" }, plugin = {
		"json:target/cucumber-reports/cucumber.json", "pretty", "html:target/cucumber-reports" })
public class CucumberRunnerTest {
}