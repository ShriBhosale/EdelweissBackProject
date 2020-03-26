package com.shreeya;

import java.io.IOException;
import java.util.HashMap;
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
import com.shreeya.util.HelperCode;
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
	private String newOrderStatus;
	

	
	public  TestLaunch() throws IOException {
		coder = new CsvReaderCode();
		writer=coder.writerProvider();
		csvTestDataModelIterator = coder.responseGenerator();
		login = new LoginPage();
		
	}
	
	
	public void Execution() throws InterruptedException, IOException {
		//ApacheCode excelWriter=new ApacheCode();
		HashMap<WebDriver,String> mapObject=new HashMap<WebDriver,String>();
		HashMap<WebDriver,String> newMapObject=new HashMap<WebDriver,String>();
		driver = login.loginExecution(writer);
		HelperCode helperObject=new HelperCode();
		String timeStamp=helperObject.timeStampGenerator();
		reporter=new ExtendReporter(timeStamp);
		int orderNo=0;
		while (csvTestDataModelIterator.hasNext()) {
			model = csvTestDataModelIterator.next();
			orderNo++;
			//reporter.testCreation("Order Place "+orderNo);
			if (model.getAction().equalsIgnoreCase("New")) {
				System.out.println("Action :: "+model.getAction());
				NewOrderPage newOrder = new NewOrderPage();
				newMapObject=newOrder.newOrderExecution(model,driver,orderNo);
			} else if (model.getAction().equalsIgnoreCase("Mod")) {
				 newOrderStatus = helperObject.statusRemoveBracket(newMapObject.values());
				 
				System.out.println("Action :: "+model.getAction());
				/*if(newOrderStatus.equalsIgnoreCase("Open")) {*/
				ModOrderPage modOrder = new ModOrderPage();
				
				mapObject=modOrder.modExecution(model,driver,orderNo,newOrderStatus);
				/*}else {
					continue;
				}*/
				
			} else if (model.getAction().equalsIgnoreCase("Cxl")) {
				newOrderStatus = helperObject.statusRemoveBracket(newMapObject.values());
				System.out.println("Action :: "+model.getAction());
				/*if(newOrderStatus.equalsIgnoreCase("Open")) {*/
				CxlOrderPage cxlOrder = new CxlOrderPage();
				mapObject=cxlOrder.cxlExecution(driver,orderNo,newOrderStatus);
				/*}else {
					continue;
				}*/
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
