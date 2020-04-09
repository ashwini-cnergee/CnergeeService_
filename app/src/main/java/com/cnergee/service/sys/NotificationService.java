package com.cnergee.service.sys;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.AllListActvity;
import com.cnergee.service.ComplaintListActivity;
import com.cnergee.service.SOAP.NotificationServiceSOAP;
import com.cnergee.service.SOAP.UpdateNotificationServiceSOAP;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.util.Utils;

public class NotificationService extends Service {

	Utils utils;
	 private String sharedPreferences_name;
	 private AuthenticationMobile Authobj;
	 private String TAG="NotificationService";
	 PackageInfo pInfo;
	 String str_Complaint_no,str_Prospect_no,str_Shifting_no,str_Deactivation_no;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		
		str_Complaint_no="";
		str_Prospect_no="";
		str_Shifting_no="";
		str_Deactivation_no="";
		Utils.log(TAG+"","start");
		utils=new Utils();
		  sharedPreferences_name = getString(R.string.shared_preferences_name);
			SharedPreferences sharedPreferences = getApplicationContext()
					.getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode
			utils.setSharedPreferences(sharedPreferences);

			
			try {
				pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			Authobj = new AuthenticationMobile();
			Authobj.setMobileNumber(utils.getMobileNumber());
			Authobj.setMobLoginId(utils.getMobLoginId());
			Authobj.setMobUserPass(utils.getMobUserPass());
			Authobj.setIMEINo(utils.getIMEINo());
			Authobj.setCliectAccessId(utils.getCliectAccessId());
			Authobj.setMacAddress(utils.getMacAddress());
			Authobj.setPhoneUniqueId(utils.getPhoneUniqueId());
			Authobj.setAppVersion(pInfo.versionName);
			
			if(utils.isOnline(this)){
				new NotificationServiceAsyncTask().execute();
			}
			else{
				
			}
		
		return START_STICKY;
	}
	
	/*
	 * CLASS: ASYNCTASK TO GET NOTIFICATION 
	 * 
	 * */
	
