package com.franki.automation.ios.tests;

import static com.franki.automation.utility.Assertion.assertTrue;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.franki.automation.api.AuthenticateAPI;
import com.franki.automation.api.GigsAPI;
import com.franki.automation.datamodel.GigData;
import com.franki.automation.datamodel.GigPrizeData;
import com.franki.automation.datamodel.GigRuleData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.ios.pages.business.BusinessProfilePage;
import com.franki.automation.ios.pages.gigs.GigProfilePage;
import com.franki.automation.ios.pages.gigs.GigsPage;
import com.franki.automation.ios.pages.home.BottomBarPage;
import com.franki.automation.ios.pages.login.LoginWithUsernamePage;
import com.franki.automation.ios.pages.payment.PaymentPage;
import com.franki.automation.report.Log;
import com.franki.automation.setup.Constant;
import com.franki.automation.setup.MobileTestSetup;

public class GigsTests extends MobileTestSetup {

	UserData user;
	private UserData admin;
	private ArrayList<GigData> activeGigs = new ArrayList<>();
	private int[] allBizTypes = { 9, 2, 8, 12, 11, 0 };
	private int PERMIT_MAX_ACTIVE_GIGS = 8;

	@DataProvider(name = "StateAndLocation")
	public Object[][] LocationAndState() {
		return new Object[][] { { "Alaska", "California" } };
	}

	@BeforeClass
	public void setupAPI() throws Exception {
		user = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		admin = new AuthenticateAPI().loginForAccessToken(Constant.ACCOUNT_ADMIN, Constant.ACCOUNT_PASSWORD_ADMIN);
	}

	/**
	 * Create active gigs as test data if not enough
	 * 
	 * @throws Exception
	 */
	private ArrayList<GigData> setupActiveGigs() throws Exception {
		GigsAPI adminGigAPI = new GigsAPI(admin);

		activeGigs = new GigsAPI(user).getAllActiveGigsByCorporateGroup(allBizTypes, Constant.StateIds.NATION_WIDE_ID, Constant.Location.LATITUDE, Constant.Location.LONGITUDE);

		// Create at least 2 active gigs
		if (activeGigs.size() < 2) {
			adminGigAPI.adminCreateActiveGig(Constant.BusinessIds.LOCAL_BUSINESS_ID);
			adminGigAPI.adminCreateActiveGig(Constant.BusinessIds.LOCAL_BUSINESS_ID);
		}

		// Remove gigs if there are too many gigs
		if (activeGigs.size() > PERMIT_MAX_ACTIVE_GIGS) {
		}

		return activeGigs;
	}

	public GigsPage gotoGigsPage() throws Exception {

		LoginWithUsernamePage loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		BottomBarPage bottomBarPage = new BottomBarPage(driver);
		assertTrue(bottomBarPage.isActive(), "User is not in Homepage", "You're in Home page");
		// click profile tab here
		bottomBarPage.clickOnGigsTab();
		
		return new GigsPage(driver).clickSkipInstruction();
	}

	private boolean compareGigDataList(ArrayList<GigData> list1, ArrayList<GigData> list2) {
		if (list1.size() != list2.size()) {
			return false;
		}

		if (list1.size() == list2.size() && list2.size() == 0) {
			return true;
		}

		for (int i = 0; i < list1.size(); i++) {
			GigData item1 = list1.get(i);

			if (!list2.stream().anyMatch(item2 -> item2.getGigName().equals(item1.getGigName()))) {
				return false;
			}
		}

		for (int i = 0; i < list2.size(); i++) {
			GigData item2 = list2.get(i);

			if (!list1.stream().anyMatch(item1 -> item1.getGigName().equals(item2.getGigName()))) {
				return false;
			}
		}

		return true;
	}

	private boolean compareGigDataListInOrder(ArrayList<GigData> list1, ArrayList<GigData> list2) {
		if (list1.size() != list2.size()) {
			return false;
		}

		if (list1.size() == list2.size() && list2.size() == 0) {
			return true;
		}

		for (int i = 0; i < list1.size(); i++) {
			if (!list1.get(i).getGigName().equals(list2.get(i).getGigName())) {
				return false;
			}
		}

		return true;
	}

