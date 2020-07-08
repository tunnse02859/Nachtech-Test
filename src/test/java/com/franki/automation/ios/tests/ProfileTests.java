package com.franki.automation.ios.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.franki.automation.utility.Assertion.*;

import java.util.ArrayList;

import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.BusinessAPI;
import com.franki.automation.api.EverContentAPI;
import com.franki.automation.api.MonitoringAPI;
import com.franki.automation.datamodel.BusinessData;
import com.franki.automation.datamodel.ContentData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.ios.pages.home.BottomBarPage;
import com.franki.automation.ios.pages.login.LoginWithUsernamePage;
import com.franki.automation.ios.pages.profile.CurrentUserProfilePage;
import com.franki.automation.ios.pages.profile.OtherUserProfilePage;
import com.franki.automation.ios.pages.review.ReviewDetailPage;
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
		BottomBarPage bottomBarPage = new BottomBarPage(driver);
		assertTrue(bottomBarPage.isActive(), "User is not in Homepage", "You're in Home page");
		// click profile tab here
		bottomBarPage.clickOnProfileTab();
		profilePage = new CurrentUserProfilePage(driver);
		assertTrue(profilePage.isActive(), "Profile page is not displayed", "Profile page is displayed");
	}
	
	public boolean compareUserList(ArrayList<UserData> list1, ArrayList<UserData> list2) {
		if(list1.size() != list2.size()) {
			return false;
		}
		
		if(list1.size() == list2.size() && list2.size() == 0) {
			return true;
		}
		
		for (int i = 0; i < list1.size(); i++) {
			UserData item1 = list1.get(i);
			
			if(! list2.stream().anyMatch(item2 -> item2.getDisplayName().equals(item1.getDisplayName()) && item2.getFullName().equals(item1.getFullName()))) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean compareBusinessList(ArrayList<BusinessData> list1, ArrayList<BusinessData> list2) {
		if(list1.size() != list2.size()) {
			return false;
		}
		
		if(list1.size() == list2.size() && list2.size() == 0) {
			return true;
		}
		
		for (int i = 0; i < list1.size(); i++) {
			BusinessData item1 = list1.get(i);
			
			if(! list2.stream().anyMatch(item2 -> item2.getDisplayName().equals(item1.getDisplayName()) && item2.getAddress().equals(item1.getAddress()))) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean compareSavedContentList(ArrayList<ContentData> list1, ArrayList<ContentData> list2) {
		if(list1.size() != list2.size()) {
			return false;
		}
		
		if(list1.size() == list2.size() && list2.size() == 0) {
			return true;
		}
		
		for (int i = 0; i < list1.size(); i++) {
			ContentData item1 = list1.get(i);
			
			if(! list2.stream().anyMatch(item2 -> item2.getCorporateGroup().getBusinessType().equals(item1.getCorporateGroup().getBusinessType()) 
					&& item2.getCorporateGroup().getDisplayName().equals(item1.getCorporateGroup().getDisplayName())
							&& item2.getPostBody().equals(item1.getPostBody()))) {
				return false;
			}
		}
		
		return true;
	}
	

	@Test
	public void FRAN_3393_Verify_user_can_access_to_Profile_screen() throws Exception {
		gotoProfilePage();

		// verify full name
		assertEquals(profilePage.getFullName(), user_1.getFullName(), "Full Name displayed incorrectly",
				"Full Name displayed correctly");
		// verify full name
		assertEquals(profilePage.getDisplayName(), user_1.getDisplayName(), "Display Name displayed incorrectly",
				"Display Name displayed correctly");
	}

	@Test
	public void FRAN_3394_Verify_user_can_access_to_Reviews_tab() throws Exception {
		gotoProfilePage();
		profilePage.clickOnReviewsTab();
		assertTrue(profilePage.isOnReviewsTab(), "Review tab is not displayed", "Review tab is displayed");
	}


	@Test
	public void FRAN_3396_Verify_user_can_view_empty_review_tab() throws Exception {
		everContentAPI_1.archiveAllUserContent();

		// go to profile page, review tab to see empty view
		gotoProfilePage();
		profilePage.clickOnReviewsTab();
		assertTrue(profilePage.isOnReviewsTab(), "Review tab is not displayed", "Review tab is displayed");
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

		// verify empty in people tab
		profilePage.clickOnUserFollowingsTab();
		assertTrue(profilePage.isUserFollowingTabActive(), "People tab does not should empty view",
				"Empty view is displayed correctly in People tab");

		// verify empty in product tab
		profilePage.clickOnBusinessFollowingsTab();
		assertTrue(profilePage.isBusinessFollowingTabActive(), "Product tab does not should empty view",
				"Empty view is displayed correctly in Product tab");

	}

	@Test
	public void FRAN_3419_Verify_users_that_following_are_entered_when_tapping_from_following_tab() throws Exception {
		// setup
		userMonitoringAPI_1.unfollowAllUserAndProduct();
		userMonitoringAPI_1.unblockUserRequest(user_2.getUserId());
		// let's main account follow atleast one other user
		userMonitoringAPI_1.followUser(user_2.getUserId());
		

		// go to following tab, verify following account
		gotoProfilePage();
		profilePage.clickFollowingTab();
		assertTrue(profilePage.isFollowingTabActive(), "Following Tab is not displayed",
				"Following tab is displayed correctly");

		// The following users tab is active as default
		assertTrue(profilePage.isUserFollowingTabActive(), "Following Users Tab is not displayed as default",
				"Following Users tab is displayed as default");

		// verify list follow displayed = 1, displayed is correct
		assertEquals(profilePage.getFollowingUsersNumber(), 1, "Number of user following are incorrectly",
				"Number of user following are correctly");

		// Check the user following info
		assertEquals(profilePage.getDisplayNameOfUserFollowingsByIndex(0), user_2.getDisplayName(),
				"Displayed name of following is incorrectly", "Displayed name of following is correctly");

		assertEquals(profilePage.getFullNameOfUserFollowingsByIndex(0), user_2.getFullName(),
				"Displayed name of following is incorrectly", "Displayed name of following is correctly");

	}

	@Test
	public void FRAN_3420_Verify_user_can_see_all_following_listed_in_following_tab() throws Exception {
		// let's main account follow at least one user and group
		// there's issues when follow a place -> ignore
		userMonitoringAPI_1.followUser(user_2.getUserId());
		userMonitoringAPI_1.associateWithGroup(corporateGroupAPI.getCorporateGroupIDInSearchListByIndex(0));

		// go to following tab, verify following account
		gotoProfilePage();
		profilePage.clickFollowingTab();
		assertTrue(profilePage.isFollowingTabActive(), "Following Tab is not displayed",
				"Following tab is displayed correctly");

		// select tab people (make sure even it's default)
		profilePage.clickOnUserFollowingsTab();
		// verify list user follow is displayed
		ArrayList<UserData> userListOnUI = profilePage.getAllUsersFollowing();
		ArrayList<UserData> userListOnAPI = userMonitoringAPI_1.getAllUsersFollowing();

		assertTrue(compareUserList(userListOnUI, userListOnAPI), "User Followings doesn't match", "User Followings match");

		// select tab Product
		profilePage.clickOnBusinessFollowingsTab();
		assertTrue(profilePage.isBusinessFollowingTabActive(), "You're not on the Business Followings tab",
				"You're on the Business Followings tab");

		// verify list business followings are displayed
		ArrayList<BusinessData> businessListOnUI = profilePage.getAllBusinessFollowing();
		ArrayList<BusinessData> businessListOnAPI = userMonitoringAPI_1.getAllBusinessFollowing();

		assertTrue(compareBusinessList(businessListOnUI, businessListOnAPI), "Business Followings doesn't match", "Business Followings match");
	}

	@Test
	public void FRAN_3422_Verify_public_profile_is_entered_when_tapping_from_following_tab() throws Exception {
		// setup
		userMonitoringAPI_1.unfollowAllUserAndProduct();
		userMonitoringAPI_1.unblockUserRequest(user_2.getUserId());
		userMonitoringAPI_1.followUser(user_2.getUserId());

		// go to following tab, verify following account
		gotoProfilePage();
		profilePage.clickFollowingTab();
		assertTrue(profilePage.isFollowingTabActive(), "Following Tab is not displayed",
				"Following tab is displayed correctly");

		// select tab people (make sure even it's default)
		profilePage.clickOnUserFollowingsTab();
		assertTrue(profilePage.isUserFollowingTabActive(), "User Following Tab is not displayed",
				"User Following tab is displayed correctly");

		// select first user following in list
		profilePage.clickOnFollowingsByIndex(0);

		OtherUserProfilePage otherUserProfilePage = new OtherUserProfilePage(driver);

		assertTrue(otherUserProfilePage.isActive(), "Other User Profile page of other user is not displayed",
				"Other User Profile page of other user is displayed");
		assertEquals(otherUserProfilePage.getFullName(), user_2.getFullName(),
				"Following user's full name is incorrectly", "Following user's full name is correctly");
		assertEquals(otherUserProfilePage.getDisplayName(), user_2.getDisplayName(),
				"Following user's display name is incorrectly", "Following user's display name is correctly");
	}

	@Test
	public void FRAN_3399_Verify_user_can_access_to_Follower_tab() throws Exception {
		gotoProfilePage();
		profilePage.clickOnFollowersTab();
		// user is on followers tab
		assertTrue(profilePage.isOnFollowersTab(), "Followers tab is not displayed", "Followers tab is display");
	}

	@Test
	public void FRAN_3402_Verify_user_can_see_all_followers_listed_in_follower_tab() throws Exception {

		// Make sure the current user doesn't have followers
		userMonitoringAPI_1.blockAllFollowers();
		// let's atleast one user follow main user
		userMonitoringAPI_2.createMonitoringRequest(user_1.getUserId());

		gotoProfilePage();
		profilePage.clickOnFollowersTab();
		// user is on followers tab
		assertTrue(profilePage.isOnFollowersTab(), "Followers tab is not displayed", "Followers tab is display");

		// verify list followers are displayed
		ArrayList<UserData> userListOnUI = profilePage.getAllFollowers();
		ArrayList<UserData> userListOnAPI = userMonitoringAPI_1.getAllFollowers();

		assertTrue(compareUserList(userListOnUI, userListOnAPI), "Followers don't match", "Followers match");
	}

	@Test
	public void FRAN_3404_Verify_public_profile_entered_when_tapping_from_follower_tab() throws Exception {

		// let's atleast one user follow main user
		userMonitoringAPI_1.unblockUserRequest(user_2.getUserId());
		Assert.assertTrue(userMonitoringAPI_2.followUser(user_1.getUserId()), "Setup follower failed");

		gotoProfilePage();
		profilePage.clickOnFollowersTab();
		// user is on followers tab
		assertTrue(profilePage.isOnFollowersTab(), "Followers tab is not displayed", "Followers tab is display");

		// select first user following in list
		profilePage.clickOnFollowerByIndex(0);

		OtherUserProfilePage otherUserProfilePage = new OtherUserProfilePage(driver);

		assertTrue(otherUserProfilePage.isActive(), "Other User Profile page of other user is not displayed",
				"Other User Profile page of other user is displayed");
		assertEquals(otherUserProfilePage.getFullName(), user_2.getFullName(),
				"Following user's full name is incorrectly", "Following user's full name is correctly");
		assertEquals(otherUserProfilePage.getDisplayName(), user_2.getDisplayName(),
				"Following user's display name is incorrectly", "Following user's display name is correctly");
	}

	@Test
	public void FRAN_3418_Verify_user_can_view_empty_screen_when_no_follower() throws Exception {
		// Make sure the current user doesn't have followers
		userMonitoringAPI_1.blockAllFollowers();
		gotoProfilePage();
		profilePage.clickOnFollowersTab();
		assertTrue(profilePage.isNoFollowerDisplayed(), "No Follower page is not displayed", "No Follower page is displayed");
	}
	
	@Test
	public void FRAN_3401_Verify_user_can_view_empty_screen_when_no_saved_content() throws Exception {
		// Make sure the current user doesn't have saved content
		everContentAPI_1.unfavoriteAllContents();
		gotoProfilePage();
		profilePage.clickOnSavedTab();
		assertTrue(profilePage.isOnSavedTab(), "You're not in Saved tab", "You're in Saved tab");
		assertTrue(profilePage.isNoSavedContent(), "No Saved content page is not displayed", "No Saved content page is displayed");
	}
	
	@Test
	public void FRAN_3398_Verify_user_can_go_to_saved_tab() throws Exception {
		gotoProfilePage();
		profilePage.clickOnSavedTab();
		assertTrue(profilePage.isOnSavedTab(), "You're not in Saved tab", "You're in Saved tab");
	}
	
	@Test
	public void FRAN_3400_Verify_user_can_see_all_saved_posts() throws Exception {
		// Setup saved contents
		Assert.assertTrue(everContentAPI_1.favoriteContent(), "Saved content failed");
		ArrayList<ContentData> savedContentOnAPI = everContentAPI_1.getUserFavoritedEverContent();
		
		// On saved tab
		gotoProfilePage();
		profilePage.clickOnSavedTab();
		assertTrue(profilePage.isOnSavedTab(), "You're not in Saved tab", "You're in Saved tab");
		
		// Verify data
		ArrayList<ContentData> savedContentOnUI = profilePage.getAllSavedContents();
		assertTrue(compareSavedContentList(savedContentOnUI, savedContentOnAPI), "Saved Contents don't match", "Saved Contents match");

	}
	
	 @Test
	 public void FRAN_3395_Verify_user_can_view_posts_in_review_tab() throws Exception {
		 /************* Setup *******************/
		 // Remove all user's content
		 everContentAPI_1.archiveAllUserContent();
		 // Create a new content
		 everContentAPI_1.createVideoContentForBusiness(Constant.BusinessIds.LOCAL_BUSINESS_ID);
		 // Get user's review list
		 ArrayList<ContentData> userContentList = everContentAPI_1.getUserContents();
		 
		 /************* Actions *******************/
		 gotoProfilePage();
		 profilePage.clickOnReviewsTab();
		 assertTrue(profilePage.isOnReviewsTab(), "Review tab is not displayed", "Review tab is displayed");
		 
		 // The review's count displays on Video tab
		 assertEquals(profilePage.getReviewsCountDisplayedOnTab(), userContentList.size(), "The video count displays incorrectly", "The video count displays correctly");
	 
		 // The list review count
		 assertEquals(profilePage.getListReviewSize(), userContentList.size(), "The video list size is incorrect", "The video list size is correct");
	 
	 }
}
