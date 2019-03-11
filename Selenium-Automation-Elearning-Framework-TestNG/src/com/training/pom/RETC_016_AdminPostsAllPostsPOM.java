package com.training.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RETC_016_AdminPostsAllPostsPOM {
	private WebDriver driver;

	public RETC_016_AdminPostsAllPostsPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[contains(text(),'Posts')]") // Locator to locate Posts link
	private WebElement posts;

	@FindBy(xpath = "//table[1]/tbody[1]/tr[1]/th[1]/input[1]") // Locator to locate first post row in the table
	private WebElement firstTableDataAllPosts;

	@FindBy(xpath = "//p[contains(text(),'1 post moved to the Trash.')]") // Locator to locate text message "1 post
																			// moved to the Trash. Undo"
	private WebElement getMessageTextForTrash;

	public void clickOnPosts() { // Method for clicking on Posts link
		this.posts.click();
	}

	public void clickAdminPageLink(String adminPageLinkText) { // Method for clicking any hyper link on admin page
		driver.findElement(By.linkText(adminPageLinkText)).click();
	}

	public void moveMouseToTDAllPosts() { // Method for mouse over movement to first post row in the table
		Actions act = new Actions(driver);
		act.moveToElement(firstTableDataAllPosts).build().perform();
	}

	public String getMessageTextForTrash() { // Method for getting text message "1 post moved to the Trash. Undo"
		return this.getMessageTextForTrash.getText();
	}

}
