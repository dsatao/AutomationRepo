package hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import utils.BasePage;

public class CustomHook {

	/*@After
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			final byte[] screenshot = ((TakesScreenshot) BasePage.driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "image");
			BasePage.log.info("Scenario ["+scenario.getName()+"] failed.");
			
		}
	} */

}
