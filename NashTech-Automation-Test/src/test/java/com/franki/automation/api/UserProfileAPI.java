package com.franki.automation.api;

import com.franki.automation.api.APIRequest;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;

public class UserProfileAPI {
	
	UserData user;
	
	public UserProfileAPI(UserData user) {
		this.user = user;
	}
	
	//get profile
	private String getUserProfilePath = "/Api/UserProfile/GetProfile";
	// set user profile to public/private
	private String setUserPublicPrivateProfilePath = "/Api/Objectionable/SetUserPublicPrivateProfile";

	public APIRequest getUserProfile() throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(getUserProfilePath).addParam("userId", user.getUserId())
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).get();
		return request;
	}

	public String getUserProfileInfoWithKeychain(String keychain) throws Exception {
		return getUserProfile().getResponse().getValueFromResponse(keychain);
	}

	

	public APIRequest setUserPublicPrivateProfile(String isPublic) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(setUserPublicPrivateProfilePath).addParam("isPublic", isPublic)
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post();
		return request;
	}

	public void setUserToPublic() throws Exception {
		setUserPublicPrivateProfile("true");
	}

	public void setUserToPrivate() throws Exception {
		setUserPublicPrivateProfile("false");
	}

}
