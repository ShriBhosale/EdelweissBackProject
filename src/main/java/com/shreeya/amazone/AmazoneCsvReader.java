package com.shreeya.amazone;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.shreeya.model.TestDataModel;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.HelperCode;

public class AmazoneCsvReader {

	private String responseString;
	private static Iterator<AmazoneModel> csvTestDataModelIterator;
	private static AmazoneModel model;
	private CSVWriter writer;
	private HelperCode helperObject;
	
	
	public 	ArrayList<AmazoneModel> LoginFileReader() {
		ConfigReader configReader=new ConfigReader();
		String testDataPath="E:\\EdelweissProject\\TestData\\LoginData";
		CSVReader reader = null;
		System.out.println("Login Test Data ======> "+testDataPath);
		try {
			reader = new CSVReader(new FileReader(testDataPath+".txt"), '\t');
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CsvToBean<AmazoneModel> csvToBean = new CsvToBeanBuilder(reader).withType(AmazoneModel.class).build();
		ArrayList<AmazoneModel> arrayListObject=new ArrayList<AmazoneModel>();
		Iterator<AmazoneModel> csvTestDataModelIterator = csvToBean.iterator();
		while(csvTestDataModelIterator.hasNext()) {
			AmazoneModel some= csvTestDataModelIterator.next();
			arrayListObject.add(some);
			System.out.println("CsvReader ===>  "+some.toString());
		}
		
		return arrayListObject;
	}

}
