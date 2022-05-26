package com.testproject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import static com.testproject.PreRequirements.driver;
import static com.testproject.PreRequirements.softAssert;
import org.testng.Reporter;

public class CompanyPage {
	
	
	static String companyUrl = Inputs.getString("CompanyUrl");
	static int starRating = Integer.parseInt(Inputs.getString("StarRating"));
	static String dropdownOption = Inputs.getString("Policy");
	static String review = Inputs.getString("Review"); 

	/*
	Method Name:verifyReview(String reviewId)
	Method Description:To verify the review entered in the company profile page
	Input Parameters:The review id received from profile page
	Return Parameters:Nil
	*/
	public void verifyReview(String reviewId) {
		
		String reviewUrl = companyUrl+"?review="+reviewId;
		String reviewSection ="//article[@data-rvid='"+reviewId+"']";
		driver.get(reviewUrl);
		System.out.println(reviewSection);
		
		//To verify that the review is available in the company profile
		if (driver.findElement(By.xpath(reviewSection+"//meta[@itemprop='name']")).getAttribute("content").equals("Your Review"))
		{
			Reporter.log("Your review in available");
			//To verify correct star rating is available
			if (Integer.valueOf(driver.findElement(By.xpath(reviewSection+"//meta[@itemprop='ratingValue']")).getAttribute("content"))==starRating)
				Reporter.log("Correct star rating is selected");
			else
				softAssert.fail("Correct no of starts is not available");
			//To verify that the correct review is displayed
			if (driver.findElement(By.xpath(reviewSection+"//div[@itemprop='description']")).getText().equals(review))
				Reporter.log("The correct review is posted");
			else
				softAssert.fail("The correct review is not posted");
			//To verify that the correct product is selected
			if (driver.findElement(By.xpath(reviewSection+"//div[@class='rvtab-ci-category']/span")).getText().endsWith(dropdownOption))
				Reporter.log("The correct category is selected");
			else
				softAssert.fail("The correct category is not selected");
		}
		else {
			Reporter.log("Your review in not available");
			softAssert.fail();
		}
		
	}

	/*
	Method Name:starRating()
	Method Description:To select the correct star rating in the company page
	Input Parameters:Nil
	Return Parameters:Nil
	*/
	public void starRating(){
		
		if (starRating <=5)
		{
			String stars;
			driver.get(Inputs.getString("CompanyUrl"));
			stars = "//review-star[@class='rvs-svg']//*[local-name()='svg' and @aria-label='"+starRating+" star rating']";
			Actions mouseAction = new Actions(driver);
			mouseAction.moveToElement(driver.findElement(By.xpath(stars))).perform();
			boolean highlight = driver.findElement(By.xpath("//review-star[@class='rvs-svg']//*[local-name()='svg' and @aria-label='"+starRating+" star rating']")).getAttribute("aria-checked").contains("true");// and ='true']"));
			if (highlight)
			{
				Reporter.log("The star in "+starRating+" position is highlighted");
				mouseAction.click().perform();
			}
			else
			{
				softAssert.fail("Star rating is not highlghted");
				Reporter.log("The star in "+starRating+" position is not highlighted");
			}
			mouseAction.click().perform();
		}
		else{
			softAssert.fail("Invalid star rating");
			Reporter.log("Please enter a star rating from 1-5");
		}
	}
	
	/*
	Method Name:selectdropdown()
	Method Description:To select the policy from the policy dropdown
	Input Parameters:Nil
	Return Parameters:Nil
	*/
	public void selectdropdown() throws InterruptedException {
		// TODO Auto-generated method stub
		driver.findElement(By.className("wrev-drp")).click();
		WebElement dropdown = driver.findElement(By.xpath("//ng-dropdown[@class='wrev-drp']//ul")); 
		Thread.sleep(2000);
		if (dropdown.isDisplayed()==true)
		{
			Reporter.log("Dropdown is available");
			List<WebElement> dropdownList= driver.findElements(By.xpath("//ng-dropdown[@class='wrev-drp']//ul/li"));
			for(int i=0;i<dropdownList.size();i++)
			{
				
				if(dropdownList.get(i).getText().equals(dropdownOption))
				{
					Reporter.log(dropdownList.get(i).getText());
					dropdownList.get(i).click();
					break;
				}
				else if(i==dropdownList.size()-1 &&  (!dropdownList.get(i).getText().equals(dropdownOption)))
				{
					Reporter.log(dropdownList.get(i).getText());
					softAssert.fail("Dropdown failure");
					Reporter.log("The \""+dropdownOption+"\" option is not available in the above dropdownlist");
				}
				else 
				{
					Reporter.log(dropdownList.get(i).getText());
				}
			}
		}
		else 
		{
			softAssert.fail("Dropdown failure");
			Reporter.log("Dropdown not available");
		}
	}
	/*
	Method Name:writeReview()
	Method Description:To write the users review in the review section and click submit button
	Input Parameters:Nil
	Return Parameters:Nil
	*/
	public void writeReview() throws InterruptedException {
		
		driver.findElement(By.xpath("//textarea[@placeholder = 'Write your review...']")).sendKeys(review);
		String charCounter = driver.findElement(By.className("wrev-user-input-count")).getText();
		//I have used inpage validation to check the minimum characters allowed instead of 200
		int minCount = Integer.parseInt(charCounter.split(" ")[0]);
		int enteredChars = Integer.parseInt(driver.findElement(By.xpath("//div[@class='wrev-user-input-count']/span")).getText());
		Reporter.log("The minimum no of character required is"+minCount);
		
		if(minCount > enteredChars)
		{
			driver.findElement(By.xpath("//div[text()=' Submit ']")).click();
			//to verify if error message is displayed
			WebElement error = driver.findElement(By.xpath("//div[text()='Please add at least "+minCount+" characters']"));
			if (error.isDisplayed()==true)
			{
				Reporter.log("Error message\""+error.getText()+"\" is displayed");
			}
		}
		else if(minCount <= enteredChars)
		{
			driver.findElement(By.xpath("//div[text()=' Submit ']")).click();
			Thread.sleep(3000);
			if(driver.getTitle().equals("WalletHub - Review Confirmation"))
			{
				Reporter.log("Confirmation Screen is opened");
			}
			else 
			{
				softAssert.fail("Confirmation Screen is not opened");
				Reporter.log("You did not receive a confirmation for your review");
			}
		}
	}
	
}
