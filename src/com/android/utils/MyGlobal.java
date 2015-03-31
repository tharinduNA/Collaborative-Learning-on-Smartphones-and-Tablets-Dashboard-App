package com.android.utils;

import android.app.Application;

public class MyGlobal extends Application {
	
	private static int CLIENT_PORT = 43301;
	private static String TODAYLESSON = "abc";
	private static String MY_MAC = "mymac";
	
	public static void SetClientPort(int port) {
		CLIENT_PORT = port;
	}
	
	public static int getClientPort() {
		return CLIENT_PORT;
	}

	public static String getTodayLesson() {
		return TODAYLESSON;
	}

	public static void setTodayLesson(String todayLesson) {
		TODAYLESSON = todayLesson;
	}
	
	public static void setMyMac(String myMac) {
		MY_MAC = myMac;
	}
	
	public static String getMyMac() {
		return MY_MAC;
	}
}
