package com.nashtech.test;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.nashtech.pageobject.UploadCompetePage;
import com.nashtech.pageobject.UploadPage;
import com.nashtech.utils.TestBase;

public class UploadTest extends TestBase{

	UploadPage uploadPage;
	UploadCompetePage uploadCompletePage;
	//WebDriver driver;

	@Parameters("browser")
	@BeforeMethod
	public void setup(String browser) {
		driver = createDriver(browser);
		driver.get("https://encodable.com/uploaddemo/");
		uploadPage = new UploadPage(driver);
		assertTrue(uploadPage.isDisplayed(),"Upload page not displayed after go to https://encodable.com/uploaddemo/");
	}
	
	@Test
	public void uploadTest() {
		String uploadFile = System.getProperty("user.dir") + "/datasource/Untitled.png";
		uploadPage.inputData(uploadFile, "/uploaddemo/files/", "TuNgocNguyen", "nashtech@gmail.com", "NASH TECH");
		uploadPage.clickUpload();
		uploadCompletePage = new UploadCompetePage(driver);
		assertTrue(uploadCompletePage.isDisplayed(),"Upload Complete page not displayed after click upload");
		uploadCompletePage.verifyInfo("nashtech@gmail.com", "Untitled.png");
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		//this.driver.quit();
	}
	
}
