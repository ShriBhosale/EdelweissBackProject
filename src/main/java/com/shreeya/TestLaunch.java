package com.shreeya;

import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.shreeya.model.TestDataModel;
import com.shreeya.page.CxlOrderPage;
import com.shreeya.page.LoginPage;
import com.shreeya.page.ModOrderPage;
import com.shreeya.page.NewOrderPage;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.SeleniumCoder;

public class TestLaunch {

	static WebDriver driver;
	static Iterator<TestDataModel> csvTestDataModelIterator;
	static TestDataModel model;

	public static void main(String[] args) throws InterruptedException, IllegalStateException, InstantiationException,
			IllegalAccessException, CsvRequiredFieldEmptyException, IOException {
		CsvReaderCode coder = new CsvReaderCode();
		csvTestDataModelIterator = coder.responseGenerator();
		LoginPage login = new LoginPage();
		driver = login.loginExecution();
		while (csvTestDataModelIterator.hasNext()) {
			model = csvTestDataModelIterator.next();
			if (model.getAction().equalsIgnoreCase("New")) {
				System.out.println("Action :: "+model.getAction());
				NewOrderPage newOrder = new NewOrderPage();
				newOrder.newOrderExecution(model,driver);
			} else if (model.getAction().equalsIgnoreCase("Mod")) {
				System.out.println("Action :: "+model.getAction());
				ModOrderPage modOrder = new ModOrderPage();
				modOrder.modExecution(model,driver);
			} else if (model.getAction().equalsIgnoreCase("Cxl")) {
				System.out.println("Action :: "+model.getAction());
				CxlOrderPage cxlOrder = new CxlOrderPage();
				cxlOrder.cxlExecution(driver);
			}
		}
		login.logout(driver);
	}

}
