package com.android.dashboardapp;

import java.io.File;
import java.util.Arrays;

import com.android.utils.MyGlobal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.util.Log;

public class LessonSelectDialogFragment extends DialogFragment {

	String[] xmlListWithExtensions;
	private String TAG = "LessonSelectDialogFragment";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		File file = new File(Environment.getExternalStorageDirectory() + "/lessons");
		xmlListWithExtensions = file.list();
		Arrays.sort(xmlListWithExtensions);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.dialog_title_lesson_select)
				.setSingleChoiceItems(xmlListWithExtensions, -1, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Log.i(TAG, "which" + which);
						MyGlobal.setTodayLesson(xmlListWithExtensions[which]);
						dialog.dismiss();
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();					
					}
				});
				
		return builder.create();
	}

}
