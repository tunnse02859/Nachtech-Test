package com.franki.automation.android.pages.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage {

	public AppiumBaseDriver driver;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Continue with Facebook\")")
	private WebElement btnLoginByFacebook;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Continue with Google\")")
	private WebElement btnLoginByGoogle;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(4)")
	private WebElement btnLoginByEmailOrPhone;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(5)")
	private WebElement btnForgotPwd;

	public LoginPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		// Need to check this later
		return true;
	}

	public LoginFacebookPage loginWithFacebook() throws Exception {
		driver.click(btnLoginByFacebook);
		driver.switchToWebView();
		return new LoginFacebookPage(driver);
	}

	public LoginWithUsernamePage loginWithUsername() throws Exception {
		driver.click(btnLoginByEmailOrPhone);
		return new LoginWithUsernamePage(driver);
	}

	public void clickToForgotPwd() throws Exception {
		driver.click(btnForgotPwd);
	}

}