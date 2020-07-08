package com.franki.automation.report.markuphelper;

import java.util.List;

import com.aventstack.extentreports.markuputils.Markup;

import okhttp3.Request;
import okhttp3.Response;

public class MarkupHelper extends com.aventstack.extentreports.markuputils.MarkupHelper{
	
	public static Markup createAPIRequestStep(Request request, Response response,String responseBody) {
        return new APIRequestStep(request,response,responseBody);
    }
	
	public static Markup createAPIRequestStep(Request request, Throwable exception) {
        return new APIRequestStep(request,exception);
    }
	
	public static Markup createAPIRequestStep(Request request, Response response, String responseBody,List<String> assertionList, List<String> extractedVariable) {
		return new APIRequestStep(request,response,responseBody,assertionList,extractedVariable,null);
    }
	
	public static Markup createAPIRequestStep(Request request, Response response,String responseBody,List<String> assertionList, List<String> extractedVariable, Throwable exception) {
        return new APIRequestStep(request,response,responseBody,assertionList,extractedVariable,exception);
    }

}
