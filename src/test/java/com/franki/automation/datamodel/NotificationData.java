package com.franki.automation.datamodel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.ocpsoft.prettytime.PrettyTime;

import com.franki.automation.report.Log;

public class NotificationData {
	
	private int notificationId;
	private int category;
	private int statusId;
	private boolean viewed;
	private String sentTime;
	private String relativeTime;
	private String message;
	private String contentKey;
	private int instigatingUserId;
	private String localizationKey;
	private String[] localizationValues;
	
	public final String SENT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	
	// notificationId
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	
	public int getNotificationId() {
		return this.notificationId;
	}
	
	// category
	public void setCategory(int category) {
		this.category = category;
	}
	
	public int getCategory() {
		return this.category;
	}
	
	// statusId
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	public int getStatusId() {
		return this.statusId;
	}
	
	// Viewed Status
	public void setViewedStatus(boolean viewed) {
		this.viewed = viewed;
	}
	
	public boolean getViewedStatus() {
		return this.viewed;
	}
	
	// Sent (time)
	public void setSentTime(String sentTime) {
		this.sentTime = sentTime;
	}
	
	public String getSentTime() {
		return this.sentTime;
	}
	
	// Relative time (display on UI)
	public void setRelativeTime(String relativeTime) {
		this.relativeTime = relativeTime;
	}
	
	public String getRelativeTime() throws ParseException {
		if(this.relativeTime == null) {
			PrettyTime p = new PrettyTime();
			DateFormat dateFormat = new SimpleDateFormat(SENT_DATE_TIME_FORMAT);
			this.relativeTime = p.format(dateFormat.parse(this.sentTime));
		}
		return this.relativeTime;
	}
	
	// Message
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	// ContentKey
	public void setContentKey(String contentKey) {
		this.contentKey = contentKey;
	}
	
	public String getContentKey() {
		return this.contentKey;
	}
	
	// instigatingUserId
	public void setInstigatingUserId(int instigatingUserId) {
		this.instigatingUserId = instigatingUserId;
	}
	
	public int getInstigatingUserId() {
		return this.instigatingUserId;
	}
	
	// localizationKey
	public void setLocalizationKey(String localizationKey) {
		this.localizationKey = localizationKey;
	}
	
	public String getLocalizationKey() {
		return this.localizationKey;
	}
	

}
