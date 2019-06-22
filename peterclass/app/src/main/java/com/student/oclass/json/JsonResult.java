package com.student.oclass.json;

public class JsonResult {

	public int stateCode;
	public String reason;
	private String message;
	private boolean success = false;
//	private String result;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
