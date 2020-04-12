package com.shreeya.seleniumgrid;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ChromeGridTest {

	static WebDriver driver;
	public static void main(String[] args) {
		//1.Set desiredCapabilites in cap
		
		DesiredCapabilities cap=new DesiredCapabilities();
		cap.setBrowserName("chrome");
		cap.setPlatform(org.openqa.selenium.Platform.WIN10);
		
		//2.Chrome Options definition
		//You can also write option for poxy if you have
		ChromeOptions options=new ChromeOptions();
		options.merge(cap);
		
		String hubUrl="http://192.168.0.106:4444/wd/hub";
		try {
			 driver=new RemoteWebDriver(new URL(hubUrl),options);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver.get("https://www.facebook.com/");
		System.out.println("Titile =====> "+driver.getTitle());
	}

}
