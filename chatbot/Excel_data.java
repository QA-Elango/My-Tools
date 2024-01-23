package main.Botamil.chatbot;

import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_data {
 
	public static String[][] Getdataexcel()  {
		 String filelocation="./Test data/Gst.xlsx";
		 XSSFWorkbook Wbook = null;
		try {
			Wbook = new  XSSFWorkbook(filelocation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 XSSFSheet sheet= Wbook.getSheetAt(0);
		int physicalrows = sheet.getPhysicalNumberOfRows();
		System.out.println("Inclusive of rows : "+physicalrows);
		int lastrownum =  sheet.getLastRowNum();
		System.out.println("no of row  : "+lastrownum);
		 short lastcellnum=sheet.getRow(0).getLastCellNum();
		System.out.println("No of cells :"+lastcellnum);
		String[] [] data = new String[lastrownum][lastcellnum];
		 for (int i = 1; i <=lastrownum; i++) {
			XSSFRow row = sheet.getRow(i);
			for (int j = 0; j <lastcellnum; j++) { 
				XSSFCell cell = row.getCell(j);
				DataFormatter dft = new DataFormatter();
				dft.formatCellValue(cell);
				String value= dft.formatCellValue(cell);
//				String value = cell.getStringCellValue();
//				System.out.println(value);
				data [i-1][j] = value;
			} 
		}
		 try {
			Wbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return data;
	}
	}
