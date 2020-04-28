package com.shreeya.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.grid.web.Hub;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.annotations.Listeners;

import com.shreeya.FunctionKeyword;
@Listeners(CustomListener.class)
public class BrowserLunch {
	WebDriver driver = null;

	public WebDriver browserLaunch(String scenario) throws MalformedURLException {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");

		// DesiredCapabilities capabilities = new DesiredCapabilities();
		// capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\grid\\chromedriver.exe");
		Hub hub = FunctionKeyword.getHub();
		if (!scenario.equalsIgnoreCase("Partial Order")) {

			try {
				driver = new RemoteWebDriver(new URL(
						"http://" + hub.getConfiguration().host + ":" + hub.getConfiguration().port + "/wd/hub"),
						options);
			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			driver = new RemoteWebDriver(
					new URL("http://" + hub.getConfiguration().host + ":" + hub.getConfiguration().port + "/wd/hub"),
					options);
		}
		((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

		// driver.get("https://www.google.com/");
		driver.get("https://ewuat.edelbusiness.in/ewhtml/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		Reporter.log("Browser Launch", true);
		return driver;
	}

}
