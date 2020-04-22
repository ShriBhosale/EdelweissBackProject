package com.shreeya.amazone;

import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.shreeya.model.MasterTestModel;
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.util.CsvReaderCode;

public class TestFactory1 {
	
	
	static MasterTestModel masterTestmodel;
	public static void main(String[] args) throws IOException {
		CsvReaderCode code=new CsvReaderCode();
		Iterator<MasterTestModel> csvMasterTestModelIterator=code.masterTestDataProvider();
		while(csvMasterTestModelIterator.hasNext()) {
			masterTestmodel=csvMasterTestModelIterator.next();
			if(masterTestmodel.getKeyword().equalsIgnoreCase("seeholdings")) {
				Reporter.log("Steps : "+masterTestmodel.getSteps(), true);
			}
		}
	}
}
