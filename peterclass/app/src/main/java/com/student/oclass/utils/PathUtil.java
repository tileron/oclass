package com.student.oclass.utils;

import android.os.Environment;

public class PathUtil {
	public static final String ROOT = Environment.getExternalStorageDirectory().getPath() + "/oclass/";
	public static final String CAMERA = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/";
	public static final String CACHE_IMG = ROOT + "cache/images/";
	public static final String CACHE_SHARE = ROOT + "cache/share/";
	 /**
     * 应用日志目录文件
     */
    public static String APP_LOG_PATH = ROOT + "log/";

	/** 
	 * 日志文件路径 
	 */
    public static String LOGFILE = APP_LOG_PATH + "log.txt";
}
