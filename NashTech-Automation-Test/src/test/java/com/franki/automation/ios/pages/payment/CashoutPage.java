package com.franki.automation.ios.pages.payment;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CashoutPage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "settings_action")
	private WebElement btnSetting;

	@AndroidFindBy(id = "tv_transfer_bank")
	private WebElement txtTransferBank;

	@AndroidFindBy(id = "bt_find_more_gigs")
	private WebElement btnFindMoreGigs;

	@AndroidFindBy(id = "iv_transfer_empty")
	private WebElement imageTransferEmpty;

	@AndroidFindBy(id = "ivEmptyStateImage")
	private WebElement imageEmptyState;

	public CashoutPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isNoMoneyDisplayed() {
		return driver.isElementDisplayed(btnFindMoreGigs) && driver.isElementDisplayed(imageTransferEmpty);
	}

	public BanksPage goToBanksPage() throws Exception {
		driver.click(btnSetting);
		return new BanksPage(driver);
	}
}