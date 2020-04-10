package com.shreeya.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.common.io.Files;

public class FolderStructure {
	static String timeStamp;
	ConfigReader configReader=new ConfigReader();
	public String[] reportFolderCreator(int orderNo) throws IOException {
		System.out.println("reportFolderCreator orderNo =====> "+orderNo);
		HelperCode helperObject = new HelperCode();
		
		String outputFile="FolderStructure not able give outputFile";
		if(orderNo==1) {
			timeStamp = helperObject.timeStampGenerator();
			//copyFile(configReader.configReader("TestData")+".xlsx", "../WorkingE/Report" + timeStamp+"/OutputFile.xlsx");
			//outputFile="../WorkingE/Report" + timeStamp+"/OutputFile.xlsx";
		}
		String reportFolderPath = "../WorkingE/Report" + timeStamp;
		String subFolderPath= "../Report" + timeStamp;
		String[] folderArray = { reportFolderPath, reportFolderPath + "/HtmlReports",
				reportFolderPath + "/Screenshots" };
		for (int i = 0; i < 3; i++) {
			File reportFolder = new File(folderArray[i]);
			reportFolder.mkdir();
		}
		return folderArray;
	}
	
	public void createFolderForFailReport(String pathString) {
		File errorReportFolder=new File(pathString);
		errorReportFolder.mkdir();
		File errorReportSubFolder=new File(pathString+"/Screenshot");
		errorReportSubFolder.mkdir();
	}
	
	public String copyFile(String targetName) throws IOException {
		ConfigReader configReader=new ConfigReader();
		File source = new File(configReader.configReader("TestData")+".xlsx");
        File dest = new File(targetName+"/OutputFile.xlsx");

        FileUtils.copyFile(source, dest);
        System.out.println("TestData File copy into output folder");
        System.out.println(dest.toPath());
		return dest.toString();
				
	}
	
	

}
