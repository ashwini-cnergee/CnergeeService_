package com.cnergee.service;



import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.SOAP.LogOutButtonSettingSOAP;
import com.cnergee.service.SOAP.NotificationServiceSOAP;
import com.cnergee.service.SOAP.UnreadNotificationCountSOAP;
import com.cnergee.service.database.DatabaseHandler;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.sys.NotificationService;
import com.cnergee.service.util.BadgeView;
import com.cnergee.service.util.Utils;


public class DashboardActivity extends Activity {

	ImageView ivComplaints,ivEnquiry,ivShifting,ivDeactivation,ivRefund;
	BadgeView bvComplaints,bvEnquiry,bvShifting,bvDeactivation,bvRefund;
	
	BadgeView bvlblComplaints,bvlblEnquiry,bvlblShifting,bvlblDeactivation,bvlblRefund;
	
	LinearLayout llComplaints,llEnquiry,llShifting,llDeactivation,llRefund,llLogout, llCAFfrom;
	
	ImageView lblIvComplaints,lblIvEnquiry,lblIvShifting,lblIvDeactivation,lblIvRefund;
	//ImageView lblIvComplaints;
	private String sharedPreferences_name;
	SharedPreferences sharedPreferences;
	Utils utils;
	private DatabaseHandler dbHandler;
	private 	ArrayList<String> alMobilSettingName= new ArrayList<String>();
	private 	ArrayList<String> alMobilSettingValue= new ArrayList<String>();
	//ProgressDialog prgDialog;
	PackageInfo pInfo;
	int countdashBoardButton=1;

	/////to post CAr from
	String client_accesss_id;

