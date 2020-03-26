package com.shreeya.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	 private Properties properties;
	 private final String propertyFilePath= "E:\\EdelweissProject\\DigitalWebPlatformAutomation\\src\\main\\java\\com\\shreeya\\cofing.properties";
	
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
		return properties.getProperty(configName);
		
	}

	public static void main(String[] args) {
	ConfigReader reader=new ConfigReader();
	System.out.println(reader.configReader("amoFlag"));

	}

}
