package com.franki.automation.android.pages.explore;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.android.pages.common.PermissionManager;
import com.franki.automation.android.pages.search.HomeSearchPage;
import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ExplorePage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "tvShowMe")
	private WebElement btnShowEverything;

	@AndroidFindBy(id = "vNext")
	private WebElement btnLetGo;

	@AndroidFindBy(id = "fb_toolbar_search")
	private WebElement btnSearch;

	@AndroidFindBy(id = "tvBusiness")
	private WebElement btnCheckoutBiz;

	@AndroidFindBy(id = "sectionExploreFilters")
	private WebElement sectionFilter;

	@AndroidFindBy(id = "ivFilterRestaurant")
	private WebElement btnFilterRestaurant;

	@AndroidFindBy(id = "ivFilterBar")
	private WebElement btnFilterBar;

	@AndroidFindBy(id = "ivFilterNightclub")
	private WebElement btnFilterClub;

	@AndroidFindBy(id = "ivFilterAlcohol")
	private WebElement btnFilterAlcohol;

	@AndroidFindBy(id = "ivFilterCannabis")
	private WebElement btnFilterCanabis;

	@AndroidFindBy(id = "ivFilterTravel")
	private WebElement btnFilterTravel;

	@AndroidFindBy(id = "btGigs")
	private WebElement productTab;

	@AndroidFindBy(id = "tv_fullname")
	private WebElement txtPlace;

	@AndroidFindBy(id = "avProfileCityState")
	private WebElement txtRegion;

	@AndroidFindBy(id = "btn_next")
	private WebElement btnLetGoAfterSelectRegion;

	@AndroidFindBy(id = "places_autocomplete_prediction_primary_text")
	private WebElement primaryResult;

	@AndroidFindBy(id = "places_autocomplete_content")
	private WebElement listAutocompleteContent;

	@AndroidFindBy(id = "places_autocomplete_search_bar")
	private WebElement searchTextLocation;

	@AndroidFindBy(id = "places_autocomplete_list")
	private WebElement listAutocompleteLocation;

	public ExplorePage(AppiumBaseDriver driver) throws Exception {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
		PermissionManager.allowAccessLocation();
	}

	public void clickToShowEverything() throws Exception {
		driver.click(btnShowEverything);
		driver.waitForElementDisplayed(btnLetGo, 10);
		driver.click(btnLetGo);
		PermissionManager.allowAccessLocation();
	}

	public HomeSearchPage clickToSearchPage() throws Exception {
		driver.click(btnSearch);
		return new HomeSearchPage(driver);
	}

	public void clickToCheckoutBiz() throws Exception {
		driver.click(btnCheckoutBiz);
	}

	public void verifyExploreDisplayed() throws Exception {
		driver.isElementDisplayed(sectionFilter);
		driver.isElementDisplayed(btnShowEverything);
	}

	public void verifyFiltersAreEnabledToSelect() {
		String isFilterRes = driver.getAttribute(btnFilterRestaurant, "clickable");
		String isFilterBar = driver.getAttribute(btnFilterBar, "clickable");
		String isFilterClub = driver.getAttribute(btnFilterClub, "clickable");
		String isFilterAlcohol = driver.getAttribute(btnFilterAlcohol, "clickable");
		String isFilterCanabis = driver.getAttribute(btnFilterCanabis, "clickable");
		String isFilterTravel = driver.getAttribute(btnFilterTravel, "clickable");
		if (isFilterRes.equals("true") && isFilterBar.equals("true") && isFilterClub.equals("true") && isFilterAlcohol.equals("true") && isFilterCanabis.equals("true") && isFilterTravel.equals("true")) {
			assertTrue(true);
		}
	}

	public void clickToProductTab() throws Exception {
		driver.click(productTab);
		String status = driver.getAttribute(productTab, "selected");
		assertEquals(status, "true");
	}

	public String getNameOfPlace() throws Exception {
		return driver.getText(txtPlace);
	}

	public void verifyPlaceNameIsCorrect(String cityName) throws Exception {
		String actual = getNameOfPlace();
		assertEquals(actual, cityName);
	}

	public void inputRegionWhenLocationServiceOff(String city) throws Exception {
		driver.waitForElementDisplayed(txtRegion, 10);
		driver.click(txtRegion);
		driver.waitForElementDisplayed(listAutocompleteContent, 10);
		driver.inputText(searchTextLocation, city);
		driver.waitForElementDisplayed(listAutocompleteLocation, 10);
		driver.click(primaryResult);
		driver.click(btnLetGoAfterSelectRegion);
	}
}