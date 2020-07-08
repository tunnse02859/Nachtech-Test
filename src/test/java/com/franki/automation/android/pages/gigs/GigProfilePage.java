package com.franki.automation.android.pages.gigs;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class GigProfilePage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "tvRulesTitle")
	private WebElement titleRules;

	@AndroidFindBy(id = "tvRulesContent")
	private List<WebElement> lblGigRulesDescription;

	@AndroidFindBy(id = "flReviewThis")
	private WebElement btnReview;

	@AndroidFindBy(id = "btGrid")
	private WebElement btnGrid;

	@AndroidFindBy(id = "btFeed")
	private WebElement btnFeed;

	@AndroidFindBy(id = "post_user_photo")
	private WebElement thumbnail;

	@AndroidFindBy(id = "tvDistance")
	private WebElement txtDistance;

	@AndroidFindBy(id = "tvSchedule")
	private WebElement txtSchedule;

	@AndroidFindBy(id = "tvDescription")
	private WebElement lblGigBriefDescription;

	@AndroidFindBy(id = "tvPrizesTitle")
	private WebElement titlePrizes;

	@AndroidFindBy(id = "tvPrizesContent")
	private List<WebElement> lblGigPrizesDescription;

	@AndroidFindBy(id = "tvSubmissionsTitle")
	private WebElement titleSubmissions;

	@AndroidFindBy(id = "tvSubmissionsContent")
	private WebElement contentSubmissions;

	@AndroidFindBy(id = "collapsing_toolbar")
	private WebElement lblGigName;

	public GigProfilePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isOnGigProfilePage() {
		return driver.isElementDisplayed(titleRules) && driver.isElementDisplayed(btnReview) && driver.isElementDisplayed(btnGrid) && driver.isElementDisplayed(btnFeed) && driver.isElementDisplayed(txtDistance) && driver.isElementDisplayed(txtSchedule) && driver.isElementDisplayed(lblGigBriefDescription) && driver.isElementDisplayed(titlePrizes) && driver.isElementDisplayed(titleSubmissions) && driver.isElementDisplayed(contentSubmissions);
	}

	/******* General Actions ****************/

	public String getGigName() throws Exception {
		return driver.getAttribute(lblGigName, "content-desc");
	}

	public String getGigBusinessInfo() throws Exception {
		return driver.getText(lblGigBriefDescription);
	}

	// Brief
	public String getGigBrief() throws Exception {
		return driver.getText(lblGigBriefDescription);
	}

	// Gig Rules
	public ArrayList<String> getGigRules() throws Exception {
		ArrayList<String> gigRules = new ArrayList<>();

		if (lblGigRulesDescription.size() == 0) {
			return gigRules;
		}

		for (int i = 0; i < lblGigRulesDescription.size(); i++) {
			gigRules.add(driver.getText(lblGigRulesDescription.get(i)));
		}

		return gigRules;
	}

	// Gig Prizes
	public ArrayList<String> getGigPrizes() throws Exception {
		ArrayList<String> gigPrizes = new ArrayList<>();

		if (lblGigPrizesDescription.size() == 0) {
			return gigPrizes;
		}

		for (int i = 0; i < lblGigPrizesDescription.size(); i++) {
			gigPrizes.add(driver.getText(lblGigPrizesDescription.get(i)));
		}

		return gigPrizes;
	}
}