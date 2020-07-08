package com.franki.automation.ios.pages.recording;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.franki.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class RecordingPage {
	
	public AppiumBaseDriver driver;

	/***** On User's gallery screen *********/
	
	// Button recording
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCollectionView/XCUIElementTypeCell")
	List<WebElement> userVideoList;
	
	// Button Cancel Gallery
	@iOSXCUITFindBy(accessibility = "Cancel")
	WebElement btnCancelGallery;

	// Button Done Gallery
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Done'")
	WebElement btnDoneGallery;
	
	/***** On Recording screen *********/
	
	// Button recording
	@iOSXCUITFindBy(accessibility = "recordingButton")
	WebElement btnStartRecording;
	
	// Button browser Galerry
	@iOSXCUITFindBy(accessibility = "assetPickerButton")
	WebElement btnBorrowGallery;
	
	// Button browser Galerry
	@iOSXCUITFindBy(accessibility = "dismissButton")
	WebElement btnCloseRecording;
	
	// Button Camera's flash
	@iOSXCUITFindBy(accessibility = "torchToggleButton")
	WebElement btnEnableFlash;
	
	// Button Flip Camera
	@iOSXCUITFindBy(accessibility = "flipCameraButton")
	WebElement btnFlipCamera;
	
	// Button Mute/unmute
	@iOSXCUITFindBy(accessibility = "audioToggleButton")
	WebElement btnMuteAudio;
	
	// After recording, keep or delete recorded video
	@iOSXCUITFindBy(accessibility = "undoButton")
	WebElement btnDeleteRecordedVideo;
	
	// Confirm deleting
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Confirm\"]")
	WebElement btnConfirmDelete;
	
	// Confirm deleting
	@iOSXCUITFindBy(iOSNsPredicate = "name == 'Cancel'")
	WebElement btnCancelDelete;
	
	// After recording, preview the recorded video
	@iOSXCUITFindBy(accessibility = "completeButton")
	WebElement btnPreview;
	
	/***** On Preview screen *********/
	
	// The list of recorded videos
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCollectionView/XCUIElementTypeCell")
	List<WebElement> recordedVideoList;
	
	// Delete the recorded video when previewing, enable only when have more than 2 recored videos previewing
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'deleteButton'")
	WebElement btnDeleteVideoPreview;
	
	// Done previewing
	@iOSXCUITFindBy(accessibility = "exportButton")
	WebElement btnDonePreview;
	
	// Processing pupup
	@iOSXCUITFindBy(accessibility = "Processing")
	WebElement iconProcessingVideo;
	

	
	public RecordingPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(btnStartRecording);
	}
	
	/**
	 * Recording a video
	 * @param videoDuration
	 * @throws Exception
	 */
	public RecordingPage recordVideo(int videoDuration) throws Exception {
		// Turn on the front camera
		driver.click(btnFlipCamera);
		
		for(int i = 0; i < 3; i++) {
			// Start Recording
			driver.tap(btnStartRecording);
			// Wait for recording run
			driver.sleep(videoDuration);
			// Stop Recording
			driver.tap(btnStartRecording);
			
			if(driver.isElementDisplayed(btnPreview, 10)) {
				break;
			}
		}
		
		return this;
	}
	
	/**
	 * Select a video from gallery
	 * @return
	 * @throws Exception
	 */
	public RecordingPage selectSingleVideoFromGallery() throws Exception {
		driver.click(btnBorrowGallery);
		driver.click(userVideoList.get(0));
		driver.click(btnDoneGallery);
		driver.waitForElementDisplayed(btnPreview, 10);
		return this;
	}
	
	/**
	 * Delete the recorded video
	 * @throws Exception
	 */
	public RecordingPage deleteTheRecordedSelectedVideo() throws Exception {
		driver.tap(btnDeleteRecordedVideo);
		driver.tap(btnConfirmDelete);
		driver.waitForElementDisappear(btnConfirmDelete, 10);
		return this;
	}
	
	/**
	 * Check whether the button Preview displayed
	 * @return
	 */
	public boolean isPreviewButtonDisplayed() {
		return driver.isElementDisplayed(btnPreview, 10);
	}
	
	/**
	 * Preview the recording
	 * @return
	 * @throws Exception
	 */
	public RecordingPage preview() throws Exception {
		driver.tap(btnPreview);
		return this;
	}
	
	/**
	 * Done preview then move to the create content page
	 * @return
	 * @throws Exception
	 */
	public CreateOrEditReviewPage donePreview() throws Exception {
		driver.tap(btnDonePreview);
		driver.waitForElementDisplayed(iconProcessingVideo, 10);
		driver.waitForElementDisappear(iconProcessingVideo, 120);
		
		return new CreateOrEditReviewPage(driver);
	}
	
}
