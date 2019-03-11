package com.training.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class RETC_019_AdminPostsDeleteCategoryPOM {
	private WebDriver driver;

	public RETC_019_AdminPostsDeleteCategoryPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//table[1]/tbody[1]/tr[1]/th[1]/input[1]") // Locator to locate check box of the category to be
																// deleted
	private WebElement chkboxOfCategoryToBeDeleted;

	@FindBy(id = "bulk-action-selector-top") // Locator to locate Bulk Action list box
	private WebElement dropdownAction;

	@FindBy(id = "doaction") // Locator to locate Apply button
	private WebElement btnApplyAction;

	@FindBy(xpath = "//p[contains(text(),'Categories deleted.')]") // Locator to locate text message "Categories
																	// deleted."
	private WebElement textDeletedCategoryMessage;

	public void clickchkboxOfCategoryToBeDeleted() { // Method to click on the check box of the category to be deleted
		this.chkboxOfCategoryToBeDeleted.click();

	}

	public void clickDropdownAction() { // Method to click on Bulk Action list box
		this.dropdownAction.click();
	}

	public void selectDeleteFromDropdownAction() { // Method to select Delete in Bulk Action links
		Select sel = new Select(this.dropdownAction);
		sel.selectByVisibleText("Delete");

	}

	public void clickbtnApplyAction() { // Method to click on Apply button
		this.btnApplyAction.click();
	}

	public String gettextDeletedCategoryMessage() { // Method to get text message "Categories deleted."
		return this.textDeletedCategoryMessage.getText();
	}

}
