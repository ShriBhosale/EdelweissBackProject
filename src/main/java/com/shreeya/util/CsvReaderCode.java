package com.shreeya.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.shreeya.model.TestDataModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		File file = new File("E:\\EdelweissProject\\Reports\\ReportInExcel\\ExcelReport"
				+ helperObject.timeStampGenerator() + ".csv");

		FileWriter outputfile = new FileWriter(file, true);

		return new CSVWriter(outputfile);
	}

	public Iterator<TestDataModel> responseGenerator() {

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader("E:\\EdelweissProject\\TestData\\LoginData.txt"), '\t');
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CsvToBean<TestDataModel> csvToBean = new CsvToBeanBuilder(reader).withType(TestDataModel.class).build();

		Iterator<TestDataModel> csvTestDataModelIterator = csvToBean.iterator();

		return csvTestDataModelIterator;
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

	}

}
