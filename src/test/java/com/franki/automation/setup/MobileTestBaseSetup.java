package com.franki.automation.setup;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.franki.automation.appium.driver.AppiumBaseDriver;
import com.franki.automation.appium.driver.AppiumHandler;
import com.franki.automation.report.HtmlReporter;
import com.franki.automation.utility.FilePaths;


public class MobileTestBaseSetup {

	// Web driver
	public static AppiumBaseDriver driver;
	// hashmap contains device infor like: platform, deviceName, uuid,
	// browser...... etc
	public HashMap<String, String> deviceInfo;

	@BeforeSuite
	public void beforeSuite() throws Exception {
		/*********** Init Html reporter *************************/
		FilePaths.initReportFolder();
		HtmlReporter.setReporter(FilePaths.getReportFilePath());
		driver = new AppiumHandler().startDriver();
	}

	@BeforeClass
	public void beforeClass() throws Exception {
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

	@AfterSuite(alwaysRun = true)
	public void afterSuite() throws Exception {
		HtmlReporter.flush();
		if(driver != null) {
			driver.closeDriver();
		}
	}
}
