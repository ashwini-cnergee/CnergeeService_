package com.cnergee.service.sys;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.caller.SendLocationCaller;
import com.cnergee.service.database.DatabaseHandler;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.util.Utils;



public class GpsTrackerService extends Service implements LocationListener {

	Timer time;
	public static String provider;
	public boolean flag_first=true;
	LocationManager lm;
	ProviderAsyncTask providerAsyncTask ;
	Utils utils= new Utils();
	String nowDate,nowTime;
	public static String rslt="";
	String activity="";
	PackageInfo pInfo;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		 Utils.log("Gps Tracker","started");
		
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		if(intent!=null){
			activity=intent.getStringExtra("activity");
		}
		if(activity!=null){
			
		}
		else{
			activity="";
		}
		lm=(LocationManager) getSystemService(LOCATION_SERVICE);
		if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			provider=LocationManager.GPS_PROVIDER;
		}
		else{
			provider=LocationManager.NETWORK_PROVIDER;
		}
		lm.requestLocationUpdates(provider, 10000, 0, this);
		Utils.log("GpsTracker","service start");
		if(time!=null){
		time.cancel();
		}
		time= new Timer();
		//time.scheduleAtFixedRate(task, 0, 30000);
		//stopSelf();
		providerAsyncTask=	new ProviderAsyncTask();
		providerAsyncTask.execute();
		
		return START_STICKY;
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Utils.log("Service Lattitude","is: "+location.getLatitude());
		Utils.log("Service Longitude","is: "+location.getLongitude());
		if(location!=null){
			Utils.log("location changed","not null");
			
			Calendar cal=Calendar.getInstance();
			SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyyHHmmss");
			//cal.set(cal.get(Calendar.DAY_OF_MONTH),(cal.get(Calendar.MONTH)+1),cal.get(Calendar.YEAR),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND));
			nowDate=dateFormatter.format(cal.getTime());
			Utils.log("date"," format is: "+nowDate);
			//nowTime=cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
			
			String str_gps_status="";
			if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
				str_gps_status="Yes";			
			else
				str_gps_status="No";
			SharedPreferences sharedPreferences = getApplicationContext()
					.getSharedPreferences(getApplicationContext().getString(R.string.shared_preferences_name), 0);
			Utils.log("database insert"," gps_status "+str_gps_status);
			Utils.log("database insert"," activity "+activity);
			Utils.log("database insert"," USER LOGIN NAME "+sharedPreferences.getString("USER_NAME", "0"));
			DatabaseHandler db= new DatabaseHandler(GpsTrackerService.this);
			
			
			// 0 - for
			db.insertLocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), sharedPreferences.getString("USER_NAME", "0"), nowDate, provider, str_gps_status,activity);
			
			
			lm.removeUpdates(GpsTrackerService.this);
			if(providerAsyncTask!=null)
				providerAsyncTask.cancel(true);
			
			new SendLocationAsyncTask().execute();
			
		}
		else{
			Utils.log("location changed","null");
		}
			
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		Utils.log("Status","Provide is: "+provider);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	TimerTask  task=new TimerTask() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Utils.log("GpsTrackerService run","30");
			if(flag_first){
				Utils.log("provider if","in timer:"+provider);
			}
			else{
				lm.removeUpdates(GpsTrackerService.this);
				provider=LocationManager.NETWORK_PROVIDER;
				Utils.log("provider else","in timer: "+provider);
				callAsync();
				//lm.requestLocationUpdates(provider, 0, 0, GpsTrackerService.this);
			}
			flag_first=false;
		}

		private void callAsync() {
			// TODO Auto-generated method stub
			new ProviderAsyncTask().execute();
		}
	};
	
	public class ProviderAsyncTask extends AsyncTask<String, Void, Void>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//lm.requestLocationUpdates(provider, 0, 0, GpsTrackerService.this);
			Utils.log("onPreExecute ","executed");
		}
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			Utils.log("doInBackground ","executed");
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Utils.log("onPostExecute ","executed");
			lm.removeUpdates(GpsTrackerService.this);
			provider=LocationManager.NETWORK_PROVIDER;
			lm.requestLocationUpdates(provider, 0, 0, GpsTrackerService.this);
			
		}
		
	}
	
	public class SendLocationAsyncTask extends AsyncTask<String, Void, Void>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//lm.requestLocationUpdates(provider, 0, 0, GpsTrackerService.this);
			
		}
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			try{
				SharedPreferences sharedPreferences = getApplicationContext()
						.getSharedPreferences("CNERGEE_SERVICE", 0); // 0 - for private mode
				utils.setSharedPreferences(sharedPreferences);
			Utils.log("doInBackground ","executed");
			
			AuthenticationMobile Authobj = new AuthenticationMobile();
			Authobj.setMobileNumber(utils.getMobileNumber());
			Authobj.setMobLoginId(utils.getMobLoginId());
			Authobj.setMobUserPass(utils.getMobUserPass());
			Authobj.setIMEINo(utils.getIMEINo());
			Authobj.setCliectAccessId(utils.getCliectAccessId());
			Authobj.setMacAddress(utils.getMacAddress());
			Authobj.setPhoneUniqueId(utils.getPhoneUniqueId());
			Authobj.setAppVersion(pInfo.versionName);
			
			SendLocationCaller caller = new SendLocationCaller(
					getApplicationContext().getResources().getString(
							R.string.WSDL_TARGET_NAMESPACE),
							utils.getDynamic_Url(), getApplicationContext()
							.getResources().getString(
									R.string.METHOD_SEND_LOCATION),Authobj,GpsTrackerService.this
									);
		
		
			caller.username=sharedPreferences.getString("USER_NAME", "0");
			
			caller.join();
			caller.start();
			rslt = "START";

			while (rslt == "START") {
				try {
					Thread.sleep(10);
				} catch (Exception ex) {
				}
			}
			}
			catch (Exception e) {
				//	AlertsBoxFactory.showErrorAlert(e.toString(),context );
				}
		
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Utils.log("SendLocation onPostExecute ","result:"+rslt);
			Toast.makeText(GpsTrackerService.this, "Location sent", Toast.LENGTH_LONG).show();
			stopSelf();
			
		}
		
	}
}
