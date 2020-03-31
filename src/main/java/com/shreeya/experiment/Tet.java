package com.shreeya.experiment;

import java.io.IOException;

import com.shreeya.util.ExtendReporter;

public class Tet {

	 static String number="880.00";
	 
	
	
	public static void main(String[] args) throws IOException {
	ExtendReporter report=new ExtendReporter("E:\\EdelweissProject\\Reports\\Report1585229092729","dfsdf");
	
	report.testCreation("ac");
	report.errroMsg();
	report.logFlush();
		
	}
	
	
}	
	
