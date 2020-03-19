package com.shreeya.util;

import java.sql.Timestamp;

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
	
	public  String timeStampGenerator() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp.getTime());
		return Long.toString(timestamp.getTime());
	}
	public static void main(String[] args) {
		
	}
	
}
