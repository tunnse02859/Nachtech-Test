package com.franki.automation.ios.pages.recording;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.ios.pages.review.ReviewDetailPage;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class CreateOrEditReviewPage {

	public AppiumBaseDriver driver;
	
	/***** Define constants in Create/Edit Post Summary *********/
	
	public interface OCCASION_TYPE {
		public static final String YES = "Yes";
		public static final String NO = "No";
		public static final String UNKNOWN = "Unknown";
	}
	
	public interface ATTRIBUTE_TYPE {
		public static final String YES = "Yes";
		public static final String NO = "No";
		public static final String UNKNOWN = "Unknown";
		
		public static final String CHEAP = "1";
		public static final String NORMAL = "2";
		public static final String EXPENSIVE = "3";
		public static final String VERY_EXPENSIVE = "4";
	}
	
	public interface RATING_TYPE {
		public static final String SATISFY = "SATISFY";
		public static final String UNSATISFY = "UNSATISFY";
	}
	
	public interface AMBIENCES {
		public static final String NOISY = "NOISY";
		public static final String CASUAL = "CASUAL";
		public static final String QUIET = "QUIET";
		public static final String CLASSY = "CLASSY";
		public static final String TRENDY = "TRENDY";
		public static final String TOURISTY = "TOURISTY";
		public static final String INTIMATE = "INTIMATE";
		public static final String DARK = "DARK";
		public static final String WELL_LIT = "WELL_LIT";
		public static final String FRIENDLY = "FRIENDLY";
	}

	/***** Select Business *********/

	// Label "Select Business"
	@iOSXCUITFindBy(accessibility = "SELECT BUSINESS")
	WebElement lblSelectBusiness;

	// Select Business text box
	@iOSXCUITFindBy(xpath = "//*[@name='SELECT BUSINESS']/following-sibling::*//XCUIElementTypeTextField")
	WebElement txtSelectBusinessTextBox;

	/***** Search Business *********/
	// Search business text box
	@iOSXCUITFindBy(accessibility = "Search")
	WebElement txtSearchBusiness;

	// The list of searched business
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	List<WebElement> listBusinessSearchResults;

	By searchedBusinessName = MobileBy.name("businessNameLabel");
	By searchedBusinessAddress = MobileBy.name("addressLabel");
	By searchedBusinessType = MobileBy.name("captionLabel");

	/***** Caption *********/

	// Label "CAPTION"
	@iOSXCUITFindBy(accessibility = "CAPTION")
	WebElement lblCaption;

	// Caption text box
	@iOSXCUITFindBy(xpath = "//*[@name='CAPTION']/following-sibling::*")
	WebElement txtCaption;

	// Label "CAPTION"
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'Done'")
	WebElement btnDoneCaption;
	
	// Label Max 500 characters warning
	@iOSXCUITFindBy(iOSNsPredicate = "label == 'Maximum text length is 500'")
	WebElement lblMaxCaptionWarning;

	/***** Occasion Type *********/

	// Occasion Friends Yes
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Yes\"])[1]")
	WebElement btnFriendsYes;
	
	// Occasion Friends Unknown
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Unknown\"])[1]")
	WebElement btnFriendsUnknown;
	
	// Occasion Friends No
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"No\"])[1]")
	WebElement btnFriendsNo;

	// Occasion Family Yes
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Yes\"])[2]")
	WebElement btnFamilyYes;

	// Occasion Family Unknown
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Unknown\"])[2]")
	WebElement btnFamilyUnknown;
	
	// Occasion Family No
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"No\"])[2]")
	WebElement btnFamilyNo;
	
	// Occasion Date Yes
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Yes\"])[3]")
	WebElement btnDateYes;
	
	// Occasion Date Unknown
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Unknown\"])[3]")
	WebElement btnDateUnknown;
	
	// Occasion Date No
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"No\"])[3]")
	WebElement btnDateNo;

	/***** Attributes *********/

	// Price - Cheap
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"priceButton\"])[1]")
	WebElement btnPriceCheap;
	
	// Price - Normal
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"priceButton\"])[2]")
	WebElement btnPriceNormal;
	
	// Price - Expensive
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"priceButton\"])[3]")
	WebElement btnPriceExpensive;
	
	// Price - Very expensive
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"priceButton\"])[4]")
	WebElement btnPriceVeryExpensive;
	
	// Parking Yes
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Yes\"])[4]")
	WebElement btnParkingYes;
	
	// Parking Unknown
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Unknown\"])[4]")
	WebElement btnParkingUnknown;
	
	// Parking No
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"No\"])[4]")
	WebElement btnParkingNo;

	// Kid Friendly Yes
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Yes\"])[5]")
	WebElement btnKidFriendlyYes;
	
	// Kid Friendly Unknown
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Unknown\"])[5]")
	WebElement btnKidFriendlyUnknown;
	
	// Kid Friendly No
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"No\"])[5]")
	WebElement btnKidFriendlyNo;

	// Wheelchair Friendly Yes
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Yes\"])[6]")
	WebElement btnWheelchairYes;
	
	// Wheelchair Friendly Unknown
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"Unknown\"])[6]")
	WebElement btnWheelchairUnknown;
	
	// Wheelchair Friendly Yes
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"No\"])[6]")
	WebElement btnWheelchairNo;
	
	/***** Ratings *********/

	// Food - Positive
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"positiveButtion\"])[1]")
	WebElement btnFoodPositive;
	
	// Food - Negative
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"negativeButton\"])[1]")
	WebElement btnFoodNegative;
	
	// Drinks - Positive
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"positiveButtion\"])[2]")
	WebElement btnDrinksPositive;
	
	// Drinks - Negative
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"negativeButton\"])[2]")
	WebElement btnDrinksNegative;

	// Service - Positive
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"positiveButtion\"])[3]")
	WebElement btnServicePositive;
	
	// Service - Negative
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"negativeButton\"])[3]")
	WebElement btnServiceNegative;

	// Overall - Positive
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"positiveButtion\"])[4]")
	WebElement btnOverallPositive;
	
	// Overall - Negative
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name=\"negativeButton\"])[4]")
	WebElement btnOverallNegative;
	
	/***** Ambience *********/

	// Noisy
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'Noisy'")
	WebElement btnAmbienceNoisy;
	
	// Casual
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'Casual'")
	WebElement btnAmbienceCasual;
	
	// Quiet
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'Quiet'")
	WebElement btnAmbienceQuiet;
	
	// Classy
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'Classy'")
	WebElement btnAmbienceClassy;
	
	// Trendy
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'Trendy'")
	WebElement btnAmbienceTrendy;
	
	// Touristy
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'Touristy'")
	WebElement btnAmbienceTouristy;
	
	// Intimate
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'Intimate'")
	WebElement btnAmbienceIntimate;
	
	// Dark
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'Dark'")
	WebElement btnAmbienceDark;
	
	// Well-lit
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'Well-lit'")
	WebElement btnAmbienceWellLit;
	
	// Friendly
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'Friendly'")
	WebElement btnAmbienceFriendly;
	
	/****** Save post **********/
	
	// SAVE & POST button
	@iOSXCUITFindBy(accessibility = "submitButton")
	WebElement btnSubmit;
	
	// Update btn
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'UPDATE'")
	WebElement btnUpdatePost;
	
	// Upload successfully
	@iOSXCUITFindBy(accessibility = "upload successful")
	WebElement lblUploadSuccessfully;

	public CreateOrEditReviewPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(lblSelectBusiness);
	}

	/******* Select Business actions *************/

	public CreateOrEditReviewPage selectBusiness(String businessName) throws Exception {
		// Scroll to Caption will make sure the business box displayed
		driver.scrollToElement(txtCaption);
		driver.tap(txtSelectBusinessTextBox);

		if (driver.isElementDisplayed(txtSearchBusiness)) {
			driver.inputText(txtSearchBusiness, businessName);
			driver.click(listBusinessSearchResults.get(0));
		} else {
			throw new Exception("Search Business page doesn't display");
		}
		
		return this;
	}

	public String getSelectedBusiness() throws Exception {
		return driver.getText(txtSelectBusinessTextBox);
	}

	/******* Add Caption *************/

	public CreateOrEditReviewPage setCaption(String caption) throws Exception {
		driver.scrollToElement(txtCaption);
		driver.click(txtCaption);
		driver.inputText(txtCaption, caption);
		return this;
	}
	
	public CreateOrEditReviewPage doneCaption() throws Exception {
		driver.click(btnDoneCaption);
		return this;
	}
	
	public boolean isMaxCaptionLengthWarningDisplays() {
		return driver.isElementDisplayed(lblMaxCaptionWarning) && ! driver.isElementClickable(btnDoneCaption, 5);
	}
	
	/******* Select Occasion Types *************/
	
	/**
	 * Select occasion types, leave it as empty string if you don't want to choose
	 * @param friends
	 * @param family
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public CreateOrEditReviewPage selectOccasionTypes(String friends, String family, String date) throws Exception {
		driver.scrollToElement(btnPriceCheap);
		
		switch(friends) {
			case OCCASION_TYPE.YES:
				driver.click(btnFriendsYes);
				break;
				
			case OCCASION_TYPE.NO:
				driver.click(btnFriendsNo);
				break;
				
			case OCCASION_TYPE.UNKNOWN:
				driver.click(btnFriendsUnknown);
				break;
				
			default:
				break;
		}
		
		switch(family) {
			case OCCASION_TYPE.YES:
				driver.click(btnFamilyYes);
				break;
				
			case OCCASION_TYPE.NO:
				driver.click(btnFamilyNo);
				break;
				
			case OCCASION_TYPE.UNKNOWN:
				driver.click(btnFamilyUnknown);
				break;
				
			default:
				break;
		}
		
		switch(date) {
			case OCCASION_TYPE.YES:
				driver.click(btnDateYes);
				break;
				
			case OCCASION_TYPE.NO:
				driver.click(btnDateNo);
				break;
				
			case OCCASION_TYPE.UNKNOWN:
				driver.click(btnDateUnknown);
				break;
				
			default:
				break;
		}
		
		return this;
	}
	
	/******* Select Attributes *************/
	
	/**
	 * Select attributes, leave it as empty string if you don't want to choose
	 * @param price
	 * @param parking
	 * @param kidFriendly
	 * @param wheelChair
	 * @return
	 * @throws Exception
	 */
	public CreateOrEditReviewPage selectAttributes(String price, String parking, String kidFriendly, String wheelChair) throws Exception {
		driver.scrollToElement(btnFoodPositive);
		
		switch(price) {
			case ATTRIBUTE_TYPE.CHEAP:
				driver.click(btnPriceCheap);
				break;
				
			case ATTRIBUTE_TYPE.NORMAL:
				driver.click(btnPriceNormal);
				break;
				
			case ATTRIBUTE_TYPE.EXPENSIVE:
				driver.click(btnPriceExpensive);
				break;
				
			case ATTRIBUTE_TYPE.VERY_EXPENSIVE:
				driver.click(btnPriceVeryExpensive);
				break;
				
			default:
				break;
		}
		
		switch(parking) {
			case ATTRIBUTE_TYPE.YES:
				driver.click(btnParkingYes);
				break;
				
			case ATTRIBUTE_TYPE.NO:
				driver.click(btnParkingNo);
				break;
				
			case ATTRIBUTE_TYPE.UNKNOWN:
				driver.click(btnParkingUnknown);
				break;
				
			default:
				break;
		}
		
		switch(kidFriendly) {
			case ATTRIBUTE_TYPE.YES:
				driver.click(btnKidFriendlyYes);
				break;
				
			case ATTRIBUTE_TYPE.NO:
				driver.click(btnKidFriendlyNo);
				break;
				
			case ATTRIBUTE_TYPE.UNKNOWN:
				driver.click(btnKidFriendlyUnknown);
				break;
				
			default:
				break;
		}
		
		switch(wheelChair) {
			case ATTRIBUTE_TYPE.YES:
				driver.click(btnWheelchairYes);
				break;
				
			case ATTRIBUTE_TYPE.NO:
				driver.click(btnWheelchairNo);
				break;
				
			case ATTRIBUTE_TYPE.UNKNOWN:
				driver.click(btnWheelchairUnknown);
				break;
				
			default:
				break;
		}
		
		return this;
	}
	
	/******* Select Ratings *************/
	
	/**
	 * Select ratings, leave it as empty string if you don't want to choose
	 * @param food
	 * @param drinks
	 * @param service
	 * @param overall
	 * @return
	 * @throws Exception
	 */
	public CreateOrEditReviewPage selectRatings(String food, String drinks, String service, String overall) throws Exception {
		// Scroll to Ambience will make sure the ratings displayed
		driver.scrollToElement(btnAmbienceFriendly);
		
		switch(food) {
			case RATING_TYPE.SATISFY:
				driver.tap(btnFoodPositive);
				break;
				
			case RATING_TYPE.UNSATISFY:
				driver.tap(btnFoodNegative);
				break;
				
			default:
				break;
		}
		
		switch(drinks) {
			case RATING_TYPE.SATISFY:
				driver.tap(btnDrinksPositive);
				break;
				
			case RATING_TYPE.UNSATISFY:
				driver.tap(btnDrinksNegative);
				break;
				
			default:
				break;
		}
		
		switch(service) {
			case RATING_TYPE.SATISFY:
				driver.tap(btnServicePositive);
				break;
				
			case RATING_TYPE.UNSATISFY:
				driver.tap(btnServiceNegative);
				break;
				
			default:
				break;
		}
		
		switch(overall) {
			case RATING_TYPE.SATISFY:
				driver.tap(btnOverallPositive);
				break;
				
			case RATING_TYPE.UNSATISFY:
				driver.tap(btnOverallNegative);
				break;
				
			default:
				break;
		}
		
		return this;
	}
	
	/**
	 * De-Select ratings, leave it as empty string if you don't want to choose
	 * @param food
	 * @param drinks
	 * @param service
	 * @param overall
	 * @return
	 * @throws Exception
	 */
	public CreateOrEditReviewPage deselectRatings(String food, String drinks, String service, String overall) throws Exception {
		// Scroll to Ambience will make sure the ratings displayed
		driver.scrollToElement(btnAmbienceFriendly);
		
		switch(food) {
			case RATING_TYPE.SATISFY:
				driver.deselectCheckBox(btnFoodPositive);
				break;
				
			case RATING_TYPE.UNSATISFY:
				driver.deselectCheckBox(btnFoodNegative);
				break;
				
			default:
				break;
		}
		
		switch(drinks) {
			case RATING_TYPE.SATISFY:
				driver.deselectCheckBox(btnDrinksPositive);
				break;
				
			case RATING_TYPE.UNSATISFY:
				driver.deselectCheckBox(btnDrinksNegative);
				break;
				
			default:
				break;
		}
		
		switch(service) {
			case RATING_TYPE.SATISFY:
				driver.deselectCheckBox(btnServicePositive);
				break;
				
			case RATING_TYPE.UNSATISFY:
				driver.deselectCheckBox(btnServiceNegative);
				break;
				
			default:
				break;
		}
		
		switch(overall) {
			case RATING_TYPE.SATISFY:
				driver.deselectCheckBox(btnOverallPositive);
				break;
				
			case RATING_TYPE.UNSATISFY:
				driver.deselectCheckBox(btnOverallNegative);
				break;
				
			default:
				break;
		}
		
		return this;
	}
	
	/******* Select Ambience *************/
	
	/**
	 * Select ambiences, leave it as empty array if you don't want to choose
	 * @param ambiences
	 * @return
	 * @throws Exception
	 */
	public CreateOrEditReviewPage selectAmbience(String[] ambiences) throws Exception {
		driver.scrollToElement(btnAmbienceFriendly);
		
		if(ambiences.length > 0) {
			for(int i = 0; i < ambiences.length; i++) {
				
				switch(ambiences[i]) {
				
					case AMBIENCES.NOISY:
						driver.click(btnAmbienceNoisy);
						break;
						
					case AMBIENCES.CASUAL:
						driver.click(btnAmbienceCasual);
						break;
						
					case AMBIENCES.QUIET:
						driver.click(btnAmbienceQuiet);
						break;
						
					case AMBIENCES.CLASSY:
						driver.click(btnAmbienceClassy);
						break;
						
					case AMBIENCES.TRENDY:
						driver.click(btnAmbienceTrendy);
						break;
						
					case AMBIENCES.TOURISTY:
						driver.click(btnAmbienceTouristy);
						break;
						
					case AMBIENCES.INTIMATE:
						driver.click(btnAmbienceIntimate);
						break;
						
					case AMBIENCES.DARK:
						driver.click(btnAmbienceDark);
						break;
						
					case AMBIENCES.WELL_LIT:
						driver.click(btnAmbienceWellLit);
						break;
						
					case AMBIENCES.FRIENDLY:
						driver.click(btnAmbienceFriendly);
						break;
						
					default:
						break;
				}
			}
		}
		
		return this;
	}
	
	/******* Submit *************/
	
	public CreateOrEditReviewPage submit() throws Exception {
		driver.scrollToElement(btnSubmit);
		driver.click(btnSubmit);
		return this;
	}
	
	public ReviewDetailPage update() throws Exception {
		driver.scrollToElement(btnUpdatePost);
		driver.click(btnUpdatePost);
		return new ReviewDetailPage(driver);
	}
	
	public boolean postReviewSuccessfully() {
		return driver.isElementDisplayed(lblUploadSuccessfully, 60);
	}
	
	public String getTextAlert() {
		driver.waitForAlertPresent(20);
		String text = driver.getAlertText();
		driver.acceptAlert();
		return text;
	}
	
}
