package com.shreeya;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shreeya.util.ApacheCode;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;


public class Tet {
	
	public static void main(String[] args) throws IOException {
		ApacheCode code=new ApacheCode();
		code.closeExcelWriting();
	}
	
}
