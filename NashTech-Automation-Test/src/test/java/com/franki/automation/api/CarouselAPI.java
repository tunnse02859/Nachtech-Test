package com.franki.automation.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.franki.automation.datamodel.CarouselsData;
import com.franki.automation.datamodel.NotificationData;
import com.franki.automation.datamodel.PromotionData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;
import com.franki.automation.utility.JsonParser;

public class CarouselAPI {
	private UserData user;

	public CarouselAPI(UserData user) {
		this.user = user;
	}

	private String getCarouselsCategoriesPath = "/Api/Discover/Carousels/Categories";
	
	// Promotions 
	private String getDiscoverPromotionsPath = "/Api/Discover/Promotions";
	
	// Trending 
	private String getCarouselsTrendingPath = "/Api/Discover/Carousels/Trending";
	
	// Trending In Your City
	private String getCarouselsTrendingInYourCityPath = "/Api/Discover/Carousels/TrendingInYourCity";
	
	// Near You 
	private String getCarouselsNearYouPath = "/Api/Discover/Carousels/NearYou";

	/**
	 * Get the list of Promotions when user go to Home
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws Exception
	 */
	public ArrayList<PromotionData> getCarouselsPromotions(double latitude, double longitude) throws Exception {
		
		ArrayList<PromotionData> promotionsData = new ArrayList<>();
		
		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(getDiscoverPromotionsPath)
				.addParam("latitude", String.valueOf(latitude))
				.addParam("longitude", String.valueOf(longitude))
				.addHeader("Content-Type", "application/json")
				.oauth2(user.getToken()).get().getResponse();
		
		if(response.getStatusCode() == 200 && response.getValueFromResponse("Message").equals("Success")) {
			
			JSONArray results = JsonParser.convetToJsonArray(response.getValueFromResponse("Data"));

			for(int i = 0; i< results.length(); i++) {
				
				PromotionData promotionData = new PromotionData();

				promotionData.setPromotionTitle(((JSONObject) results.get(i)).getString("PromotionTitle"));
				promotionData.setPromotionId(((JSONObject) results.get(i)).getInt("PromotionId"));
					
				promotionsData.add(promotionData);
			}
		}
		
		return promotionsData;
	}
	
	/**
	 * Get the list of Trending collections when user go to Home
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws Exception
	 */
	public ArrayList<CarouselsData> getCarouselsTrendingCollection(double latitude, double longitude) throws Exception {
		
		ArrayList<CarouselsData> carouselsData = new ArrayList<>();
		
		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(getCarouselsTrendingPath)
				.addParam("latitude", String.valueOf(latitude))
				.addParam("longitude", String.valueOf(longitude))
				.addHeader("Content-Type", "application/json")
				.oauth2(user.getToken()).get().getResponse();
		
		if(response.getStatusCode() == 200 && response.getValueFromResponse("Message").equals("Records found.")) {
			
			JSONArray results = JsonParser.convetToJsonArray(response.getValueFromResponse("Data.ResultList"));

			for(int i = 0; i< results.length(); i++) {
				
				CarouselsData carouselData = new CarouselsData();

				carouselData.setBusinessDisplayName(((JSONObject) results.get(i)).getString("BusinessDisplayName"));
				carouselData.setBusinessAddress(((JSONObject) results.get(i)).getString("BusinessAddress"));
				carouselData.setCommentCount(((JSONObject) results.get(i)).getInt("CommentCount"));
				carouselData.setLikeCount(((JSONObject) results.get(i)).getInt("LikeCount"));
				carouselData.setOverAllRatingPercent(((JSONObject) results.get(i)).getInt("OverallRatingPercent"));
					
				carouselsData.add(carouselData);
			}
		}
		
		return carouselsData;
	}

	/**
	 * Get the list of Trending collections when user go to Home
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws Exception
	 */
	public ArrayList<CarouselsData> getCarouselsTrendingInYourCityCollection(double latitude, double longitude) throws Exception {
		
		ArrayList<CarouselsData> carouselsData = new ArrayList<>();
		
		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(getCarouselsTrendingInYourCityPath)
				.addParam("latitude", String.valueOf(latitude))
				.addParam("longitude", String.valueOf(longitude))
				.addHeader("Content-Type", "application/json")
				.oauth2(user.getToken()).get().getResponse();
		
		if(response.getStatusCode() == 200 && response.getValueFromResponse("Message").equals("Records found.")) {
			
			JSONArray results = JsonParser.convetToJsonArray(response.getValueFromResponse("Data.ResultList"));

			for(int i = 0; i< results.length(); i++) {
				
				CarouselsData carouselData = new CarouselsData();

				carouselData.setBusinessDisplayName(((JSONObject) results.get(i)).getString("BusinessDisplayName"));
				carouselData.setBusinessAddress(((JSONObject) results.get(i)).getString("BusinessAddress"));
				carouselData.setCommentCount(((JSONObject) results.get(i)).getInt("CommentCount"));
				carouselData.setLikeCount(((JSONObject) results.get(i)).getInt("LikeCount"));
				carouselData.setOverAllRatingPercent(((JSONObject) results.get(i)).getInt("OverallRatingPercent"));
					
				carouselsData.add(carouselData);
			}
		}
		
		return carouselsData;
	}
	
	/**
	 * Get the list of Near You collections when user go to Home
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws Exception
	 */
	public ArrayList<CarouselsData> getCarouselsNearYouCollection(double latitude, double longitude) throws Exception {
		
		ArrayList<CarouselsData> carouselsData = new ArrayList<>();
		
		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(getCarouselsNearYouPath)
				.addParam("latitude", String.valueOf(latitude))
				.addParam("longitude", String.valueOf(longitude))
				.addHeader("Content-Type", "application/json")
				.oauth2(user.getToken()).get().getResponse();
		
		if(response.getStatusCode() == 200 && response.getValueFromResponse("Message").equals("Records found.")) {
			
			JSONArray results = JsonParser.convetToJsonArray(response.getValueFromResponse("Data.ResultList"));

			for(int i = 0; i< results.length(); i++) {
				
				CarouselsData carouselData = new CarouselsData();

				carouselData.setBusinessDisplayName(((JSONObject) results.get(i)).getString("BusinessDisplayName"));
				carouselData.setBusinessAddress(((JSONObject) results.get(i)).getString("BusinessAddress"));
				carouselData.setCommentCount(((JSONObject) results.get(i)).getInt("CommentCount"));
				carouselData.setLikeCount(((JSONObject) results.get(i)).getInt("LikeCount"));
				carouselData.setOverAllRatingPercent(((JSONObject) results.get(i)).getInt("OverallRatingPercent"));
					
				carouselsData.add(carouselData);
			}
		}
		
		return carouselsData;
	}

}