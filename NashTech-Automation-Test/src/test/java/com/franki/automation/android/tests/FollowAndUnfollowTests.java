package com.franki.automation.android.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.franki.automation.utility.Assertion.*;

import com.franki.automation.android.pages.business.BusinessProfilePage;
import com.franki.automation.android.pages.home.HomePage;
import com.franki.automation.android.pages.login.LoginWithUsernamePage;
import com.franki.automation.android.pages.profile.OtherUserProfilePage;
import com.franki.automation.android.pages.search.HomeSearchPage;
import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.BusinessAPI;
import com.franki.automation.api.EverContentAPI;
import com.franki.automation.api.MonitoringAPI;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class FollowAndUnfollowTests extends MobileTestSetup {

	LoginWithUsernamePage loginPage;
	OtherUserProfilePage profilePage;
	BusinessProfilePage groupProfilePage;
	HomePage homePage;
	HomeSearchPage searchPage;

	EverContentAPI everContentAPI;
	MonitoringAPI userMonitoringAPI_1;
	MonitoringAPI userMonitoringAPI_2;
	BusinessAPI corporateGroupAPI;
	AuthenticateAPI authenAPI;
	UserData user_1;
	UserData user_2;
	
	String productGroupId;
	String productGroupDisplayName;
	
	String placeGroupId;
	String placeGroupDisplayName;

	@BeforeClass
	public void setupAPI() throws Exception {
		authenAPI = new AuthenticateAPI();
		user_1 = authenAPI.loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		user_2 = authenAPI.loginForAccessToken(Constant.ACCOUNT_EMAIL_2, Constant.ACCOUNT_PASSWORD_2);

		userMonitoringAPI_1 = new MonitoringAPI(user_1);
		userMonitoringAPI_2 = new MonitoringAPI(user_2);
		
		corporateGroupAPI = new BusinessAPI(user_1);
		
		productGroupId = corporateGroupAPI.getCorporateGroupIDInSearchListByIndex(0);
		productGroupDisplayName = corporateGroupAPI.getCorporateGroupDisplayNameInSearchListByIndex(0);
	}

	@Test
	public void FRAN_3479_Verify_user_able_to_follow_user_profile() throws Exception {

		// let's main account unfollow clone account
		userMonitoringAPI_1.stopMonitoringRequest(user_2.getUserId());

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		// Call API for user data
		String user2_displayName = user_2.getDisplayName();
		String user2_fullName = user_2.getFullName();
		// search for clone user
		searchPage = homePage.clickToSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab people
		searchPage.clickTabPeople();

		// search and verify result displayed
		searchPage.inputSearch(user2_displayName);
		assertTrue(searchPage.isResultDisplayed(user2_displayName), "Cannot see expected Search result",
				"Search result is displayed correctly");

		// go to user's profile
		searchPage.clickSearchResult(user2_displayName);
		profilePage = new OtherUserProfilePage(driver);

		// verify opened profile is correct
		assertEquals(profilePage.getFullName(), user2_fullName, "Profile full name is displayed incorrectly",
				"Profile fullname is displayed correctly");
		assertEquals(profilePage.getFollowStatus(), "FOLLOW", "Following status is incorrectly",
				"Following status is correctly");

		// click follow and verify with API
		profilePage.clickFollow();
		assertEquals(profilePage.getFollowStatus(), "UNFOLLOW", "Following status is incorrectly",
				"Following status is correctly");

		// verify current user is following clone:
		assertTrue(userMonitoringAPI_1.isFollowingUser(user_2.getUserId()),
				"API verification: Follow user failed! Cannot find clone user in following list", "API verification: Follow user successfully");
	}
	
	@Test
	public void FRAN_3480_Verify_user_able_to_unfollow_user_profile() throws Exception {

		// let's main account follow clone account
		userMonitoringAPI_1.createMonitoringRequest(user_2.getUserId());

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		// Call API for user data
		String user2_displayName = user_2.getDisplayName();
		String user2_fullName = user_2.getFullName();
		// search for clone user
		searchPage = homePage.clickToSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab people
		searchPage.clickTabPeople();

		// search and verify result displayed
		searchPage.inputSearch(user2_displayName);
		assertTrue(searchPage.isResultDisplayed(user2_displayName), "Cannot see expected Search result",
				"Search result is displayed correctly");

		// go to user's profile
		searchPage.clickSearchResult(user2_displayName);
		profilePage = new OtherUserProfilePage(driver);

		// verify opened profile is correct
		assertEquals(profilePage.getFullName(), user2_fullName, "Profile full name is displayed incorrectly",
				"Profile fullname is displayed correctly");
		assertEquals(profilePage.getFollowStatus(), "UNFOLLOW", "Following status is incorrectly",
				"Following status is correctly");

		// click follow and verify with API
		profilePage.clickFollow();
		assertTrue(profilePage.isConfirmUnfollowPopupDisplayed(),"Popup confirm unfollow is not displayed",
				"Popup confirm unfollow is displayed correctly");
		profilePage.confirmUnfollow();
		assertEquals(profilePage.getFollowStatus(), "FOLLOW", "Following status is incorrectly",
				"Following status is correctly");

		// verify current user is following clone:
		assertTrue(userMonitoringAPI_1.isFollowingUser(user_2.getUserId()) == false,
				"API verification: Unfollow user failed! Cannot find clone user in following list", "API verification: Unfollow user successfully");
	}
	
	@Test
	public void FRAN_3483_Verify_user_able_to_follow_business() throws Exception {

		// let's main account unfollow clone account
		userMonitoringAPI_1.archiveGroupAssociation(productGroupId);

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		// search for clone user
		searchPage = homePage.clickToSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab people
		searchPage.clickTabPlace();

		// search and verify result displayed
		searchPage.inputSearch(productGroupDisplayName);
		assertTrue(searchPage.isResultDisplayed(productGroupDisplayName), "Cannot see expected Search result",
				"Search result is displayed correctly");

		// go to product profile
		searchPage.clickSearchResult(productGroupDisplayName);
		groupProfilePage = new BusinessProfilePage(driver);

		// verify opened profile is correct
		assertTrue(groupProfilePage.isActive(), "Profile screen is not displayed", "Profile screen is displayed");
		assertEquals(groupProfilePage.getBusinessDisplayedName(), productGroupDisplayName, "Profile name is displayed incorrectly",
				"Profile fullname is displayed correctly");
		assertEquals(groupProfilePage.getFollowStatus(), "FOLLOW", "Following status is incorrectly",
				"Following status is correctly");

		// click follow and verify with API
		groupProfilePage.clickFollow_Unfollow();
		assertEquals(groupProfilePage.getFollowStatus(), "UNFOLLOW", "Following status is incorrectly",
				"Following status is correctly");

		// verify current user is following clone:
		assertTrue(userMonitoringAPI_1.isFollowingGroupOrPlace(productGroupId),
				"API verification: Follow product failed! Cannot find clone user in following list", "API verification: Follow product successfully");
	}
	
	@Test
	public void FRAN_3484_Verify_user_able_to_unfollow_business() throws Exception {

		// let's main account unfollow clone account
		userMonitoringAPI_1.associateWithGroup(productGroupId);

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		// search for clone user
		searchPage = homePage.clickToSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab people
		searchPage.clickTabPlace();

		// search and verify result displayed
		searchPage.inputSearch(productGroupDisplayName);
		assertTrue(searchPage.isResultDisplayed(productGroupDisplayName), "Cannot see expected Search result",
				"Search result is displayed correctly");

		// go to product profile
		searchPage.clickSearchResult(productGroupDisplayName);
		groupProfilePage = new BusinessProfilePage(driver);

		// verify opened profile is correct
		assertTrue(groupProfilePage.isActive(), "Profile screen is not displayed", "Profile screen is displayed");
		assertEquals(groupProfilePage.getBusinessDisplayedName(), productGroupDisplayName, "Profile name is displayed incorrectly",
				"Profile fullname is displayed correctly");
		assertEquals(groupProfilePage.getFollowStatus(), "UNFOLLOW", "Following status is incorrectly",
				"Following status is correctly");

		// click follow and verify with API
		groupProfilePage.clickFollow_Unfollow();
		assertTrue(groupProfilePage.isConfirmUnfollowPopupDisplayed(),"Popup confirm unfollow is not displayed",
				"Popup confirm unfollow is displayed correctly");
		groupProfilePage.confirmUnfollow();
		assertEquals(groupProfilePage.getFollowStatus(), "FOLLOW", "Following status is incorrectly",
				"Following status is correctly");

		// verify current user is following clone:
		assertTrue(userMonitoringAPI_1.isFollowingGroupOrPlace(productGroupId) == false,
				"API verification: Unfollow product failed! Cannot find clone user in following list", "API verification: Unfollow product successfully");
	}

}
