package com.shreeya.experiment.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.internal.Utils;

public class ConvertImage {

	 public static void main(String[] args) throws FileNotFoundException, IOException {
		 
		       
		 
		      ConnectionBdImage bd = new ConnectionBdImage();
		 
		       
		 
		      String fileSource = "D:\\Adapterisdown.gif";
		 
		      String fileDestination = "C:\\Adapterisdown.gif";
		 
		       
		 
		      try {
		 
		        
		    	  File file=new File(fileSource);
		       byte [] byteImage = CovertImageToByte.ImageToByte(file);
		 
		        
		 
		       bd.addImage(byteImage, 1);
		 
		       System.out.println(org.postgresql.util.Base64.encodeBytes(bd.getImage(1)));
		 
		      CovertImageToByte.byteToImage(bd.getImage(1), new File(fileDestination));
		 
		   } catch (Exception e) {
		 
		    e.printStackTrace();
		 
		   }     
		 
		     }
		 
  
		 
		 
	
}
