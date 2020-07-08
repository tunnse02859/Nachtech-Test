package com.franki.automation.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.franki.automation.datamodel.NotificationData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;
import com.franki.automation.utility.JsonParser;

public class NotificationAPI {
	private UserData user;

	public NotificationAPI(UserData user) {
		this.user = user;
	}

	private String getNotificationListPath = "/Api/Notification/GetNotificationList";

	public APIRequest getNotificationListRequest(int maxItems) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(getNotificationListPath)
				.addParam("numberOfNotifications", String.valueOf(maxItems))
				.addHeader("Content-Type", "application/json")
				.oauth2(user.getToken()).get();
		return request;
	}
	
	public ArrayList<NotificationData> getNotificationList(int maxItems) throws Exception {
		ArrayList<NotificationData> notificationsList = new ArrayList<NotificationData>();
		
		APIResponse response = getNotificationListRequest(maxItems).getResponse();
		
		JSONArray notifications = JsonParser.convetToJsonArray(response.getValueFromResponse("Data"));

		for(int i = 0; i< notifications.length(); i++) {
			
				NotificationData notification = new NotificationData();

				notification.setNotificationId(((JSONObject) notifications.get(i)).getInt("NotificationId"));
				notification.setCategory(((JSONObject) notifications.get(i)).getInt("Category"));
				notification.setStatusId(((JSONObject) notifications.get(i)).getInt("StatusId"));
				notification.setViewedStatus(((JSONObject) notifications.get(i)).getBoolean("Viewed"));
				notification.setSentTime(((JSONObject) notifications.get(i)).getString("Sent"));
				notification.setMessage(((JSONObject) notifications.get(i)).getString("Message"));
				
				notificationsList.add(notification);
		}
		
		
		return notificationsList;

	}


}