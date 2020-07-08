package com.franki.automation.android.pages.home;

import static com.franki.automation.utility.Assertion.assertTrue;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.android.pages.explore.ExplorePage;
import com.franki.automation.android.pages.gigs.GigsPage;
import com.franki.automation.android.pages.profile.CurrentUserProfilePage;
import com.franki.automation.android.pages.review.ReviewDetailPage;
import com.franki.automation.android.pages.search.HomeSearchPage;
import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class HomePage {
	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
	private WebElement btnAllowAccessLocation;

	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_deny_button")
	private WebElement btnDenyAccessLocation;

	@AndroidFindBy(id = "btn_profile_action")
	private WebElement btnProfileTab;

	@AndroidFindBy(id = "cl_cell_layout")
	private WebElement contentLayout;

	@AndroidFindBy(id = "post_like_img")
	private WebElement btnLikePost;

	@AndroidFindBy(id = "post_like_text")
	private WebElement likesNumber;

	@AndroidFindBy(id = "post_comment_text")
	private WebElement commentsNumber;

	@AndroidFindBy(id = "post_more_img")
	private WebElement moreIcon;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Save Video\")")
	private WebElement saveVideo;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Added to Saved\")")
	private WebElement savedPopup;

	@AndroidFindBy(id = "btn_favorite")
	private WebElement btnFavorite;

	@AndroidFindBy(id = "search_action")
	private WebElement btnSearch;

	@AndroidFindBy(id = "title")
	private WebElement txtOptionMenu;

	@AndroidFindBy(id = "tvBusinessName")
	private WebElement txtBusinessName;

	@AndroidFindBy(id = "vgTabExplore")
	private WebElement btnExploreTab;

	@AndroidFindBy(id = "vgTabHome")
	private WebElement btnHomeTab;

	@AndroidFindBy(id = "vgTabGigs")
	private WebElement btnGigsTab;

	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup/android.view.ViewGroup")
	private WebElement firstReviewInNearYou;

	public HomePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public void clickToHomeTab() throws Exception {
		driver.click(btnHomeTab);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(btnProfileTab);
	}

	public void allowAccessLocation() throws Exception {
		if (driver.isElementDisplayed(btnAllowAccessLocation, 10)) {
			driver.click(btnAllowAccessLocation);
		}
	}

	public void denyAccessLocation() throws Exception {
		if (driver.isElementDisplayed(btnDenyAccessLocation, 10)) {
			driver.click(btnDenyAccessLocation);
		}
	}

	public CurrentUserProfilePage clickToProfileTab() throws Exception {
		driver.click(btnProfileTab);
		return new CurrentUserProfilePage(driver);
	}

	public void verifyPostIsDisplayed() throws Exception {
		boolean contentDisplayed = driver.isElementDisplayed(contentLayout);
		assertTrue(contentDisplayed, "HomePage is not displayed", "HomePage is displayed");
	}

	public int getAPostLikeNumber() throws Exception {
		String text = driver.getText(likesNumber);
		int end_number = text.indexOf("LIKES");
		return Integer.parseInt(text.substring(0, end_number - 1));
	}

	public void likeAPost() throws Exception {
		driver.click(btnLikePost);
	}

	public void clickMoreIconAPost() throws Exception {
		driver.click(moreIcon);
	}

	public void clickFavoriteAPost() throws Exception {
		driver.click(btnFavorite);
	}

	public void verifySavedPopupDisplay() throws Exception {
		driver.isElementDisplayed(savedPopup);
	}

	public HomeSearchPage clickToSearchIcon() throws Exception {
		driver.click(btnSearch);
		return new HomeSearchPage(driver);
	}

	public String getTextInMenu() throws Exception {
		return driver.getText(txtOptionMenu);
	}

	public String getBusinessNameOfPost() throws Exception {
		return driver.getText(txtBusinessName);
	}

	public void clickToMenuItem(String item) {
		List<WebElement> listMenu = driver.getListElement(By.id("content"));
		listMenu.stream().filter(e -> e.findElement(By.id("title")).getText().contains(item)).findAny().orElse(null);
	}

	public void verifyUserLikedAPost(int beforeClick, int afterClick) throws Exception {
		if (afterClick - beforeClick == 1) {
			assertTrue(true, "User is not liked a post", "User like a post successfully");
		}
	}

	public void swipeDownToBottomPage() throws InterruptedException {
		driver.swipingVertical();
	}

	public ExplorePage clickToExploreTab() throws Exception {
		driver.click(btnExploreTab);
		return new ExplorePage(driver);
	}

	public GigsPage clickToGigsTab() throws Exception {
		driver.click(btnGigsTab);
		return new GigsPage(driver);
	}

	public ReviewDetailPage selectReviewNearYouByIndex(int index) throws Exception {
			for (int i = 0; i < index - 1; i++) {
				// get item localtion
				int leftX = firstReviewInNearYou.getLocation().getX();
				int rightX = leftX + firstReviewInNearYou.getSize().getWidth();
				int upperY = firstReviewInNearYou.getLocation().getY();
				int lowerY = upperY + firstReviewInNearYou.getSize().getHeight();
				int middleY = (upperY + lowerY) / 2;

				// do swipe left n times to get item index
				new TouchAction<>(driver.getDriver()).press(PointOption.point(rightX, middleY))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
						.moveTo(PointOption.point(0, middleY)).release().perform();
				driver.sleep(1);
			}
			driver.click(firstReviewInNearYou);
			return new ReviewDetailPage(driver);
		
	}
}