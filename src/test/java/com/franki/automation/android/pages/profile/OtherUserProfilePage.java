package com.franki.automation.android.pages.profile;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.android.pages.business.ProductPage;
import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class OtherUserProfilePage {

	public AppiumBaseDriver driver;

	// -------------------------------

	@AndroidFindBy(id = "tvName")
	private WebElement productList;

	@AndroidFindBy(id = "tvProductsLabel")
	private WebElement productsTab;

	@AndroidFindBy(id = "btFollow")
	private WebElement btnFollow;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Are you sure, you want to unfollow this user?\")")
	private WebElement popupUnfollow;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"OK\")")
	private WebElement btnConfirmPopup;

	// --------------------------------

	@AndroidFindBy(id = "edit_action")
	private WebElement editProfile;

	@AndroidFindBy(id = "settings_action")
	private WebElement settingProfile;

	@AndroidFindBy(id = "search_action")
	private WebElement searchProfile;

	// element when open another user's profile
	@AndroidFindBy(id = "more_settings_action")
	private WebElement moreSettingProfile;

	@AndroidFindBy(id = "tv_displayname")
	private WebElement displayName;

	@AndroidFindBy(id = "tv_fullname")
	private WebElement fullName;

	@AndroidFindBy(id = "tvGigsEarnings")
	private WebElement gigEarning;

	// -------------Reviews tab elements-----------------
	@AndroidFindBy(id = "tvReviewsCount")
	private WebElement reviews;

	@AndroidFindBy(id = "rv_review")
	private WebElement reviewsTab;

	@AndroidFindBy(id = "btn_Grid")
	private WebElement btnReviewsInGrid;

	@AndroidFindBy(id = "btn_Feed")
	private WebElement btnReviewsInFeed;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"rv_review\").childSelector(new UiSelector().className(\"android.view.ViewGroup\"))")
	private List<MobileElement> reviewItemsinGrid;

	@AndroidFindBy(id = "cl_cell_layout")
	private List<MobileElement> reviewItemsinFeed;

	@AndroidFindBy(id = "view_empty")
	private WebElement emptyView_Review;

	// ------------Follower tab elements--------------
	@AndroidFindBy(id = "tvFollowersCount")
	private WebElement followers;

	@AndroidFindBy(id = "tvInfoUsername")
	private List<MobileElement> followerUsers;

	@AndroidFindBy(id = "emptyview")
	private WebElement emptyView_Follower;

	// ------------Following tab elements--------------
	@AndroidFindBy(id = "tvgigsCount")
	private WebElement following;

	@AndroidFindBy(id = "btPeople")
	private WebElement btnPeople;

	@AndroidFindBy(id = "btPlaces")
	private WebElement btnPlace;

	@AndroidFindBy(id = "btProducts")
	private WebElement btnProduct;

	@AndroidFindBy(id = "emptyview")
	private WebElement emptyView_Following;

	@AndroidFindBy(id = "tvInfoUsername")
	private List<MobileElement> followingUsers;

	public OtherUserProfilePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(moreSettingProfile);
	}

	public String getReviewCount() throws Exception {
		return driver.getText(reviews);
	}

	public String getFollowerCount() throws Exception {
		return driver.getText(followers);
	}

	public String getFollowingCount() throws Exception {
		return driver.getText(following);
	}

	public void clickReviewTab() throws Exception {
		driver.click(reviews);
	}

	public boolean isReviewTabActive() {
		return driver.isElementDisplayed(btnReviewsInGrid) && driver.isElementDisplayed(btnReviewsInFeed);
	}

	public void clickFollowersTab() throws Exception {
		driver.click(followers);
	}

	public void clickFollowingTab() throws Exception {
		driver.click(following);
	}

	public String getDisplayedName() throws Exception {
		return driver.getText(displayName);
	}

	public String getFullName() throws Exception {
		return driver.getText(fullName);
	}

	public void clickReviewInGrid() throws Exception {
		driver.click(btnReviewsInGrid);
	}

	public void clickReviewInFeed() throws Exception {
		driver.click(btnReviewsInFeed);
	}

	public int getListReviewInGridCount() {
		return reviewItemsinGrid.size();
	}

	public int getListReviewInFeedCount() {
		return reviewItemsinFeed.size();
	}

	public boolean isEmptyReviewDisplayed() {
		return driver.isElementDisplayed(emptyView_Review);
	}

	public boolean isEmptyFollowingDisplayed() {
		return driver.isElementDisplayed(emptyView_Following);
	}

	public boolean isEmptyFollowerDisplayed() {
		return driver.isElementDisplayed(emptyView_Follower);
	}

	public boolean isFollowingTabActive() {
		return driver.isElementDisplayed(btnPeople) && driver.isElementDisplayed(btnProduct);
	}

	public void clickFollowingPeople() throws Exception {
		driver.click(btnPeople);
	}

	public void clickFollowingPlace() throws Exception {
		driver.click(btnPlace);
	}

	public void clickFollowingProduct() throws Exception {
		driver.click(btnProduct);
	}

	public int getCountListUserInTab() {
		return followingUsers.size();
	}

	public String getUserDisplayedNameInListByIndex(int index) throws Exception {
		return driver.getText(followingUsers.get(index));
	}

	public void clickUserInListByIndex(int index) throws Exception {
		driver.click(followingUsers.get(index));
	}

	public void clickFollowerUserByDisplayName(String displayName) throws Exception {
		WebElement userFollower = driver.scrollUntillViewText(displayName);
		driver.click(userFollower);
	}

	public boolean isConfirmUnfollowPopupDisplayed() {
		return driver.isElementDisplayed(popupUnfollow);
	}

	public void confirmUnfollow() throws Exception {
		driver.click(btnConfirmPopup);
	}

	public ProductPage selectProduct() throws Exception {
		driver.click(productList);
		return new ProductPage(driver);
	}

	public void clickFollow() throws Exception {
		driver.click(btnFollow);
	}

	public String getFollowStatus() throws Exception {
		return driver.getText(btnFollow);
	}
}