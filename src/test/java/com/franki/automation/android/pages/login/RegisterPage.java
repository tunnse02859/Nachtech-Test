package com.franki.automation.android.pages.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class RegisterPage {
	
	public AppiumBaseDriver driver;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Continue with Facebook\")")
	private WebElement btnRegisterByFacebook;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Continue with Google\")")
	private WebElement btnRegisterByGoogle;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(4)")
	private WebElement btnRegisterByEmailOrPhone;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(5)")
	private WebElement btnGoToLogin;
	
	public RegisterPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(btnGoToLogin);
	}

	public LoginPage goToLogin() throws Exception {
		driver.click(btnGoToLogin);
		return new LoginPage(driver);
	}
	
	public SignupPage goToSignup() throws Exception {
		driver.click(btnRegisterByEmailOrPhone);
		return new SignupPage(driver);
	}
}