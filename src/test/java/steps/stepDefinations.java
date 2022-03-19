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
import utils.Utility;

public class stepDefinations {
	BasePage page;
	Utility utility = new Utility();
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

	@Given("^User enter email as \"([^\"]*)\" on \"([^\"]*)\"$")
	public void enterEmailOnPage(String email, String pageName) {
		page = utility.getPageObject(pageName);
		page.loginInApp(BasePage.getProperty(email));
		BasePage.addLogsInReports("User Login In Application");
	}
	
	@Given("^User enter \"([^\"]*)\" value as (.*) on \"([^\"]*)\"$")
	public void enterOnPage(String fieldName,String value, String pageName) {
		page = utility.getPageObject(pageName);
		page.enterText(fieldName,BasePage.getProperty(value));
	}

	@Given("^User Login with email as (.*) on \"([^\"]*)\"$")
	public void loginInApplication(String email, String pageName) {
		page = utility.getPageObject(pageName);
		page.loginInApp(BasePage.getProperty(email));
		// BasePage.addLogsInReports("User Login In with
		// Email"+BasePage.getProperty(email));
	}

	@Given("Update a user with Persona {string} on tab {string}")
	public void UpdateAUserWithPersona(String persona, String pageName) {
		page = utility.getPageObject(pageName);
		page.updateUser(persona);
	}

	@Given("Delete a user with Persona {string} on tab {string}")
	public void DeleteAUserWithPersona(String persona, String pageName) {
		page = utility.getPageObject(pageName);
		page.deleteUser(persona);
	}

	@Given("^User click on (.*) as \"([^\"]*)\" on \"([^\"]*)\"$")
	public void select(String elementText, String elementName, String pageName) {
		page = utility.getPageObject(pageName);
		page.clickOnElement(BasePage.getProperty(elementText), elementName);
		//BasePage.addLogsInReports("User Select Building"+BasePage.getProperty(elementText));
	}

	@Given("^Validate user is navigated to \"([^\"]*)\"$")
	public void loginInApplication(String pageName) {
		page = utility.getPageObject(pageName);
		page.validatePage();
	}

	@Then("^User should see \"([^\"]*)\" as (.*) on \"([^\"]*)\"$")
	public void validateSearchDeviceName(String validationPoint,String value,String pageName) {
		page = utility.getPageObject(pageName);
		page.validateSensePageDetails(validationPoint,BasePage.getProperty(value));
	}

	@Given("^User validate \"([^\"]*)\" link in footer on \"([^\"]*)\"$")
	public void validateFooterLinks(String linkText, String pageName) {
		page = utility.getPageObject(pageName);
		page.verifyIsDispalyed(linkText);
	}

	@Given("^User navigate to \"([^\"]*)\" tab on \"([^\"]*)\"$")
	public void navigateToTab(String tabName, String pageName) {
		page = utility.getPageObject(pageName);
		page.navigateToTab(tabName);
	}

	@Then("^Validate user navigated to url of \"([^\"]*)\"$")
	public void Check_Subheader_Contains(String pageName) {
		assertion.assertApplicationURLContains(pageName);
	}

	@Then("^User logout from \"([^\"]*)\"$")
	public void userLogout(String pageName) {
		page = utility.getPageObject(pageName);
		page.userLogoutOption();
	}

	@Then("^Check subheader contains \"([^\"]*)\" on \"([^\"]*)\"$")
	public void Check_Subheader_Contains(String header, String pageName) {
		page = utility.getPageObject(pageName);
		page.CheckSubHeader(header);
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

	@Then("Create a user with Persona \"([^\"]*)\" on tab \"([^\"]*)\"")
	public void create_a_user_with_Persona(String persona, String pageName) {
		page = utility.getPageObject(pageName);
		page.createUser(persona);
	}

	@Then("Validate user is created/updated on tab \"([^\"]*)\"")
	public void validate_user_is_created(String pageName) {
		page = utility.getPageObject(pageName);
		page.validateUserCreatedOrNot();
	}

	@Given("^Validate/Check user able to login with newly created user (.*) on \"([^\"]*)\"$")
	public void ValidateUserAbleToLoginWithNewlyCreatedUser(String emailText, String pageName) {
		page = utility.getPageObject(pageName);
		page.logoutFromApp();
		page.loginInApp(BasePage.getProperty(emailText));
	}

	@Given("Error message displays for \"([^\"]*)\" on \"([^\"]*)\"")
	public void ErrorMessageDisplaysFor(String errorMessage, String pageName) {
		page = utility.getPageObject(pageName);
		page.loginPageErrors(errorMessage);
	}
	
	@Given("User should see error message displayed as \"([^\"]*)\" on \"([^\"]*)\"$")
	public void ErrorMessageInvalidCrede(String errorMessage, String pageName) {
		page = utility.getPageObject(pageName);
		page.invalidLoginErrors(errorMessage);
	}
	

	@Then("Validate user is deleted on tab \"([^\"]*)\"")
	public void validate_user_is_deleted(String pageName) {
		page = utility.getPageObject(pageName);
		page.validateUserDeletedOrNot();
	}

	@Given("^User clicks on \"([^\"]*)\" as \"([^\"]*)\" on \"([^\"]*)\"$")
	public void clickOnLink(String elementText, String elementName, String pageName) {
		page = utility.getPageObject(pageName);
		page.clickOnElement(elementText, elementName);
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
