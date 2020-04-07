package com.shreeya.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ApacheCoder {

	public void addHyperLink(String reportPath,int rowNo,int colNo) throws InvalidFormatException, IOException {
		HelperCode code=new HelperCode();
		File file=new File("../ReportABC/ReportInExcel/SampleFile.xlsx");
		XSSFWorkbook workBook=new XSSFWorkbook(file);
		XSSFSheet sheet=workBook.getSheet("Sheet1");
		Row row=sheet.getRow(rowNo);
		Cell cell=row.getCell(colNo);
		cell.setCellValue("HTMLReport_"+code.timeStampGenerator());
		Hyperlink href = workBook.getCreationHelper().createHyperlink(HyperlinkType.URL);
		href.setAddress(reportPath);
		cell.setHyperlink(href);
		System.out.println("ans : "+cell.toString());
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		 workBook.write(out);
		    FileOutputStream fout = new FileOutputStream(file);
		    fout.write(out.toByteArray());
		    fout.close();
	}
}
