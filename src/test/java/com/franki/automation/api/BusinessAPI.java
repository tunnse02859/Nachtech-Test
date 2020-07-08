package com.franki.automation.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.franki.automation.datamodel.BusinessAttributionData;
import com.franki.automation.datamodel.BusinessData;
import com.franki.automation.datamodel.BusinessRatingsData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;
import com.franki.automation.utility.JsonParser;

public class BusinessAPI {

	private UserData user;
	private BusinessData business;

	public BusinessAPI(UserData user) {
		this.user = user;
		this.business = new BusinessData();
	}


	private String getAllCorporateGroupsPath = "/API/Group/GetAllCorporateGroups";
	private String getBusinessDetailsPath = "/Api/Businesses/%d/Details";
	private String followBusinessPath = "/Api/Businesses/%d/Follow";
	private String unfollowBusinessPath = "/Api/Businesses/%d/Unfollow";
	private String getBusinessRatingStatsPath = "/Api/Ratings/Business/%d/Stats";
	private String getBusinessAttributeStatsPath = "/Api/Attributions/Business/%d/Stats";
	private String getCorporateGroupUsersPath = "/Api/Group/GetCorporateGroupUsers";

	public APIRequest getAllCorporateGroups(int type) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(getAllCorporateGroupsPath).addParam("type", String.valueOf(type))
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).get();
		return request;
	}

	public APIRequest getBusinessDetailsRequest(int businessId) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(String.format(getBusinessDetailsPath, businessId))
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).get();
		return request;
	}

	public BusinessData getBusinessDetails(int businessId) throws Exception {
		APIResponse response = getBusinessDetailsRequest(businessId).getResponse();

		business.setBusinessId(businessId);
		business.setAddress(response.getValueFromResponse("Data.Address"));
		business.setPhone(response.getValueFromResponse("Data.Phone"));
		business.setWebsite(response.getValueFromResponse("Data.Website"));
		business.setDisplayName(response.getValueFromResponse("Data.DisplayName"));
		business.setBusinessSourceId(Integer.valueOf(response.getValueFromResponse("Data.BusinessSourceId")));
		business.setRatingCount(Integer.valueOf(response.getValueFromResponse("Data.RatingCount")));
		business.setVideoCount(Integer.valueOf(response.getValueFromResponse("Data.VideoCount")));
		business.setFollowerCount(Integer.valueOf(response.getValueFromResponse("Data.FollowerCount")));
		business.setGigCount(Integer.valueOf(response.getValueFromResponse("Data.GigCount")));

		return business;
	}

	public APIRequest followBusinessRequest(int businessId) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(String.format(followBusinessPath, businessId))
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post();
		return request;
	}

	public boolean isFollowingBusiness(int businessId) throws Exception {
		return getBusinessDetailsRequest(businessId).getResponse()
				.getValueFromResponse("Data.IsUserFollowingTheBusiness").equals("true");
	}

	public APIRequest unfollowBusinessRequest(int businessId) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(String.format(unfollowBusinessPath, businessId))
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post();
		return request;
	}
	
	public ArrayList<UserData> getAllBusinessFollowers(int businessId) throws JSONException, Exception {
		
		ArrayList<UserData> followers = new ArrayList<>();
		
		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(getCorporateGroupUsersPath)
		.addParam("corporateGroupId", String.valueOf(businessId))
		.addHeader("Content-Type", "application/json").oauth2(user.getToken()).get().getResponse();
		
		JSONArray data = JsonParser.convetToJsonArray(response.getValueFromResponse("Data"));
		
		if(! data.isEmpty()) {
			data.forEach(e -> {
				try {
					UserData user = new UserData();
					user.setDisplayName(new JsonParser((JSONObject)e).extractJsonValue("DisplayName"));
					user.setFullName(new JsonParser((JSONObject)e).extractJsonValue("Fullname"));
					user.setUserId(new JsonParser((JSONObject)e).extractJsonValue("UserId"));
					followers.add(user);
				} catch (Exception e1) {
				}
			});
		}
		
		return followers;
	}

	public BusinessData getBusinessRatingStats(int businessId) throws Exception {
		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST)
				.path(String.format(getBusinessRatingStatsPath, businessId)).addHeader("Content-Type", "application/json")
				.oauth2(user.getToken()).get().getResponse();
		
		getBusinessDetails(businessId);
		BusinessRatingsData rating = new BusinessRatingsData();
		
		JSONArray data = JsonParser.convetToJsonArray(response.getValueFromResponse("Data"));
		
		data.forEach(r -> {
			try {
				switch(JsonParser.extractJsonValue((JSONObject) r, "Name")) {
				
				case BusinessRatingsData.FOOD_RATING_TYPE:
					rating.setFoodRating(JsonParser.extractJsonValue((JSONObject) r, "Percentage"));
					break;
					
				case BusinessRatingsData.DRINKS_RATING_TYPE:
					rating.setDrinksRating(JsonParser.extractJsonValue((JSONObject) r, "Percentage"));
					break;
					
				case BusinessRatingsData.SERVICE_RATING_TYPE:
					rating.setServiceRating(JsonParser.extractJsonValue((JSONObject) r, "Percentage"));
					break;
					
				case BusinessRatingsData.OVERALL_RATING_TYPE:
					rating.setOverallRating(JsonParser.extractJsonValue((JSONObject) r, "Percentage"));
					break;
					
				default:
					break;
				}
			} catch (Exception e) {
			}
		});
		
		business.setRatings(rating);
		
		return business;
	}
	
	public BusinessData getBusinessAttributionStats(int businessId) throws Exception {
		
		JSONArray occasionData = new JSONArray();
		JSONArray ambienceData = new JSONArray();
		
		getBusinessDetails(businessId);
		BusinessAttributionData attributionData = new BusinessAttributionData();
		
		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST)
				.path(String.format(getBusinessAttributeStatsPath, businessId)).addHeader("Content-Type", "application/json")
				.oauth2(user.getToken()).get().getResponse();
		
		int totalReviewCount = Integer.valueOf(response.getValueFromResponse("Data.TotalReviewCount"));
		if(totalReviewCount == 0) {
			attributionData.setOccasionFriendsPercentage("0%");
			attributionData.setOccasionFamilyPercentage("0%");
			attributionData.setOccasionDatePercentage("0%");
			attributionData.setAmbienceNoisy("0%");
			attributionData.setAmbienceCasual("0%");
			attributionData.setAmbienceClassy("0%");
			attributionData.setAmbienceDark("0%");
			attributionData.setAmbienceFriendly("0%");
			attributionData.setAmbienceIntimate("0%");
			attributionData.setAmbienceQuiet("0%");
			attributionData.setAmbienceTouristy("0%");
			attributionData.setAmbienceTrendy("0%");
			attributionData.setAmbienceWelllit("0%");
			business.setAttributions(attributionData);
			return business; 
		}
		
		JSONArray attributions = JsonParser.convetToJsonArray(response.getValueFromResponse("Data.Attributions"));
		
		/********* Occasion *********************************/
		
		for(int i = 0; i < attributions.length(); i++) {
			if(((JSONObject) attributions.get(i)).get("IdCode").equals("OCCASION")) {
				occasionData = ((JSONObject) attributions.get(i)).getJSONArray("Answers");
				break;
			}
		}
		
		for(int i = 0 ; i < occasionData.length() ; i ++) {
			int votes = ((JSONObject) occasionData.get(i)).getInt("TotalNumberOfTimesSelected");
			String percent = String.format("%d", (votes * 100) / totalReviewCount ) + "%";
			
			switch(JsonParser.extractJsonValue((JSONObject) occasionData.get(i), "IdCode")) {
			
			case BusinessAttributionData.Occasion.OCCASION_FRIENDS:
				attributionData.setOccasionFriendsPercentage(percent);
				break;
				
			case BusinessAttributionData.Occasion.OCCASION_FAMILY:
				attributionData.setOccasionFamilyPercentage(percent);
				break;
				
			case BusinessAttributionData.Occasion.OCCASION_DATE:
				attributionData.setOccasionDatePercentage(percent);
				break;
				
			default:
				break;
			}
		}
		
		
		/********* Ambience *********************************/
		
		for(int i = 0; i < attributions.length(); i++) {
			if(((JSONObject) attributions.get(i)).get("IdCode").equals("AMBIENCE")) {
				ambienceData = ((JSONObject) attributions.get(i)).getJSONArray("Answers");
				break;
			}
		}
		
		for(int i = 0 ; i < ambienceData.length() ; i ++) {
			int votes = ((JSONObject) ambienceData.get(i)).getInt("TotalNumberOfTimesSelected");
			String percent = String.format("%d", (votes * 100) / totalReviewCount ) + "%";
			
			switch(JsonParser.extractJsonValue((JSONObject) ambienceData.get(i), "IdCode")) {
			
			case BusinessAttributionData.Ambience.AMBIENCE_NOISY:
				attributionData.setAmbienceNoisy(percent);
				break;
				
			case BusinessAttributionData.Ambience.AMBIENCE_CASUAL:
				attributionData.setAmbienceCasual(percent);
				break;
				
			case BusinessAttributionData.Ambience.AMBIENCE_QUIET:
				attributionData.setAmbienceQuiet(percent);
				break;
				
			case BusinessAttributionData.Ambience.AMBIENCE_CLASSY:
				attributionData.setAmbienceClassy(percent);
				break;
				
			case BusinessAttributionData.Ambience.AMBIENCE_TRENDY:
				attributionData.setAmbienceTrendy(percent);
				break;
				
			case BusinessAttributionData.Ambience.AMBIENCE_TOURISTY:
				attributionData.setAmbienceTouristy(percent);
				break;
				
			case BusinessAttributionData.Ambience.AMBIENCE_INTIMATE:
				attributionData.setAmbienceIntimate(percent);
				break;
				
			case BusinessAttributionData.Ambience.AMBIENCE_DARK:
				attributionData.setAmbienceDark(percent);
				break;
				
			case BusinessAttributionData.Ambience.AMBIENCE_WELLLIT:
				attributionData.setAmbienceWelllit(percent);
				break;
				
			case BusinessAttributionData.Ambience.AMBIENCE_FRIENDLY:
				attributionData.setAmbienceFriendly(percent);
				break;

			default:
				break;
			}
		}

		business.setAttributions(attributionData);
		
		return business;
	}
	
	// get a business group (type = 1)
	public String getCorporateGroupDisplayNameInSearchListByIndex(int index) throws Exception {
		return getAllCorporateGroups(1).getResponse().getValueFromResponse("Data[" + index + "].DisplayName");
	}

	// get a business group (type = 1)
	public String getCorporateGroupIDInSearchListByIndex(int index) throws Exception {
		return getAllCorporateGroups(1).getResponse().getValueFromResponse("Data[" + index + "].GroupCode");
	}

	// get product name in business group (type = 1)
	public String getProductNameInBusinessGroup(int indexOfBusinessGroup, int indexOfProduct) throws Exception {
		return getAllCorporateGroups(1).getResponse()
				.getValueFromResponse("Data[" + indexOfBusinessGroup + "].Products[" + indexOfProduct + "].Name");
	}

	// search and get the first valid place group (type = 2)
	public HashMap<String, String> getFirstValidPlaceGroup() throws Exception {
		HashMap<String, String> data = new HashMap<String, String>();
		JSONArray listPlaceGroup = getAllCorporateGroups(2).getResponse().getResponseBodyInJson().getJSONArray("Data");
		for (int i = 0; i < 2; i++) {
			JSONObject group = listPlaceGroup.getJSONObject(i);
			if (!group.getString("GroupCode").equalsIgnoreCase("null")
					&& !group.getString("DisplayName").equalsIgnoreCase("null")
					&& !group.getString("GooglePlaceId").equalsIgnoreCase("null")) {
				return data;
			}
		}
		return null;
	}

}
