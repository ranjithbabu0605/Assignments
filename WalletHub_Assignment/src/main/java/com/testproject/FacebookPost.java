package com.testproject;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FacebookPost {
	public static void main(String[] args) throws InterruptedException, Exception
	{
		//Setting system property for chromedriver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\test\\eclipse-workspace\\test-project\\src\\main\\resources\\chromedriver\\chromedriver.exe");
		//Invoke browser
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
				
		WebDriver driver = new ChromeDriver(options);
		String username = "spectest1@gmail.com", password = "Test@1234",postMessage="Hello World";
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();		
		driver.get("https://www.facebook.com/");
		driver.findElement(By.id("email")).sendKeys(username);
		driver.findElement(By.id("pass")).sendKeys(password);
		driver.findElement(By.name("login")).click();
		driver.findElement(By.xpath("//div[@aria-label='Create a post']//span[contains(text(),\"What's on your mind\")]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[contains(@aria-label,\"What's on your mind\")]")).sendKeys(postMessage);
		driver.findElement(By.xpath("//div[@aria-label='Post' and @role='button']")).click();

	}
}
