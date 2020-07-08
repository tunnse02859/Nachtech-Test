package com.franki.automation.ios.pages.business;

import org.openqa.selenium.WebElement;
import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.setup.Constant;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BusinessRatingsPage extends BusinessProfilePage{

	public BusinessRatingsPage(AppiumBaseDriver driver) {
		super(driver);
	}

	@iOSXCUITFindBy(xpath = "//*[@value='Food']/../*[@name='percentageLabel']")
	private WebElement lblFoodRatingPercent;

	@iOSXCUITFindBy(xpath = "//*[@value='Drinks']/../*[@name='percentageLabel']")
	private WebElement lblDrinksRatingPercent;
	
	@iOSXCUITFindBy(xpath = "//*[@value='Service']/../*[@name='percentageLabel']")
	private WebElement lblServiceRatingPercent;

	@iOSXCUITFindBy(xpath = "//*[@value='Overall']/../*[@name='percentageLabel']")
	private WebElement lblOverallRatingPercent;
	
	public Boolean isOnBusinessRatingsPage() {
		return Boolean.valueOf(driver.getAttribute(tabRatings, "selected"));
	}

	public String getFoodRatingPercent() throws Exception {
		String foodPercent = driver.getText(lblFoodRatingPercent);
		return foodPercent.equals(Constant.NOT_AVAILABLE) ? "" : foodPercent;
	}

	public String getDrinksRatingPercent() throws Exception {
		String drinkPercent = driver.getText(lblDrinksRatingPercent);
		return drinkPercent.equals(Constant.NOT_AVAILABLE) ? "" : drinkPercent;
	}
	
	public String getServiceRatingPercent() throws Exception {
		String servicePercent = driver.getText(lblServiceRatingPercent);
		return servicePercent.equals(Constant.NOT_AVAILABLE) ? "" : servicePercent;
	}
	
	public String getOverallRatingPercent() throws Exception {
		String overallPercent = driver.getText(lblOverallRatingPercent);
		return overallPercent.equals(Constant.NOT_AVAILABLE) ? "" : overallPercent;
	}
}