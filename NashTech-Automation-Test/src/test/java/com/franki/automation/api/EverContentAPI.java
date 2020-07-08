package com.franki.automation.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.franki.automation.datamodel.BusinessData;
import com.franki.automation.datamodel.ContentData;
import com.franki.automation.datamodel.UserData;
import com.franki.automation.setup.Constant;
import com.franki.automation.utility.FilePaths;
import com.franki.automation.utility.JsonParser;

public class EverContentAPI {

	private UserData user;

	public EverContentAPI(UserData user) {
		this.user = user;
	}

	private String getEverContentPath = "/Api/SocialFeed/GetEverContent";

	// Get saved contents
	private String getUserFavoritedEverContentPath = "/Api/SocialFeed/GetUserFavoritedEverContent";
	// Save a content
	private String favoriteContentItemPath = "/Api/Album/FavoriteContentItem";
	// Unsave a content
	private String unfavoriteContentItemPath = "/Api/Album/UnFavoriteContentItem";
	// Edit a content
	private String editEverContentPath = "/Api/SocialFeed/EditEverContent";

	private String getSpecificUserEverContentPath = "/Api/SocialFeed/GetSpecificUserEverContent";
	private String archiveContentPath = "/Api/SocialFeed/ArchiveContent";
	private String createVideoContentPath = "/api/socialfeed/CreateVideoContent";
	private String getContentDetailPath = "/Api/SocialFeed/Content/Details";
	private String likeContentPath = "/Api/SocialFeed/LikeContent";
	private String unLikeContentPath = "/Api/SocialFeed/UnlikeContent";
	private String GET_CONTENT_KEY_KEYCHAINS = "Data[%d].Key";
	private String createCommentPath = "/Api/SocialFeed/CreateComment";

