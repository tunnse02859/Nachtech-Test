package com.franki.automation.android.pages.business;

import org.openqa.selenium.WebElement;
import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BusinessBookPage extends BusinessProfilePage{


	public BusinessBookPage(AppiumBaseDriver driver) {
		super(driver);
	}
	
	@iOSXCUITFindBy(accessibility = "phoneButton")
	private WebElement btnPhone;
	
	@iOSXCUITFindBy(accessibility = "websiteButton")
	private WebElement btnWebsite;
	
	@iOSXCUITFindBy(accessibility = "addressButton")
	private WebElement btnAddress;
	
	public Boolean isOnBusinessBookPage() {
		return driver.isElementDisplayed(btnPhone, 20);
	}
	
	public String getBusinessPhone() throws Exception {
		return driver.getText(btnPhone.findElement(MobileBy.className("XCUIElementTypeStaticText")));
	}
	
	public String getBusinessAddress() throws Exception {
		return driver.getText(btnAddress.findElement(MobileBy.className("XCUIElementTypeStaticText")));
	}
	
	public String getBusinessWebsite() throws Exception {
		return driver.getText(btnWebsite.findElement(MobileBy.className("XCUIElementTypeStaticText")));
	}

}