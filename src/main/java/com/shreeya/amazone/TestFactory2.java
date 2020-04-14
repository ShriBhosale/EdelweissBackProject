package com.shreeya.amazone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.shreeya.page.LoginPage;

public class TestFactory2 {

	String name;
	private WebDriver driver;

	public TestFactory2(String name) {
		System.out.println("I am TestFactory2 constructor........");
		this.name=name;
	}
	
	@Test
	public void playerName() {
		System.out.println("Player name is "+name);
		LoginPage login=new LoginPage();
		driver=login.browserLaunch(name);
		driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']")).sendKeys(name);
	}
}
