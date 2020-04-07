package com.shreeya.experiment.database;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionBdImage {

	public byte[] getImage(int id) {

		byte[] byteImg = null;

		ConnectDB connect = new ConnectDB();

		Connection connection = null;

		try {

			connection = connect.getConnection();

			// String byteImg="";

			PreparedStatement ps = connection

					.prepareStatement("SELECT image FROM image WHERE id = ?");

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				byteImg = rs.getBytes(1);

				// use the data in some way here

			}

			rs.close();

			ps.close();

			return byteImg;

		} catch (Exception e) {

			// TODO: handle exception

			return null;

		}

	}

	public void addImage(byte[] img, int id) {
		
		  ConnectDB connect = new ConnectDB();
		
		  Connection connection = null;
		
		  try {
		
		   connection = connect.getConnection();
		 //Statement statement = connection.createStatement();
		
		 
		
		   PreparedStatement ps = connection
		
		     .prepareStatement("INSERT INTO image VALUES (?, ?, ?)");
		
		   ps.setInt(1, id);
	
		   ps.setBytes(3, img);
		
		   ps.setString(2, "test");
		
		 
		
		   ps.executeUpdate();
		
		   ps.close();
		
		  } catch (Exception e) {
		
		   e.printStackTrace();
	
		  } finally {
	
		   try {
		
		    connection.close();
		
		 
		
		   } catch (Exception e) {
	
		    // TODO: handle exception
		
		   }
		
		  }
	}

}
