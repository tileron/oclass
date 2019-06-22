package com.student.oclass.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;

/**
 * 图片处理类
 * @author lanyj
 *
 */
@SuppressLint("NewApi") 
public class HImage {
	private static final String TAG = "HImage";
	public static final int LEFT = 0;   
	public static final int RIGHT = 1;   
	public static final int TOP = 3;   
	public static final int BOTTOM = 4;   
			
	/**
	 * 
	 * 把图片转成圆角
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = 90;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	/**
	 * 把图片转成圆角
	 * @param bitmap
	 * @param angle 图角角度 建议0~90
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float angle) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		//final float roundPx = 90;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, angle, angle, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		//BitmapFactory.decodeFile(filePath, options);
		//options.inSampleSize = calculateInSampleSize(options, 480, 800);
		options.inSampleSize = 2;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);// 内存溢出
	}
	
	public static Bitmap getSmallBitmap(String filePath,int width,int height) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options, width, height);
		options.inJustDecodeBounds = false;
		
		return BitmapFactory.decodeFile(filePath, options);// 内存溢出
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
/*	public static String bitmapToString(String filePath) {

		Bitmap bm = getSmallBitmap(filePath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);

	}*/

	/**
	 * 把本地图片压缩后另存到指定目录
	 * 
	 * @param imagePath
	 * @return 压缩后的图片路径
	 */
/*	public static String compressImage(String imagePath, String saveDir) {
		String compressPath = null;
		Bitmap bitmap = getSmallBitmap(imagePath);
		if (bitmap != null) {
			compressPath = saveDir+ UUID.randomUUID()+ imagePath.substring(imagePath.lastIndexOf("."),imagePath.length());
			saveBitmap(bitmap, compressPath);
		}
		return compressPath;

	}*/

	/**
	 * 将bitmap保存到本地
	 * @param mBitmap
	 * @param imagePath
	 * @param imageType 图片类型 PNG,JPG,
	 */
	public static void saveBitmap(Bitmap bitmap, String imagePath,String imageType) {
		try {
			File file = new File(imagePath);
			FileUtil.createDipPath(imagePath);
			FileOutputStream fOut = new FileOutputStream(file);
			if("PNG".equalsIgnoreCase(imageType)){
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			}else if("JPG".equalsIgnoreCase(imageType) || "JPEG".equalsIgnoreCase(imageType)){
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			}else  if("WEBP".equalsIgnoreCase(imageType) ){
				bitmap.compress(Bitmap.CompressFormat.WEBP, 100, fOut);
			}else{
				AppLog.w(TAG, "图片保存失败，无法确定图片类型。类型为：" + imageType);
			}
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			AppLog.e(TAG, e.toString());
		} catch (IOException e) {
			AppLog.e(TAG, e.toString());
		}
		
	}
	
	/**
	 * 将bitmap保存到本地
	 * 
	 * @param mBitmap
	 * @param imagePath
	 */
	public static void saveBitmap(Bitmap bitmap, String imagePath,int s) {
		File file = new File(imagePath);
		FileUtil.createDipPath(imagePath);
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.WEBP, s, fOut);
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

