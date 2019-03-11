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
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class RETC_018 {

	private WebDriver driver;
	private String baseUrl;
	private LoginPOM loginPOM;
	private RETC_016_AdminPostsAllPostsPOM adminPOM;
	private RETC_018_AdminPostsCategoriesPOM adminCategoriesPOM;
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
		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver);
		report = new ExtentReports("report\\RETC_018.html");
		test = report.startTest("Test Case name: RETC_018");
		// open the browser
		driver.get(baseUrl);
		test.log(LogStatus.INFO, "Pre-Condition 1:", "User launched the application by entering valid URL");
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		test.log(LogStatus.INFO, "Pre-Condition 2:", "Admin logged in.");
		screenShot.captureScreenShot("RETC_018_Admin_Login_Success");
	}

	@AfterTest
	public void tearDown() throws Exception {
		Thread.sleep(1000);
		driver.quit();
	}

	@Test
	public void Functional_Test_RETC_018() throws Exception {

		adminPOM.clickOnPosts();
		test.log(LogStatus.INFO, "Test Step 1.", "Clicked on Posts link");

		adminPOM.clickAdminPageLink("Categories");
		test.log(LogStatus.INFO, "Test Step 2.", "Clicked on Categories link");

		String beforeAddingCategory = adminCategoriesPOM.getTextNumberOfCategories();
		test.log(LogStatus.INFO, "No. of items before Adding Category", beforeAddingCategory);
		screenShot.captureScreenShot("RETC_018_noOfCategoriesBeforeAdding");

		adminCategoriesPOM.enterNameText("RETC_018");
		test.log(LogStatus.INFO, "Test Step 3.", "Entered Valid Credentials in Name textbox");

		String slungTextAdded = adminCategoriesPOM.enterSlungText("launch");
		test.log(LogStatus.INFO, "Test Step 4.", "Entered Valid Credentials in Slug textbox");

		adminCategoriesPOM.enterDescriptionTextArea("New Launches of villas, apartments, flats");
		test.log(LogStatus.INFO, "Test Step 5.", "Entered Valid Credentials in Description textbox");

		adminCategoriesPOM.clickButtonAddNewCategory();
		test.log(LogStatus.INFO, "Test Step 6.", "Clicked on Add New Category button");

		adminCategoriesPOM.refreshPage();
		test.log(LogStatus.INFO, "Refresh Page", "Refreshed page to get added category on the table");

		String afterAddingCategory = adminCategoriesPOM.getTextNumberOfCategories();
		test.log(LogStatus.INFO, "No. of items after Adding Category", afterAddingCategory);

		adminCategoriesPOM.verifyAddedCategoryAndPrint(slungTextAdded);

		if ((afterAddingCategory.compareToIgnoreCase(beforeAddingCategory)) == 1) {
			test.log(LogStatus.PASS, "Test Passed", "Added category in existing categories module is displayed");
		} else {
			test.log(LogStatus.FAIL, "Test Failed", "Added category in existing categories module is not displayed");
		}
		Assert.assertNotSame(beforeAddingCategory, afterAddingCategory);
		screenShot.captureScreenShot("RETC_018_noOfCategoriesAfterAdding");
		report.endTest(test);
		report.flush();
	}

}