	private boolean compareGigDataListInPotentialMoneyOrder(ArrayList<GigData> gigsFromUI, ArrayList<GigData> gigFromAPI) {
		if (gigsFromUI.size() != gigFromAPI.size()) {
			return false;
		}

		if (gigsFromUI.size() == gigFromAPI.size() && gigFromAPI.size() <= 1) {
			return true;
		}

		for (int i = 0; i < gigFromAPI.size(); i++) {

			if (gigFromAPI.get(i).getGigPotentialPrize() == gigFromAPI.get(i + 1).getGigPotentialPrize()) {
				break;
			}

			if (!gigFromAPI.get(i).getGigName().equals(gigsFromUI.get(i).getGigName())) {
				return false;
			}
		}

		return true;
	}

	@Test
	public void FRAN_5674__VerifyAnnimationsDisplayed() throws Exception {
		/*********** Actions ****************/
		LoginWithUsernamePage loginPage = new LoginWithUsernamePage(driver);
		loginPage.doLoginProcess(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1);
		BottomBarPage bottomBarPage = new BottomBarPage(driver);
		assertTrue(bottomBarPage.isActive(), "User is not in Homepage", "You're in Home page");
		// click profile tab here
		bottomBarPage.clickOnGigsTab();
		
		/*********** Verify ****************/
		
		GigsPage gigsPage = new GigsPage(driver);
		
		// On Step 1
		String strInstructTitle = gigsPage.getInstructionTitle();
		String strInstructDescription = gigsPage.getInstructionDescription();
		Assert.assertEquals(strInstructTitle, "step 1: find your gig");
		Assert.assertEquals(strInstructDescription, "you can find gigs through: explore, business profiles, or posts on your home feed - look for the yellow gig pin.");
		
		// On Step 2
		gigsPage.swipeLeft();
		strInstructTitle = gigsPage.getInstructionTitle();
		strInstructDescription = gigsPage.getInstructionDescription();
		Assert.assertEquals(strInstructTitle, "step 2: read the rules");
		Assert.assertEquals(strInstructDescription, "each gig has a set of rules to follow so you can be eligible to win. you can save gigs for later, but don’t miss the deadline.");
		
		// On Step 3
		gigsPage.swipeLeft();
		strInstructTitle = gigsPage.getInstructionTitle();
	 	strInstructDescription = gigsPage.getInstructionDescription();
		Assert.assertEquals(strInstructTitle, "step 3: make your video");
		Assert.assertEquals(strInstructDescription, "from gig details, upload your video. don’t be afraid to get creative and have fun. be sure to check out the competition.");
		
		// On Step 4
		gigsPage.swipeLeft();
		strInstructTitle = gigsPage.getInstructionTitle();
		strInstructDescription = gigsPage.getInstructionDescription();
		Assert.assertEquals(strInstructTitle, "step 4: get paid");
		Assert.assertEquals(strInstructDescription, "businesses pick the winners. if your review is chosen, we will notify you. go to gigs to see your earnings and cash out.");
		
		// Click COOL
		gigsPage.clickOnCool();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		
	}
	
	@Test
	public void FRAN3470_VerifyLocationQuickFilterDisplayed() throws Exception {
		/*********** Actions ****************/
		GigsPage gigsPage = gotoGigsPage();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		gigsPage.clickToLocationQuickFilter();

		/*********** Verify ****************/
		assertTrue(gigsPage.isLocationOptionsDisplayed(), "Location options is not displayed", "Location options is displayed");
	}

	@DataProvider(name = "FilterByStateData")
	public Object[][] generateBusinessIds() {
		return new Object[][] { { Constant.StateIds.COLORADO_STATE_ID, Constant.StateIds.COLORADO_STATE_NAME, Constant.BusinessIds.COLORADO_TESTING_BUSINESS_ID } };
	}

