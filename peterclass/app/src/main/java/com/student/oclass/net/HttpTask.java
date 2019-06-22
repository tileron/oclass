package com.student.oclass.net;

import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.os.Handler;

import com.student.oclass.R;
import com.student.oclass.json.JsonParser;
import com.student.oclass.json.JsonResult;
import com.student.oclass.net.FunctionOfUrl.Function;
import com.student.oclass.utils.AppLog;
import com.student.oclass.utils.ScreenManager;


public class HttpTask {
	
	//private static final String FILE_NAME = "/sdcard/testrequest.json";
	//private static final int MaxBufferSize = 8 * 1024;
	
	private static final String TAG="HttpTask";
	private Handler uiHandler;
	HHttp hhttp=null;
	public HttpTask(Handler uiHandler) {
		this.uiHandler = uiHandler;
		hhttp = new HHttp();
	}
	
	public HttpTask(){
		hhttp = new HHttp();
	}

	
	
	
	/**
	 * 上传单个文件
	 * Date:2014-6-10
	 * @author lanyj
	 * @param fun
	 * @param mapstr
	 * @param formFile
	 * @return 
	 * @return JsonResult
	 */
	/*public JsonResult getReqPOSTForFile(Function fun,Map<String, String> mapstr,FormFile formFile){
		String urlrequest=null;
		JsonResult requestResult=null;
		
		try {
			urlrequest = HHttp.post(FunctionOfUrl.getURL(fun), mapstr, formFile);
			//toFile(urlrequest.getBytes());
			requestResult=JsonParser.parse(urlrequest, fun);
		} catch (JSONException e) {
			e.printStackTrace();
			AppLog.d(TAG, e.getMessage());
			ScreenManager.updateUI(uiHandler, R.id.ui_show_text,R.string.exception_parse);
		} catch (Exception e) {
			e.printStackTrace();
			AppLog.d(TAG, e.getMessage());
			ScreenManager.updateUI(uiHandler, R.id.ui_show_text,R.string.exception_timeout);
		}		
		return requestResult;
	}*/
	

	public JsonResult getRequestbyPOST(Function fun,Map<String, String> mapstr){
		String urlrequest=null; 
		JsonResult requestResult=null;
		try {
			//toFile(urlrequest.getBytes());
			urlrequest = hhttp.doPost(FunctionOfUrl.getURL(fun), mapstr);
			requestResult=JsonParser.parse(urlrequest, fun);
		} catch (JSONException e) {
			e.printStackTrace();
			AppLog.d(TAG, e.getMessage());
			ScreenManager.updateUI(uiHandler, R.id.ui_show_text,R.string.exception_parse);
		} catch (Exception e) {
			e.printStackTrace();
			AppLog.d(TAG, e.getMessage());
			ScreenManager.updateUI(uiHandler, R.id.ui_show_text,R.string.exception_timeout);
		}	
		return requestResult;
	}
	public JsonResult getRequestbyPOST(Function fun,List<String[]> pramas){
		String urlrequest=null; 
		JsonResult requestResult=null;
		try {
			//toFile(urlrequest.getBytes());
			urlrequest = hhttp.doPostList(FunctionOfUrl.getURL(fun), pramas);
			requestResult=JsonParser.parse(urlrequest, fun);
		} catch (JSONException e) {
			e.printStackTrace();
			AppLog.d(TAG, e.getMessage());
			ScreenManager.updateUI(uiHandler, R.id.ui_show_text,R.string.exception_parse);
		} catch (Exception e) {
			e.printStackTrace();
			AppLog.d(TAG, e.getMessage());
			ScreenManager.updateUI(uiHandler, R.id.ui_show_text,R.string.exception_timeout);
		}	
		return requestResult;
	}
	
}
