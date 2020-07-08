package com.franki.automation.setup;

public class Constant {

	public interface SaveGigStatus {
		public static final String SAVE = "Save";
		public static final String UNSAVE = "Unsave";
	}

	public interface FollowStatus {
		public static final String FOLLOW = "Follow";
		public static final String UNFOLLOW = "Unfollow";
	}

	public interface BizTypes {
		public static final String BIZ_RESTAURANTS = "restaurants";
		public static final int BIZ_RESTAURANTS_ID = 9;

		public static final String BIZ_BARS = "bars";
		public static final int BIZ_BARS_ID = 2;

		public static final String BIZ_NIGHTCLUBS = "nightclubs";
		public static final int BIZ_NIGHTCLUBS_ID = 8;

		public static final String BIZ_TAKEAWAY = "takeaway/delivery";
		public static final int BIZ_TAKEAWAY_ID = 12;

		public static final String BIZ_CAFE = "cafe";
		public static final int BIZ_CAFE_ID = 11;

		public static final int BIZ_OTHER_ID = 0;
	}

	public interface TestingBankAccount {
		public static final String BANK_USERNAME = "user_good";
		public static final String BANK_PASSWORD = "pass_good";
	}

	public interface StateIds {
		public static final int NATION_WIDE_ID = 1;

		public static final String COLORADO_STATE_NAME = "Colorado";
		public static final int COLORADO_STATE_ID = 8;

		public static final String ALASKA_STATE_NAME = "Alaska";
		public static final int ALASKA_STATE_ID = 3;
	}

	public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	
	// When we access a google business, the attributes and ratings are set to N/A value
	public static final String NOT_AVAILABLE = "N/A";

	/*********** Editable *************************/
	public interface Location {
		public static final double LATITUDE = 21.00576431880715;
		public static final double LONGITUDE = 105.7277537623464;
	}

	public interface BusinessIds {
		public static final int COLORADO_TESTING_BUSINESS_ID = 38;
		public static final int LOCAL_BUSINESS_ID = 33588;
		public static final int GOOGLE_BUSINESS_ID = 33530;
	}

	public static final String ACCOUNT_EMAIL_1 = "autotest06@gmail.com";
	public static final String ACCOUNT_EMAIL_2 = "autotest05@gmail.com";
	public static final String ACCOUNT_PASSWORD_1 = "Franki123";
	public static final String ACCOUNT_PASSWORD_2 = "Franki123";

	public static final String ACCOUNT_ADMIN = "admin";
	public static final String ACCOUNT_PASSWORD_ADMIN = "%23r@nk!@pP";

	// API host
	public static final String API_HOST = "https://develop.frankiapp.com";

	// Facebook account
	public static final String LOGIN_FACEBOOK_USERNAME = "tai.le@adaptis.io";
	public static final String LOGIN_FACEBOOK_PASSWORD = "Tailt01319";

	// Normal login account
	public static final String NORMAL_LOGIN_USERNAME = "tai.le@adaptis.io";
	public static final String NORMAL_LOGIN_PASSWORD = "Franki123";

}