package com.shreeya.util;
import com.opencsv.CSVReader;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.shreeya.model.TestDataModel;

import java.io.FileReader;
import java.io.IOException;

import java.util.Iterator;

public class CsvReaderCode {
	
	String responseString;
	static Iterator<TestDataModel> csvTestDataModelIterator;
	static TestDataModel model;

	public Iterator<TestDataModel> responseGenerator() throws IllegalStateException, InstantiationException,
			IllegalAccessException, CsvRequiredFieldEmptyException, IOException {

		CSVReader reader = new CSVReader(new FileReader("E:\\EdelweissProject\\TestData\\LoginData.txt"), '\t');

		CsvToBean<TestDataModel> csvToBean = new CsvToBeanBuilder(reader).withType(TestDataModel.class).build();

		Iterator<TestDataModel> csvTestDataModelIterator = csvToBean.iterator();

		return csvTestDataModelIterator;
	}
	
	public static void main(String[] args) throws IllegalStateException, InstantiationException, IllegalAccessException, CsvRequiredFieldEmptyException, IOException {
		CsvReaderCode coder=new CsvReaderCode();
		csvTestDataModelIterator=coder.responseGenerator();
		while (csvTestDataModelIterator.hasNext()) {
			  model=csvTestDataModelIterator.next();
			  System.out.println(model.getAction());
			}
	}

}