	/**
	 * 根据指定的大小压缩图片
	 * 
	 * @param sourceImagePath
	 * @param outDirectory
	 * @return 压缩后的图片路径, 图片不需要压缩则返回原路径
	 */
	public static  String compressImage(String sourceImagePath, String outDirectory) {
		int maxWidth = 480;
		int maxHeight = 800;
		String compressPath = null;
		BitmapFactory.Options ops = new BitmapFactory.Options();
		ops.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(sourceImagePath, ops);
		double ratio = 1.0;
		if (ops.outWidth > ops.outHeight && ops.outWidth > maxWidth) {
			ratio = ops.outWidth / maxWidth;
		} else if (ops.outHeight > ops.outWidth && ops.outHeight > maxHeight) {
			ratio = ops.outHeight / maxHeight;
		} else {
			return sourceImagePath;
		}
		BitmapFactory.Options newOps = new BitmapFactory.Options();
		newOps.inSampleSize = (int) (ratio + 1);
		newOps.outWidth = (int) (ops.outWidth / ratio);
		newOps.outHeight = (int) (ops.outHeight / ratio);
		Bitmap bitmap = BitmapFactory.decodeFile(sourceImagePath, newOps);
		compressPath = outDirectory+ UUID.randomUUID()+ sourceImagePath.substring(sourceImagePath.lastIndexOf("."),sourceImagePath.length());
		File outFile = new File(compressPath);
		try {
			File parent = outFile.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			outFile.createNewFile();
			OutputStream os = new FileOutputStream(outFile);
			bitmap.compress(CompressFormat.JPEG, 100, os);
			os.close();
			bitmap.recycle();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return compressPath;
	}
	
	/**
	 * 得到网络图片
	 * 
	 * @param url
	 * @param localPath
	 * @return
	 */
	public static Bitmap getBitmap(String url, String localPath) {
		Bitmap bitmap = null;
		File file = new File(localPath);
		if (file.exists()) {
			try {
				FileInputStream fs = new FileInputStream(file);
				bitmap = BitmapFactory.decodeStream(fs);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			bitmap = getHttpBitmap(url);
		}
		return bitmap;

	}
	
	/**
	 * 获取网落图片资源
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);

			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			// 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			// 连接设置获得数据流
			conn.setDoInput(true);
			// 不使用缓存
			conn.setUseCaches(false);
			// 这句可有可无，没有影响
			// conn.connect();
			// 得到数据流
			InputStream is = conn.getInputStream();
			// 解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			// 关闭数据流
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;

	}
	
	/**
	 * 得到本地的Bitmap
	 * 
	 * @param localPath
	 * @return
	 */
	public static Bitmap getLocalBitMap(String localPath) {
		System.out.println("localPath:" + localPath);
		File file = new File(localPath);
		Bitmap bitmap = null;
		if (file.exists()) {
			try {
				FileInputStream fs = new FileInputStream(file);
				bitmap = BitmapFactory.decodeStream(fs);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		return bitmap;
	}
	
	/**
	 * 复制分享图片到本地目录
	 * 
	 * @param context
	 */
	public static boolean copyAppFileToSDCard(Context context, int imgForm,
			String toPath) {
		boolean copyResult = true;
		try {
			// File dir = new File(toPath);
			// 如果/sdcard/dictionary目录中存在，创建这个目录

			// 如果在/sdcard/dictionary目录中不存在
			// dictionary.db文件，则从res\raw目录中复制这个文件到
			// SD卡的目录（/sdcard/dictionary）

			// 获得封装dictionary.db文件的InputStream对象
			FileUtil.createDipPath(toPath);
			InputStream is = context.getResources().openRawResource(imgForm);
			FileOutputStream fos = new FileOutputStream(new File(toPath));
			byte[] buffer = new byte[8192];
			int count = 0;
			// 开始复制dictionary.db文件
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			fos.close();
			is.close();
			// setTitle("copy DB");

		} catch (Exception e) {
			copyResult = false;
		}
		return copyResult;
	}
	
	/**
	 * 批量图片压缩到指定目录
	 * @param images
	 * @param compressDir
	 * @return
	 */
	public static ArrayList<String> compressImage(List<String> images,String compressDir){
		if(images == null)
			return null;
		//图片最大上传大小，300KB
		int maxSize = 1000*1024;
		ArrayList<String> resultImage = new ArrayList<String>();
		for (int i = 0; i < images.size(); i++) {
			String imagePath = images.get(i);
			File file = new File(imagePath);
			if(file.exists()){
				long length = file.length();
				//int compressPercent = 100;
				if(length > maxSize){
					BitmapFactory.Options newOpts = new BitmapFactory.Options();  
			        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
			        newOpts.inJustDecodeBounds = true;  
			        Bitmap bitmap = BitmapFactory.decodeFile(imagePath,newOpts);//此时返回bm为空  
			        newOpts.inJustDecodeBounds = false;  
			        int w = newOpts.outWidth;  
			        int h = newOpts.outHeight;  
			        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
			        float hh = 800f;//这里设置高度为800f  
			        float ww = 480f;//这里设置宽度为480f  
			        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
			        int be = 1;//be=1表示不缩放  
			        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
			            be = (int) (newOpts.outWidth / ww);  
			        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
			            be = (int) (newOpts.outHeight / hh);  
			        }  
			        if (be <= 0)  
			            be = 1;  
			        newOpts.inSampleSize = be;//设置缩放比例  
			        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
			        bitmap = BitmapFactory.decodeFile(imagePath, newOpts);  
					
					//保存图片
					String fileName  = file.getName();
					String newFileName = null;
					if(fileName.indexOf(".") > 0)
						newFileName = compressDir + UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."),fileName.length());
					else
						newFileName = compressDir + UUID.randomUUID().toString() +".jpg";
					
					//String saveImagePath =compressDir +file.getName();
					File saveImagefile = new File(newFileName);
					FileUtil.createDipPath(newFileName);
					FileOutputStream fOut = null;
					try {
						fOut = new FileOutputStream(saveImagefile);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					file = new File(imagePath);
					length = file.length();
					//compressPercent =(int)(length /maxSize);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
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
					resultImage.add(newFileName);
				}else{
					String strNewName = compressDir + UUID.randomUUID().toString() + imagePath.substring(imagePath.lastIndexOf("."), imagePath.length());
					FileUtil.copyFile(imagePath, strNewName);
					resultImage.add(strNewName);
				}
			}
		}
		return resultImage;
	}
	
	/**
	 * Bitmap图片压缩
	 * @param image
	 * @return
	 */
	public static Bitmap comp(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();		
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if( baos.toByteArray().length / 1024> 512) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出	
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;//这里设置高度为800f
		float ww = 480f;//这里设置宽度为480f
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap,100);//压缩好比例大小后再进行质量压缩
	}
	
	/**
	 * 本地图片压缩
	 * @param srcPath
	 * @return
	 */
	public static Bitmap getimage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;//就是说如果该值设为true那么将不返回实际的bitmap对象，不给其分配内存空间但是可以得到一些解码边界信息即图片大小等信息。因此我们可以通过设置inJustDecodeBounds为true，获取到outHeight(图片原始高度)和 outWidth(图片的原始宽度)，然后计算一个inSampleSize(缩放值)，就可以取图片了，这里要注意的是，inSampleSize 可能等于0，必须做判断。也就是说先将Options的属性inJustDecodeBounds设为true，先获取图片的基本大小信息数据(信息没有保存在bitmap里面，而是保存在options里面)，通过options.outHeight和 options. outWidth获取的大小信息以及自己想要到得图片大小计算出来缩放比例inSampleSize，然后紧接着将inJustDecodeBounds设为false，就可以根据已经得到的缩放比例得到自己想要的图片缩放图
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;//这里设置高度为800f
		float ww = 480f;//这里设置宽度为480f
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例//图片长宽各缩小be分之一
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap,100);//压缩好比例大小后再进行质量压缩
	}
	
	/**
	 * 图片质量压缩
	 * @param image
	 * @size 图片大小（kb）
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image, int size) {
		try{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( baos.toByteArray().length / 1024 > size) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩		
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;//每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 旋转图片
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		if(bitmap == null)
			return null;
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
	
	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	
	/**
	 * 读取本地的图片得到缩略图，如图片需要旋转则旋转。
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public static  Bitmap getLocalThumbImg(String path,float width,float height){
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path,newOpts);//此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > width) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / width);
		} else if (w < h && h > height) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / height);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(path, newOpts);
		bitmap = compressImage(bitmap,100);//压缩好比例大小后再进行质量压缩
		int degree = readPictureDegree(path);
		bitmap = rotaingImageView(degree, bitmap);
		return bitmap;
	}
	
	/**
	 * 图片去色,返回灰度图片
	 * 
	 * @param bmpOriginal
	 *            传入的图片
	 * @return 去色后的图片
	 * @author lanyj
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();
		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}
	
	/**
	 * 去色同时加圆角
	 * 
	 * @param bmpOriginal
	 *            原图
	 * @param pixels
	 *            圆角弧度
	 * @return 修改后的图片
	 * @author lanyj
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
		return toRoundCorner(toGrayscale(bmpOriginal), pixels);
	}

	/**
	 * 把图片变成圆角
	 * 
	 * @param bitmap
	 *            需要修改的图片
	 * @param pixels
	 *            圆角的弧度
	 * @return 圆角图片
	 * @author lanyj
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	
	/**
	* 使圆角功能支持BitampDrawable  
	*   
	* @param bitmapDrawable  
	* @param pixels  
	* @return  
	* @author lanyj
	*/  
	public static BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable, int pixels) {   
	Bitmap bitmap = bitmapDrawable.getBitmap();   
	bitmapDrawable = new BitmapDrawable(toRoundCorner(bitmap, pixels));   
	return bitmapDrawable;   
	} 
	
