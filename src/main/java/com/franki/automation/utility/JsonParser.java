package com.franki.automation.utility;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParser {
	
	private JSONObject json;

	public JsonParser(JSONObject json) {
		this.json = json;
	}
	
	public JsonParser() {
		// TODO Auto-generated constructor stub
	}

	public static JSONArray convetToJsonArray(String jsonString) {
		return new JSONArray(jsonString);
	}
	
	public static JSONObject convetToJsonObject(String jsonString) {
		return new JSONObject(jsonString);
	}

	public String extractJsonValue(String keychains) throws Exception {
		return extractJsonValue(this.json, keychains);
	}
	
	public static String extractJsonValue(JSONObject json, String keychains) throws Exception {

		String errorMessage = "";

		if (json != null) {
			JSONObject currentNode = json;
			String[] keys = keychains.split("\\.");

			for (int i = 0; i < keys.length; i++) {
				String currentExtractKey = keys[i];
				String key = "";
				String indexInArray = StringUtils.substringBetween(currentExtractKey, "[", "]");

				if (indexInArray != null) {
					key = currentExtractKey.substring(0, currentExtractKey.indexOf("["));
				} else {
					key = currentExtractKey;
				}

				if (currentNode.has(key)) {

					Object extractedNode = currentNode.get(key);

					if ((indexInArray != null) && (extractedNode instanceof JSONArray)) {

						extractedNode = ((JSONArray) extractedNode).get(Integer.parseInt(indexInArray));

					} else if ((indexInArray != null) && !(extractedNode instanceof JSONArray)) {

						errorMessage = "[Extract][FAILED] [" + key + "] in [" + keychains + "] is not an json array";
						break;

					}

					if (i == keys.length - 1) {

						String extractedValue = extractedNode.toString();
						return extractedValue.equalsIgnoreCase("") || extractedValue.equalsIgnoreCase("null") ? ""
								: extractedValue;

					} else if (!(extractedNode instanceof JSONObject) && !(extractedNode instanceof JSONArray)) {

						errorMessage = "[Extract][FAILED] key [" + key + "] in [" + keychains + "] does not contain ["
								+ keys[i + 1] + "]";
						break;

					} else {

						currentNode = (JSONObject) extractedNode;

					}
				} else {

					errorMessage = "[FAILED] key [" + key + "] in [" + keychains + "] does not exist";
					break;
				}
			}

		} else {
			throw new Exception("Response is null!!!");
		}
		
		throw new Exception(errorMessage);
	}



}
