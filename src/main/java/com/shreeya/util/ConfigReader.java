package com.shreeya.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	 private Properties properties;
	 private Properties propertiesFM;
	 private Properties propertiesWL;
	 private Properties propertiesAN;
	 private final String propertyFilePath= System.getProperty("user.dir")+"\\src\\main\\java\\com\\shreeya\\cofing.properties";
	 private final String fundTransferpropertyFilePath= System.getProperty("user.dir")+"\\src\\main\\java\\com\\shreeya\\fundtransferpages\\fundtransfercofing.properties";
	 private final String watchListpropertyFilePath= System.getProperty("user.dir")+"\\src\\main\\java\\com\\shreeya\\watchlistPages\\WatchListConfig.properties";
	 private final String alertpropertyFilePath= System.getProperty("user.dir")+"\\src\\main\\java\\com\\shreeya\\alertandnotification\\AlertConfig.properties";
	public  ConfigReader(){
		 BufferedReader reader;
		 try {
		 reader = new BufferedReader(new FileReader(propertyFilePath));
		 properties = new Properties();
		 try {
		 properties.load(reader);
		 reader.close();
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 } catch (FileNotFoundException e) {
		 e.printStackTrace();
		 throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		 } 
		 }
	
	public String configReader(String configName) {
		return properties.getProperty(configName).trim();
		
	}
	
	public void fundTransferConfig() {
		BufferedReader reader;
		 try {
		 reader = new BufferedReader(new FileReader(fundTransferpropertyFilePath));
		 propertiesFM = new Properties();
		 try {
		 propertiesFM.load(reader);
		 reader.close();
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 } catch (FileNotFoundException e) {
		 e.printStackTrace();
		 throw new RuntimeException("Configuration.properties not found at " + fundTransferpropertyFilePath);
		 } 
	}
	
	public void alertConfig() {
		BufferedReader reader;
		 try {
		 reader = new BufferedReader(new FileReader(alertpropertyFilePath));
		 propertiesAN = new Properties();
		 try {
		 propertiesAN.load(reader);
		 reader.close();
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 } catch (FileNotFoundException e) {
		 e.printStackTrace();
		 throw new RuntimeException("Configuration.properties not found at " + alertpropertyFilePath);
		 } 
	}
	public void watchListConfig() {
		BufferedReader reader;
		 try {
		 reader = new BufferedReader(new FileReader(watchListpropertyFilePath));
		 propertiesWL = new Properties();
		 try {
		 propertiesWL.load(reader);
		 reader.close();
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 } catch (FileNotFoundException e) {
		 e.printStackTrace();
		 throw new RuntimeException("Configuration.properties not found at " + watchListpropertyFilePath);
		 } 
	}
	

	public String configReaderFM(String configName) {
		return propertiesFM.getProperty(configName).trim();
		
	}
	
	public String configReaderWL(String configName) {
		return propertiesWL.getProperty(configName).trim();
		
	}
	
	public String configReaderAN(String configName) {
		return propertiesAN.getProperty(configName).trim();
		
	}
	

	public static void main(String[] args) {
	ConfigReader reader=new ConfigReader();
	reader.fundTransferConfig();
	
	System.out.println(reader.configReaderFM("AtomicBank"));

	}

}
