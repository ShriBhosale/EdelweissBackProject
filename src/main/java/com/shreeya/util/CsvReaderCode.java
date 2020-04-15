package com.shreeya.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.shreeya.model.LoginModel;
import com.shreeya.model.TestDataModel;

import freemarker.core.ReturnInstruction.Return;
import net.bytebuddy.description.modifier.SynchronizationState;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CsvReaderCode {

	String responseString;
	static Iterator<TestDataModel> csvTestDataModelIterator;
	static TestDataModel model;
	CSVWriter writer;
	HelperCode helperObject;

	public CsvReaderCode() throws IOException  {
		
	}
	
	public CSVWriter writerProvider()throws IOException {
		helperObject = new HelperCode();
		File file = new File("E:\\EdelweissProject\\Reports\\ReportInExcel\\ExcelReport"+ helperObject.timeStampGenerator() + ".csv");

		FileWriter outputfile = new FileWriter(file, true);

		return new CSVWriter(outputfile);
	}

	public Iterator<TestDataModel> testDataProvider() {
		ConfigReader configReader=new ConfigReader();
		String testDataPath=configReader.configReader("TestData");
		CSVReader reader = null;
		System.out.println("Test Data ======> "+testDataPath);
		try {
			reader = new CSVReader(new FileReader(testDataPath+".txt"), '\t');
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CsvToBean<TestDataModel> csvToBean = new CsvToBeanBuilder(reader).withType(TestDataModel.class).build();

		Iterator<TestDataModel> csvTestDataModelIterator = csvToBean.iterator();
		
		
		return csvTestDataModelIterator;
	}
	
	
	public int noRowInTestData() {
		System.out.println("****************************************Number no counting start***************************************************");
		TestDataModel m1;
		int i=0;
		Iterator<TestDataModel> csvTestDataModelIterator=testDataProvider();
		while (csvTestDataModelIterator.hasNext()) {
			m1=csvTestDataModelIterator.next();
			i++;
		}
		
		return i;
	}

	public void WriteFile(String[] orderDetailArray,CSVWriter writer) {

		writer.writeNext(orderDetailArray);

	}

	public void closeWriteFile(CSVWriter writer) throws IOException {
		writer.close();
	}

	public static void main(String[] args) throws IllegalStateException, InstantiationException, IllegalAccessException,
			CsvRequiredFieldEmptyException, IOException {
		CsvReaderCode coder = new CsvReaderCode();
		System.out.println("No of rows = "+coder.noRowInTestData());
	}

	private static void While(boolean hasNext) {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<LoginModel> LoginFileReader() {
		ConfigReader configReader=new ConfigReader();
		String testDataPath=configReader.configReader("LoginData");
		CSVReader reader = null;
		System.out.println("Login Test Data ======> "+testDataPath);
		try {
			reader = new CSVReader(new FileReader(testDataPath+".txt"), '\t');
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CsvToBean<LoginModel> csvToBean = new CsvToBeanBuilder(reader).withType(LoginModel.class).build();
		ArrayList<LoginModel> arrayListObject=new ArrayList<LoginModel>();
		Iterator<LoginModel> csvTestDataModelIterator = csvToBean.iterator();
		
		  while(csvTestDataModelIterator.hasNext()) { LoginModel some=
		  csvTestDataModelIterator.next(); arrayListObject.add(some);
		 // System.out.println(some.toString());
		  }
		 
		
		
		
		return arrayListObject;
	
	}

}
