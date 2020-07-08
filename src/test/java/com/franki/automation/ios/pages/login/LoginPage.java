package com.franki.automation.ios.pages.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginPage {

	public AppiumBaseDriver driver;

	@iOSXCUITFindBy(accessibility = "Continue with Facebook")
	private WebElement btnLoginByFacebook;

	@iOSXCUITFindBy(accessibility = "Continue with Google")
	private WebElement btnLoginByGoogle;

	@iOSXCUITFindBy(accessibility = "Use Phone or Email")
	private WebElement btnLoginByEmailOrPhone;

	@iOSXCUITFindBy(accessibility = "Don't have an account? Sign Up.")
	private WebElement btnGotoSignUp;

	public LoginPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(btnGotoSignUp);
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

}