package com.shreeya.experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JavaExample {
	
	public static void main(String[] args) {
	
		ArrayList<String> friendsList=new ArrayList<String>();
		friendsList.add("Shreeya");
		friendsList.add("Privina");
		friendsList.add("Apurva");
		friendsList.add("Rahul");
		friendsList.add("priti");
		
		

	 int count=5;
     String temp;
     
     
     
     String str[] = new String[count];
    
     
     //User is entering the strings and they are stored in an array
     System.out.println("Enter the Strings one by one:");
     for(int i = 0; i < count; i++)
     {
         str[i] = friendsList.get(i);
     }
     
     
     //Sorting the strings
     for (int i = 0; i < count; i++) 
     {
         for (int j = i + 1; j < count; j++) { 
             if (str[i].compareTo(str[j])>0) 
             {
                 temp = str[i];
                 str[i] = str[j];
                 str[j] = temp;
             }
         }
     }
     
     //Displaying the strings after sorting them based on alphabetical order
     //System.out.print("Strings in Sorted Order:");
     for (int i = 0; i <= count - 1; i++) 
     {
         System.out.println(str[i]);
     }
 }
}
