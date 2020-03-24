package com.shreeya;

import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.shreeya.model.TestDataModel;
import com.shreeya.page.CxlOrderPage;
import com.shreeya.page.LoginPage;
import com.shreeya.page.ModOrderPage;
import com.shreeya.page.NewOrderPage;
import com.shreeya.util.ApacheCode;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.SeleniumCoder;

public class TestLaunch {

	static WebDriver driver;
	static Iterator<TestDataModel> csvTestDataModelIterator;
	static TestDataModel model,testModel;
	CsvReaderCode coder;
	LoginPage login;
	ExtendReporter reporter;
	String [] orderDetailArray;
	CSVWriter writer;

	
	public  TestLaunch() throws IOException {
		coder = new CsvReaderCode();
		writer=coder.writerProvider();
		csvTestDataModelIterator = coder.responseGenerator();
		login = new LoginPage();
		//reporter=new ExtendReporter();
	}
	
	
	public void Execution() throws InterruptedException, IOException {
		//ApacheCode excelWriter=new ApacheCode();
		driver = login.loginExecution(writer);
		
		int orderNo=0;
		while (csvTestDataModelIterator.hasNext()) {
			model = csvTestDataModelIterator.next();
			orderNo++;
			//reporter.testCreation("Order Place "+orderNo);
			if (model.getAction().equalsIgnoreCase("New")) {
				System.out.println("Action :: "+model.getAction());
				NewOrderPage newOrder = new NewOrderPage();
				driver=newOrder.newOrderExecution(model,driver,orderNo);
			} else if (model.getAction().equalsIgnoreCase("Mod")) {
				System.out.println("Action :: "+model.getAction());
				ModOrderPage modOrder = new ModOrderPage();
				driver=modOrder.modExecution(model,driver,orderNo);
			} else if (model.getAction().equalsIgnoreCase("Cxl")) {
				System.out.println("Action :: "+model.getAction());
				CxlOrderPage cxlOrder = new CxlOrderPage();
				cxlOrder.cxlExecution(driver,orderNo);
			}
			
			
		}
		login.logout(driver);
		
		coder.closeWriteFile(writer);
		//excelWriter.closeExcelWriting();
		//reporter.logFlush();
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		TestLaunch testObject=new TestLaunch();
		testObject.Execution();
	}
}
