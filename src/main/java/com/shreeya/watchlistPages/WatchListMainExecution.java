package com.shreeya.watchlistPages;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.shreeya.MyTestLauncher;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.SeleniumCoder;

public class WatchListMainExecution extends SeleniumCoder{

	WebDriver driver;
	WatchListExecution execution;
	WatchListExtraScenario extraScenario;
	WatchListSorting sorting;
	WatchListTestcase testcase;
	
	public WatchListMainExecution(WebDriver driver) {
		super(driver);
		this.driver=driver;
		execution=new WatchListExecution(driver);
		extraScenario=new WatchListExtraScenario(driver);
		sorting=new WatchListSorting(driver);
		testcase=new WatchListTestcase(driver);
	}
	
	public void watchListExecute(String segment) {
		Reporter.log("<b><font color='Yellow'>=========@@@@ WatchListExecute @@@@========</font></b>", true);
		ExtendReporter reporter=new ExtendReporter(MyTestLauncher.reportFolderPath[1], "WatchList", 0);
		
		/*
		 * reporter=execution.watchListExecute(reporter);
		 * reporter=extraScenario.watchListExtraScenarioExecute(segment,reporter);
		 * reporter=sorting.sortingScenarioExecute(segment, reporter);
		 */
		reporter=testcase.watchListTestcaseExecute(segment, reporter);
		 reporter.logFlush();
	}
}
