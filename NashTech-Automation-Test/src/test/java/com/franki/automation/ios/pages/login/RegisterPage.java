package com.franki.automation.ios.pages.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class RegisterPage {
	
	public AppiumBaseDriver driver;

	@iOSXCUITFindBy(accessibility = "Continue with Facebook")
	private WebElement btnLoginByFacebook;

	@iOSXCUITFindBy(accessibility = "Continue with Google")
	private WebElement btnLoginByGoogle;

	@iOSXCUITFindBy(accessibility = "Use Phone or Email")
	private WebElement btnLoginByEmailOrPhone;

	@iOSXCUITFindBy(accessibility = "Already have an account? Login.")
	private WebElement btnGotoLogin;

	public RegisterPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(btnGotoLogin);
	}


	public LoginPage goToLogin() throws Exception {
		driver.click(btnGotoLogin);
		return new LoginPage(driver);
	}
}