package com.franki.automation.android.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.franki.automation.utility.Assertion.*;

import java.util.ArrayList;

import com.franki.automation.android.pages.business.BusinessProfilePage;
import com.franki.automation.android.pages.home.HomePage;
import com.franki.automation.android.pages.login.LoginWithUsernamePage;
import com.franki.automation.android.pages.profile.OtherUserProfilePage;
import com.franki.automation.android.pages.review.ReviewCommentPage;
import com.franki.automation.android.pages.review.ReviewDetailPage;
import com.franki.automation.android.pages.review.ReviewRatingPage;
import com.franki.automation.android.pages.search.HomeSearchPage;
import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.EverContentAPI;
import com.franki.automation.datamodel.ContentData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;
import com.franki.automation.utility.Common;

public class ReviewDetailTests extends MobileTestSetup {

	LoginWithUsernamePage loginPage;
	OtherUserProfilePage profilePage;
	BusinessProfilePage businessProfilePage;
	HomePage homePage;
	HomeSearchPage searchPage;
	ReviewDetailPage reviewDetailPage;
	ReviewCommentPage reviewCommentPage;
	ReviewRatingPage reviewRatingPage;
	
	EverContentAPI everContentAPI;
	AuthenticateAPI authenAPI;
	UserData user;

	@BeforeClass
	public void setupAPI() throws Exception {
		authenAPI = new AuthenticateAPI();
		user = authenAPI.loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		everContentAPI = new EverContentAPI(user);
	}

	@Test
	public void FRAN_5585_Verify_user_able_to_view_ReviewDetail_when_tap_on_a_post() throws Exception {

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		driver.sleep(5);
		reviewDetailPage = homePage.selectReviewNearYouByIndex(2);
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");

	}

	@Test
	public void FRAN_5587_Verify_next_post_displayed_when_user_swipes_up_on_ReviewDetail() throws Exception {
		ArrayList<ContentData> listReview = everContentAPI.getEverContent();
		ContentData selectedContent = listReview.get(1);
		ContentData nextContent = listReview.get(2);

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		driver.sleep(5);
		reviewDetailPage = homePage.selectReviewNearYouByIndex(2);
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");

		String currentReviewBusinessName = reviewDetailPage.getBusinessName();
		String currentReviewDescription = reviewDetailPage.getReviewDescription();

		assertEquals(currentReviewBusinessName, selectedContent.getCorporateGroup().getDisplayName(),
				"Business name of current review is displayed incorrectly",
				"Business name of current review is displayed correctly");
		assertEquals(currentReviewDescription, selectedContent.getPostBody(),
				"Review description of current review is displayed incorrectly",
				"Review description of current review is displayed correctly");

		reviewDetailPage.goToNextReview();

		String nextReviewBusinessName = reviewDetailPage.getBusinessName();
		String nextReviewDescription = reviewDetailPage.getReviewDescription();

		assertEquals(nextReviewBusinessName, nextContent.getCorporateGroup().getDisplayName(),
				"Business name of current review is displayed incorrectly",
				"Business name of current review is displayed correctly");
		assertEquals(nextReviewDescription, nextContent.getPostBody(),
				"Review description of current review is displayed incorrectly",
				"Review description of current review is displayed correctly");
	}

	@Test
	public void FRAN_5588_Verify_previous_post_displayed_when_user_swipes_down_on_ReviewDetail() throws Exception {
		ArrayList<ContentData> listReview = everContentAPI.getEverContent();
		ContentData selectedContent = listReview.get(1);
		ContentData previousContent = listReview.get(0);

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		driver.sleep(5);
		reviewDetailPage = homePage.selectReviewNearYouByIndex(2);
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");

		String currentReviewBusinessName = reviewDetailPage.getBusinessName();
		String currentReviewDescription = reviewDetailPage.getReviewDescription();

		assertEquals(currentReviewBusinessName, selectedContent.getCorporateGroup().getDisplayName(),
				"Business name of current review is displayed incorrectly",
				"Business name of current review is displayed correctly");
		assertEquals(currentReviewDescription, selectedContent.getPostBody(),
				"Review description of current review is displayed incorrectly",
				"Review description of current review is displayed correctly");

		reviewDetailPage.goToPreviousReview();

		String nextReviewBusinessName = reviewDetailPage.getBusinessName();
		String nextReviewDescription = reviewDetailPage.getReviewDescription();

		assertEquals(nextReviewBusinessName, previousContent.getCorporateGroup().getDisplayName(),
				"Business name of current review is displayed incorrectly",
				"Business name of current review is displayed correctly");
		assertEquals(nextReviewDescription, previousContent.getPostBody(),
				"Review description of current review is displayed incorrectly",
				"Review description of current review is displayed correctly");
	}

