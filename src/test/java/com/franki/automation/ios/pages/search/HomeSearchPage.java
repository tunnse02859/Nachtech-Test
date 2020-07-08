package com.franki.automation.ios.pages.search;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.datamodel.BusinessData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.report.HtmlReporter;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HomeSearchPage {
	public AppiumBaseDriver driver;

	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeSearchField")
	private WebElement inputSearchText;

	@iOSXCUITFindBy(iOSNsPredicate = "name = \"Cancel\"")
	private WebElement btnCancel;

//	// Empty Screen when first in
//	@iOSXCUITFindBy(iOSNsPredicate = "value = \"get searchin\"")
//	private WebElement lblGetSearchin;
//
//	@iOSXCUITFindBy(iOSNsPredicate = "value = \"find what you're trying to get into.\"")
//	private WebElement lblGetSearchinDes;
//
//	// No results found
//	@iOSXCUITFindBy(iOSNsPredicate = "value = \"party foul\"")
//	private WebElement lblPartyFoul;
//
//	@iOSXCUITFindBy(iOSNsPredicate = "value = \"no results for you, try again\"")
//	private WebElement lblNoResultsForYou;

	// People search
	@iOSXCUITFindBy(accessibility = "people")
	private WebElement tabPeopleSearch;

	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> listPeopleResults;

	private By lblUserSearchDisplayName = MobileBy.name("displayNameLabel");
	private By lblUserSearchFullName = MobileBy.name("fullNameLabel");

	// Places search
	@iOSXCUITFindBy(accessibility = "places")
	private WebElement tabPlacesSearch;

	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> listPlacesResults;

	private By lblBusinessCategory = MobileBy.AccessibilityId("captionLabel");
	private By lblBusinessSearchDisplayName = MobileBy.AccessibilityId("businessNameLabel");
	private By lblBusinessSearchAddress = MobileBy.AccessibilityId("addressLabel");

	// Product search
	@iOSXCUITFindBy(accessibility = "products")
	private WebElement tabProductSearch;

	@iOSXCUITFindBy(className = "XCUIElementTypeCell")
	private List<WebElement> listProductResults;

	public HomeSearchPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(inputSearchText);
	}

	public void inputSearch(String text) throws Exception {
		driver.inputTextWithClear(inputSearchText, text);
	}

	public void goBackToPreviousPage() {
		driver.goBack();
	}

	/************** On tab People *****************/
	public void clickTabPeople() throws Exception {
		driver.click(tabPeopleSearch);
	}

	public boolean isOnTabPeople() {
		return Boolean.valueOf(driver.getAttribute(tabPeopleSearch, "selected"));
	}

	public boolean isUsersReturnedForSearchText(String searchText) {
		if (listPeopleResults.size() > 0) {

			return listPeopleResults.stream().allMatch(result -> {
				return result.findElement(lblUserSearchDisplayName).getText().toUpperCase()
						.contains(searchText.toUpperCase())
						|| result.findElement(lblUserSearchFullName).getText().toUpperCase()
								.contains(searchText.toUpperCase());
			});
		}
		return false;
	}

	public ArrayList<UserData> getTheUserSearchResults() {

		ArrayList<UserData> searchedUsers = new ArrayList<>();

		listPeopleResults.forEach(result -> {
			UserData user = new UserData();
			user.setDisplayName(result.findElement(lblUserSearchDisplayName).getText());
			user.setFullName(result.findElement(lblUserSearchFullName).getText());

			searchedUsers.add(user);
		});

		return searchedUsers;
	}

	/************** On tab Places *****************/

	public void clickTabPlaces() throws Exception {
		driver.click(tabPlacesSearch);
	}

	public boolean isOnTabPlaces() {
		return Boolean.valueOf(driver.getAttribute(tabPlacesSearch, "selected"));
	}

	public ArrayList<BusinessData> getTheBusinessSearchResults() {

		ArrayList<BusinessData> searchedBusinesses = new ArrayList<>();

		listPlacesResults.forEach(result -> {
			BusinessData business = new BusinessData();
			business.setDisplayName(result.findElement(lblBusinessSearchDisplayName).getText());
			business.setAddress(result.findElement(lblBusinessSearchAddress).getText());

			searchedBusinesses.add(business);
		});

		return searchedBusinesses;
	}

	public boolean isBusinessesReturnedForSearchText(String searchText) {
		if (listPlacesResults.size() > 0) {

			return listPeopleResults.stream().allMatch(result -> {
				return result.findElement(lblBusinessSearchDisplayName).getText().toUpperCase()
						.contains(searchText.toUpperCase());
			});
		}
		return false;
	}

	public void selectSearchPlaceResultByText(String searchText) throws Exception {
		if (isResultDisplayed()) {
			listPlacesResults.forEach(place -> {
				WebElement displayName = place.findElement(lblBusinessSearchDisplayName);
				if (displayName.getText().equalsIgnoreCase(searchText)) {
					displayName.click();
					HtmlReporter.pass("Click on the searched business: " + searchText);
				}
			});
		} else {
			throw new Exception("Place search for text not found: " + searchText);
		}
	}

	/************** On tab Product *****************/

	public void clickTabProduct() throws Exception {
		driver.click(tabProductSearch);
	}

	public boolean isOnTabProducts() {
		return Boolean.valueOf(driver.getAttribute(tabProductSearch, "selected"));
	}

	public boolean isResultDisplayed() {

		if (isOnTabPeople()) {
			return listPeopleResults.size() > 0;
		}

		if (isOnTabPlaces()) {
			return listPlacesResults.size() > 0;
		}

		if (isOnTabProducts()) {
			return listProductResults.size() > 0;
		}

		return false;
	}

	public void clickSearchResult(int index) throws Exception {
		if (isOnTabPeople()) {
			driver.click(listPeopleResults.get(index));
			return;
		}

		if (isOnTabPlaces()) {
			driver.click(listPlacesResults.get(index));
			return;
		}

		if (isOnTabProducts()) {
			driver.click(listProductResults.get(index));
			return;
		}
	}

	public boolean isEmpySearch() throws Exception {
		return listPeopleResults.size() == 0 ;
	}

	public boolean isNoResultScreen() throws Exception {
		return listPeopleResults.size() == 0 ;
	}

}