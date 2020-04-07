package com.shreeya.experiment.database;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CovertImageToByte {

	
	public static byte [] ImageToByte(File file) throws FileNotFoundException{

		
		FileInputStream fis=new FileInputStream(file);
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		byte [] buf=new byte[1024];
		try {
			for(int readNum;(readNum = fis.read(buf)) != -1;){
			bos.write(buf, 0, readNum);     

               System.out.println("read " + readNum + " bytes,");
			}
           }catch (IOException ex) {

       }

       byte[] bytes = bos.toByteArray();

     

    return bytes;
		}
	
	public static  void byteToImage(byte[] byteArray,File file) throws IOException {
		FileOutputStream outputStream=new FileOutputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
		BufferedImage image = ImageIO.read(bais);
	}
	
}
