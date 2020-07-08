package com.franki.automation.datamodel;

public class BusinessRatingsData {
	
	public static final String FOOD_RATING_TYPE = "Food";
	public static final String DRINKS_RATING_TYPE = "Drinks";
	public static final String SERVICE_RATING_TYPE = "Service";
	public static final String OVERALL_RATING_TYPE = "Overall";
	
	private String foodPercentage;
	private String drinksPercentage;
	private String servicePercentage;
	private String overallPercentage;

	
	// Food Rating
	public void setFoodRating(String foodPercentage) {
		this.foodPercentage = foodPercentage;
	}
	
	public String getFoodRating() {
		return this.foodPercentage;
	}
	
	// Drinks Rating
	public void setDrinksRating(String drinksPercentage) {
		this.drinksPercentage = drinksPercentage;
	}
	
	public String getDrinksRating() {
		return this.drinksPercentage;
	}
	
	// Service Rating
	public void setServiceRating(String servicePercentage) {
		this.servicePercentage = servicePercentage;
	}
	
	public String getServiceRating() {
		return this.servicePercentage;
	}
	
	// Overall Rating
	public void setOverallRating(String overallPercentage) {
		this.overallPercentage = overallPercentage;
	}
	
	public String getOverallRating() {
		return this.overallPercentage;
	}
	
}
