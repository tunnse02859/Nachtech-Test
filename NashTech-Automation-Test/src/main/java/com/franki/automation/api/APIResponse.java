package com.franki.automation.api;

import java.io.IOException;
import org.json.JSONObject;

import com.franki.automation.report.markuphelper.MarkupHelper;
import com.franki.automation.report.HtmlReporter;
import com.franki.automation.utility.JsonParser;

import okhttp3.Response;

public class APIResponse {

	private String responseInString;
	private JSONObject responseInJsonObject;
	private int statusCode;

	public APIResponse(Response response) {
		try {
			responseInString = response.body().string();
			responseInJsonObject = responseInString.startsWith("[")
					? JsonParser.convetToJsonArray(responseInString).getJSONObject(0)
					: JsonParser.convetToJsonObject(responseInString);
			statusCode = response.code();
			
			HtmlReporter.getTest().info(MarkupHelper.createAPIRequestStep(response.request(),response,responseInString));
		} catch (IOException e) {
		} finally {
			response.body().close();
		}
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public JSONObject getResponseBodyInJson() {
		return this.responseInJsonObject;
	}

	public String getStringResponse() throws IOException {
		return responseInString;
	}

	public String getValueFromResponse(String keychains) throws Exception {
		JsonParser jsonParser = new JsonParser(responseInJsonObject);
		return jsonParser.extractJsonValue(keychains);
	}

}
