package com.training.pom;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RETC_020_AdminPostsTagsPOM {
	private WebDriver driver;

	public RETC_020_AdminPostsTagsPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "tag-name") // Locator to locate Name text box
	private WebElement enterTagNameText;

	@FindBy(id = "tag-slug") // Locator to locate Slug text box
	private WebElement enterTagSlungText;

	@FindBy(id = "tag-description") // Locator to locate Description text area
	private WebElement enterTagDescriptionTextArea;

	@FindBy(id = "submit") // Locator to locate 'Add New Tag' button
	private WebElement btnAddNewTagCategory;

	@FindBy(xpath = "//form[1]/div[1]/div[2]/span[1]") // Locator to locate no. of tag items text message
	private WebElement textNumberOfTags;

	public void enterTagNameText(String textToEnter) { // Method to enter Valid Credentials in Name textbox
		this.enterTagNameText.clear();
		this.enterTagNameText.sendKeys(textToEnter);
	}

	public String enterTagSlungText(String textToEnter) { // Method to enter Valid Credentials in Slug textbox
		Date date = new Date();
		long timeInMilliSeconds = date.getTime();
		this.enterTagSlungText.clear();
		this.enterTagSlungText.sendKeys(textToEnter + timeInMilliSeconds);
		return textToEnter + timeInMilliSeconds;

	}

	public void enterTagDescriptionTextArea(String textToEnter) throws Exception { // Method to enter Valid Credentials
																					// in Description text area
		this.enterTagDescriptionTextArea.clear();
		this.enterTagDescriptionTextArea.sendKeys(textToEnter);
		Thread.sleep(2000);
	}

	public void clickButtonAddNewTag() { // Method to click on Add New Tag button
		this.btnAddNewTagCategory.click();

	}

	public String getTextNumberOfTags() { // Method to get 'no. of tag items' text message
		return this.textNumberOfTags.getText();
	}

	public void refreshTagPage() { // Method to refresh the page in order to see the newly added tag
		driver.navigate().refresh();

	}

	public void verifyAddedTagsAndPrint(String slungText) throws Exception {// Method to verify newly added tag
		List<WebElement> tableRowsCategory = driver.findElements(By.xpath("//form[1]/table[1]/tbody[1]/tr"));

		for (int i = 1; i < tableRowsCategory.size(); i++) {
			String tagName = driver.findElement(By.xpath("//form[1]/table[1]/tbody[1]/tr[" + i + "]/td[1]")).getText();
			String tagSlung = driver.findElement(By.xpath("//form[1]/table[1]/tbody[1]/tr[" + i + "]/td[3]")).getText();

			if (tagName.equalsIgnoreCase("RETC_020") && tagSlung.equalsIgnoreCase(slungText)) {
				System.out.println("Newly Added Tag is :Name= " + tagName + "   " + "Slung= " + tagSlung);

			}
		}

	}

}
