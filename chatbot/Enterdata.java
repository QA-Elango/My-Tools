package main.Botamil.chatbot;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import com.google.inject.Key;

import io.github.bonigarcia.wdm.WebDriverManager; 

public class Enterdata {
	  WebDriver driver;
    public RemoteWebDriver setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Store the WebDriver instance in the class variable
        this.driver = new ChromeDriver(options);

        return (RemoteWebDriver) this.driver;
    }

	@Test(dataProvider = "getdata", dataProviderClass = Data_utils.class)
	public void Login_data(String[] data) throws InterruptedException {
		System.err.println("login :" + data[0]);
		System.err.println("password :" + data[1]);

		setUp();
		driver.get("https://demo.botaiml.com/inbot/ChatBOT/inbot.html");
		
	WebElement Questions= driver.findElement(By.id("chat"));
	Questions.sendKeys(data[0]);
	Questions.sendKeys(Keys.ENTER);
	
		driver.findElement(By.id("myPassword")).sendKeys(data[1]);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div[3]/div[1]/button")).click();

		Thread.sleep(3000);
		
		String url = driver.getCurrentUrl(); 
		String  page1 = "https://uat.docuexec.com/accountInfo";
		if (page1.equals(url)) {
			System.out.println("Login successfully");
		} else {
			System.out.println("login failed");
		}
		driver.quit();
	}
	}