	/**
	 * 水印
	 * 
	 * @param bitmap
	 * @return
	 * @author lanyj
	 */
	public static Bitmap createBitmapForWatermark(Bitmap src, Bitmap watermark) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		// draw watermark into
		cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newb;
	}
	
	/**
	 * 图片合成
	 * 
	 * @return
	 * @author lanyj
	 */
	public static Bitmap potoMix(int direction, Bitmap... bitmaps) {
		if (bitmaps.length <= 0) {
			return null;
		}
		if (bitmaps.length == 1) {
			return bitmaps[0];
		}
		Bitmap newBitmap = bitmaps[0];
		// newBitmap = createBitmapForFotoMix(bitmaps[0],bitmaps[1],direction);
		for (int i = 1; i < bitmaps.length; i++) {
			newBitmap = createBitmapForFotoMix(newBitmap, bitmaps[i], direction);
		}
		return newBitmap;
	}

	private static Bitmap createBitmapForFotoMix(Bitmap first, Bitmap second,
			int direction) {
		if (first == null) {
			return null;
		}
		if (second == null) {
			return first;
		}
		int fw = first.getWidth();
		int fh = first.getHeight();
		int sw = second.getWidth();
		int sh = second.getHeight();
		Bitmap newBitmap = null;
		if (direction == LEFT) {
			newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, sw, 0, null);
			canvas.drawBitmap(second, 0, 0, null);
		} else if (direction == RIGHT) {
			newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, 0, null);
			canvas.drawBitmap(second, fw, 0, null);
		} else if (direction == TOP) {
			newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, sh, null);
			canvas.drawBitmap(second, 0, 0, null);
		} else if (direction == BOTTOM) {
			newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, 0, null);
			canvas.drawBitmap(second, 0, fh, null);
		}
		return newBitmap;
	}
	
	/**
	 * 读取路径中的图片，然后将其转化为缩放后的bitmap
	 * 
	 * @param path
	 * @author lanyj
	 */
	public static void saveBefore(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高
		Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空
		options.inJustDecodeBounds = false;
		// 计算缩放比
		int be = (int) (options.outHeight / (float) 200);
		if (be <= 0)
			be = 1;
		options.inSampleSize = 2; // 图片长宽各缩小二分之一
		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
		bitmap = BitmapFactory.decodeFile(path, options);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		System.out.println(w + " " + h);
		// savePNG_After(bitmap,path);
		saveJPGE(bitmap, path);
	}
	
	/**
	 * 将两张图片合成，指定小图片的宽和高放在大图片的正中间位置
	 * 
	 * @param bigBitmap背景图片
	 * @param smallBitmap内容图片
	 * @param width内容图片的宽，默认0，表不压缩
	 * @param height内容图片的高，默认0，表不压缩
	 * @param pixels内容图片圆角的度数，默认0
	 * @return
	 * @author lanyj
	 */
	public static Bitmap potoMix(Bitmap bigBitmap, Bitmap smallBitmap, int width, int height, int pixels){
		
		bigBitmap = bigBitmap.copy(Bitmap.Config.ARGB_8888, true);
		smallBitmap = smallBitmap.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(bigBitmap);
		Paint paint = new Paint();
		if(width != 0 && height != 0){
			smallBitmap = HImage.createBitmapBySize(smallBitmap, width, height);
		}
		if(pixels != 0){
			smallBitmap = HImage.toRoundCorner(smallBitmap, pixels);
		}
		int bW = bigBitmap.getWidth();
		int bH = bigBitmap.getHeight();
		int sW = smallBitmap.getWidth();
		int sH = smallBitmap.getHeight();
		canvas.drawBitmap(smallBitmap, (bW - sW)/2, (bH - sH)/2, paint);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return bigBitmap;
	}
	
	/**
	 * 保存图片为PNG
	 * 
	 * @param bitmap
	 * @param name
	 * @author lanyj
	 */
	public static void savePNG(Bitmap bitmap, String name) {
		File file = new File(name);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存图片为JPEG
	 * 
	 * @param bitmap
	 * @param path
	 * @author lanyj
	 */
	public static void saveJPGE(Bitmap bitmap, String path) {
		File file = new File(path);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将Bitmap转换成指定大小
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 * @author lanyj
	 */
	public static Bitmap createBitmapBySize(Bitmap bitmap, int width, int height) {
		return Bitmap.createScaledBitmap(bitmap, width, height, true);
	}

	/**
	 * Drawable 转 Bitmap
	 * 
	 * @param drawable
	 * @return
	 * @author lanyj
	 */
	public static Bitmap drawableToBitmapByBD(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		return bitmapDrawable.getBitmap();
	}

	/**
	 * Bitmap 转 Drawable
	 * 
	 * @param bitmap
	 * @return
	 * @author lanyj
	 */
	public static Drawable bitmapToDrawableByBD(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * byte[] 转 bitmap
	 * 
	 * @param b
	 * @return
	 * @author lanyj
	 */
	public static Bitmap bytesToBimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * bitmap 转 byte[]
	 * 
	 * @param bm
	 * @return
	 * @author lanyj
	 */
	public static byte[] bitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
}
