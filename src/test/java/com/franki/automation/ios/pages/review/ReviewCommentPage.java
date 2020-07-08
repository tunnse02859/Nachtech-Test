package com.franki.automation.ios.pages.review;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ReviewCommentPage {
	public AppiumBaseDriver driver;

	@iOSXCUITFindBy(accessibility = "dismissButton")
	private WebElement btnClose;

	@iOSXCUITFindBy(className = "XCUIElementTypeTextView")
	private WebElement inputComment;

	@iOSXCUITFindBy(accessibility = "createCommentButton")
	private WebElement btnPost;

	@iOSXCUITFindBy(accessibility = "usernameLabel")
	private List<WebElement> listCommentUsername;
	
	@iOSXCUITFindBy(accessibility = "commentLabel")
	private List<WebElement> listCommentContent;
	
	
	public ReviewCommentPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(inputComment) && driver.isElementDisplayed(btnPost) ;
	}
	
	public void postComment(String comment) throws Exception {
		driver.inputText(inputComment, comment);
		driver.click(btnPost);
	}
	
	public boolean isNewCommentAdded(String comment) throws Exception {
		return driver.scrollUntillViewText(comment) != null;
	}
	
	public void clickClose() throws Exception {
		driver.click(btnClose);
	}
	
	public String getContentLastComment() {
		if(listCommentContent.size() > 0) {
			return listCommentContent.get(listCommentContent.size() - 1).getText();
		}
		
		return "";
	}
	
	public String getUsernameLastComment() {
		if(listCommentUsername.size() > 0) {
			String username = listCommentUsername.get(listCommentUsername.size() - 1).getText();
			return username.startsWith("@") ? username.substring(1) : username;
		}
		
		return "";
	}
}
