package com.student.oclass.net;

/**
 * 接口具体的路径
 * 
 * @author lanyj
 */
public class FunctionOfUrl {
	//
	public final static String APP_URL="";
	// 定义请求参数的路径
	public enum Function {
		APP_UPDATE, 
	}

	public static String getURL(Function function) {
		switch (function) {		
		case APP_UPDATE:
			return APP_URL;
		default:
			return "";
		}
	}
}
