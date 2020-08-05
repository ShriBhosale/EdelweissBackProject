package com.shreeya.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
@Listeners(CustomListener.class)
public class BrowserLaunch {
	WebDriver driver = null;

	public WebDriver browserLaunch(String scenario) throws MalformedURLException {
		killChromeDriverBackgroundInstances();
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\grid\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		//Hub hub = FunctionKeyword.getHub();
		if (!scenario.equalsIgnoreCase("Partial Order")) {

			try {
				/*
				 * driver = new RemoteWebDriver(new URL( "http://" + hub.getConfiguration().host
				 * + ":" + hub.getConfiguration().port + "/wd/hub"), options);
				 */
				driver= new ChromeDriver(capabilities);
			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			/*
			 * driver = new RemoteWebDriver( new URL("http://" + hub.getConfiguration().host
			 * + ":" + hub.getConfiguration().port + "/wd/hub"), options);
			 */
			driver=new ChromeDriver(capabilities);
		}
		//((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
		driver.manage().timeouts().pageLoadTimeout(200, TimeUnit.SECONDS);

		// driver.get("https://www.google.com/");
		driver.get("https://ewuat.edelbusiness.in/ewhtml/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		Reporter.log("Browser Launch", true);
		return driver;
	}
	
	public void killChromeDriverBackgroundInstances() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
