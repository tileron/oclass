package com.student.oclass.json;

import org.json.JSONException;
import org.json.JSONObject;

import com.student.oclass.net.FunctionOfUrl.Function;


/**
 * 
 * @author lanyj
 * @version 1.0
 */
public class JsonParser {

	synchronized public static JsonResult parse(String response, Function func)
			throws JSONException {
		JSONObject jsonObject= new JSONObject(response);	
		switch (func) {		
		default:
			return null;
		}
	}
	
}
