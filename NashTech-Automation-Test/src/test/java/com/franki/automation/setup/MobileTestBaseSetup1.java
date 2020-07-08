package com.franki.automation.setup;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.appium.driver.AppiumHandler;
import com.franki.automation.report.HtmlReporter;
import com.franki.automation.utility.FilePaths;


public class MobileTestBaseSetup1 {

	// Web driver
	public AppiumBaseDriver driver;
	public static HashMap<String, AppiumBaseDriver> drivers;
	// hashmap contains device infor like: platform, deviceName, uuid,
	// browser...... etc
	public HashMap<String, String> deviceInfo;

	@BeforeSuite
	public void beforeSuie() throws IOException {
		FilePaths.initReportFolder2();
		drivers = new HashMap<String, AppiumBaseDriver>();
	}
	
	@BeforeTest
	public void beforeTest(String deviceName, String platformName, String platformVersion) throws Exception {
		System.out.println("beforeTest");
		/*********** Init Html reporter *************************/
		FilePaths.initReportFilePath(deviceName,platformName,platformVersion);
		HtmlReporter.setReporter(FilePaths.getReportFilePath());
		AppiumBaseDriver driver = new AppiumHandler().startDriver(deviceName,platformName,platformVersion);
		drivers.put(Thread.currentThread().toString(), driver);
	}

	@BeforeClass
	public void beforeClass() throws Exception {
		driver = drivers.get(Thread.currentThread().toString());
		HtmlReporter.createTest(this.getClass().getSimpleName(), "");
		HtmlReporter.currentTest = this.getClass().getSimpleName();
	}

	@BeforeMethod
	public void beforeMethod(Method method,Object[] listParameter) throws Exception {
		String methodName = listParameter.length != 0 ? method.getName() + " - " + ArrayUtils.toString(listParameter) : method.getName();
		HtmlReporter.createNode(this.getClass().getSimpleName(), methodName,"");
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) throws Exception {
		String mess = "";
		try {
			switch (result.getStatus()) {
				case ITestResult.SUCCESS:
					mess = String.format("The test [%s] is PASSED", result.getName());
					HtmlReporter.pass(mess);
					break;	
				case ITestResult.SKIP:
					mess = String.format("The test [%s] is SKIPPED", result.getName());
					HtmlReporter.skip(mess, result.getThrowable());
					break;
				
				case ITestResult.FAILURE:
					mess = String.format("The test [%s] is FAILED", result.getName());
					HtmlReporter.fail(mess, result.getThrowable(), driver.takeScreenshot());;
					break;		
				default:
					break;
			}
		} catch (Exception e) {
		}
		finally {
			driver.resetApp();
		}
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception {
	}
	
	@AfterTest
	public void afterTest() throws Exception {
		HtmlReporter.flush();
		System.out.println("afterTest");
		if(driver != null) {
			driver.closeDriver();
		}
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() throws Exception {
	}
}
