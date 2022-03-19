package utils;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class AssertSteps extends BasePage {
	CommonActions commonAction = new CommonActions();

	public void assertVariableContainsText(String variable, String expectedText) {
		String actualText = (String) vars.get(variable);
		log.info("Actual text [" + actualText + "] , expected text [" + expectedText + "]");
		Assert.assertTrue(actualText.contains(expectedText),
				"Text contains failed. Actual [" + actualText + "] Expected was [" + expectedText + "]");
	}

	public void assertVariableMatchesText(String variable, String expectedText) {
		String actualText = (String) vars.get(variable);
		log.info("Actual text [" + actualText + "] , expected text [" + expectedText + "]");
		Assert.assertTrue(actualText.equalsIgnoreCase(expectedText),
				"Text match failed. Actual [" + actualText + "] Expected was [" + expectedText + "]");
	}

	public void assertActualMatchesExpected(String actualText, String expectedText) {
		log.info("Actual text [" + actualText + "] , expected text [" + expectedText + "]");
		BasePage.addLogsInReports("Actual text [" + actualText + "] , expected text [" + expectedText + "]");
		Assert.assertTrue(actualText.equalsIgnoreCase(expectedText),
				"Text match failed. Actual [" + actualText + "] Expected was [" + expectedText + "]");
	}

	public void assertApplicationURLContains(String expectedText) {
		log.info("Actual Application URL [" + commonAction.getUrl() + "] , expected url to contains [" + expectedText
				+ "]");
		BasePage.addLogsInReports("Actual Application URL [" + commonAction.getUrl() + "] , expected url to contains ["
				+ expectedText + "]");
		Assert.assertTrue(commonAction.getUrl().contains(expectedText),
				"Text match failed. Actual [" + commonAction.getUrl() + "] Expected was [" + expectedText + "]");
	}
	
	public void assertThatElementIsDisplayed(WebElement element) {
		log.info("Verifying Element Is Dispalyed");
		BasePage.addLogsInReports("Verifying Element Is Dispalyed");
		Assert.assertTrue(element.isDisplayed(), "Verifying Element Is Dispalyed");
	}
}
