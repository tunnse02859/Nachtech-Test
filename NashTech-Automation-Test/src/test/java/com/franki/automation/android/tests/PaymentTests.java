package com.franki.automation.android.tests;

import static com.franki.automation.utility.Assertion.assertTrue;

import org.testng.annotations.Test;

import com.franki.automation.android.pages.gigs.GigsPage;
import com.franki.automation.android.pages.home.HomePage;
import com.franki.automation.android.pages.login.LoginWithUsernamePage;
import com.franki.automation.android.pages.payment.BanksPage;
import com.franki.automation.android.pages.payment.CashoutPage;
import com.franki.automation.android.pages.payment.PaymentPage;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class PaymentTests extends MobileTestSetup {

	@Test
	public void Verify_Payment_Screen_Is_Displayed_When_Tap_To_Cashout_Money_Button() throws Exception {
		LoginWithUsernamePage loginWithUsernamePage = new LoginWithUsernamePage(driver);
		HomePage homePage = loginWithUsernamePage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
		GigsPage gigsPage = homePage.clickToGigsTab();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		PaymentPage paymentPage = gigsPage.goToPaymentPage();
		paymentPage.skipPreviewPage();
		assertTrue(paymentPage.isPaymentPageDisplayed(), "Payment Page is not displayed", "Payment Page is displayed");
	}

	@Test
	public void Verify_Empty_Screen_Is_Displayed_When_There_Is_No_Transfer() throws Exception {
		LoginWithUsernamePage loginWithUsernamePage = new LoginWithUsernamePage(driver);
		// should use different account to test this function
		HomePage homePage = loginWithUsernamePage.doLoginProcess(Constant.ACCOUNT_EMAIL_2, Constant.ACCOUNT_EMAIL_2);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
		GigsPage gigsPage = homePage.clickToGigsTab();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		PaymentPage paymentPage = gigsPage.goToPaymentPage();
		paymentPage.skipPreviewPage();
		assertTrue(paymentPage.isPaymentPageDisplayed(), "Payment Page is not displayed", "Payment Page is displayed");
		assertTrue(paymentPage.isEmptyScreenDisplayed(), "Empty Screen is not displayed", "Empty Screen is displayed");
	}

	@Test
	public void Verify_Cashout_Button_Is_Displayed() throws Exception {
		LoginWithUsernamePage loginWithUsernamePage = new LoginWithUsernamePage(driver);
		HomePage homePage = loginWithUsernamePage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
		GigsPage gigsPage = homePage.clickToGigsTab();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		PaymentPage paymentPage = gigsPage.goToPaymentPage();
		paymentPage.skipPreviewPage();
		assertTrue(paymentPage.isCashoutBtnDisplayed(), "Cashout Button is not displayed", "Cashout Button is displayed");
	}

	@Test
	public void Verify_No_Money_Image_And_Find_More_Gigs_Btn_Are_Displayed_If_There_Is_No_Money() throws Exception {
		LoginWithUsernamePage loginWithUsernamePage = new LoginWithUsernamePage(driver);
		// should use different account to test this function
		HomePage homePage = loginWithUsernamePage.doLoginProcess(Constant.ACCOUNT_EMAIL_2, Constant.ACCOUNT_PASSWORD_2);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
		GigsPage gigsPage = homePage.clickToGigsTab();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		PaymentPage paymentPage = gigsPage.goToPaymentPage();
		paymentPage.skipPreviewPage();
		assertTrue(paymentPage.isPaymentPageDisplayed(), "Payment Page is not displayed", "Payment Page is displayed");
		CashoutPage cashoutPage = paymentPage.clickToCashoutButton();
		assertTrue(cashoutPage.isNoMoneyDisplayed(), "No Money Image is not displayed", "No Money Image is displayed");
	}

	@Test
	public void Verify_Empty_Screen_And_Add_Banks_Btn_Are_Displayed_When_There_Is_No_Bank() throws Exception {
		LoginWithUsernamePage loginWithUsernamePage = new LoginWithUsernamePage(driver);
		// should use different account to test this function
		HomePage homePage = loginWithUsernamePage.doLoginProcess(Constant.ACCOUNT_EMAIL_2, Constant.ACCOUNT_PASSWORD_2);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
		GigsPage gigsPage = homePage.clickToGigsTab();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		PaymentPage paymentPage = gigsPage.goToPaymentPage();
		paymentPage.skipPreviewPage();
		assertTrue(paymentPage.isPaymentPageDisplayed(), "Payment Page is not displayed", "Payment Page is displayed");
		CashoutPage cashoutPage = paymentPage.clickToCashoutButton();
		BanksPage banksPage = cashoutPage.goToBanksPage();
		assertTrue(banksPage.isEmptyPageAndAddBankBtnDisplayed(), "Empty bank page is not displayed", "Empty bank page is displayed");
	}

	@Test
	public void Verify_User_Is_Able_To_Add_Bank() throws Exception {
		LoginWithUsernamePage loginWithUsernamePage = new LoginWithUsernamePage(driver);
		// should use different account to test this function
		HomePage homePage = loginWithUsernamePage.doLoginProcess(Constant.ACCOUNT_EMAIL_2, Constant.ACCOUNT_PASSWORD_2);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
		GigsPage gigsPage = homePage.clickToGigsTab();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		PaymentPage paymentPage = gigsPage.goToPaymentPage();
		paymentPage.skipPreviewPage();
		assertTrue(paymentPage.isPaymentPageDisplayed(), "Payment Page is not displayed", "Payment Page is displayed");
		CashoutPage cashoutPage = paymentPage.clickToCashoutButton();
		BanksPage banksPage = cashoutPage.goToBanksPage();
		banksPage.addBank(Constant.TestingBankAccount.BANK_USERNAME, Constant.TestingBankAccount.BANK_USERNAME);
	}
}