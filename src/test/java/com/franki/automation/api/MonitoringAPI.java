package com.franki.automation.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.franki.automation.datamodel.BusinessData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;
import com.franki.automation.utility.JsonParser;

public class MonitoringAPI {
	
	private UserData user;
	
	public MonitoringAPI(UserData user) {
		this.user = user;
	}

	// follow another user
	private String createMonitoringRequestPath = "/Api/Monitoring/CreateMonitoringRequest";
	// unfollow another user
	private String stopMonitoringRequestPath = "/Api/Monitoring/StopMonitoringUser";

	// follow group
	private String associateWithGroupPath = "/Api/Group/AssociateWithGroup";
	// unfollow group
	private String archiveGroupAssociationPath = "/Api/Group/ArchiveGroupAssociation";

	// get users who i'm following 
	private String getUserFollowingPath = "/Api/SocialFeed/GetUserFollowing";
	private String GetCorporateGroupsForUserPath = "/Api/Group/GetCorporateGroupsForUser";

	// get users who are following me
	private String getUserFollowersPath = "/Api/SocialFeed/GetUserFollowers";
	
	// block an user
	private String blockUserPath = "/Api/Objectionable/BlockUser";
	// block an user
	private String unblockUserPath = "/Api/Objectionable/UnblockUser";
	
