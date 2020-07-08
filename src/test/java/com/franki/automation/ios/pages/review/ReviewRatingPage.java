package com.franki.automation.ios.pages.review;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ReviewRatingPage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "button_close")
	private WebElement btnClose;

	@AndroidFindBy(id = "recycler_view_rating")
	private WebElement ratingView;
	

	
	public ReviewRatingPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(ratingView);
	}
	
	public void clickClose() throws Exception {
		driver.click(btnClose);
	}
	
}
