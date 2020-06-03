package com.shreeya.fundtransferpages;

import java.util.HashMap;
import java.util.Map;

import org.testng.Reporter;

public class FundTransferCommon {

	public String [] amountEnter(String amount) {
		String [] ansArray= {"QickSelectAmount","EnterAmount"};
		
		int qickAmount=0,enterAmount=0;
		int amountInt=Integer.valueOf(amount);
		int [] QuickSelectAmountArray= {5000,10000,20000};
		for(int i=QuickSelectAmountArray.length-1;i>-1;i--) {
			if(QuickSelectAmountArray[i]==amountInt) {
				ansArray[0]=String.valueOf(QuickSelectAmountArray[i]);
				break;
			}else if(QuickSelectAmountArray[i]<amountInt){
				
				ansArray[0]=String.valueOf(QuickSelectAmountArray[i]);
				qickAmount=Integer.valueOf(ansArray[0]);
				ansArray[1]=String.valueOf(amountInt-qickAmount);
				enterAmount=Integer.valueOf(ansArray[1]);
				for(int amountI:QuickSelectAmountArray)
				{
					if(enterAmount==amountI)
					{
						ansArray[0]=ansArray[0]+","+enterAmount;
						ansArray[1]="EnterAmount";
						break;
					}
				}
				
				
				break;
			}else if(QuickSelectAmountArray[0]>amountInt){
				ansArray[1]=String.valueOf(amountInt);
			}else {
				continue;
			}
		}
		return ansArray;
	
		
	}
	
	public String bankNameGiver(String bankName) {
		String bank="No bank";
		Map<String,String> bankMap=new HashMap<String,String>();
		bankMap.put("citibank", "Citibank Na");
		bankMap.put("kotak", "Kotak Mahindra Bank");
		bankMap.put("hdfc", "HDFC BANK LTD.");
		bankMap.put("icici", "ICICI BANK LTD");
		bankMap.put("axis", "AXIS BANK");
		bankMap.put("yes", " Yes Bank");
		bankMap.put("andhra","ANDHRA BANK");
		
		for(Map.Entry<String,String> entry:bankMap.entrySet()) {
			if(bankName.toLowerCase().contains(entry.getKey())) {
				bank=entry.getValue();
				break;
			}
		}
		return bank;
	}
	
	public String [] cardNumberArray(String number) {
		System.out.println("Number : "+number);
		String [] numberArray= {"","","",""};
		char [] digitArray=number.toCharArray();
		int count=0,a=0,j=0;
		for(int i=0;i<digitArray.length;i++) {
			
			count++;
			if(count<5) {
				numberArray[a]=numberArray[a]+digitArray[i];
				j=i;
			}else if(count==5) {
				a++;
				count=0;
				i=j;
			}
		}
		
		return numberArray;
	}
	
	public String removeHtmlTextCheckText(String text,String checkText) {
		String msg="";
		String result="FAIL";
		if(text.contains("\n"))
			text=text.replace("\n", "").trim();
		String [] textArray=text.split("<");
		 //msg=textArray[0];
		for(String textStr:textArray) {
			if(textStr.contains(checkText)) {
				result="PASS";
			}
		 textArray=textStr.trim().split(">");
		 Reporter.log(textStr, true);
		 msg=msg+" "+textArray[textArray.length-1];
		}
		Reporter.log("removeHtmlText msg : "+msg+"-"+result, true);
		return msg+"-"+result;
	}
	
	public String verifAccountNo(String accountNo) {
		int xNo=0;
		String [] accountNoArray= {"","",""};
		//String account="12345678901234";
		char [] accountNoCharArray=accountNo.toCharArray();
		int count=accountNoCharArray.length-5;
		for(int i=0;i<accountNoCharArray.length;i++) {
			if(i==0 || i==1) {
				accountNoArray[0]=accountNoArray[0]+accountNoCharArray[i];
			}
			else if(i>count) {
				accountNoArray[2]=accountNoArray[2]+accountNoCharArray[i];
			}else {
				if(xNo>0) 
				accountNoArray[1]=accountNoArray[1]+"X";
				
				xNo++;
			}
		}
		System.out.println("Account no : "+accountNoArray[0]+accountNoArray[1]+accountNoArray[2]);
		return accountNoArray[0]+accountNoArray[1]+accountNoArray[2];
	}
	
	public static void main(String[] args) {
		FundTransferCommon c=new FundTransferCommon();
		c.verifAccountNo("6911174021");
	}
		
	}

	
