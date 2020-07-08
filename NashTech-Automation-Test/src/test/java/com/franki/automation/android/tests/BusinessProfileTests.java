package com.franki.automation.android.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.franki.automation.utility.Assertion.*;

import java.util.ArrayList;

import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.BusinessAPI;
import com.franki.automation.api.GigsAPI;
import com.franki.automation.api.MonitoringAPI;
import com.franki.automation.datamodel.BusinessData;
import com.franki.automation.datamodel.GigData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.android.pages.business.BusinessProfilePage;
import com.franki.automation.android.pages.business.BusinessRatingsPage;
import com.franki.automation.android.pages.business.BusinessVideosPage;
import com.franki.automation.android.pages.business.BusinessAttributesPage;
import com.franki.automation.android.pages.business.BusinessBookPage;
import com.franki.automation.android.pages.business.BusinessFollowersPage;
import com.franki.automation.android.pages.business.BusinessGigsPage;
import com.franki.automation.android.pages.home.HomePage;
import com.franki.automation.android.pages.login.LoginWithUsernamePage;
import com.franki.automation.android.pages.search.HomeSearchPage;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class BusinessProfileTests extends MobileTestSetup {

	LoginWithUsernamePage loginPage;
	HomePage homePage;
	HomeSearchPage searchPage;
	BusinessProfilePage businessProfilePage;
	BusinessAPI businessAPI;

	UserData user;

	@DataProvider(name = "BusinessIds")
	public Object[][] generateBusinessIds() {
		return new Object[][] { { Constant.BusinessIds.LOCAL_BUSINESS_ID },
				{ Constant.BusinessIds.GOOGLE_BUSINESS_ID } };
	}

	@BeforeClass
	public void setupAPI() throws Exception {
		user = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		businessAPI = new BusinessAPI(user);
	}

	private boolean compareFollowersList(ArrayList<UserData> list1, ArrayList<UserData> list2) {
		if (list1.size() != list2.size()) {
			return false;
		}

		if (list1.size() == list2.size() && list2.size() == 0) {
			return true;
		}

		for (int i = 0; i < list1.size(); i++) {
			UserData item1 = list1.get(i);

			if (!list2.stream().anyMatch(item2 -> item2.getDisplayName().equals(item1.getDisplayName()))) {
				return false;
			}
		}

		return true;
	}

	@Test(dataProvider = "BusinessIds")
	public void FRAN_3458_Verify_user_able_to_follow_business(int businessId) throws Exception {
		BusinessAPI businessAPI = new BusinessAPI(user);
		// Setup
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		// Make sure the user doesn't follow the business
		businessAPI.unfollowBusinessRequest(businessId);

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		// search for clone user
		// searchPage = homePage.clickToSearchIcon();
		// assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search
		// screen is displayed");
		homePage.clickToProfileTab();
		searchPage = homePage.clickToSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab place
		searchPage.clickTabPlace();

		// search and verify result displayed
		searchPage.inputSearch(searchBusinessDisplayName);
		assertTrue(searchPage.isResultDisplayed(searchBusinessDisplayName), "Cannot see expected Search result",
				"Search result is displayed correctly");

		// go to product profile
		searchPage.clickSearchResult(searchBusinessDisplayName);
		businessProfilePage = new BusinessProfilePage(driver);

		// verify opened profile is correct
		assertTrue(businessProfilePage.isActive(), "Profile screen is not displayed", "Profile screen is displayed");
		assertEquals(businessProfilePage.getFollowStatus(), "FOLLOW", "Following status is incorrectly",
				"Following status is correctly");

		// click follow and verify with API
		businessProfilePage.clickFollow_Unfollow();
		assertEquals(businessProfilePage.getFollowStatus(), "UNFOLLOW", "Following status is incorrectly",
				"Following status is correctly");

		// verify current user is following clone:
		assertTrue(businessAPI.isFollowingBusiness(businessId),
				"API verification: Follow product failed! Cannot find business in following list",
				"API verification: Follow product successfully");
	}

	@Test(dataProvider = "BusinessIds")
	public void FRAN_3459_Verify_user_able_to_unfollow_business(int businessId) throws Exception {
		BusinessAPI businessAPI = new BusinessAPI(user);
		// Setup
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		// Make sure the user doesn't follow the business
		businessAPI.followBusinessRequest(businessId);

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		// search for clone user
		// searchPage = homePage.clickToSearchIcon();
		// assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search
		// screen is displayed");
		homePage.clickToProfileTab();
		searchPage = homePage.clickToSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab place
		searchPage.clickTabPlace();

		// search and verify result displayed
		searchPage.inputSearch(searchBusinessDisplayName);
		assertTrue(searchPage.isResultDisplayed(searchBusinessDisplayName), "Cannot see expected Search result",
				"Search result is displayed correctly");

		// go to product profile
		searchPage.clickSearchResult(searchBusinessDisplayName);
		businessProfilePage = new BusinessProfilePage(driver);

		// verify opened profile is correct
		assertTrue(businessProfilePage.isActive(), "Profile screen is not displayed", "Profile screen is displayed");
		assertEquals(businessProfilePage.getFollowStatus(), "UNFOLLOW", "Following status is incorrectly",
				"Following status is correctly");

		// click follow and verify with API
		businessProfilePage.clickFollow_Unfollow();
		assertTrue(businessProfilePage.isConfirmUnfollowPopupDisplayed(), "Popup confirm unfollow is not displayed",
				"Popup confirm unfollow is displayed correctly");
		businessProfilePage.confirmUnfollow();
		assertEquals(businessProfilePage.getFollowStatus(), "FOLLOW", "Following status is incorrectly",
				"Following status is correctly");

		// verify current user is following clone:
		assertTrue(!businessAPI.isFollowingBusiness(businessId),
				"API verification: Unfollow business failed! found business in following list",
				"API verification: Unfollow business successfully");
	}

	@Test(dataProvider = "BusinessIds",enabled = false)
	public void FRAN_5601_Verify_user_able_to_book_business(int businessId) throws Exception {
		BusinessAPI businessAPI = new BusinessAPI(user);
		// Setup
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		// Make sure the user doesn't follow the business
		businessAPI.followBusinessRequest(businessId);

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		// search for clone user
		// searchPage = homePage.clickToSearchIcon();
		// assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search
		// screen is displayed");
		homePage.clickToProfileTab();
		searchPage = homePage.clickToSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab place
		searchPage.clickTabPlace();

		// search and verify result displayed
		searchPage.inputSearch(searchBusinessDisplayName);
		assertTrue(searchPage.isResultDisplayed(searchBusinessDisplayName), "Cannot see expected Search result",
				"Search result is displayed correctly");

		// go to product profile
		searchPage.clickSearchResult(searchBusinessDisplayName);
		businessProfilePage = new BusinessProfilePage(driver);

		// verify opened profile is correct
		assertTrue(businessProfilePage.isActive(), "Profile screen is not displayed", "Profile screen is displayed");

		// Book
		BusinessBookPage bookPage = businessProfilePage.clickOnBookButton();
		assertTrue(bookPage.isOnBusinessBookPage(), "You're not on the Book Page", "You're on the Book Page");

		// Check Phone
		assertEquals(bookPage.getBusinessPhone(), businessDataOnAPI.getPhone(), "The business phone is not correct",
				"The business phone is correct");

		// Check Address
		assertEquals(bookPage.getBusinessAddress(), businessDataOnAPI.getAddress(),
				"The business Address is not correct", "The business Address is correct");

		// Check Phone
		assertEquals(bookPage.getBusinessWebsite(), businessDataOnAPI.getWebsite(),
				"The business Website is not correct", "The business Website is correct");
	}

}
