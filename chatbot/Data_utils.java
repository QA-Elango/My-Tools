package main.Botamil.chatbot;

import org.testng.annotations.DataProvider;

public class Data_utils {
	
	@DataProvider(parallel = !true )
	
public  String[][]  getdata(){  
	
	
	
	String[][] exceldata = Excel_data.Getdataexcel();
	
	
	return exceldata;
	}

	
}
