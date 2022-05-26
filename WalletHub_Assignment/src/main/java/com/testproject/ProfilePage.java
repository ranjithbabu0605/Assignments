package com.testproject;
import org.openqa.selenium.By;
import org.testng.Reporter;
import static com.testproject.PreRequirements.driver;
import static com.testproject.PreRequirements.softAssert;

public class ProfilePage {
	public static String cmpanyPath = "//a[contains(@href,'"+Inputs.getString("CompanyUrl")+"')]";
	/*
	Method Name:verifyCompanyReview(String profileUrl)
	Method Description:To verify whether the review is available in the user's profile
	Input Parameters:The profile Url received on successful login
	Return Parameters:It return the Id value of the review if available else it returns a null value
	*/
	public String verifyCompanyReview(String profileUrl) throws InterruptedException {
		String reviewId=null;
		driver.get(profileUrl);
		/*I have faced issue where review is not posted immediately.
		 *I have added a forloop to reload the URL with interval 5 seconds for a maximum of 25seconds.
		 *The loops breaks if the review is available or completed only after 25 seconds
		*/
		for (int i=1;i<=5;i++)
		{
			if	(driver.findElements(By.xpath(cmpanyPath)).isEmpty()) {
				driver.get(profileUrl);
				Thread.sleep(5000);	
			}
			else
				break;
		}

		
		if	(driver.findElements(By.xpath(cmpanyPath)).isEmpty()) {
			softAssert.fail();
			Reporter.log("Your review feed is not updated in your profile");
			reviewId=null;
		}
		else {
			Reporter.log("Your review feed is updated in your profile");
			reviewId = driver.findElement(By.xpath(cmpanyPath)).getAttribute("href").split("=")[1];
		}
		return reviewId;
	}
}