	@Test
	public void FRAN_5589_Verify_BusinessProfile_displayed_when_user_swipes_left_on_ReviewDetail() throws Exception {
		ArrayList<ContentData> listReview = everContentAPI.getEverContent();
		ContentData selectedContent = listReview.get(1);

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		driver.sleep(5);
		reviewDetailPage = homePage.selectReviewNearYouByIndex(2);
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");

		String currentReviewBusinessName = reviewDetailPage.getBusinessName();
		String currentReviewDescription = reviewDetailPage.getReviewDescription();

		assertEquals(currentReviewBusinessName, selectedContent.getCorporateGroup().getDisplayName(),
				"Business name of current review is displayed incorrectly",
				"Business name of current review is displayed correctly");
		assertEquals(currentReviewDescription, selectedContent.getPostBody(),
				"Review description of current review is displayed incorrectly",
				"Review description of current review is displayed correctly");

		businessProfilePage = reviewDetailPage.goToBusinessProfile();

		assertTrue(businessProfilePage.isActive(), "Business profile is not displayed when swipe left",
				"Business profile is displayed when swipe left");
		assertEquals(businessProfilePage.getBusinessDisplayedName(), selectedContent.getCorporateGroup().getDisplayName(),
				"Business name is displayed incorrectly", "Business name is displayed correctly");
		assertEquals(businessProfilePage.getBusinessType(),
				selectedContent.getCorporateGroup().getBusinessType().toUpperCase(),
				"Business type is displayed incorrectly", "Business type is displayed correctly");
	}

