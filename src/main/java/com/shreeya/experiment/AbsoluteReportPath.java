package com.shreeya.experiment;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;

import com.shreeya.page.LoginPage;
import com.shreeya.util.ApacheCode;
import com.shreeya.util.ApacheCoder;

public class AbsoluteReportPath {

	public static void main(String[] args) throws IOException, InvalidFormatException {
		System.out.println("Execution Started.............");
		LoginPage loginPageObj=new LoginPage();
		Report reportObj=new Report("Absolute Path");
		WebDriver driver=loginPageObj.browserLaunch("Testing");
		reportObj.testCreation("Screenshot");
		reportObj.errroMsg("hello shreeya");
		String reportPath=reportObj.addScreenshotMethod(driver);
		driver.close();
		ApacheCode coder=new ApacheCode();
		coder.excelFile();
		//String [] orderDetail= {"../ReportABC/"+"Abc"+".html",reportPath};
		String [] orderDetail= {"../ReportABC/Abc.html",reportPath};
		coder.excelWriter1(orderDetail, 2);
		coder.closeExcelWriting();
		reportObj.logFlush();
		
	}

}
