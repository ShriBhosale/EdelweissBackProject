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
import com.shreeya.page.PartialOrderPage;


public class TestLaunch {

	static WebDriver driver;
	static Iterator<TestDataModel> csvTestDataModelIterator;
	static TestDataModel model,testModel;
	CsvReaderCode coder;
	LoginPage login;
	ExtendReporter reporter;
	String [] orderDetailArray;
	CSVWriter writer;
	private String newOrderStatus="rejected";
	NewOrderPage newOrder;
	ModOrderPage modOrder;
	CxlOrderPage cxlOrder;
	int countOfrejectNew=0;
	private boolean partialOrderReport;

	
	public  TestLaunch() throws IOException {
		coder = new CsvReaderCode();
		writer=coder.writerProvider();
		csvTestDataModelIterator = coder.responseGenerator();
		login = new LoginPage();
		newOrder=new NewOrderPage();
		 modOrder = new ModOrderPage();
		 cxlOrder = new CxlOrderPage();
	}
	
	
	public void Execution() throws InterruptedException, IOException {
		//ApacheCode excelWriter=new ApacheCode();
		HashMap<WebDriver,String> mapObject=new HashMap<WebDriver,String>();
		HashMap<WebDriver,String> newMapObject=new HashMap<WebDriver,String>();
		PartialOrderPage partialOrderOb=new PartialOrderPage();
		driver = login.loginExecution("no scenario");
		login.headerInExcel(writer);
		HelperCode helperObject=new HelperCode();
		String timeStamp=helperObject.timeStampGenerator();
		//reporter=new ExtendReporter(timeStamp,"dfsf","jgug",1);
		int orderNo=0;
		while (csvTestDataModelIterator.hasNext() &&(driver!=null)) {
			model = csvTestDataModelIterator.next();
			orderNo++;
			if(model.getScenario().equalsIgnoreCase("Partial Order")){
					if(!newOrderStatus.equalsIgnoreCase("rejected")||newOrderStatus.equalsIgnoreCase("put order req received")){
				partialOrderOb.partialOrderExecution(model.getScenario(), model, orderNo);
				partialOrderOb.orderDetail(driver, model,orderNo);
				model = csvTestDataModelIterator.next();
				orderNo++;
				orderNo++;
			}else 
				continue;
			
			}
			if (model.getAction().equalsIgnoreCase("New")&&(!model.getScenario().equalsIgnoreCase("Partial Order"))) {
				System.out.println("TestLaunch::Action :: "+model.getAction());
				newMapObject=newOrder.newOrderExecution(model,driver,orderNo);
				// newOrderStatus = helperObject.statusRemoveBracket(newMapObject.values());
				 if(newOrderStatus.equalsIgnoreCase("rejected")) {
					 countOfrejectNew++;
				 }
			} else if (model.getAction().equalsIgnoreCase("Mod")) {
				// newOrderStatus = helperObject.statusRemoveBracket(newMapObject.values());
				 System.out.println("TestLaunch::Action :: "+model.getAction());
				 if(!newOrderStatus.equalsIgnoreCase("rejected")) {
				mapObject=modOrder.modExecution(model,driver,orderNo,newOrderStatus);
				 }else {
					 continue;
				 }
				
			} else if (model.getAction().equalsIgnoreCase("Cxl")) {
				String modOrderStatus = helperObject.statusRemoveBracket(mapObject.values());
				if(modOrderStatus.equalsIgnoreCase("complete")) 
					newOrderStatus=modOrderStatus;
				else
				//newOrderStatus = helperObject.statusRemoveBracket(newMapObject.values());
				System.out.println("TestLaunch::Action :: "+model.getAction());
				 if(!newOrderStatus.equalsIgnoreCase("rejected")) {
				mapObject=cxlOrder.cxlExecution(driver,orderNo,newOrderStatus,model);
				 }else {
					 continue;
				 }
			}
			
			
			System.out.println("Action ====> "+model.getAction()+" newOrderStatus =====> "+newOrderStatus);
			
			String status=helperObject.outputProcessor(driver, model.getAction(), orderNo, newOrderStatus, model);
			if(model.getAction().equalsIgnoreCase("New")&& model.getScenario().equalsIgnoreCase("Fresh Order Placement")) {
				newOrderStatus=status;
			}
			if(countOfrejectNew==4) {
				
				break;
			}
		}
		if(driver != null) {
			helperObject.outputProcessor(driver, model.getAction(), orderNo, "Terminate", model);
		login.logout(driver);
		driver.close();
		}else {
			
		}
		
		coder.closeWriteFile(writer);
		//excelWriter.closeExcelWriting();
		//reporter.logFlush();
	}
	@Test
	public  void main(/*String[] args*/) throws InterruptedException, IOException {
		TestLaunch testObject=new TestLaunch();
		testObject.Execution();
	}
}
