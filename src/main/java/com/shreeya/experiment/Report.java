package com.shreeya.experiment;

public class Report {
	
	public static void main(String[] args) {
		try {
		String arr[]= {"abc"};
		System.out.println(arr[1]);
		}catch(ArrayIndexOutOfBoundsException e) {
			String exceptionName=e.toString();
			System.out.println(exceptionName);
			/*
			 * for(StackTraceElement errorMsg:e.getStackTrace()) { String
			 * errorM=errorMsg.toString(); System.out.println(errorM); }
			 */
			System.out.println(e.getStackTrace()[0]);
			//e.printStackTrace();
		}
	}
}
