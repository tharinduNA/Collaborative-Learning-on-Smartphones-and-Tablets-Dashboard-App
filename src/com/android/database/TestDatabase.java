package com.android.database;

import java.util.List;

import com.android.dashboardapp.MainActivity;

import android.util.Log;

public class TestDatabase {

	private static String TAG = "TestDatabase";

	public static String ClientDetailsDatabase()
	{
		String s="";
		Log.i(TAG , "hh");
		DatabaseHandler db = new DatabaseHandler(MainActivity.appContext);
		Log.i(TAG , "hh");
		List<ClientDetails> clientList = db.getAllClientDetails();
		Log.i(TAG , "hh");
		for(ClientDetails cd:clientList)
		{
			s = s + " " + cd.getID() + " " + cd.getMacAddress() + "\n";
		}
		db.close();
		return s;
	}
	
	public static String FeedbackDatabase()
	{
		String s="";
		DatabaseHandler db = new DatabaseHandler(MainActivity.appContext);
		List<Feedback> feedbackList = db.getAllFeedbacks();
		for(Feedback f:feedbackList)
		{
			s = s + " " + f.getID() + " " + f.getClientId() + " " + f.getLessonName() + " " + f.getType() + "\n";
		}
		db.close();
		return s;
	}
}