	@Test(dataProvider = "FilterByStateData")
	public void FRAN3471_VerifyUsersAreAbleToSearchByState(int stateId, String stateName, int businessIdInState) throws Exception {

		/*********** Setup ****************/
		activeGigs = setupActiveGigs();
		if (!activeGigs.stream().anyMatch(e -> e.getGigCorporateGroupId() == businessIdInState)) {
			new GigsAPI(admin).adminCreateActiveGig(businessIdInState);
		}

		ArrayList<GigData> activeGigsByStateFromAPI = new GigsAPI(user).getAllActiveGigsByCorporateGroup(allBizTypes, stateId, Constant.Location.LATITUDE, Constant.Location.LONGITUDE);

		/*********** Actions ****************/
		GigsPage gigsPage = gotoGigsPage();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		gigsPage.clickToLocationQuickFilter();
		assertTrue(gigsPage.isLocationOptionsDisplayed(), "Location options is not displayed", "Location options is displayed");

		// Tap on Pick State
		gigsPage.clickOnPickAStateTab();
		assertTrue(gigsPage.verifyPickStateIsSelected(), "Pick a state option is not selected", "Pick a state option is selected");

		gigsPage.selectAState(stateName);
		assertTrue(gigsPage.getStateAfterSelect().equalsIgnoreCase(stateName), stateName + "is not selected", stateName + "is selected");
		gigsPage.clickToDoneBtn();

		assertTrue(gigsPage.getTextDisplayInLocationFilter().equalsIgnoreCase(stateName), "You're not on State filter", "You're on State filter");

		ArrayList<GigData> gigListFromUi = gigsPage.getGigList();

		assertTrue(compareGigDataList(gigListFromUi, activeGigsByStateFromAPI), "Filter by State returns incorrect data", "Filter by State returns correct data");
	}

	@Test
	public void FRAN3472_VerifyUsersAreAbleToSearchByMyLocation() throws Exception {
		/******* Setup ****************************/
		setupActiveGigs();

		/*********** Actions ****************/
		GigsPage gigsPage = gotoGigsPage();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		gigsPage.clickToLocationQuickFilter();
		assertTrue(gigsPage.isLocationOptionsDisplayed(), "Location options is not displayed", "Location options is displayed");

		// Tap on My Location
		gigsPage.clickToMyLocation();
		assertTrue(gigsPage.verifyMyLocationIsSelected(), "My Location option is not selected", "My Location option is selected");
		gigsPage.clickToDoneBtn();

		assertTrue(gigsPage.getTextDisplayInLocationFilter().equalsIgnoreCase("my location"), "You're not on My Location filter", "You're on My Location filter");

		ArrayList<GigData> gigListFromUi = gigsPage.getGigList();

		assertTrue(compareGigDataList(gigListFromUi, activeGigs), "Filter by My Location returns incorrect data", "Filter by My Location returns correct data");
	}

