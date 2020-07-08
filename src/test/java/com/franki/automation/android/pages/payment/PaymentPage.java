package com.franki.automation.android.pages.payment;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PaymentPage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "btDone")
	private WebElement btnSkip;

	@AndroidFindBy(id = "btCashOut")
	private WebElement btnCashOut;

	@AndroidFindBy(id = "tvCashCount")
	private WebElement totalCash;

	@AndroidFindBy(id = "tvgigsCount")
	private WebElement gigsCount;

	@AndroidFindBy(id = "tvEarningsCount")
	private WebElement earningCount;

	@AndroidFindBy(id = "vLocalEarningsClickableArea")
	private WebElement localEarning;

	@AndroidFindBy(id = "btActiveGigs")
	private WebElement btActiveGigs;

	@AndroidFindBy(id = "btPastGigs")
	private WebElement btPastGigs;

	@AndroidFindBy(id = "btSavedGigs")
	private WebElement btSavedGigs;

	@AndroidFindBy(id = "viewpager")
	private WebElement gigsDisplayElement;

	@AndroidFindBy(id = "emptyview")
	private WebElement emptyPageElement;

	public PaymentPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public void skipPreviewPage() throws Exception {
		if (driver.isElementDisplayed(btnSkip)) {
			driver.click(btnSkip);
		}
	}

	public boolean isPaymentPageDisplayed() throws Exception {
		skipPreviewPage();
		return driver.isElementDisplayed(btnCashOut) && driver.isElementDisplayed(totalCash) && driver.isElementDisplayed(gigsCount) && driver.isElementDisplayed(earningCount) && driver.isElementDisplayed(localEarning) && driver.isElementDisplayed(btPastGigs) && driver.isElementDisplayed(btActiveGigs) && driver.isElementDisplayed(btSavedGigs) && driver.isElementDisplayed(gigsDisplayElement);
	}

	public boolean isEmptyScreenDisplayed() {
		return driver.isElementDisplayed(emptyPageElement);
	}

	public boolean isCashoutBtnDisplayed() {
		return driver.isElementDisplayed(btnCashOut);
	}

	public CashoutPage clickToCashoutButton() throws Exception {
		driver.click(btnCashOut);
		return new CashoutPage(driver);
	}
}