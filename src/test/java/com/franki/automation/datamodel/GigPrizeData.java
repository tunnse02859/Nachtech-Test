package com.franki.automation.datamodel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class GigPrizeData {
	
	private int gigPrizeId;
	private String gigPrizeDescription;
	private double gigPrizeAmount;
	private int gitPrizeWinnerQty; 
	private int gigPrizeDisplayOrder;

	// Gig Prize ID
	public void setGigPrizeId(int gigPrizeId) {
		this.gigPrizeId = gigPrizeId;
	}
	
	public int getGigPrizeId() {
		return this.gigPrizeId;
	}
	
	// Gig Prize Description
	public void setGigPrizeDescription(String gigPrizeDescription) {
		this.gigPrizeDescription = gigPrizeDescription.trim();
	}
	
	public String getGigPrizeDescription() {
		return WordUtils.capitalize(this.gigPrizeDescription);
	}
	
	// Gig Prize Amount
	public void setGigPrizeAmount(double gigPrizeAmount) {
		this.gigPrizeAmount = gigPrizeAmount;
	}
	
	public double getGigPrizeAmount() {
		return this.gigPrizeAmount;
	}
	
	// Gig Prize Winner Quantity
	public void setGigPrizeWinnerQty(int gitPrizeWinnerQty) {
		this.gitPrizeWinnerQty = gitPrizeWinnerQty;
	}
	
	public int getGigPrizeWinnerQty() {
		return this.gitPrizeWinnerQty;
	}
	
	// Gig Prize Display Order
	public void setGigPrizeDisplayOrder(int gigPrizeDisplayOrder) {
		this.gigPrizeDisplayOrder = gigPrizeDisplayOrder;
	}
	
	public int getGigPrizeDisplayOrder() {
		return this.gigPrizeDisplayOrder;
	}

}
