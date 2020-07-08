package com.franki.automation.android.pages.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.android.pages.home.HomePage;
import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginWithUsernamePage {

	public AppiumBaseDriver driver;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Mobile Number Or Email *\")")
	private WebElement txtEmailOrPhone;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Password\")")
	private WebElement txtPassword;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(3)")
	private WebElement btnNext;

	@AndroidFindBy(id = "android:id/message")
	private WebElement popupForgotPwd;

	@AndroidFindBy(id = "button_next")
	private WebElement btnNextInForgotPwd;

	public LoginWithUsernamePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(txtEmailOrPhone);
	}

	public HomePage doLoginProcess(String username, String password) throws Exception {
		OverViewPage overViewPage = new OverViewPage(driver);

		// On App's Overview
		RegisterPage registerPage = overViewPage.clickSkipOverview();

		// On Register page
		LoginPage loginPage = registerPage.goToLogin();

		// On Login page
		LoginWithUsernamePage loginWithUsernamePage = loginPage.loginWithUsername();

		// On Login With Email or Phone page
		loginWithUsernamePage.login(username, password);

		return new HomePage(driver);
	}

	public void login(String username, String password) throws Exception {
		driver.inputText(txtEmailOrPhone, username);
		driver.click(btnNext);
		driver.inputText(txtPassword, password);
		driver.click(btnNext);
	}

	public void goToResetPassword() throws Exception {
		OverViewPage overViewPage = new OverViewPage(driver);

		// On App's Overview
		RegisterPage registerPage = overViewPage.clickSkipOverview();

		// On Register page
		LoginPage loginPage = registerPage.goToLogin();
		loginPage.clickToForgotPwd();
	}

	public void inputEmailToRecoverPwd(String email) throws Exception {
		driver.inputText(txtEmailOrPhone, email);
		driver.click(btnNextInForgotPwd);
	}

	public boolean isPasswordResetOrNot() {
		return driver.isElementDisplayed(popupForgotPwd, 30);
	}

}