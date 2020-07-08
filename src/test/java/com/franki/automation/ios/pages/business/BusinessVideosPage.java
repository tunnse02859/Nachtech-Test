package com.franki.automation.ios.pages.business;

import java.util.List;

import org.openqa.selenium.WebElement;
import com.franki.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BusinessVideosPage extends BusinessProfilePage{


	public BusinessVideosPage(AppiumBaseDriver driver) {
		super(driver);
	}
	
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCollectionView/XCUIElementTypeCell")
	private List<WebElement> listVideos;
	
	public Boolean isOnBusinessVideosPage() {
		return Boolean.valueOf(driver.getAttribute(tabVideos, "selected"));
	}
	
	public Boolean videoListDisplayed() throws Exception {
		if(getVideosCount() > 0) {
			return listVideos.size() > 0;
		}
		
		return true;
	}

}