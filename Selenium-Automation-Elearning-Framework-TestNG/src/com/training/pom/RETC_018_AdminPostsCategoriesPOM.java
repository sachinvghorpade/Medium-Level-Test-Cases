package com.training.pom;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RETC_018_AdminPostsCategoriesPOM {
	private WebDriver driver;

	public RETC_018_AdminPostsCategoriesPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "tag-name") // Locator to locate Name text box on the category page
	private WebElement enterNameText;

	@FindBy(id = "tag-slug") // Locator to locate Slug text box on the category page
	private WebElement enterSlungText;

	@FindBy(id = "tag-description") // Locator to locate Description text area on the category page
	private WebElement enterDescriptionTextArea;

	@FindBy(id = "submit") // Locator to locate 'Add New Category' button on the category page
	private WebElement btnAddNewCategory;

	@FindBy(xpath = "//form[1]/div[1]/div[2]/span[1]") // Locator to locate no. of categories items on the category page
	private WebElement textNumberOfCategories;

	public void enterNameText(String textToEnter) { // Method to enter Valid Credentials in Name text box category page
		this.enterNameText.clear();
		this.enterNameText.sendKeys(textToEnter);
	}

	public String enterSlungText(String textToEnter) { // Method to enter Valid Credentials in Slung text box category
														// page
		Date date = new Date();
		long timeInMilliSeconds = date.getTime();
		this.enterSlungText.clear();
		this.enterSlungText.sendKeys(textToEnter + timeInMilliSeconds);
		return textToEnter + timeInMilliSeconds;

	}

	public void enterDescriptionTextArea(String textToEnter) throws Exception { // Method to enter Valid Credentials in
																				// Description text area category page
		this.enterDescriptionTextArea.clear();
		this.enterDescriptionTextArea.sendKeys(textToEnter);
		Thread.sleep(2000);
	}

	public void clickButtonAddNewCategory() { // Method to click on 'Add New Category' button
		this.btnAddNewCategory.click();

	}

	public String getTextNumberOfCategories() { // Method to get no. of categories items on the category page
		return this.textNumberOfCategories.getText();
	}

	public void refreshPage() { // Method to refresh the page in order to see new added category in the category
								// table
		driver.navigate().refresh();

	}

	public void verifyAddedCategoryAndPrint(String slungText) throws Exception { // Method to verify the newly added
																					// category in the category table
		List<WebElement> tableRowsCategory = driver.findElements(By.xpath("//form[1]/table[1]/tbody[1]/tr"));

		for (int i = 1; i < tableRowsCategory.size(); i++) {
			String categoryName = driver.findElement(By.xpath("//form[1]/table[1]/tbody[1]/tr[" + i + "]/td[1]"))
					.getText();
			String categorySlung = driver.findElement(By.xpath("//form[1]/table[1]/tbody[1]/tr[" + i + "]/td[3]"))
					.getText();

			if (categoryName.equalsIgnoreCase("RETC_018") && categorySlung.equalsIgnoreCase(slungText)) {
				System.out
						.println("Newly Added Category is :Name= " + categoryName + "   " + "Slung= " + categorySlung);

			}
		}

	}

}
