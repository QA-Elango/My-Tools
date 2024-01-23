package main.Botamil.chatbot;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.Table.Cell;

import io.github.bonigarcia.wdm.WebDriverManager;

public class IND_GST_Advantage {
	
     WebDriver driver;
  
     public RemoteWebDriver setUp() {
         WebDriverManager.chromedriver().setup();
         ChromeOptions options = new ChromeOptions();
         options.addArguments("--remote-allow-origins=*");

         // Store the WebDriver instance in the class variable
         this.driver = new ChromeDriver(options);

         return (RemoteWebDriver) this.driver;
     }
	    @Test
	    public void chatbotAutomation() throws Exception {
	    	setUp();
	    	driver.get("https://demo.botaiml.com/inbot/ChatBOT/inbot.html");

	        String excelFilePath = "C:\\Users\\sysadmin\\eclipse-workspace\\Signup\\Integrating with jenkins\\Test data\\Gst.xlsx";
	        FileInputStream fis = new FileInputStream(excelFilePath);
	        Workbook workbook = new XSSFWorkbook(fis);
	        Sheet sheet = (Sheet) workbook.getSheetAt(0);
	        Iterator<Row> rowIterator = sheet.iterator();
	        driver.findElement(By.id("chat")).click();
	        driver.switchTo().frame("ibotWindow");
            Thread.sleep(3000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	        while (rowIterator.hasNext()) {
	        	System.out.println("New test");
	            Row row = rowIterator.next();
	            String question = row.getCell(0).getStringCellValue();
	            Thread.sleep(6000);
	          
	            WebElement inputField = wait
	                    .until(ExpectedConditions.visibilityOfElementLocated(By.className("input-box")));
	            inputField.sendKeys(question);
	            WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("send-button-wrapper")));
	            sendButton.click();
	 
	            Thread.sleep(10000);  
//	            WebElement total=driver.findElement(By.className("MuiPaper-elevation1"));
	            List<WebElement> responseElements = driver.findElements(By.className("faq-menu-width-70"));
//	            List<WebElement> responseElements = driver.findElements(By.cssSelector("#chatbot > div:nth-child(3) > div > div > div > div"));


	            for (int i = 1; i < responseElements.size(); i++) {
	                String response = responseElements.get(i).getText();
	                System.out.println("Response : "+ response);
	                org.apache.poi.ss.usermodel.Cell responseCell = row.createCell(1);
	                String botResponse =response;
		            responseCell.setCellValue(botResponse);
//		            responseCell.setCellValue(response);
		            
	            }
	            FileOutputStream fos = new FileOutputStream(excelFilePath);
	            workbook.write(fos);
	            fos.close();
	            System.out.println("Next Question");

	        
	          
	            
	        }
	        workbook.close();
	    }

//	    
//	    public void tearDown() {
//	        if (driver != null) {
//	            driver.quit();
//	        }
//	    }
}
	



