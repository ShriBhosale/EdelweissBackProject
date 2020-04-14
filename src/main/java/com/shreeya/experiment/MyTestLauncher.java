package com.shreeya.experiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.shreeya.model.LoginModel;
import com.shreeya.util.CsvReaderCode;

public class MyTestLauncher {
	public static ArrayList<LoginModel> loginData;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		CsvReaderCode csvReader = new CsvReaderCode(); 
		loginData =csvReader.LoginFileReader();
		Iterator<LoginModel> loginIteratior = loginData.iterator();
		
		
		XmlSuite suite = new XmlSuite();
		suite.setName("TmpSuite");
		suite.setParallel(XmlSuite.ParallelMode.TESTS);
		suite.setThreadCount(2);
		int count=0;
		 while(loginIteratior.hasNext()) {
			 LoginModel loginModel = loginIteratior.next(); 
			 Map<String,String> testScenarioParameters = new HashMap<>();
			 testScenarioParameters.put("Reference", loginModel.getReferNo());
		XmlTest test = new XmlTest(suite);
		test.setName(loginModel.getReferNo());
		test.setParameters(testScenarioParameters);
		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(new XmlClass(ParalleExecution.class.getName()));
		test.setXmlClasses(classes) ;
		 }
		 List<XmlSuite> suites = new ArrayList<XmlSuite>();
		 suites.add(suite);
		 
		 TestNG tng = new TestNG();
		 
		 tng.setXmlSuites(suites);
		 tng.run(); 
		 
	}

}
