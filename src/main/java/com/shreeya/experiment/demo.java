package com.shreeya.experiment;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class demo {
	
	@BeforeMethod
  public void abc() {
	  Reporter.log("Before method");
  }
	@Test
	public void execute() {
		Reporter.log("Test execution");
	}
	
	@AfterMethod
	public void abc1() {
		Reporter.log("After method");
	}
	
	@BeforeMethod
	  public void abc2() {
		  Reporter.log("Before method  @");
	  }
		@Test
		public void execute1() {
			Reporter.log("Test execution @");
		}
		
		@AfterMethod
		public void abc3() {
			Reporter.log("After method @");
		}
	

}
