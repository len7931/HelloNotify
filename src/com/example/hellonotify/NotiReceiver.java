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
			Intent tIntent = new Intent();
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
	    Class<?> conMgrClass = null; // ConnectivityManager��     
	    Field iConMgrField = null; // ConnectivityManager���е��ֶ�     
	    Object iConMgr = null; // IConnectivityManager�������     
	    Class<?> iConMgrClass = null; // IConnectivityManager��     
	    Method setMobileDataEnabledMethod = null; // setMobileDataEnabled����     
	    try {     
	        // ȡ��ConnectivityManager��      
	    conMgrClass = Class.forName(conMgr.getClass().getName());     
	    // ȡ��ConnectivityManager���еĶ���mService      
	    iConMgrField = conMgrClass.getDeclaredField("mService");     
	    // ����mService�ɷ���     
	        iConMgrField.setAccessible(true);     
	    // ȡ��mService��ʵ������IConnectivityManager      
	    iConMgr = iConMgrField.get(conMgr);     
	    // ȡ��IConnectivityManager��      
	    iConMgrClass = Class.forName(iConMgr.getClass().getName());     
	    // ȡ��IConnectivityManager���е�setMobileDataEnabled(boolean)����      
	    setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);     
	    // ����setMobileDataEnabled�����ɷ���      
	    setMobileDataEnabledMethod.setAccessible(true);     
	    // ����setMobileDataEnabled����      
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
