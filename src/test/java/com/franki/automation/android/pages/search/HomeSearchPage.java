package com.franki.automation.android.pages.search;

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
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomeSearchPage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "tietSearch")
	private WebElement inputSearchText;

	@AndroidFindBy(className = "android.view.ViewGroup")
	private List<WebElement> listPeopleResults;

	private By lblUserSearchDisplayName = MobileBy.id("tvInfoUsername");
	private By lblUserSearchFullName = MobileBy.id("tvInfoLocation");

	@AndroidFindBy(id = "emptyview")
	private WebElement emptyView;

	@AndroidFindBy(id = "btPeople")
	private WebElement btnPeoples;

	@AndroidFindBy(id = "btPlaces")
	private WebElement btnPlaces;

	@AndroidFindBy(id = "clInfoContainer")
	private List<WebElement> listPlacesResults;

	private By lblBusinessCategory = MobileBy.id("tvCategory");
	private By lblBusinessSearchDisplayName = MobileBy.id("tvInfoUsername");
	private By lblBusinessSearchAddress = MobileBy.id("tvInfoLocation");

	public HomeSearchPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(inputSearchText);
	}

	public void clickTabPeople() throws Exception {
		driver.click(btnPeoples);
	}

	public boolean isOnTabPeople() {
		return Boolean.valueOf(driver.getAttribute(btnPeoples, "selected"));
	}

	public void clickTabPlace() throws Exception {
		driver.click(btnPlaces);
	}

	public boolean isOnTabPlaces() {
		return Boolean.valueOf(driver.getAttribute(btnPlaces, "selected"));
	}

	public void inputSearch(String text) throws Exception {
		driver.inputTextWithClear(inputSearchText, text);
	}

	public boolean isUsersReturnedForSearchText(String searchText) {
		if (listPeopleResults.size() > 0) {
			for(WebElement result : listPeopleResults) {
				if(!result.findElement(lblUserSearchDisplayName).getText().toUpperCase()
						.contains(searchText.toUpperCase()) || !result.findElement(lblUserSearchFullName).getText().toUpperCase()
						.contains(searchText.toUpperCase())) {
					return false;
				}
			}
			return true;
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
			for(WebElement result : listPlacesResults) {
				if(!result.findElement(lblBusinessSearchDisplayName).getText().toUpperCase()
						.contains(searchText.toUpperCase()))
					return false;
			}
			return true;
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

	public boolean isResultDisplayed() {
		if (isOnTabPeople()) {
			return listPeopleResults.size() > 0;
		}

		if (isOnTabPlaces()) {
			return listPlacesResults.size() > 0;
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

	}

	public boolean isResultDisplayed(String displayedName) {
		try {
			String locator = String.format("new UiSelector().className(\"android.widget.TextView\").text(\"%s\")",
					displayedName);
			By by = MobileBy.AndroidUIAutomator(locator);
			driver.getDriver().findElement(by);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void clickSearchResult(String displayedName) throws Exception {
		String locator = String.format("new UiSelector().className(\"android.widget.TextView\").text(\"%s\")",
				displayedName);
		By by = MobileBy.AndroidUIAutomator(locator);
		WebElement searchResult = driver.getDriver().findElement(by);
		driver.click(searchResult);
	}

	public void goBackToPreviousPage() {
		driver.goBack();
	}

	public boolean isEmptyScreenDisplayed() throws Exception {
		return driver.isElementDisplayed(emptyView);
	}

}