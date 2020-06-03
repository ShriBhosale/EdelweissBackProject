package com.shreeya.fundtransferpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.shreeya.util.SeleniumCoder;

public class FundTransferProfile extends SeleniumCoder{

	WebDriver driver;
	WebElement profileImage;
	WebElement seeProfileLink;

	public FundTransferProfile() {}

	public FundTransferProfile(WebDriver driver) {
		super(driver);
		
	}
	
	public void redirectToProfile() {
		profileImage=fluentWaitCodeXpath("//a[@id='caUser']", "Profile image");
		clickElement(profileImage, "Profile image");
		seeProfileLink=fluentWaitCodeXpath("//a[text()=' See Profile']", "See profile link");
		clickElement(seeProfileLink,"See profile link");
	}
	
	public void profileExecution() {
		
	}
	
}
