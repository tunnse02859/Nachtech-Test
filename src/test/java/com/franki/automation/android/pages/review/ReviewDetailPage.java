package com.franki.automation.android.pages.review;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.android.pages.business.BusinessProfilePage;
import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.appium.driver.AppiumBaseDriver.DIRECTION;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ReviewDetailPage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "btn_back")
	private WebElement btnBack;

	@AndroidFindBy(id = "img_avatar")
	private WebElement posterAvatar;

	@AndroidFindBy(id = "img_logo")
	private WebElement posterBusinessLogo;

	@AndroidFindBy(id = "tv_business_name")
	private WebElement businessName;

	@AndroidFindBy(id = "tv_distance")
	private WebElement distance;

	@AndroidFindBy(id = "tv_category")
	private WebElement category;

	@AndroidFindBy(id = "tv_posted_time_stamp")
	private WebElement postTime;

	@AndroidFindBy(id = "text_view_description")
	private WebElement reviewDescription;

	@AndroidFindBy(id = "ic_star")
	private WebElement btnStar;

	@AndroidFindBy(id = "tv_star")
	private WebElement startNumber;

	@AndroidFindBy(id = "ic_love")
	private WebElement btnHeart;

	@AndroidFindBy(id = "tv_love")
	private WebElement likeNumber;

	@AndroidFindBy(id = "img_comment")
	private WebElement btnComment;

	@AndroidFindBy(id = "img_comment")
	private WebElement commentNumber;

	@AndroidFindBy(id = "img_share")
	private WebElement btnShare;

	@AndroidFindBy(id = "ic_save")
	private WebElement btnSave;

	@AndroidFindBy(id = "ic_flag")
	private WebElement btnFlag;

	public ReviewDetailPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(btnStar) && driver.isElementDisplayed(btnHeart)
				&& driver.isElementDisplayed(btnComment);
	}
	
	public String getBusinessName() throws Exception {
		return driver.getText(businessName);
	}
	
	public String getReviewDescription() throws Exception {
		return driver.getText(reviewDescription);
	}
	
	public void goToNextReview() throws Exception {
		driver.swipe(DIRECTION.UP);
		driver.sleep(1);
	}
	
	public void goToPreviousReview() throws Exception {
		driver.swipe(DIRECTION.DOWN);
		driver.sleep(1);
	}
	
	public BusinessProfilePage goToBusinessProfile() throws Exception {
		driver.swipe(DIRECTION.LEFT);
		driver.sleep(1);
		return new BusinessProfilePage(driver);
	}
	
	public void clickSaveReview() throws Exception {
		driver.click(btnSave);
		driver.sleep(1);
	}
	
	public void clickLikeReview() throws Exception {
		driver.click(btnHeart);
		driver.sleep(1);
	}
	
	public ReviewRatingPage clickRatingReview() throws Exception {
		driver.click(btnStar);
		return new ReviewRatingPage(driver);
	}
	
	public ReviewCommentPage clickCommentReview() throws Exception {
		driver.click(btnComment);
		return new ReviewCommentPage(driver);
	}
	
	public void clickBack() throws Exception {
		driver.click(btnBack);
	}

}
