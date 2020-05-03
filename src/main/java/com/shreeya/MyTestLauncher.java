package com.shreeya;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;
import org.testng.TestNG;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.shreeya.model.LoginModel;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.FolderStructure;

public class MyTestLauncher {
	public static ArrayList<LoginModel> loginData;
	public static FolderStructure folderCreationObj;
	public static String [] reportFolderPath;
	
	public MyTestLauncher() throws IOException {
		 folderCreationObj = new FolderStructure();
		 reportFolderPath = folderCreationObj.reportFolderCreator();
	}


	public static void main( String[] args ) throws IOException {
		Reporter.log("================<< Execution Started >>================");
		// TODO Auto-generated method stub
		MyTestLauncher launcher=new MyTestLauncher();
		CsvReaderCode csvReader = new CsvReaderCode(); 
		loginData =csvReader.LoginFileReader();
		Iterator<LoginModel> loginIteratior = loginData.iterator();
		XmlSuite suite = new XmlSuite();
		suite.setName("TestNGReport");
		suite.setParallel(XmlSuite.ParallelMode.TESTS);
		suite.setThreadCount(2);
		
		int count=0;
		 while(loginIteratior.hasNext()) {
			 LoginModel loginModel = loginIteratior.next(); 
			 Reporter.log("MyTestLauncher "+loginModel.toString(), true);
			 Map<String,String> testScenarioParameters = new HashMap<>();
			 testScenarioParameters.put("Reference", loginModel.getReferNo());
			 testScenarioParameters.put("UserId", loginModel.getUserId());
			 testScenarioParameters.put("Pwd", loginModel.getPassword());
			 testScenarioParameters.put("Yob", loginModel.getYob());
			 testScenarioParameters.put("StartNo", loginModel.getStartingRowNo());
			 testScenarioParameters.put("EndNo", loginModel.getEndRowNo());
			 testScenarioParameters.put("Module", loginModel.getModule());
		XmlTest test = new XmlTest(suite);
		Reporter.log("ReferNo=====> "+loginModel.getReferNo(),true);
		test.setName("ServerLog_"+loginModel.getReferNo());
		test.setVerbose(2);
		test.setParameters(testScenarioParameters);
		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(new XmlClass(FunctionKeyword.class.getName()));
		test.setXmlClasses(classes) ;
		 }
		 List<XmlSuite> suites = new ArrayList<XmlSuite>();
		 suites.add(suite);
		 
		 TestNG tng = new TestNG();
		 
		 tng.setXmlSuites(suites);
		 tng.setOutputDirectory(reportFolderPath[0]+"/SeverLog");
		
		 tng.run(); 
		 
	}

}
