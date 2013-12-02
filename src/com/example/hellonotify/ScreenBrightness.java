package com.example.hellonotify;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class ScreenBrightness {
	
	
	
	
	/**
	 * 
	 * @param context
	 * ��õ�ǰ��Ļ���ȵ�ģʽ SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 Ϊ�Զ�������Ļ����
	 * 
	 * 
	 * @return integer
	 */
	public static int getScreenMode(Context context){
		int screenMode = 0;
		try {
			screenMode = Settings.System.getInt(context.getContentResolver(), 
					Settings.System.SCREEN_BRIGHTNESS_MODE);
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		}
		return screenMode;
	}
	
	/**
	 * ��õ�ǰ��Ļ����ֵ 0--255
	 */
	private static int getScreenBrightness(Context context){
		int screenBrightness = 255;
		try {
			screenBrightness = Settings.System.getInt(context.getContentResolver(), 
					Settings.System.SCREEN_BRIGHTNESS);
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		}
		Log.i("OoO", "brightness is : " + screenBrightness);
		if(screenBrightness > 215){
			screenBrightness = 215;
		}else if(screenBrightness < 50){
			screenBrightness = 50;
		}
		return screenBrightness;
	}
	/**
	 * ���õ�ǰ��Ļ����ֵ 0--255
	 */
	private static void saveScreenBrightness(Context context, int paramInt){
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
		Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, paramInt);
		resolver.notifyChange(uri, null);
	}
	
	public static void addScreenBrightness(Activity activity){
		int brightness = getScreenBrightness(activity);

		saveScreenBrightness(activity, brightness+40);
		setScreenBrightness(activity, brightness+40);
		//setLight(activity, brightness+40);
	}
	
	public static void minusScreenBrightness(Activity activity){
		int brightness = getScreenBrightness(activity);
		saveScreenBrightness(activity, brightness-40);
		setScreenBrightness(activity, brightness-40);
		//setLight(activity, brightness-40);
	}
	
	public static void setScreenBrightness(Activity activity, int paramInt){
		Window localWindow = activity.getWindow();
		WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
		float f = paramInt / 255.0F;
		localLayoutParams.screenBrightness = f;
		localWindow.setAttributes(localLayoutParams);
	}
	
    private static void setLight(Context context,int light)  
    {
    	PowerManager mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    	
        try  
        {  
            //�õ�PowerManager���Ӧ��Class����   
            Class<?> pmClass = Class.forName(mPowerManager.getClass().getName());  
            //�õ�PowerManager���еĳ�ԱmService��mServiceΪPowerManagerService���ͣ�   
            Field field = pmClass.getDeclaredField("mService");  
            field.setAccessible(true);  
            //ʵ����mService   
            Object iPM = field.get(mPowerManager);  
            //�õ�PowerManagerService��Ӧ��Class����   
            Class<?> iPMClass = Class.forName(iPM.getClass().getName());  
            /*�õ�PowerManagerService�ĺ���setBacklightBrightness��Ӧ��Method���� 
             * PowerManager�ĺ���setBacklightBrightnessʵ����PowerManagerService�� 
             */  
            Method method = iPMClass.getDeclaredMethod("setBacklightBrightness", int.class);  
            method.setAccessible(true);  
            //����ʵ��PowerManagerService��setBacklightBrightness   
            method.invoke(iPM, light);  
        }catch (ClassNotFoundException e){  
            e.printStackTrace();  
        }catch (NoSuchFieldException e){  
            e.printStackTrace();  
        }catch (IllegalArgumentException e){  
            e.printStackTrace();  
        }catch (IllegalAccessException e){  
            e.printStackTrace();  
        }catch (NoSuchMethodException e){  
            e.printStackTrace();  
        }catch (InvocationTargetException e){  
            e.printStackTrace();  
        }  
  
    }  


}
