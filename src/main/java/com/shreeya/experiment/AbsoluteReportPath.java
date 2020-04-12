package com.shreeya.experiment;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;

import com.shreeya.page.LoginPage;
import com.shreeya.util.ApacheCode;
import com.shreeya.util.ApacheCoder;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.FolderStructure;

public class AbsoluteReportPath {

	public static void main(String[] args) throws IOException, InvalidFormatException {
		System.out.println("Execution Started.............");
		FolderStructure folder=new FolderStructure();
		String [] folderPathArray=folder.reportFolderCreator(1);
		LoginPage loginPageObj=new LoginPage();
		ExtendReporter reportObj=new ExtendReporter(folderPathArray[0],"abc",0);

				
		WebDriver driver=loginPageObj.browserLaunch("Testing");
		reportObj.testCreation("Screenshot");
		reportObj.errroMsg("hello shreeya");
		String reportPath=reportObj.addScreenshotMethod(driver,folderPathArray[2],"abc",0);
		driver.close();
		ApacheCode coder=new ApacheCode(folderPathArray[0]);
		coder.excelFile();
		//String [] orderDetail= {"../ReportABC/"+"Abc"+".html",reportPath};
		String [] orderDetail= {"../ReportABC/Abc.html",reportPath};
		coder.excelWriter1(folderPathArray, 2);
		coder.closeExcelWriting();
		reportObj.logFlush();
		
	}

}