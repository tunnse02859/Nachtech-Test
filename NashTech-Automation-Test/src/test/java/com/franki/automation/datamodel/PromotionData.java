package com.franki.automation.datamodel;

public class PromotionData {
	
	private int promotionId;
	private String promotionTitle;


	// Promotion Title
	public void setPromotionTitle(String promotionTitle) {
		this.promotionTitle = promotionTitle;
	}
	
	public String getPromotionTitle() {
		return this.promotionTitle;
	}
	
	
	// Promotion ID
	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}
	
	public int getPromotionId() {
		return this.promotionId;
	}


}