	public APIRequest getEverContentRequest(int items) throws Exception {
		JSONObject requestBody = new JSONObject();
		requestBody.put("forceCardUpdate", false);

		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(getEverContentPath).addParam("items", String.valueOf(items))
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).get();
		return request;
	}

	public APIRequest getUserFavoritedEverContentRequest() throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(getUserFavoritedEverContentPath).addParam("items", "10")
				.addParam("userId", user.getUserId()).addHeader("Content-Type", "application/json")
				.oauth2(user.getToken()).get();
		return request;
	}

	public APIRequest favoritedEverContentRequest(String contentKey) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(favoriteContentItemPath).addParam("contentKey", contentKey)
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post();
		return request;
	}

	public APIRequest unfavoritedEverContentRequest(String contentKey) throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(unfavoriteContentItemPath).addParam("contentKey", contentKey)
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post();
		return request;
	}

	public APIRequest getSpecificUserEverContentRequest() throws Exception {
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(getSpecificUserEverContentPath).addParam("targetId", user.getUserId())
				.addParam("items", "500").addParam("cutoffTime", "").addHeader("Content-Type", "application/json")
				.oauth2(user.getToken()).get();
		return request;
	}

	public APIRequest archiveContentRequest(String contentKey) throws Exception {

		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(archiveContentPath).addParam("contentKey", contentKey)
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post();
		return request;
	}

	public APIRequest getContentDetailRequest(String contentKey) throws Exception {

		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(getContentDetailPath).addParam("contentKeys", contentKey)
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).get();
		return request;
	}

	public APIRequest likeContentRequest(String contentKey) throws Exception {

		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(likeContentPath).addParam("contentKey", contentKey)
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post();
		return request;
	}

	public boolean likeContent(String contentKey) throws Exception {
		return likeContentRequest(contentKey).getResponse().getStatusCode() == 200;
	}

	public APIRequest unLikeContentRequest(String contentKey) throws Exception {

		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(unLikeContentPath).addParam("contentKey", contentKey)
				.addHeader("Content-Type", "application/json").oauth2(user.getToken()).post();
		return request;
	}

	public boolean unLikeContent(String contentKey) throws Exception {
		return unLikeContentRequest(contentKey).getResponse().getStatusCode() == 200;
	}

	public ContentData getContentDetail(String contentKey) throws Exception {
		JSONObject content = new JSONObject(
				getContentDetailRequest(contentKey).getResponse().getValueFromResponse("Data.ContentInfo[0]"));
		ContentData contentData = new ContentData();
		BusinessData business = new BusinessData();
		// set content info
		contentData.setContentKey(JsonParser.extractJsonValue(content, "Key"));

		int likeCount = Integer.parseInt(JsonParser.extractJsonValue(content, "LikeCount"));
		int commenCount = Integer.parseInt(JsonParser.extractJsonValue(content, "CommentCount"));
		contentData.setLikeCount(likeCount);
		contentData.setCommentCount(commenCount);
		// set content's business info
		business.setDisplayName(JsonParser.extractJsonValue(content, "Business.DisplayName"));
		String businessType = JsonParser.extractJsonValue(content, "Business.BusinessType");
		businessType = businessType.equalsIgnoreCase("") ? ""
				: JsonParser.extractJsonValue(content, "Business.BusinessType.Name");
		business.setBusinessType(businessType);
		contentData.setCorporateGroup(business);
		return contentData;
	}

	// Get a list of Contents
	public ArrayList<ContentData> getEverContent() throws Exception {
		ArrayList<ContentData> contentList = new ArrayList<ContentData>();
		JSONArray contents = getEverContentRequest(10).getResponse().getResponseBodyInJson().getJSONArray("Data");
		contents.forEach(c -> {
			try {
				ContentData content = new ContentData();
				BusinessData business = new BusinessData();
				// set content info
				content.setContentKey(JsonParser.extractJsonValue((JSONObject) c, "Key"));
				content.setPostBody(JsonParser.extractJsonValue((JSONObject) c, "PostBody"));
				content.setLikeCount(Integer.parseInt(JsonParser.extractJsonValue((JSONObject) c, "LikeCount")));
				content.setLikeCount(Integer.parseInt(JsonParser.extractJsonValue((JSONObject) c, "CommentCount")));

				// set content's business info
				business.setDisplayName(JsonParser.extractJsonValue((JSONObject) c, "CorporateGroup.DisplayName"));
				business.setFullName(JsonParser.extractJsonValue((JSONObject) c, "CorporateGroup.FullName"));
				String businessType = JsonParser.extractJsonValue((JSONObject) c, "CorporateGroup.BusinessType");
				businessType = businessType.equalsIgnoreCase("") ? ""
						: JsonParser.extractJsonValue((JSONObject) c, "CorporateGroup.BusinessType.Name");
				business.setBusinessType(businessType);
				content.setCorporateGroup(business);
				contentList.add(content);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		return contentList;
	}

	// Get a list of saved content
	public ArrayList<ContentData> getUserFavoritedEverContent() throws Exception {
		ArrayList<ContentData> contentList = new ArrayList<ContentData>();
		JSONArray contents = getUserFavoritedEverContentRequest().getResponse().getResponseBodyInJson()
				.getJSONArray("Data");
		contents.forEach(c -> {
			try {
				ContentData content = new ContentData();
				BusinessData business = new BusinessData();
				// set content info
				content.setContentKey(JsonParser.extractJsonValue((JSONObject) c, "Key"));
				content.setPostBody(JsonParser.extractJsonValue((JSONObject) c, "PostBody"));
				content.setLikeCount(Integer.parseInt(JsonParser.extractJsonValue((JSONObject) c, "LikeCount")));
				content.setLikeCount(Integer.parseInt(JsonParser.extractJsonValue((JSONObject) c, "CommentCount")));

				// set content's business info
				business.setDisplayName(JsonParser.extractJsonValue((JSONObject) c, "CorporateGroup.DisplayName"));
				business.setFullName(JsonParser.extractJsonValue((JSONObject) c, "CorporateGroup.FullName"));
				String businessType = JsonParser.extractJsonValue((JSONObject) c, "CorporateGroup.BusinessType");
				businessType = businessType.equalsIgnoreCase("") ? ""
						: JsonParser.extractJsonValue((JSONObject) c, "CorporateGroup.BusinessType.Name");
				business.setBusinessType(businessType);
				content.setCorporateGroup(business);
				contentList.add(content);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		return contentList;
	}
	
	public ArrayList<ContentData> getUserContents() throws Exception {
		ArrayList<ContentData> contentList = new ArrayList<ContentData>();
		JSONArray contents = getSpecificUserEverContentRequest().getResponse().getResponseBodyInJson().getJSONArray("Data");
		contents.forEach(c -> {
			try {
				ContentData content = new ContentData();
				BusinessData business = new BusinessData();
				// set content info
				content.setContentKey(JsonParser.extractJsonValue((JSONObject) c, "Key"));
				content.setPostBody(JsonParser.extractJsonValue((JSONObject) c, "PostBody"));
				content.setLikeCount(Integer.parseInt(JsonParser.extractJsonValue((JSONObject) c, "LikeCount")));
				content.setLikeCount(Integer.parseInt(JsonParser.extractJsonValue((JSONObject) c, "CommentCount")));

				// set content's business info
				business.setDisplayName(JsonParser.extractJsonValue((JSONObject) c, "CorporateGroup.DisplayName"));
				business.setFullName(JsonParser.extractJsonValue((JSONObject) c, "CorporateGroup.FullName"));
				String businessType = JsonParser.extractJsonValue((JSONObject) c, "CorporateGroup.BusinessType");
				businessType = businessType.equalsIgnoreCase("") ? ""
						: JsonParser.extractJsonValue((JSONObject) c, "CorporateGroup.BusinessType.Name");
				business.setBusinessType(businessType);
				content.setCorporateGroup(business);
				contentList.add(content);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		return contentList;
	}

	public boolean isSavedContent(String contentKey) throws Exception {
		ArrayList<ContentData> savedContentList = getUserFavoritedEverContent();
		for (ContentData content : savedContentList) {
			if (content.getContentKey().equalsIgnoreCase(contentKey))
				return true;
		}
		return false;
	}

	// Save a content
	public boolean favoriteContent() throws Exception {
		String contentKey = getAnyContentKey(10, 0);
		return favoritedEverContentRequest(contentKey).getResponse().getStatusCode() == 200;
	}

	// Unsave a content
	public boolean unfavoriteContent(String contentKey) throws Exception {
		return unfavoritedEverContentRequest(contentKey).getResponse().getStatusCode() == 200;
	}

	// Remove all saved contents
	public void unfavoriteAllContents() throws Exception {
		ArrayList<ContentData> savedContent = getUserFavoritedEverContent();

		savedContent.forEach(c -> {
			try {
				unfavoriteContent(c.getContentKey());
			} catch (Exception e) {
			}
		});
	}

	public String getAnyContentKey(int items, int itemIndex) throws Exception {
		return getEverContentRequest(items).getResponse()
				.getValueFromResponse(String.format(GET_CONTENT_KEY_KEYCHAINS, itemIndex));
	}

	public void archiveAllUserContent() throws Exception {
		// delete all content for empty posts
		JSONObject userContents = getSpecificUserEverContentRequest().getResponse().getResponseBodyInJson();
		JSONArray listContent = userContents.getJSONArray("Data");
		listContent.forEach(item -> {
			JSONObject obj = (JSONObject) item;
			try {
				archiveContentRequest(obj.get("Key").toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Create a random video post for a business
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	public ContentData createVideoContentForBusiness(int businessId)
			throws Exception {
		
		JSONObject ratings = new JSONObject();
		ratings.put("1", "true").put("2", "true").put("3", "true").put("4", "true");
		
		String contentDescription = "createcontent" + System.currentTimeMillis();

		ContentData content = new ContentData();
		String tempThumbnailFile = "/testdata/CreateVideoContent.jpg";
		String tempVideoFile = "/testdata/CreateVideoContent.mp4";
		String image = FilePaths.getResourcePath(tempThumbnailFile);
		String video = FilePaths.getResourcePath(tempVideoFile);

		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(createVideoContentPath)
				.addHeader("Content-Type", "multipart/form-data").oauth2(user.getToken())
				.addFormDataPart("DurationSeconds", "150").addFormDataPart("Height", "640")
				.addFormDataPart("Width", "640")
				.addFormDataPart("Text", contentDescription).addFormDataPart("video", video, "video/mp4")
				.addFormDataPart("thumbnail", image, "image/jpeg")
				.addFormDataPart("AttributionAnswers", "1,2,3,5,17,20,23,7")
				.addFormDataPart("Ratings", ratings.toString())
				.addFormDataPart("CorporateGroupId", String.valueOf(businessId)).post().getResponse();

		if (response.getValueFromResponse("Message").equalsIgnoreCase("Successfully added the social post.")) {
			content.setContentKey(response.getValueFromResponse("Data.ContentKey"));
			content.setPostBody(response.getValueFromResponse("Data.Text"));
			
			BusinessData businessData = new BusinessData();
			businessData.setBusinessId(Integer.valueOf(response.getValueFromResponse("Data.CorporateGroupId")));
			
			content.setCorporateGroup(businessData);
			
			return content;
		}

		throw new Exception("Create content failed!!!");
	}

	/**
	 * Create a video post for a business with tagged user
	 * @param businessId
	 * @param taggedUserId
	 * @param taggedUserDisplayName
	 * @return
	 * @throws Exception
	 */
	public ContentData createVideoContentForBusiness(int businessId, String taggedUserId, String taggedUserDisplayName)
			throws Exception {
		
		JSONObject ratings = new JSONObject();
		ratings.put("1", "true").put("2", "true").put("3", "true").put("4", "true");
		
		String contentDescription = "createcontent" + System.currentTimeMillis() + "@" + taggedUserDisplayName;

		ContentData content = new ContentData();
		String tempThumbnailFile = "/testdata/CreateVideoContent.jpg";
		String tempVideoFile = "/testdata/CreateVideoContent.mp4";
		String image = FilePaths.getResourcePath(tempThumbnailFile);
		String video = FilePaths.getResourcePath(tempVideoFile);

		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(createVideoContentPath)
				.addHeader("Content-Type", "multipart/form-data").oauth2(user.getToken())
				.addFormDataPart("DurationSeconds", "150").addFormDataPart("Height", "640")
				.addFormDataPart("Width", "640")
				.addFormDataPart("Text", contentDescription).addFormDataPart("video", video, "video/mp4")
				.addFormDataPart("thumbnail", image, "image/jpeg")
				.addFormDataPart("TaggedUsers", taggedUserId)
				.addFormDataPart("AttributionAnswers", "1,2,3,5,17,20,23,7")
				.addFormDataPart("Ratings", ratings.toString())
				.addFormDataPart("CorporateGroupId", String.valueOf(businessId)).post().getResponse();

		if (response.getValueFromResponse("Message").equalsIgnoreCase("Successfully added the social post.")) {
			content.setContentKey(response.getValueFromResponse("Data.ContentKey"));
			content.setPostBody(response.getValueFromResponse("Data.Text"));
			
			BusinessData businessData = new BusinessData();
			businessData.setBusinessId(Integer.valueOf(response.getValueFromResponse("Data.CorporateGroupId")));
			
			content.setCorporateGroup(businessData);
			
			return content;
		}

		throw new Exception("Create content failed!!!");
	}
	
	/**
	 * Create a video post for a gig belongings to a Business with tagged user
	 * @param businessId
	 * @param gigId
	 * @param contentDescription
	 * @param taggedUserId
	 * @param taggedUserDisplayName
	 * @return
	 * @throws Exception
	 */
	public ContentData createVideoContentForBusiness(int businessId, int gigId, String taggedUserId, String taggedUserDisplayName)
			throws Exception {
		
		JSONObject ratings = new JSONObject();
		ratings.put("1", "true").put("2", "true").put("3", "true").put("4", "true");
		
		String contentDescription = "createcontent" + System.currentTimeMillis() + "@" + taggedUserDisplayName;

		ContentData content = new ContentData();
		String tempThumbnailFile = "/testdata/CreateVideoContent.jpg";
		String tempVideoFile = "/testdata/CreateVideoContent.mp4";
		String image = FilePaths.getResourcePath(tempThumbnailFile);
		String video = FilePaths.getResourcePath(tempVideoFile);

		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(createVideoContentPath)
				.addHeader("Content-Type", "multipart/form-data").oauth2(user.getToken())
				.addFormDataPart("DurationSeconds", "150").addFormDataPart("Height", "640")
				.addFormDataPart("Width", "640")
				.addFormDataPart("Text", contentDescription).addFormDataPart("video", video, "video/mp4")
				.addFormDataPart("thumbnail", image, "image/jpeg")
				.addFormDataPart("TaggedUsers", taggedUserId)
				.addFormDataPart("AttributionAnswers", "1,2,3,5,17,20,23,7")
				.addFormDataPart("Ratings", ratings.toString())
				.addFormDataPart("GigId", String.valueOf(gigId))
				.addFormDataPart("CorporateGroupId", String.valueOf(businessId)).post().getResponse();

		if (response.getValueFromResponse("Message").equalsIgnoreCase("Successfully added the social post.")) {
			content.setContentKey(response.getValueFromResponse("Data.ContentKey"));
			content.setPostBody(response.getValueFromResponse("Data.Text"));
			
			BusinessData businessData = new BusinessData();
			businessData.setBusinessId(Integer.valueOf(response.getValueFromResponse("Data.CorporateGroupId")));
			
			content.setCorporateGroup(businessData);
			
			return content;
			
		}

		throw new Exception("Create content failed!!!");
	}
	
	/**
	 * Edit a post to tag user
	 * @param contentKey
	 * @param taggedUserId
	 * @param taggedUserDisplayName
	 * @return
	 * @throws Exception
	 */
	public ContentData editEverContentToTagUser(String contentKey, String taggedUserId, String taggedUserDisplayName)
			throws Exception {
		
		String contentDescription = "editcontent" + System.currentTimeMillis() + "@" + taggedUserDisplayName;

		ContentData content = new ContentData();

		APIResponse response = new APIRequest().baseUrl(Constant.API_HOST).path(editEverContentPath)
				.addHeader("Content-Type", "multipart/form-data").oauth2(user.getToken())
				.addFormDataPart("ContentKey", contentKey)
				.addFormDataPart("PostBody", contentDescription)
				.addFormDataPart("TaggedUsers", taggedUserId)
				.post().getResponse();

		if (response.getValueFromResponse("Message").equalsIgnoreCase("Successfully updated the social post.")) {
			content.setContentKey(response.getValueFromResponse("Data.ContentKey"));
			content.setPostBody(response.getValueFromResponse("Data.PostBody"));
			return content;
			
		}

		throw new Exception("Update content failed!!!");
	}

	/**
	 * Comment to a post
	 * @param contentKey
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	public boolean createComment(String contentKey, String comment) throws Exception {
		APIRequest request = new APIRequest();
		APIResponse response = request.baseUrl(Constant.API_HOST).path(createCommentPath)
				.addHeader("Content-Type", "application/json").oauth2(user.getToken())
				.addFormDataPart("ContentKey", contentKey).addFormDataPart("Comment", comment).post().getResponse();
		return response.getStatusCode() == 200;
	}

}
