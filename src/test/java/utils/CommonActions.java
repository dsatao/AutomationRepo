package utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

/*
 * @author : Jayesh.Hinge
 * @date : 01/01/2022
 */

public class CommonActions extends BasePage {

	public void openUrl(String url) {
		driver.get(url);
	}

	public void waitForElementToBeClickable(WebElement locator) {
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void closeBrowser() {
		driver.close();
	}

	public void quitSession() {
		driver.quit();
	}

	public String getUrl() {
		return driver.getCurrentUrl();
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public void submit(WebElement locator) {
		try {
			locator.submit();
			CommonActions.addLogsInReports("Done submitting form locator [" + locator + "]");
			log.info("Done submitting form locator [" + locator + "]");
		} catch (WebDriverException e) {
			CommonActions
					.addLogsInReports("Failed submitting locator [" + locator + "]. Error: [" + e.getMessage() + "]");
			log.info("Failed submitting locator [" + locator + "]. Error: [" + e.getMessage() + "]", e);
			throw e;
		}
	}

	//
	public void click(WebElement locator) {
		try {
			waitForElementToBeClickable(locator, WaitEnum.getResource("AVG_TIME"));
			locator.click();
			CommonActions.addLogsInReports("Done clicking on locator [" + locator + "]");
			log.info("Done clicking on locator [" + locator + "]");
		} catch (WebDriverException e) {
			CommonActions
					.addLogsInReports("Failed clicking on locator [" + locator + "]. Error: [" + e.getMessage() + "]");
			log.info("Failed clicking on locator [" + locator + "]. Error: [" + e.getMessage() + "]", e);
			throw e;
		}
	}

	public void click(WebElement locator, boolean tryWithJS) {
		try {
			locator.click();
			CommonActions.addLogsInReports("Done clicking on locator [" + locator + "]");
			log.info("Done clicking on locator [" + locator + "]");
		} catch (Exception e) {
			CommonActions.addLogsInReports(
					"Skipping WebDriver clicking on locator [" + locator + "], retrying with JS Click");
			log.info("Skipping WebDriver clicking on locator [" + locator + "], retrying with JS Click");
			jsClick(locator);
		}
	}

	public void jsClick(WebElement locator) {
		try {
			this.performScript("arguments[0].click()", locator);
			log.info("Done with js click Element [" + locator + "]");
		} catch (WebDriverException e) {
			log.error("Failed executing Javascript click on locator[" + locator + "], Error: [" + e.getMessage() + "]",
					e);
			throw e;
		}
	}

	private Object performScript(String script, WebElement ele) {
		try {
			Object retValue = ((JavascriptExecutor) driver).executeScript(script, new Object[] { ele });
			log.info("Done with executing javascript for locator [" + ele + "]. Result is [" + retValue + "]");
			return retValue;
		} catch (WebDriverException e) {
			log.error("Failed executing Javascript for locator[" + ele + "], Error: [" + e.getMessage() + "]", e);
			throw e;
		}
	}

	private Object performScript(String script) {
		try {
			Object retValue = ((JavascriptExecutor) driver).executeScript(script, new Object[] { 0 });
			log.info("Done with executing javascript for locator [" + script + "]. Result is [" + retValue + "]");
			return retValue;
		} catch (WebDriverException e) {
			log.error("Failed executing Javascript for locator[" + script + "], Error: [" + e.getMessage() + "]", e);
			throw e;
		}
	}

	public void clear(WebElement locator) {
		try {
			locator.clear();

			CommonActions.addLogsInReports("Done clearing locator [" + locator + "]");
			log.info("Done clearing locator [" + locator + "]");
		} catch (WebDriverException e) {
			CommonActions
					.addLogsInReports("Failed clearing locator [" + locator + "]. Error: [" + e.getMessage() + "]");
			log.info("Failed clearing locator [" + locator + "]. Error: [" + e.getMessage() + "]", e);
			throw e;
		}
	}
	
	public void clickClearAndType(WebElement locator, String value) {
		try {
			click(locator);
			locator.sendKeys(Keys.CONTROL, "a");
			locator.sendKeys(Keys.DELETE);
			locator.sendKeys(value);
			CommonActions.addLogsInReports(
					"Done clearing locator using [" + locator + "] and typing value [" + value + "]");
			log.info("Done clearing locator using [" + locator + "] and typing value [" + value + "]");
		} catch (WebDriverException e) {
			CommonActions.addLogsInReports("Failed to clear on locator[" + locator
					+ "] and typing value [" + value + "], Error: [" + e.getMessage() + "]");
			log.error("Failed to clear on locator[" + locator + "] and typing value [" + value
					+ "]d, Error: [" + e.getMessage() + "]", e);
			throw e;
		}
	}

	public void clearAndTypeValueJS(WebElement locator, String value) {
		try {
			this.performScript("arguments[0].value='" + value + "'", locator);
			CommonActions.addLogsInReports(
					"Done clearing locator using JS [" + locator + "] and typing value [" + value + "]");
			log.info("Done clearing locator using JS [" + locator + "] and typing value [" + value + "]");
		} catch (WebDriverException e) {
			CommonActions.addLogsInReports("Failed executing Javascript clear on locator[" + locator
					+ "] and typing value [" + value + "], Error: [" + e.getMessage() + "]");
			log.error("Failed executing Javascript clear on locator[" + locator + "] and typing value [" + value
					+ "]d, Error: [" + e.getMessage() + "]", e);
			throw e;
		}
	}

	public void type(WebElement locator, String text) {
		try {
			locator.sendKeys(text);
			CommonActions.addLogsInReports("Done typing [" + text + "] into locator [" + locator + "]");
			log.info("Done typing [" + text + "] into locator [" + locator + "]");
		} catch (WebDriverException e) {
			CommonActions.addLogsInReports(
					"Failed typing [" + text + "] into locator [" + locator + "].. Error: [" + e.getMessage() + "]");
			log.info("Failed typing [" + text + "] into locator [" + locator + "].. Error: [" + e.getMessage() + "]",
					e);
			throw e;
		}

	}

	public void selectByValue(WebElement locator, String value) {
		try {
			new Select(locator).selectByValue(value);
			log.info("Done selecting option by value [" + value + "], Element [" + locator + "]");
		} catch (WebDriverException e) {
			log.info("Failed selecting option by value [" + value + "], Element [" + locator + "].. Error: ["
					+ e.getMessage() + "]", e);
			throw e;
		}
	}

	public void selectByVisibleText(WebElement locator, String value) {
		try {
			new Select(locator).selectByVisibleText(value);
			log.info("Done selecting option by VisibleText [" + value + "], Element [" + locator + "]");
		} catch (WebDriverException e) {
			log.info("Failed selecting option by VisibleText [" + value + "], Element [" + locator + "].. Error: ["
					+ e.getMessage() + "]", e);
			throw e;
		}

	}

	public void selectByIndex(WebElement locator, int value) {
		try {
			new Select(locator).selectByIndex(value);
			log.info("Done selecting option by index [" + value + "], Element [" + locator + "]");
		} catch (WebDriverException e) {
			log.info("Failed selecting option by index [" + value + "], Element [" + locator + "].. Error: ["
					+ e.getMessage() + "]", e);
			throw e;
		}

	}

	public boolean isDisplayed(WebElement locator) {
		try {
			return locator.isDisplayed();
		} catch (WebDriverException e) {
			return false;
		}
	}

	public void hoverOnElement(WebElement locator) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(locator).build().perform();
			log.info("Done hovering locator [" + locator + "]");
		} catch (WebDriverException e) {
			log.info("Failed hover locator [" + locator + "]. Error: [" + e.getMessage() + "]", e);
			throw e;
		}
	}

