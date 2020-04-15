package com.shreeya.experiment;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNgTag {

	@BeforeTest
	public void m1() {
		System.out.println("Before Test methode");
	}
	
	@BeforeMethod
	public void m2() {
		System.out.println("Before method methode");
	}
	
	@Test
	public void m3() {
		System.out.println("Test1 methode");
	}
	
	@Test
	public void m4() {
		System.out.println("Test 2 methode");
	}
	
	@AfterMethod
	public void m5() {
		System.out.println("After method methode");
	}
	
	@AfterTest
	public void m6() {
		System.out.println("After Test methode");
	}
}
