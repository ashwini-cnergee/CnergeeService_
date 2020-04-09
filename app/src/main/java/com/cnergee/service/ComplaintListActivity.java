/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  25 Feb. 2013
 *
 * @Author 
 * Ashok Parmar
 * 
 * Version 1.0
 *
 */
package com.cnergee.service;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
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
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.SOAP.LogOutButtonSettingSOAP;
import com.cnergee.service.adapter.ComplaintListAdapter;
import com.cnergee.service.adapter.ComplaintListAdapter1;
import com.cnergee.service.caller.ComplaintCaller;
import com.cnergee.service.database.DatabaseHandler;
import com.cnergee.service.obj.AppConstants1;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.obj.ComplaintListObj;
import com.cnergee.service.obj.ComplaintObj;
import com.cnergee.service.sys.NotificationService;
import com.cnergee.service.util.FontTypefaceHelper;
import com.cnergee.service.util.KillProcess;
import com.cnergee.service.util.Utils;
import com.traction.ashok.util.AlertsBoxFactory;

@SuppressLint("DefaultLocale")
public class ComplaintListActivity extends Activity  {

	Utils utils;
	public static Context context;
	public static String rslt = "";
	
	private String logtag = getClass().getSimpleName();
	FontTypefaceHelper fontTypeface = new FontTypefaceHelper();
	private String sharedPreferences_name;
	private ListView complaintListView;
	public String Complain = null;
	public String Memberid = null;
	public static int ComplaintCount = 0;
	public int ListSize = 0;
	public static ArrayList<ComplaintListObj> complaintList = new ArrayList<ComplaintListObj>();
	public static ArrayList<ComplaintObj>complaintObj = new ArrayList<ComplaintObj>(); 
	private boolean isFinish = false;
	private Map complaintMap;
	boolean isLogout = false;
	EditText etSearch;
	LocationManager locationManager;
	AlertDialog  alert ;
	AlertDialog.Builder   alertDialogBuilder;
	SharedPreferences sharedPreferences;
	ComplaintListAdapter adapter;
	ComplaintListAdapter1 adapter1;
	public static String ShowButtonResult="";
	Button logoutBtn,btnProspect;
	public static	ArrayList<String> alMobilSettingName= new ArrayList<String>();
	public static	ArrayList<String> alMobilSettingValue= new ArrayList<String>();
	String Dynamic_Url="";
	PackageInfo pInfo;
	RelativeLayout rlMain;
	 private GestureDetector gestureDetector;
	 DatabaseHandler dbHandler;
	 Button btnBack;
	 ImageView ivHome;
	 int countdashBoardButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_complaint_list);
		
		btnBack=(Button) findViewById(R.id.btnBackComplaintList);
		ivHome=(ImageView) findViewById(R.id.ivHome);
		dbHandler= new DatabaseHandler(ComplaintListActivity.this);
		btnBack.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ComplaintListActivity.this.finish();
				Intent dashboardIntent= new Intent(ComplaintListActivity.this,DashboardActivity.class);
				startActivity(dashboardIntent);
			}
		});
		
		ivHome.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ComplaintListActivity.this.finish();
				Intent dashboardIntent= new Intent(ComplaintListActivity.this,DashboardActivity.class);
				startActivity(dashboardIntent);
			}
		});
		TextView versionView = (TextView) findViewById(R.id.versionView);
		String app_ver;
		try {
			app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
			versionView.setText("Ver."+app_ver);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*PackageManager pm = getApplicationContext().getPackageManager();
		boolean hasGps = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
		if(hasGps)
			Toast.makeText(this, "Gps Available", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, "Gps Not Available", Toast.LENGTH_SHORT).show();*/
		
		PackageManager pm = getApplicationContext().getPackageManager();
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		alMobilSettingName.clear();
		alMobilSettingValue.clear();
		
		if(AppConstants1.hasGPSDevice(this)){
			AppConstants1.GPS_AVAILABLE=true;
			//Toast.makeText(this, "Gps Available Main", Toast.LENGTH_SHORT).show();
		}
		else{
			AppConstants1.GPS_AVAILABLE=false;
			//Toast.makeText(this, "Gps Not Available Main", Toast.LENGTH_SHORT).show();
		}
		
		utils = new Utils();
		context = this;
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Utils.log("Activity","is: "+this.getClass().getSimpleName());
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Login.this);
		sharedPreferences_name = getString(R.string.shared_preferences_name);
		 sharedPreferences = getApplicationContext()
				.getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode
		 utils.setSharedPreferences(sharedPreferences);
		
		 countdashBoardButton=sharedPreferences.getInt("dashboard_count", 1);
		 Utils.log("Count", "Dashboard: "+countdashBoardButton);
		 logoutBtn = (Button)findViewById(R.id.logoutBtn);
		 
		 logoutBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedPreferences_name = getString(R.string.shared_preferences_name);
    			SharedPreferences sharedPreferences = getApplicationContext()
    					.getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode
    			
    			utils.setSharedPreferences(sharedPreferences);
            	SharedPreferences.Editor editor = sharedPreferences.edit();
            	//editor.clear();
            
            	editor.remove("USER_NAME");
            	editor.remove("USER_PASSWORD");
            	editor.remove("USER_ID");
            	editor.commit();
				
				finish();
			}
		});
		 
		 if(countdashBoardButton>1){
			 ivHome.setVisibility(View.VISIBLE);
			 logoutBtn.setVisibility(View.GONE);
			 btnBack.setVisibility(View.VISIBLE);
		 }
		 else{
			 ivHome.setVisibility(View.GONE);
			 btnBack.setVisibility(View.GONE);
			 
			 if(sharedPreferences.getBoolean("show_logout",false)){
					Utils.log("Setting Button ","visisble");
					logoutBtn.setVisibility(View.VISIBLE);
				}
				else{
					Utils.log("Setting Button ","invisisble");
					logoutBtn.setVisibility(View.GONE);
				}
		 }
		 
		
		
		LocalBroadcastManager.getInstance(this).registerReceiver(
	            mMessageReceiver, new IntentFilter("servicestatus"));
		Utils.log("Cliect","Id: "+sharedPreferences.getString("CliectAccessId", "0"));
		//String ClientAccessId=sharedPreferences.getString("CliectAccessId", "0");
		
		rlMain=(RelativeLayout) findViewById(R.id.rlMainComplaint);
		
		 //logoutBtn = (Button)findViewById(R.id.logoutBtn);
		
		if(utils.isOnline(ComplaintListActivity.this)){
			new ComplaintListWebService().execute();
		}
		
		/* *************Log Of Button*******setting based starts here************************* */
		/*if(sharedPreferences.getBoolean("check_app",true)){
			Utils.log("Setting Button ","Webservice called");
			new SettingBasedLogout().execute();
		}
		else{
		if(sharedPreferences.getBoolean("show_logout",false)){
			Utils.log("Setting Button ","visisble");
			logoutBtn.setVisibility(View.VISIBLE);
		}
		else{
			Utils.log("Setting Button ","invisisble");
			logoutBtn.setVisibility(View.GONE);
		}
		}*/
		
		/* *************Log Of Button********setting based ends here************************/
		
		etSearch=(EditText) findViewById(R.id.etSearch);
		etSearch.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			  String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
              adapter1.filter(text);
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	});
		complaintListView = (ListView)findViewById(R.id.complaintListView);
		
