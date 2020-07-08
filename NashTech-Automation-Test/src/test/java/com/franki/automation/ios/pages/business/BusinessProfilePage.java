package com.franki.automation.ios.pages.business;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.ios.pages.recording.RecordingPage;
import com.franki.automation.setup.Constant;
import com.franki.automation.utility.Common;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BusinessProfilePage {

	public AppiumBaseDriver driver;

	@iOSXCUITFindBy(accessibility = "Back")
	private WebElement btnBack;

	@iOSXCUITFindBy(accessibility = "createReviewButton")
	private WebElement btnCreateReview;

	@iOSXCUITFindBy(accessibility = "followButton")
	private WebElement btnFollow;

	@iOSXCUITFindBy(accessibility = "bookButton")
	private WebElement btnBook;

	@iOSXCUITFindBy(accessibility = "titleLabelHeader")
	private WebElement lblBusinessDisplayName;

	@iOSXCUITFindBy(accessibility = "descriptionLabelHeader")
	private WebElement lblBusinessAddress;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Rating'")
	protected WebElement tabRatings;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Attributes'")
	protected WebElement tabAttributes;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Video'")
	protected WebElement tabVideos;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Follower'")
	protected WebElement tabFollowers;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'GiG'")
	protected WebElement tabGigs;

	public BusinessProfilePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(btnCreateReview, 10) && driver.isElementDisplayed(lblBusinessDisplayName)
				&& driver.isElementDisplayed(tabRatings) && driver.isElementDisplayed(tabAttributes)
				&& driver.isElementDisplayed(tabVideos) && driver.isElementDisplayed(tabFollowers)
				&& driver.isElementDisplayed(tabGigs);
	}

	public RecordingPage clickBeFrankFromBusiness() throws Exception {
		driver.click(btnCreateReview);
		return new RecordingPage(driver);
	}
	
	public void clickFollowUnfollowBusiness() throws Exception {
		driver.click(btnFollow);
	}

	public boolean isFollowingBusiness() throws Exception {
		return driver.getText(btnFollow).equalsIgnoreCase(Constant.FollowStatus.FOLLOW) ? false : true;
	}

	public String getBusinessDisplayName() throws Exception {
		return driver.getText(lblBusinessDisplayName);
	}

	public String getBusinessAddress() throws Exception {
		return driver.getText(lblBusinessAddress);
	}

	public BusinessBookPage clickOnBookButton() throws Exception {
		driver.click(btnBook);
		return new BusinessBookPage(driver);
	}
	
	public void clickBack() throws Exception {
		driver.click(btnBack);
	}

	/************ Ratings ****************************/
	public BusinessRatingsPage clickOnRatingsTab() throws Exception {
		driver.click(tabRatings);
		return new BusinessRatingsPage(driver);
	}

	public int getRatingsCount() throws Exception {
		List<Integer> numbers = Common.extractNumberFromString(
				driver.getText(tabRatings.findElement(MobileBy.className("XCUIElementTypeStaticText"))));
		return numbers.size() > 0 ? numbers.get(0) : 0;
	}

	/************ Followers ****************************/
	public BusinessFollowersPage clickOnFollowersTab() throws Exception {
		driver.click(tabFollowers);
		return new BusinessFollowersPage(driver);
	}

	public int getFollowersCount() throws Exception {
		List<Integer> numbers = Common.extractNumberFromString(
				driver.getText(tabFollowers.findElement(MobileBy.className("XCUIElementTypeStaticText"))));
		return numbers.size() > 0 ? numbers.get(0) : 0;
	}

	/************ Videos ****************************/
	public BusinessVideosPage clickOnVideosTab() throws Exception {
		driver.click(tabVideos);
		return new BusinessVideosPage(driver);
	}

	public int getVideosCount() throws Exception {
		List<Integer> numbers = Common.extractNumberFromString(
				driver.getText(tabVideos.findElement(MobileBy.className("XCUIElementTypeStaticText"))));
		return numbers.size() > 0 ? numbers.get(0) : 0;
	}

	/************ Attributions ****************************/
	public BusinessAttributesPage clickOnAttributionTab() throws Exception {
		driver.click(tabAttributes);
		return new BusinessAttributesPage(driver);
	}

	/************ Gigs ****************************/
	public BusinessGigsPage clickOnGigsTab() throws Exception {
		driver.click(tabGigs);
		return new BusinessGigsPage(driver);
	}

	public int getActiveGigsCount() throws Exception {
		List<Integer> numbers = Common.extractNumberFromString(
				driver.getText(tabGigs.findElement(MobileBy.className("XCUIElementTypeStaticText"))));
		return numbers.size() > 0 ? numbers.get(0) : 0;

	}

}