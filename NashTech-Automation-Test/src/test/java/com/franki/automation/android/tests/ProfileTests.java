package com.franki.automation.android.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.franki.automation.utility.Assertion.*;

import com.franki.automation.android.pages.home.HomePage;
import com.franki.automation.android.pages.login.LoginWithUsernamePage;
import com.franki.automation.android.pages.profile.CurrentUserProfilePage;
import com.franki.automation.android.pages.profile.OtherUserProfilePage;
import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.BusinessAPI;
import com.franki.automation.api.EverContentAPI;
import com.franki.automation.api.MonitoringAPI;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class ProfileTests extends MobileTestSetup {

	CurrentUserProfilePage profilePage;
	UserData user_1;
	UserData user_2;
	EverContentAPI everContentAPI_1;
	MonitoringAPI userMonitoringAPI_1;
	MonitoringAPI userMonitoringAPI_2;
	AuthenticateAPI authenAPI_1;
	AuthenticateAPI authenAPI_2;
	BusinessAPI corporateGroupAPI;
	
	
	@BeforeClass
	public void setupAPI() throws Exception {
		authenAPI_1 = new AuthenticateAPI();
		authenAPI_2 = new AuthenticateAPI();
		user_1 = authenAPI_1.loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		user_2 = authenAPI_2.loginForAccessToken(Constant.ACCOUNT_EMAIL_2, Constant.ACCOUNT_PASSWORD_2);


		everContentAPI_1 = new EverContentAPI(user_1);

		userMonitoringAPI_1 = new MonitoringAPI(user_1);
		userMonitoringAPI_2 = new MonitoringAPI(user_2);

		corporateGroupAPI = new BusinessAPI(user_1);
	}

	public void gotoProfilePage() throws Exception {
		LoginWithUsernamePage loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		HomePage homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
		// click profile tab here
		profilePage = homePage.clickToProfileTab();
		assertTrue(profilePage.isActive(), "Profile page is not displayed", "Profile page is displayed");
	}

	@Test
	public void FRAN_3393_Verify_user_can_access_to_Profile_screen() throws Exception {
		gotoProfilePage();

		// Call API for user data
		String fullname = user_1.getFullName();

		// verify full name
		assertEquals(profilePage.getFullName(), fullname, "Full Name displayed incorrectly",
				"Full Name displayed correctly");
	}

	@Test
	public void FRAN_3394_Verify_user_can_access_to_Reviews_tab() throws Exception {
		gotoProfilePage();
		profilePage.clickReviewTab();
		
		String reviewCount = profilePage.getReviewCount();

		if (reviewCount.equalsIgnoreCase("0")) {
			assertTrue(profilePage.isEmptyReviewDisplayed(), "Review tab is not displayed", "Review tab is displayed");
		} else {
			assertTrue(profilePage.isReviewTabActive(), "Review tab is not displayed", "Review tab is displayed");
		}
	}
	
	@Test
	public void FRAN_3395_Verify_user_can_view_posts_in_review_tab() throws Exception {
		everContentAPI_1.createVideoContentForBusiness(Constant.BusinessIds.LOCAL_BUSINESS_ID);
		
		gotoProfilePage();
		profilePage.clickReviewTab();
		assertTrue(profilePage.isReviewTabActive(), "Review tab is not displayed", "Review tab is displayed");

		profilePage.clickReviewInGrid();
		assertTrue(profilePage.getListReviewInGridCount() > 0, "User cannot view reviews in grid (grid count = 0)",
				"User view reviews in grid (grid count > 0)");

		profilePage.clickReviewInFeed();
		assertTrue(profilePage.getListReviewInFeedCount() > 0, "User cannot view reviews in feed (feed count = 0)",
				"User view reviews in feed (feed count > 0)");
	}

	@Test
	public void FRAN_3396_Verify_user_can_view_empty_review_tab() throws Exception {
		everContentAPI_1.archiveAllUserContent();

		// go to profile page, review tab to see empty view
		gotoProfilePage();
		profilePage.clickReviewTab();
		assertTrue(profilePage.isEmptyReviewDisplayed(), "Empty view does not displayed",
				"Empty view displayed correctly");
	}

	@Test
	public void FRAN_3397_Verify_user_can_view_empty_following_in_following_tab() throws Exception {
		// setup
		userMonitoringAPI_1.unfollowAllUserAndProduct();

		// go to following tab
		gotoProfilePage();
		profilePage.clickFollowingTab();
		assertTrue(profilePage.isFollowingTabActive(), "Following Tab is not displayed",
				"Following tab is displayed correctly");
		assertEquals(profilePage.getFollowingCount(), "0", "Following count is displayed incorrectly",
				"Following count is displayed correctly");

		// verify empty in people tab
		profilePage.clickFollowingPeople();
		assertTrue(profilePage.isEmptyFollowingDisplayed(), "People tab does not should empty view",
				"Empty view is displayed correctly in People tab");

		// verify empty in product tab
		profilePage.clickFollowingProduct();
		assertTrue(profilePage.isEmptyFollowingDisplayed(), "Product tab does not should empty view",
				"Empty view is displayed correctly in Product tab");

	}

	@Test
	public void FRAN_3419_Verify_users_that_following_are_entered_when_tapping_from_following_tab() throws Exception {
		// setup
		userMonitoringAPI_1.unfollowAllUserAndProduct();
		// let's main account follow atleast one other user
		userMonitoringAPI_1.createMonitoringRequest(user_2.getUserId());

		// go to following tab, verify following account
		gotoProfilePage();
		profilePage.clickFollowingTab();
		assertTrue(profilePage.isFollowingTabActive(), "Following Tab is not displayed",
				"Following tab is displayed correctly");
		assertEquals(profilePage.getFollowingCount(), "1", "Following count is displayed incorrectly",
				"Following count is displayed correctly");

		// select tab people (make sure even it's default)
		profilePage.clickFollowingPeople();

		// verify list follow displayed = 1, displayed is correct
		assertEquals(profilePage.getCountListUserInTab(), 1, "Number of user following are incorrectly",
				"Number of user following are correctly");
		// need update this as user 2 displayName
		assertEquals(profilePage.getUserDisplayedNameInListByIndex(0),
				user_2.getDisplayName(),
				"Displayed name of following is incorrectly", "Displayed name of following is correctly");

		// select first user following in list
		profilePage.clickUserInListByIndex(0);

		OtherUserProfilePage otherProfile = new OtherUserProfilePage(driver);
		assertTrue(otherProfile.isActive(),
				"Following user's profile is not displayed", "Following user's profile is displayed");
		assertEquals(otherProfile.getFullName(), user_2.getFullName(),
				"Following user's displayed name is incorrectly", "Following user's displayed name is correctly");
	}

	@Test
	public void FRAN_3420_Verify_user_can_see_all_following_listed_in_following_tab() throws Exception {
		// setu

		// let's main account follow atleast one user and group
		// there's issues when follow a place -> ignore
		userMonitoringAPI_1.createMonitoringRequest(user_2.getUserId());
		userMonitoringAPI_1.associateWithGroup(corporateGroupAPI.getCorporateGroupIDInSearchListByIndex(0));

		// go to following tab, verify following account
		gotoProfilePage();
		profilePage.clickFollowingTab();
		assertTrue(profilePage.isFollowingTabActive(), "Following Tab is not displayed",
				"Following tab is displayed correctly");

		// select tab people (make sure even it's default)
		profilePage.clickFollowingPeople();
		// verify list user follow is displayed
		assertTrue(profilePage.getCountListUserInTab() > 0, "List of user following is not displayed",
				"List of following user is displayed (number of record > 0)");

		// verify the first record is correct
		String firstUserDisplayName = userMonitoringAPI_1.getUserFollowingInfor(0, "DisplayName");
		assertEquals(profilePage.getUserDisplayedNameInListByIndex(0), firstUserDisplayName,
				"First user in list following people is incorrectly",
				"First user in list following people is correctly");

		// select tab Product
		profilePage.clickFollowingProduct();
		String firstFollowingProductDisplayName = userMonitoringAPI_1.getFirstFollowingProductData("DisplayName");
		assertEquals(profilePage.getUserDisplayedNameInListByIndex(0), firstFollowingProductDisplayName,
				"First following product in list following product is incorrectly",
				"First following product in list following product is correctly");

		// select tab Place
		profilePage.clickFollowingPlace();
		String firstFollowingPlaceDisplayName = userMonitoringAPI_1.getFirstFollowingPlaceData("DisplayName");
		// firstPlace may be null cause no initiation
		if (firstFollowingPlaceDisplayName == null) {
			assertTrue(profilePage.isEmptyFollowingDisplayed(),
					"Following place is null, empty view should be displayed",
					"Following place is null, empty view is displayed correctly");
		} else {
			assertEquals(profilePage.getUserDisplayedNameInListByIndex(0), firstFollowingPlaceDisplayName.toString(),
					"First following place in list following place is incorrectly",
					"First following place in list following place is correctly");
		}
	}

	@Test
	public void FRAN_3422_Verify_public_profile_is_entered_when_tapping_from_following_tab() throws Exception {
		// setup
		userMonitoringAPI_1.unfollowAllUserAndProduct();
		userMonitoringAPI_1.createMonitoringRequest(user_2.getUserId());

		// go to following tab, verify following account
		gotoProfilePage();
		profilePage.clickFollowingTab();
		assertTrue(profilePage.isFollowingTabActive(), "Following Tab is not displayed",
				"Following tab is displayed correctly");
		assertEquals(profilePage.getFollowingCount(), "1", "Following count is displayed incorrectly",
				"Following count is displayed correctly");

		// select tab people (make sure even it's default)
		profilePage.clickFollowingPeople();

		// verify list follow displayed = 1, displayed is correct
		assertEquals(profilePage.getCountListUserInTab(), 1, "Number of user following are incorrectly",
				"Number of user following are correctly");
		// need update this as user 2 displayName
		assertEquals(profilePage.getUserDisplayedNameInListByIndex(0),
				user_2.getDisplayName(),
				"Displayed name of following is incorrectly", "Displayed name of following is correctly");

		// select first user following in list
		profilePage.clickUserInListByIndex(0);

		OtherUserProfilePage otherProfile = new OtherUserProfilePage(driver);
		assertTrue(otherProfile.isActive(),
				"Following user's profile is not displayed", "Following user's profile is displayed");
		assertEquals(otherProfile.getFullName(), user_2.getFullName(),
				"Following user's displayed name is incorrectly", "Following user's displayed name is incorrectly");
	}

	@Test
	public void FRAN_3399_Verify_user_can_access_to_Follower_tab() throws Exception {
		gotoProfilePage();
		profilePage.clickFollowersTab();

		String numberOfFollower = profilePage.getFollowerCount();
		if (numberOfFollower.equalsIgnoreCase("0")) {
			assertTrue(profilePage.isEmptyFollowerDisplayed(), "Follower tab is not displayed",
					"Follower tab is displayed");
		} else {
			// verify list user follow is displayed
			assertTrue(profilePage.getCountListUserInTab() > 0, "List of user Follower is not displayed",
					"List of Follower user is displayed (number of record > 0)");
		}
	}

	@Test
	public void FRAN_3402_Verify_user_can_see_all_followers_listed_in_follower_tab() throws Exception {

		// let's atleast one user follow main user
		userMonitoringAPI_2.createMonitoringRequest(user_1.getUserId());

		// go to following tab, verify following account
		gotoProfilePage();
		profilePage.clickFollowersTab();

		// verify follow count is correct
		assertEquals(profilePage.getFollowerCount(), String.valueOf(userMonitoringAPI_1.getUserFollowerCount()),
				"Follower count is displayed incorrectly", "Follower count is displayed correctly");

		// verify list user follow is displayed
		assertTrue(profilePage.getCountListUserInTab() > 0, "List of user Follower is not displayed",
				"List of Follower user is displayed (number of record > 0)");

		// verify the first record is correct
		String firstUserDisplayName = userMonitoringAPI_1.getUserFollowerInfor(0, "DisplayName");
		assertEquals(profilePage.getUserDisplayedNameInListByIndex(0), firstUserDisplayName,
				"First user in list following people is incorrectly",
				"First user in list following people is correctly");
	}

	@Test
	public void FRAN_3404_Verify_public_proifle_entered_when_tapping_from_follower_tab() throws Exception {

		// let's atleast one user follow main user
		userMonitoringAPI_2.createMonitoringRequest(user_1.getUserId());

		// go to following tab, verify following account
		gotoProfilePage();
		profilePage.clickFollowersTab();

		// verify list user follow is displayed
		assertTrue(profilePage.getCountListUserInTab() > 0, "List of user Follower is not displayed",
				"List of Follower user is displayed (number of record > 0)");

		// scroll to find and click to user 2
		profilePage.clickFollowerUserByDisplayName(user_2.getDisplayName());

		OtherUserProfilePage otherProfile = new OtherUserProfilePage(driver);
		assertTrue(otherProfile.isActive(),
				"Follower user's profile is not displayed", "Follower user's profile is displayed");
		assertEquals(otherProfile.getFullName(), user_2.getFullName(),
				"Follower user's displayed name is incorrectly", "Follower user's displayed name is incorrectly");
	}

	// Need setup no follower -> how
	// @Test
	// public void FRAN_3418_Verify_user_can_view_empty_screen_when_no_follower()
	// throws Exception {
	// gotoProfilePage();
	// profilePage.clickFollowersTab();
	// assertTrue(profilePage.isReviewTabActive(), "Follower tab is not displayed",
	// "Follower tab is displayed");
	// }
}
