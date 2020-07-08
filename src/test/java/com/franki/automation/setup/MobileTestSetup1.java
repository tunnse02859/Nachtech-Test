package com.franki.automation.setup;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.franki.automation.excelhelper.ExcelHelper;
import com.franki.automation.report.Log;

public class MobileTestSetup1 extends MobileTestBaseSetup1 {

	public static String dataFilePath;
	public static String sheetName;

	public Object[][] getTestProvider(String filepPath, String sheetName) throws Exception {
		// return the data from excel file
		Object[][] data = ExcelHelper.getTableArray(filepPath, sheetName);
		return data;
	}
	
	@BeforeSuite
	public void beforeSuie() throws IOException {
		super.beforeSuie();
	}
	
	@Parameters({ "deviceName", "platformName", "platformVersion" })
	@BeforeTest
	public void beforeTest(String deviceName, String platformName, String platformVersion) throws Exception {
		super.beforeTest(deviceName,platformName,platformVersion);
	}

	@BeforeClass
	public void beforeClass() throws Exception {
		super.beforeClass();
		Log.startTestCase(this.getClass().getName());
	}

	@BeforeMethod
	public void beforeMethod(Method method,Object[] listParameter) throws Exception {
		String methodName = listParameter.length != 0 ? method.getName() + " - " + ArrayUtils.toString(listParameter) : method.getName();
		Log.info("+++++++++ Start testing: " + methodName + " ++++++++++++++");
		super.beforeMethod(method,listParameter);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) throws Exception {
		super.afterMethod(result);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception {
		Log.endTestCase(this.getClass().getName());
		super.afterClass();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() throws Exception {
		super.afterSuite();
	}
}
