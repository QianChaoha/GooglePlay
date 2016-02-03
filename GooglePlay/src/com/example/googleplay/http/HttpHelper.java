package com.example.googleplay.http;

public class HttpHelper {
	public static final String URL = "http://192.168.1.182:8090/";
	public static final String HOME_URL =URL+"home?index=";//http://127.0.0.1:8090/home?index=0
	public static final String APP_URL =URL+"app?index=0";
	public static final String GAME_URL =URL+"game?index=0";
	public static final String SUBJECT_URL =URL+"subject?index=";
	public static final String HOME_PICTURE_URL =URL+"image?name=";
	public static final String CATEGORY_URL =URL+"category?index=0";
	public static final String TOP_URL =URL+"hot";
	public static String getDetailUrl(int index,String packageName){
		return URL+"detail?index="+index+"&packageName="+packageName;
	}
}
