package com.franki.automation.android.pages.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class OverViewPage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "tvSkipDone")
	private WebElement btnSkipOverview;

	public OverViewPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(btnSkipOverview);
	}
	public RegisterPage clickSkipOverview() throws Exception {
		driver.click(btnSkipOverview);
		return new RegisterPage(driver);
	}

}