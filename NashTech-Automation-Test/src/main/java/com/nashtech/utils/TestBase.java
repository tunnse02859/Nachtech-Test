package com.nashtech.utils;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.nashtech.utils.TestLogger.*;
import static org.testng.Assert.fail;

public class TestBase {
	public WebDriver driver;
	public WebDriverWait wait;
	public Properties prop = Utils.loadConfig("config.properties");;
	
	public TestBase() {}
	
	public TestBase(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Integer.parseInt(prop.getProperty("default_wait_timeout")));
	}

	public WebDriver createDriver(String browser) {
		WebDriver driver = null;
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "driver/gecko.exe");
			driver = new FirefoxDriver();
		}
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(prop.getProperty("default_implicitwait")),
				TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, Integer.parseInt(prop.getProperty("default_wait_timeout")));
		return driver;
	}

	public WebElement getElement(String locator) {
		WebElement element = null;
		String[] extract = locator.split("=", 2);
		String by = extract[0];
		String value = extract[1];
		try {
			if (by.equalsIgnoreCase("id")) {
				element = driver.findElement(By.id(value));
			} else if (by.equalsIgnoreCase("xpath")) {
				element = driver.findElement(By.xpath(value));
			}
		} catch (NoSuchElementException e) {
			error("Timeout occurred! Cannot get element locator by " + locator);
			fail("Timeout occurred! Cannot get element locator by " + locator);
		}
		return element;
	}

	public void click(String locator) {
		try {
			WebElement element = getElement(locator);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			error("Timeout occurred! Cannot wait until element to be clickabe locator by " + locator);
			fail("Timeout occurred! Cannot wait until element to be clickabe locator by " + locator);
		}
	}
	
	public void sendKey(String locator,String value) {
		try {
			WebElement element = getElement(locator);
			element.sendKeys(value);
		} catch (Exception e) {
			error("Cannot send key to element locator by " + locator);
			fail("Cannot send key to element locator by " + locator);
		}
	}
	
	public void select(String locator,String by, String value) {
		try {
			WebElement element = getElement(locator);
			Select s = new Select(element);
			if(by.equalsIgnoreCase("value")) {
				s.selectByValue(value);
			}else if(by.equalsIgnoreCase("visiableText")) {
				s.selectByVisibleText(value);
			}else if(by.equalsIgnoreCase("index")) {
				s.selectByIndex(Integer.parseInt(value));
			}
		} catch (Exception e) {
			error("Cannot select element locator by " + locator + " with " + by + " = " + value);
			fail("Cannot select element locator by " + locator + " with " + by + " = " + value);
		}
	}

}
