package com.franki.automation.android.pages.gigs;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.android.pages.payment.PaymentPage;
import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.datamodel.GigData;
import com.franki.automation.android.pages.gigs.GigProfilePage;
import com.franki.automation.setup.Constant;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;

public class GigsPage {

	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "tvCashCount")
	private WebElement btnCashout;

	@AndroidFindBy(id = "btSortClosing")
	private WebElement btnSortClosing;

	@AndroidFindBy(id = "btSortCash")
	private WebElement btnSortCash;

	@AndroidFindBy(id = "btSortNear")
	private WebElement btnSortNear;

	@AndroidFindBy(id = "ivFilterRestaurant")
	private WebElement btnFilterRestaurant;

	@AndroidFindBy(id = "ivFilterBar")
	private WebElement btnFilterBar;

	@AndroidFindBy(id = "ivFilterNightclub")
	private WebElement btnFilterNightClub;

	@AndroidFindBy(id = "ivFilterAlcohol")
	private WebElement btnFilterAlcohol;

	@AndroidFindBy(id = "ivFilterCannabis")
	private WebElement btnFilterCannabis;

	@AndroidFindBy(id = "ivFilterTravel")
	private WebElement btnFilterTravel;

	@AndroidFindBy(id = "btMyLocation")
	private WebElement btnMyLocation;

	@AndroidFindBy(id = "btPickState")
	private WebElement btnPickState;

	@AndroidFindBy(id = "spStates")
	private WebElement spinnerStates;

	@AndroidFindBy(id = "ivIcon")
	private WebElement btnState;

	@AndroidFindBy(id = "android:id/text1")
	private WebElement txtState;

	@AndroidFindBy(id = "vNext")
	private WebElement btnDone;

	@AndroidFindBy(id = "tvTitle")
	private WebElement titleSortBy;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ListView\").instance(0)")
	private WebElement listStates;

	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"sort by:\")")
	private WebElement btnSortByFilter;

	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
	private WebElement btnAllowAccessLocation;

	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_deny_button")
	private WebElement btnDenyAccessLocation;

	@HowToUseLocators(androidAutomation = LocatorGroupStrategy.CHAIN)
	@AndroidFindBy(id = "recyclerview")
	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\")")
	private List<WebElement> gigItems;

	@HowToUseLocators(androidAutomation = LocatorGroupStrategy.CHAIN)
	@AndroidFindBy(id = "rvBizTypes")
	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.LinearLayout\").instance(1)")
	WebElement btnBizTypeFilter;

	@HowToUseLocators(androidAutomation = LocatorGroupStrategy.CHAIN)
	@AndroidFindBy(id = "rvBizTypes")
	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.LinearLayout\").instance(2)")
	WebElement btnLocationFilter;
	
	@HowToUseLocators(androidAutomation = LocatorGroupStrategy.CHAIN)
	@AndroidFindBy(id = "rvBizTypes")
	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.LinearLayout\").instance(2)")
	@AndroidFindBy(id = "tvTitle")
	WebElement txtLocationFilter;

	public GigsPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		allowAccessLocation();
		return driver.isElementDisplayed(btnCashout);
	}

	public PaymentPage goToPaymentPage() throws Exception {
		driver.click(btnCashout);
		return new PaymentPage(driver);
	}

	public ArrayList<String> getListGigName() {
		driver.waitForAllElementsDisplayed(gigItems, 30);
		ArrayList<String> listName = new ArrayList<String>();
		gigItems.forEach(e -> listName.add(e.findElement(By.id("tvTitle")).getText()));
		return listName;
	}

	public ArrayList<Integer> getListGigPrize() {
		driver.waitForAllElementsDisplayed(gigItems, 30);
		ArrayList<Integer> listValue = new ArrayList<Integer>();
		for (int i = 0; i < gigItems.size(); i++) {
			String rawData = gigItems.get(i).findElement(By.id("tvPrize")).getText();
			String data = rawData.substring(rawData.lastIndexOf("$"));
			listValue.add(Integer.parseInt(data));
		}
		return listValue;
	}

	public ArrayList<Float> getListGigDistance() {
		driver.waitForAllElementsDisplayed(gigItems, 30);
		ArrayList<Float> listValue = new ArrayList<Float>();
		for (int i = 0; i < gigItems.size(); i++) {
			String rawData = gigItems.get(i).findElement(By.id("tvDistance")).getText();
			String data = rawData.substring(0, rawData.indexOf(" "));
			listValue.add(Float.parseFloat(data));
		}
		return listValue;
	}

	public ArrayList<String> getListGigFullName() {
		driver.waitForAllElementsDisplayed(gigItems, 30);
		ArrayList<String> listValue = new ArrayList<String>();
		gigItems.forEach(e -> listValue.add(e.findElement(By.id("tv_fullname")).getText()));
		return listValue;
	}

	public GigProfilePage selectFirstGigItem() throws Exception {
		driver.sleep(5);
		driver.click(gigItems.get(0));
		return new GigProfilePage(driver);
	}

	public boolean isGigsSortByNearMeCorrectly(ArrayList<Float> listValue) {
		for (int i = 0; i < listValue.size() - 1; i++) {
			if (listValue.get(i) > listValue.get(i + 1)) {
				return false;
			}
		}
		return true;
	}

	public boolean isGigsSortByPotentialMoneyCorrectly(ArrayList<Integer> listValue) {
		for (int i = 0; i < listValue.size() - 1; i++) {
			if (listValue.get(i) < listValue.get(i + 1)) {
				return false;
			}
		}
		return true;
	}

	public boolean isisBizTypesFilterDisplayedCorrectly(ArrayList<String> listName, String biz) {
		for (int i = 0; i < listName.size(); i++) {
			if (!listName.get(i).contains(biz)) {
				return false;
			}
		}
		return true;
	}

	public Integer getNumberOfGigsActive() {
		return gigItems.size();
	}

	public boolean isSortByQuickFilterBtnDisplayed() {
		return driver.isElementDisplayed(btnSortByFilter);
	}

	public WebElement getElementQuickFilterBtn(String locator, String value) {
		String quickFilterLocator = String.format(locator, value);
		By by = MobileBy.AndroidUIAutomator(quickFilterLocator);
		WebElement quickFilterElement = driver.getDriver().findElement(by);
		return quickFilterElement;
	}

	public boolean isSortbyOptionsDisplayed() {
		return driver.isElementDisplayed(btnSortClosing) && driver.isElementDisplayed(btnSortCash) && driver.isElementDisplayed(btnSortNear);
	}

	public String getTextDisplayInSortByQuickFilter() throws Exception {
		return driver.getText(titleSortBy);
	}

	public boolean isBizTypesOptionsDisplayed() {
		return driver.isElementDisplayed(btnFilterRestaurant) && driver.isElementDisplayed(btnFilterBar) && driver.isElementDisplayed(btnFilterNightClub) && driver.isElementDisplayed(btnFilterAlcohol) && driver.isElementDisplayed(btnFilterCannabis) && driver.isElementDisplayed(btnFilterTravel);
	}

	public void clickToSortByQuickFilter() throws Exception {
		driver.click(btnSortByFilter);
	}

	public void clickToBizTypesQuickFilter() throws Exception {
		driver.click(btnBizTypeFilter);
	}

	public void clickToLocationQuickFilter() throws Exception {
		driver.click(btnLocationFilter);
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
			driver.click(btnFilterAlcohol);
			break;
		case Constant.BizTypes.BIZ_CAFE:
			driver.click(btnFilterCannabis);
			break;
		default:
			break;
		}
	}

	public boolean isLocationOptionsDisplayed() {
		return driver.isElementDisplayed(btnMyLocation) && driver.isElementDisplayed(btnPickState) && driver.isElementDisplayed(btnState) && driver.isElementDisplayed(spinnerStates);
	}

	public boolean verifyPickStateIsSelected() {
		return Boolean.valueOf(driver.getAttribute(btnPickState, "selected"));
	}

	public void selectAState(String state) throws Exception {
		driver.click(spinnerStates);
		driver.waitForElementDisplayed(listStates, 10);
		String locator = "new UiSelector().text(\"%s\")";
		locator = String.format(locator, state);
		WebElement element = driver.getDriver().findElement(MobileBy.AndroidUIAutomator(locator));
		driver.click(element);
	}

	public String getStateAfterSelect() throws Exception {
		return driver.getText(txtState);
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

	public void allowAccessLocation() throws Exception {
		if (driver.isElementDisplayed(btnAllowAccessLocation, 10)) {
			driver.sleep(3);
			driver.click(btnAllowAccessLocation);
		}
	}

	public void clickOnPickAStateTab() throws Exception {
		driver.click(btnPickState);
	}

	public String getTextDisplayInLocationFilter() throws Exception {
		return driver.getText(txtLocationFilter);
	}

	public ArrayList<GigData> getGigList() {
		driver.waitForPresenceOfAllElementLocatedBy(MobileBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup"), 30);
		ArrayList<GigData> gigList = new ArrayList<GigData>();
		gigItems.forEach(e -> {
			GigData gig = new GigData();
			gig.setGigName(e.findElement(By.id("tvTitle")).getText());
			gig.setGigDistance(Double.valueOf(e.findElement(By.id("tvDistance")).getText().split(" ")[0].replace(",", "")));
			gigList.add(gig);
		});

		return gigList;
	}

	public boolean isGigListInDistanceOrder(ArrayList<GigData> gigList) {

		if (gigList.size() > 1) {
			return true;
		}

		for (int i = 0; i < gigList.size(); i++) {
			if (gigList.get(i).getGigDistance() > gigList.get(i + 1).getGigDistance()) {
				return false;
			}
		}

		return true;
	}

	public GigProfilePage clickOnGigByIndex(int index) throws Exception {
		driver.sleep(8);
		driver.scrollToElement(gigItems.get(index));
		driver.click(gigItems.get(index));
		return new GigProfilePage(driver);
	}
}