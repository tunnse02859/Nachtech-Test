package com.franki.automation.ios.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.franki.automation.utility.Assertion.*;

import java.util.ArrayList;

import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.CarouselAPI;
import com.franki.automation.api.EverContentAPI;
import com.franki.automation.datamodel.CarouselsData;
import com.franki.automation.datamodel.ContentData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.ios.pages.business.BusinessProfilePage;
import com.franki.automation.ios.pages.home.BottomBarPage;
import com.franki.automation.ios.pages.home.HomePage;
import com.franki.automation.ios.pages.login.LoginWithUsernamePage;
import com.franki.automation.ios.pages.profile.CurrentUserProfilePage;
import com.franki.automation.ios.pages.profile.OtherUserProfilePage;
import com.franki.automation.ios.pages.review.ReviewCommentPage;
import com.franki.automation.ios.pages.review.ReviewDetailPage;
import com.franki.automation.ios.pages.review.ReviewRatingPage;
import com.franki.automation.ios.pages.search.HomeSearchPage;
import com.franki.automation.report.HtmlReporter;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;
import com.franki.automation.utility.Common;

public class ReviewDetailTests extends MobileTestSetup {

	
	EverContentAPI everContentAPI;
	UserData user;

	@BeforeClass
	public void setupAPI() throws Exception {
		user = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		everContentAPI = new EverContentAPI(user);
	}

	private HomePage gotoHomePage() throws Exception {
		LoginWithUsernamePage loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		HomePage homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
		
		return homePage;
	}
	
	@Test
	public void FRAN_5585_Verify_user_able_to_view_ReviewDetail_when_tap_on_a_post() throws Exception {

		ReviewDetailPage reviewDetailPage;
		HomePage homePage = gotoHomePage();

		if(homePage.isNearYouCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickNearYouReview(0);
		}
		else if(homePage.isTrendingCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickTrendingReview(0);
		}
		else if(homePage.isTrendingInYourCityCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickTrendingInYourCityReview(0);
		}
		else {
			throw new Exception("Can't find any reviews in your location!!!");
		}
		
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");

	}

	@Test
	public void FRAN_5587_Verify_swipe_up_down_on_ReviewDetail() throws Exception {
		
		/************************ Setup ************************/
		// Make sure we have 2 Trending reviews 
		ArrayList<CarouselsData> trendingCollections = new CarouselAPI(user)
				.getCarouselsTrendingCollection(Constant.Location.LATITUDE, Constant.Location.LONGITUDE);
		
		if(trendingCollections.size() < 2) {
			new EverContentAPI(user).createVideoContentForBusiness(Constant.BusinessIds.LOCAL_BUSINESS_ID);
		}
		
		HomePage homePage = gotoHomePage();
		
		/******** Verify next posts are displayed while a user swipes up on Review Detail screen *******/
		
		// Click on the review index = 0
		ReviewDetailPage reviewDetailPage = homePage.clickTrendingReview(0);
		// Swipe up
		reviewDetailPage.swipeUp();
		// The next review index should be 1
		int currentIndex = reviewDetailPage.getDisplayingReviewIndex();
		assertTrue(currentIndex == 1, "Swipe up to next review unsuccessfully",
				"Swipe up to next review successfully");
		
		/******** Verify next posts are displayed while a user swipes up on Review Detail screen *******/
		// Swipe down
		reviewDetailPage.swipeDown();
		// The next review index should be 0
		currentIndex = reviewDetailPage.getDisplayingReviewIndex();
		assertTrue(currentIndex == 0, "Swipe up to previous review unsuccessfully",
				"Swipe up to previous review successfully");
		
		/******** Verify a user is able to get back to Previous screen after tap to Back button on Review Screen *******/
		reviewDetailPage.clickBack();
		assertTrue(homePage.isActive(), "User is not directed to Home screen when tap on Back", "User is directed to Home screen when tap on back");
		
	}

