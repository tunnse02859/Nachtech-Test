package com.franki.automation.datamodel;

public class ContentData {
	
	private String contentKey;
	private String postedTime;
	private String postBody;
	private UserData poster;
	private int likeCount;
	private int commentCount;
	private BusinessData corporateGroup;

	// CorporateGroup
	public void setCorporateGroup(BusinessData corporateGroup) {
		this.corporateGroup = corporateGroup;
	}
	
	public BusinessData getCorporateGroup() {
		return this.corporateGroup;
	}
	
	// ContentKey
	public void setContentKey(String contentKey) {
		this.contentKey = contentKey;
	}
	
	public String getContentKey() {
		return this.contentKey;
	}
	
	// postedTime
	public void setPostedTime(String postedTime) {
		this.postedTime = postedTime;
	}
	
	public String getPostedTime() {
		return this.postedTime;
	}
	
	// postBody
	public void setPostBody(String postBody) {
		this.postBody = postBody;
	}
	
	public String getPostBody() {
		return this.postBody;
	}
	
	// Poster
	public void setDisplayName(UserData poster) {
		this.poster = poster;
	}
	
	public UserData getDisplayName() {
		return this.poster;
	}

	// Likes
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	
	public int getLikeCount() {
		return likeCount;
	}

	// Comments
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
	public int getCommentCount() {
		return commentCount;
	}

}
