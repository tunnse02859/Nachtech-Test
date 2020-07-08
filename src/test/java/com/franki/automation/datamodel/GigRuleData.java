package com.franki.automation.datamodel;

import org.apache.commons.lang3.text.WordUtils;

public class GigRuleData {
	
	private int gigRuleId;
	private String gigRuleDescription;
	private int gigRuleDisplayOrder;

	// Gig Rule ID
	public void setGigRuleId(int gigRuleId) {
		this.gigRuleId = gigRuleId;
	}
	
	public int getGigRuleId() {
		return this.gigRuleId;
	}
	
	// Gig Rule Description
	public void setGigRuleDescription(String gigRuleDescription) {
		this.gigRuleDescription = gigRuleDescription.trim();
	}
	
	public String getGigRuleDescription() {
		return WordUtils.capitalize(this.gigRuleDescription);
	}
	
	// Gig Rule Display Order
	public void setGigRuleDisplayOrder(int gigRuleDisplayOrder) {
		this.gigRuleDisplayOrder = gigRuleDisplayOrder;
	}
	
	public int getGigRuleDisplayOrder() {
		return this.gigRuleDisplayOrder;
	}

}
