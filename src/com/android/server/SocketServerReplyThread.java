package com.android.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import com.android.dashboardapp.MainActivity;
import com.android.database.ClientDetails;
import com.android.database.DatabaseHandler;
import com.android.database.Feedback;
import com.android.database.Messages;
import com.android.utils.MyGlobal;
import com.android.utils.XmlFileUtil;

import android.os.Environment;
import android.util.Log;

public class SocketServerReplyThread extends Thread{
	
	private Socket hostThreadSocket;
	private String TAG = "SocketServerReplyThread";
	private String response = "";
	public SocketServerReplyThread(Socket socket) {
		hostThreadSocket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
//		super.run();
//		OutputStream outputStream;
		try {
			Log.i(TAG , "yeeeehaaah");
			DataOutputStream dataoutputStream = new DataOutputStream(hostThreadSocket.getOutputStream());
			DataInputStream dataInputStream = new DataInputStream(hostThreadSocket.getInputStream());
			dataoutputStream.writeUTF("connection_success"); //sending response for client socket request
			
			String requestCode = dataInputStream.readUTF(); //get client socket request code
			String[] splittedRequestcode = requestCode.split(" ");
			if(splittedRequestcode[0].equalsIgnoreCase("TodayLessonRequest"))
			{
				dataoutputStream.writeUTF(MyGlobal.getTodayLesson()); //sending todaylesson. Today lesson is global variable and it should set by teachers app
				Log.i(TAG , "yeeeehaaah" + requestCode);
				Log.i(TAG , "yeeeehaaah file start" + requestCode);
				String todayLesson = MyGlobal.getTodayLesson();
				File myFile = new File(Environment.getExternalStorageDirectory()+ "/lessons/" +todayLesson + "/" + todayLesson + ".rar");//"/lessons/lesson3/" +in+  
				byte[] mybytearray = new byte[(int) myFile.length()];
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
				OutputStream os = hostThreadSocket.getOutputStream();
				
				for(int i=0; i<myFile.length();i=i+1024)
				{
					int j = bis.read(mybytearray, 0, 1024);
					os.write(mybytearray, 0, j);
				}
				
				Log.i(TAG , "yeeeehaaah file end" + requestCode);
				os.flush();
			}else
				if(splittedRequestcode[0].equalsIgnoreCase("StudentFeedback"))
				{
					Log.i(TAG, "studentfeedback1");
					DatabaseHandler db = new DatabaseHandler(MainActivity.appContext);
					
					String lessonName = splittedRequestcode[1];
					String type = splittedRequestcode[2];
					Log.i(TAG, "studentfeedback11");
					if(lessonName.equalsIgnoreCase(MyGlobal.getTodayLesson()))
					{
						Log.i(TAG, "studentfeedback111");
						int totalFeedbacks = XmlFileUtil.getTotalFeedbacksForTodayLesson();
						Log.i(TAG, "studentfeedback111 " + splittedRequestcode[3]);
						
						ClientDetails clientDetails = db.getClientDetailsForMacAddress(splittedRequestcode[3]);
						Log.i(TAG, "studentfeedback111");
						List<Feedback> fList = db.getAllFeedbacksForMacIdAndLesson(clientDetails.getID(), lessonName);
						Log.i(TAG, "zzzz tf " + totalFeedbacks);
						Log.i(TAG, "zzzz flistlength " + fList.size());
						if(fList.size() < totalFeedbacks){
							db.addFeedback(clientDetails.getID(), lessonName, type);
//							Log.i(TAG, "studentfeedback");
						}
					}
					db.close();
				}else
					if(splittedRequestcode[0].equalsIgnoreCase("Message"))
					{
						Log.i(TAG, "studentfeedback1 message");
						DatabaseHandler db = new DatabaseHandler(MainActivity.appContext);
						
						Messages messages = new Messages();
						messages.setMacAddress(splittedRequestcode[1]);
						Log.i(TAG, "nnnmmm "+splittedRequestcode[1]);
						String receivedMessage = "";
						for(int i=2;i<splittedRequestcode.length;i++)
						{
							receivedMessage = receivedMessage + splittedRequestcode[i] + " ";
						}
						 
						messages.setMessage(receivedMessage);
						db.addMessage(messages);
						db.close();
						Log.i(TAG, "studentfeedback2 message");
					}
					
					dataoutputStream.close();
					dataInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	


}
