package com.shreeya.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.shreeya.MyTestLauncher;

public class ScreenshortProvider extends SeleniumCoder{
	static WebDriver driver;
	
	static HelperCode helperObject;
	public ScreenshortProvider(WebDriver driver) {
		super(driver);
		this.driver=driver;
		
	}

	public static String captureScreen(WebDriver driver,String moduleNameWithReferNo)  {
		staticWaitStatic(800);
		helperObject=new HelperCode();
		Reporter.log("Capture Screenshot : "+moduleNameWithReferNo,true);
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		String dest =MyTestLauncher.reportFolderPath[2]+"/"+moduleNameWithReferNo+"_"+helperObject.timeStampGenerator()+".png";
		File target = new File(dest);
		try {
			FileUtils.copyFile(src, target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dest;
	}
}
