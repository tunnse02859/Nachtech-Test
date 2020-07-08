package com.franki.automation.ios.pages.business;

import java.util.List;

import org.openqa.selenium.WebElement;
import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.setup.Constant;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BusinessAttributesPage extends BusinessProfilePage{

	public BusinessAttributesPage(AppiumBaseDriver driver) {
		super(driver);
	}

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'priceButton'")
	private List<WebElement> iconPrices;

	@iOSXCUITFindBy(xpath = "//*[@value='Friends']/..//*[@name='progressLabel']")
	private WebElement lblFriendsPercent;

	@iOSXCUITFindBy(xpath = "//*[@value='Family']/..//*[@name='progressLabel']")
	private WebElement lblFamilyPercent;
	
	@iOSXCUITFindBy(xpath = "//*[@value='Date']/..//*[@name='progressLabel']")
	private WebElement lblDatePercent;
	
	// Ambience
	
	@iOSXCUITFindBy(xpath = "//*[@value='Noisy']/..//*[@name='leadingLabel']")
	private WebElement lblNoisyPercent;
	
	@iOSXCUITFindBy(xpath = "//*[@value='Casual']/..//*[@name='leadingLabel']")
	private WebElement lblCasualPercent;
	
	@iOSXCUITFindBy(xpath = "//*[@value='Quiet']/..//*[@name='leadingLabel']")
	private WebElement lblQuietPercent;
	
	@iOSXCUITFindBy(xpath = "//*[@value='Classy']/..//*[@name='leadingLabel']")
	private WebElement lblClassyPercent;
	
	@iOSXCUITFindBy(xpath = "//*[@value='Trendy']/..//*[@name='leadingLabel']")
	private WebElement lblTrendyPercent;
	
	@iOSXCUITFindBy(xpath = "//*[@value='Touristy']/..//*[@name='leadingLabel']")
	private WebElement lblTouristyPercent;
	
	@iOSXCUITFindBy(xpath = "//*[@value='Intimate']/..//*[@name='leadingLabel']")
	private WebElement lblIntimatePercent;
	
	@iOSXCUITFindBy(xpath = "//*[@value='Dark']/..//*[@name='leadingLabel']")
	private WebElement lblDarkPercent;
	
	@iOSXCUITFindBy(xpath = "//*[@value='Well-lit']/..//*[@name='leadingLabel']")
	private WebElement lblWelllitPercent;
	
	@iOSXCUITFindBy(xpath = "//*[@value='Friendly']/..//*[@name='leadingLabel']")
	private WebElement lblFriendlyPercent;
	
	public Boolean isOnBusinessAttributesPage() {
		return Boolean.valueOf(driver.getAttribute(tabAttributes, "selected"));
	}
	
	public int getPriceVote() {
		return iconPrices.size();
	}

	public String getOccasionFriendsPercentage() throws Exception {
		String friendPercent = driver.getText(lblFriendsPercent);
		return friendPercent.equals(Constant.NOT_AVAILABLE) ? "" : friendPercent;
	}

	public String getOccasionFamilyPercentage() throws Exception {
		String familyPercent = driver.getText(lblFamilyPercent);
		return familyPercent.equals(Constant.NOT_AVAILABLE) ? "" : familyPercent;
	}
	
	public String getOccasionDatePercentage() throws Exception {
		String datePercent = driver.getText(lblDatePercent);
		return datePercent.equals(Constant.NOT_AVAILABLE) ? "" : datePercent;
	}
	
	public String getAmbienceNoisyPercentage() throws Exception {
		return driver.getText(lblNoisyPercent);
	}
	
	public String getAmbienceCausalPercentage() throws Exception {
		return driver.getText(lblCasualPercent);
	}
	
	public String getAmbienceQuietPercentage() throws Exception {
		return driver.getText(lblQuietPercent);
	}
	
	public String getAmbienceClassyPercentage() throws Exception {
		return driver.getText(lblClassyPercent);
	}
	
	public String getAmbienceTrendyPercentage() throws Exception {
		return driver.getText(lblTrendyPercent);
	}
	
	public String getAmbienceTouristyPercentage() throws Exception {
		return driver.getText(lblTouristyPercent);
	}
	
	public String getAmbienceIntimatePercentage() throws Exception {
		return driver.getText(lblIntimatePercent);
	}
	
	public String getAmbienceDarkPercentage() throws Exception {
		return driver.getText(lblDarkPercent);
	}
	
	public String getAmbienceWellLitPercentage() throws Exception {
		return driver.getText(lblWelllitPercent);
	}
	
	public String getAmbienceFriendlyPercentage() throws Exception {
		return driver.getText(lblFriendlyPercent);
	}
}