	@Test
	public void FRAN_5589_Verify_BusinessProfile_displayed_when_user_swipes_left_on_ReviewDetail() throws Exception {
		// Click on a review
		ReviewDetailPage reviewDetailPage;
		HomePage homePage = gotoHomePage();

		if(homePage.isNearYouCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickNearYouReview(0);
		}
		else if(homePage.isTrendingCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickTrendingReview(0);
		}
		else if(homePage.isTrendingInYourCityCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickTrendingInYourCityReview(0);
		}
		else {
			throw new Exception("Can't find any reviews in your location!!!");
		}
		
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");
		
		String postBusinessDisplayName = reviewDetailPage.getBusinessDisplayNameByReviewIndex(0);

		// Swipe left
		reviewDetailPage.swipeLeft();
		BusinessProfilePage businessPage = new BusinessProfilePage(driver);

		assertTrue(businessPage.isActive(), "Business profile is not displayed when swipe left",
				"Business profile is displayed when swipe left");
		
		assertEquals(businessPage.getBusinessDisplayName(), postBusinessDisplayName,
				"Business name is displayed incorrectly", "Business name is displayed correctly");
		
		// Click Back
		businessPage.clickBack();
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed when back",
				"Review detail screen is displayed when back");

	}


	@Test
	public void FRAN_5592_Verify_user_able_to_save_the_post() throws Exception {
		// Click on a review
		ReviewDetailPage reviewDetailPage;
		HomePage homePage = gotoHomePage();

		if(homePage.isNearYouCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickNearYouReview(0);
		}
		else if(homePage.isTrendingCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickTrendingReview(0);
		}
		else if(homePage.isTrendingInYourCityCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickTrendingInYourCityReview(0);
		}
		else {
			throw new Exception("Can't find any reviews in your location!!!");
		}
		
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");
		
		String postBusinessDisplayName = reviewDetailPage.getBusinessDisplayNameByReviewIndex(0);
		String postBody = reviewDetailPage.getReviewBodyByReviewIndex(0);
		
		/********* Verify a user is able to save the post ****************/
		// save the review
		reviewDetailPage.saveReview();
		reviewDetailPage.clickBack();
		
		// Goto the Profile page then check the saved review
		BottomBarPage bottomBar = new BottomBarPage(driver);
		bottomBar.clickOnProfileTab();
		
		CurrentUserProfilePage userProfilePage = new CurrentUserProfilePage(driver);
		assertTrue(userProfilePage.isActive(), "User Profile screen is not displayed",
				"User Profile screen is displayed");
		
		userProfilePage.clickOnSavedTab();
		reviewDetailPage = userProfilePage.viewSavedPost(0);
		String savedPostBusinessDisplayName = reviewDetailPage.getBusinessDisplayNameByReviewIndex(0);
		String savedPostBody = reviewDetailPage.getReviewBodyByReviewIndex(0);
		
		assertEquals(postBusinessDisplayName, savedPostBusinessDisplayName, "The review's business name not match", "The review's business name matches");
		assertEquals(postBody, savedPostBody, "The review's body not match, save review failed", "The review's body matches, save review successfully");
		
		HtmlReporter.pass("The review has been saved successfully!");
	}
	
	@Test
	public void FRAN_5593_Verify_user_able_to_like_the_post() throws Exception {
		// Click on a review
		ReviewDetailPage reviewDetailPage;
		HomePage homePage = gotoHomePage();

		if(homePage.isNearYouCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickNearYouReview(0);
		}
		else if(homePage.isTrendingCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickTrendingReview(0);
		}
		else if(homePage.isTrendingInYourCityCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickTrendingInYourCityReview(0);
		}
		else {
			throw new Exception("Can't find any reviews in your location!!!");
		}
		
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");
		
		/********* Verify a user is able to like the post ****************/
		int likeCountBefore = reviewDetailPage.getLikeCount();
		reviewDetailPage.likeReview();
		int likeCountAfter = reviewDetailPage.getLikeCount();
		assertTrue(likeCountAfter - likeCountBefore == 1, "Like the review unsuccessfully",
				"Like the review successfully");
	}

	@Test
	public void FRAN_5594_Verify_user_able_to_comment_the_post() throws Exception {
		// Click on a review
		ReviewDetailPage reviewDetailPage;
		HomePage homePage = gotoHomePage();

		if(homePage.isNearYouCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickNearYouReview(0);
		}
		else if(homePage.isTrendingCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickTrendingReview(0);
		}
		else if(homePage.isTrendingInYourCityCategoriesDisplayed()) {
			reviewDetailPage = homePage.clickTrendingInYourCityReview(0);
		}
		else {
			throw new Exception("Can't find any reviews in your location!!!");
		}
		
		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
				"Review detail screen is displayed");
		
		/********* Verify a user is able to comment the post ****************/
		ReviewCommentPage reviewCommentPage =  reviewDetailPage.clickCommentReview();
		assertTrue(reviewCommentPage.isActive(), "Comment screen is not displayed",
				"Comment screen is displayed");
		
		//post a comment and verify new comment displayed
		String commentString = "test comment : " + System.currentTimeMillis();
		reviewCommentPage.postComment(commentString);
		
		Assert.assertEquals(reviewCommentPage.getUsernameLastComment(), user.getDisplayName(), "The comment hasn't been added");
		Assert.assertEquals(reviewCommentPage.getContentLastComment(), commentString, "The comment hasn't been added");
		
		HtmlReporter.pass("The comment has been added successfully!");
		
	}
	
	@Test
	public void FRAN_5595_Verify_user_able_to_flag_review() throws Exception {
		
		/************************ Setup ************************/
		ArrayList<CarouselsData> trendingCollectionsBefore = new CarouselAPI(user)
				.getCarouselsTrendingCollection(Constant.Location.LATITUDE, Constant.Location.LONGITUDE);
		HomePage homePage = gotoHomePage();
		
		/******** Flag the review *******/
		
		// Click on the review index = 0, the displaying review index is 0
		ReviewDetailPage reviewDetailPage = homePage.clickTrendingReview(0);
		int reviewCountBefore = reviewDetailPage.getTheReviewCount();
		// Flag the post
		reviewDetailPage.flagReview();
		// The displaying review index should be 1
		int reviewCountAfter = reviewDetailPage.getTheReviewCount();
		assertTrue(reviewCountBefore - reviewCountAfter == 1, "The review is not hiden",
				"The review is hiden");
		// The review is removed from carousel category
		ArrayList<CarouselsData> trendingCollectionsAfter = new CarouselAPI(user)
				.getCarouselsTrendingCollection(Constant.Location.LATITUDE, Constant.Location.LONGITUDE);
		assertTrue(trendingCollectionsBefore.size() - trendingCollectionsAfter.size() == 1, "The review is not hiden from Carousel",
				"The review is hiden from Carousel");
	}
	
