package com.shreeya;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shreeya.util.ApacheCode;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.HelperCode;


public class Tet {
	static ExtendReporter report;
	public static void main(String[] args) throws IOException {
		String [] arr= {"shreeya","priyanka","pravina","apurva"};
	 report=new ExtendReporter(arr[0]);
	report.testCreation("abc");
	report.report(arr);
	report.tearDown("PASS");
	report.logFlush();
	
				
	}
	
}
