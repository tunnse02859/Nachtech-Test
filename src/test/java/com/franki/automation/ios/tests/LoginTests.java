package com.franki.automation.ios.tests;

import static com.franki.automation.utility.Assertion.*;
import org.testng.annotations.Test;

import com.franki.automation.ios.pages.alert.AlertPage;
import com.franki.automation.ios.pages.home.BottomBarPage;
import com.franki.automation.ios.pages.login.LoginWithUsernamePage;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class LoginTests extends MobileTestSetup {
	

	@Test
	public void FRAN_5435_login_With_Valid_Credential() throws Exception {
		LoginWithUsernamePage loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1,Constant.ACCOUNT_PASSWORD_1);
		BottomBarPage bttomBar = new BottomBarPage(driver);
		assertTrue(bttomBar.isActive(), "User is not in Homepage","You're in Home page");
	}
	
	@Test
	public void FRAN_5436_login_With_Invalid_Credential() throws Exception {
		LoginWithUsernamePage loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess("invalidusernamehere@gmail.com","Password123");
		// Check alert
		AlertPage alert = new AlertPage(driver);
		assertEquals(alert.getAlertTitle(), "Oops","Error title is displayed incorrectly", "Error title is displayed correctly");
		assertEquals(alert.getAlertMessage(), "Your username or password is incorrect. Please try again.","Error message is displayed incorrectly","Error message is displayed correctly");
	}

}