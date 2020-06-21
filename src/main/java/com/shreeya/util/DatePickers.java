package com.shreeya.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

public class DatePickers extends SeleniumCoder{

	WebDriver driver;
	WebElement monthButton;
	Help help;
	private String[] dateArray;
	private String monthStr;
	private String monthXpath;
	private String day;
	private String dayXpathStr;
	private WebElement dayLable;
	private WebElement dayLabel;
	private WebElement yearButton;
	private WebElement yearOptionButton;
	
	public DatePickers(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help();
	}
	
	
	public  void dataPickersExecuter(String date) {
		//String date="6-4-2020";
		dateArray=help.separater(date, "-");
		Reporter.log("==== dataPickersExecuter ====", true);
		monthButton=fluentWaitCodeXpath("//table//thead//tr//th[2]//button", "Month button");
		monthStr=fetchTextFromElement(monthButton);
		//Year
		if(!monthStr.contains(dateArray[2])) {
			clickElement(monthButton, "month Button");
			yearButton=fluentWaitCodeXpath("//table//thead//tr//th[2]//button", "Year button");
			clickElement(yearButton, "Year button");
			yearOptionButton=fluentWaitCodeXpath("//span[text()='"+dateArray[2]+"']",dateArray[2]+"year");
			clickElement(yearOptionButton, "Year option button");
			monthXpath="//table//tbody//tr//button//span[text()='"+dateArray[1]+"']";
			monthButton=fluentWaitCodeXpath(monthXpath, dateArray[1]+" month");
			clickElement(monthButton, dateArray[1]+" month");
			
			monthButton=fluentWaitCodeXpath("//table//thead//tr//th[2]//button", "Month button");
			monthStr=fetchTextFromElement(monthButton);
		}
		//month
		if(!monthStr.contains(dateArray[1])) {
			clickElement(monthButton, "month Button");
			
				monthXpath="//table//tbody//tr//button//span[text()='"+dateArray[1]+"']";
				monthButton=fluentWaitCodeXpath(monthXpath, dateArray[1]+" month");
				clickElement(monthButton, dateArray[1]+" month");
				
		}
		//day
		for(int i=1;i<7;i++) {
			day=dayNoProcess(dateArray[0]);
			dayXpathStr="//table//tbody//tr["+i+"]//td//button//span[text()='"+day+"']";
			dayLabel=fluentWaitCodeXpath(dayXpathStr, "Day "+day);
			if(dayLabel!=null) {
				clickElement(dayLabel, "Day "+day);
				break;
			}
		}
		
	}
	
	public String dayNoProcess(String day) {
		int noDay=Integer.valueOf(day);
		if(noDay<10) {
			if(!day.contains("0")) {
				day="0"+day;
			}
		}
		return day;
	}
}
