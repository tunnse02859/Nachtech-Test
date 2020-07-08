package com.franki.automation.android.tests;

import static com.franki.automation.utility.Assertion.assertEquals;
import static com.franki.automation.utility.Assertion.assertTrue;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.franki.automation.android.pages.home.HomePage;
import com.franki.automation.android.pages.login.LoginWithUsernamePage;
import com.franki.automation.android.pages.profile.OtherUserProfilePage;
import com.franki.automation.android.pages.search.HomeSearchPage;
import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.BusinessAPI;
import com.franki.automation.datamodel.BusinessData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.android.pages.business.BusinessProfilePage;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class SearchTests extends MobileTestSetup {

	LoginWithUsernamePage loginPage;
	OtherUserProfilePage otherUserProfilePage;
	HomePage homePage;
	HomeSearchPage searchPage;

	@Test
	public void FRAN_3485_Verify_empty_state_displayed_when_tap_to_search() throws Exception {
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
		
		
		HomeSearchPage searchPage = homePage.clickToSearchIcon();
		assertTrue(searchPage.isEmptyScreenDisplayed(),"Empty state is not displayed","Empty state is  displayed correctly");
	}
	
	@DataProvider(name = "UserSearchData")
	public Object[][] generateUserSearchData() {
		return new Object[][] { { "Anh" }};
	}
	
	@Test(dataProvider = "UserSearchData")
	public void FRAN_3486_Verify_user_able_search_user(String userSearchText) throws Exception {
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");

		// search for clone user
		searchPage = homePage.clickToSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab people
		searchPage.clickTabPeople();
		assertTrue(searchPage.isOnTabPeople(), "Search People screen is not displayed",
				"Search People screen is displayed");

		// search user
		searchPage.inputSearch(userSearchText);
		assertTrue(searchPage.isUsersReturnedForSearchText(userSearchText), "The correct users not return", "The correct users return");
		
	}
	
	@Test
	public void FRAN_5611_Verify_party_foul_screen_displayed_when_no_search_result() throws Exception {
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Home page");
		
		
		searchPage = homePage.clickToSearchIcon();
		assertTrue(searchPage.isEmptyScreenDisplayed(),"Empty state is not displayed","Empty state is  displayed correctly");
		
		searchPage.inputSearch("dsferweffdsfdsfsdfdsfd");
		assertTrue(searchPage.isEmptyScreenDisplayed(),"Party foul screen is not displayed","Party foul screen is  displayed correctly");
	}
	
	
	@Test(dataProvider = "UserSearchData")
	public void FRAN_3487_Verify_able_view_user_profile_when_tap_a_search_result(String userSearchText) throws Exception {
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");

		// search for clone user
		searchPage = homePage.clickToSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab people
		searchPage.clickTabPeople();
		assertTrue(searchPage.isOnTabPeople(), "Search People screen is not displayed",
				"Search People screen is displayed");

		// search user
		searchPage.inputSearch(userSearchText);
		assertTrue(searchPage.isUsersReturnedForSearchText(userSearchText), "The correct users not return", "The correct users return");
		
		// Save the first search result and click on it
		ArrayList<UserData> searchedUsers = searchPage.getTheUserSearchResults();
		
		searchPage.clickSearchResult(0);
		OtherUserProfilePage userProfilePAge = new OtherUserProfilePage(driver);
		assertTrue(userProfilePAge.isActive(), "You are not on the User Profile page", "You are on the User Profile page");
		
		assertEquals(userProfilePAge.getDisplayedName(), searchedUsers.get(0).getDisplayName(), "The user's DisplayName is not correct", "The user's DisplayName is correct");
		assertEquals(userProfilePAge.getFullName(), searchedUsers.get(0).getFullName(), "The user's FullName is not correct", "The user's FullName is correct");	
	}
	
	@DataProvider(name = "BusinessIds")
	public Object[][] generateBusinessIds() {
		return new Object[][] { { Constant.BusinessIds.LOCAL_BUSINESS_ID },
				{ Constant.BusinessIds.GOOGLE_BUSINESS_ID } };
	}
	
	@Test(dataProvider = "BusinessIds")
	public void FRAN_5613_Verify_user_able_search_local_or_google_business(int businessId) throws Exception {

		/*************** Setup *********************************/
		UserData user = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		BusinessAPI businessAPI = new BusinessAPI(user);
		// Setup
		BusinessData businessDataOnAPI = businessAPI.getBusinessDetails(businessId);
		String searchBusinessDisplayName = businessDataOnAPI.getDisplayName();
		String searchBusinessAddress = businessDataOnAPI.getAddress();

		/*************** Actions *********************************/
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		searchPage = homePage.clickToSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab people
		searchPage.clickTabPlace();
		assertTrue(searchPage.isOnTabPlaces(), "Search Place screen is not displayed",
				"Search Place screen is displayed");

		// search 
		searchPage.inputSearch(searchBusinessDisplayName);
		
		/*************** Verify *********************************/
		assertTrue(searchPage.isBusinessesReturnedForSearchText(searchBusinessDisplayName), "The correct businesses not return", "The correct businesses return");
		
		// Save the first search result and click on it
		ArrayList<BusinessData> searchedBusinesses = searchPage.getTheBusinessSearchResults();
		searchPage.clickSearchResult(0);
		
		BusinessProfilePage businessPage = new BusinessProfilePage(driver);
		assertTrue(businessPage.isActive(), "Business Profile screen is not displayed", "Business Profile screen is displayed");
		
		assertEquals(businessPage.getBusinessDisplayedName(), searchedBusinesses.get(0).getDisplayName(), "The Business's DisplayName is not correct", "The Business's DisplayName is correct");
		assertEquals(businessPage.getBusinessAddress(), searchBusinessAddress, "The Business's Address is not correct", "The Business's Address is correct");
	}

	
}