	public class NotificationServiceAsyncTask extends AsyncTask<String, Void, Void>{
		NotificationServiceSOAP notificationServiceSOAP;
		String jsonResponse="";
		String notificationResult="";
		int ComplaintNumber=0,ProspectNumber=0,ShiftingNumber=0,DeactivationNumber=0;
		String str_Complaint_Number="",str_Prospect_Number="",str_Shifting_Number="",str_Deactivation_Number="";
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			notificationServiceSOAP= new NotificationServiceSOAP(NotificationService.this.getString(R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), NotificationService.this.getString(R.string.METHOD_GET_ALL_NOTIFICATION));
			try {
				notificationResult=notificationServiceSOAP.getAllNotifications(utils.getAppUserId(), Authobj);
				jsonResponse=notificationServiceSOAP.getJsonResponse();
				getJson(jsonResponse);
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			try{
				Utils.log("Result","Notification: "+notificationResult);
			if(notificationResult.length()>0){
				if(notificationResult.equalsIgnoreCase("OK")){
					if(jsonResponse.length()>0){
						Utils.log("Result","Notification: "+notificationResult);
						
						createNotification(ComplaintNumber, ProspectNumber,ShiftingNumber,DeactivationNumber);
						str_Complaint_no=str_Complaint_Number;
						str_Prospect_no=str_Prospect_Number;
						str_Shifting_no=str_Shifting_Number;
						str_Deactivation_no=str_Deactivation_Number;
						new UpdateNotificationServiceAsyncTask().execute();
					}
				}
			}
			}catch(Exception e){
				Utils.log("EXCEPTION",":"+e);
			}
			super.onPostExecute(result);
		}
		
/*************************************************INSIDE ASYNCTASK****************************************/		
		public void getJson(String json) {
			
			if (json != null) {
				if(json.length()>0){
				try {

					JSONObject mainJson = new JSONObject(json);
					JSONObject newDatasetJson = mainJson.getJSONObject("NewDataSet");
					
				/*
				 * JSON PARSE FRO COMPLAINT
				 * *********START HERE****************
				 * */	
					if(newDatasetJson.has("Complaint")){
						
					if (newDatasetJson.get("Complaint") instanceof JSONObject) {
						
						JSONObject complaintJsonObject = newDatasetJson.getJSONObject("Complaint");
						String ComplaintNo = complaintJsonObject.getString("ComplaintNo");
						
						Utils.log("ComplaintNo", "JSONObject");
						Utils.log("ComplaintNo", ":" + ComplaintNo);
						ComplaintNumber=1;
						str_Complaint_Number=ComplaintNo;
						
					}
					else if (newDatasetJson.get("Complaint") instanceof JSONArray) {
						
						
						JSONArray complaintJsonArray = newDatasetJson.getJSONArray("Complaint");
						Utils.log("ComplaintNo", "JSONArray");
						ComplaintNumber=complaintJsonArray.length();
						
						for (int i = 0; i < complaintJsonArray.length(); i++) {
							JSONObject jObj = complaintJsonArray.getJSONObject(i);
							String ComplaintNo = jObj.getString("ComplaintNo");
							Utils.log("ComplaintNo", ":" + ComplaintNo);
							
							if(str_Complaint_Number.length()>0){
								str_Complaint_Number+=","+ComplaintNo;
							}
							else{
								str_Complaint_Number=ComplaintNo;
							}
						}
						
					}
					else {
						
					}
					}
					/*
					 * JSON PARSE FRO COMPLAINT
					 * *********ENDS HERE****************
					 * */
					
					
					
					/*
					 * JSON PARSE FRO PROSPECT
					 * *********STARTS HERE****************
					 * */
					if(newDatasetJson.has("Prospect")){
						
					if (newDatasetJson.get("Prospect") instanceof JSONObject) {
						JSONObject prospectJsonObject = newDatasetJson.getJSONObject("Prospect");
						String ProspectId = prospectJsonObject.getString("ProspectId");
						Utils.log("ProspectId", "JSONObject");
						Utils.log("ProspectId", ":" + ProspectId);
						
						ProspectNumber=1;
						str_Prospect_Number=ProspectId;
					}
					else if (newDatasetJson.get("Prospect") instanceof JSONArray) {
						
						JSONArray prospectJsonArray = newDatasetJson.getJSONArray("Prospect");
						Utils.log("ProspectId", "JSONArray");
						ProspectNumber=prospectJsonArray.length();
						
						for (int i = 0; i < prospectJsonArray.length(); i++) {
							JSONObject jObj = prospectJsonArray.getJSONObject(i);
							String ProspectId = jObj.getString("ProspectId");
							Utils.log("ProspectId", ":" + ProspectId);
							
							if(str_Prospect_Number.length()>0){
								str_Prospect_Number+=","+ProspectId;
							}
							else{
								str_Prospect_Number=ProspectId;
							}
						}
					}
					else{
						
					}
					}
					/*
					 * JSON PARSE FRO PROSPECT
					 * *********ENDS HERE****************
					 * */
					
					
					/*
					 * JSON PARSE FRO SHIFTING
					 * *********STARTS HERE****************
					 * */
					if(newDatasetJson.has("Shifting")){
						
					if (newDatasetJson.get("Shifting") instanceof JSONObject) {
						JSONObject shiftingJsonObject = newDatasetJson.getJSONObject("Shifting");
						String ShiftingId = shiftingJsonObject.getString("MemberShiftingComplaintNo");
						Utils.log("Shifting", "JSONObject");
						Utils.log("Shifting", ":" + ShiftingId);
						
						ShiftingNumber=1;
						str_Shifting_Number=ShiftingId;
					}
					else if (newDatasetJson.get("Shifting") instanceof JSONArray) {
						
						JSONArray shiftingJsonArray = newDatasetJson.getJSONArray("Shifting");
						Utils.log("Shifting", "JSONArray");
						ShiftingNumber=shiftingJsonArray.length();
						
						for (int i = 0; i < shiftingJsonArray.length(); i++) {
							JSONObject jObj = shiftingJsonArray.getJSONObject(i);
							String ShiftingId = jObj.getString("MemberShiftingComplaintNo");
							Utils.log("ShiftingId", ":" + ShiftingId);
							
							if(str_Shifting_Number.length()>0){
								str_Shifting_Number+=","+ShiftingId;
							}
							else{
								str_Shifting_Number=ShiftingId;
							}
						}
					}
					else{
						
					}
					}
					/*
					 * JSON PARSE FRO SHIFTING
					 * *********ENDS HERE****************
					 * */
					
					
					
					/*
					 * JSON PARSE FRO DEACTIVATION
					 * *********STARTS HERE****************
					 * */
					if(newDatasetJson.has("Discontinue")){
						
					if (newDatasetJson.get("Discontinue") instanceof JSONObject) {
						JSONObject DeactivationJsonObject = newDatasetJson.getJSONObject("Discontinue");
						String DeactivationId = DeactivationJsonObject.getString("MemberDeactComplaintNo");
						Utils.log("Deactivation", "JSONObject");
						Utils.log("Deactivation", ":" + DeactivationId);
						
						DeactivationNumber=1;
						str_Deactivation_Number=DeactivationId;
					}
					else if (newDatasetJson.get("Discontinue") instanceof JSONArray) {
						
						JSONArray deactivationJsonArray = newDatasetJson.getJSONArray("Discontinue");
						Utils.log("Deactivation", "JSONArray");
						ShiftingNumber=deactivationJsonArray.length();
						
						for (int i = 0; i < deactivationJsonArray.length(); i++) {
							JSONObject jObj = deactivationJsonArray.getJSONObject(i);
							String DeactivationId = jObj.getString("MemberDeactComplaintNo");
							Utils.log("DeactivationId", ":" + DeactivationId);
							
							if(str_Deactivation_Number.length()>0){
								str_Deactivation_Number+=","+DeactivationId;
							}
							else{
								str_Deactivation_Number=DeactivationId;
							}
						}
					}
					else{
						
					}
					}
					/*
					 * JSON PARSE FRO DEACTIVATION
					 * *********ENDS HERE****************
					 * */
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		}
/*************************************************INSIDE ASYNCTASK****************************************/		
	}
	
	
	
	public void createNotification(int complaintNumber,int prospectNumber,int shiftingNumber,int deactivationNumber){
	
	//	if(!Utils.DASHBOARD_OPEN){
		
		Intent ListActivityIntent = new Intent(this, AllListActvity.class);
		
		/*
		 * Notification for New complaint
		 * */
		if(complaintNumber>0){
		
			Intent resultIntent = new Intent(this, ComplaintListActivity.class);
			

			Bundle bundle = new Bundle();
			bundle.putString("username",utils.getAppUserName());
			bundle.putString("password",utils.getAppPassword());
			resultIntent.putExtra("com.cnergee.service.home.screen.INTENT", bundle);
			// Because clicking the notification opens a new ("special") activity, there's
			// no need to create an artificial back stack.
			PendingIntent resultPendingIntent =
			    PendingIntent.getActivity(
			    this,
			    0,
			    resultIntent,
			    PendingIntent.FLAG_UPDATE_CURRENT
			);
			
			
		int mNotificationId = 001;
		
		NotificationCompat.Builder mBuilder =
			    new NotificationCompat.Builder(this)
			    .setSmallIcon(R.drawable.alert)
			    .setContentTitle("Complaint Notification")
			    .setContentIntent(resultPendingIntent)
			    .setContentText(complaintNumber+" new complaint(s)");
	
		NotificationManager mNotifyMgr = 
		        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mBuilder.setAutoCancel(true);
		  
		mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

		mBuilder.setDefaults(Notification.DEFAULT_SOUND);
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
		
						
		}
		
		
		/*
		 * Notification for New Prospect
		 * */
		if(prospectNumber>0){
			
			ListActivityIntent.putExtra("flag", "P");
			// Because clicking the notification opens a new ("special") activity, there's
			// no need to create an artificial back stack.
			PendingIntent resultPendingIntent =
			    PendingIntent.getActivity(
			    this,
			    0,
			    ListActivityIntent,
			    PendingIntent.FLAG_UPDATE_CURRENT
			);
			
			
		int mNotificationId = 002;
		
		NotificationCompat.Builder mBuilder =
			    new NotificationCompat.Builder(this)
			    .setSmallIcon(R.drawable.alert)
			    .setContentTitle("Enquiry Notification")
			    .setContentIntent(resultPendingIntent)
			    .setContentText(prospectNumber>1?prospectNumber+" new enquiries.":prospectNumber+" new enquiry.");
	
		NotificationManager mNotifyMgr = 
		        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mBuilder.setAutoCancel(true);
		mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
		mBuilder.setDefaults(Notification.DEFAULT_SOUND);
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
						
		}
		
		
		/*
		 * Notification for Shifting
		 * */
		if(shiftingNumber>0){
			
			ListActivityIntent.putExtra("flag", "S");
			// Because clicking the notification opens a new ("special") activity, there's
			// no need to create an artificial back stack.
			PendingIntent resultPendingIntent =
			    PendingIntent.getActivity(
			    this,
			    0,
			    ListActivityIntent,
			    PendingIntent.FLAG_UPDATE_CURRENT
			);
			
			
		int mNotificationId = 003;
		
		NotificationCompat.Builder mBuilder =
			    new NotificationCompat.Builder(this)
			    .setSmallIcon(R.drawable.alert)
			    .setContentTitle("Shifting Notification")
			    .setContentIntent(resultPendingIntent)
			    .setContentText(shiftingNumber+" new shifting(s).");
	
		NotificationManager mNotifyMgr = 
		        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mBuilder.setAutoCancel(true);
		mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
		mBuilder.setDefaults(Notification.DEFAULT_SOUND);
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
						
		}
		
		/*
		 * Notification for Deactivation
		 * */
		if(deactivationNumber>0){
			
			ListActivityIntent.putExtra("flag", "D");
			// Because clicking the notification opens a new ("special") activity, there's
			// no need to create an artificial back stack.
			PendingIntent resultPendingIntent =
			    PendingIntent.getActivity(
			    this,
			    0,
			    ListActivityIntent,
			    PendingIntent.FLAG_UPDATE_CURRENT
			);
			
			
		int mNotificationId = 003;
		
		NotificationCompat.Builder mBuilder =
			    new NotificationCompat.Builder(this)
			    .setSmallIcon(R.drawable.alert)
			    .setContentTitle("Deactivation Notification")
			    .setContentIntent(resultPendingIntent)
			    .setContentText(deactivationNumber+" new deactivation(s).");
	
		NotificationManager mNotifyMgr = 
		        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mBuilder.setAutoCancel(true);
		mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
		mBuilder.setDefaults(Notification.DEFAULT_SOUND);
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
						
		}
		
		//}
		if(Utils.DASHBOARD_OPEN){
			if(complaintNumber>0 || prospectNumber>0 ||shiftingNumber>0||deactivationNumber>0){
				Intent DashboardIntent = new Intent("receiveNotification");	
				DashboardIntent.putExtra("complaintNumber", complaintNumber);
				DashboardIntent.putExtra("prospectNumber", prospectNumber);
				DashboardIntent.putExtra("shiftingNumber", shiftingNumber);
				DashboardIntent.putExtra("deactivationNumber", deactivationNumber);
                LocalBroadcastManager.getInstance(NotificationService.this).sendBroadcast(DashboardIntent);
			}
		}
	}
	
	
	public class UpdateNotificationServiceAsyncTask extends AsyncTask<String, Void, Void>{
		UpdateNotificationServiceSOAP updateNotificationServiceSOAP;
		String result="";
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			Utils.log("UpdateNotificationServiceAsyncTask PreExcute","started");
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			Utils.log("UpdateNotificationServiceAsyncTask doInBackground","started");
			updateNotificationServiceSOAP= new UpdateNotificationServiceSOAP(NotificationService.this.getString(R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), NotificationService.this.getString(R.string.METHOD_UPDATE_ALL_NOTIFICATION));
			try {
				updateNotificationServiceSOAP.updateNotifications(utils.getAppUserId(), str_Complaint_no, str_Prospect_no,str_Shifting_no,str_Deactivation_no, "P", Authobj);
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Utils.log("UpdateNotificationServiceAsyncTask onPostExecute","started");
		}
	}
}
