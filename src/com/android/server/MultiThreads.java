package com.android.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.android.utils.MyGlobal;

public class MultiThreads extends Thread {

	int port = MyGlobal.getClientPort();

	@Override
	public void run() {
		// TODO Auto-generated method stub
//		super.run();
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while(true){
				Socket socket = serverSocket.accept();
				SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(socket);
				socketServerReplyThread.run();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
