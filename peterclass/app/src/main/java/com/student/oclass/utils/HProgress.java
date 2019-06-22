package com.student.oclass.utils;

import android.app.ProgressDialog;
import android.content.Context;


public class HProgress {
	private static ProgressDialog dialog = null;

	public static void show(Context context, String message) {
		try {
			if (dialog != null)
				dialog.dismiss();
			dialog = new ProgressDialog(context);
			// dialog.setCancelable(false);
			if (message != null) {
				dialog.setMessage(message);
			} else {
				dialog.setMessage("加载中...");
			}
			dialog.show();
		} catch (Exception e) {

		}
	}

	public static void dismiss() {
		try {
			if (dialog != null)
				dialog.dismiss();
		} catch (Exception e) {

		}
	}
}