//******asasd**************************sadasdasdas********************asdada********asasddasd**********NOwasdasda***********************asdad		
		if(utils.isOnline(context)){
									
			TextView headerView = (TextView)findViewById(R.id.headerView);
			headerView.setText(getString(R.string.app_complaint_list_title));
			
			ComplaintListObj complaint_data[] = new ComplaintListObj[]
			{new ComplaintListObj("No Complaints ")};
			//adapter = new ComplaintListAdapter(this,R.layout.complaint_listview_item_row, complaint_data);
			adapter1=new ComplaintListAdapter1(this, R.layout.complaint_listview_item_row, complaintList);

		
			View header = (View)getLayoutInflater().inflate(R.layout.complaint_listview_header_row, null);
			complaintListView.addHeaderView(header);
	
		//	complaintListView.setAdapter(adapter);
			complaintListView.setAdapter(adapter1);
			
			
			//new ComplaintListWebService().execute();
			
		}else{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(Html.fromHtml("<font color='#FA0606'>Unable to connect to the Internet.<br>Can't display the details because your device isn't connected to the Internet.</font>"))
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(Html.fromHtml("<font color='#FA0606'>Internet Connection</font>"))
			       .setCancelable(false)
			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
							finish();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
		
//******asasd**************************sadasdasdas********************asdada********asasddasd**********NOwasdasda***********************asdad 
		complaintListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // When clicked, show a toast with the TextView text
				ComplaintListObj  selectedFromList = (ComplaintListObj) complaintListView.getItemAtPosition(position);
				
				/*Log.i(logtag+" >> OBJ ", "");*/
				
				/*if( selectedFromList.getComplaintNo() != null){
					Log.i(logtag+"", selectedFromList.getComplaintNo());
					String ListCount = String.valueOf(ListSize);
					isFinish = true;
					finish();
					Intent intent = new Intent(ComplaintListActivity.this,	ComplaintDetailsActivity.class);
					Bundle bundle = new Bundle();
					//ComplaintListObj seleComplObj = (ComplaintListObj)complaintMap.get(selectedFromList.getComplaintNo());
					ComplaintListObj seleComplObj = (ComplaintListObj)complaintMap.get(selectedFromList.getComplaintNo());
					bundle.putString("complaintID", seleComplObj.getComplaintNo());
					bundle.putString("ListSize", ListCount);
					//bundle.putString("ListCount", Long.valueOf(ComplaintCount).toString());
					bundle.putString("complaintNo",  seleComplObj.getComplaintNo());
					bundle.putString("memID", seleComplObj.getMemberLoginId());
					bundle.putString("ListCount", ""+complaintList.size());
					bundle.putBoolean("isRead", seleComplObj.isRead());
					intent.putExtra("com.cnergee.service.complaint.details.screen.INTENT", bundle);
					startActivity(intent);
					
					Log.i(logtag+" << OBJ << ", seleComplObj.getComplaintId());
					Log.i(logtag+" << OBJ << ", seleComplObj.getComplaintNo());
					Log.i(logtag+" << OBJ << ", seleComplObj.getMemberLoginId());
					

				}*/		
				
				if( selectedFromList.getComplaintNo() != null){
					Log.i(logtag+"", selectedFromList.getComplaintNo());
					String ListCount = String.valueOf(ListSize);
					isFinish = true;
					finish();
					Intent intent = new Intent(ComplaintListActivity.this,	ComplaintDetailsActivity.class);
					Bundle bundle = new Bundle();
					//ComplaintListObj seleComplObj = (ComplaintListObj)complaintMap.get(selectedFromList.getComplaintNo());
					//ComplaintListObj seleComplObj = (ComplaintListObj)complaintMap.get(selectedFromList.getComplaintNo());
					bundle.putString("complaintID", selectedFromList.getComplaintId());
					bundle.putString("ListSize", ListCount);
					//bundle.putString("ListCount", Long.valueOf(ComplaintCount).toString());
					bundle.putString("complaintNo",  selectedFromList.getComplaintNo());
					bundle.putString("memID", selectedFromList.getMemberLoginId());
					bundle.putString("ListCount", ""+complaintList.size());
					bundle.putBoolean("isRead", selectedFromList.isRead());
					intent.putExtra("com.cnergee.service.complaint.details.screen.INTENT", bundle);
					startActivity(intent);
					
					Log.i(logtag+" << OBJ << ", selectedFromList.getComplaintId());
					Log.i(logtag+" << OBJ << ", selectedFromList.getComplaintNo());
					Log.i(logtag+" << OBJ << ", selectedFromList.getMemberLoginId());
					

				}
				}
		});
		
		/*if(sharedPreferences.getString("CliectAccessId", "0").equalsIgnoreCase("CM000005LS")){
			logoutBtn.setVisibility(View.VISIBLE);
		}*/
		
		
		/*logoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	
            	sharedPreferences_name = getString(R.string.shared_preferences_name);
    			SharedPreferences sharedPreferences = getApplicationContext()
    					.getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode
    			
    			utils.setSharedPreferences(sharedPreferences);
            	SharedPreferences.Editor editor = sharedPreferences.edit();
            	//editor.clear();
            
            	editor.remove("USER_NAME");
            	editor.remove("USER_PASSWORD");
            	editor.remove("USER_ID");
            	editor.commit();
				isFinish = true;
				finish();
            }
        });*/
		
		
		
		/*btnProspect=(Button) findViewById(R.id.btnProspectListActivity);
		btnProspect.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				   Intent i= new Intent(ComplaintListActivity.this,ProspectActivity.class);
			        startActivity(i);
			        overridePendingTransition(R.anim.slide_in_right,
							R.anim.slide_out_left);
			}
		});*/
	}
	
	

	public void onRefresh(View view){
		new ComplaintListWebService().execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_complaint_list, menu);
		return true;
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
	this.finish();
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
	}
	@Override
	protected void onResume() {
		super.onResume();
		//if(sharedPreferences.getBoolean("check_first", true)){
		
		
		/*Intent intentService1 = new Intent(ComplaintListActivity.this,AlarmBroadcastReceiver.class);
		intentService1.putExtra("activity", "login service");
		PendingIntent pintent1 = PendingIntent.getBroadcast(context, 0, intentService1, 0);
		Calendar cal = Calendar.getInstance();
		AlarmManager alarm1 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		// Start every 30 seconds
		//alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60*1000, pintent);15*60*1000
		// Start every 1mon..
		alarm1.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 15*60*1000, pintent1);*/
		
		
		//Intent intentService = new Intent(ComplaintListActivity.this,CnergeeService.class);
		Intent intentService = new Intent(ComplaintListActivity.this,NotificationService.class);
		
		Calendar cal1 = Calendar.getInstance();
		PendingIntent pintent = PendingIntent.getService(context, 0, intentService, 0);
		AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		// Start every 30 seconds
		//alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*1000, pintent);
		// Start every 1mon..
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), 60000*4, pintent);
		//}
		AppConstants1.APP_OPEN=true;
		if(AppConstants1.GPS_AVAILABLE){ 
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
	            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
	        }else{
	        	  //showGPSDisabledAlertToUser();
	        }
		}
		
		Editor edit= sharedPreferences.edit();
		edit.putBoolean("check_first", false);
		edit.commit();
		
	}
	
	protected class ComplaintListWebService extends AsyncTask<String, Void, Void>{
		
		private ProgressDialog Dialog = new ProgressDialog(ComplaintListActivity.this);

		protected void onPreExecute() {
			Dialog.setMessage("Please Wait..");
			Dialog.show();
		}
		protected void onPostExecute(Void unused) {
		//if(isActivityRunning(ComplaintListActivity.class)){
			Dialog.dismiss();
		//}
			if (rslt.trim().equalsIgnoreCase("ok")) {
				
				Log.i("Complaint","is "+complaintList.size());
				ComplaintCount = complaintList.size();
				TextView compcount = (TextView)findViewById(R.id.headerViewdata);
				if(ComplaintCount>0)
				compcount.setText(Integer.toString(ComplaintCount));
				else
				compcount.setText("0");	
				ListSize = complaintList.size();
				ComplaintListObj complaint_data[] = new ComplaintListObj[complaintList.size()];
				ComplaintListObj complaint_id[] = new ComplaintListObj[complaintList.size()];
				Iterator iter = complaintList.iterator();
				int i = 0;
				complaintMap = new HashMap();
				
				
				while(iter.hasNext()){
					ComplaintListObj obj = (ComplaintListObj)iter.next();
					String id = obj.getMemberLoginId();
					Complain = obj.getComplaintNo();
					
					String MemberId = id + "," + Complain;
					Log.i(" >> TEST <<< ",""+MemberId);
					
					if(!obj.isUpdated()){
						if(!obj.isSclosed()){
							complaintMap.put(id, obj);
							complaint_data[i] = new ComplaintListObj(id);
							//complaint_id[i] = new ComplaintListObj(Complain);
							i++;
						}
					}
				}
				// adapter = new ComplaintListAdapter(ComplaintListActivity.this,R.layout.complaint_listview_item_row, complaint_data);
				 adapter1= new ComplaintListAdapter1(ComplaintListActivity.this,R.layout.complaint_listview_item_row, complaintList);
				// complaintListView.setAdapter(adapter);
				 complaintListView.setAdapter(adapter1);
			} else {
				AlertsBoxFactory.showAlert(rslt,context );
				return;
			}
			
			Thread t=	new Thread() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					List<ComplaintObj> alTicketsDb=dbHandler.getAllContacts();
					if (alTicketsDb != null) {
						if (alTicketsDb.size() > 0) {
							for (int i = 0; i < alTicketsDb.size(); i++) {
								boolean flag=false;
								for (int j = 0; j < complaintList.size(); j++) {
									ComplaintListObj comp= complaintList.get(j);
									ComplaintObj compDbObj=alTicketsDb.get(i);
									Utils.log("From Service",""+comp.getComplaintNo());
									Utils.log("From DB",""+compDbObj.getComplaintNo());
									if (compDbObj.getComplaintNo().equalsIgnoreCase(comp.getComplaintNo())) {
										flag=true;
										break;
									} 
									else {
										
									}
								}
								if(!flag){
									Utils.log("Ticket NOT"," exist:"+alTicketsDb.get(i).getComplaintNo());
									// dbHandler.DeleteTicketRow(DatabaseHandler.TABLE_CONTACTS,alTicketsDb.get(i));
									dbHandler.deleteComplaint(alTicketsDb.get(i).getComplaintNo());
								}
								else{
									Utils.log("Ticket"," exist:"+alTicketsDb.get(i).getComplaintNo());
								}
							}
						}
					}
				}
			};
			t.start();
			
		}
		@Override
		protected Void doInBackground(String... params) {
			
			try{
				Log.i("Complaint","background "+complaintList.size());
				AuthenticationMobile Authobj = new AuthenticationMobile();
				Authobj.setMobileNumber(utils.getMobileNumber());
				Authobj.setMobLoginId(utils.getMobLoginId());
				Authobj.setMobUserPass(utils.getMobUserPass());
				Authobj.setIMEINo(utils.getIMEINo());
				Authobj.setCliectAccessId(utils.getCliectAccessId());
				Authobj.setMacAddress(utils.getMacAddress());
				Authobj.setPhoneUniqueId(utils.getPhoneUniqueId());
				Authobj.setAppVersion(pInfo.versionName);
				ComplaintCaller  caller = new ComplaintCaller(
						getApplicationContext().getResources().getString(
								R.string.WSDL_TARGET_NAMESPACE),
								utils.getDynamic_Url(), getApplicationContext()
								.getResources().getString(
										R.string.METHOD_GET_COMPLAINT),
										Authobj);
				
				caller.setUserId(Long.parseLong(utils.getAppUserId()));
				
				caller.join();
				caller.start();
				rslt = "START";

				while (rslt == "START") {
					try {
						Thread.sleep(10);
					} catch (Exception ex) {
					}
				}
			}catch (Exception e) {
				//AlertsBoxFactory.showErrorAlert(e.toString(),context );
				Utils.log("ComplaintListWebService","error"+e);
			}
			
			return null;
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
	    		 //showGPSDisabledAlertToUser();
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
 
 	/*@SuppressLint("DefaultLocale")
	public void setSearchResult(String result){
 		ArrayList<ComplaintListObj> alComplaintListObjs= new ArrayList<ComplaintListObj>();
 		//alComplaintListObjs.addAll(complaintList);
	 if(result.length()==0){
		 Utils.log("Length in Activity","if : "+alComplaintListObjs.size());
	 }
	 else{		 
		 for(int i=0;i<complaintList.size();i++){
				 ComplaintListObj comp= complaintList.get(i);
				 String name= comp.getMemberLoginId();
				 Utils.log("name","is:"+name);
				 Utils.log("search","for:"+result);
			 if(result.toLowerCase().toString().contains(name.toLowerCase().toString())){
				 Utils.log("alComplaintListObjs"," added");
				 alComplaintListObjs.add(comp);
			 }
		 }
		 Utils.log("Length in Activity","else : "+alComplaintListObjs.size());
	 }
	 
	//********************************
	 if(alComplaintListObjs.size()>0){
	 Iterator iter = alComplaintListObjs.iterator();
		int i = 0;
		//complaintMap = new HashMap();
		
		ComplaintListObj complaint_data[] = {};
		while(iter.hasNext()){
			ComplaintListObj obj = (ComplaintListObj)iter.next();
			//String id = obj.getMemberLoginId();
			//Complain = obj.getComplaintNo();
			//String 
			
		//	String MemberId = id + "," + Complain;
		//	Log.i(" >> TEST <<< ",""+MemberId);
			
			if(!obj.isUpdated()){
				if(!obj.isSclosed()){
					//complaintMap.put(id, obj);
					//complaint_data[i] = new ComplaintListObj(id);
					//complaint_data[i] = obj;
					complaint_data[i] = new ComplaintListObj(obj.getComplaintNo(),obj.getMemberLoginId());
					i++;
				}
			}
		}Log.i(" >> TEST I<<< ",""+i);Log.i(" >> TEST Data<<< ",""+complaint_data.length);
		 adapter = new ComplaintListAdapter(ComplaintListActivity.this,R.layout.complaint_listview_item_row, complaint_data);
			complaintListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
	 }
	 //******************************
 	}*/
 
 protected Boolean isActivityRunning(Class activityClass)
 {
         ActivityManager activityManager = (ActivityManager) getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
         List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

         for (ActivityManager.RunningTaskInfo task : tasks) {
             if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                 return true;
         }

         return false;
 }
 
 public class SettingBasedLogout extends AsyncTask<String, Void, Void>{
	 ProgressDialog prg;
	 String Settingrslt="";
	 @Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		prg= new ProgressDialog(ComplaintListActivity.this);
		prg.setCancelable(false);
		prg.setMessage("Please Wat...");
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
		LogOutButtonSettingSOAP logOutButtonSettingSOAP= new LogOutButtonSettingSOAP(
				getApplicationContext().getResources().getString(
						R.string.WSDL_TARGET_NAMESPACE),
						utils.getDynamic_Url(), getApplicationContext()
						.getResources().getString(
								R.string.METHOD_LOGOUT_SETTING)
								); 
		try {
			Settingrslt=logOutButtonSettingSOAP.getButtonSetting(Authobj,"Service");
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
			
			if(alMobilSettingName.size()>0){
				Utils.log("size","is: "+alMobilSettingName.size());
				for(int i=0;i<alMobilSettingName.size();i++){
					
					/* Inserts Logout Button setting in shared preferences whether to show logout button or not  */
					if(alMobilSettingName.get(i).equalsIgnoreCase("LoginLogOut")){
						
						Utils.log("Value From","SOAP:"+Boolean.valueOf(alMobilSettingValue.get(i)));
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("show_logout", Boolean.valueOf(alMobilSettingValue.get(i)));
						editor.putBoolean("check_app",false );
						editor.commit();
						
						if(sharedPreferences.getBoolean("show_logout",false)){
							Utils.log("Setting Button ","visisble");
							logoutBtn.setVisibility(View.VISIBLE);
						}
						else{
							Utils.log("Setting Button ","invisisble");
							logoutBtn.setVisibility(View.GONE);
						}
					}
					
					/* Inserts ComplaintList  setting in shared preferences whether to show all complaint list or filtered list  */
					
					if(alMobilSettingName.get(i).equalsIgnoreCase("ViewAllComplaintType")){
						
						Utils.log("Value From","SOAP:"+alMobilSettingValue.get(i));
						SharedPreferences.Editor editor = sharedPreferences.edit();				
						editor.putBoolean("all_complaints", Boolean.valueOf(alMobilSettingValue.get(i)));
						editor.putBoolean("check_app",false );
						editor.commit();

					}
			
				
				}
			}
			
		}
		else{
			
		}
		}
		
		
	}
	 
 }
 
 

}

