package com.cgearc.notwork;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	WifiManager wifiManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

		if (wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(false);
			setMobileDataEnabled(this,true);
//			changeIcon("3g");
			Toast.makeText(this, "Using [3G] Now", Toast.LENGTH_LONG)
					.show();

		} else {
			wifiManager.setWifiEnabled(true);
			setMobileDataEnabled(this,false);
//			changeIcon("wifi");
			Toast.makeText(this, "Using [Wifi] Now", Toast.LENGTH_LONG).show();
		}

		this.finish();
		//
	}

	private void setMobileDataEnabled(Context context, boolean enabled) {
		try {
			final ConnectivityManager conman = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			final Class conmanClass = Class
					.forName(conman.getClass().getName());
			final Field iConnectivityManagerField = conmanClass
					.getDeclaredField("mService");
			iConnectivityManagerField.setAccessible(true);
			final Object iConnectivityManager = iConnectivityManagerField
					.get(conman);
			final Class iConnectivityManagerClass = Class
					.forName(iConnectivityManager.getClass().getName());
			final Method setMobileDataEnabledMethod = iConnectivityManagerClass
					.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
			setMobileDataEnabledMethod.setAccessible(true);

			setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void changeIcon(String activityAlias){
		getPackageManager().setComponentEnabledSetting(
		        new ComponentName("com.cgearc.notwork", "com.cgearc.notwork.MainActivity-"+activityAlias), 
		            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
	}
}
