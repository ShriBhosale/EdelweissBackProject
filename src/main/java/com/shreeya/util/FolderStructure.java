package com.shreeya.util;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

public class FolderStructure {
	static String timeStamp;

	public String[] reportFolderCreator(int orderNo) {
		System.out.println("reportFolderCreator orderNo =====> "+orderNo);
		HelperCode helperObject = new HelperCode();
		if(orderNo==1) {
		timeStamp = helperObject.timeStampGenerator();
		}
		String reportFolderPath = "../Reporter/Report" + timeStamp;
		String subFolderPath= "../Report" + timeStamp;
		String[] folderArray = { reportFolderPath, reportFolderPath + "/HtmlReports",
				reportFolderPath + "/Screenshots" };
		for (int i = 0; i < 3; i++) {
			File reportFolder = new File(folderArray[i]);
			reportFolder.mkdir();
		}
		return folderArray;
	}
	
	public void copyFile() throws IOException {
		File file=new File("E:\\EdelweissProject\\Sources\\PQR.xlsx");
		File targetFile=new File("E:\\EdelweissProject\\Target\\PQR.xlsx");
		//file.renameTo(targetFile);
		Files.copy(targetFile, file);
		System.out.println("File copy pasting done...............");
	}

}
