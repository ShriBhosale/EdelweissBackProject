package com.shreeya.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Reporter;

import com.google.common.io.Files;

public class FolderStructure {
	static String timeStamp;
	static int orderNo=0;
	static String[] folderArray = { "no", "no" + "/HtmlReports",
			"no" + "/Screenshots" };
	ConfigReader configReader=new ConfigReader();
	public String[] reportFolderCreator() throws IOException {
		Reporter.log("Folder Structure creation method ", true);
		Reporter.log("orderNo ===> "+orderNo, true);
		
		HelperCode helperObject = new HelperCode();
		
		String outputFile="FolderStructure not able give outputFile";
		if(orderNo==0) {
			timeStamp = helperObject.timeStampGenerator();
			//copyFile(configReader.configReader("TestData")+".xlsx", "../WorkingE/Report" + timeStamp+"/OutputFile.xlsx");
			//outputFile="../WorkingE/Report" + timeStamp+"/OutputFile.xlsx";
	
		String reportFolderPath = "../WorkingE/Report" + timeStamp;
		String subFolderPath= "../Report" + timeStamp;
		folderArray[0]=reportFolderPath;
		folderArray[1]=reportFolderPath + "/HtmlReports";
		folderArray[2]=reportFolderPath + "/Screenshots" ;
		
		for (int i = 0; i < 3; i++) {
			File reportFolder = new File(folderArray[i]);
			if(!reportFolder.exists()) {
			Reporter.log("FolderArray["+i+"] : "+folderArray[i], true);
			reportFolder.mkdir();
			}
		}
		}
		orderNo++;
		return folderArray;
	}
	
	public  String [] createFolderForFailReport(String pathString) {
		HelperCode helperObject=new HelperCode();
		String [] pathArray= {pathString,pathString+"/Screenshot"};
		System.out.println("Folder Path "+pathArray[0]+"\nScreenshot folderPath====> "+pathString+"/Screenshot");
		File errorReportFolder=new File(pathArray[0]);
		errorReportFolder.mkdir();
		File errorReportSubFolder=new File(pathArray[1]);
		errorReportSubFolder.mkdir();
		return pathArray;
	}
	
	public String copyFile(String targetName) throws IOException {
		
		ConfigReader configReader=new ConfigReader();
		File source = new File(configReader.configReader("TestData")+".xlsx");
        File dest = new File(targetName+"/OutputFile.xlsx");

        FileUtils.copyFile(source, dest);
        Reporter.log("TestData File copy into output folder",true);
        Reporter.log("Output excel file : "+dest.toPath(),true);
		return dest.toString();
				
	}
	
	

}
