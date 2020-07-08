package com.franki.automation.datamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class GigData {

	public interface GigPaymentStatus {
		public static final int UNPAID = 1;
		public static final int FREE = 3;
		public static final int PAID = 2;
	}

	public interface GigStatus {
		public static final int DRAFT_GIG = 0;
		public static final int ACTIVE_GIG = 1;
		public static final int INREVIEW_GIG = 2;
		public static final int CLOSED_GIG = 3;
	}

	public interface SortBy {
		public static final String SORT_BY_CLOSING_SOON = "closing soon";
		public static final String SORT_BY_POTENTIAL_MONEY = "potential $$";
		public static final String SORT_BY_NEAR_ME = "near me";
	}

	public interface LocationsIndex {
		public static final int ALASKA = 3;
	}

	public interface Location {
		public static final double LATITUDE = 21.00576431880715;
		public static final double LONGITUDE = 105.7277537623464;
	}

	private int gigId;
	private boolean isSaved;
	private String gigName;
	private String gigDescription;
	private int gigCorporateGroupId;
	private Date gigStartDate;
	private Date gigEndDate;
	private double gigDistance;
	private int gigStatus; // Defined: 0 == Draft, 1 == (Active), 2 == (In Review), 3 == (Closed)
	private ArrayList<GigPrizeData> gigPrizes;
	private double potentialPrize;
	private ArrayList<GigRuleData> gigRules;

	private BusinessData business;

	// Gig ID
	public void setGigId(int gigId) {
		this.gigId = gigId;
	}

	public int getGigId() {
		return this.gigId;
	}

	// Is user saved gig?
	public void setUserSavedGigStatus(boolean isSaved) {
		this.isSaved = isSaved;
	}

	public boolean getUserSavedGigStatus() {
		return this.isSaved;
	}

	// Gig Name
	public void setGigName(String gigName) {
		this.gigName = gigName.trim();
	}

	public String getGigName() {
		return this.gigName;
	}

	// Gig Description
	public void setGigDescription(String gigDescription) {
		this.gigDescription = gigDescription.trim();
	}

	public String getGigDescription() {
		return this.gigDescription;
	}

	// Gig Start Date
	public void setGigStartDate(Date gigStartDate) {
		this.gigStartDate = gigStartDate;
	}

	public Date getGigStartDate() {
		return this.gigStartDate;
	}

	// Gig End Date
	public void setGigEndDate(Date gigEndDate) {
		this.gigEndDate = gigEndDate;
	}

	public Date getGigEndDate() {
		return this.gigEndDate;
	}

	// Gig Distance
	public void setGigDistance(double gigDistance) {
		this.gigDistance = gigDistance;
	}

	public double getGigDistance() {
		return this.gigDistance;
	}

	// Gig CorporateGroup id
	public void setGigCorporateGroupId(int gigCorporateGroupId) {
		this.gigCorporateGroupId = gigCorporateGroupId;
	}

	public int getGigCorporateGroupId() {
		return this.gigCorporateGroupId;
	}

	// Gig status
	public void setGigStatus(int gigStatus) {
		this.gigStatus = gigStatus;
	}

	public int getGigStatus() {
		return this.gigStatus;
	}

	public void setGigPrizes(ArrayList<GigPrizeData> gigPrizes) {
		this.gigPrizes = gigPrizes;

		this.gigPrizes.forEach(e -> {
			this.potentialPrize += e.getGigPrizeAmount();
		});
	}

	public ArrayList<GigPrizeData> getGigPrizes() {
		return this.gigPrizes;
	}

	// Gig Rules
	public void setGigRules(ArrayList<GigRuleData> gigRules) {
		this.gigRules = gigRules;
	}

	public ArrayList<GigRuleData> getGigRules() {
		return this.gigRules;
	}

	// Get Gig Max prize
	public double getGigPotentialPrize() {
		return this.potentialPrize;
	}

	// Gig's Business info
	public void setGigBusiness(BusinessData business) {
		this.business = business;
	}

	public BusinessData getGigBusiness() {
		return this.business;
	}

}
