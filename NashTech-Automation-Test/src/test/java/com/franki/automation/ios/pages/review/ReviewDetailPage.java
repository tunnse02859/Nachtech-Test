package com.franki.automation.ios.pages.review;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.android.pages.business.BusinessProfilePage;
import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.appium.driver.AppiumBaseDriver.DIRECTION;
import com.franki.automation.ios.pages.recording.CreateOrEditReviewPage;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ReviewDetailPage {
	public AppiumBaseDriver driver;

	@iOSXCUITFindBy(accessibility = "Back")
	private WebElement btnBack;

	// The icon at the top right corner, click on right to view poster profile
	// This element displays only view post of others
	@iOSXCUITFindBy(accessibility = "_TtGC6franki18ListView")
	private WebElement btnPoster;
	
	// This element displays only view post of current user
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"EDIT\"]")
	private WebElement btnEditPost;

	/********* General Post Info ****************************/
	
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> listReviewDetails;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'businessNameLabel'")
	private List<WebElement> lblBusinessDisplayName;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'businessDescriptionLabel'")
	private List<WebElement> lblBusinessDescription;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'postedDateLabel'")
	private List<WebElement> lblPostedDate;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'captionLabel'")
	private List<WebElement> lblPostedBody;

	/********* Like, Share, Comment, Flag, ... ****************************/

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'starsAction'")
	private WebElement btnViewBusinessRatings;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'likesAction'")
	private WebElement btnLikePost;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'commentsAction'")
	private WebElement btnComment;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'shareAction'")
	private WebElement btnShare;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'saveAction'")
	private WebElement btnSave;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'flagAction'")
	private WebElement btnFlag;

	public ReviewDetailPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(btnViewBusinessRatings) && driver.isElementDisplayed(btnLikePost)
				&& driver.isElementDisplayed(btnComment);
	}
	
	/**
	 * Get the index of displaying review in the review list
	 * @return index
	 */
	public int getDisplayingReviewIndex () {
		for(int i = 0; i < listReviewDetails.size(); i++) {
			if(driver.isElementDisplayed(listReviewDetails.get(i), 1)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Get the total reviews in Review Detail screen
	 * @return index
	 */
	public int getTheReviewCount () {
		return listReviewDetails.size();
	}
	
	/**
	 * Get the Business Display Name of the review based on the review's index on Review collection
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public String getBusinessDisplayNameByReviewIndex(int index) throws Exception {
		return driver.getText(lblBusinessDisplayName.get(index));
	}
	
	/**
	 * Get the Posted body of the review based on the review's index on Review collection
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public String getReviewBodyByReviewIndex(int index) throws Exception {
		return driver.getText(lblPostedBody.get(index));
	}
	
	public void swipeUp() throws Exception {
		driver.swipe(DIRECTION.UP);
		driver.sleep(1);
	}
	
	public void swipeDown() throws Exception {
		driver.swipe(DIRECTION.DOWN);
		driver.sleep(1);
	}
	
	public void swipeLeft() throws Exception {
		driver.swipe(DIRECTION.LEFT);
		driver.sleep(1);
	}
	
	public void saveReview() throws Exception {
		driver.click(btnSave);
	}
	
	public void likeReview() throws Exception {
		driver.click(btnLikePost);
	}
	
	public void flagReview() throws Exception {
		driver.click(btnFlag);
		driver.sleep(5);
	}
	
	public int getLikeCount() {
		return Integer.parseInt(btnLikePost.findElement(MobileBy.className("XCUIElementTypeStaticText")).getText());
	}
	
	public ReviewRatingPage clickRatingReview() throws Exception {
		driver.click(btnViewBusinessRatings);
		return new ReviewRatingPage(driver);
	}
	
	public ReviewCommentPage clickCommentReview() throws Exception {
		driver.click(btnComment);
		return new ReviewCommentPage(driver);
	}
	
	public void clickBack() throws Exception {
		driver.click(btnBack);
	}
	
	public CreateOrEditReviewPage editReview() throws Exception {
		driver.click(btnEditPost);
		driver.waitForElementDisappear(btnEditPost, 120);
		return new CreateOrEditReviewPage(driver);
	}

}
