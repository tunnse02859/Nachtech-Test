package com.franki.automation.ios.pages.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.appium.driver.AppiumBaseDriver.DIRECTION;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class OnboadingPage {
	public AppiumBaseDriver driver;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"primaryActionButton\"]")
	private WebElement lblOverview1;

	@iOSXCUITFindBy(accessibility = "ALLOW NOTIFICATIONS")
	private WebElement btnAllowNotifications;

	@iOSXCUITFindBy(accessibility = "SHARE MY LOCATION")
	private WebElement btnShareLocation;

	@iOSXCUITFindBy(iOSNsPredicate = "label = \"Allow While Using App\"")
	private WebElement btnLocationAllowWhileUsingApp;
	
	@iOSXCUITFindBy(iOSNsPredicate = "label = \"OK\"")
	private WebElement btnOK;

	@iOSXCUITFindBy(accessibility = "MAYBE LATER")
	private WebElement btnMaybeLater;

	public OnboadingPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(lblOverview1, 10);
	}

	public LoginPage onboardingSuccess() throws Exception {

		if (driver.isElementDisplayed(lblOverview1, 10)) {
			driver.swipe(DIRECTION.LEFT);

			if (driver.isElementDisplayed(btnShareLocation, 3)) {
				driver.tap(btnShareLocation);
				if(driver.isElementDisplayed(btnLocationAllowWhileUsingApp, 3)) {
					driver.tap(btnLocationAllowWhileUsingApp);
				}
				else {
					driver.tap(btnOK);
				}
			}

			driver.swipe(DIRECTION.LEFT);
			driver.swipe(DIRECTION.LEFT);
			driver.swipe(DIRECTION.LEFT);
			driver.swipe(DIRECTION.LEFT);
		}
		return new LoginPage(driver);

	}

}