	@Test
	public void FRAN3473_VerifyUsersAreAbleToSearchByBusinessType() throws Exception {

		/******* Setup ****************************/
		activeGigs = setupActiveGigs();
		ArrayList<GigData> gigListFromUi = new ArrayList<>();
		ArrayList<GigData> gigListFromAPI = new ArrayList<>();

		/*********** Actions ****************/

		GigsPage gigsPage = gotoGigsPage();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		gigsPage.clickToBizTypesQuickFilter();
		assertTrue(gigsPage.isBizTypesOptionsDisplayed(), "Biz types options is not displayed", "Biz types options is displayed");

		/*********** Verify ****************/
		// VErify Restaurant type
		gigsPage.selectBizType(Constant.BizTypes.BIZ_RESTAURANTS);
		gigsPage.clickToDoneBtn();

		gigListFromUi = gigsPage.getGigList();
		gigListFromAPI = (ArrayList<GigData>) activeGigs.stream().filter(e -> e.getGigBusiness().getBusinessTypeId() == Constant.BizTypes.BIZ_RESTAURANTS_ID).collect(Collectors.toList());
		assertTrue(compareGigDataList(gigListFromUi, gigListFromAPI), "Filter by Restaurant returns incorrect data", "Filter by Restaurant returns correct data");

		// VErify Bars type
		gigsPage.clickToBizTypesQuickFilter();
		gigsPage.selectBizType(Constant.BizTypes.BIZ_RESTAURANTS);
		gigsPage.selectBizType(Constant.BizTypes.BIZ_BARS);
		gigsPage.clickToDoneBtn();

		gigListFromUi = gigsPage.getGigList();
		gigListFromAPI = (ArrayList<GigData>) activeGigs.stream().filter(e -> e.getGigBusiness().getBusinessTypeId() == Constant.BizTypes.BIZ_BARS_ID).collect(Collectors.toList());
		assertTrue(compareGigDataList(gigListFromUi, gigListFromAPI), "Filter by Bars returns incorrect data", "Filter by Bars returns correct data");

		// VErify NightClubs type
		gigsPage.clickToBizTypesQuickFilter();
		gigsPage.selectBizType(Constant.BizTypes.BIZ_BARS);// de-select
		gigsPage.selectBizType(Constant.BizTypes.BIZ_NIGHTCLUBS);
		gigsPage.clickToDoneBtn();

		gigListFromUi = gigsPage.getGigList();
		gigListFromAPI = (ArrayList<GigData>) activeGigs.stream().filter(e -> e.getGigBusiness().getBusinessTypeId() == Constant.BizTypes.BIZ_NIGHTCLUBS_ID).collect(Collectors.toList());

		assertTrue(compareGigDataList(gigListFromUi, gigListFromAPI), "Filter by NightClubs returns incorrect data", "Filter by NightClubs returns correct data");

		// VErify Take Away type
		gigsPage.clickToBizTypesQuickFilter();
		gigsPage.selectBizType(Constant.BizTypes.BIZ_NIGHTCLUBS);// de-select
		gigsPage.selectBizType(Constant.BizTypes.BIZ_TAKEAWAY);
		gigsPage.clickToDoneBtn();

		gigListFromUi = gigsPage.getGigList();
		gigListFromAPI = (ArrayList<GigData>) activeGigs.stream().filter(e -> e.getGigBusiness().getBusinessTypeId() == Constant.BizTypes.BIZ_TAKEAWAY_ID).collect(Collectors.toList());

		assertTrue(compareGigDataList(gigListFromUi, gigListFromAPI), "Filter by Takeaway returns incorrect data", "Filter by Takeaway returns correct data");

		// VErify Cafe type
		gigsPage.clickToBizTypesQuickFilter();
		gigsPage.selectBizType(Constant.BizTypes.BIZ_TAKEAWAY); // de-select
		gigsPage.selectBizType(Constant.BizTypes.BIZ_CAFE);
		gigsPage.clickToDoneBtn();

		gigListFromUi = gigsPage.getGigList();
		gigListFromAPI = (ArrayList<GigData>) activeGigs.stream().filter(e -> e.getGigBusiness().getBusinessTypeId() == Constant.BizTypes.BIZ_CAFE_ID).collect(Collectors.toList());

		assertTrue(compareGigDataList(gigListFromUi, gigListFromAPI), "Filter by Cafe returns incorrect data", "Filter by Cafe returns correct data");
	}

