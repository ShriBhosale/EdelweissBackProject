package com.shreeya.experiment.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {

	Connection mySqlConnection;
	String url="jdbc:postgresql://localhost:5432/postgres";
	String pwd="shreeya";
	String user = "postgres";

	
	public Connection getConnection() {
		  try {
		
		   Class.forName("org.postgresql.Driver");
		  } catch (Exception e) {
		
		   System.out.println("Driver Problem");
		
		   e.printStackTrace();
		
		  }
		
		  try {
		
		   if (mySqlConnection == null) {
		
		    mySqlConnection = DriverManager.getConnection(url, user, pwd);
		
		    
		   }
		
		  } catch (Exception e) {
		
		   e.printStackTrace();
		
		  }

		  return mySqlConnection;
		
		 }

}