//	
//	@Test
//	public void FRAN_5597_Verify_user_able_to_view_RatingScreen_when_click_on_star_icon() throws Exception {
//		//get one content data from list for testing
//		ArrayList<ContentData> listReview = everContentAPI.getEverContent();
//		ContentData selectedContent = listReview.get(0);
//					
//		loginPage = new LoginWithUsernamePage(driver);
//		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
//		homePage = new HomePage(driver);
//		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
//
//		driver.sleep(5);
//		reviewDetailPage = homePage.selectReviewNearYouByIndex(1);
//		assertTrue(reviewDetailPage.isActive(), "Review detail screen is not displayed",
//				"Review detail screen is displayed");
//
//		String currentReviewBusinessName = reviewDetailPage.getBusinessName();
//		String currentReviewDescription = reviewDetailPage.getReviewDescription();
//
//		assertEquals(currentReviewBusinessName, selectedContent.getCorporateGroup().getDisplayName(),
//				"Business name of current review is displayed incorrectly",
//				"Business name of current review is displayed correctly");
//		assertEquals(currentReviewDescription, selectedContent.getPostBody(),
//				"Review description of current review is displayed incorrectly",
//				"Review description of current review is displayed correctly");
//
//		reviewRatingPage = reviewDetailPage.clickRatingReview();
//		assertTrue(reviewRatingPage.isActive(), "Review rating screen is not displayed",
//				"Review rating screen is dislayed");
//		
//	}
}
