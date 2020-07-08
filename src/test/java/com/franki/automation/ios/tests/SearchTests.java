package com.franki.automation.ios.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.franki.automation.utility.Assertion.*;

import java.util.ArrayList;

import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.BusinessAPI;
import com.franki.automation.datamodel.BusinessData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.ios.pages.business.BusinessProfilePage;
import com.franki.automation.ios.pages.home.HomePage;
import com.franki.automation.ios.pages.login.LoginWithUsernamePage;
import com.franki.automation.ios.pages.profile.OtherUserProfilePage;
import com.franki.automation.ios.pages.search.HomeSearchPage;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class SearchTests extends MobileTestSetup {

	LoginWithUsernamePage loginPage;
	OtherUserProfilePage otherUserProfilePage;
	BusinessProfilePage businessProfilePage;
	HomePage homePage;
	HomeSearchPage searchPage;


	@Test
	public void FRAN_3485_Verify_empty_state_display_when_tap_search_icon() throws Exception {

		// Login
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");
		
		// Goto Search screen
		searchPage = homePage.clickOnSearchIcon();
		
		// On tab people as default
		assertTrue(searchPage.isOnTabPeople(), "Search screen is not on People tab as default", "Search screen is on People tab as default");
		// Empty state
		assertTrue(searchPage.isEmpySearch(), "Search screen is not empty as default", "Search screen is empty as default");
		
	}
	
	@Test
	public void FRAN_5611_Verify_no_result_return_screen() throws Exception {

		// Login
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");
		
		// Goto Search screen
		searchPage = homePage.clickOnSearchIcon();
		
		// select tab people
		searchPage.clickTabPeople();
		assertTrue(searchPage.isOnTabPeople(), "Search People screen is not displayed",
				"Search People screen is displayed");
		searchPage.inputSearch("dsferweffdsfdsfsdfdsfd");
		assertTrue(searchPage.isNoResultScreen(), "No-Result page is not displayed", "No-Result page is displayed");
		
		// select tab places
		searchPage.clickTabPlaces();
		assertTrue(searchPage.isOnTabPlaces(), "Search Place screen is not displayed",
				"Search Place screen is displayed");
		searchPage.inputSearch("dsferweffdsfdsfsdfdsfd");
		assertTrue(searchPage.isNoResultScreen(), "No-Result page is not displayed", "No-Result page is displayed");
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
		searchPage = homePage.clickOnSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab people
		searchPage.clickTabPeople();
		assertTrue(searchPage.isOnTabPeople(), "Search People screen is not displayed",
				"Search People screen is displayed");

		// search user
		searchPage.inputSearch(userSearchText);
		assertTrue(searchPage.isUsersReturnedForSearchText(userSearchText), "The correct users not return", "The correct users return");
		
	}
	
	@Test(dataProvider = "UserSearchData")
	public void FRAN_3487_Verify_able_view_user_profile_when_tap_a_search_result(String userSearchText) throws Exception {
		loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		homePage = new HomePage(driver);
		assertTrue(homePage.isActive(), "User is not in Homepage", "You're in Homepage");

		// search for clone user
		searchPage = homePage.clickOnSearchIcon();
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
		
		assertEquals(userProfilePAge.getDisplayName(), searchedUsers.get(0).getDisplayName(), "The user's DisplayName is not correct", "The user's DisplayName is correct");
		assertEquals(userProfilePAge.getFullName(), searchedUsers.get(0).getFullName(), "The user's FullName is not correct", "The user's FullName is correct");	
	}
	
	@DataProvider(name = "BusinessIds")
	public Object[][] generateBusinessIds() {
		return new Object[][] { { Constant.BusinessIds.LOCAL_BUSINESS_ID },
				{ Constant.BusinessIds.GOOGLE_BUSINESS_ID } };
	}
	
	@Test(dataProvider = "BusinessIds")
	public void FRAN_5613Verify_user_able_search_local_or_google_business(int businessId) throws Exception {

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
		searchPage = homePage.clickOnSearchIcon();
		assertTrue(searchPage.isActive(), "Search screen is not displayed", "Search screen is displayed");

		// select tab people
		searchPage.clickTabPlaces();
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
		
		assertEquals(businessPage.getBusinessDisplayName(), searchedBusinesses.get(0).getDisplayName(), "The Business's DisplayName is not correct", "The Business's DisplayName is correct");
		assertEquals(businessPage.getBusinessAddress(), searchBusinessAddress, "The Business's Address is not correct", "The Business's Address is correct");
	}
	

}
