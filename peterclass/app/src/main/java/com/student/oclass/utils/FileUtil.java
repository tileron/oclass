package com.student.oclass.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

/**
 * 文件处理
 * @author lanyj
 *
 */
public class FileUtil {

	private static final String TAG = "FileUtil";
	/**
	 * 根据文件路径 递归创建文件
	 * 
	 * @param file
	 */
	public static void createDipPath(String file) {
		String parentFile = file.substring(0, file.lastIndexOf("/"));
		File file1 = new File(file);
		File parent = new File(parentFile);
		if (!file1.exists()) {
			parent.mkdirs();
			try {
				file1.createNewFile();
				AppLog.i(TAG,"Create new file :" + file);
			} catch (IOException e) {
				AppLog.e(TAG, e.getMessage());
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 */
	public static boolean deleteFile(String path) {
		boolean bl;
		File file = new File(path);
		if (file.exists()) {
			bl = file.delete();
		} else {
			bl = false;
		}
		return bl;
	}
	/**
	 * 将bitmap保存到本地
	 * 
	 * @param mBitmap
	 * @param imagePath
	 */
	@SuppressLint("NewApi")
	public static void saveBitmap(Bitmap bitmap, String imagePath,int s) {
		File file = new File(imagePath);
		createDipPath(imagePath);
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(imagePath.toLowerCase().endsWith(".png")){
			bitmap.compress(Bitmap.CompressFormat.PNG, s, fOut);
		}else if(imagePath.toLowerCase().endsWith(".jpg")){
			bitmap.compress(Bitmap.CompressFormat.JPEG, s, fOut);
		}else{
			bitmap.compress(Bitmap.CompressFormat.WEBP, s, fOut);
		}
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 复制文件
		public static void copyFile(String sourcePath, String toPath) {
			File sourceFile = new File(sourcePath);
			File targetFile = new File(toPath);
			createDipPath(toPath);
			try {
				BufferedInputStream inBuff = null;
				BufferedOutputStream outBuff = null;
				try {
					// 新建文件输入流并对它进行缓冲
					inBuff = new BufferedInputStream(
							new FileInputStream(sourceFile));

					// 新建文件输出流并对它进行缓冲
					outBuff = new BufferedOutputStream(new FileOutputStream(
							targetFile));

					// 缓冲数组
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = inBuff.read(b)) != -1) {
						outBuff.write(b, 0, len);
					}
					// 刷新此缓冲的输出流
					outBuff.flush();
				} finally {
					// 关闭流
					if (inBuff != null)
						inBuff.close();
					if (outBuff != null)
						outBuff.close();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 复制文件
		public static void copyFile(File sourceFile, File targetFile) {

			try {
				BufferedInputStream inBuff = null;
				BufferedOutputStream outBuff = null;
				try {
					// 新建文件输入流并对它进行缓冲
					inBuff = new BufferedInputStream(
							new FileInputStream(sourceFile));

					// 新建文件输出流并对它进行缓冲
					outBuff = new BufferedOutputStream(new FileOutputStream(
							targetFile));

					// 缓冲数组
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = inBuff.read(b)) != -1) {
						outBuff.write(b, 0, len);
					}
					// 刷新此缓冲的输出流
					outBuff.flush();
				} finally {
					// 关闭流
					if (inBuff != null)
						inBuff.close();
					if (outBuff != null)
						outBuff.close();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



	public String getMIMEType(File file) {
		final String[][] MIME_MapTable={
				//{后缀名，    MIME类型}
				{".3gp",    "video/3gpp"},
				{".apk",    "application/vnd.android.package-archive"},
				{".asf",    "video/x-ms-asf"},
				{".avi",    "video/x-msvideo"},
				{".bin",    "application/octet-stream"},
				{".bmp",      "image/bmp"},
				{".c",        "text/plain"},
				{".class",    "application/octet-stream"},
				{".conf",    "text/plain"},
				{".cpp",    "text/plain"},
				{".doc",    "application/msword"},
				{".exe",    "application/octet-stream"},
				{".gif",    "image/gif"},
				{".gtar",    "application/x-gtar"},
				{".gz",        "application/x-gzip"},
				{".h",        "text/plain"},
				{".htm",    "text/html"},
				{".html",    "text/html"},
				{".jar",    "application/java-archive"},
				{".java",    "text/plain"},
				{".jpeg",    "image/jpeg"},
				{".jpg",    "image/jpeg"},
				{".js",        "application/x-javascript"},
				{".log",    "text/plain"},
				{".m3u",    "audio/x-mpegurl"},
				{".m4a",    "audio/mp4a-latm"},
				{".m4b",    "audio/mp4a-latm"},
				{".m4p",    "audio/mp4a-latm"},
				{".m4u",    "video/vnd.mpegurl"},
				{".m4v",    "video/x-m4v"},
				{".mov",    "video/quicktime"},
				{".mp2",    "audio/x-mpeg"},
				{".mp3",    "audio/x-mpeg"},
				{".mp4",    "video/mp4"},
				{".mpc",    "application/vnd.mpohun.certificate"},
				{".mpe",    "video/mpeg"},
				{".mpeg",    "video/mpeg"},
				{".mpg",    "video/mpeg"},
				{".mpg4",    "video/mp4"},
				{".mpga",    "audio/mpeg"},
				{".msg",    "application/vnd.ms-outlook"},
				{".ogg",    "audio/ogg"},
				{".pdf",    "application/pdf"},
				{".png",    "image/png"},
				{".pps",    "application/vnd.ms-powerpoint"},
				{".ppt",    "application/vnd.ms-powerpoint"},
				{".prop",    "text/plain"},
				{".rar",    "application/x-rar-compressed"},
				{".rc",        "text/plain"},
				{".rmvb",    "audio/x-pn-realaudio"},
				{".rtf",    "application/rtf"},
				{".sh",        "text/plain"},
				{".tar",    "application/x-tar"},
				{".tgz",    "application/x-compressed"},
				{".txt",    "text/plain"},
				{".wav",    "audio/x-wav"},
				{".wma",    "audio/x-ms-wma"},
				{".wmv",    "audio/x-ms-wmv"},
				{".wps",    "application/vnd.ms-works"},
				//{".xml",    "text/xml"},
				{".xml",    "text/plain"},
				{".z",        "application/x-compress"},
				{".zip",    "application/zip"},
				{"",        "*/*"}
		};
		String type="*/*";
		String fName = file.getName();
		//获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if(dotIndex < 0)
			return type;
		/* 获取文件的后缀名 */
		String fileType = fName.substring(dotIndex,fName.length()).toLowerCase();
		if(fileType == null || "".equals(fileType))
			return type;
		//在MIME和文件类型的匹配表中找到对应的MIME类型。
		for(int i=0;i<MIME_MapTable.length;i++){
			if(fileType.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

}