	@Test
	public void FRAN3475_VerifySortByQuickFilterDisplayed() throws Exception {

		ArrayList<GigData> gigListFromUi = new ArrayList<>();
		ArrayList<GigData> gigListFromAPI = new ArrayList<>();

		/******* Setup ****************************/
		activeGigs = setupActiveGigs();

		/*******
		 * Verify a biz type quick filter is displayed
		 ****************************/
		GigsPage gigsPage = gotoGigsPage();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		assertTrue(gigsPage.isSortByQuickFilterBtnDisplayed(), "Sort by quick filter button is not displayed", "Sort by quick filter button is displayed");
		gigsPage.clickToSortByQuickFilter();
		assertTrue(gigsPage.isSortbyOptionsDisplayed(), "Sort by options is not displayed", "Sort by options is displayed");

		/*******
		 * Verify users are able to search gigs by closing soon
		 ****************************/
		gigsPage.selectSortBy(GigData.SortBy.SORT_BY_CLOSING_SOON);
		gigsPage.clickToDoneBtn();
		assertTrue(gigsPage.getTextDisplayInSortByQuickFilter().equalsIgnoreCase("sort by: closing soon"), "The sortby closing soon label is incorrect", "The sortby closing soon label is correct");

		gigListFromUi = gigsPage.getGigList();

		gigListFromAPI = (ArrayList<GigData>) activeGigs.stream().sorted((e1, e2) -> e1.getGigEndDate().compareTo(e2.getGigEndDate())).collect(Collectors.toList());

		assertTrue(compareGigDataListInOrder(gigListFromUi, gigListFromAPI), "SortBy: Closing Soon returns incorrect data", "SortBy: Closing Soon returns correct data");

		/*******
		 * Verify users are able to search gigs by Potential $$
		 ****************************/
		gigsPage.clickToSortByQuickFilter();
		gigsPage.selectSortBy(GigData.SortBy.SORT_BY_POTENTIAL_MONEY);
		gigsPage.clickToDoneBtn();
		assertTrue(gigsPage.getTextDisplayInSortByQuickFilter().equalsIgnoreCase("sort by: potential $$"), "The sortby potential$$ label is incorrect", "The sortby potential$$ label is correct");

		gigListFromUi = gigsPage.getGigList();
		gigListFromAPI = (ArrayList<GigData>) activeGigs.stream().sorted((e1, e2) -> Double.compare(e2.getGigPotentialPrize(), e1.getGigPotentialPrize())).collect(Collectors.toList());

		assertTrue(compareGigDataListInPotentialMoneyOrder(gigListFromUi, gigListFromAPI), "SortBy: potential $$ returns incorrect data", "SortBy: potential $$ returns correct data");

		/*******
		 * Verify users are able to search gigs by Near Me
		 ****************************/

		gigsPage.clickToSortByQuickFilter();
		gigsPage.selectSortBy(GigData.SortBy.SORT_BY_NEAR_ME);
		gigsPage.clickToDoneBtn();
		assertTrue(gigsPage.getTextDisplayInSortByQuickFilter().equalsIgnoreCase("sort by: near me"), "The sortby near me label is incorrect", "The sortby near me label is correct");

		gigListFromUi = gigsPage.getGigList();
		assertTrue(gigsPage.isGigListInDistanceOrder(gigListFromUi), "Sort by near me is not correct", "Sort by near me is correct");
	}

	@Test
	public void FRAN3462_VerifyGigsProfileScreenIsDisplayedCorrectly() throws Exception {

		int testingGigIndex = 0;

		/******* Setup ****************************/
		activeGigs = setupActiveGigs();

		// Sort by end date
		activeGigs = (ArrayList<GigData>) activeGigs.stream().sorted((e1, e2) -> e1.getGigEndDate().compareTo(e2.getGigEndDate())).collect(Collectors.toList());
		GigData gigItemDataFromAPI = new GigsAPI(user).getGigDetail(activeGigs.get(testingGigIndex).getGigId());

		/******* Actions ****************************/
		GigsPage gigsPage = gotoGigsPage();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		GigProfilePage gigProfilePage = gigsPage.clickOnGigByIndex(testingGigIndex);

		/********** Gig Profile Info *********************/
		// You're on Gig Profile
		assertTrue(gigProfilePage.isOnGigProfilePage(), "Gig Profile Screen is not displayed", "Gig Profile Screen is displayed");

		// Verify Gig's name
		assertTrue(gigProfilePage.getGigName().equals(gigItemDataFromAPI.getGigName()), "Gig's Name is incorrect", "Gig's Name is correct");

		// Verify Gig Business Name
		assertTrue(gigProfilePage.getGigBusinessInfo().contains(gigItemDataFromAPI.getGigBusiness().getDisplayName()), "Gig Business Name is incorrect", "Gig Business Name is correct");

		// Verify Gig Business Type
		assertTrue(gigProfilePage.getGigBusinessInfo().contains(gigItemDataFromAPI.getGigBusiness().getBusinessType()), "Gig's Biz Type is incorrect", "Gig's Biz Type is correct");

	}

