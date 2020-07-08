package com.franki.automation.ios.pages.home;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.appium.driver.AppiumBaseDriver.DIRECTION;
import com.franki.automation.ios.pages.profile.CurrentUserProfilePage;
import com.franki.automation.ios.pages.recording.RecordingPage;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BottomBarPage {

	public AppiumBaseDriver driver;

	@iOSXCUITFindBy(accessibility = "discover")
	private WebElement tabHome;

	@iOSXCUITFindBy(accessibility = "explore")
	private WebElement tabExplore;

	@iOSXCUITFindBy(accessibility = "contentCreation")
	private WebElement tabBeFrank;

	@iOSXCUITFindBy(accessibility = "gigs")
	private WebElement tabGigs;

	@iOSXCUITFindBy(accessibility = "profile")
	private WebElement tabProfile;

	public BottomBarPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
//		return driver.isElementDisplayed(tabHome) && driver.isElementDisplayed(tabExplore)
//				&& driver.isElementDisplayed(tabBeFrank) && driver.isElementDisplayed(tabGigs)
//				&& driver.isElementDisplayed(tabProfile);
		return driver.isElementDisplayed(tabHome);
	}

	public HomePage clickOnHomeTab() throws Exception {
		driver.click(tabHome);
		driver.swipe(DIRECTION.DOWN);
		return new HomePage(driver);
	}

	public void clickOnExploreTab() throws Exception {
		driver.click(tabExplore);
	}

	public RecordingPage clickOnBeFrankTab() throws Exception {
		driver.click(tabBeFrank);
		return new RecordingPage(driver);
	}

	public void clickOnGigsTab() throws Exception {
		driver.click(tabGigs);
	}

	public CurrentUserProfilePage clickOnProfileTab() throws Exception {
		for(int i = 0; i < 10; i++) {
			driver.click(tabProfile);
			if(isOnProfileTab()) {
				break;
			}
			driver.sleep(1);
		}
		
		return new CurrentUserProfilePage(driver);
	}
	
	public boolean isOnProfileTab() {
		return Boolean.valueOf(driver.getAttribute(tabProfile, "selected"));
	}

}