	@Test
	public void FRAN_5590_Verify_ReviewDetail_displayed_when_back_from_BusinessProfile() throws Exception {
		ArrayList<ContentData> listReview = everContentAPI.getEverContent();
		ContentData selectedContent = listReview.get(1);

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		driver.sleep(5);
		reviewDetailPage = homePage.selectReviewNearYouByIndex(2);
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");

		String currentReviewBusinessName = reviewDetailPage.getBusinessName();
		String currentReviewDescription = reviewDetailPage.getReviewDescription();

		assertEquals(currentReviewBusinessName, selectedContent.getCorporateGroup().getDisplayName(),
				"Business name of current review is displayed incorrectly",
				"Business name of current review is displayed correctly");
		assertEquals(currentReviewDescription, selectedContent.getPostBody(),
				"Review description of current review is displayed incorrectly",
				"Review description of current review is displayed correctly");

		businessProfilePage = reviewDetailPage.goToBusinessProfile();

		assertTrue(businessProfilePage.isActive(), "Business profile is not displayed when swipe left",
				"Business profile is displayed when swipe left");
		assertEquals(businessProfilePage.getBusinessDisplayedName(), selectedContent.getCorporateGroup().getDisplayName(),
				"Business name is displayed incorrectly", "Business name is displayed correctly");
		assertEquals(businessProfilePage.getBusinessType(),
				selectedContent.getCorporateGroup().getBusinessType().toUpperCase(),
				"Business type is displayed incorrectly", "Business type is displayed correctly");

		businessProfilePage.clickOnBack();

		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed after click on back",
				"Review detail screen is displayed after click on back");
		assertEquals(currentReviewBusinessName, selectedContent.getCorporateGroup().getDisplayName(),
				"Business name of current review is displayed incorrectly",
				"Business name of current review is displayed correctly");
		assertEquals(currentReviewDescription, selectedContent.getPostBody(),
				"Review description of current review is displayed incorrectly",
				"Review description of current review is displayed correctly");
	}
	
	@Test
	public void FRAN_5591_Verify_user_get_back_to_previous_screen_from_ReviewDetail() throws Exception {
		ArrayList<ContentData> listReview = everContentAPI.getEverContent();
		ContentData selectedContent = listReview.get(1);

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		driver.sleep(5);
		reviewDetailPage = homePage.selectReviewNearYouByIndex(2);
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");

		String currentReviewBusinessName = reviewDetailPage.getBusinessName();
		String currentReviewDescription = reviewDetailPage.getReviewDescription();

		assertEquals(currentReviewBusinessName, selectedContent.getCorporateGroup().getDisplayName(),
				"Business name of current review is displayed incorrectly",
				"Business name of current review is displayed correctly");
		assertEquals(currentReviewDescription, selectedContent.getPostBody(),
				"Review description of current review is displayed incorrectly",
				"Review description of current review is displayed correctly");
		
		reviewDetailPage.clickBack();
		assertTrue(homePage.isActive(), "User is not directed to Home screen when tap on Back", "User is directed to Home screen when tap on back");
	}

	@Test
	public void FRAN_5592_Verify_user_able_to_save_the_post() throws Exception {
		ArrayList<ContentData> listReview = everContentAPI.getEverContent();
		ContentData selectedContent = listReview.get(0);
		everContentAPI.unfavoriteContent(selectedContent.getContentKey());

		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		driver.sleep(5);
		reviewDetailPage = homePage.selectReviewNearYouByIndex(1);
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");

		String currentReviewBusinessName = reviewDetailPage.getBusinessName();
		String currentReviewDescription = reviewDetailPage.getReviewDescription();

		assertEquals(currentReviewBusinessName, selectedContent.getCorporateGroup().getDisplayName(),
				"Business name of current review is displayed incorrectly",
				"Business name of current review is displayed correctly");
		assertEquals(currentReviewDescription, selectedContent.getPostBody(),
				"Review description of current review is displayed incorrectly",
				"Review description of current review is displayed correctly");

		reviewDetailPage.clickSaveReview();
		driver.sleep(1);
		assertTrue(everContentAPI.isSavedContent(selectedContent.getContentKey()),
				"API verification: Review is not saved", "API verification: Review is saved successfully");
	}
	
	@Test
	public void FRAN_5593_Verify_user_able_to_like_the_post() throws Exception {
		//get one content data from list for testing
		ArrayList<ContentData> listReview = everContentAPI.getEverContent();
		ContentData selectedContent = listReview.get(0);
		String postBody = selectedContent.getPostBody();
		//unlike content and get data again
		everContentAPI.unLikeContent(selectedContent.getContentKey());
		selectedContent = everContentAPI.getContentDetail(selectedContent.getContentKey());
		int beforeClickLikeCount = selectedContent.getLikeCount();
				
				
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		driver.sleep(5);
		reviewDetailPage = homePage.selectReviewNearYouByIndex(1);
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");

		String currentReviewBusinessName = reviewDetailPage.getBusinessName();
		String currentReviewDescription = reviewDetailPage.getReviewDescription();

		assertEquals(currentReviewBusinessName, selectedContent.getCorporateGroup().getDisplayName(),
				"Business name of current review is displayed incorrectly",
				"Business name of current review is displayed correctly");
		assertEquals(currentReviewDescription, postBody,
				"Review description of current review is displayed incorrectly",
				"Review description of current review is displayed correctly");

		reviewDetailPage.clickLikeReview();
		//get content again from API
		selectedContent = everContentAPI.getContentDetail(selectedContent.getContentKey());
		assertEquals(selectedContent.getLikeCount(),beforeClickLikeCount + 1,"API verification: Like count is not increased","API verification: Like count is increased correctly");
	}
	
	@Test
	public void FRAN_5594_Verify_user_able_to_comment_the_post() throws Exception {
		//get one content data from list for testing
		ArrayList<ContentData> listReview = everContentAPI.getEverContent();
		ContentData selectedContent = listReview.get(0);
		String postBody = selectedContent.getPostBody();
		selectedContent = everContentAPI.getContentDetail(selectedContent.getContentKey());
		int beforeCommentCount = selectedContent.getCommentCount();
				
				
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		driver.sleep(5);
		reviewDetailPage = homePage.selectReviewNearYouByIndex(1);
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");

		String currentReviewBusinessName = reviewDetailPage.getBusinessName();
		String currentReviewDescription = reviewDetailPage.getReviewDescription();

		assertEquals(currentReviewBusinessName, selectedContent.getCorporateGroup().getDisplayName(),
				"Business name of current review is displayed incorrectly",
				"Business name of current review is displayed correctly");
		assertEquals(currentReviewDescription, postBody,
				"Review description of current review is displayed incorrectly",
				"Review description of current review is displayed correctly");

		reviewCommentPage = reviewDetailPage.clickCommentReview();
		
		assertTrue(reviewCommentPage.isActive(), "Comment screen is not displayed",
				"Comment screen is displayed");
		
		//post a comment and verify new comment displayed
		String commentString = "test comment : " + Common.randomAlphaNumeric(10);
		reviewCommentPage.postComment(commentString);
		assertTrue(reviewCommentPage.isNewCommentAdded(commentString), "Cannot find new comment in screen",
				"New comment is displayed");
		
		//get content again from API
		selectedContent = everContentAPI.getContentDetail(selectedContent.getContentKey());
		assertEquals(selectedContent.getCommentCount(),beforeCommentCount + 1,"API verification: Comment count is not increased","API verification: Comment count is increased correctly");
		
	}
	
	@Test
	public void FRAN_5597_Verify_user_able_to_view_RatingScreen_when_click_on_star_icon() throws Exception {
		//get one content data from list for testing
		ArrayList<ContentData> listReview = everContentAPI.getEverContent();
		ContentData selectedContent = listReview.get(0);
					
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");

		driver.sleep(5);
		reviewDetailPage = homePage.selectReviewNearYouByIndex(1);
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");

		String currentReviewBusinessName = reviewDetailPage.getBusinessName();
		String currentReviewDescription = reviewDetailPage.getReviewDescription();

		assertEquals(currentReviewBusinessName, selectedContent.getCorporateGroup().getDisplayName(),
				"Business name of current review is displayed incorrectly",
				"Business name of current review is displayed correctly");
		assertEquals(currentReviewDescription, selectedContent.getPostBody(),
				"Review description of current review is displayed incorrectly",
				"Review description of current review is displayed correctly");

		reviewRatingPage = reviewDetailPage.clickRatingReview();
		assertTrue(reviewRatingPage.isActive(), "Review rating screen is not displayed",
				"Review rating screen is dislayed");
		
	}
}
