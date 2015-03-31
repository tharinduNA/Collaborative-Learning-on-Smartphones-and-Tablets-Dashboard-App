package com.android.dashboardapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.android.navdrawer.NavDrawerItem;
import com.android.navdrawer.NavDrawerListAdapter;
import com.android.server.ServerService;
import com.android.utils.MyGlobal;
import com.android.dashboardapp.R;
import com.android.database.ClientDetails;
import com.android.database.DatabaseHandler;
import com.android.database.TestDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	private static final String TAG = "MainActivity";
	private String[] mDrawerTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mListView;
	private Fragment mainFragment;
	public static Context appContext;
	private TypedArray mDrawerIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.i(TAG, "mainaccctivity");
		startService(new Intent(getBaseContext(), ServerService.class));
		Log.i(TAG, "mainaccctivity");
		mainFragment = new MainFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, mainFragment).commit();

		mDrawerTitles = getResources().getStringArray(R.array.drawer_list_array);
		mDrawerIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mListView = (ListView) findViewById(R.id.drawer_list);
		
		//here mac can only pull if wifi is on
		WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		
		if(manager.isWifiEnabled()) {
		    // WIFI ALREADY ENABLED. GRAB THE MAC ADDRESS HERE
		    WifiInfo info = manager.getConnectionInfo();
		    String address = info.getMacAddress();
		    MyGlobal.setMyMac(address);
		} else {
		    // ENABLE THE WIFI FIRST
			manager.setWifiEnabled(true);

		    // WIFI IS NOW ENABLED. GRAB THE MAC ADDRESS HERE
		    WifiInfo info = manager.getConnectionInfo();
		    String address = info.getMacAddress();
		    MyGlobal.setMyMac(address);
		}
				
		//initializing navigation drawer
        navDrawerItems = new ArrayList<NavDrawerItem>();
 
        // adding nav drawer items to array
        
        // Home
        navDrawerItems.add(new NavDrawerItem(mDrawerTitles[0], mDrawerIcons.getResourceId(0, -1)));
        // lesson
        navDrawerItems.add(new NavDrawerItem(mDrawerTitles[1], mDrawerIcons.getResourceId(1, -1)));
        //messages
        navDrawerItems.add(new NavDrawerItem(mDrawerTitles[2], mDrawerIcons.getResourceId(2, -1)));
         
 
        // Recycle the typed array
        mDrawerIcons.recycle();
 
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
        mListView.setAdapter(adapter);
		//dcdfdfdfdf
		
		//initialize database values
		appContext = getApplicationContext(); //this is used in TestDatabase and SocketServerReplyThread classes
		DatabaseHandler db = new DatabaseHandler(this);
		String client = "50:FC:9F:AC:CC:B4";
		int checkBit = 0;
		List<ClientDetails> cdList = db.getAllClientDetails();
		for(ClientDetails c:cdList)
		{
			if(client.equalsIgnoreCase(c.getMacAddress()))
			{
				checkBit =1;
				break;
			}
		}
		if(checkBit == 0)
		{
			db.addClientDetails(new ClientDetails("50:FC:9F:AC:CC:B4"));
		}
		//TestDatabase tb = new TestDatabase();
		String sq = TestDatabase.ClientDetailsDatabase();
		Log.i(TAG, "wwww " + sq);
		
		String sa = TestDatabase.FeedbackDatabase();
		Log.i(TAG, "wwww " + sa);
		//--------------------------------------
		
		
		//removing extentions from file names
		File file = new File(Environment.getExternalStorageDirectory() + "/lessons");
		String[] xmlListWithExtensions = file.list();
		ArrayList<String> xmlList = new ArrayList<String>();
		String[] xmlTemp;
		for(String s:xmlListWithExtensions){
			xmlTemp = s.split("\\.");
			xmlList.add(xmlTemp[0]);
		}
		Collections.sort(xmlList);
	
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				displayView(position);
			}
		});
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private void displayView(int position){
		Fragment fragment = null;
		switch(position){
		case 0:
			fragment = new MainFragment();
			break;
		case 1:
			fragment = new LessonsFragment();
			break;
		case 2:
			fragment = new MessagesFragment();
			break;
		}
		if(fragment != null){
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			setTitle(mDrawerTitles[position]);
			mDrawerLayout.closeDrawer(mListView);
			
		}
	}

}
