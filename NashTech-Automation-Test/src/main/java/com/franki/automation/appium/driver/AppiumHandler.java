package com.franki.automation.appium.driver;

import com.franki.automation.utility.PropertiesLoader;

import io.appium.java_client.remote.MobilePlatform;

public class AppiumHandler {

	public AppiumBaseDriver startDriver() throws Exception {

		AppiumBaseDriver driver;

		String platform = PropertiesLoader.getPropertiesLoader().appium_configuration.getProperty("appium.platform");
		String awsPlatform = System.getenv("DEVICEFARM_DEVICE_PLATFORM_NAME");
		if (awsPlatform != null) {
			if (awsPlatform.equalsIgnoreCase(MobilePlatform.ANDROID)) {
				AppiumAndroidDriver android = new AppiumAndroidDriver();
				android.createAWSDriver();
				driver = android;
			} else if (awsPlatform.equalsIgnoreCase(MobilePlatform.IOS)) {
				AppiumIOsDriver ios = new AppiumIOsDriver();
				ios.createAWSDriver();
				driver = ios;
			} else {
				throw new Exception(String.format("The platform [%s] is not supported", awsPlatform));
			}
		} else {
			if (platform.equalsIgnoreCase(MobilePlatform.ANDROID)) {
				AppiumAndroidDriver android = new AppiumAndroidDriver();
				android.createDriver();
				driver = android;
			} else if (platform.equalsIgnoreCase(MobilePlatform.IOS)) {
				AppiumIOsDriver ios = new AppiumIOsDriver();
				ios.createDriver();
				driver = ios;
			} else {
				throw new Exception(String.format("The platform [%s] is not supported", platform));
			}
		}
		driver.setDefaultImplicitWaitTime();
		return driver;
	}

	public AppiumBaseDriver startDriver(String deviceName, String platformName, String platformVersion)
			throws Exception {

		AppiumBaseDriver driver = null;
		String sauceLabPlatform = PropertiesLoader.getPropertiesLoader().appium_configuration
				.getProperty("appium.saucelab.enable");
		String localPlatform = PropertiesLoader.getPropertiesLoader().appium_configuration.getProperty("appium.platform");
		if (sauceLabPlatform != null) {
			if (platformName.equalsIgnoreCase(MobilePlatform.ANDROID)) {
				AppiumAndroidDriver android = new AppiumAndroidDriver();
				android.createSauceLabDriver(deviceName, platformName, platformVersion);
				driver = android;
			} else if (platformName.equalsIgnoreCase(MobilePlatform.IOS)) {
				AppiumIOsDriver ios = new AppiumIOsDriver();
				ios.createDriver();
				driver = ios;
			} else {
				throw new Exception(String.format("The platform [%s] is not supported", platformName));
			}
		}else {
			if (localPlatform.equalsIgnoreCase(MobilePlatform.ANDROID)) {
				AppiumAndroidDriver android = new AppiumAndroidDriver();
				android.createDriver();
				driver = android;
			} else if (localPlatform.equalsIgnoreCase(MobilePlatform.IOS)) {
				AppiumIOsDriver ios = new AppiumIOsDriver();
				ios.createDriver();
				driver = ios;
			} else {
				throw new Exception(String.format("The platform [%s] is not supported", localPlatform));
			}
		}
		driver.setDefaultImplicitWaitTime();
		return driver;
	}
}
