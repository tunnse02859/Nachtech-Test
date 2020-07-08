package com.franki.automation.android.pages.review;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ReviewCommentPage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "button_close")
	private WebElement btnClose;

	@AndroidFindBy(id = "recycler_comment")
	private WebElement commentDiv;
	
	@AndroidFindBy(id = "input_comment")
	private WebElement inputComment;

	@AndroidFindBy(id = "button_post")
	private WebElement btnPost;

	
	public ReviewCommentPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(commentDiv);
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
	
}
