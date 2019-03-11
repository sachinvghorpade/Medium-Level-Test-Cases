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
import com.training.pom.RETC_020_AdminPostsTagsPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class RETC_020 {

	private WebDriver driver;
	private String baseUrl;
	private LoginPOM loginPOM;
	private RETC_016_AdminPostsAllPostsPOM adminPOM;
	private RETC_020_AdminPostsTagsPOM adminTagsPOM;
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
		adminTagsPOM = new RETC_020_AdminPostsTagsPOM(driver);
		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver);
		report = new ExtentReports("report\\RETC_020.html");
		test = report.startTest("Test Case name: RETC_020");

		// open the browser
		driver.get(baseUrl);
		test.log(LogStatus.INFO, "Pre-Condition 1:", "User launched the application by entering valid URL");
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		test.log(LogStatus.INFO, "Pre-Condition 2:", "Admin logged in.");
		screenShot.captureScreenShot("RETC_020_Admin_Login_Success");
	}

	@AfterTest
	public void tearDown() throws Exception {
		Thread.sleep(1000);
		driver.quit();
	}

	@Test
	public void Functional_Test_RETC_020() throws Exception {

		adminPOM.clickOnPosts();
		test.log(LogStatus.INFO, "Test Step 1.", "Clicked on Posts link");

		adminPOM.clickAdminPageLink("Tags");
		test.log(LogStatus.INFO, "Test Step 2.", "Clicked on Tags link");

		String beforeAddingTag = adminTagsPOM.getTextNumberOfTags();
		test.log(LogStatus.INFO, "No. of items before Adding Tag", beforeAddingTag);
		screenShot.captureScreenShot("RETC_020_noOfTagsBeforeAdding");

		adminTagsPOM.enterTagNameText("RETC_020");
		test.log(LogStatus.INFO, "Test Step 3.", "Entered Valid Credentials in Name textbox");

		String slungTextAdded = adminTagsPOM.enterTagSlungText("launch");
		test.log(LogStatus.INFO, "Test Step 4.", "Entered Valid Credentials in Slug textbox");

		adminTagsPOM.enterTagDescriptionTextArea("New Launches of villas, apartments, flats");
		test.log(LogStatus.INFO, "Test Step 5.", "Entered Valid Credentials in Description textbox");

		adminTagsPOM.clickButtonAddNewTag();
		test.log(LogStatus.INFO, "Test Step 6.", "Clicked on Add New Tag button");

		adminTagsPOM.refreshTagPage();
		test.log(LogStatus.INFO, "Refresh Page", "Refreshed page to get added tag on the table");

		String afterAddingTag = adminTagsPOM.getTextNumberOfTags();
		test.log(LogStatus.INFO, "No. of items after Adding Tag", afterAddingTag);

		adminTagsPOM.verifyAddedTagsAndPrint(slungTextAdded);

		if ((afterAddingTag.compareToIgnoreCase(beforeAddingTag)) == 1) {
			test.log(LogStatus.PASS, "Test Passed", "Added tag in existing tags module is displayed");
		} else {
			test.log(LogStatus.FAIL, "Test Failed", "Added tag in existing tags module is not displayed");
		}
		Assert.assertNotSame(beforeAddingTag, afterAddingTag);
		screenShot.captureScreenShot("RETC_020_noOfTagsAfterAdding");
		report.endTest(test);
		report.flush();
	}

}