	public APIRequest blockUserRequest(String userID) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(blockUserPath).addParam("userId", userID)
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post();
		return request;
	}
	
	public APIRequest unblockUserRequest(String userID) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(unblockUserPath).addParam("userId", userID)
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post();
		return request;
	}

	public APIRequest createMonitoringRequest(String userID) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(createMonitoringRequestPath).addParam("userId", userID)
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post();
		return request;
	}
	
	public boolean followUser(String userId) throws Exception {
		return createMonitoringRequest(userId).getResponse().getStatusCode() == 200;
	}

	public APIRequest stopMonitoringRequest(String userID) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(stopMonitoringRequestPath).addParam("monitored", userID)
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post();
		return request;
	}
	
	public boolean unfollowUser(String userId) throws Exception {
		return stopMonitoringRequest(userId).getResponse().getStatusCode() == 200;
	}

	// follow group
	public APIRequest associateWithGroup(String groupCode) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(associateWithGroupPath).addParam("groupCode", groupCode)
				.addParam("corporateGroupOnly", "false").addHeader("Content-Type", "application/json")
				.oauth2(user.getToken()).post();
		return request;
	}

	// unfollow group
	public APIRequest archiveGroupAssociation(String groupCode) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(archiveGroupAssociationPath).addParam("groupCode", groupCode)
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post();
		return request;
	}

	// Get users who are followed by me
	public APIRequest getUserFollowing() throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(getUserFollowingPath).addParam("userId", user.getUserId())
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).get();
		return request;
	}

	public String getUserFollowingInfor(int index, String keychain) throws JSONException, Exception {
		return getUserFollowing().getResponse().getValueFromResponse("Data["+index+"]."+ keychain +"");
	}
	
	// Get all users who i'm following
	public ArrayList<UserData> getAllUsersFollowing() throws JSONException, Exception {
		ArrayList<UserData> userFollowingList = new ArrayList<UserData>();
		JSONArray data = JsonParser.convetToJsonArray(getUserFollowing().getResponse().getValueFromResponse("Data"));
		
		if(! data.isEmpty()) {
			data.forEach(e -> {
				try {
					UserData user = new UserData();
					user.setDisplayName(new JsonParser((JSONObject)e).extractJsonValue("DisplayName"));
					user.setFullName(new JsonParser((JSONObject)e).extractJsonValue("Fullname"));
					userFollowingList.add(user);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
		
		return userFollowingList;
	}
	
	// Get all users who are following me
	public ArrayList<UserData> getAllFollowers() throws JSONException, Exception {
		ArrayList<UserData> followers = new ArrayList<UserData>();
		JSONArray data = JsonParser.convetToJsonArray(getUserFollower().getResponse().getValueFromResponse("Data"));
		
		if(! data.isEmpty()) {
			data.forEach(e -> {
				try {
					UserData user = new UserData();
					user.setDisplayName(new JsonParser((JSONObject)e).extractJsonValue("DisplayName"));
					user.setFullName(new JsonParser((JSONObject)e).extractJsonValue("Fullname"));
					user.setUserId(new JsonParser((JSONObject)e).extractJsonValue("UserId"));
					followers.add(user);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
		
		return followers;
	}
	
	public void blockAllFollowers() throws JSONException, Exception {
		
		ArrayList<UserData> followers = getAllFollowers();
		followers.forEach(follower -> {
			try {
				blockUserRequest(follower.getUserId());
			} catch (Exception e) {
			}
		});
	}
	

	public APIRequest getUserFollower() throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(getUserFollowersPath).addParam("userId", user.getUserId())
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).get();
		return request;
	}

	public String getUserFollowerInfor(int index, String keychain) throws JSONException, Exception {
		return getUserFollower().getResponse().getValueFromResponse("Data["+index+"]."+ keychain +"");
	}

	public int getUserFollowerCount() throws Exception {
		return getUserFollower().getResponse().getResponseBodyInJson().getJSONArray("Data").length();
	}

	public APIRequest getCorporateGroupsForUser() throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(GetCorporateGroupsForUserPath).addParam("userId", user.getUserId())
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).get();
		return request;
	}

	public String getFirstFollowingProductData(String keychain) throws JSONException, Exception {
		JSONArray listPlaceAndProduct = getCorporateGroupsForUser().getResponse().getResponseBodyInJson()
				.getJSONArray("Data");
		for (int i = 0; i < listPlaceAndProduct.length(); ++i) {
			JSONObject index = listPlaceAndProduct.getJSONObject(i);
			if (index.get("GooglePlaceId").toString().equalsIgnoreCase("null")) {
				return index.get(keychain).toString();
			}
		}
		return null;
	}
	
	public ArrayList<BusinessData> getAllBusinessFollowing() throws JSONException, Exception {
		ArrayList<BusinessData> businessFollowingList = new ArrayList<BusinessData>();
		JSONArray data = getCorporateGroupsForUser().getResponse().getResponseBodyInJson().getJSONArray("Data");
		
		if(! data.isEmpty()) {
			data.forEach(e -> {
				try {
					BusinessData business = new BusinessData();
					business.setDisplayName(new JsonParser((JSONObject)e).extractJsonValue("DisplayName"));
					business.setAddress(new JsonParser((JSONObject)e).extractJsonValue("Address"));
					businessFollowingList.add(business);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
		
		return businessFollowingList;
	}

	public String getFirstFollowingPlaceData(String keychain) throws JSONException, Exception {
		JSONArray listPlaceAndProduct = getCorporateGroupsForUser().getResponse().getResponseBodyInJson()
				.getJSONArray("Data");
		for (int i = 0; i < listPlaceAndProduct.length(); ++i) {
			JSONObject index = listPlaceAndProduct.getJSONObject(i);
			if (!index.get("GooglePlaceId").toString().equalsIgnoreCase("null")) {
				return index.get(keychain).toString();
			}
		}
		return null;
	}

	public void unfollowAllUserAndProduct() throws Exception {
		// unfollow all account
		JSONObject userFollowing = getUserFollowing().getResponse().getResponseBodyInJson();
		JSONArray listUser = userFollowing.getJSONArray("Data");
		listUser.forEach(item -> {
			JSONObject obj = (JSONObject) item;
			try {
				stopMonitoringRequest(obj.get("UserId").toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// unfollow all products and place (all stored in getCorporateGroupFollowing)
		JSONObject groupFollowing = getCorporateGroupsForUser().getResponse().getResponseBodyInJson();
		JSONArray listGroupFollowing = groupFollowing.getJSONArray("Data");
		listGroupFollowing.forEach(item -> {
			JSONObject obj = (JSONObject) item;
			try {
				archiveGroupAssociation(obj.get("GroupCode").toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public boolean isFollowingUser(String userId) throws Exception {
		JSONObject userFollowing = getUserFollowing().getResponse().getResponseBodyInJson();
		JSONArray listUser = userFollowing.getJSONArray("Data");
		for(int i = 0;i < listUser.length();i++) {
			JSONObject user = listUser.getJSONObject(i);
			if(user.get("UserId").toString().equalsIgnoreCase(userId)) 
				return true;
		}
		return false;
	}
	
	public boolean isFollowingGroupOrPlace(String groupId) throws Exception {
		JSONObject userFollowing = getCorporateGroupsForUser().getResponse().getResponseBodyInJson();
		JSONArray listUser = userFollowing.getJSONArray("Data");
		for(int i = 0;i < listUser.length();i++) {
			JSONObject user = listUser.getJSONObject(i);
			if(user.get("GroupCode").toString().equalsIgnoreCase(groupId)) 
				return true;
		}
		return false;
	}
}
