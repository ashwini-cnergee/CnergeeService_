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

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.broadcast.AlarmBroadcastReceiver;
import com.cnergee.service.caller.LoginCaller;
import com.cnergee.service.obj.AppConstants1;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.util.FontTypefaceHelper;
import com.cnergee.service.util.Utils;
import com.traction.ashok.util.AlertsBoxFactory;

public class LoginActivity extends Activity {

	Utils utils = new Utils();
	public static String rslt = "";
	Button btnsignin = null, settingBtn = null;
	EditText username, password;
	public static boolean isVaildUser = false;
	public static String userId="";
	
	public static Context context;
	private String logtag = getClass().getSimpleName();
	private ValidUserWebService validUserWebService = null;
	FontTypefaceHelper fontTypeface = new FontTypefaceHelper();
	private String sharedPreferences_name;
	private boolean isFinish = false;
	SharedPreferences sharedPreferences;
	LocationManager locationManager;
	AlertDialog  alert ;
	AlertDialog.Builder   alertDialogBuilder;
	PackageInfo pInfo;
	//String Dynamic_Url="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		 
		/*PackageManager pm = getApplicationContext().getPackageManager();
		boolean hasGps = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
		if(hasGps)
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
		
		context = this;
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Login.this);
		
		
		
		sharedPreferences_name = getString(R.string.shared_preferences_name);
		 sharedPreferences = getApplicationContext()
				.getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode
		
		utils.setSharedPreferences(sharedPreferences);
		
		//Dynamic_Url=sharedPreferences.getString("Dynamic_Url", "0");
		
		if (utils.getMobileNumber().equals("") || 
				 utils.getMobLoginId().equals("")	||
				 utils.getMobUserPass().equals("") ||utils.getDynamic_Url().equals("")) {
				Toast.makeText(LoginActivity.this,
						getString(R.string.app_please_setup_ws_label),
						Toast.LENGTH_LONG).show();
				Utils.log("MobileNumber LoginActivity",":"+utils.getMobileNumber());
				Utils.log("MobLoginId LoginActivity",":"+utils.getMobLoginId());
				Utils.log("MobUserPass LoginActivity",":"+utils.getMobUserPass());
				Utils.log("Dynamic_Url LoginActivity",":"+utils.getDynamic_Url());
				
				finish();
				Intent intent = new Intent(LoginActivity.this, AppSettingActivity.class);
				//startActivityForResult(intent, 0);
				startActivity(intent);
		}
		/*else{
			Log.i(logtag+" getAppUserId ",utils.getAppUserId());
			if(utils.getAppUserId() != null ){
				finish();
				Intent intent = new Intent(LoginActivity.this,
						ComplaintListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("username",utils.getAppUserName());
				bundle.putString("password",utils.getAppPassword());
				intent.putExtra("com.cnergee.service.home.screen.INTENT", bundle);
				startActivity(intent);
			}
		}*/
		/*Log.i(logtag+" getAppUserId ",utils.getAppUserId());*/
		
