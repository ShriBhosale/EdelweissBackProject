package com.shreeya;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shreeya.util.ApacheCode;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.HelperCode;


public class Tet {

	 String name="name not found";
	 
	static HashMap<Integer,String> mapObject;
	
	public static void main(String[] args) throws FileNotFoundException {
		String[] headerArray = { "Id", "Action", "Status", "Order Action", "Trading Symbol", "Product Type",
				"Order Price", "Order Type", "User id", "Exchange", "Validity", "Nest Id", "Rejection Reason",
				"ScriptResult", "E://EdelweissProject//Reports//Report1584974785157.html", "E://EdelweissProject//Reports//Report1584974785157.html" };
		Tet t=new Tet();
		ApacheCode code=new ApacheCode("E:\\EdelweissProject\\TestData");
		code.excelWriter(headerArray, 1);
		code.closeExcelWriting();
		
		
	}
	
	
	
	
}
