package com.shreeya.orderdetailpages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.opencsv.CSVWriter;
import com.shreeya.model.LoginModel;
import com.shreeya.model.TestDataModel;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.HelperCode;
import com.shreeya.util.SeleniumCoder;

public class OrderAction extends SeleniumCoder{

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
	LoginPage loginPageObj;
	int countOfrejectNew=0;
	 int rowNo=0;
	private boolean partialOrderReport;
	int orderNo=0;
	private CSVWriter writer;
	private HashMap<WebDriver,String> newMapObject,mapObject;
	
	public OrderAction(WebDriver driver) throws IOException {
		super(driver);
		this.driver=driver;
		newOrder=new NewOrderPage(driver);
		modOrder=new ModOrderPage(driver);
		cxlOrder=new CxlOrderPage(driver);
		CsvReaderCode csvReaderObj=new CsvReaderCode();
		csvTestDataModelIterator=csvReaderObj.testDataProvider();
		partialOrderOb=new PartialOrderPage(driver);
		newMapObject=new HashMap<WebDriver,String>();
		mapObject=new HashMap<WebDriver,String>();
		helperObject=new HelperCode();
		loginPageObj=new LoginPage(driver);
	}
	
	public WebDriver orderActionStart(LoginModel loginModel) throws InterruptedException, IOException {
		//WebDriver driver=loginPageObj.driver;
		Reporter.log("<========OrderActionStart=======>",true);
		Reporter.log(loginModel.toString(),true);
		while (csvTestDataModelIterator.hasNext() &&(driver!=null)) {
			model = csvTestDataModelIterator.next();
			orderNo++;
			int startExecution=Integer.valueOf(loginModel.getStartingRowNo());
			int endExecution=Integer.valueOf(loginModel.getEndRowNo());
			Reporter.log("endExecution ================================@> "+endExecution+"\nOrderNo ==========@> "+orderNo,true);
			
			if(orderNo>=endExecution+1)
				break;
			if(orderNo>=startExecution) {
				
				/* if(orderNo==startExecution) { rowNo=0; } */
				  
				  rowNo++;
				 
				  Reporter.log("Order No ========================@> "+orderNo+"\nStartExecutionNo =======================@> "+startExecution,true);
				  Reporter.log("Scenarion : "+model.getScenario()+"\nAction : "+model.getAction()+"\nNewOrderStatus : "+newOrderStatus,true);
			if(model.getScenario().equalsIgnoreCase("Partial Order")){
				
				if(!newOrderStatus.equalsIgnoreCase("rejected")||newOrderStatus.equalsIgnoreCase("put order req received")){
				partialOrderOb.partialOrderExecution(model, orderNo,loginModel);
				partialOrderOb.orderDetail(driver,model,orderNo);
				model = csvTestDataModelIterator.next();
				//orderNo++;
				//orderNo++;
			}else 
				Reporter.log("Partial Order but new order status rejected",true);
				continue;
			
			}
			if (model.getAction().equalsIgnoreCase("New")&&(!model.getScenario().equalsIgnoreCase("Partial Order"))) {
				
				newMapObject=newOrder.newOrderExecution(model,driver,orderNo);
				// newOrderStatus = helperObject.statusRemoveBracket(newMapObject.values());
				 if(newOrderStatus.equalsIgnoreCase("rejected")) {
					 countOfrejectNew++;
				 }
			} else if (model.getAction().equalsIgnoreCase("Mod")) {
				// newOrderStatus = helperObject.statusRemoveBracket(newMapObject.values());
				 Reporter.log("TestLaunch::Action :: "+model.getAction(),true);
				 if(!newOrderStatus.equalsIgnoreCase("rejected")) {
				mapObject=modOrder.modExecution(model,driver,orderNo,newOrderStatus);
				 }else {
					 Reporter.log("Action MOD but new order status rejected",true);
					 helperObject.reportForSkipAction(model);
					 continue;
				 }
				
			} else if (model.getAction().equalsIgnoreCase("Cxl")) {
				String modOrderStatus = helperObject.statusRemoveBracket(mapObject.values());
				if(modOrderStatus.equalsIgnoreCase("complete")) 
					newOrderStatus=modOrderStatus;
				else
				//newOrderStatus = helperObject.statusRemoveBracket(newMapObject.values());
				
				 if(!newOrderStatus.equalsIgnoreCase("rejected")) {
				mapObject=cxlOrder.cxlExecution(driver,orderNo,newOrderStatus,model);
				 }else {
					 Reporter.log("Action CXL but new order status rejected",true);
					 helperObject.reportForSkipAction(model);
					 continue;
				 }
			}
			
			
			Reporter.log("Action ====> "+model.getAction()+" newOrderStatus =====> "+newOrderStatus+"\nRowNo==============> "+rowNo,true);
			
			String status=helperObject.outputProcessor(driver, model.getAction(), orderNo, newOrderStatus, model,rowNo);
			if(model.getAction().equalsIgnoreCase("New")&& model.getScenario().equalsIgnoreCase("Fresh Order Placement")) {
				newOrderStatus=status;
			}
			if(countOfrejectNew==4) {
				Reporter.log("<==================@@@@@@@ New order Rejection count no : @@@@@@====================> "+4,true);
				break;
			}
			
			
			}
		}
		
		return driver;
	}
}
