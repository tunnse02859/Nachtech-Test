package com.franki.automation.android.pages.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.android.pages.home.HomePage;
import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PasswordPage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Password\")")
	private WebElement txtPassword;

	@AndroidFindBy(id = "btn_next")
	private WebElement btnNext;

	public PasswordPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(txtPassword);
	}
	
	public HomePage clickNextBtn() throws Exception {
		driver.click(btnNext);
		return new HomePage(driver);
	}
	
	public void inputNewPassword(String newPassword) throws Exception {
		driver.inputText(txtPassword, newPassword);
	}
}
