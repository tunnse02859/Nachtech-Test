package com.franki.automation.ios.pages.profile;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.setup.Constant;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class OtherUserProfilePage {

	public AppiumBaseDriver driver;

	// -------------Main elements-----------------

	@iOSXCUITFindBy(iOSNsPredicate = "name = \"Back\"")
	private WebElement btnBack;

	@iOSXCUITFindBy(accessibility = "titleLabel")
	private WebElement lblFullName;

	@iOSXCUITFindBy(accessibility = "descriptionLabel")
	private WebElement lblDisplayName;

	// Button follow/unfollow
	@iOSXCUITFindBy(accessibility = "followButton")
	private WebElement btnFollow;

	@iOSXCUITFindBy(accessibility = "blockButton")
	private WebElement btnBlock;

	// -------------Reviews tab elements-----------------
	@iOSXCUITFindBy(iOSNsPredicate = "name = \"Reviews\"")
	private WebElement tabReviews;

	@iOSXCUITFindBy(className = "XCUIElementTypeCell")
	private List<WebElement> listReviews;

	// ------------Follower tab elements--------------
	@iOSXCUITFindBy(iOSNsPredicate = "name = \"Followers\"")
	private WebElement tabFollowers;

	@iOSXCUITFindBy(className = "XCUIElementTypeCell")
	private List<WebElement> listFollowers;

	// ------------Following tab elements--------------
	@iOSXCUITFindBy(iOSNsPredicate = "name = \"Followings\"")
	private WebElement tabFollowings;

	@iOSXCUITFindBy(className = "XCUIElementTypeCell")
	private List<WebElement> listFollowings;

	public OtherUserProfilePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(btnFollow, 20) && driver.isElementDisplayed(btnBlock);
	}

	public void clickFollowUnfollow() throws Exception {
		driver.tap(btnFollow);
	}

	public boolean isFollowingUser() throws Exception {
		return driver.getText(btnFollow).equalsIgnoreCase(Constant.FollowStatus.FOLLOW) ? false : true;
	}

	public String getFullName() throws Exception {
		return driver.getText(lblFullName);
	}

	public String getDisplayName() throws Exception {
		String displayName = driver.getText(lblDisplayName);
		return displayName.startsWith("@") ? displayName.substring(1) : displayName;
	}

}