package com.franki.automation.ios.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.franki.automation.android.pages.login.LoginFacebookPage;
import com.franki.automation.android.pages.login.LoginPage;
import com.franki.automation.android.pages.login.OverViewPage;
import com.franki.automation.android.pages.login.RegisterPage;
import com.franki.automation.setup.MobileTestSetup;

public class SocialLoginTests extends MobileTestSetup {

//	@Test
//	public void FRAN_4668_loginWithFacebookSuccessfully() throws Exception {
//		OverViewPage overViewPage = new OverViewPage(driver);
//		
//		// On App's Overview
//		Assert.assertTrue(overViewPage.isActive(), "User is not in Overview page");
//		RegisterPage registerPage = overViewPage.clickSkipOverview();
//
//		// On Register page
//		Assert.assertTrue(registerPage.isActive(), "User is not in Register page");
//		LoginPage loginPage = registerPage.goToLogin();
//
//		// On Login page
//		Assert.assertTrue(loginPage.isActive(), "User is not in Login page");
//		LoginFacebookPage loginFbPage = loginPage.loginWithFacebook();
//
//		// On Login FB page
//		Assert.assertTrue(loginFbPage.isActive(), "User is not in FB Login page");
//		HomePage homePage = loginFbPage.loginFacebook();
//		
//		// On Home Page
//		Assert.assertTrue(homePage.isActive(), "User is not in Homepage");
//	}
	
	@Test
	public void FRAN_4672_cancelLoginFacebook() throws Exception {
		OverViewPage overViewPage = new OverViewPage(driver);
		
		// On App's Overview
		Assert.assertTrue(overViewPage.isActive(), "User is not in Overview page");
		RegisterPage registerPage = overViewPage.clickSkipOverview();

		// On Register page
		Assert.assertTrue(registerPage.isActive(), "User is not in Register page");
		LoginPage loginPage = registerPage.goToLogin();

		// On Login page
		Assert.assertTrue(loginPage.isActive(), "User is not in Login page");
		LoginFacebookPage loginFbPage = loginPage.loginWithFacebook();

		// On Login FB page
		Assert.assertTrue(loginFbPage.isActive(), "User is not in FB Login page");
		loginPage = loginFbPage.cancelLoginFacebook();
		
		// Back to the Login page
		Assert.assertTrue(loginPage.isActive(), "User is not in Login page");
	}
}