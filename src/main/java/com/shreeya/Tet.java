package com.shreeya;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;




public class Tet {
	
	@BeforeMethod
	public void beforeExecution() {
		System.out.println("Before Execution......");
	}

	@Test
	public void function1() {
		System.out.println("Testing is done 1");
	}
	
	@Test
	public void function2() {
		System.out.println("Testing is done 2");
	}
	
	@Test
	public void function3() {
		System.out.println("Testing is done 3");
	}
	
	@AfterMethod
	public void afterExecution() {
		System.out.println("After Execution......");
	}
}
