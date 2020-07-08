package com.franki.automation.datamodel;

public class BusinessAttributionData {
	
	public interface Occasion {
		public static final String OCCASION_FRIENDS = "OCCASION_FRIENDS";
		public static final String OCCASION_FAMILY = "OCCASION_FAMILY";
		public static final String OCCASION_DATE = "OCCASION_DATE";
	}
	
	public interface Price {
		public static final String PRICE_1 = "PRICE_1";
		public static final String PRICE_2 = "PRICE_2";
		public static final String PRICE_3 = "PRICE_3";
		public static final String PRICE_4 = "PRICE_4";
	}
	
	public interface Parking {
		public static final String PARKING_NO = "PARKING_NO";
		public static final String PARKING_UNKNOWN = "PARKING_UNKNOWN";
		public static final String PARKING_YES = "PARKING_YES";
	}
	
	public interface Kidfriendly {
		public static final String KIDFRIENDLY_NO = "KIDFRIENDLY_NO";
		public static final String KIDFRIENDLY_UNKNOWN = "KIDFRIENDLY_UNKNOWN";
		public static final String KIDFRIENDLY_YES = "KIDFRIENDLY_YES";
	}
	
	public interface Wheelchair {
		public static final String WHEELCHAIR_NO = "WHEELCHAIR_NO";
		public static final String WHEELCHAIR_UNKNOWN = "WHEELCHAIR_UNKNOWN";
		public static final String WHEELCHAIR_YES = "WHEELCHAIR_YES";
	}
	
	public interface Ambience {
		public static final String AMBIENCE_NOISY = "AMBIENCE_NOISY";
		public static final String AMBIENCE_CASUAL = "AMBIENCE_CASUAL";
		public static final String AMBIENCE_QUIET = "AMBIENCE_QUIET";
		public static final String AMBIENCE_CLASSY = "AMBIENCE_CLASSY";
		public static final String AMBIENCE_TRENDY = "AMBIENCE_TRENDY";
		public static final String AMBIENCE_TOURISTY = "AMBIENCE_TOURISTY";
		public static final String AMBIENCE_INTIMATE = "AMBIENCE_INTIMATE";
		public static final String AMBIENCE_DARK = "AMBIENCE_DARK";
		public static final String AMBIENCE_WELLLIT = "AMBIENCE_WELLLIT";
		public static final String AMBIENCE_FRIENDLY = "AMBIENCE_FRIENDLY";
	}
	
	// Occasion
	private String occasionFriendsPercentage;
	private String occasionFamilyPercentage;
	private String occasionDatePercentage;
	
	// Ambience
	private String ambienceNoisy;
	private String ambienceCasual;
	private String ambienceQuiet;
	private String ambienceClassy;
	private String ambienceTrendy;
	private String ambienceTouristy;
	private String ambienceIntimate;
	private String ambienceDark;
	private String ambienceWellLit;
	private String ambienceFriendly;

	/************** Occasion ***************************/
	// Occsion Friends
	public void setOccasionFriendsPercentage(String occasionFriendsPercentage) {
		this.occasionFriendsPercentage = occasionFriendsPercentage;
	}
	
	public String getOccasionFriendsPercentage() {
		return this.occasionFriendsPercentage;
	}
	
	// Occasion Family
	public void setOccasionFamilyPercentage(String occasionFamilyPercentage) {
		this.occasionFamilyPercentage = occasionFamilyPercentage;
	}
	
	public String getOccasionFamilyPercentage() {
		return this.occasionFamilyPercentage;
	}
	
	// Occasion Date
	public void setOccasionDatePercentage(String occasionDatePercentage) {
		this.occasionDatePercentage = occasionDatePercentage;
	}
	
	public String getOccasionDatePercentage() {
		return this.occasionDatePercentage;
	}
	
	/************** Ambience ***************************/
	// Noisy
	public void setAmbienceNoisy(String ambienceNoisy) {
		this.ambienceNoisy = ambienceNoisy;
	}
	
	public String getAmbienceNoisy() {
		return this.ambienceNoisy;
	}
	
	// Casual
	public void setAmbienceCasual(String ambienceCasual) {
		this.ambienceCasual = ambienceCasual;
	}
	
	public String getAmbienceCasual() {
		return this.ambienceCasual;
	}
	
	// Quiet
	public void setAmbienceQuiet(String ambienceQuiet) {
		this.ambienceQuiet = ambienceQuiet;
	}
	
	public String getAmbienceQuiet() {
		return this.ambienceQuiet;
	}
	
	// Classy
	public void setAmbienceClassy(String ambienceClassy) {
		this.ambienceClassy = ambienceClassy;
	}
	
	public String getAmbienceClassy() {
		return this.ambienceClassy;
	}
	
	// Trendy
	public void setAmbienceTrendy(String ambienceTrendy) {
		this.ambienceTrendy = ambienceTrendy;
	}
	
	public String getAmbienceTrendy() {
		return this.ambienceTrendy;
	}
	
	// Touristy
	public void setAmbienceTouristy(String ambienceTouristy) {
		this.ambienceTouristy = ambienceTouristy;
	}
	
	public String getAmbienceTouristy() {
		return this.ambienceTouristy;
	}
	
	// Intimate
	public void setAmbienceIntimate(String ambienceIntimate) {
		this.ambienceIntimate = ambienceIntimate;
	}
	
	public String getAmbienceIntimate() {
		return this.ambienceIntimate;
	}
	
	// Dark
	public void setAmbienceDark(String ambienceDark) {
		this.ambienceDark = ambienceDark;
	}
	
	public String getAmbienceDark() {
		return this.ambienceDark;
	}
	
	// Well-lit
	public void setAmbienceWelllit(String ambienceWellLit) {
		this.ambienceWellLit = ambienceWellLit;
	}
	
	public String getAmbienceWelllit() {
		return this.ambienceWellLit;
	}
	
	// Friendly
	public void setAmbienceFriendly(String ambienceFriendly) {
		this.ambienceFriendly = ambienceFriendly;
	}
	
	public String getAmbienceFriendly() {
		return this.ambienceFriendly;
	}
	
}
