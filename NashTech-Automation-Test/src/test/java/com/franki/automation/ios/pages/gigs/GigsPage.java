package com.franki.automation.ios.pages.gigs;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.appium.driver.AppiumBaseDriver.DIRECTION;
import com.franki.automation.datamodel.GigData;
import com.franki.automation.ios.pages.payment.PaymentPage;
import com.franki.automation.setup.Constant;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class GigsPage {

	public AppiumBaseDriver driver;

	/************ Animations *********/
	// Skip button
	@iOSXCUITFindBy(accessibility = "SKIP")
	private WebElement btnSkip;
	
	// Cool button
	@iOSXCUITFindBy(accessibility = "COOL")
	private WebElement btnCool;
	
	@iOSXCUITFindBy(accessibility = "titleLabel")
	private WebElement lblInstructionTitle;
	
	@iOSXCUITFindBy(accessibility = "descriptionLabel")
	private WebElement lblInstructionDescription;
	

	@iOSXCUITFindBy(accessibility = "amountButton")
	private WebElement btnCashout;

	/************ Close or Cancel filter *********/
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'done'")
	private WebElement btnDone;

	@iOSXCUITFindBy(iOSNsPredicate = "name == 'cancel'")
	private WebElement btnCancel;

	/************ Sort By *********/
	// Click on this to choose sort type
	@iOSXCUITFindBy(accessibility = "sortByButton")
	private WebElement btnSortByFilter;

	// The label after select sort type, e.g: Sort by: closing soon
	@iOSXCUITFindBy(iOSNsPredicate = "name CONTAINS 'Sort by:'")
	private WebElement titleSortBy;

	@iOSXCUITFindBy(accessibility = "closing soon")
	private WebElement btnSortClosing;

	@iOSXCUITFindBy(accessibility = "potential $$")
	private WebElement btnSortCash;

	@iOSXCUITFindBy(accessibility = "near me")
	private WebElement btnSortNear;

	/************ Filter by Biz Types *********/
	// Click on this to choose biz type
	@iOSXCUITFindBy(xpath = "//*[@name='sortByButton']/following-sibling:: XCUIElementTypeOther")
	WebElement btnBizTypeFilter;

	@iOSXCUITFindBy(iOSNsPredicate = "label == 'restaurants'")
	private WebElement btnFilterRestaurant;

	@iOSXCUITFindBy(iOSNsPredicate = "label == 'bars'")
	private WebElement btnFilterBar;

	@iOSXCUITFindBy(iOSNsPredicate = "label == 'nightclubs'")
	private WebElement btnFilterNightClub;

	@iOSXCUITFindBy(iOSNsPredicate = "label == 'takeaway/delivery'")
	private WebElement btnFilterTakeAway;

	@iOSXCUITFindBy(iOSNsPredicate = "label == 'cafe'")
	private WebElement btnFilterCafe;

	/************ Filter by Locations *********/
	// Click on this to choose location
	@iOSXCUITFindBy(accessibility = "locationButton")
	WebElement btnLocationFilter;

	// The location filter label
	@iOSXCUITFindBy(accessibility = "locationButton")
	WebElement lblLocationFilterInstruction;

	// Tab My Location
	@iOSXCUITFindBy(accessibility = "my location")
	private WebElement btnMyLocation;

	// Tab Pick a state
	@iOSXCUITFindBy(accessibility = "pick a state")
	private WebElement btnPickState;

	// Click on this to show the spinner
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='choose your location']/following-sibling::XCUIElementTypeTextField")
	private WebElement spinnerStates;

	/************ The gig list table *********/
	// The list of gigs
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> gigItems;

	private By lblGigName = MobileBy.name("gigNameLabel");

	private By lblAmountInfo = MobileBy.name("amountInfoLabel");

	private By lblDistanceInfo = MobileBy.name("distanceLabel");

	private By lblClosingInfo = MobileBy.name("closingInfoLabel");

	private By lblBizType = MobileBy.name("bizTypeLabel");

	public GigsPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}
	
	/******* Animations  ******************************/
	
	public void swipeLeft() {
		driver.swipe(DIRECTION.LEFT);
	}
	
	public String getInstructionTitle() throws Exception {
		return driver.getText(lblInstructionTitle);
	}
	
	public String getInstructionDescription() throws Exception {
		return driver.getText(lblInstructionDescription);
	}
	
	public GigsPage clickOnCool() throws Exception {
		driver.tap(btnCool);
		return this;
	}
	
	public GigsPage clickSkipInstruction() throws Exception {
		if (driver.isElementDisplayed(btnSkip, 3)) {
			driver.tap(btnSkip);
		}
		return this;
	}

	/******* General actions ******************************/
	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(btnCashout);
	}

	public PaymentPage goToPaymentPage() throws Exception {
		driver.click(btnCashout);
		return new PaymentPage(driver);
	}
	
	public ArrayList<GigData> getGigList() {
		ArrayList<GigData> gigDataList = new ArrayList<GigData>();
		driver.waitForPresenceOfAllElementLocatedBy(MobileBy.iOSClassChain("**/XCUIElementTypeTable/XCUIElementTypeCell"), 10);
		gigItems.forEach(e -> {
			GigData gigData = new GigData();
			gigData.setGigName(e.findElement(lblGigName).getText());
			gigData.setGigDistance(Double.valueOf(e.findElement(lblDistanceInfo).getText().split(" ")[0].replace(",", "")));
			
//			gig.setGigBizType(e.findElement(lblBizType).getText());
			gigDataList.add(gigData);
		});
		
		return gigDataList;
	}
	
	public GigProfilePage selectFirstGigItem() throws Exception {
		driver.click(gigItems.get(0));
		return new GigProfilePage(driver);
	}
	
	public GigProfilePage clickOnGigByIndex(int index) throws Exception {
		driver.scrollToElement(gigItems.get(index));
		driver.click(gigItems.get(index));
		return new GigProfilePage(driver);
	}
	
	/******* Sort By Actions ******************************/
	
	public void clickToSortByQuickFilter() throws Exception {
		driver.click(btnSortByFilter);
	}

	public boolean isSortByQuickFilterBtnDisplayed() {
		return driver.isElementDisplayed(btnSortByFilter);
	}
	
	public boolean isSortbyOptionsDisplayed() {
		return driver.isElementDisplayed(btnSortClosing) && driver.isElementDisplayed(btnSortCash)
				&& driver.isElementDisplayed(btnSortNear);
	}
	
	public void selectSortBy(String options) throws Exception {
		switch (options) {
		case GigData.SortBy.SORT_BY_CLOSING_SOON:
			driver.click(btnSortClosing);
			break;
		case GigData.SortBy.SORT_BY_POTENTIAL_MONEY:
			driver.click(btnSortCash);
			break;
		case GigData.SortBy.SORT_BY_NEAR_ME:
			driver.click(btnSortNear);
			break;
		default:
			break;
		}
	}
	
	public boolean isGigListInDistanceOrder(ArrayList<GigData> gigList) {
		
		if(gigList.size() > 1) {
			return true;
		}
		
		for(int i = 0; i < gigList.size(); i++) {
			if(gigList.get(i).getGigDistance() > gigList.get(i + 1).getGigDistance()) {
				return false;
			}
		}
		
		return true;
	}

	public String getTextDisplayInSortByQuickFilter() throws Exception {
		return driver.getText(btnSortByFilter.findElement(MobileBy.className("XCUIElementTypeStaticText")));
	}

	
	/*********** Filter By Biz Type **************************/
	
	public void clickToBizTypesQuickFilter() throws Exception {
		driver.click(btnBizTypeFilter);
	}
	
	public boolean isBizTypesOptionsDisplayed() {
		return driver.isElementDisplayed(btnFilterRestaurant) && driver.isElementDisplayed(btnFilterBar)
				&& driver.isElementDisplayed(btnFilterNightClub) && driver.isElementDisplayed(btnFilterTakeAway)
				&& driver.isElementDisplayed(btnFilterCafe);
	}

	public void selectBizType(String biz) throws Exception {
		switch (biz) {
		case Constant.BizTypes.BIZ_RESTAURANTS:
			driver.click(btnFilterRestaurant);
			break;
		case Constant.BizTypes.BIZ_BARS:
			driver.click(btnFilterBar);
			break;
		case Constant.BizTypes.BIZ_NIGHTCLUBS:
			driver.click(btnFilterNightClub);
			break;
		case Constant.BizTypes.BIZ_TAKEAWAY:
			driver.click(btnFilterTakeAway);
			break;
		case Constant.BizTypes.BIZ_CAFE:
			driver.click(btnFilterCafe);
			break;
		default:
			break;
		}
	}

	/*********** Filter By Locations **************************/
	
	public void clickToLocationQuickFilter() throws Exception {
		driver.click(btnLocationFilter);
	}
	
	public boolean isLocationOptionsDisplayed() {
		return driver.isElementDisplayed(btnMyLocation) && driver.isElementDisplayed(btnPickState)
				&& driver.isElementDisplayed(spinnerStates);
	}
	
	public void clickOnPickAStateTab() throws Exception {
		driver.click(btnPickState);
	}

	public boolean verifyPickStateIsSelected() {
		return Boolean.valueOf(driver.getAttribute(btnPickState, "selected"));
	}

	public void selectAState(String state) throws Exception {
		driver.click(spinnerStates);
		driver.selectPickerWheel(null, state, true);
	}

	public String getStateAfterSelect() throws Exception {
		return driver.getText(spinnerStates);
	}

	public void clickToMyLocation() throws Exception {
		driver.click(btnMyLocation);
	}

	public boolean verifyMyLocationIsSelected() {
		return Boolean.valueOf(driver.getAttribute(btnMyLocation, "selected"));
	}

	public void clickToDoneBtn() throws Exception {
		driver.click(btnDone);
	}
	
	public String getTextDisplayInLocationFilter() throws Exception {
		return driver.getText(btnLocationFilter.findElement(MobileBy.className("XCUIElementTypeStaticText")));
	}

}