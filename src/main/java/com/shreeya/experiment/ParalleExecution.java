package com.shreeya.experiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.shreeya.model.LoginModel;
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.util.CsvReaderCode;

public class ParalleExecution {
	WebDriver driver;
	LoginPage login;

	@Parameters("Test")
	@BeforeTest
	public void executionBefore() {
		System.out.println("Execution started....");
		//login = new LoginPage();

	}

	@DataProvider(name = "getDataMethod")
	public Iterator<LoginModel> getDataMethod() throws IOException {
		CsvReaderCode csvReader = new CsvReaderCode(); 
		ArrayList<LoginModel> loginData =csvReader.LoginFileReader();
		Iterator<LoginModel>loginIteratior = loginData.iterator();
		return loginIteratior;
	}

	/*
	 * @Test(dataProvider = "getDataMethod") public void firstExecution(LoginModel
	 * loginModelObj) { System.out.println("Execution..");
	 * System.out.println(loginModelObj.toString());
	 * 
	 * 
	 * 
	 * try { driver=login.loginExecution(loginModelObj); } catch
	 * (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
	 * System.out.println(e); }
	 * 
	 * 
	 * 
	 * }
	 */
	@Parameters({"Reference"})
	@Test
	public void firstExecution1(String reference) {
		System.out.println("Execution..");
		Iterator<LoginModel> loginIteratior =  MyTestLauncher.loginData.iterator();
		while(loginIteratior.hasNext()) {
			
			LoginModel loginModelObj= loginIteratior.next();
			if(reference.equals(loginModelObj.getReferNo())) {
				
			}
			
		}
		

	}

	/*
	 * @DataProvider(name="getDataMethod",parallel=true) public Iterator<LoginModel>
	 * getDataMethod() throws IOException { CsvReaderCode csvReader=new
	 * CsvReaderCode(); Iterator<LoginModel> loginData=csvReader.LoginFileReader();
	 * return loginData; }
	 * 
	 * 
	 * @Test(dataProvider="getDataMethod") public void firstExecution(
	 * Iterator<LoginModel> loginModelObj ) { System.out.println("Execution..");
	 * System.out.println();
	 * 
	 * 
	 * try { driver=login.loginExecution(loginModelObj); } catch
	 * (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
	 * System.out.println(e); }
	 * 
	 * 
	 * }
	 */

	@AfterMethod
	public void endExecution() {
		System.out.println("Execution end");
	}

}
