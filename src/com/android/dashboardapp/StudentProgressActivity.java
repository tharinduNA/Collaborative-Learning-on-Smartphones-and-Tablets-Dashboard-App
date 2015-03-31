package com.android.dashboardapp;

import java.util.ArrayList;
import java.util.List;

import com.android.database.ClientDetails;
import com.android.database.DatabaseHandler;
import com.android.database.Feedback;
import com.android.utils.MyGlobal;
import com.android.utils.XmlFileUtil;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StudentProgressActivity extends ActionBarActivity {

	ArrayList<String[]> tagsData;
	ArrayList<String[]> mcqQuestions;
	String TAG = "StudentProgressActivity";
	private int progressStatus = 0;
	private Handler handler = new Handler();
	String macAddress, feedbackLesson;
	int clientDetailsId, feedbackMacId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_student_progress);
		
		setTitle("Students' progress");
		
		Log.i(TAG, "nn");
		String todayLessonName = MyGlobal.getTodayLesson();
		int totalFeedbacks = XmlFileUtil.getTotalFeedbacksForTodayLesson();
		
		ViewGroup lLayoutStudentProgress = (ViewGroup)findViewById(R.id.lLayoutStudentProgress);
         
        //lLayoutStudentProgress.addView(layout_student_progress);
		
        DatabaseHandler db = new DatabaseHandler(this);
        
        List<ClientDetails> clientDetailsList = db.getAllClientDetails();
		for(ClientDetails cd: clientDetailsList)
		{
			Log.i(TAG, "nn");
			macAddress = cd.getMacAddress();
			clientDetailsId = cd.getID();
			List<Feedback> feedbackList = db.getAllFeedbacksForMacId(clientDetailsId);
			int count = 0;
			for(Feedback f: feedbackList)
			{
				Log.i(TAG, "nn1");
				feedbackLesson = f.getLessonName();
				feedbackMacId = f.getClientId();
				if(clientDetailsId == feedbackMacId && todayLessonName.equalsIgnoreCase(feedbackLesson))
				{
					count++;
				}
			}
			Log.i(TAG, "nn2 count" + count + " " + feedbackLesson + " " + totalFeedbacks);
			View layout_student_progress = LayoutInflater.from(this).inflate(R.layout.layout_student_progress, null);
			TextView tvClientName = (TextView) layout_student_progress.findViewById(R.id.tvClientName);
			TextView tvProgressText = (TextView) layout_student_progress.findViewById(R.id.tvProgressText);
			ProgressBar pbShowProgress = (ProgressBar) layout_student_progress.findViewById(R.id.pbShowProgress);
			lLayoutStudentProgress.addView(layout_student_progress);
			addHorizontalDivider(lLayoutStudentProgress);
			
			tvClientName.setText(macAddress);
			
			if(count == totalFeedbacks)
			{
				tvProgressText.setText("100%");
				pbShowProgress.setProgress(100);
			}else
			{
				double percentage = ((double)count/(double)totalFeedbacks) * 100;
				Log.i(TAG, "per " + percentage);
				int result =  (int) Math.ceil(percentage);
				tvProgressText.setText(result + "%");
				pbShowProgress.setProgress(result);
			}
			
			count = 0;
			
		}
        
		db.close();
		
	}

	private void addHorizontalDivider(ViewGroup lLayoutStudentProgress) {
		// TODO Auto-generated method stub
		TextView tv1 = new TextView(this);
		tv1.setTextAppearance(this, android.R.style.TextAppearance_Small);
		tv1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 10));
		lLayoutStudentProgress.addView(tv1);
		
		View v = new View(this);
		v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 2));
		v.setBackgroundColor(getResources().getColor(R.color.gray_divider));
		lLayoutStudentProgress.addView(v);
		
		TextView tv2 = new TextView(this);
		tv2.setTextAppearance(this, android.R.style.TextAppearance_Small);
		tv2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 10));
		lLayoutStudentProgress.addView(tv2);
	}

}
