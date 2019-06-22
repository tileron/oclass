package com.student.oclass.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司
 * @author lanyj
 */
public class HToast {
	protected static final String TAG = "CustomToast";
	public static Toast toast;
	/**
	 * 信息提示
	 * 
	 * @param context
	 * @param content
	 */
	public static void makeToast(Context context, String content) {
		if(toast != null)
			toast.cancel();
		toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
		toast.show();
	}

	public static void showShortText(Context context, int resId) {
		try {
			if(toast != null)
				toast.cancel();
			toast = Toast.makeText(context, context.getString(resId),Toast.LENGTH_SHORT);
			toast.show();
		} catch (Exception e) {
			AppLog.e(TAG,e.getMessage());
		}
	}
	public static void showShortText(Context context, CharSequence text) {
		if(toast != null)
			toast.cancel();
		toast = Toast.makeText(context, text,Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public static void showLongText(Context context, int resId) {
		try {
			if(toast != null)
				toast.cancel();
			toast = Toast.makeText(context, context.getString(resId),Toast.LENGTH_LONG);
			toast.show();
			
		} catch (Exception e) {
			AppLog.e(TAG,e.getMessage());
		}
	}

	public static void showLongText(Context context, CharSequence text) {
		if(toast != null)
			toast.cancel();
		toast = Toast.makeText(context, text,Toast.LENGTH_LONG);
		toast.show();
	}
}
