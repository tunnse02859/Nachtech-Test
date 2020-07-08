package com.franki.automation.ios.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.franki.automation.utility.Assertion.*;

import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.BusinessAPI;
import com.franki.automation.api.MonitoringAPI;
import com.franki.automation.datamodel.BusinessData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.ios.pages.business.BusinessProfilePage;
import com.franki.automation.ios.pages.home.HomePage;
import com.franki.automation.ios.pages.login.LoginWithUsernamePage;
import com.franki.automation.ios.pages.profile.OtherUserProfilePage;
import com.franki.automation.ios.pages.search.HomeSearchPage;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class FollowAndUnfollowTests extends MobileTestSetup {

	LoginWithUsernamePage loginPage;
	OtherUserProfilePage otherUserProfilePage;
	BusinessProfilePage businessProfilePage;
	HomePage homePage;
	HomeSearchPage searchPage;

	MonitoringAPI userMonitoringAPI_1;
	MonitoringAPI userMonitoringAPI_2;
	UserData user_1;
	UserData user_2;


	@BeforeClass
	public void setupAPI() throws Exception {
		user_1 = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		user_2 = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_EMAIL_2, Constant.ACCOUNT_PASSWORD_2);

		userMonitoringAPI_1 = new MonitoringAPI(user_1);
		userMonitoringAPI_2 = new MonitoringAPI(user_2);

	}
	
	@DataProvider(name = "BusinessIds")
	public Object[][] generateBusinessIds() {
		return new Object[][] { { Constant.BusinessIds.LOCAL_BUSINESS_ID },
				{ Constant.BusinessIds.GOOGLE_BUSINESS_ID } };
	}

	@Test
	public void FRAN_3479_Verify_user_able_to_follow_user_profile() throws Exception {

		// let's main account unfollow clone account
		userMonitoringAPI_1.unfollowUser(user_2.getUserId());

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");

		// Call API for user data
		String user2_displayName = user_2.getDisplayName();

		// search for clone user
		searchPage = homePage.clickOnSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab people
		searchPage.clickTabPeople();
		assertTrue(searchPage.isOnTabPeople(), "Search People screen is not displayed",
				"Search People screen is displayed");

		// search and verify result displayed
		searchPage.inputSearch(user2_displayName);
		Assert.assertTrue(searchPage.isResultDisplayed(), "No Results return");

		// Select the first result
		searchPage.clickSearchResult(0);
		otherUserProfilePage = new OtherUserProfilePage(driver);

		// Follow user
		otherUserProfilePage.clickFollowUnfollow();
		
		// Verify on UI
		assertTrue(otherUserProfilePage.isFollowingUser(), "The user follows the clone user UNSUCCESSFULLY",
				"The user follows the clone user SUCCESSFULLY, UI passed");

		// Verify on API
		assertTrue(userMonitoringAPI_1.isFollowingUser(user_2.getUserId()),
				"API verification: Follow user failed! Cannot find clone user in following list",
				"API verification: Follow user successfully");
	}

	@Test
	public void FRAN_3480_Verify_user_able_to_unfollow_user_profile() throws Exception {

		// let's main account follow clone account
		userMonitoringAPI_1.followUser(user_2.getUserId());

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");

		// Call API for user data
		String user2_displayName = user_2.getDisplayName();

		// search for clone user
		searchPage = homePage.clickOnSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab people
		searchPage.clickTabPeople();
		assertTrue(searchPage.isOnTabPeople(), "Search People screen is not displayed",
				"Search People screen is displayed");

		// search and verify result displayed
		searchPage.inputSearch(user2_displayName);
		Assert.assertTrue(searchPage.isResultDisplayed(), "No Results return");

		// Select the first result
		searchPage.clickSearchResult(0);
		otherUserProfilePage = new OtherUserProfilePage(driver);

		// Unfollow user
		otherUserProfilePage.clickFollowUnfollow();
		
		// Verify on UI
		assertFalse(otherUserProfilePage.isFollowingUser(), "The user unfollows the clone user UNSUCCESSFULLY",
				"The user unfollows the clone user SUCCESSFULLY, UI passed");

		// Verify on API
		assertFalse(userMonitoringAPI_1.isFollowingUser(user_2.getUserId()),
				"API verification: Unfollow user failed! Cannot find clone user in following list",
				"API verification: Unfollow user successfully");
	}

	@Test(dataProvider = "BusinessIds")
	public void FRAN_3483_Verify_user_able_to_follow_business(int businessId) throws Exception {

		BusinessAPI businessAPI = new BusinessAPI(user_1);
		// Setup
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		// Make sure the user doesn't follow the business
		businessAPI.unfollowBusinessRequest(businessId);

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		searchPage = homePage.clickOnSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab people
		searchPage.clickTabPlaces();
		assertTrue(searchPage.isOnTabPlaces(), "Search Place screen is not displayed",
				"Search Place screen is displayed");

		// search and verify result displayed
		searchPage.inputSearch(searchBusinessDisplayName);
		Assert.assertTrue(searchPage.isResultDisplayed(), "No Results return");

		// Select the result
		searchPage.selectSearchPlaceResultByText(searchBusinessDisplayName);
		BusinessProfilePage businessProfilePage = new BusinessProfilePage(driver);
		// The current user is following the business
		Assert.assertFalse(businessProfilePage.isFollowingBusiness(), "The current user is not following the business");

		// Click follow then verify on UI and API
		businessProfilePage.clickFollowUnfollowBusiness();

		// Verify on UI
		assertTrue(businessProfilePage.isFollowingBusiness(), "The user follows the business UNSUCCESSFULLY",
				"The user follows the business SUCCESSFULLY, UI passed");

		// Verify on API
		assertTrue(businessAPI.isFollowingBusiness(businessId), "The user follows the business UNSUCCESSFULLY",
				"The user follows the business SUCCESSFULLY, API passed");
	}

	@Test(dataProvider = "BusinessIds")
	public void FRAN_3459_Verify_user_able_to_unfollow_business(int businessId) throws Exception {
		BusinessAPI businessAPI = new BusinessAPI(user_1);
		// Setup
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		// Make sure the user followed the business
		businessAPI.followBusinessRequest(businessId);

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		searchPage = homePage.clickOnSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab people
		searchPage.clickTabPlaces();
		assertTrue(searchPage.isOnTabPlaces(), "Search Place screen is not displayed",
				"Search Place screen is displayed");

		// search and verify result displayed
		searchPage.inputSearch(searchBusinessDisplayName);
		Assert.assertTrue(searchPage.isResultDisplayed(), "No Results return");

		// Select the result
		searchPage.selectSearchPlaceResultByText(searchBusinessDisplayName);
		BusinessProfilePage businessProfilePage = new BusinessProfilePage(driver);
		// The current user is following the business
		Assert.assertTrue(businessProfilePage.isFollowingBusiness(), "The current user is not following the business");

		// Click follow then verify on UI and API
		businessProfilePage.clickFollowUnfollowBusiness();

		// Verify on UI
		assertFalse(businessProfilePage.isFollowingBusiness(), "The user unfollows the business UNSUCCESSFULLY",
				"The user unfollows the business SUCCESSFULLY, UI passed");

		// Verify on API
		assertFalse(businessAPI.isFollowingBusiness(businessId), "The user unfollows the business UNSUCCESSFULLY",
				"The user unfollows the business SUCCESSFULLY, API passed");
	}


}
