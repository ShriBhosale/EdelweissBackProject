package com.shreeya.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.shreeya.Execution;
import com.shreeya.model.TestDataModel;
import com.shreeya.page.OrderDetail;

import net.bytebuddy.description.modifier.SynchronizationState;

public class HelperCode {
	private String reportPathString;
	ExtendReporter report;
	String [] resultString = {"FAIL","NOT MOD"};
	String[] pathArray = { "Report path not found", "ScreenShot path not found" };
	static ApacheCode excelWriter = null;
	int noRowInTestData=0;
	private String [] folderPathArray= {"ReportFolder path","htmlFolder path","screenshort path"};
	String[] orderDetailArray= { "Id", "Action", "Status", "Order Action", "Trading Symbol", "Product Type",
		"Order Price", "Order Type", "User id", "Exchange", "Validity", "Nest Id","Qty","Partial Qty","Rejection Reason",
		"ScriptResult", "Report link", "Screenshot link" };
	 boolean reportFlag=false;
	static private int noRowInTestData1;
	static boolean amoFlag=false;
	static int countNewOrderReject=0;
	static int rowPrint=0;
	static boolean excelFileClose=false;
	static String outputFolderPath="Main output folder not generated in HelperCode";
	static int executionCount=0;
	public HelperCode() {

	}

	public String nestIdProvider(String strForNestId) {
		String arr[] = strForNestId.split(">");
		String[] nestIdArray = arr[1].split("<");
		/*
		 * for(String a:nestIdArray) { Reporter.log(a);
		 * Reporter.log("===================================="); }
		 * 
		 */
		if(nestIdArray[0].contains("|"))
		{
			nestIdArray[0]=nestIdArray[0].replace("|", "");
		}
		return nestIdArray[0];
	}
	
	public String removeExtraString(String str,String extraString) {
		return str.replace(extraString, "");
	}

