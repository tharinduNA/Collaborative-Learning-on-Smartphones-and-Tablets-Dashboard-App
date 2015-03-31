package com.android.dashboardapp;

import java.util.List;

import com.android.dashboardapp.R;
import com.android.database.DatabaseHandler;
import com.android.database.Messages;
import com.android.utils.MyGlobal;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessagesFragment extends Fragment {

	private LinearLayout lLayoutMessagesView;
	private LinearLayout lLayoutMessages;
	private EditText etMessages;
	private Button btnSend;
	private String TAG = "MessagesFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_messages, container,false);
		lLayoutMessagesView =  (LinearLayout) rootView.findViewById(R.id.lLayout_fragment_messages_scroll);
		lLayoutMessages = (LinearLayout) rootView.findViewById(R.id.lLayout_fragment_messages);
		etMessages = (EditText) rootView.findViewById(R.id.etMessages);
		btnSend = (Button) rootView.findViewById(R.id.btnSend);
		btnSend.setBackgroundResource(R.drawable.btn_green_selector);
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!etMessages.getText().toString().equalsIgnoreCase(""))
				{
					DatabaseHandler db = new DatabaseHandler(MainActivity.appContext);
					
					Messages messages = new Messages();
					messages.setMessage(etMessages.getText().toString());
					messages.setMacAddress(MyGlobal.getMyMac());
					db.addMessage(messages);
					db.close();
				}
				etMessages.setText("");
			}
		});
		Log.i(TAG , "studentfeedback1 message view");
		DatabaseHandler db = new DatabaseHandler(MainActivity.appContext);
		List<Messages> messageList = db.getAllMessages();
		for(Messages m: messageList)
		{
			TextView tv0 = new TextView(getActivity());
			LayoutParams params0 = new LayoutParams(
			        LayoutParams.WRAP_CONTENT,      
			        50
			);
			tv0.setLayoutParams(params0);
			lLayoutMessagesView.addView(tv0);
			
			TextView tv = new TextView(getActivity());
			LayoutParams params = new LayoutParams(
			        LayoutParams.MATCH_PARENT,      
			        LayoutParams.WRAP_CONTENT
			);
			tv.setLayoutParams(params);
			tv.setTextSize(18);
						
			if(m.getMacAddress().equalsIgnoreCase(MyGlobal.getMyMac()))
			{
				tv.setGravity(Gravity.LEFT);
				tv.setTextColor(getResources().getColor(R.color.chat_my));
			}else
			{
				tv.setGravity(Gravity.RIGHT);
				tv.setTextColor(getResources().getColor(R.color.chat_other));
			}
			
			tv.setText(m.getMessage());
			lLayoutMessagesView.addView(tv);
			Log.i(TAG , "studentfeedback1 message view");
			
		}
		return rootView;
	}

}
