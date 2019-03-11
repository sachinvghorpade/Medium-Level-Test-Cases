package com.training.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RETC_017_AdminPostsAddNewPOM {
	private WebDriver driver;

	public RETC_017_AdminPostsAddNewPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(name = "post_title") // Locator to locate 'title here' text box
	private WebElement enterTitleText;

	@FindBy(xpath = "//textarea[@id='content']") // Locator to locate 'body text box' text box
	private WebElement enterContentInTextArea;

	@FindBy(id = "publish") // Locator to locate 'Publish' button
	private WebElement btnPublish;

	@FindBy(xpath = "//p[contains(text(),'Post published.')]") // Locater to locate message "Post published. View post
																// message"
	private WebElement getTextPostPublished;

	public void enterTitleText(String textToEnter) { // Method for entering credentials in Enter title here text box
		this.enterTitleText.clear();
		this.enterTitleText.sendKeys(textToEnter);
	}

	public void enterContentInTextArea(String contentToEnter) throws Exception { // Method for entering credentials in
																					// body text box
		this.enterContentInTextArea.clear();
		this.enterContentInTextArea.sendKeys(contentToEnter);
		Thread.sleep(2000);

	}

	public void clickButtonPublishOnAddNew() { // Method for clicking on Publish button
		this.btnPublish.click();
	}

	public String getTextPostPublished() { // Method for getting message "Post published. View post message"
		return this.getTextPostPublished.getText();
	}

}