	@Test
	public void FRAN_5578_VerifyGigRulesScreenIsDisplayedCorrectly() throws Exception {

		int testingGigIndex = 0;

		/******* Setup ****************************/
		activeGigs = setupActiveGigs();

		// Sort by end date
		activeGigs = (ArrayList<GigData>) activeGigs.stream().sorted((e1, e2) -> e1.getGigEndDate().compareTo(e2.getGigEndDate())).collect(Collectors.toList());
		GigData gigItemDataFromAPI = new GigsAPI(user).getGigDetail(activeGigs.get(testingGigIndex).getGigId());
		ArrayList<GigRuleData> gigRulesFromAPI = (ArrayList<GigRuleData>) gigItemDataFromAPI.getGigRules().stream().sorted((e1, e2) -> e1.getGigRuleDisplayOrder() - e2.getGigRuleDisplayOrder()).collect(Collectors.toList());
		ArrayList<GigPrizeData> gigPrizesFromAPI = (ArrayList<GigPrizeData>) gigItemDataFromAPI.getGigPrizes().stream().sorted((e1, e2) -> e1.getGigPrizeDisplayOrder() - e2.getGigPrizeDisplayOrder()).collect(Collectors.toList());

		/******* Actions ****************************/
		GigsPage gigsPage = gotoGigsPage();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");
		GigProfilePage gigProfilePage = gigsPage.clickOnGigByIndex(testingGigIndex);

		// You're on Gig Profile
		assertTrue(gigProfilePage.isOnGigProfilePage(), "Gig Profile Screen is not displayed", "Gig Profile Screen is displayed");

		/********** Gig Rules Info *********************/
		// Gig brief
		assertTrue(gigProfilePage.getGigBrief().equalsIgnoreCase(gigItemDataFromAPI.getGigDescription()), "Gig Brief is incorrect", "Gig Brief is correct");

		// Gig Rules
		ArrayList<String> gigRulesFromUI = gigProfilePage.getGigRules();

		Assert.assertEquals(gigRulesFromAPI.size(), gigRulesFromUI.size(), "Gig Rules count not match");

		if (gigRulesFromAPI.size() > 0) {
			for (int i = 0; i < gigRulesFromAPI.size(); i++) {
				assertTrue(gigRulesFromUI.get(i).contains(gigRulesFromAPI.get(i).getGigRuleDescription()), "Gig Rule Order " + (i + 1) + " not match", "Gig Rule Order " + (i + 1) + " match");
			}
		}

		/********** Gig Prizes Info *********************/
		ArrayList<String> gigPrizesFromUI = gigProfilePage.getGigPrizes();
		Assert.assertEquals(gigPrizesFromAPI.size(), gigPrizesFromUI.size(), "Gig Prizes count not match");
		
		if (gigPrizesFromAPI.size() > 0) {
			for (int i = 0; i < gigPrizesFromAPI.size(); i++) {
				assertTrue(gigPrizesFromUI.get(i).contains(String.valueOf(gigPrizesFromAPI.get(i).getGigPrizeAmount())), "Gig Prize Amount " + (i + 1) + " not match", "Gig Prize Amount " + (i + 1) + " match");
				assertTrue(gigPrizesFromUI.get(i).contains(gigPrizesFromAPI.get(i).getGigPrizeDescription()), "Gig Prize Description " + (i + 1) + " not match", "Gig Prize Description " + (i + 1) + " match");
				assertTrue(gigPrizesFromUI.get(i).contains(String.valueOf(gigPrizesFromAPI.get(i).getGigPrizeWinnerQty())), "Gig Prize Winner Qty " + (i + 1) + " not match", "Gig Prize Winner Qty " + (i + 1) + " match");
			}
		}

	}

	@Test
	public void FRAN_5579_VerifyUserGigScreenIsDisplayedWhenTapToTopRightMoneyIcon() throws Exception {

		/******* Actions ****************************/
		GigsPage gigsPage = gotoGigsPage();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");

		PaymentPage paymentPage = gigsPage.goToPaymentPage();
		assertTrue(paymentPage.isOnPaymentPage(), "Payment Page is not displayed", "Payment Page is displayed");
	}

