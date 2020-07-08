package com.franki.automation.ios.pages.payment;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class PaymentPage {
	public AppiumBaseDriver driver;

	/*********** General info *************/

	@iOSXCUITFindBy(accessibility = "cashOutButton")
	private WebElement btnCashOut;

	@iOSXCUITFindBy(accessibility = "moneyInBankLabel")
	private WebElement lblTotalCashInBank;

	@iOSXCUITFindBy(accessibility = "gigEnteredLabel")
	private WebElement lblGigEnteredCount;

	@iOSXCUITFindBy(accessibility = "totalEarningLabel")
	private WebElement lblGigEarning;

	@iOSXCUITFindBy(accessibility = "gigsAroundAmountLabel")
	private WebElement lblGigAroungAmount;

	/*********** 3 gigs tab *************/

	@iOSXCUITFindBy(accessibility = "active gigs")
	private WebElement tabActiveGigs;

	@iOSXCUITFindBy(accessibility = "past gigs")
	private WebElement tabPastGigs;

	@iOSXCUITFindBy(accessibility = "saved gigs")
	private WebElement tabSavedGigs;

	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> gigList;

	private By lblGigName = MobileBy.AccessibilityId("titleLabel");

	private By lblPrizesInfo = MobileBy.AccessibilityId("descriptionLabel");

	private By lblClosingInfo = MobileBy.AccessibilityId("closingInfoLabel");

	private By lblGigStatus = MobileBy.AccessibilityId("statusInfoButton");

	public PaymentPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isOnPaymentPage() {
		return driver.isElementDisplayed(btnCashOut) && driver.isElementDisplayed(lblTotalCashInBank)
				&& driver.isElementDisplayed(lblGigEnteredCount) && driver.isElementDisplayed(lblGigEarning)
				&& driver.isElementDisplayed(lblGigAroungAmount) && driver.isElementDisplayed(tabActiveGigs)
				&& driver.isElementDisplayed(tabPastGigs) && driver.isElementDisplayed(tabSavedGigs);
	}

	public boolean isCashoutBtnDisplayed() {
		return driver.isElementDisplayed(btnCashOut);
	}

	public CashoutPage clickToCashoutButton() throws Exception {
		driver.click(btnCashOut);
		return new CashoutPage(driver);
	}
}