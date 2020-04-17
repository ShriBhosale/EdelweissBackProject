package com.shreeya.amazone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.shreeya.page.LoginPage;

public class TestFactory1 {
	
	String name;
	WebDriver driver;

	public TestFactory1(String name) {
		Reporter.log("TestFactory constructor", true);
		this.name=name;
	}
	
	@Test
	public void playerName() {
		Reporter.log("Shreeya PlayerName", true);
		System.out.println("Player name is "+name);
		LoginPage login=new LoginPage();
		//driver=login.browserLaunch(name);
		//driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']")).sendKeys(name);
	}
}