	public void contextClick(WebElement locator) {
		try {
			Actions actions = new Actions(driver);
			actions.contextClick(locator).build().perform();
			log.info("Done hovering locator [" + locator + "]");
		} catch (WebDriverException e) {
			log.info("Failed hover locator [" + locator + "]. Error: [" + e.getMessage() + "]", e);
			throw e;
		}

	}

	public void doubleClick(WebElement locator) {
		try {
			Actions actions = new Actions(driver);
			actions.doubleClick(locator).build().perform();
			log.info("Done hovering locator [" + locator + "]");
		} catch (WebDriverException e) {
			log.info("Failed hover locator [" + locator + "]. Error: [" + e.getMessage() + "]", e);
			throw e;
		}

	}

	public void acceptAlert() {
		log.info("Accepting Alert message.");
		driver.switchTo().alert().accept();

	}

	public void dismissAlert() {
		log.info("Dismissing Alert message.");
		driver.switchTo().alert().dismiss();
	}

	public String getText(WebElement locator) {
		CommonActions.addLogsInReports("Reading text from locator [" + locator + "] text:" + locator.getText());
		log.info("Reading text from locator [" + locator + "] text:" + locator.getText());
		return locator.getText();
	}

	public int getNumberFromText(WebElement locator) {
		CommonActions.addLogsInReports("Reading number from text using locator [" + locator + "]");
		log.info("Reading number from text using locator [" + locator + "]");
		String s = locator.getText();
		return Integer.parseInt(StringUtils.getDigits(s));
	}

