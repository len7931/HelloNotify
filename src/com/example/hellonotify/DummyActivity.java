package com.example.hellonotify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DummyActivity extends Activity {
	
	private static final int MSG_FINISH = 1;
	
	private Handler dummyHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_FINISH:
				finish();
				break;

			default:
				break;
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("OoO", "OoO Dummy activity onCreate");
		
		Intent tIntent = getIntent();
		String action = tIntent.getAction();
		if(NotiReceiver.ADDBRIGHT.equals(action)){
			Log.i("OoO", "OoO add brightness");
			ScreenBrightness.addScreenBrightness(this);
			Message msg = dummyHandler.obtainMessage(MSG_FINISH);
			// delay 400 ms
			dummyHandler.sendMessageDelayed(msg, 400);
			
		}else if(NotiReceiver.MINUSBRIGHT.equals(action)){
			Log.i("OoO", "OoO minus brightness");
			ScreenBrightness.minusScreenBrightness(this);
			Message msg = dummyHandler.obtainMessage(MSG_FINISH);
			// delay 400 ms
			dummyHandler.sendMessageDelayed(msg, 400);
		}
	}


	
	

}
