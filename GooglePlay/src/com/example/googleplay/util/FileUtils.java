package com.example.googleplay.util;import java.io.BufferedReader;import java.io.BufferedWriter;import java.io.File;import java.io.FileInputStream;import java.io.FileReader;import java.io.FileWriter;import java.io.IOException;import android.annotation.SuppressLint;import android.annotation.TargetApi;import android.content.Context;import android.os.Build;import android.os.Environment;import android.os.StatFs;import android.os.Build.VERSION_CODES;public class FileUtils {	public static final String CACHE = "cache";	public static final String ICON = "icon";	public static final String ROOT = "googleplay";	/**	 * 获取图片的缓存的路径	 * 	 * @return	 */	public static File getIconDir(Context context) {		return getDiskCacheDir(context, ICON);	}	/**	 * 获取缓存路径	 * 	 * @return	 */	public static File getCacheDir(Context context) {		return getDiskCacheDir(context, ROOT);	}	public static File getDiskCacheDir(Context context, String uniqueName) {		boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);		 String cachePath = null;		if (externalStorageAvailable && context.getExternalCacheDir() != null) {			cachePath = context.getExternalCacheDir().getPath();		} else if (context.getCacheDir() != null) {			cachePath = context.getCacheDir().getPath();		}		File file = new File(cachePath + File.separator + uniqueName);		if (!file.exists() || !file.isDirectory()) {			file.mkdirs();// 创建文件夹		}		return file;	}	public static File getDir(Context context, String cache) {		StringBuilder path = new StringBuilder();		if (isSDAvailable()) {			path.append(Environment.getExternalStorageDirectory().getAbsolutePath());			path.append(File.separator);// '/'			path.append(ROOT);// /mnt/sdcard/GooglePlay			path.append(File.separator);			path.append(cache);// /mnt/sdcard/GooglePlay/cache		} else {			File filesDir = context.getCacheDir(); // cache getFileDir file			path.append(filesDir.getAbsolutePath());//			// /data/data/com.itheima.googleplay/cache			path.append(File.separator);// /data/data/com.itheima.googleplay/cache/			path.append(cache);// /data/data/com.itheima.googleplay/cache/cache		}		File file = new File(path.toString());		if (!file.exists() || !file.isDirectory()) {			file.mkdirs();// 创建文件夹		}		return file;	}	private static boolean isSDAvailable() {		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {			return true;		} else {			return false;		}	}	public static byte[] readFile(String fileName) {		FileInputStream fin = null;		try {			File file = new File(fileName);			fin = new FileInputStream(file);			int length = fin.available();			byte[] buffer = new byte[length];			fin.read(buffer);			fin.close();			return buffer;		} catch (Exception e) {			e.printStackTrace();		} finally {			try {				if (fin != null) {					fin.close();				}			} catch (Exception e) {			}		}		return null;	}	/**	 * 将数据存储在本地	 * 	 * @param json	 * @param url	 */	public static void saveLocal(String json, String url, Context context) {		BufferedWriter bw = null;		try {			File dir = FileUtils.getCacheDir(context);			// 在第一行写一个过期时间			File file = new File(dir, Md5Utils.md5(url)); // /mnt/sdcard/googlePlay/cache/home_0			FileWriter fw = new FileWriter(file);			bw = new BufferedWriter(fw);			bw.write(json);// 把整个json文件保存起来			bw.flush();		} catch (Exception e) {			e.printStackTrace();		} finally {			try {				if (bw != null) {					bw.close();				}			} catch (IOException e) {				e.printStackTrace();			}		}	}	/**	 * 加载本地数据	 * 	 * @param url	 * @return	 */	public static String loadLocal(String url, Context context) {		File dir = FileUtils.getCacheDir(context);// 获取缓存所在的文件夹		File file = new File(dir, Md5Utils.md5(url));		FileReader fr = null;		BufferedReader br = null;		try {			fr = new FileReader(file);			br = new BufferedReader(fr);			String str = null;			StringBuilder builder = new StringBuilder();			while ((str = br.readLine()) != null) {				builder.append(str);			}			return builder.toString();		} catch (Exception e) {			e.printStackTrace();			return null;		} finally {			if (br != null) {				try {					br.close();				} catch (IOException e) {					e.printStackTrace();				}			}			if (fr != null) {				try {					fr.close();				} catch (IOException e) {					e.printStackTrace();				}			}		}	}	@TargetApi(VERSION_CODES.GINGERBREAD)	public static long getUsableSpace(File path) {		if (Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {			return path.getUsableSpace();		}		final StatFs stats = new StatFs(path.getPath());		return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();	}	/**	 * Check if external storage is built-in or removable.	 * 	 * @return True if external storage is removable (like an SD card), false	 *         otherwise.	 */	@SuppressLint("NewApi")	public static boolean isExternalStorageRemovable() {		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {			return Environment.isExternalStorageRemovable();		}		return true;	}	/**	 * Get the external app cache directory.	 * 	 * @param context	 *            The context to use	 * @return The external cache dir	 */	@SuppressLint("NewApi")	public static File getExternalCacheDir(Context context) {		if (hasExternalCacheDir()) {			return context.getExternalCacheDir();		}		// Before Froyo we need to construct the external cache dir ourselves		final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";		return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);	}	/**	 * Check if OS version has built-in external cache dir method.	 * 	 * @return	 */	public static boolean hasExternalCacheDir() {		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;	}}