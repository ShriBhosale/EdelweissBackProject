package com.shreeya.experiment;

import org.testng.Reporter;

import com.shreeya.util.FolderStructure;

public class Execution {
	
	static String path="E:\\EdelweissProject\\WorkingE";
	
	public String generateRelativePath(String path) {
		path=path.replace("\\", "-");
		String [] pathArray=path.split("-");
		for(String pathString:pathArray) {
			System.out.println(pathString);
		}
		String relativePath="../"+pathArray[pathArray.length-1]+"/Report";
		System.out.println(relativePath);
		return relativePath;
	}
	
	public void abc() {
		Reporter.log("Hello shreeya I am from execution ",true);
	}
	public static void main(String[] args) {
		FolderStructure folder=new FolderStructure();
		Execution e=new Execution();
		folder.createFolderForFailReport(e.generateRelativePath(path));
		
	}
	

}
