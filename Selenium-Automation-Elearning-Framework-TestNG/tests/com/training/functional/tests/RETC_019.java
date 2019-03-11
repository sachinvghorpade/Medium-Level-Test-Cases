package com.training.functional.tests;

import java.io.FileInputStream;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.training.generics.ScreenShot;
import com.training.pom.LoginPOM;
import com.training.pom.RETC_016_AdminPostsAllPostsPOM;
import com.training.pom.RETC_018_AdminPostsCategoriesPOM;
import com.training.pom.RETC_019_AdminPostsDeleteCategoryPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class RETC_019 {

	private WebDriver driver;
	private String baseUrl;
	private LoginPOM loginPOM;
	private RETC_016_AdminPostsAllPostsPOM adminPOM;
	private RETC_018_AdminPostsCategoriesPOM adminCategoriesPOM;
	private RETC_019_AdminPostsDeleteCategoryPOM adminDeleteCategoryPOM;
	private static Properties properties;
	private ScreenShot screenShot;
	private ExtentTest test;
	private ExtentReports report;

	@BeforeTest
	public void setUp() throws Exception {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);
		driver = DriverFactory.getDriver(DriverNames.CHROME);
		loginPOM = new LoginPOM(driver);
		adminPOM = new RETC_016_AdminPostsAllPostsPOM(driver);
		adminCategoriesPOM = new RETC_018_AdminPostsCategoriesPOM(driver);
		adminDeleteCategoryPOM = new RETC_019_AdminPostsDeleteCategoryPOM(driver);
		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver);
		report = new ExtentReports("report\\RETC_019.html");
		test = report.startTest("Test Case name: RETC_019");
		// open the browser
		driver.get(baseUrl);
		test.log(LogStatus.INFO, "Pre-Condition 1:", "User launched the application by entering valid URL");
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		test.log(LogStatus.INFO, "Pre-Condition 2:", "Admin logged in.");
		screenShot.captureScreenShot("RETC_019_Admin_Login_Success");
	}

	@AfterTest
	public void tearDown() throws Exception {
		Thread.sleep(1000);
		driver.quit();
	}

	@Test
	public void Functional_Test_RETC_019() throws Exception {

		String expectedResult = "Categories deleted.";

		adminPOM.clickOnPosts();
		test.log(LogStatus.INFO, "Test Step 1.", "Clicked on Posts link");

		adminPOM.clickAdminPageLink("Categories");
		test.log(LogStatus.INFO, "Test Step 2.", "Clicked on Categories link");

		String beforeDeletingCategory = adminCategoriesPOM.getTextNumberOfCategories();
		test.log(LogStatus.INFO, "No. of items before Deleting Category", beforeDeletingCategory);
		screenShot.captureScreenShot("RETC_019_noOfCategoriesBeforeDeleting");

		adminDeleteCategoryPOM.clickchkboxOfCategoryToBeDeleted();
		test.log(LogStatus.INFO, "Test Step 3.", "Clicked on the checkbox of the category to be deleted");

		adminDeleteCategoryPOM.clickDropdownAction();
		test.log(LogStatus.INFO, "Test Step 4.", "Bulk Actions and Delete link is displayed.");

		adminDeleteCategoryPOM.selectDeleteFromDropdownAction();
		test.log(LogStatus.INFO, "Test Step 5.", "Selected Delete in Bulk Action links");

		adminDeleteCategoryPOM.clickbtnApplyAction();
		test.log(LogStatus.INFO, "Test Step 6.", "Clicked on Apply button");

		String afterDeletingCategory = adminCategoriesPOM.getTextNumberOfCategories();
		test.log(LogStatus.INFO, "No. of items after Deleting Category", afterDeletingCategory);
		screenShot.captureScreenShot("RETC_019_noOfCategoriesAfterDeleting");

		String actualResult = adminDeleteCategoryPOM.gettextDeletedCategoryMessage();

		if (expectedResult.equals(actualResult)) {
			test.log(LogStatus.PASS, "Test Passed",
					"Categories deleted. Message is displayed and selected category is removed from the categories list");
		} else {
			test.log(LogStatus.FAIL, "Test Failed",
					"Categories deleted. Message is not displayed and selected category is not removed from the categories list");
		}

		screenShot.captureScreenShot("RETC_019_Categories deleted");
		Assert.assertEquals(actualResult, expectedResult);
		report.endTest(test);
		report.flush();
	}

}
