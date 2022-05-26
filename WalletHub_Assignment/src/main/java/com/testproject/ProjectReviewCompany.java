package com.testproject;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class ProjectReviewCompany extends PreRequirements{
	
	
	@Test
	public static void login() throws InterruptedException {
		
		LoginPage loginPage = new LoginPage();
		String profileUrl= loginPage.login();
		//To proceed only if login is successful
		Reporter.log("The Users profile Url is "+profileUrl);
		if(profileUrl!=null) {
			CompanyPage companyPage = new CompanyPage();
			companyPage.starRating();
			companyPage.selectdropdown();
			companyPage.writeReview();
			ReviewConfirmationPage reviewConfirmation = new ReviewConfirmationPage();
			reviewConfirmation.reviewConfirmation();
			ProfilePage profilepage = new ProfilePage();
			String reviewId = profilepage.verifyCompanyReview(profileUrl);
			//To proceed to verify review in company page only if the reviewId is valid
			if(reviewId!=null)
			{
				companyPage.verifyReview(reviewId);
			}
			
		}
		softAssert.assertAll();
	}

}
