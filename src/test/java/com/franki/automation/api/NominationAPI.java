package com.franki.automation.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.franki.automation.datamodel.BusinessData;
import com.franki.automation.datamodel.ContentData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;
import com.franki.automation.utility.Common;
import com.franki.automation.utility.FilePaths;
import com.franki.automation.utility.JsonParser;

public class NominationAPI {

	private UserData admin;

	public NominationAPI(UserData admin) {
		this.admin = admin;
	}

	private String nominatedVideoPath = "/AdminApi/NominatedVideos/CreateVideoNomination";
	private String assignGigPrizePath = "/AdminApi/GigManager/AssignGigPrize";

	/**
	 * Nominate for a content
	 * 
	 * @param businessId
	 * @param gigId
	 * @param prizeId
	 * @param userId
	 * @param contentKey
	 * @return
	 * @throws Exception
	 */
	public boolean nominateAContent(int businessId, int gigId, int prizeId, String userId, String contentKey)
			throws Exception {

		JSONObject nominateBody = new JSONObject();
		nominateBody.put("CorporateGroupId", businessId).put("NominatedGigId", gigId)
				.put("NominatedGigPrizeId", prizeId).put("NominatingMemberId", userId)
				.put("NominatedVideoContentKey", contentKey);

		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(nominatedVideoPath)
				.body(nominateBody.toString()).addHeader("Content-Type", "application/json").oauth2(admin.getToken())
				.post().getResponse();

		return response.getStatusCode() == 200
				&& response.getValueFromResponse("Message").equals("Success. Video is now nominated for a gig prize.");
	}

	/**
	 * Assign a gig prize for a nomination
	 * @param gigPrizeId
	 * @param contentKey
	 * @return
	 * @throws Exception
	 */
	public boolean assignGigPrizeForNominatation(int gigPrizeId, String contentKey) throws Exception {

		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(assignGigPrizePath)
				.addParam("gigPrizeId", String.valueOf(gigPrizeId)).addParam("contentKey", contentKey)
				.addHeader("Content-Type", "application/json").oauth2(admin.getToken()).post().getResponse();

		return response.getStatusCode() == 200
				&& response.getValueFromResponse("Message").equals("Successfully assigned the gig prize to content.");
	}

}
