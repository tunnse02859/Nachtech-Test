package com.franki.automation.api;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.franki.automation.datamodel.BusinessData;
import com.franki.automation.datamodel.GigData;
import com.franki.automation.datamodel.GigPrizeData;
import com.franki.automation.datamodel.GigRuleData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;
import com.franki.automation.utility.Common;
import com.franki.automation.utility.JsonParser;

public class GigsAPI {
	private UserData user;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public GigsAPI(UserData user) {
		this.user = user;
	}

	private String getGigAllPath = "/Api/GigManager/GetGigAll";
	private String getGigsByCorporateGroupPath = "/Api/Gig/GetGigsByCorporateGroup";
	private String getAllCorporateGroupsPath = "/Api/Group/GetAllCorporateGroups";
	
	// Get Gig's detail path
	private String getGigDetailPath = "/Api/V2/Gig/GetGigDetail";
	// Create Gig path
	private String adminCreateGigPath = "/AdminApi/GigManager/CreateGig";
	// Create Gig Prize path
	private String adminCreateGigPrizesPath = "/AdminApi/GigManager/CreateGigPrizes";
	// Create Gig Rules path
	private String adminCreateGigRulesPath = "/AdminApi/GigManager/CreateGigRules";
	// Delete Gig
	private String adminDeleteGigPath = "/AdminApi/GigManager/DeleteGig";
	
	// Save Gig
	private String userSaveGigPath = "/Api/Gig/SaveGig";
	// Unsave Gig
	private String userUnsaveGigPath = "/Api/Gig/UnsaveGig";