	public String timeStampGenerator() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String timeStamGeString=Long.toString(timestamp.getTime());
		Reporter.log(timeStamGeString,true);
		return Long.toString(timestamp.getTime());
	}

	public String[] passFailResult(String[] orderDetail,TestDataModel model) {
		Reporter.log("<=== passFail Result "+model.getAction()+" "+model.getOrderNo()+" ===>",true);
		if (orderDetail[1].equalsIgnoreCase("New")) {
			if (orderDetail[2].equalsIgnoreCase("Open")||orderDetail[2].equalsIgnoreCase("Complete")) {
				resultString[0] = "PASS";
			}
		} else if (orderDetail[1].equalsIgnoreCase("Mod")) {
			if (orderDetail[2].equalsIgnoreCase("modified")||orderDetail[2].equalsIgnoreCase("Modify After Market Order Req Received")){
				resultString[0] = "PASS";
			if(model.getScenario().equalsIgnoreCase("Modification Price")) {
				if(orderDetailArray[6].equalsIgnoreCase(model.getPartialPrice())) {
					resultString[1] = "PASS";
				}else if(removeDecimal(orderDetailArray[6]).equalsIgnoreCase(model.getPartialPrice())) {
					resultString[1] = "PASS";
				}
			}else if(model.getScenario().equalsIgnoreCase("Modification Qty")) {
				
			if (orderDetail[2].equalsIgnoreCase("modified")&&(orderDetail[12].equalsIgnoreCase(model.getQtyMod()))) {
				resultString[1]= "PASS";
			}
			}
			
		} 
		}else if (orderDetail[1].equalsIgnoreCase("Cxl")) {
			if (orderDetail[2].equalsIgnoreCase("cancelled")) {
				resultString[0] = "PASS";
			}
		}else if(orderDetail[1].equalsIgnoreCase("Partial Order")) {
			if(orderDetail[2].equalsIgnoreCase("complete")||orderDetail[2].equalsIgnoreCase("open"));
			{
				resultString[0] = "PASS";
				if(orderDetail[13].equalsIgnoreCase(model.getQtyMod())&&orderDetail[12].equalsIgnoreCase(model.getQty())) {
				resultString[1] = "PASS";
				}
			}
		}
		Reporter.log("Action Result ===> "+resultString[0]+"\nMod Scenario wise Result ====> "+resultString[1],true);
		return resultString;
	}

	public String[] screenShotAndReportPath(WebDriver driver, ExtendReporter report,String folderPathStr,String scenario,int orderNo) throws IOException {
		// report=new ExtendReporter();
		String[] pathArray = { report.getReportPathString(), report.captureScreen(driver,folderPathStr,scenario,orderNo) };
		return pathArray;
	}
	
	

	public String outputProcessor(WebDriver driver, String action, int orderNo,String newOrderStatus,TestDataModel model,int rowPrint)
			throws InterruptedException, IOException {
		Reporter.log("*************************************<<<<<<<<<<<<Output Processor Started>>>>>>>>>>>>>>>>************************************",true);
		Reporter.log("====<<<<< OrderNo in Sheet "+model.getOrderNo()+" Action : "+model.getAction()+" >>>>>====",true);
		executionCount++;
		
		Reporter.log("FolderCreation array form execution class ===> "+Execution.folderPath[0],true);
		
		boolean reportFlag=false;
		rowPrint++;
		if(!newOrderStatus.equalsIgnoreCase("Terminate")) {
			Reporter.log("Action ======>  "+action+"\nNewOrderStatus =====>  "+newOrderStatus,true);
		
			Reporter.log("New order status =====> "+newOrderStatus,true);
		report = new ExtendReporter(Execution.folderPath[1],model.getScenario(),orderNo);
		report.testCreation("Order Detail " + orderNo);
		
		if((action.equalsIgnoreCase("Mod")||action.equalsIgnoreCase("Cxl"))&&((newOrderStatus.equalsIgnoreCase("Open")||newOrderStatus.equalsIgnoreCase("after market order req received")))){
			
				reportFlag=true;
			
		} else if (action.equalsIgnoreCase("New")&& model.getScenario().equalsIgnoreCase("Fresh Order Placement")){
			reportFlag=true;
			
		}else if(model.getScenario().equalsIgnoreCase("Partial Order")&&(!newOrderStatus.equalsIgnoreCase("rejected"))) {
			reportFlag=true;
			
		}
		
		if(reportFlag) {
			Reporter.log("Execution Count : "+rowPrint,true);
			Reporter.log("Execution no ===> "+executionCount);
		//Reporter.log("Order no===========================================================> "+orderNo+"\nExecution Count==========================================>"+rowPrint);
		//Reporter.log("noRowInTestData : "+noRowInTestData+"\n folderPathArray[0] : "+folderPathArray[0]);
		if(executionCount==1) {
			CsvReaderCode reader=new CsvReaderCode();
			noRowInTestData1=reader.noRowInTestData();
			FolderStructure folderStructureObject=new FolderStructure();
			folderStructureObject.copyFile(Execution.folderPath[0]);
		 //excelWriter=new ApacheCode(folderPathArray[0]);
		 Execution.apacheCodeObj.outputFileWriterHeader(Execution.folderPath[0]);
		}
		OrderDetail orderDetailObj = new OrderDetail();
		orderDetailArray = orderDetailObj.orderDetailProvider(driver, action,model.getOrderNo());
		
		Reporter.log("OutSide orderDetailProvider method......",true);
		orderDetailArray[0] = String.valueOf(orderNo);
		orderDetailArray[1] = action;
		if(action.equalsIgnoreCase("Partial Order")) {
			noRowInTestData1++;
			model.setAction(action);
		}
		resultString = passFailResult(orderDetailArray,model);
		report.reportGenerator(orderDetailArray,resultString,model);
		if(action.equalsIgnoreCase("Mod")||action.equalsIgnoreCase("Partial Order")) {
			if(resultString[0].equalsIgnoreCase("PASS")&&resultString[1].equalsIgnoreCase("PASS")){
				orderDetailArray[15] = "PASS";
			}else {
				orderDetailArray[15] = "FAIL";
			}
		}else {
			orderDetailArray[15] = resultString[0];
		}
		
		pathArray = screenShotAndReportPath(driver, report,Execution.folderPath[2],model.getScenario(),orderNo);
		orderDetailArray[16] = pathArray[0];
		orderDetailArray[17] = pathArray[1];
		//Execution.apacheCodeObj.excelWriter(orderDetailArray, rowPrint);
		Execution.apacheCodeObj.outputFileWriter(orderDetailArray, orderNo);
		//excelWriter.excelWriter(orderDetailArray, orderNo);
		for(String orderDetail:orderDetailArray)
			Reporter.log(orderDetail,true);
		
		report.logFlush();
		}
		else {
		report.errroMsg("New order reject");
		orderDetailArray[2]="New order reject1";
		
		//noRowInTestData=orderNo;
		}
		//report.tearDown(resultString);
		
		Reporter.log("<<==== After if else =========>>",true);
		/*
		 * for(String orderDetail:orderDetailArray) Reporter.log(orderDetail);
		 */
		if(orderDetailArray[2].equalsIgnoreCase("rejected")&& model.getScenario().equalsIgnoreCase("Fresh Order Placement")) {
			countNewOrderReject++;
		}
		if(countNewOrderReject==4) {
			if(excelFileClose==false) {
			//Execution.apacheCodeObj.closeExcelWriting();
			//Execution.apacheCodeObj.outputExcelFileClose(outputFolderPath);
			excelFileClose=true;
			}else {
				Reporter.log("Excel file close successfully..........................",true);
			}
		}
		}else {
			Reporter.log("****Termination Conditin****",true);
			noRowInTestData1=0;
			if(excelFileClose==false) {
				Reporter.log("ExcelFileClose flag : "+excelFileClose,true);
				//Execution.apacheCodeObj.closeExcelWriting();
				//Execution.apacheCodeObj.outputExcelFileClose(outputFolderPath);
				excelFileClose=true;
			}
		}
		Reporter.log("Order no =====>  "+orderNo+"\nNoRowInTestData =======> "+noRowInTestData1,true);
		if(noRowInTestData1==orderNo) {
			Reporter.log("Excel file closing...........",true);
			Reporter.log("ExcelFileClose flag : "+excelFileClose,true);
			if(excelFileClose==false) {
				
				//Execution.apacheCodeObj.closeExcelWriting();
				Execution.apacheCodeObj.outputExcelFileClose(outputFolderPath);
		excelFileClose=true;
			}
		}
		return orderDetailArray[2];
	}
	
	public  String getCurrentTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date now = new Date();
	    String strDate = sdf.format(now);
	    return strDate;
	}

	public String statusRemoveBracket(Collection<String> str) {
		String name=str.toString();
		name=name.replace("[", "");
		name=name.replace("]", "");
		
		return name;
	}

	public String removeDecimal(String number) {
		String [] no=number.split("\\.");
		if(!no[1].equalsIgnoreCase("00")) {
			no[0]=number;
		}
		Reporter.log(no[0],true);
		return no[0];
	}
	

}
