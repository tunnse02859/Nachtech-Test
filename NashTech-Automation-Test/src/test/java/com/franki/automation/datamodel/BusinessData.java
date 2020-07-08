package com.franki.automation.datamodel;

public class BusinessData {
	
	private int businessId;
	private int businessSourceId;
	private String corporateGroupId;
	private String groupCode;
	private String businessType;
	private int businessTypeId;
	private String displayName;
	private String fullName;
	private String googlePlaceId;
	private String address;
	private String phone;
	private String website;
	
	private int ratingCount;
	private int videoCount;
	private int followerCount;
	private int gigCount;
	
	private BusinessRatingsData ratings;
	private BusinessAttributionData attributions;

	// BusinessID
	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}
	
	public int getBusinessId() {
		return this.businessId;
	}
	
	// BusinessSourceID
	public void setBusinessSourceId(int businessSourceId) {
		this.businessSourceId = businessSourceId;
	}
	
	public int getBusinessSourceId() {
		return this.businessSourceId;
	}
	
	// CorporateGroupId
	public void setCorporateGroupId(String corporateGroupId) {
		this.corporateGroupId = corporateGroupId;
	}
	
	public String getCorporateGroupId() {
		return this.corporateGroupId;
	}
	
	// GroupCode
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	public String getGroupCode() {
		return this.groupCode;
	}
	
	// GooglePlaceId
	public void setGooglePlaceId(String googlePlaceId) {
		this.googlePlaceId = googlePlaceId;
	}
	
	public String getGooglePlaceId() {
		return this.googlePlaceId;
	}
	
	// Address
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	// Phone
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	// Website
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public String getWebsite() {
		return this.website;
	}
	
	// Display Name
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	// Full Name
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getFullName() {
		return this.fullName;
	}
	
	// Business Type Id
	public void setBusinessTypeId(int businessTypeId) {
		this.businessTypeId = businessTypeId;
	}
	
	public int getBusinessTypeId() {
		return this.businessTypeId;
	}
	
	// Business Type
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	public String getBusinessType() {
		return this.businessType;
	}
	
	// Rating Count
	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}
	
	public int getRatingCount() {
		return this.ratingCount;
	}
	
	// VideoCount
	public void setVideoCount(int videoCount) {
		this.videoCount = videoCount;
	}
	
	public int getVideoCount() {
		return this.videoCount;
	}
	
	// FollowerCount
	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}
	
	public int getFollowerCount() {
		return this.followerCount;
	}
	
	// GigCount
	public void setGigCount(int gigCount) {
		this.gigCount = gigCount;
	}
	
	public int getGigCount() {
		return this.gigCount;
	}
	
	// Ratings
	public void setRatings(BusinessRatingsData ratings) {
		this.ratings = ratings;
	}
	
	public BusinessRatingsData getRatings() {
		return this.ratings;
	}
	
	// Attributions
	public void setAttributions(BusinessAttributionData attributions) {
		this.attributions = attributions;
	}
	
	public BusinessAttributionData getAttributions() {
		return this.attributions;
	}
}
