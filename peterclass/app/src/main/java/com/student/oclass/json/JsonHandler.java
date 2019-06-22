package com.student.oclass.json;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonHandler {	
	 public JsonResult parse(JSONObject jsonObject) throws JSONException ;
}
