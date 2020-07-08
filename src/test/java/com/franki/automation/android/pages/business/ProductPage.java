package com.franki.automation.android.pages.business;

import static com.franki.automation.utility.Assertion.assertEquals;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ProductPage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "tvBusinessName")
	private WebElement businessName;

	@AndroidFindBy(id = "tvProductName")
	private WebElement prodName;

	@AndroidFindBy(id = "tvFollow")
	private WebElement btnFollow;

	@AndroidFindBy(id = "android:id/button1")
	private WebElement btnOK;

	public ProductPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public String getBusinessName() throws Exception {
		return driver.getText(businessName);
	}

	public String getProductName() throws Exception {
		return driver.getText(prodName);
	}

	public void verifyProductPageIsDisplayed(String bizName, String prodName) throws Exception {
		String actualBizName = getBusinessName();
		String actualProdName = getProductName();
		assertEquals(actualBizName, bizName);
		assertEquals(actualProdName, prodName);
	}

	public String getFollowStatus() throws Exception {
		return driver.getText(btnFollow);
	}

	public void clickToFollowProduct() throws Exception {
		String status = getFollowStatus();
		if (status.equalsIgnoreCase("FOLLOW")) {
			driver.click(btnFollow);
		}
	}

	public void clickToUnfollowProduct() throws Exception {
		String status = getFollowStatus();
		if (status.equalsIgnoreCase("UNFOLLOW")) {
			driver.click(btnFollow);
			driver.waitForElementDisplayed(btnOK, 10);
			driver.click(btnOK);
		}
	}

	public void verifyProductIsFollowed() throws Exception {
		String status = getFollowStatus();
		assertEquals(status, "UNFOLLOW", "Product is not followed", "Product is followed successfully");
	}

	public void verifyProductIsUnfollowed() throws Exception {
		String status = getFollowStatus();
		assertEquals(status, "FOLLOW", "Product is not unfollowed", "Product is unfollowed successfully");
	}
}