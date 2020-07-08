package com.franki.automation.ios.pages.login;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginWithUsernamePage {

	public AppiumBaseDriver driver;

	@iOSXCUITFindBy(accessibility = "identityInputField")
	private WebElement txtEmailOrPhone;

	@iOSXCUITFindBy(accessibility = "passwordInputField")
	private WebElement txtPassword;

	@iOSXCUITFindBy(accessibility = "submitButton")
	private WebElement btnNext;

	// Touch id alert
	@iOSXCUITFindBy(iOSNsPredicate = "name = \"TouchID\"")
	private List<WebElement> iconTouchID;

	@iOSXCUITFindBy(iOSNsPredicate = "name = \"Cancel\"")
	private List<WebElement> btnCancelTouchID;

	public LoginWithUsernamePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(txtEmailOrPhone);
	}

	public void doLoginProcess(String username, String password) throws Exception {
		OnboadingPage overViewPage = new OnboadingPage(driver);
		LoginPage loginPage = null;

		loginPage = overViewPage.onboardingSuccess();
		
		if(loginPage.isActive()) {
			// On Login page
			LoginWithUsernamePage loginWithUsernamePage = loginPage.loginWithUsername();

			// On Login With Email or Phone page
			loginWithUsernamePage.login(username, password);
		}
		

	}

	public void login(String username, String password) throws Exception {
		// Set username
		driver.inputText(txtEmailOrPhone, username);
		driver.click(btnNext);
		driver.dismissAlert();
		// Cancel TouchID
		// if(driver.findDisplayElement(btnCancelTouchID) != null) {
		// driver.findDisplayElement(btnCancelTouchID).click();
		// }
		// Set password
		driver.inputText(txtPassword, password);
		driver.click(btnNext);
	}

}