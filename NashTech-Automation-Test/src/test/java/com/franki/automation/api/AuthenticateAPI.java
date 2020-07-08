package com.franki.automation.api;

import com.franki.automation.api.APIRequest;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;

public class AuthenticateAPI {

	private String token;
	private String userId;
	private String loginPath = "/Api/Authentication/Authenticate";
	

	public APIRequest loginRequest(String username, String password) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(loginPath).addParam("identifier", username)
				.addParam("password", password).addHeader("Content-Type", "application/json").post();
		return request;
	}

	public UserData loginForAccessToken(String username, String password) throws Exception {
		UserData user = new UserData();
		APIResponse loginResponse = loginRequest(username, password).getResponse();
		token = loginResponse.getValueFromResponse("Data.Token.Value");
		userId = loginResponse.getValueFromResponse("Data.Profile.UserId");
		
		user.setToken(loginResponse.getValueFromResponse("Data.Token.Value"));
		user.setUserId(loginResponse.getValueFromResponse("Data.Profile.UserId"));
		user.setDisplayName(loginResponse.getValueFromResponse("Data.Profile.DisplayName"));
		user.setFullName(loginResponse.getValueFromResponse("Data.Profile.Fullname"));
		user.setUsername(username);
		user.setPassword(password);
		
		return user;
	}

	public String getAccessToken() throws Exception {
		if (this.token == null) {
			return loginRequest(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1).getResponse()
					.getValueFromResponse("Data.Token.Value");
		} else {
			return this.token;
		}
	}
	
	public void setAuthenticationInfo(String token,String userId) throws Exception {
		this.token = token;
		this.userId = userId;
	}
	
	public String getUserId() throws Exception {
		if (this.token.equalsIgnoreCase("")) {
			return loginRequest(Constant.ACCOUNT_EMAIL_1, Constant.ACCOUNT_PASSWORD_1).getResponse()
					.getValueFromResponse("Data.Token.Value");
		} else {
			return this.userId;
		}
	}
}
