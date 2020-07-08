package com.franki.automation.ios.pages.business;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BusinessGigsPage extends BusinessProfilePage{

	public BusinessGigsPage(AppiumBaseDriver driver) {
		super(driver);
	}

	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> listGigs;
	
	By lblGigClosingInfo = MobileBy.name("closingInfoLabel");
	By lblGigName = MobileBy.name("titleLabel");
	By lblGigDescription = MobileBy.name("descriptionLabel");
	By lblGigAvailablePlace = MobileBy.name("bottomLabel");
	By lblGigAmount = MobileBy.name("amountButton");
	
	public Boolean isOnBusinessGigsPage() {
		return Boolean.valueOf(driver.getAttribute(tabGigs, "selected"));
	}
	
}