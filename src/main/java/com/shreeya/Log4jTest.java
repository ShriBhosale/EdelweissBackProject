package com.shreeya;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jTest {

	static Logger log = Logger.getLogger(Log4jTest.class.getClass());
	
	public static void main(String[] args) {
		System.out.println("Execution started............log4j.properties");
		PropertyConfigurator.configure("log4j.properties");
		log.info("This statement is info");
		log.debug("This statement is debug");
		log.fatal("This statement is fatal");
		log.warn("This statement is warn");
		log.error("This statement is error");
		log.info("This statement is info 2");
	}
}
