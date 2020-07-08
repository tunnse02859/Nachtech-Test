package com.franki.automation.ios.pages.profile;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.datamodel.BusinessData;
import com.franki.automation.datamodel.ContentData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.ios.pages.review.ReviewDetailPage;
import com.franki.automation.utility.Common;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class CurrentUserProfilePage {

	public AppiumBaseDriver driver;

	// -------------------------------

	@iOSXCUITFindBy(accessibility = "franki.NewMyProfileView")
	private WebElement btnThreeDot;
	
	@iOSXCUITFindBy(accessibility = "titleLabel")
	private WebElement lblFullName;
	
	@iOSXCUITFindBy(accessibility = "descriptionLabel")
	private WebElement lblDisplayName;
	
	@iOSXCUITFindBy(accessibility = "gigStatButton")
	private WebElement btnGigEarnings;
	
	// -------------Video tab elements-----------------
	@iOSXCUITFindBy(accessibility = "Video")
	private WebElement tabVideo;
	
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCollectionView/XCUIElementTypeCell")
	private List<WebElement> listReviews;

	// ------------Follower tab elements--------------
	@iOSXCUITFindBy(accessibility = "Follower")
	private WebElement tabFollowers;

	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> listFollowers;
	
	By btnFollowFollower = MobileBy.name("followButton");
	By lblFollowerDisplayName = MobileBy.name("displayNameLabel");
	By lblFollowerFullName = MobileBy.name("fullNameLabel");

	// ------------Following tab elements--------------
	@iOSXCUITFindBy(accessibility = "Following")
	private WebElement tabFollowings;
	
	@iOSXCUITFindBy(accessibility = "userTabItem")
	private WebElement tabUserFollowing;
	
	@iOSXCUITFindBy(accessibility = "businessTabItem")
	private WebElement tabBusinessFollowing;

	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> listFollowings;
	
	By lblUserFollowingDisplayName = MobileBy.AccessibilityId("primaryLabel");
	By lblUserFollowingFullname = MobileBy.AccessibilityId("secondaryLabel");
	
	By lblBusinessFollowingDisplayName = MobileBy.AccessibilityId("primaryLabel");
	By lblBusinessFollowingAddress = MobileBy.AccessibilityId("secondaryLabel");
	
	By btnFollowingFollow = MobileBy.AccessibilityId("followButton");

	// ------------Saved tab elements--------------
	@iOSXCUITFindBy(accessibility = "Saved")
	private WebElement tabSaved;
	
	@iOSXCUITFindBy(iOSNsPredicate = "value = \"no saved content\"")
	private WebElement lblNoSavedContent;
	
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> listSavedContent;
	
	By lblBusinessType = MobileBy.name("businessDescriptionLabel");
	By lblBusinessDisplayName = MobileBy.name("titleLabel");
	By lblPostedBody = MobileBy.name("descriptionLabel");
	

	public CurrentUserProfilePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(tabVideo, driver.DEFAULT_WAITTIME_SECONDS) && driver.isElementDisplayed(tabFollowers)
				&& driver.isElementDisplayed(tabFollowings) && driver.isElementDisplayed(tabSaved);
	}
	
	/**
	 * Get the current user FullName
	 * @return FullName
	 * @throws Exception
	 */
	public String getFullName() throws Exception {
		return driver.getText(lblFullName);
	}
	
	/**
	 * Get the current user DisplayName
	 * @return DisplayName
	 * @throws Exception
	 */
	public String getDisplayName() throws Exception {
		String displayName = driver.getText(lblDisplayName);
		return displayName.startsWith("@") ? displayName.substring(1) : displayName;
	}
	
	/******* Actions on Review tab *******************/
	
	public void clickOnReviewsTab() throws Exception {
		driver.click(tabVideo);
	}
	
	public boolean isOnReviewsTab() {
		return Boolean.valueOf(driver.getAttribute(tabVideo, "selected"));
	}
	
	public boolean isEmptyReviewDisplayed() {
		return listReviews.size() == 0;
	}
	
	public int getReviewsCountDisplayedOnTab() throws Exception {
		List<Integer> numbers = Common.extractNumberFromString(
				driver.getText(tabVideo.findElement(MobileBy.className("XCUIElementTypeStaticText"))));
		return numbers.size() > 0 ? numbers.get(0) : 0;
	}
	
	public int getListReviewSize() {
		return listReviews.size();
	}
	
	public ReviewDetailPage clickOnReviewByIndex(int index) throws Exception {
		if(index < listReviews.size()) {
			listReviews.get(index).click();
			return new ReviewDetailPage(driver);
		}
		
		throw new Exception("No review found by index!");
	}
	
	/***** Actions on Followers tab *********************/
	
	public void clickOnFollowersTab() throws Exception {
		driver.click(tabFollowers);
	}
	
	public boolean isOnFollowersTab() {
		return Boolean.valueOf(driver.getAttribute(tabFollowers, "selected"));
	}
	
	public boolean isNoFollowerDisplayed() {
		return listFollowers.size() == 0;
	}
	
	public void clickOnFollowerByIndex(int index) throws Exception {
		if(listFollowers.size() == 0 ) {
			throw new Exception("No Follower");
		}
		driver.click(listFollowers.get(index));
	}
	
	private String getFollowerDisplayName(WebElement cell) {
		try {
			String displayName = cell.findElement(lblFollowerDisplayName).getText();
			return displayName.startsWith("@") ? displayName.substring(1) : displayName;
		} catch (Exception e) {
			return "";
		}
	}
	
	private String getFollowerFullname(WebElement cell) {
		try {
			return cell.findElement(lblFollowerFullName).getText();
		} catch (Exception e) {
			return "";
		}
	}
	
	// Get DisplayName and FullName of all followers
	public ArrayList<UserData> getAllFollowers() {
		ArrayList<UserData> followers = new ArrayList<UserData>();
		listFollowers.stream().forEach(e -> {
			UserData user = new UserData();
			user.setDisplayName(getFollowerDisplayName(e));
			user.setFullName(getFollowerFullname(e));
			followers.add(user);
		});
		return followers;
	}
	
	/***** Actions on Following tab *********************/
	
	public void clickFollowingTab() throws Exception {
		driver.click(tabFollowings);
	}
	
	public boolean isFollowingTabActive() {
		return Boolean.valueOf(driver.getAttribute(tabFollowings, "selected"));
	}
	
	public void clickOnUserFollowingsTab() throws Exception {
		driver.click(tabUserFollowing);
	}
	
	public boolean isUserFollowingTabActive() {
		return Boolean.valueOf(driver.getAttribute(tabUserFollowing, "selected"));
	}
	
	public int getFollowingUsersNumber() {
		return listFollowings.size();
	}
	
	public String getDisplayNameOfUserFollowingsByIndex(int index) {
		return getUserFollowingDisplayName(listFollowings.get(index));
	}
	
	private String getBusinessFollowingDisplayName(WebElement cell) {
		try {
			return cell.findElement(lblBusinessFollowingDisplayName).getText();
		} catch (Exception e) {
			return "";
		}
	}
	
	private String getBusinessFollwingAddress(WebElement cell) {
		try {
			return cell.findElement(lblBusinessFollowingAddress).getText();
		} catch (Exception e) {
			return "";
		}
	}
	
	private String getUserFollowingDisplayName(WebElement cell) {
		try {
			String displayName = cell.findElement(lblUserFollowingDisplayName).getText();
			return displayName.startsWith("@") ? displayName.substring(1) : displayName;
		} catch (Exception e) {
			return "";
		}
	}
	
	private String getUserFollowingFullname(WebElement cell) {
		try {
			return cell.findElement(lblUserFollowingFullname).getText();
		} catch (Exception e) {
			return "";
		}
	}
	
	// Get DisplayName and FullName of all following users
	public ArrayList<UserData> getAllUsersFollowing() {
		ArrayList<UserData> userFollowingList = new ArrayList<UserData>();
		listFollowings.stream().forEach(e -> {
			UserData user = new UserData();
			user.setDisplayName(getUserFollowingDisplayName(e));
			user.setFullName(getUserFollowingFullname(e));
			userFollowingList.add(user);
		});
		return userFollowingList;
	}
	
	// Get DisplayName and Address of all following Businesses
	public ArrayList<BusinessData> getAllBusinessFollowing() {
		ArrayList<BusinessData> businessFollowingList = new ArrayList<BusinessData>();
		listFollowings.stream().forEach(e -> {
			BusinessData business = new BusinessData();
			business.setDisplayName(getBusinessFollowingDisplayName(e));
			business.setAddress(getBusinessFollwingAddress(e));
			businessFollowingList.add(business);
		});
		return businessFollowingList;
	}
	
	
	public String getFullNameOfUserFollowingsByIndex(int index) {
		return listFollowings.get(index).findElement(lblUserFollowingFullname).getText();
	}
	
	
	public void clickOnBusinessFollowingsTab() throws Exception {
		driver.click(tabBusinessFollowing);
	}
	
	public boolean isBusinessFollowingTabActive() {
		return Boolean.valueOf(driver.getAttribute(tabBusinessFollowing, "selected"));
	}
	
	public boolean isNoUserOrBusinessFollowing() {
		return listFollowings.size() == 0;
	}
	
	public void clickOnFollowingsByIndex(int index) throws Exception {
		driver.click(listFollowings.get(index));
	}
	
	/**** Actions on Saved tab **************************/
	
	public void clickOnSavedTab() throws Exception {
		driver.click(tabSaved);
	}
	
	public boolean isOnSavedTab() {
		return Boolean.valueOf(driver.getAttribute(tabSaved, "selected"));
	}

	public boolean isNoSavedContent() {
		return driver.isElementDisplayed(lblNoSavedContent);
	}
	
	private String getBusinessTypeLabel(WebElement cell) {
		try {
			return cell.findElement(lblBusinessType).getText().split("-")[0].trim();
		} catch (Exception e) {
			return "";
		}
	}
	
	private String getBusinessDisplayName(WebElement cell) {
		try {
			return cell.findElement(lblBusinessDisplayName).getText();
		} catch (Exception e) {
			return "";
		}
	}
	
	private String getPostedBody(WebElement cell) {
		try {
			return cell.findElement(lblPostedBody).getText();
		} catch (Exception e) {
			return "";
		}
	}
	
	// Get BusinessType, Business DisplayName, PostedBody of saved contents
	public ArrayList<ContentData> getAllSavedContents() {
		ArrayList<ContentData> savedContentList = new ArrayList<ContentData>();
		
		listSavedContent.stream().forEach(cell -> {
			ContentData content = new ContentData();
			BusinessData business = new BusinessData();
			
			business.setDisplayName(getBusinessDisplayName(cell));
			business.setBusinessType(getBusinessTypeLabel(cell));
			content.setPostBody(getPostedBody(cell));
			content.setCorporateGroup(business);
			
			savedContentList.add(content);
		});
		return savedContentList;
	}
	
	public ReviewDetailPage viewSavedPost(int index) throws Exception {
		if(index < listSavedContent.size()) {
			listSavedContent.get(index).click();
			return new ReviewDetailPage(driver);
		}
		
		throw new Exception("No saved review found by index!");
	}
	
}