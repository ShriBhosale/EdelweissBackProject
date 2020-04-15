package com.shreeya.page;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;

import com.opencsv.CSVWriter;
import com.shreeya.model.LoginModel;
import com.shreeya.model.TestDataModel;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.HelperCode;

public class OrderAction {

	 WebDriver driver;
	 Iterator<TestDataModel> csvTestDataModelIterator;
	 Iterator<LoginModel>loginIterator;
	 TestDataModel model,testModel;
	CsvReaderCode coder;
	LoginPage login;
	PartialOrderPage partialOrderOb;
	ExtendReporter reporter;
	String [] orderDetailArray;
	//CSVWriter writer;
	private String newOrderStatus="rejected";
	NewOrderPage newOrder;
	ModOrderPage modOrder;
	CxlOrderPage cxlOrder;
	HelperCode helperObject;
	int countOfrejectNew=0;
	 int rowNo=0;
	private boolean partialOrderReport;
	int orderNo=0;
	private CSVWriter writer;
	private HashMap<WebDriver,String> newMapObject,mapObject;
	
	public OrderAction() throws IOException {
		newOrder=new NewOrderPage();
		modOrder=new ModOrderPage();
		cxlOrder=new CxlOrderPage();
		CsvReaderCode csvReaderObj=new CsvReaderCode();
		csvTestDataModelIterator=csvReaderObj.testDataProvider();
		partialOrderOb=new PartialOrderPage();
		newMapObject=new HashMap<WebDriver,String>();
		mapObject=new HashMap<WebDriver,String>();
		helperObject=new HelperCode();
	}
	
	public WebDriver orderActionStart(WebDriver driver,LoginModel loginModel) throws InterruptedException, IOException {
		
	
		while (csvTestDataModelIterator.hasNext() &&(driver!=null)) {
			model = csvTestDataModelIterator.next();
			orderNo++;
			int startExecution=Integer.valueOf(loginModel.getStartingRowNo());
			int endExecution=Integer.valueOf(loginModel.getEndRowNo());
			System.out.println("endExecution ================================@> "+endExecution+"\nOrderNo ==========@> "+orderNo);
			if(orderNo>=endExecution+1)
				break;
			if(orderNo>=startExecution) {
				
				/* if(orderNo==startExecution) { rowNo=0; } */
				  
				  rowNo++;
				 
				System.out.println("Order No ========================@> "+orderNo+"\nStartExecutionNo =======================@> "+startExecution);
			if(model.getScenario().equalsIgnoreCase("Partial Order")){
					if(!newOrderStatus.equalsIgnoreCase("rejected")||newOrderStatus.equalsIgnoreCase("put order req received")){
				partialOrderOb.partialOrderExecution(model, orderNo,loginModel);
				partialOrderOb.orderDetail(driver, model,orderNo);
				model = csvTestDataModelIterator.next();
				orderNo++;
				orderNo++;
			}else 
				continue;
			
			}
			if (model.getAction().equalsIgnoreCase("New")&&(!model.getScenario().equalsIgnoreCase("Partial Order"))) {
				System.out.println("TestLaunch::Action :: "+model.getAction()+"\n Scenario :: "+model.getScenario());
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
			if(model.getOrderNo().equalsIgnoreCase("16")||model.getOrderNo().equalsIgnoreCase("33")) {
				System.out.println("bugg");
			}
			
			System.out.println("Action ====> "+model.getAction()+" newOrderStatus =====> "+newOrderStatus+"\nRowNo==============> "+rowNo);
			
			String status=helperObject.outputProcessor(driver, model.getAction(), orderNo, newOrderStatus, model,rowNo);
			if(model.getAction().equalsIgnoreCase("New")&& model.getScenario().equalsIgnoreCase("Fresh Order Placement")) {
				newOrderStatus=status;
			}
			if(countOfrejectNew==4) {
				
				break;
			}
			
			
			}
		}
		
		return driver;
	}
}
