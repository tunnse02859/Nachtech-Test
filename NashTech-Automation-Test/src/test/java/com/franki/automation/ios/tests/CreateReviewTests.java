package com.franki.automation.ios.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.franki.automation.utility.Assertion.*;

import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.BusinessAPI;
import com.franki.automation.api.EverContentAPI;
import com.franki.automation.datamodel.BusinessData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.ios.pages.home.BottomBarPage;
import com.franki.automation.ios.pages.home.HomePage;
import com.franki.automation.ios.pages.login.LoginWithUsernamePage;
import com.franki.automation.ios.pages.profile.CurrentUserProfilePage;
import com.franki.automation.ios.pages.recording.CreateOrEditReviewPage;
import com.franki.automation.ios.pages.recording.RecordingPage;
import com.franki.automation.ios.pages.review.ReviewDetailPage;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;
import com.franki.automation.utility.Common;

public class CreateReviewTests extends MobileTestSetup {

	UserData user;

	@BeforeClass
	public void setupAPI() throws Exception {
		user = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
	}

	@DataProvider(name = "BusinessIds")
	public Object[][] generateBusinessIds() {
		return new Object[][] { { Constant.BusinessIds.GOOGLE_BUSINESS_ID },
				{ Constant.BusinessIds.LOCAL_BUSINESS_ID } };
	}

	private void login() throws Exception {
		LoginWithUsernamePage loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		HomePage homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

	}

