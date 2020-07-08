package com.franki.automation.ios.pages.business;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BusinessFollowersPage extends BusinessProfilePage {

	public BusinessFollowersPage(AppiumBaseDriver driver) {
		super(driver);
	}

	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> listFollowers;

	By lblFollowerDisplayName = MobileBy.name("displayNameLabel");
	By btnFollow = MobileBy.name("followButton");

	public Boolean isOnBusinessFollowsersPage() {
		return Boolean.valueOf(driver.getAttribute(tabFollowers, "selected"));
	}

	private String getBusinessFollowerDisplayNameByRow(WebElement row) {
		try {
			String displayName = row.findElement(lblFollowerDisplayName).getText();
			return displayName.startsWith("@") ? displayName.substring(1) : displayName;
		} catch (Exception e) {
			return "";
		}
	}

	private WebElement getFollowerRowByDisplayName(String followerDisplayName) throws Exception {
		driver.scrollUntillViewText(followerDisplayName);
		return listFollowers.stream()
				.filter(e -> getBusinessFollowerDisplayNameByRow(e).contains(followerDisplayName)).findFirst()
				.get();
	}

	public ArrayList<UserData> getAllBusinessFollowers() {
		ArrayList<UserData> followers = new ArrayList<UserData>();
		listFollowers.forEach(e -> {
			UserData user = new UserData();
			user.setDisplayName(getBusinessFollowerDisplayNameByRow(e));
			followers.add(user);
		});
		return followers;
	}

	public void followUnfollowAnUserFromBusinessFollowersTab(String followerDisplayName) throws Exception {
		getFollowerRowByDisplayName(followerDisplayName).findElement(btnFollow).click();
	}

	public boolean isMainUserFollowingBusinessFollower(String followerDisplayName) throws Exception {
		return ! getFollowerRowByDisplayName(followerDisplayName).findElement(btnFollow)
				.findElement(MobileBy.className("XCUIElementTypeStaticText")).getText()
				.equals(Constant.FollowStatus.FOLLOW);
	}

}