	public int getWindowsCount() {
		return driver.getWindowHandles().size();
	}

	public void switchWindows(String nameOrHandle) {
		log.info("Switching windows by name [" + nameOrHandle + "]");
		driver.switchTo().window(nameOrHandle);
	}

	public void switchWindowsByIndex(int index) {
		log.info("Switching windows by index [" + index + "]");
		Set<String> s = driver.getWindowHandles();
		String handle = (String) (new ArrayList<String>(s)).get(index);
		driver.switchTo().window(handle);
	}

	public void switchToFrame(String nameOrHandle) {
		log.info("Switching windows by name [" + nameOrHandle + "]");
		driver.switchTo().frame(nameOrHandle);
	}

	public void switchToDefaultContent() {
		log.info("Switching to default window");
		driver.switchTo().defaultContent();
	}

	public void switchToParentFrame() {
		log.info("Switching to parent frame");
		driver.switchTo().parentFrame();
	}

	public String getAttribute(WebElement locator, String name) {
		log.info("Reading attribute [" + name + "] from locator [" + locator + "]");
		return locator.getAttribute(name);
	}

	public String getCssAttribute(WebElement locator, String name) {
		log.info("Reading attribute [" + name + "] from locator [" + locator + "]");
		return locator.getCssValue(name);
	}

	public void scrollToElement(WebElement locator) {
		try {
			performScript("arguments[0].scrollIntoView()", locator);
			log.info("Done scrolling to locator [" + locator + "].");
		} catch (Exception e) {
			log.info("Failed scroll to locator [" + locator + "]. Error: [" + e.getMessage() + "]", e);
			throw e;
		}
	}

	public void scrollBy(int xOffset, int yOffset) {
		try {
			performScript("window.scrollBy(" + xOffset + "," + yOffset + ")");
			// log.info("Done scrolling to locator [" + locator + "].");
		} catch (Exception e) {
			// log.info("Failed scroll to locator [" + locator + "]. Error: [" +
			// e.getMessage() + "]", e);
			throw e;
		}
	}

	public void refresh() {
		log.info("Refreshing the page.");
		driver.navigate().refresh();

	}

	public void maximizeBrowser() {
		driver.manage().window().maximize();
	}

	public WebDriverWait waitInstance(int timeInSeconds) {
		return new WebDriverWait(driver, Duration.ofSeconds((long) timeInSeconds));
	}

	public WebElement waitForElementToBeClickable(WebElement locator, int timeoutInSec) {
		return waitInstance(timeoutInSec).until(ExpectedConditions.elementToBeClickable(locator));
	}

	public WebElement waitForElementToBeVisible(WebElement locator, int timeoutInSec) {
		return waitInstance(timeoutInSec).until(ExpectedConditions.visibilityOf(locator));
	}

	public WebElement waitForElementToBeClickable(WebElement locator, By byLocator, int timeoutInSec) {
		return waitInstance(timeoutInSec)
				.until(ExpectedConditions.elementToBeClickable(locator.findElement(byLocator)));
	}

	public WebElement waitForElementPresence(By locator, int timeoutInSec) {
		return waitInstance(timeoutInSec).until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public WebElement waitForElementClickable(By locator, int timeoutInSec) {
		return waitInstance(timeoutInSec).until(ExpectedConditions.elementToBeClickable(locator));
	}

//	public void waitForJStoLoad() {
//		waitInstance(10).until( new Predicate<WebDriver>() {
//			@Override
//            public boolean apply(WebDriver driver) {
//                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
//            }
//		}
//    );
//	}
//	

	public boolean waitForJStoLoad() {
		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) performScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return performScript("return document.readyState").toString().equals("complete");
			}
		};

		return waitInstance(10).until(jQueryLoad) && waitInstance(10).until(jsLoad);
	}

	public Boolean waitForElementValueToContainsText(WebElement locator, int timeoutInSec, String expectedText) {
		return waitInstance(timeoutInSec)
				.until(ExpectedConditions.textToBePresentInElementValue(locator, expectedText));
	}

	public void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ie) {
			log.error("Exception while calling thread.sleep()", ie);
		}
	}

}
