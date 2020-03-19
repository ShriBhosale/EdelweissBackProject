package com.shreeya;

import java.util.ArrayList;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shreeya.util.ExtendReporter;
import com.shreeya.util.HtmlReporter;

public class Tet {
	
	public static void main(String[] args) {
		System.out.println("Exectuion is started...........");
		ExtendReporter reporter=new  ExtendReporter();
		ArrayList<String> lsit=new ArrayList<String>();
		lsit.add("abc");
		lsit.add("xys");
		int i=1;
		while(i<=3){
		reporter.testCreation("Test no "+i);
		reporter.logsPrinter(lsit);
		i++;
		}
		reporter.logFlush();
	}
	
}
