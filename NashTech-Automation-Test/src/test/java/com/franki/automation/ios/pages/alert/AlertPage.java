package com.franki.automation.ios.pages.alert;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AlertPage {
	
	public AppiumBaseDriver driver;

	@iOSXCUITFindBy(iOSNsPredicate = "name = \"Oops\"")
	private WebElement lblAlertTitle;
	
	@iOSXCUITFindBy(accessibility = "Your username or password is incorrect. Please try again.")
	private WebElement lblAlertMessage;
	
	@iOSXCUITFindBy(accessibility = "OK")
	private WebElement btnOK;

	public AlertPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isAlertPresent();
	}

	public String getAlertTitle() throws Exception {
		return driver.getText(lblAlertTitle);
	}
	
	public String getAlertMessage() throws Exception {
		return driver.getText(lblAlertMessage);
	}
	
	public void acceptAlert() throws Exception {
		driver.click(btnOK);
	}

}