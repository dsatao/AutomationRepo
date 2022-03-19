package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.core.api.Scenario;
import pages.common.CommonInterface;

/*
 * @author : Dipak.Satao
 */

public class BasePage implements CommonInterface {
	public static Properties prop, prop1, locProp;
	public static String application, platform,environment;
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static String baseUrl;
	public static Logger log;
	public Map<Object, Object> vars = new HashMap<Object, Object>();
	public static Scenario scenario;
	public static Map<String, String> requestBody = new ConcurrentHashMap<String, String>();
	public Map<Object, Object> APIVars = new HashMap<Object, Object>();

	public static void initConfig() {
		System.setProperty("log4j2.configurationFile", "./properties/log4j2.xml");
		log = LogManager.getLogger(BasePage.class);
		try {
			loadConfigFromFile(); // for loading the configuration files
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getDriverInstance(); // will provide an driver instance
	}

	public static void loadConfigFromFile() throws IOException {
		try {
			FileReader reader = new FileReader("./properties/app.properties");
			prop = new Properties();
			prop.load(reader);
			try {
				application = System.getProperty("application");
				platform = System.getProperty("platform");
				environment = System.getProperty("environment");
			} catch (Exception e) {
				System.out.println("Maven Params Not Passed");
			}

			if (application == null)
				application = prop.getProperty("application");

			if (platform == null)
				platform = prop.getProperty("platform");
			
			if (environment == null)
				environment = prop.getProperty("environment");
			
			FileReader reader1 = new FileReader(
					"./properties/" + application + "/" + platform + "/" + environment + "/" + "app.properties");

			prop1 = new Properties();
			prop1.load(reader1);

			baseUrl = prop1.getProperty("baseURL");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getDriverInstance() {
		switch (prop.getProperty("browser")) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "./support/chromedriver.exe");
			/*ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");*/
			driver = new ChromeDriver();
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "./support/geckodriver.exe");
			driver = new FirefoxDriver();
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			break;
		default:
			break;
		}
		System.out.print(baseUrl);
		driver.get(baseUrl);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds((long) 30));
	}

	public static void loadConfig() throws IOException {
		application = prop.getProperty("application");
		platform = prop.getProperty("platform");
		environment = prop.getProperty("environment");

		FileReader reader1 = new FileReader("./properties/" + application + "/" + platform + "/" + environment + "/" + "app.properties");

		prop1 = new Properties();
		prop1.load(reader1);
		driver.navigate().to(prop1.getProperty("baseURL"));
		System.out.println("application baseURL" + prop1.getProperty("baseURL"));
	}

	public static void tearDownBrowser() {
		driver.quit();
	}

	public static String getProperty(String key) {
		return prop1.getProperty(key);
	}

	public static void setProperty(String key, String value) {
		prop1.setProperty(key, value);
	}

	public static String getLocProperty(String key) {
		return locProp.getProperty(key);
	}

	public static void setLocProperty(String key, String value) {
		locProp.setProperty(key, value);
	}

	public String getMainProperty(String key) {
		return prop.getProperty(key);
	}

	public static void setMainProperty(String key, String value) {
		prop.setProperty(key, value);
	}

	public void setVar(Object key, Object value) {
		vars.put(key, value);
	}

	public void removeVar(Object key) {
		vars.remove(key);
	}

	public Object getVar(Object key) {
		return vars.get(key);
	}

	public static void addLogsInReports(String message) {
		BasePage.scenario.write(message);
	}

	@Override
	public void clickOnDropdownAndSelectValue(String dropdownName, String dropdownValue) {
		// TODO Auto-generated method stub
		
	}
}