package com.franki.automation.ios.pages.gigs;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.ios.pages.business.BusinessProfilePage;
import com.franki.automation.setup.Constant;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class GigProfilePage {
	public AppiumBaseDriver driver;
	
	/*********** Main Gig Info ****************/
	// Gig Name
	@iOSXCUITFindBy(accessibility = "titleLabelHeader")
	private WebElement lblGigName;
	
	// Business Name | Business Type
	@iOSXCUITFindBy(accessibility = "descriptionLabelHeader")
	private WebElement lblGigDescription;
	
	// Create Review
	@iOSXCUITFindBy(accessibility = "createReviewButton")
	private WebElement btnBeFrank;
	
	// View Business
	@iOSXCUITFindBy(accessibility = "viewBusinessButton")
	private WebElement btnViewBusiness;
	
	// Save Gig
	@iOSXCUITFindBy(accessibility = "saveButton")
	private WebElement btnSaveGig;
	
	/*********** Gig Rules ****************/

	// Rules tab
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'Rules'")
	private WebElement tabRules;
	
	// Gig Closing time info
	@iOSXCUITFindBy(accessibility = "gigTimeLabel")
	private WebElement lblClosingInfo;
	
	// Gig Prize button
	@iOSXCUITFindBy(accessibility = "gigPrizeButton")
	private WebElement btnGigPrize;
	
	// Gig Brief Title
	@iOSXCUITFindBy(accessibility = "titleLabelBrief")
	private WebElement lblGigBrief;
	
	// Gig Brief Description
	@iOSXCUITFindBy(accessibility = "descriptionLabelBrief")
	private WebElement lblGigBriefDescription;
	
	// Gig Rules Title
	@iOSXCUITFindBy(accessibility = "titleLabelGigRules")
	private WebElement lblGigRulesTitle;
	
	// Gig Rules Description
	@iOSXCUITFindBy(accessibility = "descriptionLabelGigRules")
	private List<WebElement> lblGigRulesDescription;
	
	// Gig Rules Title
	@iOSXCUITFindBy(accessibility = "titleLabelGigPrize")
	private WebElement lblGigPrizesTitle;
	
	// Gig Rules Description
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'descriptionLabelGigPrize'")
	private List<WebElement> lblGigPrizesDescription;
	
	/*********** Gig Reviews ****************/
	
	// Reviews tab
	@iOSXCUITFindBy(accessibility = "Reviews")
	private WebElement tabReviews;
	//XCUIElementTypeStaticText
	
	public GigProfilePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isOnGigProfilePage() {
		return driver.isElementDisplayed(lblGigName) && driver.isElementDisplayed(lblGigDescription) && driver.isElementDisplayed(btnBeFrank) 
				&& driver.isElementDisplayed(btnViewBusiness) && driver.isElementDisplayed(btnSaveGig) && driver.isElementDisplayed(tabRules) 
				&& driver.isElementDisplayed(tabReviews);
	}
	
	/******* General Actions ****************/
	
	public String getGigName() throws Exception {
		return driver.getText(lblGigName);
	}
	
	public String getGigBusinessInfo() throws Exception {
		return driver.getText(lblGigDescription);
	}
	
	public BusinessProfilePage clickOnViewBusinessButton() throws Exception {
		driver.click(btnViewBusiness);
		return new BusinessProfilePage(driver);
	}
	
	public void clickOnSaveUnsaveGigButton() throws Exception {
		driver.click(btnSaveGig);
	}
	
	public boolean isUserSavedGig() throws Exception {
		String savedGigText = driver.getText(btnSaveGig.findElement(MobileBy.className("XCUIElementTypeStaticText")));
		
		return savedGigText.equals(Constant.SaveGigStatus.UNSAVE);
	}
	
	public boolean isUserUnsavedGig() throws Exception {
		String savedGigText = driver.getText(btnSaveGig.findElement(MobileBy.className("XCUIElementTypeStaticText")));
		
		return savedGigText.equals(Constant.SaveGigStatus.SAVE);
	}
	
	/******* Actions on Gig Rules tab  ****************/
	
	// Closing info
	public String getGigClosingInfo() throws Exception {
		return driver.getText(lblClosingInfo).trim();
	}
	
	// Brief
	public String getGigBrief() throws Exception {
		return driver.getText(lblGigBriefDescription).trim();
	}
	
	// Gig Rules
	public ArrayList<String> getGigRules() throws Exception {
		ArrayList<String> gigRules = new ArrayList<>();
		
		if(lblGigRulesDescription.size() == 0) {
			return gigRules;
		}
		
		for(int i = 0; i < lblGigRulesDescription.size(); i++) {
			gigRules.add(driver.getText(lblGigRulesDescription.get(i)).trim());
		}
		
		return gigRules;
	}
	
	// Gig Prizes
	public ArrayList<String> getGigPrizes() throws Exception {
		ArrayList<String> gigPrizes = new ArrayList<>();
		
		if(lblGigPrizesDescription.size() == 0) {
			return gigPrizes;
		}
		
		for(int i = 0; i < lblGigPrizesDescription.size(); i++) {
			gigPrizes.add(driver.getText(lblGigPrizesDescription.get(i)).trim());
		}
		
		return gigPrizes;
	}
}
