package com.franki.automation.android.pages.home;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.android.pages.explore.ExplorePage;
import com.franki.automation.android.pages.gigs.GigsPage;
import com.franki.automation.android.pages.profile.CurrentUserProfilePage;
import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BottomBarPage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "vgTabExplore")
	private WebElement btnExploreTab;

	@AndroidFindBy(id = "vgTabHome")
	private WebElement btnHomeTab;

	@AndroidFindBy(id = "vgTabGigs")
	private WebElement btnGigsTab;

	@AndroidFindBy(id = "btn_profile_action")
	private WebElement btnProfileTab;

	public BottomBarPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(btnProfileTab);
	}

	public void clickToHomeTab() throws Exception {
		driver.click(btnHomeTab);
	}

	public CurrentUserProfilePage clickToProfileTab() throws Exception {
		driver.click(btnProfileTab);
		return new CurrentUserProfilePage(driver);
	}

	public ExplorePage clickToExploreTab() throws Exception {
		driver.click(btnExploreTab);
		return new ExplorePage(driver);
	}

	public GigsPage clickToGigsTab() throws Exception {
		driver.click(btnGigsTab);
		return new GigsPage(driver);
	}
}