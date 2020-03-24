package com.shreeya.util;

import java.io.File;

public class FolderStructure {
	String timeStamp;

	public String[] reportFolderCreator(int orderNo) {
		System.out.println("abc");
		HelperCode helperObject = new HelperCode();
		if(orderNo==1) {
		timeStamp = helperObject.timeStampGenerator();
		}
		String reportFolderPath = "E:\\EdelweissProject\\Reports\\Report" + timeStamp;
		String[] folderArray = { reportFolderPath, reportFolderPath + "\\HtmlReports",
				reportFolderPath + "\\Screenshots" };
		for (int i = 0; i < 3; i++) {
			File reportFolder = new File(folderArray[i]);
			reportFolder.mkdir();
		}
		return folderArray;
	}

}
