package com.franki.automation.ios.tests;

import static com.franki.automation.utility.Assertion.*;

import java.util.ArrayList;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.BusinessAPI;
import com.franki.automation.api.CarouselAPI;
import com.franki.automation.api.EverContentAPI;
import com.franki.automation.datamodel.CarouselsData;
import com.franki.automation.datamodel.ContentData;
import com.franki.automation.datamodel.PromotionData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.ios.pages.home.BottomBarPage;
import com.franki.automation.ios.pages.home.HomePage;
import com.franki.automation.ios.pages.login.LoginWithUsernamePage;
import com.franki.automation.ios.pages.review.ReviewDetailPage;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class HomeTests extends MobileTestSetup {

	private UserData user;

	@BeforeClass
	public void setupAPI() throws Exception {
		user = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
	}

	@Test
	public void FRAN_3386_userCanAccessHomePageWhenTabOnHomeMenu() throws Exception {
		LoginWithUsernamePage loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		BottomBarPage bottomBarPage = new BottomBarPage(driver);
		// On Home Page
		HomePage homePage = bottomBarPage.clickOnHomeTab();
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
	}
	
	@Test
	public void FRAN_5720_verify_promotion_displayed_on_top_header() throws Exception {

		CarouselAPI carouselAPI = new CarouselAPI(user);
		ArrayList<PromotionData> promotions = carouselAPI
				.getCarouselsPromotions(Constant.Location.LATITUDE, Constant.Location.LONGITUDE);

		LoginWithUsernamePage loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		BottomBarPage bottomBarPage = new BottomBarPage(driver);
		// On Home Page
		HomePage homePage = bottomBarPage.clickOnHomeTab();

		// Verify Promotion displays
		if (promotions.size() > 0) {
			assertTrue(promotions.stream().anyMatch(e -> {
				try {
					return e.getPromotionTitle().equals(homePage.getPromotionTitle());
				} catch (Exception e1) {
					return false;
				}
			}), "The Promotion doesn't display",
					"The Promotion displays");
		} 
	}

	@Test
	public void FRAN_5717_verifyCaroselCategoriesDisplayed() throws Exception {

		CarouselAPI carouselAPI = new CarouselAPI(user);
		ArrayList<CarouselsData> trendingCollections = carouselAPI
				.getCarouselsTrendingCollection(Constant.Location.LATITUDE, Constant.Location.LONGITUDE);
		ArrayList<CarouselsData> trendingInYourCityCollections = carouselAPI
				.getCarouselsTrendingInYourCityCollection(Constant.Location.LATITUDE, Constant.Location.LONGITUDE);
		ArrayList<CarouselsData> nearYouCollections = carouselAPI
				.getCarouselsNearYouCollection(Constant.Location.LATITUDE, Constant.Location.LONGITUDE);

		LoginWithUsernamePage loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		BottomBarPage bottomBarPage = new BottomBarPage(driver);
		// On Home Page
		HomePage homePage = bottomBarPage.clickOnHomeTab();

		// Verify Near You category displays
		if (nearYouCollections.size() > 0) {
			assertTrue(homePage.isNearYouCategoriesDisplayed(), "The Near You carousel doesn't display",
					"The Near You carousel displays");
		} else {
			assertTrue(!homePage.isNearYouCategoriesDisplayed(),
					"The Near You collections still display even if the system doesn't have data",
					"The Near You collections don't display due to the system doesn't have data");
		}

		// Verify Trending category displays
		if (trendingCollections.size() > 0) {
			assertTrue(homePage.isTrendingCategoriesDisplayed(), "The Trending carousel doesn't display",
					"The Trending carousel displays");
		} else {
			assertTrue(!homePage.isTrendingCategoriesDisplayed(),
					"The Trending collections still display even if the system doesn't have data",
					"The Trending collections don't display due to the system doesn't have data");
		}

		// Verify Trending In Your City category displays
		if (trendingInYourCityCollections.size() > 0) {
			assertTrue(homePage.isTrendingInYourCityCategoriesDisplayed(),
					"The Trending In Your City carousel doesn't display",
					"The Trending In Your City carousel displays");
		} else {
			assertTrue(!homePage.isTrendingInYourCityCategoriesDisplayed(),
					"The Trending In Your City collections still display even if the system doesn't have data",
					"The Trending In Your City collections don't display due to the system doesn't have data");
		}

	}
	
	@Test
	public void FRAN_5719_Verify_new_post_displayed_after_user_created_post() throws Exception {
		
		/************************ Setup ************************/
		// Create a review, save the test data to verify later
		ContentData contentSetup = new EverContentAPI(user).createVideoContentForBusiness(Constant.BusinessIds.LOCAL_BUSINESS_ID);
		String postedContent = contentSetup.getPostBody();
		String postedBusinessDisplayName = new BusinessAPI(user).getBusinessDetails(Constant.BusinessIds.LOCAL_BUSINESS_ID).getDisplayName();
		
		// Goto Home page
		LoginWithUsernamePage loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		BottomBarPage bottomBarPage = new BottomBarPage(driver);
		// On Home Page
		HomePage homePage = bottomBarPage.clickOnHomeTab();
		
		/******** Verify new post displayed on Trending list *******/
		// Click on the review index = 0
		ReviewDetailPage reviewDetailPage = homePage.clickTrendingReview(0);
		// Get the size of Trending collection
		int trendingListSize = reviewDetailPage.getTheReviewCount();
		
		// The flag to find the new review displayed or not
		boolean isNewReviewDisplayed = false;
		// Scan the collections until see the new review created above
		for(int i = 0; i <  trendingListSize; i++) {
			if(reviewDetailPage.getBusinessDisplayNameByReviewIndex(i).equals(postedBusinessDisplayName) && reviewDetailPage.getReviewBodyByReviewIndex(i).equals(postedContent)) {
				isNewReviewDisplayed = true;
			}
		}
		assertTrue(isNewReviewDisplayed, "The new created review is not displayed on the Trending collection",
				"The new created review is displayed on the Trending collection");
		
	}

}