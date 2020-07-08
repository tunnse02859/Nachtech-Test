package com.franki.automation.android.tests;

import static com.franki.automation.utility.Assertion.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.franki.automation.android.pages.home.HomePage;
import com.franki.automation.android.pages.login.LoginWithUsernamePage;
import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.EverContentAPI;
import com.franki.automation.datamodel.ContentData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class NotificationTests extends MobileTestSetup {
	AuthenticateAPI authenAPI_1;
	AuthenticateAPI authenAPI_2;
	UserData user_1;
	UserData user_2;
	EverContentAPI everContentAPI_1;
	EverContentAPI everContentAPI_2;

	@BeforeClass
	public void setupAPI() throws Exception {
		authenAPI_1 = new AuthenticateAPI();
		authenAPI_2 = new AuthenticateAPI();
		user_1 = authenAPI_1.loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		user_2 = authenAPI_2.loginForAccessToken(Constant.ACCOUNT_EMAIL_2, Constant.ACCOUNT_PASSWORD_2);

		everContentAPI_1 = new EverContentAPI(user_1);
		everContentAPI_2 = new EverContentAPI(user_2);
	}

	@Test
	public void FRAN_3446_VerifyAppShowsANotificationWhenCommentingAPost() throws Exception {
		ContentData content = everContentAPI_1.createVideoContentForBusiness(Constant.BusinessIds.LOCAL_BUSINESS_ID);
		boolean isCommentSuccessful = everContentAPI_2.createComment(content.getContentKey(), "test");
		assertTrue(isCommentSuccessful, "User 2 can't comment into the post", "User 2 can comment into the post");

		LoginWithUsernamePage loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		HomePage homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
	}

}