	/**
	 * API request to get all active gigs when accessing to the Gig screen
	 * 
	 * @param businessTypes
	 * @param hasGig
	 * @param stateId
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws Exception
	 */
	public APIRequest getAllCorporateGroupsRequest(int[] businessTypes, boolean hasGig, int stateId, double latitude,
			double longitude) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(getAllCorporateGroupsPath).addParam("businessTypes", businessTypes)
				.addParam("hasGigs", String.valueOf(hasGig)).addParam("stateId", String.valueOf(stateId))
				.addParam("latitude", String.valueOf(latitude)).addParam("longitude", String.valueOf(longitude))
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).get();
		return request;
	}

	/**
	 * Get all active gigs in the Gig screen
	 * 
	 * @param businessTypes
	 * @param stateId
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws Exception
	 */
	public ArrayList<GigData> getAllActiveGigsByCorporateGroup(int[] businessTypes, int stateId, double latitude,
			double longitude) throws Exception {
		ArrayList<GigData> activeGigList = new ArrayList<>();

		APIResponse response = getAllCorporateGroupsRequest(businessTypes, true, stateId, latitude, longitude)
				.getResponse();
		JSONArray groups = response.getResponseBodyInJson().getJSONArray("Data");

		if (groups.length() == 0) {
			return activeGigList;
		}

		for (int i = 0; i < groups.length(); i++) {
			JSONArray gigs = ((JSONObject) groups.get(i)).getJSONArray("Gigs");

			for (int j = 0; j < gigs.length(); j++) {
				GigData gigData = new GigData();
				BusinessData businessData = new BusinessData();
				ArrayList<GigPrizeData> gigPrizesList = new ArrayList<>();

				// Gig info
				gigData.setGigId(((JSONObject) gigs.get(j)).getInt("GigId"));
				gigData.setGigName(((JSONObject) gigs.get(j)).getString("Name"));
				gigData.setGigDescription(((JSONObject) gigs.get(j)).getString("Description"));
				gigData.setGigStartDate(simpleDateFormat.parse(((JSONObject) gigs.get(j)).getString("StartDate")));
				gigData.setGigEndDate(simpleDateFormat.parse(((JSONObject) gigs.get(j)).getString("EndDate")));
				gigData.setGigStatus(((JSONObject) gigs.get(j)).getInt("StatusId"));
				
				// Gig's business Info
				businessData.setBusinessId(((JSONObject) gigs.get(j)).getInt("CorporateGroupId"));
				businessData.setBusinessTypeId(Integer.parseInt(JsonParser.extractJsonValue((JSONObject) groups.get(i), "BusinessType.Id")));
				businessData.setBusinessType(JsonParser.extractJsonValue((JSONObject) groups.get(i), "BusinessType.Name"));
				
				gigData.setGigBusiness(businessData);
				

				// Gig Prize
				JSONArray gigPrizes = ((JSONObject) gigs.get(j)).getJSONArray("GigPrizes");
				for (int k = 0; k < gigPrizes.length(); k++) {
					GigPrizeData gigPrizeData = new GigPrizeData();
					gigPrizeData.setGigPrizeId(((JSONObject) gigPrizes.get(k)).getInt("GigPrizeId"));
					gigPrizeData.setGigPrizeDescription(((JSONObject) gigPrizes.get(k)).getString("Description"));
					gigPrizeData.setGigPrizeAmount(((JSONObject) gigPrizes.get(k)).getDouble("PrizeAmount"));
					gigPrizeData.setGigPrizeWinnerQty(((JSONObject) gigPrizes.get(k)).getInt("WinnerQty"));
					gigPrizesList.add(gigPrizeData);
				}

				gigData.setGigPrizes(gigPrizesList);

				activeGigList.add(gigData);
			}

		}

		return activeGigList;
	}

	/**
	 * Create a gig
	 * 
	 * @param businessId
	 * @return gigID
	 * @throws Exception
	 */
	public int adminCreateGig(int businessId, String startDate, String endDate) throws Exception {
		String gigName = "auto" + System.currentTimeMillis();
		String gigDescription = "just a testing gig";

		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(adminCreateGigPath)
				.addFormDataPart("Name", gigName).addFormDataPart("Description", gigDescription)
				.addFormDataPart("StartDate", startDate).addFormDataPart("EndDate", endDate)
				.addFormDataPart("CorporateGroupId", String.valueOf(businessId))
				.addFormDataPart("PaymentStatus", String.valueOf(GigData.GigPaymentStatus.PAID))
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post().getResponse();
		return response.getResponseBodyInJson().getInt("Data");
	}

	/**
	 * Create a gig prize
	 * 
	 * @param gigId
	 * @param businessId
	 * @return success or fail
	 * @throws Exception
	 */
	public boolean adminCreateGigPrizesSuccess(int gigId, int businessId) throws Exception {
		JSONArray gigPrizesArray = new JSONArray();
		JSONObject gigPrize = new JSONObject();
		gigPrize.put("GigId", gigId).put("Description", "First").put("PrizeAmount", Common.randomNumeric(3))
				.put("WinnerQty", "2").put("CorporateGroupId", String.valueOf(businessId)).put("DisplayOrder", "1");
		gigPrizesArray.put(gigPrize);

		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(adminCreateGigPrizesPath)
				.body(gigPrizesArray.toString())
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post().getResponse();
		
		return response.getStatusCode() == 200;
	}
	
	/**
	 * Create a gig rule
	 * 
	 * @param gigId
	 * @return success or fail
	 * @throws Exception
	 */
	public boolean adminCreateGigRulesSuccess(int gigId) throws Exception {
		JSONArray gigRulessArray = new JSONArray();
		JSONObject gigRule = new JSONObject();
		gigRule.put("GigId", gigId).put("Description", "Unique").put("DisplayOrder", "1");
		gigRulessArray.put(gigRule);

		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(adminCreateGigRulesPath)
				.body(gigRulessArray.toString())
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post().getResponse();
		
		return response.getStatusCode() == 200;
	}
	
	/**
	 * Create an active gig for a business
	 * @param businessId
	 * @return gigId
	 * @throws Exception
	 */
	public int adminCreateActiveGig(int businessId) throws Exception {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constant.DATE_TIME_FORMAT);
		LocalDateTime now = LocalDateTime.now();

		String startDate = dtf.format(now.minusDays(1)).toString();
		String endDate = dtf.format(now.plusDays(20)).toString();
		
		int gigId = adminCreateGig(businessId, startDate, endDate);
		
		if(adminCreateGigPrizesSuccess(gigId, businessId) && adminCreateGigRulesSuccess(gigId)) {
			return gigId;
		}

		throw new Exception("Create active gig failed!!!");
	}
	
	/**
	 * Get all detail for a specific gig, it has more detail information than GetAllCorporateGroups api
	 * @param gigId
	 * @return
	 * @throws Exception
	 */
	public GigData getGigDetail(int gigId) throws Exception {
		
		GigData gigData = new GigData();
		BusinessData businessData = new BusinessData();
		ArrayList<GigPrizeData> gigPrizeDataList = new ArrayList<>();
		ArrayList<GigRuleData> gigRuleDataList = new ArrayList<>();
		
		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(getGigDetailPath).addParam("gigId", String.valueOf(gigId))
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).get().getResponse();
		
		if(response.getStatusCode() == 200 && response.getValueFromResponse("Message").equals("Success")) {
			
			JSONObject gigInfo = response.getResponseBodyInJson().getJSONObject("Data").getJSONObject("Gig");
			JSONObject businessInfo = response.getResponseBodyInJson().getJSONObject("Data").getJSONObject("CorporateGroup");
			
			// Gig info
			gigData.setGigId(gigInfo.getInt("GigId"));
			gigData.setGigName(gigInfo.getString("Name"));
			gigData.setGigDescription(gigInfo.getString("Description"));
			gigData.setGigStartDate(simpleDateFormat.parse(gigInfo.getString("StartDate")));
			gigData.setGigEndDate(simpleDateFormat.parse(gigInfo.getString("EndDate")));
			gigData.setGigCorporateGroupId(gigInfo.getInt("CorporateGroupId"));
			gigData.setGigStatus(gigInfo.getInt("StatusId"));
			gigData.setUserSavedGigStatus(gigInfo.getBoolean("IsSaved"));
			
			// Business Info
			businessData.setBusinessId(businessInfo.getInt("CorporateGroupId"));
			businessData.setDisplayName(businessInfo.getString("DisplayName"));
			businessData.setBusinessType(businessInfo.getJSONObject("BusinessType").getString("Name"));
			businessData.setBusinessId(businessInfo.getJSONObject("BusinessType").getInt("Id"));
			
			gigData.setGigBusiness(businessData);
			
			// Gig Prizes
			JSONArray gigPrizes = gigInfo.getJSONArray("GigPrizes");
			for (int k = 0; k < gigPrizes.length(); k++) {
				GigPrizeData gigPrizeData = new GigPrizeData();
				gigPrizeData.setGigPrizeId(((JSONObject) gigPrizes.get(k)).getInt("GigPrizeId"));
				gigPrizeData.setGigPrizeDescription(((JSONObject) gigPrizes.get(k)).getString("Description"));
				gigPrizeData.setGigPrizeAmount(((JSONObject) gigPrizes.get(k)).getDouble("PrizeAmount"));
				gigPrizeData.setGigPrizeWinnerQty(((JSONObject) gigPrizes.get(k)).getInt("WinnerQty"));
				gigPrizeData.setGigPrizeDisplayOrder(((JSONObject) gigPrizes.get(k)).getInt("DisplayOrder"));
				gigPrizeDataList.add(gigPrizeData);
			}

			gigData.setGigPrizes(gigPrizeDataList);
			
			// Gig Rules
			JSONArray gigRules = gigInfo.getJSONArray("GigRules");
			for (int k = 0; k < gigRules.length(); k++) {
				GigRuleData gigRuleData = new GigRuleData();
				gigRuleData.setGigRuleId(((JSONObject) gigRules.get(k)).getInt("GigRuleId"));
				gigRuleData.setGigRuleDescription(((JSONObject) gigRules.get(k)).getString("Description"));
				gigRuleData.setGigRuleDisplayOrder(((JSONObject) gigRules.get(k)).getInt("DisplayOrder"));
				gigRuleDataList.add(gigRuleData);
			}

			gigData.setGigRules(gigRuleDataList);
			
			return gigData;
		}
		else {
			return null;
		}
	} 
	
	/**
	 * User saves a gig
	 * 
	 * @param gigId
	 * @return success or fail
	 * @throws Exception
	 */
	public boolean saveGig(int gigId) throws Exception {
		
		JSONObject saveGigBody = new JSONObject();
		saveGigBody.put("UserId", user.getUserId()).put("GigId", String.valueOf(gigId));
		
		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(userSaveGigPath)
				.body(saveGigBody.toString())
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post().getResponse();
		
		return response.getStatusCode() == 200;
	}
	
	/**
	 * User unsaves a gig
	 * 
	 * @param gigId
	 * @return success or fail
	 * @throws Exception
	 */
	public boolean unsaveGig(int gigId) throws Exception {
		
		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(userUnsaveGigPath)
				.addParam("gigId", String.valueOf(gigId))
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post().getResponse();
		
		return response.getStatusCode() == 200;
	}
	
	/**
	 * Delete a gig
	 * 
	 * @param gigId
	 * @return success or fail
	 * @throws Exception
	 */
	public boolean deleteGig(int gigId) throws Exception {
		
		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(adminDeleteGigPath)
				.addParam("gigId", String.valueOf(gigId))
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post().getResponse();
		
		return response.getStatusCode() == 200 && response.getValueFromResponse("Message").equals("Successfully deleted the gig.");
	}

	public APIRequest getGigsByCorporateGroupRequest(int businessId) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(getGigsByCorporateGroupPath).addParam("corporateGroupId", String.valueOf(businessId)).addParam("numberOfItems", "100").addParam("numberOfSkipItems", "0").addHeader("Content-Type", "application/json").oauth2(user.getToken()).get();
		return request;
	}

	public APIRequest getActiveGigs(int status, String location, int orderBy) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(getGigAllPath).addParam("Status", String.valueOf(status)).addParam("FromDate", "").addParam("ToDate", "").addParam("SearchText", "").addParam("Location", location).addParam("PrizeAmountMin", "0").addParam("PrizeAmountMax", "200000").addParam("OrderBy", String.valueOf(orderBy)).addParam("pageSize", "20").addParam("pageNum", "1").oauth2(user.getToken()).get();
		return request;
	}

	public APIRequest getActiveGigsByCorporateGroups(int stateId) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(getAllCorporateGroupsPath).addParam("businessTypes", "1")
			.addParam("businessTypes", "9").addParam("businessTypes", "2").addParam("businessTypes", "8").addParam("businessTypes", "3")
			.addParam("businessTypes", "10").addParam("businessTypes", "0").addParam("hasGigs", "true")
			.addParam("stateId", String.valueOf(stateId)).oauth2(user.getToken()).get();
		return request;
	}

	public Integer getTotalActiveGigItems(String location) throws Exception {
		String total = getActiveGigs(1, location, 0).getResponse().getValueFromResponse("Data.TotalItems");
		return Integer.parseInt(total);
	}

	public List<String> getTotalActiveGigItemsName(int stateId) throws Exception {
		ArrayList<String> listName = new ArrayList<String>();
		JSONArray results = getActiveGigsByCorporateGroups(stateId).getResponse().getResponseBodyInJson().getJSONArray("Data");
		System.out.println(results);
		results.forEach(item -> {
			JSONObject data = (JSONObject) item;
			System.out.println(item);
			JSONArray gigs = data.getJSONArray("Gigs");
			gigs.forEach(e -> {
				try {
					System.out.println(e);
					listName.add(new JsonParser((JSONObject) e).extractJsonValue("Name"));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		});
		return listName;
	}

	public List<String> getTotalActiveGigItemsNameSortByClosingSoon(String location) throws Exception {
		ArrayList<String> listName = new ArrayList<String>();
		JSONArray results = getActiveGigs(1, location, 2).getResponse().getResponseBodyInJson().getJSONObject("Data").getJSONArray("Results");
		results.forEach(e -> {
			try {
				listName.add(new JsonParser((JSONObject) e).extractJsonValue("Gig.Name"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		return listName;
	}

	public ArrayList<GigData> getAllGigsForBusiness(int businessId) throws JSONException, Exception {

		ArrayList<GigData> gigsData = new ArrayList<>();

		APIResponse response = getGigsByCorporateGroupRequest(businessId).getResponse();

		JSONArray gigs = JsonParser.convetToJsonArray(response.getValueFromResponse("Data"));

		if (!gigs.isEmpty()) {
			gigs.forEach(e -> {
				try {
					GigData gig = new GigData();

					gig.setGigId(((JSONObject) e).getInt("GigId"));
					gig.setUserSavedGigStatus(((JSONObject) e).getBoolean("IsSaved"));
					gig.setGigName(((JSONObject) e).getString("Name"));
					gig.setGigDescription(((JSONObject) e).getString("Description"));
					gig.setGigCorporateGroupId(((JSONObject) e).getInt("CorporateGroupId"));
					gig.setGigStatus(((JSONObject) e).getInt("StatusId"));

					gigsData.add(gig);
				} catch (Exception e1) {
				}
			});
		}

		return gigsData;
	}

	public ArrayList<GigData> getAllActiveGigsForBusiness(int businessId) throws JSONException, Exception {
		return (ArrayList<GigData>) getAllGigsForBusiness(businessId).stream().filter(gig -> gig.getGigStatus() == GigData.GigStatus.ACTIVE_GIG).collect(Collectors.toList());
	}
}