package com.shreeya.watchlistPages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.shreeya.model.WatchListModel;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.Scroll;
import com.shreeya.util.SeleniumCoder;

public class WatchListCodePage extends SeleniumCoder {

	WebDriver driver;
	
	WebElement tradingSymbolLabel;
	WebElement scriptCountLabel;
	WebElement sharePriceLabel;
	WebElement keyRadioLabel;
	WebElement comparewithPeersLabel;
	WebElement ltpLabel;
	WebElement marketcapLabel;
	WebElement smallCaseLabel;
	WebElement keyRatiosLabel;
	WebElement balanceSheetLabel;
	WebElement cashFlowLabel;
	WebElement profitandLossBanksLabel;
	WebElement exchangeLabelCode;
	WebElement tradingSymbolCodeLabel;
	WebElement volumeLabel;
	WebElement corporateActionsLabel;
	WebElement averageTradingPriceLabel;
	WebElement tenderCloseLabel;
	WebElement sharePriceMomentLabel;
	WebElement keyLevelLabel;
	WebElement lptCoLabel;
	WebElement scriptNameCoLabel;
	WebElement exchangeCoLabel;

	Scroll scroll;
	Help help;
	WatchListCommon watchListCommon;
	
	String sharePriceCompanyName;
	String tradingSymbolstr;
	String [] scriptArray;

	private String[] verifyScriptArray;
	
