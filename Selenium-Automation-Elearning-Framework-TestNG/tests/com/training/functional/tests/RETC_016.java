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
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class RETC_016 {

	private WebDriver driver;
	private String baseUrl;
	private LoginPOM loginPOM;
	private RETC_016_AdminPostsAllPostsPOM adminPOM;
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
		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver);
		report = new ExtentReports("report\\RETC_016.html");
		test = report.startTest("Test Case name: RETC_016");
		// open the browser
		driver.get(baseUrl);
		test.log(LogStatus.INFO, "Pre-Condition 1:", "User launched the application by entering valid URL.");
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		test.log(LogStatus.INFO, "Pre-Condition 2:", "Admin logged in.");
		screenShot.captureScreenShot("RETC_016_Admin_Login_Success");
	}

	@AfterTest
	public void tearDown() throws Exception {
		Thread.sleep(1000);
		driver.quit();
	}

	@Test
	public void Functional_Test_RETC_016() {
		String expectedResult = "1 post moved to the Trash. Undo";
		adminPOM.clickOnPosts();
		test.log(LogStatus.INFO, "Test Step 1.", "Clicked on Posts link");

		adminPOM.clickAdminPageLink("All Posts");
		test.log(LogStatus.INFO, "Test Step 2.", "Clicked on All Posts link");

		adminPOM.moveMouseToTDAllPosts();
		test.log(LogStatus.INFO, "Test Step 3.", "Mouse over to particular post");

		adminPOM.clickAdminPageLink("Trash");
		test.log(LogStatus.INFO, "Test Step 4.", "Clicked on Trash link");

		String actualResult = adminPOM.getMessageTextForTrash();

		if (expectedResult.equals(actualResult)) {
			test.log(LogStatus.PASS, "Test Passed", "1 post moved to the Trash. Undo message is displayed");
		} else {
			test.log(LogStatus.FAIL, "Test Failed", "1 post is not moved to the Trash");
		}

		screenShot.captureScreenShot("RETC_016_1_post_moved_to_the_Trash");
		Assert.assertEquals(actualResult, expectedResult);
		report.endTest(test);
		report.flush();
	}

}
