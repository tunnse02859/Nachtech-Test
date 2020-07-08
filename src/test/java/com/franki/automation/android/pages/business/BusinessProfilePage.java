package com.franki.automation.android.pages.business;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BusinessProfilePage {

	public AppiumBaseDriver driver;
	
	@AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]")
	private WebElement btnBack;
	
	@AndroidFindBy(id = "post_content")
	private WebElement businessType;

	@AndroidFindBy(id = "post_user_name")
	private WebElement businessDisplayName;
	
	@AndroidFindBy(id = "tv_description")
	private WebElement businessTagline;
	
	@AndroidFindBy(id = "tv_address")
	private WebElement businessAddress;

	@AndroidFindBy(id = "tvFollow")
	private WebElement btnFollow;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Are you sure, you want to unfollow this user?\")")
	private WebElement popupUnfollow;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"OK\")")
	private WebElement btnConfirmPopup;

	@AndroidFindBy(id = "tvProductsLabel")
	private WebElement productsTab;

	@AndroidFindBy(id = "tvName")
	private List<WebElement> listProduct;
	
	@AndroidFindBy(id = "abc")
	protected WebElement tabGigs;
	
	@AndroidFindBy(id = "abc")
	protected WebElement btnBook;

	public BusinessProfilePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(businessDisplayName);
	}

	public void clickFollow_Unfollow() throws Exception {
		driver.click(btnFollow);
	}

	public String getBusinessDisplayedName() throws Exception {
		return driver.getText(businessDisplayName);
	}
	
	public String getBusinessType() throws Exception {
		return driver.getText(businessType);
	}
	
	public String getBusinessTagline() throws Exception {
		return driver.getText(businessTagline);
	}
	
	public void clickOnBack() throws Exception {
		driver.click(btnBack);
	}
	
	public String getBusinessAddress() throws Exception {
		return driver.getText(businessAddress);
	}

	public boolean isConfirmUnfollowPopupDisplayed() {
		return driver.isElementDisplayed(popupUnfollow);
	}

	public void confirmUnfollow() throws Exception {
		driver.click(btnConfirmPopup);
	}

	public String getFollowStatus() throws Exception {
		return driver.getText(btnFollow);
	}

	public void clickToProductsTab() throws Exception {
		driver.click(productsTab);
	}

	public ProductPage selectProductInProductsListByIndex(int index) throws Exception {
		driver.click(listProduct.get(index));
		return new ProductPage(driver);
	}
	
	public BusinessBookPage clickOnBookButton() throws Exception {
		driver.click(btnBook);
		return new BusinessBookPage(driver);
	}
}