	@Test(dataProvider = "BusinessIds")
	public void FRAN_5749_verify_user_create_review_with_single_recorded_videos_for_business(int businessId)
			throws Exception {

		/********** Setup - Get the business name from id *****************************/
		// Get business info based on the businessId
		BusinessAPI businessAPI = new BusinessAPI(user);
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		/******** Login then go to Create Review page ***************/
		login();
		BottomBarPage menuPage = new BottomBarPage(driver);
		RecordingPage recordingPage = menuPage.clickOnBeFrankTab();
		assertTrue(recordingPage.isActive(), "User is not in Recording page", "You're in Recording page");

		CreateOrEditReviewPage createReviewPage = recordingPage.recordVideo(10).preview().donePreview();

		String caption = "create review" + " #abc" + System.currentTimeMillis();
		String[] ambiences = { CreateOrEditReviewPage.AMBIENCES.QUIET };

		boolean isPostReviewSuccessfully = createReviewPage.selectBusiness(searchBusinessDisplayName)
				.setCaption(caption).doneCaption()
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
	public void FRAN_5750_verify_user_create_review_with_multiple_recorded_videos_for_business(int businessId)
			throws Exception {

		/********** Setup - Get the business name from id *****************************/
		// Get business info based on the businessId
		BusinessAPI businessAPI = new BusinessAPI(user);
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		/******** Login then go to Create Review page ***************/
		login();
		BottomBarPage menuPage = new BottomBarPage(driver);
		RecordingPage recordingPage = menuPage.clickOnBeFrankTab();
		assertTrue(recordingPage.isActive(), "User is not in Recording page", "You're in Recording page");

		CreateOrEditReviewPage createReviewPage = recordingPage.recordVideo(10).recordVideo(10).preview().donePreview();

		String caption = "create review" + System.currentTimeMillis();
		String[] ambiences = { CreateOrEditReviewPage.AMBIENCES.QUIET };

		boolean isPostReviewSuccessfully = createReviewPage.selectBusiness(searchBusinessDisplayName)
				.setCaption(caption).doneCaption()
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
	public void FRAN_5751_verify_user_create_review_with_single_video_from_gallery_for_business(int businessId)
			throws Exception {

		/********** Setup - Get the business name from id *****************************/
		// Get business info based on the businessId
		BusinessAPI businessAPI = new BusinessAPI(user);
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		/******** Login then go to Create Review page ***************/
		login();
		BottomBarPage menuPage = new BottomBarPage(driver);
		RecordingPage recordingPage = menuPage.clickOnBeFrankTab();
		assertTrue(recordingPage.isActive(), "User is not in Recording page", "You're in Recording page");

		CreateOrEditReviewPage createReviewPage = recordingPage.selectSingleVideoFromGallery().preview().donePreview();

		String caption = "create review" + System.currentTimeMillis();
		String[] ambiences = { CreateOrEditReviewPage.AMBIENCES.QUIET };

		boolean isPostReviewSuccessfully = createReviewPage.selectBusiness(searchBusinessDisplayName)
				.setCaption(caption).doneCaption()
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
	public void FRAN_5752_verify_user_create_review_with_multiple_videos_from_gallery_for_business(int businessId)
			throws Exception {

		/********** Setup - Get the business name from id *****************************/
		// Get business info based on the businessId
		BusinessAPI businessAPI = new BusinessAPI(user);
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		/******** Login then go to Create Review page ***************/
		login();
		BottomBarPage menuPage = new BottomBarPage(driver);
		RecordingPage recordingPage = menuPage.clickOnBeFrankTab();
		assertTrue(recordingPage.isActive(), "User is not in Recording page", "You're in Recording page");

		CreateOrEditReviewPage createReviewPage = recordingPage.selectSingleVideoFromGallery()
				.selectSingleVideoFromGallery().preview().donePreview();

		String caption = "create review" + System.currentTimeMillis();
		String[] ambiences = { CreateOrEditReviewPage.AMBIENCES.QUIET };

		boolean isPostReviewSuccessfully = createReviewPage.selectBusiness(searchBusinessDisplayName)
				.setCaption(caption).doneCaption()
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

	@Test
	public void FRAN_5753_verify_user_able_delete_recorded_selected_video() throws Exception {

		/******** Login then go to Create Review page ***************/
		login();
		BottomBarPage menuPage = new BottomBarPage(driver);
		RecordingPage recordingPage = menuPage.clickOnBeFrankTab();
		assertTrue(recordingPage.isActive(), "User is not in Recording page", "You're in Recording page");

		// Select then delete the selected video
		boolean isDeleteSelectedVideoSuccessfully = recordingPage.selectSingleVideoFromGallery()
				.deleteTheRecordedSelectedVideo().isPreviewButtonDisplayed();
		assertTrue(!isDeleteSelectedVideoSuccessfully, "Delete the selected video failed",
				"Delete the selected video successfully");

		// Record then delete the recorded video
		boolean isDeleteRecordedVideoSuccessfully = recordingPage.recordVideo(10).deleteTheRecordedSelectedVideo()
				.isPreviewButtonDisplayed();
		assertTrue(!isDeleteRecordedVideoSuccessfully, "Delete the recorded video failed",
				"Delete the recorded video successfully");
	}

	@Test
	public void FRAN_5754_verify_user_unable_create_review_when_missing_mandatory_fields() throws Exception {

		/********** Setup - Get the business name from id *****************************/
		// Get business info based on the businessId
		BusinessAPI businessAPI = new BusinessAPI(user);
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(Constant.BusinessIds.LOCAL_BUSINESS_ID);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		/******** Login then go to Create Review page ***************/
		login();
		BottomBarPage menuPage = new BottomBarPage(driver);
		RecordingPage recordingPage = menuPage.clickOnBeFrankTab();
		assertTrue(recordingPage.isActive(), "User is not in Recording page", "You're in Recording page");

		CreateOrEditReviewPage createReviewPage = recordingPage.recordVideo(10).preview().donePreview();

		/******** Missing Business ***************/

		String alertText = createReviewPage.submit().getTextAlert();
		assertContains(alertText, "You must select a business to tag", "Missing Business - FAILED - " + alertText,
				"Missing Business - PASSED - " + alertText);

		/******** Missing Attributes - Price ***************/

		alertText = createReviewPage.selectBusiness(searchBusinessDisplayName).submit().getTextAlert();
		
		assertContains(alertText, "Please answer all questions", "Missing Attributes - FAILED - " + alertText,
				"Missing Attributes - PASSED - " + alertText);

		/******** Missing Ratings ***************/
		alertText = createReviewPage.selectAttributes(CreateOrEditReviewPage.ATTRIBUTE_TYPE.CHEAP, "", "", "").submit().getTextAlert();
		
		assertContains(alertText, "Please answer all questions", "Missing Ratings - FAILED - " + alertText,
				"Missing Ratings - PASSED - " + alertText);

		/******** Missing Ratings ***************/
		alertText = createReviewPage.selectRatings(CreateOrEditReviewPage.RATING_TYPE.SATISFY, CreateOrEditReviewPage.RATING_TYPE.SATISFY, CreateOrEditReviewPage.RATING_TYPE.SATISFY, CreateOrEditReviewPage.RATING_TYPE.SATISFY)
				.submit().getTextAlert();
		assertContains(alertText, "Please answer all questions", "Missing Ambiences - FAILED - " + alertText,
				"Missing Ambiences - PASSED - " + alertText);
	}

	@Test
	public void FRAN_5755_verify_user_create_review_with_videos_max_60s() throws Exception {

		/********** Setup - Get the business name from id *****************************/
		// Get business info based on the businessId
		BusinessAPI businessAPI = new BusinessAPI(user);
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(Constant.BusinessIds.LOCAL_BUSINESS_ID);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();

		/******** Login then go to Create Review page ***************/
		login();
		BottomBarPage menuPage = new BottomBarPage(driver);
		RecordingPage recordingPage = menuPage.clickOnBeFrankTab();
		assertTrue(recordingPage.isActive(), "User is not in Recording page", "You're in Recording page");

		CreateOrEditReviewPage createReviewPage = recordingPage.recordVideo(60).preview().donePreview();

		String caption = "create review max 60s" + System.currentTimeMillis();
		String[] ambiences = { CreateOrEditReviewPage.AMBIENCES.QUIET };

		boolean isPostReviewSuccessfully = createReviewPage.selectBusiness(searchBusinessDisplayName)
				.setCaption(caption).doneCaption()
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

	@Test
	public void FRAN_5756_verify_user_can_update_the_review() throws Exception {
		/******************* Setup ***********************/
		new EverContentAPI(user).createVideoContentForBusiness(Constant.BusinessIds.LOCAL_BUSINESS_ID);

		/************* Go to user's videos screen *******************/
		login();
		BottomBarPage menuPage = new BottomBarPage(driver);

		// On User's profile page
		CurrentUserProfilePage profilePage = menuPage.clickOnProfileTab();
		assertTrue(profilePage.isActive(), "User is not in Profile page", "You're in Profile page");

		// On User's video tab
		profilePage.clickOnReviewsTab();
		assertTrue(profilePage.isOnReviewsTab(), "User is not in Review tab", "You're in Review tab");

		// Select the first review to edit
		ReviewDetailPage reviewDetailPage = profilePage.clickOnReviewByIndex(0);
		assertTrue(reviewDetailPage.isActive(), "User is not in Review Detail page", "You're in Review Detail page");

		/************* Edit the review *******************/
		CreateOrEditReviewPage editReviewPage = reviewDetailPage.editReview();
		assertTrue(editReviewPage.isActive(), "User is not in Edit Review page", "You're in Edit Review page");

		// Edit caption
		String newCaption = "edit review " + "@anh" + "#abc" + System.currentTimeMillis();
		editReviewPage.setCaption(newCaption).doneCaption();

		// Edit Occasion types
		editReviewPage.selectOccasionTypes(CreateOrEditReviewPage.OCCASION_TYPE.NO,
				CreateOrEditReviewPage.OCCASION_TYPE.NO, CreateOrEditReviewPage.OCCASION_TYPE.NO);

		// Edit Attributes
		editReviewPage.selectAttributes(CreateOrEditReviewPage.ATTRIBUTE_TYPE.NORMAL,
				CreateOrEditReviewPage.ATTRIBUTE_TYPE.NO, CreateOrEditReviewPage.ATTRIBUTE_TYPE.NO,
				CreateOrEditReviewPage.ATTRIBUTE_TYPE.NO);

		// Edit ambiences
		String[] ambiences = { CreateOrEditReviewPage.AMBIENCES.CASUAL };
		editReviewPage.selectAmbience(ambiences);

		// Update, if success, user will be directed back to Review Detail
		ReviewDetailPage reviewPage = editReviewPage.update();
		assertTrue(reviewPage.isActive(), "Update post failed", "Update post success");
	}

	@Test
	public void FRAN_5757_verify_user_not_able_update_the_review_if_missing_mandatories() throws Exception {
		/******************* Setup ***********************/
		new EverContentAPI(user).createVideoContentForBusiness(Constant.BusinessIds.LOCAL_BUSINESS_ID);

		/************* Go to user's videos screen *******************/
		login();
		BottomBarPage menuPage = new BottomBarPage(driver);

		// On User's profile page
		CurrentUserProfilePage profilePage = menuPage.clickOnProfileTab();
		assertTrue(profilePage.isActive(), "User is not in Profile page", "You're in Profile page");

		// On User's video tab
		profilePage.clickOnReviewsTab();
		assertTrue(profilePage.isOnReviewsTab(), "User is not in Review tab", "You're in Review tab");

		// Select the first review to edit
		ReviewDetailPage reviewDetailPage = profilePage.clickOnReviewByIndex(0);
		assertTrue(reviewDetailPage.isActive(), "User is not in Review Detail page", "You're in Review Detail page");

		/************* Edit the review *******************/
		CreateOrEditReviewPage editReviewPage = reviewDetailPage.editReview();
		assertTrue(editReviewPage.isActive(), "User is not in Edit Review page", "You're in Edit Review page");

		// Edit Rating
		editReviewPage.selectRatings(CreateOrEditReviewPage.RATING_TYPE.SATISFY, "", "", "");

		// Update, if success, user will be directed back to Review Detail
		editReviewPage.update();
		String alertText = editReviewPage.getTextAlert();
		assertContains(alertText, "Please answer all questions", "Missing Mandatory fields - FAILED - " + alertText,
				"Missing Mandatory fields - PASSED - " + alertText);
	}

	@Test
	public void FRAN_5758_verify_max_caption_length_is_500() throws Exception {

		/******** Login then go to Create Review page ***************/
		login();
		BottomBarPage menuPage = new BottomBarPage(driver);
		RecordingPage recordingPage = menuPage.clickOnBeFrankTab();
		assertTrue(recordingPage.isActive(), "User is not in Recording page", "You're in Recording page");

		CreateOrEditReviewPage createReviewPage = recordingPage.recordVideo(10).preview().donePreview();

		String maxLengthCaption = Common.randomAlphaNumeric(501);
		boolean isMaxCaptionWarning = createReviewPage.setCaption(maxLengthCaption).isMaxCaptionLengthWarningDisplays();

		assertTrue(isMaxCaptionWarning, "Max 500 length Caption warning doesn't display",
				"Max 500 length Caption warning displays");

	}
}
