package com.example.hellonotify;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.util.Log;




public class NotiReceiver extends BroadcastReceiver {

	public static final String TOGGLEWIFI = "com.example.hellonofity.togglewifi";
	public static final String TOGGLE2G = "com.example.hellonofity.toggle2G";
	public static final String ADDBRIGHT = "com.example.hellonofity.addbrightness";
	public static final String MINUSBRIGHT = "com.example.hellonofity.minusbrightness";
	public static final String TAG = "com.example.hellonofity.closewifi";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		Log.i(TAG, "OoO action is " + action);
		if(TOGGLEWIFI.equals(action)){
			toggleWIfi(context);
		}else if(TOGGLE2G.equals(action)){
			toggleMobileData(context);
		}else if(ADDBRIGHT.equals(action)){
			Intent tIntent = new Intent(ADDBRIGHT);
			tIntent.setComponent(new ComponentName("com.example.hellonotify",
					"com.example.hellonotify.DummyActivity"));
			tIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(tIntent);
		}else if(MINUSBRIGHT.equals(action)){
			Intent tIntent = new Intent(MINUSBRIGHT);
			tIntent.setComponent(new ComponentName("com.example.hellonotify",
					"com.example.hellonotify.DummyActivity"));
			tIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(tIntent);
		}

	}
	
	
	public static void toggleWIfi(Context context){
		WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if(mWifiManager.isWifiEnabled()){
			mWifiManager.setWifiEnabled(false);
		}else{
			mWifiManager.setWifiEnabled(true);
		}
	}
	
	public static void toggleMobileData(Context context, boolean enabled) {    
	    ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);    
	    Class<?> conMgrClass = null; // ConnectivityManager类     
	    Field iConMgrField = null; // ConnectivityManager类中的字段     
	    Object iConMgr = null; // IConnectivityManager类的引用     
	    Class<?> iConMgrClass = null; // IConnectivityManager类     
	    Method setMobileDataEnabledMethod = null; // setMobileDataEnabled方法     
	    try {     
	        // 取得ConnectivityManager类      
	    conMgrClass = Class.forName(conMgr.getClass().getName());     
	    // 取得ConnectivityManager类中的对象mService      
	    iConMgrField = conMgrClass.getDeclaredField("mService");     
	    // 设置mService可访问     
	        iConMgrField.setAccessible(true);     
	    // 取得mService的实例化类IConnectivityManager      
	    iConMgr = iConMgrField.get(conMgr);     
	    // 取得IConnectivityManager类      
	    iConMgrClass = Class.forName(iConMgr.getClass().getName());     
	    // 取得IConnectivityManager类中的setMobileDataEnabled(boolean)方法      
	    setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);     
	    // 设置setMobileDataEnabled方法可访问      
	    setMobileDataEnabledMethod.setAccessible(true);     
	    // 调用setMobileDataEnabled方法      
	    setMobileDataEnabledMethod.invoke(iConMgr, enabled);    
	    } catch (ClassNotFoundException e) {     
	        e.printStackTrace();    
	    } catch (NoSuchFieldException e) {     
	        e.printStackTrace();    
	    } catch (SecurityException e) {     
	        e.printStackTrace();    
	    } catch (NoSuchMethodException e) {     
	        e.printStackTrace();    
	    } catch (IllegalArgumentException e) {     
	        e.printStackTrace();    
	    } catch (IllegalAccessException e) {     
	        e.printStackTrace();    
	    } catch (InvocationTargetException e) {     
	        e.printStackTrace();    
	    }   
	}  
	public static void toggleMobileData(Context context){
		boolean currentDataStatus = isMobileDataOpen(context);
		toggleMobileData(context, !currentDataStatus);
	}
	private static boolean isMobileDataOpen(Context context){
		boolean res = false;
		ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		Class<?> conMgrClass = null;
		Method getMobileDataEnabled = null;
		try {
			conMgrClass = Class.forName(conMgr.getClass().getName());
			getMobileDataEnabled = conMgrClass.getDeclaredMethod("getMobileDataEnabled", null);
			res = (Boolean) getMobileDataEnabled.invoke(conMgr, null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i(TAG, "OoO mobile data is enable ? " + res);
		return res;
	}


}
