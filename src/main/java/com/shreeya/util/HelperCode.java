package com.shreeya.util;

public class HelperCode {

	public  String nestIdProvider(String strForNestId) {
		String arr[]=strForNestId.split(">");
		String [] nestIdArray=arr[1].split("<");
		/*for(String a:nestIdArray) {
			System.out.println(a);
			System.out.println("====================================");
		}*/
		return nestIdArray[0];
	}
}
