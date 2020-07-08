package com.franki.automation.ios.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.franki.automation.utility.Assertion.*;

import java.util.ArrayList;

import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.BusinessAPI;
import com.franki.automation.api.EverContentAPI;
import com.franki.automation.api.GigsAPI;
import com.franki.automation.api.MonitoringAPI;
import com.franki.automation.api.NominationAPI;
import com.franki.automation.api.NotificationAPI;
import com.franki.automation.datamodel.ContentData;
import com.franki.automation.datamodel.GigData;
import com.franki.automation.datamodel.NotificationData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.ios.pages.home.HomePage;
import com.franki.automation.ios.pages.login.LoginWithUsernamePage;
import com.franki.automation.ios.pages.notifications.NotificationPage;
import com.franki.automation.report.Log;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class NotificationTests extends MobileTestSetup {

	private LoginWithUsernamePage loginPage;
	private HomePage homePage;
	private UserData user;
	private UserData cloneUser;
	private UserData admin;
	private ContentData content;

	@BeforeClass
	public void setupAPI() throws Exception {
		user = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		cloneUser = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_EMAIL_2, Constant.ACCOUNT_PASSWORD_2);
		admin = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_ADMIN, Constant.ACCOUNT_PASSWORD_ADMIN);
	}

	@AfterClass
	public void clearDataTest() throws Exception {
		try {
			if (this.content != null) {
				new EverContentAPI(user).archiveContentRequest(content.getContentKey());
			}
		} catch (Exception e) {
			Log.info(e.getStackTrace().toString());
		}

	}

	@Test
	public void FRAN_3452_Verify_notification_when_a_gig_around_current_location() throws Exception {

		/********************* Setup ****************************/
		// Make sure the user doesn't follow the business
		// Make sure the business is around your location
		new BusinessAPI(user).unfollowBusinessRequest(Constant.BusinessIds.LOCAL_BUSINESS_ID);

		// Admin create a gig for the business
		int gigId = new GigsAPI(admin).adminCreateActiveGig(Constant.BusinessIds.LOCAL_BUSINESS_ID);

		try {
			/********************* Actions ****************************/
			// Login and goto Home
			loginPage = new LoginWithUsernamePage(driver);
			loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
			homePage = new HomePage(driver);
			assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");

			// Get the notifications from API
			NotificationAPI notificationAPI = new NotificationAPI(user);
			ArrayList<NotificationData> notificationListFromAPI = notificationAPI.getNotificationList(100);

			// Go to Notification screen
			NotificationPage notificationPage = homePage.clickOnNotificationIcon();
			assertTrue(notificationPage.isActive(), "Notifications screen is not displayed",
					"Notifications screen is displayed");

			/********************* Verify ****************************/
			NotificationData firstNotificationFromUI = notificationPage.getNotification(0);
			// The first notification is the newest, it have to match with api
			assertEquals(firstNotificationFromUI.getMessage(), notificationListFromAPI.get(0).getMessage(),
					"The notification message is incorrect", "The notification message is correct");

			assertTrue(
					firstNotificationFromUI.getRelativeTime().contains("seconds ago")
							|| firstNotificationFromUI.getRelativeTime().contains("minute ago"),
					"The notification time is incorrect", "The notification time is correct");
		} finally {

			// Delete the gig after testing
			if (gigId > 0) {
				new GigsAPI(admin).deleteGig(gigId);
			}
		}

	}

	@Test
	public void FRAN_3448_Verify_notification_when_a_gig_starts_from_following_business() throws Exception {

		/********************* Setup ****************************/
		// Make sure the user doesn't follow the business
		// Make sure the business is around your location
		new BusinessAPI(user).followBusinessRequest(Constant.BusinessIds.LOCAL_BUSINESS_ID);

		// Admin create a gig for the business
		int gigId = new GigsAPI(admin).adminCreateActiveGig(Constant.BusinessIds.LOCAL_BUSINESS_ID);

		try {
			/********************* Actions ****************************/
			// Login and goto Home
			loginPage = new LoginWithUsernamePage(driver);
			loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
			homePage = new HomePage(driver);
			assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");

			// Get the notifications from API
			NotificationAPI notificationAPI = new NotificationAPI(user);
			ArrayList<NotificationData> notificationListFromAPI = notificationAPI.getNotificationList(100);

			// Go to Notification screen
			NotificationPage notificationPage = homePage.clickOnNotificationIcon();
			assertTrue(notificationPage.isActive(), "Notifications screen is not displayed",
					"Notifications screen is displayed");

			/********************* Verify ****************************/
			NotificationData firstNotificationFromUI = notificationPage.getNotification(0);
			// The first notification is the newest, it have to match with api
			assertEquals(firstNotificationFromUI.getMessage(), notificationListFromAPI.get(0).getMessage(),
					"The notification message is incorrect", "The notification message is correct");

			assertTrue(
					firstNotificationFromUI.getRelativeTime().contains("seconds ago")
							|| firstNotificationFromUI.getRelativeTime().contains("minute ago"),
					"The notification time is incorrect", "The notification time is correct");
		} finally {

			// Delete the gig after testing
			if (gigId > 0) {
				new GigsAPI(admin).deleteGig(gigId);
			}
		}

	}

	@Test
	public void FRAN_3450_Verify_notification_when_a_user_start_following() throws Exception {

		/********************* Setup ****************************/
		// The clone account starts following the main user
		UserData cloneUser = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_EMAIL_2,
				Constant.ACCOUNT_PASSWORD_2);
		new MonitoringAPI(cloneUser).unfollowUser(user.getUserId());
		new MonitoringAPI(cloneUser).followUser(user.getUserId());

		/********************* Actions ****************************/
		// Login and goto Home
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");

		// Get the notifications from API
		NotificationAPI notificationAPI = new NotificationAPI(user);
		ArrayList<NotificationData> notificationListFromAPI = notificationAPI.getNotificationList(100);

		// Go to Notification screen
		NotificationPage notificationPage = homePage.clickOnNotificationIcon();
		assertTrue(notificationPage.isActive(), "Notifications screen is not displayed",
				"Notifications screen is displayed");

		/********************* Verify ****************************/
		NotificationData firstNotificationFromUI = notificationPage.getNotification(0);
		// The first notification is the newest, it have to match with api
		assertEquals(firstNotificationFromUI.getMessage(), notificationListFromAPI.get(0).getMessage(),
				"The notification message is incorrect", "The notification message is correct");

		assertTrue(
				firstNotificationFromUI.getRelativeTime().contains("seconds ago")
						|| firstNotificationFromUI.getRelativeTime().contains("minute ago"),
				"The notification time is incorrect", "The notification time is correct");
	}

	@Test
	public void FRAN_3446_VerifyAppShowsANotificationWhenCommentingAPost() throws Exception {

		/********************* Setup ****************************/
		if (this.content == null) {
			content = new EverContentAPI(user).createVideoContentForBusiness(Constant.BusinessIds.LOCAL_BUSINESS_ID);
		}
		boolean isCommentSuccessful = new EverContentAPI(cloneUser).createComment(content.getContentKey(), "test");
		assertTrue(isCommentSuccessful, "User 2 can't comment into the post: " + content.getContentKey(),
				"User 2 can comment into the post: " + content.getContentKey());

		/********************* Actions ****************************/
		// Login and goto Home
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");

		// Get the notifications from API
		NotificationAPI notificationAPI = new NotificationAPI(user);
		ArrayList<NotificationData> notificationListFromAPI = notificationAPI.getNotificationList(100);

		// Go to Notification screen
		NotificationPage notificationPage = homePage.clickOnNotificationIcon();
		assertTrue(notificationPage.isActive(), "Notifications screen is not displayed",
				"Notifications screen is displayed");

		/********************* Verify ****************************/
		NotificationData firstNotificationFromUI = notificationPage.getNotification(0);
		// The first notification is the newest, it have to match with api
		assertEquals(firstNotificationFromUI.getMessage(), notificationListFromAPI.get(0).getMessage(),
				"The notification message is incorrect", "The notification message is correct");

		assertTrue(
				firstNotificationFromUI.getRelativeTime().contains("seconds ago")
						|| firstNotificationFromUI.getRelativeTime().contains("minute ago"),
				"The notification time is incorrect", "The notification time is correct");
	}

	@Test
	public void FRAN_3445_VerifyAppShowsANotificationWhenLikeAPost() throws Exception {

		/********************* Setup ****************************/
		if (this.content == null) {
			content = new EverContentAPI(user).createVideoContentForBusiness(Constant.BusinessIds.LOCAL_BUSINESS_ID);
		}
		boolean isLikeSuccessful = new EverContentAPI(cloneUser).likeContent(content.getContentKey());
		assertTrue(isLikeSuccessful, "User 2 can't like the post: " + content.getContentKey(),
				"User 2 liked the post: " + content.getContentKey());

		/********************* Actions ****************************/
		// Login and goto Home
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");

		// Get the notifications from API
		NotificationAPI notificationAPI = new NotificationAPI(user);
		ArrayList<NotificationData> notificationListFromAPI = notificationAPI.getNotificationList(100);

		// Go to Notification screen
		NotificationPage notificationPage = homePage.clickOnNotificationIcon();
		assertTrue(notificationPage.isActive(), "Notifications screen is not displayed",
				"Notifications screen is displayed");

		/********************* Verify ****************************/
		NotificationData firstNotificationFromUI = notificationPage.getNotification(0);
		// The first notification is the newest, it have to match with api
		assertEquals(firstNotificationFromUI.getMessage(), notificationListFromAPI.get(0).getMessage(),
				"The notification message is incorrect", "The notification message is correct");

		assertTrue(
				firstNotificationFromUI.getRelativeTime().contains("seconds ago")
						|| firstNotificationFromUI.getRelativeTime().contains("minute ago"),
				"The notification time is incorrect", "The notification time is correct");
	}

	@Test
	public void FRAN_3443_Verify_notification_when_user_tagged_on_a_new_post() throws Exception {

		/********************* Setup ****************************/
		// Let's clone user create a new post and tag the main user
		ContentData content = new EverContentAPI(cloneUser).createVideoContentForBusiness(
				Constant.BusinessIds.LOCAL_BUSINESS_ID, user.getUserId(), user.getDisplayName());

		try {
			/********************* Actions ****************************/
			// Login and goto Home
			loginPage = new LoginWithUsernamePage(driver);
			loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
			homePage = new HomePage(driver);
			assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");

			// Get the notifications from API
			NotificationAPI notificationAPI = new NotificationAPI(user);
			ArrayList<NotificationData> notificationListFromAPI = notificationAPI.getNotificationList(100);

			// Go to Notification screen
			NotificationPage notificationPage = homePage.clickOnNotificationIcon();
			assertTrue(notificationPage.isActive(), "Notifications screen is not displayed",
					"Notifications screen is displayed");

			/********************* Verify ****************************/
			NotificationData firstNotificationFromUI = notificationPage.getNotification(0);
			// The first notification is the newest, it have to match with api
			assertEquals(firstNotificationFromUI.getMessage(), notificationListFromAPI.get(0).getMessage(),
					"The notification message is incorrect", "The notification message is correct");

			assertTrue(
					firstNotificationFromUI.getRelativeTime().contains("seconds ago")
							|| firstNotificationFromUI.getRelativeTime().contains("minute ago"),
					"The notification time is incorrect", "The notification time is correct");
		} finally {
			if(content != null) {
				new EverContentAPI(cloneUser).archiveAllUserContent();
			}
		}
	}
	
	
	@Test
	public void FRAN_3444_Verify_notification_when_user_tagged_on_a_post() throws Exception {

		/********************* Setup ****************************/
		// Let's clone user create a new post
		EverContentAPI everContentAPI = new EverContentAPI(cloneUser);
		ContentData content = everContentAPI.createVideoContentForBusiness(Constant.BusinessIds.LOCAL_BUSINESS_ID);
		
		// Then tag the main user to the created post
		everContentAPI.editEverContentToTagUser(content.getContentKey(), user.getUserId(), user.getDisplayName());

		try {
			/********************* Actions ****************************/
			// Login and goto Home
			loginPage = new LoginWithUsernamePage(driver);
			loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
			homePage = new HomePage(driver);
			assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");

			// Get the notifications from API
			NotificationAPI notificationAPI = new NotificationAPI(user);
			ArrayList<NotificationData> notificationListFromAPI = notificationAPI.getNotificationList(100);

			// Go to Notification screen
			NotificationPage notificationPage = homePage.clickOnNotificationIcon();
			assertTrue(notificationPage.isActive(), "Notifications screen is not displayed",
					"Notifications screen is displayed");

			/********************* Verify ****************************/
			NotificationData firstNotificationFromUI = notificationPage.getNotification(0);
			// The first notification is the newest, it have to match with api
			assertEquals(firstNotificationFromUI.getMessage(), notificationListFromAPI.get(0).getMessage(),
					"The notification message is incorrect", "The notification message is correct");

			assertTrue(
					firstNotificationFromUI.getRelativeTime().contains("seconds ago")
							|| firstNotificationFromUI.getRelativeTime().contains("minute ago"),
					"The notification time is incorrect", "The notification time is correct");
		} finally {
			if(content != null) {
				everContentAPI.archiveAllUserContent();
			}
		}
	}
	
	@Test
	public void FRAN_3447_Verify_notification_when_winning_a_gig() throws Exception {

		/********************* Setup ****************************/
		GigsAPI gigAPI = new GigsAPI(admin);
		EverContentAPI contentAPI = new EverContentAPI(user);
		NominationAPI nominateAPI = new NominationAPI(admin);
		
		// Admin create a gig for the business
		int gigId = gigAPI.adminCreateActiveGig(Constant.BusinessIds.LOCAL_BUSINESS_ID);
		GigData gigData = gigAPI.getGigDetail(gigId);
		
		// User create a video for the gig
		ContentData contentData = contentAPI.createVideoContentForBusiness(Constant.BusinessIds.LOCAL_BUSINESS_ID, gigId, "", "");
		
		// Nominate the content
		nominateAPI.nominateAContent(Constant.BusinessIds.LOCAL_BUSINESS_ID, gigData.getGigId(), gigData.getGigPrizes().get(0).getGigPrizeId(), user.getUserId(), contentData.getContentKey());
		
		// Asign a gig prize
		boolean isUserWinAGig = nominateAPI.assignGigPrizeForNominatation(gigData.getGigPrizes().get(0).getGigPrizeId(), contentData.getContentKey());
		assertTrue(isUserWinAGig, "Nominate content failed", "User wins a gig!!");
		
		/********************* Actions ****************************/
		// Login and goto Home
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");

		// Get the notifications from API
		NotificationAPI notificationAPI = new NotificationAPI(user);
		ArrayList<NotificationData> notificationListFromAPI = notificationAPI.getNotificationList(100);

		// Go to Notification screen
		NotificationPage notificationPage = homePage.clickOnNotificationIcon();
		assertTrue(notificationPage.isActive(), "Notifications screen is not displayed",
				"Notifications screen is displayed");

		/********************* Verify ****************************/
		NotificationData firstNotificationFromUI = notificationPage.getNotification(0);
		// The first notification is the newest, it have to match with api
		assertEquals(firstNotificationFromUI.getMessage(), notificationListFromAPI.get(0).getMessage(),
				"The notification message is incorrect", "The notification message is correct");

		assertTrue(
				firstNotificationFromUI.getRelativeTime().contains("seconds ago")
						|| firstNotificationFromUI.getRelativeTime().contains("minute ago"),
				"The notification time is incorrect", "The notification time is correct");

	}

}
