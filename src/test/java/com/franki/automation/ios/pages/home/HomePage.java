package com.franki.automation.ios.pages.home;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.ios.pages.notifications.NotificationPage;
import com.franki.automation.ios.pages.review.ReviewDetailPage;
import com.franki.automation.ios.pages.search.HomeSearchPage;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HomePage {
	public AppiumBaseDriver driver;

	@iOSXCUITFindBy(accessibility = "search")
	private WebElement icSearch;

	@iOSXCUITFindBy(accessibility = "notifications")
	private WebElement icNotification;
	
	/************* Promotion **********************/
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[1]//XCUIElementTypeStaticText")
	private WebElement lblPromotionTitle;
	
	/******* Carousel Near You ******************/
	
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Near You'")
	private WebElement lblNearYou;
	
	@iOSXCUITFindBy(xpath = "//*[@name='Near You']/following-sibling:: XCUIElementTypeCollectionView/XCUIElementTypeCell")
	private List<WebElement> listNearYouReviews;
	
	/******* Carousel Trending In Your City ******************/
	
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Trending In Your City'")
	private WebElement lblTrendingInYourCity;
	
	@iOSXCUITFindBy(xpath = "//*[@name='Trending In Your City']/following-sibling:: XCUIElementTypeCollectionView/XCUIElementTypeCell")
	private List<WebElement> listTrendingInYourCityReviews;
	
	/******* Carousel Trending ******************/
	
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Trending'")
	private WebElement lblTrending;
	
	@iOSXCUITFindBy(xpath = "//*[@name='Trending']/following-sibling:: XCUIElementTypeCollectionView/XCUIElementTypeCell")
	private List<WebElement> listTrendingReviews;

	public HomePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		 return driver.isElementDisplayed(icSearch) && driver.isElementDisplayed(icNotification);
	}

	public HomeSearchPage clickOnSearchIcon() throws Exception {
		driver.tap(icSearch);
		return new HomeSearchPage(driver);
	}

	public NotificationPage clickOnNotificationIcon() throws Exception {
		driver.tap(icNotification);
		return new NotificationPage(driver);
	}
	
	/********** Promotion ************************/

	public String getPromotionTitle() throws Exception {
		return driver.getText(lblPromotionTitle);
	}
	
	/********** Near You ************************/

	public boolean isNearYouCategoriesDisplayed() {
		return listNearYouReviews.size() > 0;
	}
	
	public int getSizeOfNearYouList() {
		return listNearYouReviews.size();
	}
	
	public ReviewDetailPage clickNearYouReview(int index) throws Exception {
		if(index < listNearYouReviews.size()) {
			listNearYouReviews.get(index).click();
			
			return new ReviewDetailPage(driver);
		}
		else {
			throw new Exception("Out of size Near You");
		}
	}
	
	/********** Trending In Your City ************************/

	public boolean isTrendingInYourCityCategoriesDisplayed() {
		return listTrendingInYourCityReviews.size() > 0;
	}
	
	public int getSizeOfTrendingInYourCityList() {
		return listTrendingInYourCityReviews.size();
	}
	
	public ReviewDetailPage clickTrendingInYourCityReview(int index) throws Exception {
		if(index < listTrendingInYourCityReviews.size()) {
			listTrendingInYourCityReviews.get(index).click();
			
			return new ReviewDetailPage(driver);
		}
		else {
			throw new Exception("Out of size Trending In Your City");
		}
	}

	/********** Trending ************************/
	
	public boolean isTrendingCategoriesDisplayed() {
		return listTrendingReviews.size() > 0;
	}

	public int getSizeOfTrendingList() {
		return listTrendingReviews.size();
	}
	
	public ReviewDetailPage clickTrendingReview(int index) throws Exception {
		if(index < listTrendingReviews.size()) {
			listTrendingReviews.get(index).click();
			driver.sleep(5);
			return new ReviewDetailPage(driver);
		}
		else {
			throw new Exception("Out of size Trending");
		}
	}

}