package steps;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import utils.AssertSteps;
import utils.BasePage;
import utils.CommonActions;

public class stepDefinations {
	BasePage page;
	CommonActions ca = new CommonActions();
	AssertSteps assertion = new AssertSteps();

	Object resolveProperty(String var) {
		String resolvedValue = "";
		if (BasePage.prop.containsKey(var)) {
			resolvedValue = BasePage.prop.getProperty(var);
			BasePage.log.info("Global level Property value found for [" + var + "] and resolved value is ["
					+ resolvedValue + "]");
			CommonActions.addLogsInReports("Global level Property value found for [" + var + "] and resolved value is ["
					+ resolvedValue + "]");
		} else if (BasePage.prop1.containsKey(var)) {
			resolvedValue = BasePage.prop1.getProperty(var);
			BasePage.log.info("Environment level Property value found for [" + var + "] and resolved value is ["
					+ resolvedValue + "]");
			CommonActions.addLogsInReports("Environment level Property value found for [" + var
					+ "] and resolved value is [" + resolvedValue + "]");
		} else if (StringUtils.isBlank(var)) {
			BasePage.log.info("Resolved value is null please pass some input in the step.");
		} else {
			resolvedValue = var;
			BasePage.log.info("Resolved value is [" + resolvedValue + "]");
		}
		return resolvedValue;
	}

	@Before
	public void beforeScenario(Scenario scenario) {
		BasePage.initConfig();
		BasePage.scenario = scenario;
	}

	@Given("^User launch the application$")
	public void userLaunchApplication() {
		BasePage.addLogsInReports("User Login In Application");
	}

	@Given("^Testing environment \"([^\"]*)\" support normal$")
	public void testEnvironmentnormal(String key) {
		System.out.println("User Email As Per Env Is " + BasePage.getProperty(key));
		BasePage.addLogsInReports("User Login In Application");
	}

	@Given("^Testing environment (.*) support$")
	public void testEnvironment(String key) {
		System.out.println("User Email As Per Env Is " + BasePage.getProperty(key));
		BasePage.addLogsInReports("User Login In Application");
	}

	@Then("^Validate user navigated to url of \"([^\"]*)\"$")
	public void Check_Subheader_Contains(String pageName) {
		assertion.assertApplicationURLContains(pageName);
	}

	@Given("^Set browser \"([^\"]*)\"$")
	public void Set_browser(String value) {
		if (value.equalsIgnoreCase("CHROME") || value.equalsIgnoreCase("FIREFOX")) {
			BasePage.setMainProperty("browser", value);
			BasePage.log.info("Browser is set to [" + value + "]");
		} else {
			BasePage.log
					.error("Entered browser [" + value + "] is incorrect please type any of below: CHROME/FIREFOX.");
			Assert.fail("Entered browser [" + value + "] is incorrect please type any of below: CHROME/FIREFOX.");
		}
	}

	@Given("^Set environment \"([^\"]*)\"$")
	public void Set_environment(String value) {
		if (value.equalsIgnoreCase("qa") || value.equalsIgnoreCase("dev") || value.equalsIgnoreCase("uat")
				|| value.equalsIgnoreCase("automation")) {
			BasePage.setMainProperty("environment", value);
			BasePage.log.info("environment is set to [" + value + "]");
		} else {
			BasePage.log.error("Entered environment [" + value
					+ "] is incorrect please type any of below: qa/uat/dev/production.");
			Assert.fail("Entered environment [" + value
					+ "] is incorrect please type any of below: qa/uat/dev/production.");
		}
	}

	@Given("^Set application \"([^\"]*)\"$")
	public void Set_application(String value) {
		if (value.equalsIgnoreCase("VBM") || value.equalsIgnoreCase("VSP") || value.equalsIgnoreCase("VFA")) {
			BasePage.setMainProperty("application", value);
			BasePage.log.info("application is set to [" + value + "]");
		} else {
			BasePage.log
					.error("Entered application [" + value + "] is incorrect, please type any of below: VBM/VSP/VFA.");
			Assert.fail("Entered application [" + value + "] is incorrect, please type any of below: VBM/VSP/VFA.");
		}
	}

	@Given("^Set platform \"([^\"]*)\"$")
	public void Set_platform(String value) {
		if (value.equalsIgnoreCase("web") || value.equalsIgnoreCase("mobile") || value.equalsIgnoreCase("api")) {
			BasePage.setMainProperty("platform", value);
			BasePage.log.info("platform is set to [" + value + "]");
		} else {
			BasePage.log
					.error("Entered platform [" + value + "] is incorrect, please type any of below: web/mobile/api.");
			Assert.fail("Entered platform [" + value + "] is incorrect, please type any of below: web/mobile/api.");
		}
	}

	@Given("^Load configuration with new value$")
	public void loadNew_Values() throws IOException, InterruptedException {
		BasePage.loadConfig();
		Thread.sleep(6000);
	}

	@After
	public void afterScenario() {
		if (BasePage.scenario.isFailed()) {
			TakesScreenshot ts = (TakesScreenshot) BasePage.driver;
			final byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
			BasePage.scenario.embed(screenshot, "image/png");
		}
		BasePage.tearDownBrowser();
	}

}
