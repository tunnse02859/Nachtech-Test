package com.franki.automation.android.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.franki.automation.android.pages.home.HomePage;
import com.franki.automation.android.pages.login.LoginWithUsernamePage;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class HomeTests extends MobileTestSetup {

	private LoginWithUsernamePage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void beforeMethod() {
		loginPage = new LoginWithUsernamePage(driver);
		homePage = new HomePage(driver);
	}

	@Test
	public void FRAN4427_VerifyHomepageDisplayedWhenTapToHomeTab() throws Exception {
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage.verifyPostIsDisplayed();
		homePage.clickToProfileTab();
		homePage.clickToHomeTab();
		homePage.verifyPostIsDisplayed();
	}

//	@Test
//	public void FRAN4428_VerifyUserIsAbleToLikeAPost() throws Exception {
//		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS_1, Constant.PASSWORD);
//		int beforeLike = homePage.getAPostLikeNumber();
//		homePage.likeAPost();
//		int afterLike = homePage.getAPostLikeNumber();
//		homePage.verifyUserLikedAPost(beforeLike, afterLike);
//		// tearDown
//		homePage.likeAPost();
//	}
//
//	// Question about verify Saved
//	@Test
//	public void FRAN4429_VerifyUserIsAbleToSaveAPost() throws Exception {
//		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS_1, Constant.PASSWORD);
//		homePage.clickFavoriteAPost();
//		homePage.verifySavedPopupDisplay();
//	}
//
//	@Test
//	public void FRAN4444_VerifyPostIsDisplayedWhenSwipeDown() throws Exception {
//		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS_1, Constant.PASSWORD);
//		homePage.swipeDownToBottomPage();
//		homePage.verifyPostIsDisplayed();
//	}
}