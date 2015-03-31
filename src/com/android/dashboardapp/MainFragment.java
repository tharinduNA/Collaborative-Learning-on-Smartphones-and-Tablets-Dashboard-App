package com.android.dashboardapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainFragment extends Fragment {
	LinearLayout lLayout;
	Button todayLesson, studentProgress;
	private String TAG = "MainFragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_main, container,false);
		lLayout = (LinearLayout) rootView.findViewById(R.id.llayout_fragment_main);
		todayLesson = (Button) rootView.findViewById(R.id.btnTodayLesson);
		studentProgress = (Button) rootView.findViewById(R.id.btnStudentProgress);
		
		todayLesson.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(TAG, "ff");
				DialogFragment lessonSelectDialogFragment = new LessonSelectDialogFragment();
				Log.i(TAG, "ff");
				lessonSelectDialogFragment.show(getFragmentManager(), "selectLesson");
				Log.i(TAG, "ff");
			}
		});
		
		studentProgress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), StudentProgressActivity.class);
				startActivity(intent);
			}
		});
		
		return rootView;
	}


}
