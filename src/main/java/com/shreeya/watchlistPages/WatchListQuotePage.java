package com.shreeya.watchlistPages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.yaml.snakeyaml.representer.Represent;

import com.shreeya.model.WatchListModel;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.Scroll;
import com.shreeya.util.SeleniumCoder;

public class WatchListQuotePage extends SeleniumCoder {

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
	List<String> scriptDetailList;
	

	private String[] verifyScriptArray;

	private String exchangeStr;

	private WebElement tradeButton;

	private WebElement LastPriceLable;

	private String persentage;

	private String changeCount;

	private String ltpQuote;

	private String changeNo;

	private String persentageNo;

	private WebElement ltpQuoteLabel;

	private String ltpPersantage;
	
	public WatchListQuotePage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		scroll=new Scroll(driver);
		help=new Help();
		watchListCommon=new WatchListCommon(driver);
		scriptDetailList=new ArrayList<String>();
	}
	
	public List<String> equityCodePageVerification(List<String> codePageDetailList,WatchListModel model) {
		Reporter.log("=====> equityCodePageVerification <=====", true);
		verifyScriptArray=help.commaSeparater(model.getVerifyScript());
		String [] fullScriptNameArray=help.commaSeparater(model.getFullScriptName());
		staticWait(500);
		sharePriceLabel=fluentWaitCodeXpath("//h1[@class='comp_name ib ng-binding']", "Share Price label");
		sharePriceCompanyName=fetchTextFromElement(sharePriceLabel);
		String [] scriptS=help.separater(sharePriceCompanyName, "&nbsp;Share");
		codePageDetailList.add("Script Name : "+help.testStrContainInAppliStr(scriptS[0],fullScriptNameArray[fullScriptNameArray.length-1]));
		
		
		tradingSymbolCodeLabel=fluentWaitCodeXpath("//label[@class='sym  ng-binding']", "TradingSymbol");
		codePageDetailList.add("Trading symbol : "+help.commpareTwoString(fetchTextFromElement(tradingSymbolCodeLabel), verifyScriptArray[verifyScriptArray.length-1]));
		
		if(model.getExchange().contains("CDS")||model.getExchange().contains("NFO")||model.getExchange().contains("FNO")) 
			exchangeLabelCode=fluentWaitCodeXpath("//*[@id='eqQuotes']/section/div[1]/div[1]/div/div[2]/div[1]/div[1]/div/button/span", "exchange label");
		else
			exchangeLabelCode=fluentWaitCodeXpath("//div[@class='dropdown exchangeDD qtDD']//button//span", "exchange label");
		
		if(model.getExchange().equalsIgnoreCase("NFO")||model.getExchange().equalsIgnoreCase("FNO")) {
			exchangeStr="F&O";
		}else {
			exchangeStr=model.getExchange();
		}
		
		if(fetchTextFromElement(exchangeLabelCode).contains("F&"))
		{
			String [] array=fetchTextFromElement(exchangeLabelCode).split("amp;");
			codePageDetailList.add("Exchange :  "+help.commpareTwoString(array[0]+array[1], exchangeStr));
		}else {
		codePageDetailList.add("Exchange :  "+help.commpareTwoString(fetchTextFromElement(exchangeLabelCode), exchangeStr));
		}
		
		/*
		 * ltpLabel=fluentWaitCodeXpath("//div[@class='realVals']//label[@class='ltp']",
		 * "LTP no"); String ltp=fetchTextFromElement(ltpLabel);
		 * codePageDetailList.add("LTP : "+help.removeHtmlCode(ltp));
		 */
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
			Reporter.log("====> codePageExecution <====", true);
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
			int scriptNo=0;
			String scriptForClick=verifyScriptArray[verifyScriptArray.length - 1];
			for (int i = 2; i < noScript + 2; i++) {
				String tradingSymbolString = "//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+i+"]/div[1]/div[1]/a";
				tradingSymbolLabel = fluentWaitCodeXpath(tradingSymbolString, "Script Name");
				tradingSymbolstr = fetchTextFromElement(tradingSymbolLabel);
				Reporter.log("click script : "+scriptForClick, true);
				if (tradingSymbolstr.contains(scriptForClick)) {
					
					scriptNo=i;
					
					break;
				}
			}
			
			scriptDetailList=fetchLTPAndChangePersentage(scriptNo);
			clickElement(tradingSymbolLabel, "Trading symbol link");
			staticWait(4000);
			codePageDetailList.add("WatchList Name : "+model.getWatchListName());
			waitTillNewTabUpload("//a[text()='helpdesk@edelweiss.in']", scriptForClick+" Quote ", 100,300);
			if (model.getExchange().equalsIgnoreCase("NCDEX") || model.getExchange().equalsIgnoreCase("MCX")) {
				codePageDetailList=commodityCodePageVerfication(codePageDetailList, model);
			}else {
				checkLTPAndChangePersentage(scriptDetailList, codePageDetailList, model.getExchange());
				codePageDetailList=equityCodePageVerification(codePageDetailList, model);
			}
			closeTab(2);
			switchTab(1);
		
			return codePageDetailList;
		}
		
		public List<String> fetchLTPAndChangePersentage(int scriptNo) {
			Reporter.log("====> fetchLTPAndChangePersentage <===", true);
			staticWait(1000);
			List<String> scriptDetailList=new ArrayList<String>();
			LastPriceLable=fluentWaitCodeXpath("//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+scriptNo+"]/div[1]/div[2]/span", "Last price");
			scriptDetailList.add(fetchTextFromElement(LastPriceLable));
			ltpPersantage=help.digitConvert(fetchTextFromElement("//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+scriptNo+"]/div[1]/div[3]/span[2]", "Persentage"),")");
			scriptDetailList.add(ltpPersantage);
			scriptDetailList.add(fetchTextFromElement("//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+scriptNo+"]/div[1]/div[3]/span[1]", "Change count"));
			return scriptDetailList;
		}
		
		public void checkLTPAndChangePersentage(List<String> scriptDetailList,List<String> codePageDetailList,String exchange) {
			Reporter.log("====> checkLTPAndChangePersentage <====", true);
			staticWait(1000);
			Reporter.log("Exchange : "+exchange, true);
			exchange="BSE";
			List<String> quoteDetailList=new ArrayList<String>();
			if(exchange.equalsIgnoreCase("NSE") || exchange.equalsIgnoreCase("BSE")) {
			
			ltpQuote=fetchTextFromElement("//*[@id='eqQuotes']/section/div[1]/div[2]/div[1]/div[1]/label[1]",500,"LTp Quote");
			
			quoteDetailList.add(help.removeHtmlReporter(ltpQuote).replace(" ", ""));
			quoteDetailList.add(fetchTextFromElement("//*[@id='eqQuotes']/section/div[1]/div[2]/div[1]/div[1]/label[3]",500,"Persentage").replace("%",""));
			quoteDetailList.add(fetchTextFromElement("//*[@id='eqQuotes']/section/div[1]/div[2]/div[1]/div[1]/label[2]",500,"Change no"));
			
			}
			Reporter.log("quoteDetailList size : "+quoteDetailList.size()+" scriptDetailList size : "+scriptDetailList.size(), true);
			Reporter.log("codePageDetailList  size : "+codePageDetailList.size(),true);
			codePageDetailList.add("LTP : "+help.commpareTwoString(quoteDetailList.get(0), scriptDetailList.get(0)));
			Reporter.log(quoteDetailList.get(1), true);
			Reporter.log(quoteDetailList.get(2), true);
			
			/*
			 * String [] changeArray=quoteDetailList.get(1).split("-"); String []
			 * persentageArray=quoteDetailList.get(2).split("-");
			 */
			
			
				if(help.commpareTwoString(quoteDetailList.get(1),scriptDetailList.get(1)).contains("PASS")) {
					
					codePageDetailList.add("LTP change Persentage : "+quoteDetailList.get(1)+"-PASS");
				}else {
					codePageDetailList.add("LTP change Persentage : "+quoteDetailList.get(1)+"-FAIL");
				}
				if(help.commpareTwoString(quoteDetailList.get(2),scriptDetailList.get(2)).contains("PASS")) {
						
						codePageDetailList.add("LTP Change  :"+quoteDetailList.get(2)+"-PASS");
					
				}else {
					codePageDetailList.add("LTP Change :"+quoteDetailList.get(2)+"-FAIL");
				}
				
			
				
				
			
		}
	
}
