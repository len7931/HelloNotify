package com.example.hellonotify;
/**
 * �ο�
 * PowerManager.setBacklightBrightness ���� http://ccsosnfs.iteye.com/blog/1536360
 * Android power widget ���� http://blog.csdn.net/stevenhu_223/article/details/9052083
 * Recevier ����window ����
 * 		http://stackoverflow.com/questions/10687535/how-can-i-change-the-brightness-of-the-screen-in-broadcastreceiver
 * 		http://stackoverflow.com/questions/13818540/how-to-display-a-dialog-from-a-broadcast-reciever
 * 		http://stackoverflow.com/questions/7211782/how-to-call-getwindow-using-a-method-from-a-listener
 * 
 * 
 */

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends Activity {

	private Button sendNotiBt;
	private Button sendBcst;
	private Button minus;
	private int count = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sendNotiBt = (Button) findViewById(R.id.sendNotiBt);
		sendBcst = (Button) findViewById(R.id.bcst);
		minus = (Button) findViewById(R.id.button1);
		
//		toggle2G = (Button) findViewById(R.id.T2G);
		
		sendNotiBt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDefaultNotification();
			}
		});
		
		sendBcst.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ScreenBrightness.addScreenBrightness(MainActivity.this);
				
			}
		});
		
		minus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ScreenBrightness.minusScreenBrightness(MainActivity.this);
				
			}
		});
		

		
		
	}


	
	
	
	private void showDefaultNotification() {
		int icon = R.drawable.ic_sysbar_quicksettings;
			
		Intent intentWifi;
		Intent intent2G;
		Intent intentAddBright;
		Intent intentMinusBright;
		PendingIntent pIndent;
		// �����Զ�����ͼ
		RemoteViews remoteView = new RemoteViews(this.getPackageName(),R.layout.shortcut);
		
		
		intentWifi = new Intent(NotiReceiver.TOGGLEWIFI);
		intent2G = new Intent(NotiReceiver.TOGGLE2G);
		intentAddBright = new Intent(NotiReceiver.ADDBRIGHT);
		intentMinusBright = new Intent(NotiReceiver.MINUSBRIGHT);
		
		// Ϊ�Զ�����ͼ�еİ������ô����㲥
		pIndent = PendingIntent.getBroadcast(this, 0, intentWifi, 0);
		remoteView.setOnClickPendingIntent(R.id.Twifi, pIndent);
		
		pIndent = PendingIntent.getBroadcast(this, 0, intent2G, 0);
		remoteView.setOnClickPendingIntent(R.id.T2G, pIndent);
		
		pIndent = PendingIntent.getBroadcast(this, 0, intentAddBright, 0);
		remoteView.setOnClickPendingIntent(R.id.ADD, pIndent);
		
		pIndent = PendingIntent.getBroadcast(this, 0, intentMinusBright, 0);
		remoteView.setOnClickPendingIntent(R.id.MINUS, pIndent);
		
		
		// ����notification �����͵�statusbar
		Notification.Builder mbuilder = 
				new Notification.Builder(this)
				.setSmallIcon(icon)
				.setContentTitle("My")
				.setContentText("Hello")
				.setAutoCancel(false)
				.setOngoing(true)
				.setContent(remoteView); 
		
		NotificationManager mNotificationManager = 
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		
		mNotificationManager.notify(2,mbuilder.getNotification());
		
		

		
		
		
	}

}

