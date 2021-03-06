package com.franki.automation.android.pages.common;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.franki.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PermissionManager {
	public static AppiumBaseDriver driver;
	
	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
	private static WebElement btnAllowAccessLocation;

	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_deny_button")
	private static WebElement btnDenyAccessLocation;

	public PermissionManager(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public static void allowAccessLocation() throws Exception {
		if (driver.isElementDisplayed(btnAllowAccessLocation, 20)) {
			driver.click(btnAllowAccessLocation);
		}
	}

	public static void denyAccessLocation() throws Exception {
		driver.setDefaultImplicitWaitTime();
		if (driver.isElementDisplayed(btnDenyAccessLocation, 20)) {
			driver.click(btnDenyAccessLocation);
		}
	}
}