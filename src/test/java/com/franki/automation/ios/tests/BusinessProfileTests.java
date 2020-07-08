package com.franki.automation.ios.tests;

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
import com.franki.automation.ios.pages.business.BusinessProfilePage;
import com.franki.automation.ios.pages.business.BusinessRatingsPage;
import com.franki.automation.ios.pages.business.BusinessVideosPage;
import com.franki.automation.ios.pages.business.BusinessAttributesPage;
import com.franki.automation.ios.pages.business.BusinessBookPage;
import com.franki.automation.ios.pages.business.BusinessFollowersPage;
import com.franki.automation.ios.pages.business.BusinessGigsPage;
import com.franki.automation.ios.pages.home.HomePage;
import com.franki.automation.ios.pages.login.LoginWithUsernamePage;
import com.franki.automation.ios.pages.recording.CreateOrEditReviewPage;
import com.franki.automation.ios.pages.recording.RecordingPage;
import com.franki.automation.ios.pages.search.HomeSearchPage;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class BusinessProfileTests extends MobileTestSetup {

	LoginWithUsernamePage loginPage;
	HomePage homePage;
	HomeSearchPage searchPage;

	UserData user;

	@DataProvider(name = "BusinessIds")
	public Object[][] generateBusinessIds() {
		return new Object[][] { { Constant.BusinessIds.LOCAL_BUSINESS_ID }, { Constant.BusinessIds.GOOGLE_BUSINESS_ID }};
	}

	@BeforeClass
	public void setupAPI() throws Exception {
		user = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
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
	public void FRAN_3460_Verify_user_able_create_review_from_business_profile(int businessId) throws Exception {
		
		/****************** Setup ***************************/
		BusinessData businessDataOnAPI = new BusinessAPI(user).getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		/****************** Go to Business Profile ***************************/
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
		assertTrue(businessProfilePage.isActive(), "You're not in the Business Profile page", "You're in the Business Profile page");

		/****************** Create review ***************************/
		
		RecordingPage recordingPage = businessProfilePage.clickBeFrankFromBusiness();
		assertTrue(recordingPage.isActive(), "User is not in Recording page", "You're in Recording page");

		CreateOrEditReviewPage createReviewPage = recordingPage.recordVideo(10).preview().donePreview();

		String caption = "create review" + " #abc" + System.currentTimeMillis();
		String[] ambiences = { CreateOrEditReviewPage.AMBIENCES.QUIET };

		boolean isPostReviewSuccessfully = createReviewPage.setCaption(caption).doneCaption()
				.selectOccasionTypes(CreateOrEditReviewPage.OCCASION_TYPE.YES, CreateOrEditReviewPage.OCCASION_TYPE.YES,
						CreateOrEditReviewPage.OCCASION_TYPE.YES)
				.selectAttributes(CreateOrEditReviewPage.ATTRIBUTE_TYPE.CHEAP,
						CreateOrEditReviewPage.ATTRIBUTE_TYPE.YES, CreateOrEditReviewPage.ATTRIBUTE_TYPE.YES,
						CreateOrEditReviewPage.ATTRIBUTE_TYPE.YES)
				.selectRatings(CreateOrEditReviewPage.RATING_TYPE.SATISFY, CreateOrEditReviewPage.RATING_TYPE.SATISFY,
						CreateOrEditReviewPage.RATING_TYPE.SATISFY, CreateOrEditReviewPage.RATING_TYPE.SATISFY)
				.selectAmbience(ambiences).submit().postReviewSuccessfully();

		assertTrue(isPostReviewSuccessfully, "Post the review failed", "Upload successfully");
	}

	@Test(dataProvider = "BusinessIds")
	public void FRAN_3459_Verify_user_able_to_unfollow_business(int businessId) throws Exception {
		BusinessAPI businessAPI = new BusinessAPI(user);
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
	public void FRAN_5602_Verify_ratings_displayed_on_ratings_tab(int businessId) throws Exception {
		BusinessAPI businessAPI = new BusinessAPI(user);
		// Setup
		BusinessData businessDataOnAPI = businessAPI.getBusinessRatingStats(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

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

		// On Ratings tab
		BusinessRatingsPage ratingPage = businessProfilePage.clickOnRatingsTab();
		assertTrue(ratingPage.isOnBusinessRatingsPage(), "You're not on the Ratings tab", "You're on the Ratings tab");
		assertEquals(ratingPage.getRatingsCount(), businessDataOnAPI.getRatingCount(),
				"Ratings count displays NOT correctly", "Ratings count displays correctly");

		// Check Rating percentage
		if (ratingPage.getRatingsCount() > 0) {
			// Food
			assertEquals(ratingPage.getFoodRatingPercent(), businessDataOnAPI.getRatings().getFoodRating(),
					"Food Rating Percentage is not correct", "Food Rating Percentage is not correct");

			// Drinks
			assertEquals(ratingPage.getDrinksRatingPercent(), businessDataOnAPI.getRatings().getDrinksRating(),
					"Drinks Rating Percentage is not correct", "Drinks Rating Percentage is not correct");

			// Service
			assertEquals(ratingPage.getServiceRatingPercent(), businessDataOnAPI.getRatings().getServiceRating(),
					"Service Rating Percentage is not correct", "Service Rating Percentage is not correct");

			// Overall
			assertEquals(ratingPage.getOverallRatingPercent(), businessDataOnAPI.getRatings().getOverallRating(),
					"Overall Rating Percentage is not correct", "Overall Rating Percentage is not correct");
		}

	}

	@Test(dataProvider = "BusinessIds")
	public void FRAN_5601_Verify_users_able_to_book_when_tap_book(int businessId) throws Exception {
		BusinessAPI businessAPI = new BusinessAPI(user);
		// Setup
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

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

	@Test(dataProvider = "BusinessIds")
	public void FRAN_5604_Verify_business_videos_on_videos_tab(int businessId) throws Exception {
		BusinessAPI businessAPI = new BusinessAPI(user);
		// Setup
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

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

		// On Videos tab
		BusinessVideosPage videosPage = businessProfilePage.clickOnVideosTab();
		assertTrue(videosPage.isOnBusinessVideosPage(), "You're not on the Videos tab", "You're on the Videos tab");
		assertEquals(videosPage.getVideosCount(), businessDataOnAPI.getVideoCount(),
				"Videos count displays NOT correctly", "Videos count displays correctly");
		Assert.assertTrue(videosPage.videoListDisplayed(), "The Videos list is not displayed");

	}

	@Test(dataProvider = "BusinessIds")
	public void FRAN_3455_Verify_followers_displayed_when_tap_on_followers_tab(int businessId) throws Exception {
		BusinessAPI businessAPI = new BusinessAPI(user);
		// Setup
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		// Get the followers from api
		ArrayList<UserData> followersOnAPI = businessAPI.getAllBusinessFollowers(businessId);

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

		// On Followers tab
		BusinessFollowersPage businessFollowersPage = businessProfilePage.clickOnFollowersTab();
		assertTrue(businessFollowersPage.isOnBusinessFollowsersPage(), "You're not on the Followers tab",
				"You're on the Followers tab");

		assertEquals(businessFollowersPage.getFollowersCount(), businessDataOnAPI.getFollowerCount(),
				"Followers count displays NOT correctly", "Followers count displays correctly");
		assertTrue(compareFollowersList(followersOnAPI, businessFollowersPage.getAllBusinessFollowers()),
				"Followers displaynames don't match", "Followers displaynames match");
	}

	@Test(dataProvider = "BusinessIds")
	public void FRAN_5606_Verify_user_can_follow_unfollow_user_on_followers_tab(int businessId) throws Exception {

		/********** Setup *****************************/
		// Get business info based on the businessId
		BusinessAPI businessAPI = new BusinessAPI(user);
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		// Setup make sure the business has at least one follower
		UserData clone = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_EMAIL_2,
				Constant.ACCOUNT_PASSWORD_2);
		new BusinessAPI(clone).followBusinessRequest(businessId);

		new MonitoringAPI(clone).unblockUserRequest(user.getUserId());
		new MonitoringAPI(user).unfollowUser(clone.getUserId());

		/************ Actions ****************************/

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

		// On Followers tab
		BusinessFollowersPage businessFollowersPage = businessProfilePage.clickOnFollowersTab();
		assertTrue(businessFollowersPage.isOnBusinessFollowsersPage(), "You're not on the Followers tab",
				"You're on the Followers tab");

		// Follow the target user
		businessFollowersPage.followUnfollowAnUserFromBusinessFollowersTab(clone.getDisplayName());
		assertTrue(businessFollowersPage.isMainUserFollowingBusinessFollower(clone.getDisplayName()),
				"UI verification: Follow user failed!", "UI verification: Follow user successfully");
		assertTrue(new MonitoringAPI(user).isFollowingUser(clone.getUserId()),
				"API verification: Follow user failed!", "API verification: Follow user successfully");

		// UnFollow the target user
		businessFollowersPage.followUnfollowAnUserFromBusinessFollowersTab(clone.getDisplayName());
		assertFalse(businessFollowersPage.isMainUserFollowingBusinessFollower(clone.getDisplayName()),
				"UI verification: UnFollow user failed!", "UI verification: UnFollow user successfully");
		assertFalse(new MonitoringAPI(user).isFollowingUser(clone.getUserId()),
				"API verification: UnFollow user failed!", "API verification: UnFollow user successfully");

	}

	@Test(dataProvider = "BusinessIds")
	public void FRAN_3453_Verify_business_profile_info(int businessId) throws Exception {
		BusinessAPI businessAPI = new BusinessAPI(user);
		// Setup
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

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

		// Verify Business Info
		assertEquals(businessProfilePage.getBusinessDisplayName(), businessDataOnAPI.getDisplayName(),
				"The Business's Display Name appears incorrectly", "The Business's Display Name appears correctly");
		assertEquals(businessProfilePage.getBusinessAddress(), businessDataOnAPI.getAddress(),
				"The Business's Address appears incorrectly", "The Business's Address appears correctly");
	}

	@Test(dataProvider = "BusinessIds")
	public void FRAN_5603_Verify_attribute_displayed_on_attributes_tab(int businessId) throws Exception {
		BusinessAPI businessAPI = new BusinessAPI(user);
		// Setup
		BusinessData businessDataOnAPI = businessAPI.getBusinessAttributionStats(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

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

		// On Attributes tab
		BusinessAttributesPage attributePage = businessProfilePage.clickOnAttributionTab();
		assertTrue(attributePage.isOnBusinessAttributesPage(), "You're not on the Attributes tab",
				"You're on the Attributes tab");

		/************ Verifications *********************/

		// Friends
		assertEquals(attributePage.getOccasionFriendsPercentage(),
				businessDataOnAPI.getAttributions().getOccasionFriendsPercentage(),
				"Occasion Friends Percentage is not correct", "Occasion Friends Percentage is not correct");

		// Family
		assertEquals(attributePage.getOccasionFamilyPercentage(),
				businessDataOnAPI.getAttributions().getOccasionFamilyPercentage(),
				"Occasion Family Percentage is not correct", "Occasion Family Percentage is not correct");

		// Date
		assertEquals(attributePage.getOccasionDatePercentage(),
				businessDataOnAPI.getAttributions().getOccasionDatePercentage(),
				"Occasion Date Percentage is not correct", "Occasion Date Percentage is not correct");

		// Noisy
		assertEquals(attributePage.getAmbienceNoisyPercentage(), businessDataOnAPI.getAttributions().getAmbienceNoisy(),
				"Ambience Noisy Percentage is not correct", "Ambience Noisy Percentage is not correct");

		// Casual
		assertEquals(attributePage.getAmbienceCausalPercentage(),
				businessDataOnAPI.getAttributions().getAmbienceCasual(), "Ambience Casual Percentage is not correct",
				"Ambience Casual Percentage is not correct");

		// Quiet
		assertEquals(attributePage.getAmbienceQuietPercentage(), businessDataOnAPI.getAttributions().getAmbienceQuiet(),
				"Ambience Quiet Percentage is not correct", "Ambience Quiet Percentage is not correct");

		// Classy
		assertEquals(attributePage.getAmbienceClassyPercentage(),
				businessDataOnAPI.getAttributions().getAmbienceClassy(), "Ambience Classy Percentage is not correct",
				"Ambience Classy Percentage is not correct");

		// Trendy
		assertEquals(attributePage.getAmbienceTrendyPercentage(),
				businessDataOnAPI.getAttributions().getAmbienceTrendy(), "Ambience Trendy Percentage is not correct",
				"Ambience Trendy Percentage is not correct");

		// Touristy
		assertEquals(attributePage.getAmbienceTouristyPercentage(),
				businessDataOnAPI.getAttributions().getAmbienceTouristy(),
				"Ambience Touristy Percentage is not correct", "Ambience Touristy Percentage is not correct");

		// Intimate
		assertEquals(attributePage.getAmbienceIntimatePercentage(),
				businessDataOnAPI.getAttributions().getAmbienceIntimate(),
				"Ambience Intimate Percentage is not correct", "Ambience Intimate Percentage is not correct");

		// Dark
		assertEquals(attributePage.getAmbienceDarkPercentage(), businessDataOnAPI.getAttributions().getAmbienceDark(),
				"Ambience Dark Percentage is not correct", "Ambience Dark Percentage is not correct");

		// Well-lit
		assertEquals(attributePage.getAmbienceWellLitPercentage(),
				businessDataOnAPI.getAttributions().getAmbienceWelllit(), "Ambience Well-lit Percentage is not correct",
				"Ambience Well-lit Percentage is not correct");

		// Friendly
		assertEquals(attributePage.getAmbienceFriendlyPercentage(),
				businessDataOnAPI.getAttributions().getAmbienceFriendly(),
				"Ambience Friendly Percentage is not correct", "Ambience Friendly Percentage is not correct");
	}
	
	@Test(dataProvider = "BusinessIds")
	public void FRAN_3456_Verify_business_gigs_on_gigs_tab(int businessId) throws Exception {
		
		/************ Setup *********************/
		ArrayList<GigData> businessGigData = new GigsAPI(user).getAllActiveGigsForBusiness(businessId);
		BusinessData businessDataOnAPI = new BusinessAPI(user).getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		/************ Actions *********************/
		
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

		/************ Verifications *********************/
		// On Gigs tab
		BusinessGigsPage gigsPage = businessProfilePage.clickOnGigsTab();
		assertTrue(gigsPage.isOnBusinessGigsPage(), "You're not on the Gigs tab", "You're on the Gigs tab");
		assertEquals(gigsPage.getActiveGigsCount(), businessGigData.size(),
				"Active Gigs count displays incorrectly", "Active Gigs count displays correctly");

	}
}
