package com.shreeya;

public class Tet {
	
	static String abc="Order Number: <span data-ng-bind=\"orderlog.ordID\" class=\"ng-binding\">200319000000082</span>";
	
	public static String nestIdProvider(String strForNestId) {
		String arr[]=strForNestId.split(">");
		String [] nestIdArray=arr[1].split("<");
		for(String a:nestIdArray) {
			System.out.println(a);
			System.out.println("====================================");
		}
		return nestIdArray[0];
	}
	
	public static void main(String[] args) {
		System.out.println("Executin is started...........");
		String nest=nestIdProvider(abc);
		System.out.println("ans  ========> "+nest);
	}
}
