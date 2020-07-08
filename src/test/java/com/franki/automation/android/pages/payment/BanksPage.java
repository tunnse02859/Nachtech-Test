package com.franki.automation.android.pages.payment;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BanksPage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "bt_add_bank")
	private WebElement btnAddBank;

	@AndroidFindBy(id = "tv_title")
	private WebElement txtTitle;

	@AndroidFindBy(id = "i_empty_state")
	private WebElement emptyStateElement;

	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View[1]")
	private WebElement firstBank;

	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[4]/android.view.View[1]/android.view.View[1]")
	WebElement savingAccount;

	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[4]/android.view.View[2]/android.widget.Button")
	WebElement btnContinueAccount;

	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[4]/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.widget.EditText")
	private WebElement username;

	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[4]/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.widget.EditText")
	private WebElement password;

	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[4]/android.view.View/android.view.View/android.widget.Button")
	private WebElement btnSubmit;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"continue\")")
	private WebElement btnContinue;

	public BanksPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public void clickToAddBankBtn() throws Exception {
		driver.click(btnAddBank);
	}

	public boolean isEmptyPageAndAddBankBtnDisplayed() {
		return driver.isElementDisplayed(btnAddBank) && driver.isElementDisplayed(emptyStateElement);
	}

	public void addBank(String bankUsername, String bankPassword) throws Exception {
		driver.isElementDisplayed(btnAddBank, 10);
		driver.click(btnAddBank);
		driver.isElementDisplayed(btnContinue, 20);
		driver.click(btnContinue);
		driver.click(firstBank);
		driver.waitForElementDisplayed(username, 30);
		driver.inputText(username, bankUsername);
		driver.inputText(password, bankPassword);
		driver.click(btnSubmit);
		driver.waitForElementDisplayed(savingAccount, 30);
		driver.click(savingAccount);
		driver.click(btnContinueAccount);
	}
}