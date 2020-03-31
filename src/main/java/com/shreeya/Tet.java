package com.shreeya;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;




public class Tet {
	
	static Logger log = Logger.getLogger(Tet.class.getName());
	public Tet() {
		PropertyConfigurator.configure("log4j.properties");
	}
	@BeforeMethod
	public void beforeExecution() {
		
		/* System.out.println("Before Execution......"); */
		log.info("Before execution in method...............");
	}

	@Test
	public void function1() {
		/* System.out.println("Testing is done 1"); */
		log.info("function1 execution in method...............");
	}
	
	@Test
	public void function2() {
		/* System.out.println("Testing is done 2"); */
		log.info("function2 execution in method...............");
	}
	
	@Test
	public void function3() {
		/* System.out.println("Testing is done 3"); */
		log.info("function3 execution in method...............");
	}
	
	@AfterMethod
	public void afterExecution() {
		/* System.out.println("After Execution......"); */
		log.info("After execution in method...............");
	}
}