	String userid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dashboard);
		initializeControls();
		
		dbHandler= new DatabaseHandler(DashboardActivity.this);
		
		utils= new Utils();
		
		sharedPreferences_name = getString(R.string.shared_preferences_name);
		 sharedPreferences = getApplicationContext()
				.getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode
		
		countdashBoardButton=sharedPreferences.getInt("dashboard_count", 1);
		utils.setSharedPreferences(sharedPreferences);


	//	client_accesss_id = sharedPreferences.getString("CliectAccessId" ,"");
		//userid = sharedPreferences.getString("MobLoginId","");

		//System.out.println("clientAccess**********"+client_accesss_id+" "+userid);
		
		
		bvComplaints.setText("0");
		bvComplaints.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		bvComplaints.show();
		
		bvEnquiry.setText("0");
		bvEnquiry.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		bvEnquiry.show();
		
		bvShifting.setText("0");
		bvShifting.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		bvShifting.show();
		
		bvDeactivation.setText("0");
		bvDeactivation.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		bvDeactivation.show();
		
		llComplaints.setOnClickListener(clickListener);
		llEnquiry.setOnClickListener(clickListener);
		llShifting.setOnClickListener(clickListener);
		llDeactivation.setOnClickListener(clickListener);
		llRefund.setOnClickListener(clickListener);
		llCAFfrom.setOnClickListener(clickListener);
		llLogout.setOnClickListener(clickListener);

		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//new SettingBasedLogout().execute();
		/* *************Log Of Button*******setting based starts here************************* */
		if(sharedPreferences.getBoolean("check_app",true)){
			Utils.log("Setting Button ","Webservice called");
			new SettingBasedLogout().execute();
		}
		else{
			
			if (sharedPreferences.getBoolean("show_logout", true)) {
				Utils.log("Setting Button ", "visisble");
				llLogout.setVisibility(View.VISIBLE);
				//countdashBoardButton++;
			} else {
				Utils.log("Setting Button ", "invisisble");
				llLogout.setVisibility(View.GONE);
			}

			if (sharedPreferences.getBoolean("show_prospect", true)) {
				Utils.log("Setting Button ", "visisble");
				llEnquiry.setVisibility(View.VISIBLE);
				countdashBoardButton++;
			} else {
				Utils.log("Setting Button ", "invisisble");
				llEnquiry.setVisibility(View.GONE);
			}

			if (sharedPreferences.getBoolean("show_shifting", true)) {
				Utils.log("Setting Button ", "visisble");
				llShifting.setVisibility(View.VISIBLE);
				countdashBoardButton++;
			} else {
				Utils.log("Setting Button ", "invisisble");
				llShifting.setVisibility(View.GONE);
			}

			if (sharedPreferences.getBoolean("show_discontinue", true)) {
				Utils.log("Setting Button ", "visisble");
				llDeactivation.setVisibility(View.VISIBLE);
				countdashBoardButton++;
			} else {
				Utils.log("Setting Button ", "invisisble");
				llDeactivation.setVisibility(View.GONE);
				
			}
			if(countdashBoardButton>1){
				
				if(utils.isOnline(DashboardActivity.this)){
					new GetCountAsyncTask().execute();
					}
					else{
						
					}
			}
			else{
				finish();
				Intent intent = new Intent(DashboardActivity.this,
						ComplaintListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("username",utils.getAppUserName());
				bundle.putString("password",utils.getAppPassword());
				intent.putExtra("com.cnergee.service.home.screen.INTENT", bundle);
				startActivity(intent);
			}
			
		}
		

		/* *************Log Of Button********setting based ends here************************/

		LocalBroadcastManager.getInstance(DashboardActivity.this).registerReceiver(mNotificationReceiver, new IntentFilter("receiveNotification"));
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Utils.DASHBOARD_OPEN=true;
	//	if(sharedPreferences.getBoolean("check_first", true)){
			Intent intentService = new Intent(DashboardActivity.this,NotificationService.class);
			
			Calendar cal1 = Calendar.getInstance();
			PendingIntent pintent = PendingIntent.getService(this, 0, intentService, 0);
			AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
			// Start every 30 seconds
			//alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*1000, pintent);
			// Start every 1mon..
			alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), 60000*4, pintent);
						
			Editor edit= sharedPreferences.edit();
			edit.putBoolean("check_first", false);
			edit.commit();
		//}
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Utils.DASHBOARD_OPEN=false;
		this.finish();
		
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mNotificationReceiver);
		/*if(prgDialog!=null){
			if(prgDialog.isShowing()){
				prgDialog.dismiss();
			}
				
		}*/
		
	}
	
	public void initializeControls(){
		ivComplaints=(ImageView) findViewById(R.id.ivComplaints);
		ivEnquiry=(ImageView) findViewById(R.id.ivEnquiry);
		ivEnquiry=(ImageView) findViewById(R.id.ivCAF);
		ivShifting=(ImageView) findViewById(R.id.ivShifting);
		ivDeactivation=(ImageView) findViewById(R.id.ivDeactivation);
		ivRefund=(ImageView) findViewById(R.id.ivRefund);
				
		bvComplaints=new BadgeView(DashboardActivity.this, ivComplaints);
		bvEnquiry=new BadgeView(DashboardActivity.this, ivEnquiry);
		bvShifting=new BadgeView(DashboardActivity.this, ivShifting);
		bvDeactivation=new BadgeView(DashboardActivity.this, ivDeactivation);
		bvRefund=new BadgeView(DashboardActivity.this, ivRefund);
						
		llComplaints= (LinearLayout) findViewById(R.id.LinearComplaints);
		llEnquiry= (LinearLayout) findViewById(R.id.LinearNewEnquiry);
		llShifting= (LinearLayout) findViewById(R.id.LinearShifting);
		llDeactivation= (LinearLayout) findViewById(R.id.LinearDeactivation);
		llRefund= (LinearLayout) findViewById(R.id.LinearRefund);
		llCAFfrom = (LinearLayout) findViewById(R.id.LinearCAFfrom);
		llLogout= (LinearLayout) findViewById(R.id.LinearLogout);
		
		lblIvComplaints=(ImageView) findViewById(R.id.lbl_complaints);
		lblIvEnquiry=(ImageView) findViewById(R.id.lbl_enquiry);
		//lblIvEnquiry=(ImageView) findViewById(R.id.lbl_CAF);
		lblIvShifting=(ImageView) findViewById(R.id.lbl_shifting);
		lblIvDeactivation=(ImageView) findViewById(R.id.lbl_deactivation);
		lblIvRefund=(ImageView) findViewById(R.id.lbl_refund);
		
		bvlblComplaints=new BadgeView(DashboardActivity.this, lblIvComplaints);
		bvlblEnquiry=new BadgeView(DashboardActivity.this, lblIvEnquiry);
		bvlblShifting=new BadgeView(DashboardActivity.this, lblIvShifting);
		bvlblDeactivation=new BadgeView(DashboardActivity.this, lblIvDeactivation);
		bvlblRefund=new BadgeView(DashboardActivity.this, lblIvRefund);
		
		TextView versionView = (TextView) findViewById(R.id.versionView);
		String app_ver;
		try {
			app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
			versionView.setText("Ver."+app_ver);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public OnClickListener clickListener= new android.view.View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.LinearComplaints:
				
			//	Toast.makeText(DashboardActivity.this,getString(R.string.app_login_mode),Toast.LENGTH_LONG).show();
				finish();
				Intent intent = new Intent(DashboardActivity.this,
						ComplaintListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("username",utils.getAppUserName());
				bundle.putString("password",utils.getAppPassword());
				intent.putExtra("com.cnergee.service.home.screen.INTENT", bundle);
				startActivity(intent);
				break;
				
			case R.id.LinearNewEnquiry:
				finish();
				Intent EnquiryIntent = new Intent(DashboardActivity.this,
						AllListActvity.class);				
				EnquiryIntent.putExtra("flag", "P");
				startActivity(EnquiryIntent);
				break;
				
			case R.id.LinearShifting:
				finish();
				Intent ShiftingIntent = new Intent(DashboardActivity.this,
						AllListActvity.class);				
				ShiftingIntent.putExtra("flag", "S");
				startActivity(ShiftingIntent);
				break;
				
			case R.id.LinearDeactivation:
				finish();
				Intent DeactivationIntent = new Intent(DashboardActivity.this,
						AllListActvity.class);				
				DeactivationIntent.putExtra("flag", "D");
				startActivity(DeactivationIntent);
				break;

			case R.id.LinearCAFfrom:
				finish();
//				Intent CAFFROM = new Intent(DashboardActivity.this,
//					CAF_Activity.class);
//				Intent CAFFROM = new Intent(DashboardActivity.this,
//						activity_ekyc.class);

//				Intent CAFFROM = new Intent(DashboardActivity.this,
//						AllinOne.class);
				
				//CAFFROM.putExtra("flag", "P");


				Intent CAFFROM = new Intent(DashboardActivity.this, CAF_Activity.class);
				startActivity(CAFFROM);
				break;
				
			case R.id.LinearRefund:
				/*Intent Caf_Intent = new Intent(DashboardActivity.this,
						Caf_Form_Activity.class);				
				
				startActivity(Caf_Intent);*/
				break;
			case R.id.LinearLogout:
				SharedPreferences.Editor editor = sharedPreferences.edit();
            	//editor.clear();
            
            	editor.remove("USER_NAME");
            	editor.remove("USER_PASSWORD");
            	editor.remove("USER_ID");
            	
            	editor.remove("check_app");
            	editor.commit();
				
				finish();
				break;
			}
		}
	};
	
	
		
	public class GetCountAsyncTask extends AsyncTask<String, Void, Void>{
		ProgressDialog prgDialog;
		NotificationServiceSOAP notificationServiceSOAP;
		String jsonResponse="";
		String notificationResult="";
		int ComplaintNumber=0,ProspectNumber=0;
		String str_Complaint_Number="",str_Prospect_Number="";
		AuthenticationMobile Authobj;
		 PackageInfo pInfo;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			prgDialog= new ProgressDialog(DashboardActivity.this);
			prgDialog.setMessage("Please wait...");
			prgDialog.setCancelable(false);
			prgDialog.show();
			
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
			
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			notificationServiceSOAP= new NotificationServiceSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), getString(R.string.METHOD_GET_ALL_NOTIFICATION));
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
			super.onPostExecute(result);
			
			bvlblComplaints.setBadgePosition(BadgeView.POSITION_CENTER);
			bvlblComplaints.setBackgroundColor(Color.TRANSPARENT);
			bvlblComplaints.setTextColor(Color.BLUE);
		
				bvlblComplaints.setText(String.valueOf(dbHandler.getAllContacts().size()));				
			
			bvlblComplaints.show();
			
			bvlblEnquiry.setBadgePosition(BadgeView.POSITION_CENTER);
			bvlblEnquiry.setBackgroundColor(Color.TRANSPARENT);
			bvlblEnquiry.setTextColor(Color.BLUE);
			
				bvlblEnquiry.setText(String.valueOf(dbHandler.getTicketCount(DatabaseHandler.TABLE_ENQUIRY)));				
			
			bvlblEnquiry.show();
			
			bvlblShifting.setBadgePosition(BadgeView.POSITION_CENTER);
			bvlblShifting.setBackgroundColor(Color.TRANSPARENT);
			bvlblShifting.setTextColor(Color.BLUE);
			
				bvlblShifting.setText(String.valueOf(dbHandler.getTicketCount(DatabaseHandler.TABLE_SHIFTING)));				
			
			bvlblShifting.show();
			
			bvlblDeactivation.setBadgePosition(BadgeView.POSITION_CENTER);
			bvlblDeactivation.setBackgroundColor(Color.TRANSPARENT);
			bvlblDeactivation.setTextColor(Color.BLUE);
			
			bvlblDeactivation.setText(String.valueOf(dbHandler.getTicketCount(DatabaseHandler.TABLE_DEACTIVATION)));				
			
			bvlblDeactivation.show();
			
			new GetCountNotReadAsyncTask().execute();
			if(prgDialog!=null){
				if(prgDialog.isShowing()){
					prgDialog.dismiss();
				}
			}
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
								str_Complaint_Number=","+ComplaintNo;
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
								str_Prospect_Number=","+ProspectId;
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
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		}
/*************************************************INSIDE ASYNCTASK****************************************/	
	}
	
	
	
	public class GetCountNotReadAsyncTask extends AsyncTask<String, Void, Void>{
		ProgressDialog prgDialog;
		AuthenticationMobile Authobj;
		UnreadNotificationCountSOAP unreadNotificationCountSOAP;
		private String unreadNotificationResult="";
		private String unreadNotificationJsonResponse="";
		String ComplaintNo="0",ProspectNo="0",ShiftingNo="0",DeactivationNo="0";
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			prgDialog= new ProgressDialog(DashboardActivity.this);
			prgDialog.setMessage("Please wait...");
			prgDialog.setCancelable(false);
			prgDialog.show();
			
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
			super.onPreExecute();
		}
		
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			unreadNotificationCountSOAP= new UnreadNotificationCountSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), getString(R.string.METHOD_GET_UNREAD_NOTIFICATION));
			try {
				unreadNotificationResult=unreadNotificationCountSOAP.getUnreadNotifications(utils.getAppUserId(), Authobj);
				unreadNotificationJsonResponse=unreadNotificationCountSOAP.getUnreadJsonResponse();
				convertUnreadJson(unreadNotificationJsonResponse);
				
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
			
			bvComplaints.setText(""+(Integer.valueOf(bvComplaints.getText().toString())+(Integer.valueOf(ComplaintNo))));
			bvEnquiry.setText(""+(Integer.valueOf(bvEnquiry.getText().toString())+(Integer.valueOf(ProspectNo))));
			bvShifting.setText(""+(Integer.valueOf(bvShifting.getText().toString())+(Integer.valueOf(ShiftingNo))));
			bvDeactivation.setText(""+(Integer.valueOf(bvDeactivation.getText().toString())+(Integer.valueOf(DeactivationNo))));
			//bvComplaints.setText(String.valueOf(complaintNumber));
			//bvEnquiry.setText(String.valueOf(prospectNumber));
			bvComplaints.show();
			bvEnquiry.show();
			bvShifting.show();
			bvDeactivation.show();
			if(prgDialog!=null){
				if(prgDialog.isShowing()){
					prgDialog.dismiss();
				}
			}
			
		}
		public void convertUnreadJson(String json){
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
						 ComplaintNo = complaintJsonObject.getString("ComplaintCount");
						
						Utils.log("ComplaintNo", "JSONObject");
						Utils.log("ComplaintNo", ":" + ComplaintNo);
						
						
					}
					}
					if(newDatasetJson.has("Prospect")){
						
						if (newDatasetJson.get("Prospect") instanceof JSONObject) {
							JSONObject prospectJsonObject = newDatasetJson.getJSONObject("Prospect");
							 ProspectNo = prospectJsonObject.getString("ProspectCount");
							Utils.log("ProspectId", "JSONObject");
							Utils.log("ProspectId", ":" + ProspectNo);
							
							
						}
					}
					
					if(newDatasetJson.has("Shifting")){
						
						if (newDatasetJson.get("Shifting") instanceof JSONObject) {
							JSONObject prospectJsonObject = newDatasetJson.getJSONObject("Shifting");
							ShiftingNo = prospectJsonObject.getString("ShiftingCount");
							Utils.log("ShiftingNo", "JSONObject");
							Utils.log("ShiftingNo", ":" + ShiftingNo);
							
							
						}
					}
					if(newDatasetJson.has("Discontinue")){
						
						if (newDatasetJson.get("Discontinue") instanceof JSONObject) {
							JSONObject prospectJsonObject = newDatasetJson.getJSONObject("Discontinue");
							DeactivationNo = prospectJsonObject.getString("DiscontinueCount");
							Utils.log("DeactivationNo", "JSONObject");
							Utils.log("DeactivationNo", ":" + DeactivationNo);
							
							
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		}
		}
	}
	}
	
	public class SettingBasedLogout extends AsyncTask<String, Void, Void>{
		 ProgressDialog prg;
		 String Settingrslt="";
		 HashMap<String, String> hm_setting_name;
		 
		 @Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			prg= new ProgressDialog(DashboardActivity.this);
			prg.setCancelable(false);
			prg.setMessage("Please Wat...");
			if(prg!=null)
			  prg.show();
		}
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			AuthenticationMobile Authobj = new AuthenticationMobile();
			Authobj.setMobileNumber(utils.getMobileNumber());
			Authobj.setMobLoginId(utils.getMobLoginId());
			Authobj.setMobUserPass(utils.getMobUserPass());
			Authobj.setIMEINo(utils.getIMEINo());
			Authobj.setCliectAccessId(utils.getCliectAccessId());
			Authobj.setMacAddress(utils.getMacAddress());
			Authobj.setPhoneUniqueId(utils.getPhoneUniqueId());
			Authobj.setAppVersion(pInfo.versionName);
			LogOutButtonSettingSOAP logOutButtonSettingSOAP= new LogOutButtonSettingSOAP(getApplicationContext().getResources().getString(
							R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), getApplicationContext()
							.getResources().getString(
									R.string.METHOD_LOGOUT_SETTING));
			try {
				Settingrslt=logOutButtonSettingSOAP.getButtonSetting(Authobj,"Service");
				alMobilSettingName=logOutButtonSettingSOAP.getMobilSettingName();
				alMobilSettingValue=logOutButtonSettingSOAP.getMobilSettingValue();
				hm_setting_name=logOutButtonSettingSOAP.get_setting_name_hashmap();
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
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
			prg.dismiss();
			
			if(Settingrslt.length()>0){
				Utils.log("Result","is: "+Settingrslt);
			if(Settingrslt=="ok"){
				
				/*if(alMobilSettingName.size()>0){
					Utils.log("size","is: "+alMobilSettingName.size());
					for(int i=0;i<alMobilSettingName.size();i++){
						
						 Inserts Logout Button setting in shared preferences whether to show logout button or not  
						if(alMobilSettingName.get(i).equalsIgnoreCase("LoginLogOut")){
							
							Utils.log("Value From","SOAP:"+Boolean.valueOf(alMobilSettingValue.get(i)));
							SharedPreferences.Editor editor = sharedPreferences.edit();				
							editor.putBoolean("show_logout", Boolean.valueOf(alMobilSettingValue.get(i)));
							editor.putBoolean("check_app",false );
							editor.commit();
							
							if(sharedPreferences.getBoolean("show_logout",false)){
								Utils.log("Setting Button ","visisble");
								llLogout.setVisibility(View.VISIBLE);
							}
							else{
								Utils.log("Setting Button ","invisisble");
								llLogout.setVisibility(View.GONE);
							}
						}
						
						 Inserts ComplaintList  setting in shared preferences whether to show all complaint list or filtered list  
						
						if(alMobilSettingName.get(i).equalsIgnoreCase("ViewAllComplaintType")){
							
							Utils.log("Value From","SOAP:"+alMobilSettingValue.get(i));
							SharedPreferences.Editor editor = sharedPreferences.edit();				
							editor.putBoolean("all_complaints", Boolean.valueOf(alMobilSettingValue.get(i)));
							editor.putBoolean("check_app",false );
							editor.commit();

						}
						else{
							SharedPreferences.Editor editor = sharedPreferences.edit();				
							editor.putBoolean("all_complaints", true);
							editor.putBoolean("check_app",false );
							editor.commit();
						}
				
						if(alMobilSettingName.get(i).equalsIgnoreCase("Prospect")){
							
							Utils.log("Value From","SOAP:"+Boolean.valueOf(alMobilSettingValue.get(i)));
							SharedPreferences.Editor editor = sharedPreferences.edit();				
							editor.putBoolean("show_prospect", Boolean.valueOf(alMobilSettingValue.get(i)));
							editor.putBoolean("check_app",false );
							editor.commit();
							
							if(sharedPreferences.getBoolean("show_prospect",false)){
								Utils.log("Setting Button ","visisble");
								llEnquiry.setVisibility(View.VISIBLE);
								countdashBoardButton++;
							}
							else{
								Utils.log("Setting Button ","invisisble");
								llEnquiry.setVisibility(View.GONE);
							}
						}
						
						if(alMobilSettingName.get(i).equalsIgnoreCase("Shifting")){
							
							Utils.log("Value From","SOAP:"+Boolean.valueOf(alMobilSettingValue.get(i)));
							SharedPreferences.Editor editor = sharedPreferences.edit();				
							editor.putBoolean("show_shifting", Boolean.valueOf(alMobilSettingValue.get(i)));
							editor.putBoolean("check_app",false );
							editor.commit();
							
							if(sharedPreferences.getBoolean("show_shifting",false)){
								Utils.log("Setting Button ","visisble");
								llEnquiry.setVisibility(View.VISIBLE);
								countdashBoardButton++;
							}
							else{
								Utils.log("Setting Button ","invisisble");
								llEnquiry.setVisibility(View.GONE);
							}
						}
						
						if(alMobilSettingName.get(i).equalsIgnoreCase("Discontinue")){
							
							Utils.log("Value From","SOAP:"+Boolean.valueOf(alMobilSettingValue.get(i)));
							SharedPreferences.Editor editor = sharedPreferences.edit();				
							editor.putBoolean("show_discontinue", Boolean.valueOf(alMobilSettingValue.get(i)));
							editor.putBoolean("check_app",false );
							editor.commit();
							
							if(sharedPreferences.getBoolean("show_discontinue",false)){
								Utils.log("Setting Button ","visisble");
								llDeactivation.setVisibility(View.VISIBLE);
								countdashBoardButton++;
							}
							else{
								Utils.log("Setting Button ","invisisble");
								llDeactivation.setVisibility(View.GONE);
							}
						}
						
						
						
					}
					
					SharedPreferences.Editor editor = sharedPreferences.edit();				
					editor.putInt("dashboard_count", countdashBoardButton);						
					editor.commit();
					
					if(countdashBoardButton>1){
						new GetCountAsyncTask().execute();
					}
					else{
						finish();
						Intent intent = new Intent(DashboardActivity.this,
								ComplaintListActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("username",utils.getAppUserName());
						bundle.putString("password",utils.getAppPassword());
						intent.putExtra("com.cnergee.service.home.screen.INTENT", bundle);
						startActivity(intent);
					}
					
					
				}*/
				
				countdashBoardButton=1;
				
				if(hm_setting_name.size()>0){
					if(hm_setting_name.containsKey("LoginLogOut")){
						
						Utils.log("Value From","SOAP:"+Boolean.valueOf(hm_setting_name.get("LoginLogOut")));
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("show_logout", Boolean.valueOf(hm_setting_name.get("LoginLogOut")));
						editor.putBoolean("check_app",false );
						editor.commit();
						
						if(sharedPreferences.getBoolean("show_logout",false)){
							Utils.log("Setting Button ","visisble");
							llLogout.setVisibility(View.VISIBLE);
						}
						else{
							Utils.log("Setting Button ","invisisble");
							llLogout.setVisibility(View.GONE);
						}
					}
					else{
						
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("show_logout", true);
						editor.putBoolean("check_app",false );
						editor.commit();
						
						if(sharedPreferences.getBoolean("show_logout",false)){
							Utils.log("Setting Button ","visisble");
							llLogout.setVisibility(View.VISIBLE);
						}
						else{
							Utils.log("Setting Button ","invisisble");
							llLogout.setVisibility(View.GONE);
						}
					}
					
					if(hm_setting_name.containsKey("ViewAllComplaintType")){
						
						Utils.log("Value From","SOAP:"+hm_setting_name.get("ViewAllComplaintType"));
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("all_complaints", Boolean.valueOf(hm_setting_name.get("ViewAllComplaintType")));
						editor.putBoolean("check_app",false );
						editor.commit();

					}
					else{
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("all_complaints", Boolean.valueOf(true));
						editor.putBoolean("check_app",false );
						editor.commit();
					}
					
					if(hm_setting_name.containsKey("Prospect")){
						
						Utils.log("Value From","SOAP:"+Boolean.valueOf(hm_setting_name.get("Prospect")));
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("show_prospect", Boolean.valueOf(hm_setting_name.get("Prospect")));
						editor.putBoolean("check_app",false );
						editor.commit();
						
						if(sharedPreferences.getBoolean("show_prospect",false)){
							Utils.log("Setting Button ","visisble");
							llEnquiry.setVisibility(View.VISIBLE);
							countdashBoardButton++;
						}
						else{
							Utils.log("Setting Button ","invisisble");
							llEnquiry.setVisibility(View.GONE);
						}
					}
					else{
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("show_prospect", false);
						editor.putBoolean("check_app",false );
						editor.commit();
						
						if(sharedPreferences.getBoolean("show_prospect",false)){
							Utils.log("Setting Button ","visisble");
							llEnquiry.setVisibility(View.VISIBLE);
							
						}
						else{
							Utils.log("Setting Button ","invisisble");
							llEnquiry.setVisibility(View.GONE);
						}
					}
					
					if(hm_setting_name.containsKey("Shifting")){
						
						Utils.log("Value From","SOAP:"+Boolean.valueOf(hm_setting_name.get("Shifting")));
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("show_shifting", Boolean.valueOf(hm_setting_name.get("Shifting")));
						editor.putBoolean("check_app",false );
						editor.commit();
						
						if(sharedPreferences.getBoolean("show_shifting",false)){
							Utils.log("Setting Button ","visisble");
							llEnquiry.setVisibility(View.VISIBLE);
							countdashBoardButton++;
						}
						else{
							Utils.log("Setting Button ","invisisble");
							llEnquiry.setVisibility(View.GONE);
						}
					}
					else{
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("show_shifting", false);
						editor.putBoolean("check_app",false );
						editor.commit();
						
						if(sharedPreferences.getBoolean("show_shifting",false)){
							Utils.log("Setting Button ","visisble");
							llEnquiry.setVisibility(View.VISIBLE);
							
						}
						else{
							Utils.log("Setting Button ","invisisble");
							llEnquiry.setVisibility(View.GONE);
						}
					}
					
					if(hm_setting_name.containsKey("Discontinue")){
						
						Utils.log("Value From","SOAP:"+Boolean.valueOf(hm_setting_name.get("Shifting")));
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("show_discontinue", Boolean.valueOf(hm_setting_name.get("Shifting")));
						editor.putBoolean("check_app",false );
						editor.commit();
						
						if(sharedPreferences.getBoolean("show_discontinue",false)){
							Utils.log("Setting Button ","visisble");
							llDeactivation.setVisibility(View.VISIBLE);
							countdashBoardButton++;
						}
						else{
							Utils.log("Setting Button ","invisisble");
							llDeactivation.setVisibility(View.GONE);
						}
					}
					else{
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("show_discontinue", false);
						editor.putBoolean("check_app",false );
						editor.commit();
						
						if(sharedPreferences.getBoolean("show_discontinue",false)){
							Utils.log("Setting Button ","visisble");
							llDeactivation.setVisibility(View.VISIBLE);
							
						}
						else{
							Utils.log("Setting Button ","invisisble");
							llDeactivation.setVisibility(View.GONE);
						}
					}
					
					if(hm_setting_name.containsKey("Location")){
						
						Utils.log("Value From","SOAP:"+Boolean.valueOf(hm_setting_name.get("Location")));
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("location", Boolean.valueOf(hm_setting_name.get("Location")));
						editor.putBoolean("check_app",false );
						editor.commit();
						
						
					}
					else{
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("location", false);
						editor.putBoolean("check_app",false );
						editor.commit();
						
						
					}
					
					SharedPreferences.Editor editor = sharedPreferences.edit();				
					editor.putInt("dashboard_count", countdashBoardButton);						
					editor.commit();
					
					Utils.log("Dashboard", "Count WS: "+countdashBoardButton);
					
					if(countdashBoardButton>1){
						new GetCountAsyncTask().execute();
					}
					else{
						finish();
						Intent intent = new Intent(DashboardActivity.this,
								ComplaintListActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("username",utils.getAppUserName());
						bundle.putString("password",utils.getAppPassword());
						intent.putExtra("com.cnergee.service.home.screen.INTENT", bundle);
						startActivity(intent);
					}
				}
				
			}
			else{
				
			}
			}
			
			
		}
		 
	 }
	
	private BroadcastReceiver mNotificationReceiver= new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int complaintNumber=intent.getIntExtra("complaintNumber", 0);
			int prospectNumber=intent.getIntExtra("prospectNumber", 0);
			int shiftingNumber=intent.getIntExtra("shiftingNumber", 0);
			int deactivationNumber=intent.getIntExtra("deactivationNumber", 0);
	
			bvComplaints.setText(""+(Integer.valueOf(bvComplaints.getText().toString())+complaintNumber));
			bvEnquiry.setText(""+(Integer.valueOf(bvEnquiry.getText().toString())+prospectNumber));
			bvShifting.setText(""+(Integer.valueOf(bvShifting.getText().toString())+shiftingNumber));
			bvDeactivation.setText(""+(Integer.valueOf(bvDeactivation.getText().toString())+deactivationNumber));
			//bvComplaints.setText(String.valueOf(complaintNumber));
			//bvEnquiry.setText(String.valueOf(prospectNumber));
			bvComplaints.show();
			bvEnquiry.show();
			bvShifting.show();
			bvDeactivation.show();
		}
	};
	
	

}
