package com.shreeya;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.io.Files;
import com.shreeya.util.ApacheCode;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.HelperCode;


public class Tet {

	 static String number="880.00";
	 
	
	
	public static void main(String[] args) throws IOException {
	ExtendReporter report=new ExtendReporter("E:\\EdelweissProject\\Reports\\Report1585229092729","dfsdf");
	
	report.testCreation("ac");
	report.errroMsg();
	report.logFlush();
		
	}
	
	
	
	
}
