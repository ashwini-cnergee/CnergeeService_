/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  07 Feb. 2013
 *
 * @Author 
 * Ashok Parmar
 * 
 * Version 1.0
 *
 */
package com.cnergee.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.obj.AppConstants1;
import com.cnergee.service.util.ImageAdapter;
import com.cnergee.service.util.Utils;
import com.traction.ashok.util.BundleHelper;
import com.traction.ashok.util.KillProcess;

public class HomeActivity extends Activity {

	private Utils utils = new Utils();
	public static Context context;
	private String logtag = getClass().getSimpleName();
	BundleHelper bundleHelper;
	
	public static final String backBundelPackage = "com.cnergee.service.login.screen.INTENT";
	public static final String currentBundelPackage = "com.cnergee.service.home.screen.INTENT";
	public String username, password;
	boolean isLogout = true;
	private String sharedPreferences_name;
	private SharedPreferences sharedPreferences;
	GridView gridView;
	static final String[] MENU_LIST = new String[] { 
		"Update Status", "Complaint Logs","Logout" };
	
	LocationManager locationManager;
	AlertDialog  alert ;
	AlertDialog.Builder   alertDialogBuilder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		
		PackageManager pm = getApplicationContext().getPackageManager();
		boolean hasGps = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
		/*if(hasGps)
			Toast.makeText(this, "Gps Available", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, "Gps Not Available", Toast.LENGTH_SHORT).show();*/
		
		if(AppConstants1.hasGPSDevice(this)){
			AppConstants1.GPS_AVAILABLE=true;
			//Toast.makeText(this, "Gps Available Main", Toast.LENGTH_SHORT).show();
		}
		else{
			AppConstants1.GPS_AVAILABLE=false;
			//Toast.makeText(this, "Gps Not Available Main", Toast.LENGTH_SHORT).show();
		}
		
locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Login.this);
		
		LocalBroadcastManager.getInstance(this).registerReceiver(
	            mMessageReceiver, new IntentFilter("servicestatus"));
		context = this;
		sharedPreferences_name = getString(R.string.shared_preferences_name);
		sharedPreferences = getApplicationContext().getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode
		utils.setSharedPreferences(sharedPreferences);
		
		TextView headerView = (TextView)findViewById(R.id.headerView);
		headerView.setText(getString(R.string.app_home_title));
		
		bundleHelper = new BundleHelper(this.getIntent(),backBundelPackage,currentBundelPackage);
		username = bundleHelper.getCurrentExtras().getString("username");
		password = bundleHelper.getCurrentExtras().getString("password");
		
		
		/*Utils.log(logtag, "Starting srvice");*/
		
		//setListAdapter(new HomeMenuArrayAdapter(this, MENU_LIST));
		
		gridView = (GridView) findViewById(R.id.gridView1);
		 
		gridView.setAdapter(new ImageAdapter(this, MENU_LIST));
 
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				 switch (position) {
				 	case 0:  
				 			
				 			break;
				 	case 1: 
				 		finish();
				 		Intent intentCompl = new Intent(HomeActivity.this,
				 				ComplaintListActivity.class);
						startActivity(intentCompl);
				 		break;
				 	case 2: 
				 		utils.clearSharedPreferences(sharedPreferences);
				 		finish();
						Intent intentLogout = new Intent(Intent.ACTION_MAIN);
						intentLogout.addCategory(Intent.CATEGORY_HOME);
						startActivity(intentLogout);
						break;
				 	default:
				 		Toast.makeText(
								   getApplicationContext(),
								   ((TextView) v.findViewById(R.id.grid_item_label))
								   .getText()  + ""+position , Toast.LENGTH_SHORT).show();
				 		break;
				 		
				 }
				
 
			}
		});
		
		/*
		 * FOR TESTING THE BELOW CODE IS COMMENTED
		 */
		/*Calendar cal = Calendar.getInstance();
		PendingIntent pintent = PendingIntent.getService(context, 0, intent, 0);
		AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		// Start every 30 seconds
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*1000, pintent); */
		
	
	}

	@Override
	protected void onDestroy() {
	        super.onDestroy();
	        if(isLogout){
	        	ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
	        	KillProcess kill = new KillProcess(context,am);
	        	kill.killAppsProcess();
	        }else{
	        	System.runFinalizersOnExit(true);
	        }
	   }
	@Override
	protected void onPause() {
		super.onPause();
		finish();
		if(alert!=null){
			if(alert.isShowing()){
			alert.dismiss();
		}
		}

		AppConstants1.APP_OPEN=false;
		
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
		
		//AlertsBoxFactory.Dismiss();
		
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
	}
	@Override
	protected void onResume() {
		AppConstants1.APP_OPEN=true;
		super.onResume();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}
	//*************************Bradcast receiver for GPS**************************starts
			private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
			    @Override
			    public void onReceive(Context context, Intent intent) {
			        Utils.log("Service","Message");
			        //  ... react to local broadcast message
			        if(AppConstants1.GPS_AVAILABLE){
			    	if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
			    	{
			    		if(alert!=null)
			    			alert.dismiss();
			    	}
			    	else{
			    		// showGPSDisabledAlertToUser();
			    		 }
			        }
			    }
			};
			//*************************Bradcast receiver for GPS**************************ends
		
		 public  void showGPSDisabledAlertToUser(){
			  alertDialogBuilder  = new AlertDialog.Builder(context);
		        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
		        .setCancelable(false)
		        .setPositiveButton("Goto Settings Page To Enable GPS",
		                new DialogInterface.OnClickListener(){
		            public void onClick(DialogInterface dialog, int id){
		                Intent callGPSSettingIntent = new Intent(
		                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		                startActivity(callGPSSettingIntent);
		            }
		        });
		        /*alertDialogBuilder.setNegativeButton("Cancel",
		                new DialogInterface.OnClickListener(){
		            public void onClick(DialogInterface dialog, int id){
		                dialog.cancel();
		            }
		        });*/
		        alert  = alertDialogBuilder.create();
		        alert.show();
		       	        
		    }
}
