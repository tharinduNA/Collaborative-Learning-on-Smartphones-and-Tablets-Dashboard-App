package com.android.dashboardapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import com.android.dashboardapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class LessonsFragment extends Fragment {

	LinearLayout lLayout;
	View rootView;
	private ArrayList<String> xmlList;

	private void addLessons() {
		// TODO Auto-generated method stub
		//get file names and removing extentions from file names
		File file = new File(Environment.getExternalStorageDirectory() + "/lessons");
		String[] xmlListWithExtensions = file.list();
		xmlList = new ArrayList<String>();
		String[] xmlTemp;
		for(String s:xmlListWithExtensions){
			xmlTemp = s.split("\\.");
			xmlList.add(xmlTemp[0]);
		}
		Collections.sort(xmlList);
		for(final String s:xmlList){
			Button button = new Button(getActivity());
			
			LayoutParams params = new LayoutParams(
			        LayoutParams.MATCH_PARENT,      
			        120
			);
			params.setMargins(5, 10, 5, 0);
			button.setLayoutParams(params);
			button.setBackgroundResource(R.drawable.btn_green_selector);
			button.setText(s);
			button.setGravity(Gravity.CENTER);
			button.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
			button.setTextColor(getResources().getColor(R.color.white));
			button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), LessonViewActivity.class);
					intent.putExtra("lessonName", s);
					startActivity(intent);
				}
			});
			lLayout.addView(button);
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Context context = getActivity();
		//LinearLayout lLayout = new LinearLayout(con); 
		View rootView = inflater.inflate(R.layout.fragment_lessons, container,false);
		lLayout = (LinearLayout) rootView.findViewById(R.id.lLayout_fragment_lesson);
		addLessons();
		return rootView;
	}


}
