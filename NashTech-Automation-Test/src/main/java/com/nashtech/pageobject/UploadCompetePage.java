package com.nashtech.pageobject;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;

import com.nashtech.utils.TestBase;
import com.nashtech.utils.Utils;

public class UploadCompetePage extends TestBase{
	
	//elements
	public String uploadSuccess;
	public String email;
	public String fileName;
	
	
	public UploadCompetePage(WebDriver driver) {
		super(driver);
		prop = Utils.loadConfig("objects/UploadSuccessPage.txt");
		uploadSuccess = prop.getProperty("uploadSuccess");
		email = prop.getProperty("email");
		fileName = prop.getProperty("fileName");
	}
	
	public boolean isDisplayed() {
		return getElement(uploadSuccess).isDisplayed();
	}
	
	public void verifyInfo(String inputEmail, String fileNameInput) {
		String displayedEmail = getElement(email).getText().split("Email Address: ")[1];
		String displayedFileName = getElement(fileName).getText();
		assertEquals(displayedEmail, inputEmail,"Email displayed incorrectly");
		assertEquals(displayedFileName, fileNameInput,"File name displayed incorrectly");
	}
	
}
