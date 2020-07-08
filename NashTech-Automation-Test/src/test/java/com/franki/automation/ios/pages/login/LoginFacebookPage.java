package com.franki.automation.ios.pages.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.ios.pages.home.HomePage;
import com.franki.automation.setup.Constant;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginFacebookPage {

	public AppiumBaseDriver driver;
	
	@FindBy(id = "m_login_email")
	private WebElement txtFbUsername;
	
	@FindBy(id = "m_login_password")
	private WebElement txtFbPassword;
	
	@FindBy(xpath = "//*[@text = 'Log in']")
	private WebElement btnFbLogin;
	
	@FindBy(id = "u_0_1")
	private WebElement btnContinueLoginFacebook;
	
	@FindBy(xpath = "//*[@text= 'Cancel']")
	private WebElement btnCancelLoginFacebook;

	@FindBy(id = "close_button")
	private WebElement btnCloseFacebook;
	

	public LoginFacebookPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.getCurrentUrl().contains("https://m.facebook.com");
	}

	public HomePage loginFacebook() throws Exception {
		
		if(driver.isElementDisplayed(txtFbUsername, 5)) {
			driver.inputText(txtFbUsername, Constant.LOGIN_FACEBOOK_USERNAME);
			driver.inputText(txtFbUsername, Constant.LOGIN_FACEBOOK_PASSWORD);
			driver.click(btnFbLogin);
		}
		driver.click(btnContinueLoginFacebook);
		driver.switchToNativeApp();
		return new HomePage(driver);
	}
	
	public LoginPage cancelLoginFacebook() throws Exception {
		
		if(driver.isElementDisplayed(txtFbUsername, 5)) {
			driver.inputText(txtFbUsername, Constant.LOGIN_FACEBOOK_USERNAME);
			driver.inputText(txtFbUsername, Constant.LOGIN_FACEBOOK_PASSWORD);
			driver.click(btnFbLogin);
		}
		Thread.sleep(10000);;
		driver.click(btnCloseFacebook);
		driver.switchToNativeApp();
		return new LoginPage(driver);
	}
}