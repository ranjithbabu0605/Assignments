package com.testproject;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;
import io.github.bonigarcia.wdm.WebDriverManager;



public class PreRequirements {
	 public static WebDriver driver;
	 public static SoftAssert softAssert;

	 @BeforeClass
	 public static void setUp() {
		 	//To set chromedriver property using WebDriverManager
			WebDriverManager.chromedriver().setup();
			//To disable the notifications in Google Chrome browser
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			//Implicit wait 
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			softAssert= new SoftAssert();
	 }
	 
	 @AfterClass
	 public static void tearDown() {
	   driver.quit();
	 }

	}

