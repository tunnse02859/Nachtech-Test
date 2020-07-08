package com.franki.automation.datamodel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class CarouselsData {
	
	private String businessDisplayName;
	private String businessAddress;
	private String contentKey;
	private int contentId; 
	private int likeCount;
	private int commentCount;
	private int gigId;
	private int overallRatingPercent;

	// Business DisplayName
	public void setBusinessDisplayName(String businessDisplayName) {
		this.businessDisplayName = businessDisplayName;
	}
	
	public String getGigPrizeId() {
		return this.businessDisplayName;
	}
	
	// Business Address
	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}
	
	public String getBusinessAddress() {
		return this.businessAddress;
	}
	
	// Content Key
	public void setContentKey(String contentKey) {
		this.contentKey = contentKey;
	}
	
	public String getContentKey() {
		return this.contentKey;
	}
	
	// Content ID
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	
	public int getContentId() {
		return this.contentId;
	}
	
	// Like Count
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	
	public int getLikeCount() {
		return this.likeCount;
	}
	
	// Comment Count
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
	public int getCommentCount() {
		return this.commentCount;
	}
	
	// Gig ID
	public void setGigId(int gigId) {
		this.gigId = gigId;
	}
	
	public int getGigId() {
		return this.gigId;
	}
	
	// OverAllRatingPercent
	public void setOverAllRatingPercent(int overallRatingPercent) {
		this.overallRatingPercent = overallRatingPercent;
	}
	
	public String getOverAllRatingPercent() {
		return String.valueOf(this.overallRatingPercent) + "%";
	}

}
