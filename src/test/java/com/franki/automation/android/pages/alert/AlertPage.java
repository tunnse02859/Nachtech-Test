package com.franki.automation.android.pages.alert;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class AlertPage {
	
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "alertTitle")
	private WebElement lblAlertTitle;
	
	@AndroidFindBy(id = "android:id/message")
	private WebElement lblAlertMessage;
	
	@AndroidFindBy(id = "android:id/button1")
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