	public WatchListCodePage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		scroll=new Scroll(driver);
		help=new Help();
		watchListCommon=new WatchListCommon(driver);
	}
	
	public List<String> equityCodePageVerification(List<String> codePageDetailList,WatchListModel model) {
		scriptArray=WatchListPage.scriptArray;
		sharePriceLabel=fluentWaitCodeXpath("//h1[@class='comp_name ib ng-binding']", "Share Price label");
		sharePriceCompanyName=fetchTextFromElement(sharePriceLabel);
		codePageDetailList.add(help.testStrContainInAppliStr(sharePriceCompanyName,scriptArray[scriptArray.length-1]));
		
		
		tradingSymbolCodeLabel=fluentWaitCodeXpath("//label[@class='sym  ng-binding']", "TradingSymbol");
		codePageDetailList.add(help.commpareTwoString(fetchTextFromElement(tradingSymbolCodeLabel), model.getVerifyScript()));
		
		if(model.getExchange().contains("CDS")||model.getExchange().contains("NFO")||model.getExchange().contains("FNO")) 
			exchangeLabelCode=fluentWaitCodeXpath("//*[@id='eqQuotes']/section/div[1]/div[1]/div/div[2]/div[1]/div[1]/div/button/span", "exchange label");
		else
			exchangeLabelCode=fluentWaitCodeXpath("//div[@class='dropdown exchangeDD qtDD']//button//span", "exchange label");
		
		codePageDetailList.add(help.commpareTwoString(fetchTextFromElement(exchangeLabelCode), model.getExchange()));
		
		
		ltpLabel=fluentWaitCodeXpath("//div[@class='realVals']//label[@class='ltp']", "LTP no");
		String ltp=fetchTextFromElement(ltpLabel);
		codePageDetailList.add(help.removeHtmlCode(ltp));
		codePageDetailList.add(ScreenshortProvider.captureScreen(driver, "CodePageScreenshort_1"));
		
		if(model.getExchange().contains("CDS")||model.getExchange().contains("NFO")||model.getExchange().contains("FNO")) {
			volumeLabel=fluentWaitCodeXpath("//label[text()='Volume']", "Volume");
			if(volumeLabel!=null)
			scroll.scrollAndPointToElement(volumeLabel);
		}else {
			marketcapLabel=fluentWaitCodeXpath("//label[text()='Market Cap (in crs.)']", "Market Cap");
			if(marketcapLabel!=null)
			scroll.scrollAndPointToElement(marketcapLabel);
		}
		
		
		codePageDetailList.add(ScreenshortProvider.captureScreen(driver, "CodePageScreenshort_2"));
		
		smallCaseLabel=fluentWaitCodeXpath("//h2[text()='Smallcase']", "small case");
		if(smallCaseLabel!=null) {
			scroll.scrollAndPointToElement(smallCaseLabel);
			codePageDetailList.add(ScreenshortProvider.captureScreen(driver, "CodePageScreenshort_3"));
		}
		
		keyRadioLabel=fluentWaitCodeXpath("//h2[text()='Key Ratios']", "Key ratios");
		if(keyRadioLabel!=null) {
			scroll.scrollAndPointToElement(keyRadioLabel);
			codePageDetailList.add(ScreenshortProvider.captureScreen(driver, "CodePageScreenshort_4"));
		}
		
		balanceSheetLabel=fluentWaitCodeXpath("//h2[text()='Balance Sheet']", "balance sheet");
		if(balanceSheetLabel!=null) {
			scroll.scrollAndPointToElement(balanceSheetLabel);
			codePageDetailList.add(ScreenshortProvider.captureScreen(driver, "CodePageScreenshort_5"));
		}
		
		cashFlowLabel=fluentWaitCodeXpath("//h2[text()='Cash Flow']", "cash flow");
		if(cashFlowLabel!=null) {
			scroll.scrollAndPointToElement(cashFlowLabel);
			codePageDetailList.add(ScreenshortProvider.captureScreen(driver, "CodePageScreenshort_6"));
		}
		
		if(model.getExchange().contains("CDS")||model.getExchange().contains("NFO")||model.getExchange().contains("FNO")) {
			corporateActionsLabel=fluentWaitCodeXpath("//label[text()='Corporate Actions']", "Corporate Action");
			if(corporateActionsLabel!=null)
			scroll.scrollAndPointToElement(corporateActionsLabel);
		}else {
		profitandLossBanksLabel=fluentWaitCodeXpath("//label[text()='Profit and Loss (Banks)']", "balance sheet");
		if(profitandLossBanksLabel!=null)
		scroll.scrollAndPointToElement(profitandLossBanksLabel);
		}
		codePageDetailList.add(ScreenshortProvider.captureScreen(driver, "CodePageScreenshort_7"));
		return codePageDetailList;
			}

		public List<String> commodityCodePageVerfication(List<String> codePageDetailList,WatchListModel model) {
			codePageDetailList.add("Script  : "+model.getScriptName());
			staticWait(1000);
			codePageDetailList.add(ScreenshortProvider.captureScreen(driver, "CodePageCoScreenshot_0"));
			String [] fullScriptName=help.commaSeparater(model.getFullScriptName());
			scriptArray=WatchListPage.scriptArray;
			scriptNameCoLabel=fluentWaitCodeXpath("//*[@id=\"commodity-overview\"]/div/h1","Script Name");
			if(scriptNameCoLabel!=null) {
				codePageDetailList.add("Script Name : "+help.commpareTwoString(fetchTextFromElement(scriptNameCoLabel),fullScriptName[fullScriptName.length-1]));
			}

			exchangeCoLabel=fluentWaitCodeXpath("//*[@id='contentCntr']/section/div/div[3]/div/ul/li/a","Exchange");
			if(exchangeCoLabel!=null) {
				codePageDetailList.add("Exchange : "+help.commpareTwoString(fetchTextFromElement(exchangeCoLabel),model.getExchange()));
			}
			
			lptCoLabel=fluentWaitCodeXpath("//span[@class='position large ng-binding']","Script Name");
			if(lptCoLabel!=null) {
				
				codePageDetailList.add("LPT : "+fetchTextFromElement(lptCoLabel));
			}
			
			averageTradingPriceLabel=fluentWaitCodeXpath("//span[text()='Average Trading Price for today (Rs.)']","Averge trading price");
			if(averageTradingPriceLabel!=null) {
				scroll.scrollAndPointToElement(averageTradingPriceLabel);
				codePageDetailList.add(ScreenshortProvider.captureScreen(driver, "CodePageCoScreenshot_1"));
			}
			
			tenderCloseLabel=fluentWaitCodeXpath("//*[@id='contentCntr']/div[2]/div[2]/div[1]/div[2]/div[2]/div/div/table/tbody/tr[2]/td[1]/span","tender close");
			if(tenderCloseLabel!=null) {
				scroll.scrollAndPointToElement(tenderCloseLabel);
				codePageDetailList.add(ScreenshortProvider.captureScreen(driver, "CodePageCoScreenshot_2"));
			}
			
			sharePriceMomentLabel=fluentWaitCodeXpath("//h2[@class='mg-t40 mg-b20 quote-h2 ng-binding']","Share price");
			if(sharePriceMomentLabel!=null) {
				scroll.scrollAndPointToElement(sharePriceMomentLabel);
				codePageDetailList.add(ScreenshortProvider.captureScreen(driver, "CodePageCoScreenshot_3"));
			}
			
			keyLevelLabel=fluentWaitCodeXpath("//*[@id='technical-analysis']/div/div[2]/h4/span","Key level");
			if(keyLevelLabel!=null) {
				scroll.scrollAndPointToElement(keyLevelLabel);
				codePageDetailList.add(ScreenshortProvider.captureScreen(driver, "CodePageCoScreenshot_4"));
			}
			
			return codePageDetailList;
		}
		
		public List<String> codePageExecution(List<String> codePageDetailList,WatchListModel model) {

			
			verifyScriptArray = help.commaSeparater(model.getVerifyScript());
			switchTab(1);
			watchListCommon.pageVerify(model, "codingPage");
			staticWait(2000);
			codePageDetailList.add(ScreenshortProvider.captureScreen(driver, "BeforeClickOnScirptLink"));
			
			scriptCountLabel = fluentWaitCodeXpath("//span[text()='Your watchlist has ']", "Script Count");
			String scriptCount = fetchTextFromElement(scriptCountLabel);
			String[] noOfScript = help.separater(scriptCount, " ");
			noOfScript = noOfScript[4].split(">");
			int noScript = Integer.valueOf(noOfScript[1]);
			int matchScirptNo = 0;
			for (int i = 2; i < noScript + 2; i++) {
				String tradingSymbolString = "//*[@id='contentCntr']/div/div/div[1]/div[4]/div/div/div/div/div[2]/div[" + i
						+ "]/div[1]/div[1]/a";
				tradingSymbolLabel = fluentWaitCodeXpath(tradingSymbolString, "Script Name");
				tradingSymbolstr = fetchTextFromElement(tradingSymbolLabel);
				if (tradingSymbolstr.contains(verifyScriptArray[verifyScriptArray.length - 1])) {
					clickElement(tradingSymbolLabel, "Trading symbol link");
					break;
				}
			}
			switchTab(2);
			codePageDetailList.add("WatchList Name : "+model.getWatchListName());
			
			if (model.getExchange().equalsIgnoreCase("NCDEX") || model.getExchange().equalsIgnoreCase("MCX")) {
				codePageDetailList=commodityCodePageVerfication(codePageDetailList, model);
			}else {
				codePageDetailList=equityCodePageVerification(codePageDetailList, model);
			}
			closeTab(2);
			switchTab(1);
		
			return codePageDetailList;
		}
		
	
}
