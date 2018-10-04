package com.nashtech.pageobject;

import org.openqa.selenium.WebDriver;

import com.nashtech.utils.TestBase;
import com.nashtech.utils.Utils;

public class UploadPage extends TestBase{
	
	//elements
	public String upload;
	public String uploadTo;
	public String folder;
	public String email;
	public String name;
	public String submit;
	
	
	public UploadPage(WebDriver driver) {
		super(driver);
		prop = Utils.loadConfig("objects/UploadPage.txt");
		upload = prop.getProperty("upload");
		uploadTo = prop.getProperty("uploadTo");
		folder = prop.getProperty("folder");
		email = prop.getProperty("email");
		name = prop.getProperty("name");
		submit = prop.getProperty("submit");
	}
	
	public boolean isDisplayed() {
		return driver.getTitle().equals("Upload a file");
	}
	
	public void inputData(String file, String uploadToValue, String folderValue, String emailValue, String nameValue) {
		sendKey(upload,file);
		select(uploadTo,"visiableText",uploadToValue);
		sendKey(folder,folderValue);
		sendKey(email,emailValue);
		sendKey(name,nameValue);
	}
	
	public void clickUpload() {
		click(submit);
	}
}
