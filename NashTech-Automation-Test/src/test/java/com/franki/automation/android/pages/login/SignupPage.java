package com.franki.automation.android.pages.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SignupPage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "input_user_name")
	private WebElement inputUsername;

	@AndroidFindBy(id = "button_next")
	private WebElement btnNext;

	@AndroidFindBy(id = "android:id/message")
	private WebElement txtMessage;

	@AndroidFindBy(id = "android:id/button1")
	private WebElement btnOK;

	@AndroidFindBy(id = "edit_text_pin")
	private WebElement txtPIN;

	public SignupPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(inputUsername);
	}

	public void inputMobileNumberOrEmail(String inputText) throws Exception {
		driver.clearText(inputUsername);
		driver.inputText(inputUsername, inputText);
	}

	public IdentityPage clickNextBtn() throws Exception {
		driver.click(btnNext);
		return new IdentityPage(driver);
	}

	public boolean verifyPopupMessageDisplayed() {
		driver.waitForElementDisplayed(txtMessage, 10);
		return driver.isElementDisplayed(txtMessage);
	}

	public String getPopupMessageDisplay() throws Exception {
		return driver.waitForTextElementPresent(txtMessage, 20);
	}

	public void acceptPopupDisplay() throws Exception {
		driver.click(btnOK);
	}
}
