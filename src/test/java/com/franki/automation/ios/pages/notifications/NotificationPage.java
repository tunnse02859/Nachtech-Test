package com.franki.automation.ios.pages.notifications;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.datamodel.NotificationData;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class NotificationPage {

	public AppiumBaseDriver driver;

	@iOSXCUITFindBy(accessibility = "ic back black")
	private WebElement btnBack;

	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> listNotifications;

	private By lblMessage = MobileBy.name("messageLabel");
	private By lblRelativeDateTime = MobileBy.name("dateLabel");

	public NotificationPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(btnBack);
	}

	public ArrayList<NotificationData> getNotificationList() {
		ArrayList<NotificationData> notifications = new ArrayList<NotificationData>();

		listNotifications.forEach(notification -> {
			NotificationData notiData = new NotificationData();
			notiData.setMessage(notification.findElement(lblMessage).getText());
			notiData.setRelativeTime(notification.findElement(lblRelativeDateTime).getText());
			notifications.add(notiData);
		});

		return notifications;
	}

	public NotificationData getNotification(int index) {

		NotificationData notification = new NotificationData();
		notification.setMessage(listNotifications.get(index).findElement(lblMessage).getText());
		notification.setRelativeTime(listNotifications.get(index).findElement(lblRelativeDateTime).getText());

		return notification;
	}
}