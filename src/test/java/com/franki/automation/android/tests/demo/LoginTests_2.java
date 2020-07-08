package com.franki.automation.android.tests.demo;

import static com.franki.automation.utility.Assertion.*;
import org.testng.annotations.Test;

import com.franki.automation.android.pages.home.HomePage;
import com.franki.automation.android.pages.login.LoginWithUsernamePage;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup1;

public class LoginTests_2 extends MobileTestSetup1 {


	@Test
	public void FRAN_5435_login_With_Valid_Credential() throws Exception {
		LoginWithUsernamePage loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		HomePage homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
	}


}