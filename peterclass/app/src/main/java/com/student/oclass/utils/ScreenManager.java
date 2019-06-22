package com.student.oclass.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

public class ScreenManager {

	/**
	 * Holds the single instance of a ScrrenManager that is shared by the
	 * process.
	 */
	private static ScreenManager instance;

	public static ScreenManager Instance() {
		if (instance==null) {
			instance=new ScreenManager();
		}
		return instance;
	}

	public ScreenManager() {
		instance = this;
	}

	public static void updateUI(Handler uiHandler, int what) {
		updateUI(uiHandler, what, 0);
	}

	public static void updateUI(Handler uiHandler, int what, int arg1) {
		Message msg = new Message();
		msg.what = what;
		msg.arg1 = arg1;
		if(null != uiHandler){
			uiHandler.sendMessage(msg);
		}
	}

	// 跳转系统的网络设置界面
	public void showNetSetting(Activity activity) {
		Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
		intent.addCategory("android.intent.category.DEFAULT");
		activity.startActivity(intent);
	}

	/**
	 * Show the Main bookcase page when input the right name and password.
	 * 
	 * @param activity
	 */
	public void showMain(Activity activity) {
		/*Intent intent = new Intent(activity, Maintab.class);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		activity.finish();*/
	}

	/**
	 * Show the login page in fade in style.
	 * 
	 * @param activity
	 */
	public void showLogin(Activity activity) {
		/*Intent intent = new Intent(activity, Login.class);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		activity.finish();*/
	}
	
	public void showBookModel(Activity activity) {
		//Intent intent = new Intent(activity, FBReader.class);
		//activity.startActivity(intent);		
	}
	
	public void showTestActivity(Activity activity) {
	//	Intent intent = new Intent(activity, TestActivity.class);
		//activity.startActivity(intent);		
	}
}