		if(!utils.getAppUserId().equals("")){
			Toast.makeText(LoginActivity.this,getString(R.string.app_login_mode),Toast.LENGTH_LONG).show();
			finish();
			Intent intent = new Intent(LoginActivity.this,
					DashboardActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("username",utils.getAppUserName());
			bundle.putString("password",utils.getAppPassword());
			intent.putExtra("com.cnergee.service.home.screen.INTENT", bundle);
			startActivity(intent);
		
		
		}
			
		/*Log.i(logtag,utils.getLanguage());*/
		
		if(utils.getLanguage() != null){
			//if(!utils.getLanguage().equalsIgnoreCase("en")){
				fontTypeface.createTypeFace(context,(LinearLayout)findViewById(R.id.loginView), utils.getLanguage());
				fontTypeface.overrideLoginFonts(context,(LinearLayout)findViewById(R.id.loginView));
			//}
		}
		
		TextView headerView = (TextView)findViewById(R.id.headerView);
		
		headerView.setText(getString(R.string.login_title));
		
		TextView versionView = (TextView) findViewById(R.id.versionView);
		String app_ver;
		try {
			app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
			versionView.setText("Ver."+app_ver);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		username = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.password);

		if(getString(R.string.app_run_mode).equalsIgnoreCase("dev")){	
			/*username.setText("Harshal.Karekar");
			password.setText("karekar11");*/
			//username.setText("Lalu.Jadhav");
			//password.setText("@123U");
			/*username.setText("Mobile.Test");
			password.setText("s123");*/
		}
		btnsignin = (Button) findViewById(R.id.loginBtn);
		
		btnsignin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (TextUtils.isEmpty(username.getText().toString().trim())) {
					AlertsBoxFactory.showAlert(getString(R.string.app_please_enter_valid_label),context );
					return;
				} else {
					validUserWebService = new ValidUserWebService();
					validUserWebService.execute((String) null);
				}
			}
		});
		
		settingBtn = (Button) findViewById(R.id.setting);
		settingBtn.setOnClickListener(btnSettingOnClickListener);
		
	}
	
	
	
	Button.OnClickListener btnSettingOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			finish();
			Intent intent = new Intent(LoginActivity.this, AppSettingActivity.class);
			//startActivityForResult(intent, 0);
			startActivity(intent);
		}
	};
	
	protected class ValidUserWebService extends AsyncTask<String, Void, Void> {

		// final AlertDialog alert =new
		// AlertDialog.Builder(Login.this).create();

		private ProgressDialog Dialog = new ProgressDialog(LoginActivity.this);

		protected void onPreExecute() {
			//Dialog.setMessage(getString(R.string.app_please_wait_label));
			Dialog.setMessage(getString(R.string.app_please_wait_label));
			Dialog.show();
			Dialog.setCancelable(false);
			fontTypeface.dialogFontOverride(context,Dialog);
		
		}

		protected void onPostExecute(Void unused) {
			Dialog.dismiss();
			btnsignin.setClickable(true);
			validUserWebService = null;
			
			if (rslt.trim().equalsIgnoreCase("ok")) {
				//isVaildUser = true;
				if (isVaildUser) {
					SharedPreferences sharedPreferences = getApplicationContext()
							.getSharedPreferences(sharedPreferences_name, 0); // 0 - for
																	// private
																	// mode
					//utils.clearSharedPreferences(sharedPreferences);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString("USER_NAME", username.getText().toString());
					editor.putString("USER_PASSWORD", password.getText().toString());
					editor.putString("USER_ID", userId);
					editor.putBoolean("check_app",true );
					editor.commit();
					
					isFinish = true;
					
					finish();
							      
					/*Intent intent = new Intent(LoginActivity.this,
							HomeActivity.class);*/
					Intent intent = new Intent(LoginActivity.this,
							DashboardActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("username",username.getText().toString());
					bundle.putString("password",password.getText().toString());
					intent.putExtra("com.cnergee.service.home.screen.INTENT", bundle);
					startActivity(intent);
					
					Intent intentService1 = new Intent(LoginActivity.this,AlarmBroadcastReceiver.class);
					intentService1.putExtra("activity", "login service");
					PendingIntent pintent1 = PendingIntent.getBroadcast(context, 0, intentService1, 0);
					Calendar cal = Calendar.getInstance();
					AlarmManager alarm1 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
					// Start every 30 seconds
					//alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60*1000, pintent);15*60*1000
					// Start every 1mon..
					alarm1.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*60*1000, pintent1);
					
				} else {
					username.requestFocus();
					username.setError(Html.fromHtml("<font color='red'>"+getString(R.string.login_invalid)+"</font>"));
					
					/*Toast.makeText(LoginActivity.this,getString(R.string.login_invalid),
							Toast.LENGTH_LONG).show();*/
					
					return;
				}
			} else {
				/*Utils.log("Login error is",""+rslt);*/
				AlertsBoxFactory.showAlert(rslt,context );
			}
			
			
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				//Utils.log("Dynamic_Url","is: "+Dynamic_Url);
				AuthenticationMobile Authobj = new AuthenticationMobile();
				Authobj.setMobileNumber(utils.getMobileNumber());
				Authobj.setMobLoginId(utils.getMobLoginId());
				Authobj.setMobUserPass(utils.getMobUserPass());
				Authobj.setIMEINo(utils.getIMEINo());
				Authobj.setCliectAccessId(utils.getCliectAccessId());
				Authobj.setMacAddress(utils.getMacAddress());
				Authobj.setPhoneUniqueId(utils.getPhoneUniqueId());
				Authobj.setAppVersion(pInfo.versionName);
				LoginCaller loginCaller = new LoginCaller(
						getApplicationContext().getResources().getString(
								R.string.WSDL_TARGET_NAMESPACE),
								utils.getDynamic_Url(), getApplicationContext()
								.getResources().getString(
										R.string.METHOD_VALIDATE_USER),
										Authobj);

				loginCaller.username = username.getText().toString().trim();
				loginCaller.password = password.getText().toString().trim();

				loginCaller.join();
				loginCaller.start();
				rslt = "START";

				while (rslt == "START") {
					try {
						Thread.sleep(10);
					} catch (Exception ex) {
					}
				}

			} catch (Exception e) {
				/*Utils.log("Login error is",""+e.toString());*/
				AlertsBoxFactory.showErrorAlert(e.toString(),context );
				/*Utils.log("Login error is",""+e.toString());*/
			}
			return null;
		}
		@Override
		protected void onCancelled() {
			Dialog.dismiss();
			btnsignin.setClickable(true);
			validUserWebService = null;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	@Override
	protected void onDestroy() {
	        super.onDestroy();
	        System.runFinalizersOnExit(true);

	   }
	@Override
	protected void onPause() {
		super.onPause();
		if(!isFinish)
			finish();
		
		if(alert!=null){
			if(alert.isShowing()){
			alert.dismiss();
		}
		}

		AppConstants1.APP_OPEN=false;
		
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
		
		//AlertsBoxFactory.Dismiss();
		
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(this).registerReceiver(
	            mMessageReceiver, new IntentFilter("servicestatus"));
		
		
		
		AppConstants1.APP_OPEN=true;
		if(AppConstants1.GPS_AVAILABLE){
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        }else{
        	//  showGPSDisabledAlertToUser();
        }
		}
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
		
		public void onBackPressed() {
			this.finish();
		};
	
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
	 @Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		 InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		 if (imm.isAcceptingText()) {
		        Utils.log("Keyboard","hide");
		    } else {
		    	 Utils.log("Keyboard","show");
		    }
		return true;
	}
	 
}