	@Test
	public void FRAN_5656_VerifyBusinessProfileDisplayedWhenTapOnViewBusinessButton() throws Exception {

		int testingGigIndex = 0;

		/******* Setup ****************************/
		activeGigs = setupActiveGigs();

		// Sort by end date
		activeGigs = (ArrayList<GigData>) activeGigs.stream().sorted((e1, e2) -> e1.getGigEndDate().compareTo(e2.getGigEndDate())).collect(Collectors.toList());
		GigData gigItemDataFromAPI = new GigsAPI(user).getGigDetail(activeGigs.get(testingGigIndex).getGigId());

		/******* Actions ****************************/
		// On Gig page
		GigsPage gigsPage = gotoGigsPage();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");

		// On Gig Profile page
		GigProfilePage gigProfilePage = gigsPage.clickOnGigByIndex(testingGigIndex);
		assertTrue(gigProfilePage.isOnGigProfilePage(), "Gig Profile Screen is not displayed", "Gig Profile Screen is displayed");

		// On Business Profile
		BusinessProfilePage businessPage = gigProfilePage.clickOnViewBusinessButton();
		assertTrue(businessPage.isActive(), "Business Profile Screen is not displayed", "Business Profile Screen is displayed");

		assertTrue(businessPage.getBusinessDisplayName().equals(gigItemDataFromAPI.getGigBusiness().getDisplayName()), "Business Display Name is incorrect", "Business Display Name is correct");

	}

	@Test
	public void FRAN_5659_VerifyUserCanSaveOrUnsaveAGig() throws Exception {

		int testingGigIndex = 0;

		/******* Setup ****************************/
		activeGigs = setupActiveGigs();

		// Sort by end date
		activeGigs = (ArrayList<GigData>) activeGigs.stream().sorted((e1, e2) -> e1.getGigEndDate().compareTo(e2.getGigEndDate())).collect(Collectors.toList());
		GigData gigItemDataFromAPI = new GigsAPI(user).getGigDetail(activeGigs.get(testingGigIndex).getGigId());
		int gigId = gigItemDataFromAPI.getGigId();

		// Unsave the gig first
		Assert.assertTrue(new GigsAPI(user).unsaveGig(gigId), "Setup data failed -- Can't unsave the gig: " + gigId);

		/******* Actions ****************************/
		// On Gig page
		GigsPage gigsPage = gotoGigsPage();
		assertTrue(gigsPage.isActive(), "Gigs page is not displayed", "Gigs page is displayed");

		// On Gig Profile page
		GigProfilePage gigProfilePage = gigsPage.clickOnGigByIndex(testingGigIndex);
		assertTrue(gigProfilePage.isOnGigProfilePage(), "Gig Profile Screen is not displayed", "Gig Profile Screen is displayed");

		/******* Save the gig ****************************/
		gigProfilePage.clickOnSaveUnsaveGigButton();
		// Verify text on UI
		assertTrue(gigProfilePage.isUserSavedGig(), "Verify UI: User saves gig unsuccessfully", "Verify UI: User saves gig successfully");
		// Verify via API
		gigItemDataFromAPI = new GigsAPI(user).getGigDetail(gigId);
		assertTrue(gigItemDataFromAPI.getUserSavedGigStatus(), "Verify API: User saves gig unsuccessfully", "Verify API: User saves gig successfully");

		/******* Unsave the gig ****************************/
		gigProfilePage.clickOnSaveUnsaveGigButton();
		// Verify text on UI
		assertTrue(gigProfilePage.isUserUnsavedGig(), "Verify UI: User unsaves gig unsuccessfully", "Verify UI: User unsaves gig successfully");
		// Verify via API
		gigItemDataFromAPI = new GigsAPI(user).getGigDetail(gigId);
		assertTrue(!gigItemDataFromAPI.getUserSavedGigStatus(), "Verify API: User unsaves gig unsuccessfully", "Verify API: User unsaves gig successfully");
	}

}