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
import com.training.pom.RETC_017_AdminPostsAddNewPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class RETC_017 {

	private WebDriver driver;
	private String baseUrl;
	private LoginPOM loginPOM;
	private RETC_016_AdminPostsAllPostsPOM adminPOM;
	private RETC_017_AdminPostsAddNewPOM adminAddNewPOM;
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
		adminAddNewPOM = new RETC_017_AdminPostsAddNewPOM(driver);
		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver);
		report = new ExtentReports("report\\RETC_017.html");
		test = report.startTest("Test Case name: RETC_017");
		// open the browser
		driver.get(baseUrl);
		test.log(LogStatus.INFO, "Pre-Condition 1:", "User launched the application by entering valid URL");
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		test.log(LogStatus.INFO, "Pre-Condition 2:", "Admin logged in.");
		screenShot.captureScreenShot("RETC_017_Admin_Login_Success");
	}

	@AfterTest
	public void tearDown() throws Exception {
		Thread.sleep(1000);
		driver.quit();
	}

	@Test
	public void Functional_Test_RETC_017() throws Exception {
		String expectedResult = "Post published. View post";
		adminPOM.clickOnPosts();
		test.log(LogStatus.INFO, "Test Step 1.", "Clicked on Posts link");

		adminPOM.clickAdminPageLink("Add New");
		test.log(LogStatus.INFO, "Test Step 2.", "Clicked on Add New link");

		adminAddNewPOM.enterTitleText("New Launches");
		test.log(LogStatus.INFO, "Test Step 3.", "Entered Valid credentials in Enter title here textbox");

		adminAddNewPOM.enterContentInTextArea(" New Launch in Home");
		test.log(LogStatus.INFO, "Test Step 4.", "Entered valid credentials in body textbox");

		adminAddNewPOM.clickButtonPublishOnAddNew();
		test.log(LogStatus.INFO, "Test Step 5.", "Clicked on Publish button");

		String actualResult = adminAddNewPOM.getTextPostPublished();

		if (expectedResult.equals(actualResult)) {
			test.log(LogStatus.PASS, "Test Passed", "Post published. View post message is displayed");
		} else {
			test.log(LogStatus.FAIL, "Test Failed", "Post published. View post message is not displayed");
		}

		screenShot.captureScreenShot("RETC_017_Post_published_View_post_Message");
		Assert.assertEquals(actualResult, expectedResult);
		report.endTest(test);
		report.flush();
	}

}
