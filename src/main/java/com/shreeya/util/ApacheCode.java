package com.shreeya.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ApacheCode {

	XSSFWorkbook workbook;
	FileOutputStream out;
	XSSFSheet sheet;

	public ApacheCode() throws FileNotFoundException {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Orders Details");
		out = new FileOutputStream(new File("E:\\EdelweissProject\\Reports\\ReportInExcel\\example1.xlsx"));
		String[] headerArray = { "Id", "Action", "Status", "Order Action", "Trading Symbol", "Product Type",
				"Order Price", "Order Type", "User id", "Exchange", "Validity", "Nest Id", "Rejection Reason",
				"ScriptResult", "Report link", "Screenshot link" };
		Row row = sheet.createRow(0);
		for (int i = 0; i < headerArray.length; i++) {

			Cell cell = row.createCell(i);
			cell.setCellValue(headerArray[i]);
		}
	}

	public void excelFileCreator() throws FileNotFoundException {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Orders Details");
		out = new FileOutputStream(new File("E:\\EdelweissProject\\Reports\\ReportInExcel\\demo1.xlsx"));

	}

	public void closeExcelWriting() {
		try {
			// this Writes the workbook gfgcontribute

			workbook.write(out);
			out.close();
			System.out.println("gfgcontribute.xlsx written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ExcelWriter() {

	}

	public void excelWriter(String[] orderDetails) {

	}

	public static void main(String[] args) {
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Orders Details");
		// This data needs to be written (Object[])
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] { "ID", "NAME", "LASTNAME" });
		data.put("2", new Object[] { 1, "Pankaj", "Kumar" });
		data.put("3", new Object[] { 2, "Prakashni", "Yadav" });
		data.put("4", new Object[] { 3, "Ayan", "Mondal" });
		data.put("5", new Object[] { 4, "Virat", "kohli" });

		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			// this creates a new row in the sheet
			Row row = sheet.createRow(1);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			/*
			 * for (Object obj : objArr) { // this line creates a cell in the next column of
			 * that row Cell cell = row.createCell(cellnum++); if (obj instanceof String)
			 * cell.setCellValue((String)obj); else if (obj instanceof Integer)
			 * cell.setCellValue((Integer)obj); }
			 */
			Cell cell = row.createCell(2);
			cell.setCellValue("report");

			Hyperlink href = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
			href.setAddress("file:///E://EdelweissProject//Reports//Report1584943724486.html");
			cell.setHyperlink(href);

			Cell cell1 = row.createCell(3);
			cell1.setCellValue("Screenshot");

			Hyperlink href1 = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
			href1.setAddress("file:///E://EdelweissProject//Reports//ScreenShot//1584629156634.png");
			cell1.setHyperlink(href1);
		}
		try {
			// this Writes the workbook gfgcontribute
			FileOutputStream out = new FileOutputStream(
					new File("E:\\EdelweissProject\\Reports\\ReportInExcel\\demo1.xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("gfgcontribute.xlsx written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
