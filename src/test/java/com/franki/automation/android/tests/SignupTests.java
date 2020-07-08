package com.franki.automation.android.tests;

import static com.franki.automation.utility.Assertion.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.franki.automation.android.pages.home.HomePage;
import com.franki.automation.android.pages.login.IdentityPage;
import com.franki.automation.android.pages.login.OverViewPage;
import com.franki.automation.android.pages.login.PasswordPage;
import com.franki.automation.android.pages.login.RegisterPage;
import com.franki.automation.android.pages.login.SignupPage;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;
import com.franki.automation.utility.Common;

public class SignupTests extends MobileTestSetup {

	@Test
	public void FRAN5420_VerifyErrorIsDisplayedWhenUseExistingEmailPhone() throws Exception {
		OverViewPage overViewPage = new OverViewPage(driver);

		// On App's Overview
		assertTrue(overViewPage.isActive(), "User is not in Overview page", "You're in Overview page");
		RegisterPage registerPage = overViewPage.clickSkipOverview();

		// On Register page
		assertTrue(registerPage.isActive(), "User is not in Register page", "You're in Register page");
		SignupPage signupPage = registerPage.goToSignup();

		// On Signup page
		assertTrue(signupPage.isActive(), "User is not in Signup page", "You're in Signup page");
		signupPage.inputMobileNumberOrEmail(Constant.ACCOUNT_EMAIL_1);
		signupPage.clickNextBtn();
		Assert.assertEquals(signupPage.getPopupMessageDisplay(), "The identifier already exists in the system");

		/*
		 * signupPage.acceptPopupDisplay();
		 * signupPage.inputMobileNumberOrEmail("84356112868");
		 * signupPage.clickNextBtn();
		 * Assert.assertEquals(signupPage.getPopupMessageDisplay(),
		 * "Invalid phone number");
		 */
	}

	@Test
	public void FRAN5421_VerifyUsersAreAbleToCreateWithValidCredentials() throws Exception {
		String newEmail = Common.generateEmail();
		OverViewPage overViewPage = new OverViewPage(driver);

		// On App's Overview
		Assert.assertTrue(overViewPage.isActive(), "User is not in Overview page");
		RegisterPage registerPage = overViewPage.clickSkipOverview();

		// On Register page
		Assert.assertTrue(registerPage.isActive(), "User is not in Register page");
		SignupPage signupPage = registerPage.goToSignup();

		// On Signup page
		assertTrue(signupPage.isActive(), "User is not in Signup page", "You're in Signup page");
		signupPage.inputMobileNumberOrEmail(newEmail);
		IdentityPage identityPage = signupPage.clickNextBtn();
		assertTrue(identityPage.isActive(), "User is not in Identity page", "You're in Identity page");
		PasswordPage passwordPage = identityPage.clickNextBtn();
		assertTrue(passwordPage.isActive(), "User is not in Password page", "You're in Password page");
		passwordPage.inputNewPassword(Constant.ACCOUNT_PASSWORD_1);
		HomePage homePage = passwordPage.clickNextBtn();
		assertTrue(homePage.isActive(), "User is not in Home page", "You're in Home page");
	}
}
