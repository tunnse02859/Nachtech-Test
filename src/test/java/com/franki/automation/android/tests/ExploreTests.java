package com.franki.automation.android.tests;

import static com.franki.automation.utility.Assertion.assertEquals;
import static com.franki.automation.utility.Assertion.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.franki.automation.android.pages.business.BusinessProfilePage;
import com.franki.automation.android.pages.business.ProductPage;
import com.franki.automation.android.pages.explore.ExplorePage;
import com.franki.automation.android.pages.home.HomePage;
import com.franki.automation.android.pages.login.LoginWithUsernamePage;
import com.franki.automation.android.pages.search.HomeSearchPage;
import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.BusinessAPI;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class ExploreTests extends MobileTestSetup {

	AuthenticateAPI authenAPI_1;
	BusinessAPI corporateGroupAPI;

	String businessProductName;
	String productName;

	@BeforeClass
	public void setupAPI() throws Exception {
		authenAPI_1 = new AuthenticateAPI();
		UserData user1 = authenAPI_1.loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		corporateGroupAPI = new BusinessAPI(user1);

		businessProductName = corporateGroupAPI.getCorporateGroupDisplayNameInSearchListByIndex(0);
		productName = corporateGroupAPI.getProductNameInBusinessGroup(0, 0);
	}

	@Test
	public void FRAN3429_VerifyUserIsAbleToSearchSpecificProduct() throws Exception {
		LoginWithUsernamePage loginWithUsernamePage = new LoginWithUsernamePage(driver);

		HomePage homePage = loginWithUsernamePage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		ExplorePage explorePage = homePage.clickToExploreTab();
		
		// On Explore Page
		explorePage.clickToShowEverything();
		explorePage.clickToProductTab();
		HomeSearchPage searchPage = explorePage.clickToSearchPage();

		// On Search Page + search and verify result displayed
		searchPage.clickTabPlace();
		searchPage.inputSearch(businessProductName);
		assertTrue(searchPage.isResultDisplayed(businessProductName), "Cannot see expected Search result", "Search result is displayed correctly");

		// go to product profile
		searchPage.clickSearchResult(businessProductName);
		BusinessProfilePage groupProfilePage = new BusinessProfilePage(driver);
		// verify opened profile is correct
		assertTrue(groupProfilePage.isActive(), "Profile screen is not displayed", "Profile screen is displayed");
		groupProfilePage.clickToProductsTab();
		

		ProductPage productPage = groupProfilePage.selectProductInProductsListByIndex(0);

		assertEquals(businessProductName, productPage.getBusinessName(), "A business product name is not equal", "A business product name is correctly");
		assertEquals(productName, productPage.getProductName(), "A product name is not equal", "A product name is correctly");
	}

	@Test
	public void FRAN3430_VerifyUserIsAbleToSearchSpecificCity() throws Exception {
		LoginWithUsernamePage loginWithUsernamePage = new LoginWithUsernamePage(driver);

		HomePage homePage = loginWithUsernamePage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		ExplorePage explorePage = homePage.clickToExploreTab();

		// On Explore Page
		explorePage.clickToShowEverything();
		HomeSearchPage searchPage = explorePage.clickToSearchPage();

		// On Search Page
		// need to be update after finding API to search place
		// Assert.assertTrue(searchPage.verifySearchResultDisplayed(cityPlace));
	}

	@Test
	public void FRAN3428_VerifyUserIsAbleToSearchSpecificBusiness() throws Exception {
		LoginWithUsernamePage loginWithUsernamePage = new LoginWithUsernamePage(driver);

		HomePage homePage = loginWithUsernamePage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		ExplorePage explorePage = homePage.clickToExploreTab();

		// On Explore Page
		explorePage.clickToShowEverything();
		explorePage.clickToProductTab();
		HomeSearchPage searchPage = explorePage.clickToSearchPage();

		// On Search Page
		searchPage.clickTabPlace();
		searchPage.inputSearch(businessProductName);
		assertTrue(searchPage.isResultDisplayed(businessProductName), "Cannot see expected Search result", "Search result is displayed correctly");
	}
}