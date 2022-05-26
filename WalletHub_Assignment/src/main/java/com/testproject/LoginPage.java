package com.testproject;

import org.openqa.selenium.By;
import org.testng.Reporter;

import static com.testproject.PreRequirements.driver;
import static com.testproject.PreRequirements.softAssert;

public class LoginPage  {

	private final static By email = By.id("email");
	private final static By password = By.id("password");
	/*
	Method Name:login()
	Method Description:This method is used to login to wallet hub
	Input Parameters:Nil
	Return Parameter:It returns the profile URL on successful login else returns a null value
	*/
	public String login() throws InterruptedException {
		
		driver.get(Inputs.getString("SiteUrl"));
		driver.findElement(email).sendKeys(Inputs.getString("EmailID"));
		driver.findElement(password).sendKeys(Inputs.getString("Password"));
		driver.findElement(By.xpath("//button[span=\"Login\"]")).click();
		//Given explicit wait for 5 seconds to login to get the URL after the login 
		Thread.sleep(5000);
		String currentUrl = null;
		currentUrl = driver.getCurrentUrl();
		if(currentUrl.contains("https://wallethub.com/profile"))
		{
			currentUrl = driver.getCurrentUrl();
			Reporter.log("Login successfull");
		}
		else
		{
			System.out.println("else ");
			currentUrl = null;
			Reporter.log("Login failed.\n Please Enter correct Username and Password");
			softAssert.fail("Login is failed");
		}
		return currentUrl;
		}
	
}