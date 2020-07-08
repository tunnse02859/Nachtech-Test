package com.franki.automation.setup;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.franki.automation.excelhelper.ExcelHelper;
import com.franki.automation.report.Log;

public class MobileTestSetup extends MobileTestBaseSetup {

	public static String dataFilePath;
	public static String sheetName;

	public Object[][] getTestProvider(String filepPath, String sheetName) throws Exception {
		// return the data from excel file
		Object[][] data = ExcelHelper.getTableArray(filepPath, sheetName);
		return data;
	}

	@BeforeSuite
	public void beforeSuite() throws Exception {
		super.beforeSuite();
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
