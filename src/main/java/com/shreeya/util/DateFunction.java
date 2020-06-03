package com.shreeya.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFunction {
	
	public String dataProvider() {
		String str1 = "dd-MMM-yyyy HH:mm:ss";
	      Date d = Calendar.getInstance().getTime();
	      SimpleDateFormat sdf = new SimpleDateFormat(str1, Locale.FRENCH);
	   //   System.out.println(sdf.format(d));
	      sdf = new SimpleDateFormat(str1, Locale.ENGLISH);
	      String [] dataTimeArray=sdf.format(d).split(" ");
	      String dayString=dataTimeArray[0];
	      String [] dataPartArray=dayString.split("-");
	      Integer dayInt=Integer.valueOf(dataPartArray[0]);
	     
	      if(dayInt<10) {
	    	  char [] dayCharArray=dataPartArray[0].toCharArray();
	    	  String day=Character.toString(dayCharArray[1]);
	    	  dayString=dayString.replace(dataPartArray[0], day);
	      }
	      System.out.println("Time : "+dataTimeArray[1]);
	      System.out.println(dayString.replace("-", " "));
	      return dayString.replace("-", " ");
	}

	public static void main(String[] args) {
		DateFunction f=new DateFunction();
		f.dataProvider();
	}
}
