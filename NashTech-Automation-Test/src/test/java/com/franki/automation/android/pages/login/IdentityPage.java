package com.franki.automation.android.pages.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class IdentityPage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "edit_text_pin")
	private WebElement txtPIN;

	@AndroidFindBy(id = "button_done")
	private WebElement btnNext;

	public IdentityPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(txtPIN);
	}

	public PasswordPage clickNextBtn() throws Exception {
		driver.click(btnNext);
		return new PasswordPage(driver);
	}
}
