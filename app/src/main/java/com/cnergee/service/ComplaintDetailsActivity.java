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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.SOAP.ComplaintStatusUpdateSOAP;
import com.cnergee.service.SOAP.ComplaintTypeListSOAP;
import com.cnergee.service.SOAP.GetStatusSOAP;
import com.cnergee.service.SOAP.ResolutionSOAP;
import com.cnergee.service.broadcast.AlarmBroadcastReceiver;
import com.cnergee.service.caller.ComplaintDetailsCaller;
import com.cnergee.service.caller.ComplaintUpdateCaller;
import com.cnergee.service.database.DatabaseHandler;
import com.cnergee.service.obj.AppConstants1;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.obj.ComplaintObj;
import com.cnergee.service.obj.ComplaintUpdateObj;
import com.cnergee.service.obj.StatusListObj;
import com.cnergee.service.util.FontTypefaceHelper;
import com.cnergee.service.util.StatusUpdateHelper;
import com.cnergee.service.util.Utils;
import com.cnergee.service.xml.StatusListParsing;
import com.traction.ashok.util.AlertsBoxFactory;
import com.traction.ashok.util.BundleHelper;
import com.traction.ashok.util.KillProcess;
import com.traction.ashok.util.XMLFileHelper;

public class ComplaintDetailsActivity extends Activity {

	Utils utils;
	public static Context context;
	public static String rslt = "";
	public static ComplaintObj complaintObj;
	public static ComplaintUpdateObj complaintUpdateObj;
	public static String responseMessage;
	public static String xmlStatusList;
	public static String xmlComplCat;
	public static String xmlComplType;
	public static String ComplaintCount;
	
	private String logtag = getClass().getSimpleName();
	FontTypefaceHelper fontTypeface = new FontTypefaceHelper();
	private String sharedPreferences_name;
	
	CheckBox progress = null;
	CheckBox Closed = null;
	CheckBox backtoareamanager = null;
	CheckBox doorlock = null;
	CheckBox unabletoresolve = null;
	
	Spinner spResolution,spComplaints;
	private static final String currentBundelPackage = "com.cnergee.service.complaint.details.screen.INTENT";
	BundleHelper bundleHelper;
	Bundle extras;
	private boolean isFinish = false;
	private String strComplaintNo;
	boolean isLogout = false;
	EditText compComment;
	Spinner spinnerStatus;
	TextView ComplNo;
	private AuthenticationMobile Authobj;
	private DatabaseHandler dbHandler;
	
	private static String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
	private final static String xml_folder = "cnergeeservice";
	private XMLFileHelper xmlHelper;
	private static Map<String, StatusListObj> mapStatusList;
	//private Map<String, ComplaintCategoryObj> mapComplaintCategory;
	//private Map<String, ComplaintTypeObj> mapComplaintType;
	LocationManager locationManager;
	AlertDialog  alert ;
	AlertDialog.Builder   alertDialogBuilder;
	String complaintNo="";
	ArrayList<String> alResolutionType= new ArrayList<String>();
	ArrayList<String> alResolutionId= new ArrayList<String>();
	
	ArrayList<String> alComplaintId= new ArrayList<String>();
	ArrayList<String> alComplaintName= new ArrayList<String>();
	
	SharedPreferences sharedPreferences;
	String ResolutionId="";
	private String complaintCategoryId; 
	/*Name Of File store in SDCARD*/	
	String str_complaints_xml_file="xml_complaints";
	
	String str_resolution_xml_file="xml_resolution";
	
	final static String TARGET_BASE_PATH = extStorageDirectory + "/"
			+ xml_folder + "/";
	
	public static String str_response_resolution="";
	public static String str_response_complaints="";
	
	public Boolean setSelected=false;
	String ComplaintId="-1";
	String Dynamic_Url="";
	
	public static String getUpdateDataString="";
	
	ProgressDialog prgDialog1;
	PackageInfo pInfo;
	ImageView ivHome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_complaint_details);
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		ivHome=(ImageView) findViewById(R.id.ivHome);
		spResolution=(Spinner) findViewById(R.id.spResolution);
		spComplaints=(Spinner) findViewById(R.id.spComplaints);
		spComplaints.setVisibility(View.GONE);
		spResolution.setVisibility(View.GONE);
		
		TextView versionView = (TextView) findViewById(R.id.versionView);
		String app_ver;
		try {
			app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
			versionView.setText("Ver."+app_ver);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Login.this);
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ivHome.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ComplaintDetailsActivity.this.finish();
				Intent dashboardIntent= new Intent(ComplaintDetailsActivity.this,DashboardActivity.class);
				startActivity(dashboardIntent);
			}
		});
		/*boolean hasGps = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
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
		
		LocalBroadcastManager.getInstance(this).registerReceiver(
	            mMessageReceiver, new IntentFilter("servicestatus"));
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		utils = new Utils();
		context = this;
						 
		
			progress = (CheckBox)findViewById(R.id.Inprogress);
			progress.setOnCheckedChangeListener(listener);
			 
			Closed = (CheckBox)findViewById(R.id.Closed);
			Closed.setOnCheckedChangeListener(listener);
			 
			backtoareamanager = (CheckBox)findViewById(R.id.backtoareamanager);
			backtoareamanager.setOnCheckedChangeListener(listener);
			
			doorlock = (CheckBox)findViewById(R.id.doorlock);
			doorlock.setOnCheckedChangeListener(listener);
			
			unabletoresolve = (CheckBox)findViewById(R.id.unabletoresolve);
			unabletoresolve.setOnCheckedChangeListener(listener);
		
		
		if(utils.isOnline(context)){
			sharedPreferences_name = getString(R.string.shared_preferences_name);
			sharedPreferences = getApplicationContext()
					.getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode
			
			utils.setSharedPreferences(sharedPreferences);
			
			Dynamic_Url=sharedPreferences.getString("Dynamic_Url", "0");
			
			TextView headerView = (TextView)findViewById(R.id.headerView);
			headerView.setText(getString(R.string.app_complaint_details_title));
			
			
			
			Authobj = new AuthenticationMobile();
			Authobj.setMobileNumber(utils.getMobileNumber());
			Authobj.setMobLoginId(utils.getMobLoginId());
			Authobj.setMobUserPass(utils.getMobUserPass());
			Authobj.setIMEINo(utils.getIMEINo());
			Authobj.setCliectAccessId(utils.getCliectAccessId());
			Authobj.setMacAddress(utils.getMacAddress());
			Authobj.setPhoneUniqueId(utils.getPhoneUniqueId());
			Authobj.setAppVersion(pInfo.versionName);
			Bundle bundle = this.getIntent().getBundleExtra(currentBundelPackage);
			String strComplaintNo = bundle.getString("complaintNo");
			//String strComplaintId = bundle.getString("complaintID");
			//String strMemID = bundle.getString("memID");
			String strListSize = bundle.getString("ListSize");
			
			boolean isRead = bundle.getBoolean("isRead");
			
		/*	Log.i(logtag+" >> ",strComplaintNo);*/
			setStrComplaintNo(strComplaintNo);
			
			if(!isRead)
			 updateStatus(strComplaintNo);
			
			dbHandler = new DatabaseHandler(ComplaintDetailsActivity.this);
			ComplaintObj dbComplaint = dbHandler.getComplaintObj(strComplaintNo);
			//ComplaintCount = dbHandler.getComplaintCount();
			ComplaintCount = strListSize;
			
			LinearLayout listcount =(LinearLayout)findViewById(R.id.listCount);
			
		/*	listcount.setOnClickListener(new View.OnClickListener() {
			     @Override
			     public void onClick(View v) {
			    	 finish();
			    	 Intent intent = new Intent(ComplaintDetailsActivity.this,ComplaintListActivity.class);
						startActivity(intent);
						 complaintObj=null;
			     }      
			});*/
			ImageView iv =(ImageView) findViewById(R.id.inprogimg);
			
			iv.setOnClickListener(new View.OnClickListener() {
			     @Override
			     public void onClick(View v) {
			    	 finish();
			    	 Intent intent = new Intent(ComplaintDetailsActivity.this,ComplaintListActivity.class);
						startActivity(intent);
						 complaintObj=null;
			     }      
			});
			//int Coumcount = dbHandler.getComplaintCount();
			//TextView headerViewdata = (TextView)findViewById(R.id.headerViewdata);
			//headerViewdata.setText(Coumcount);
			
			
			//spinnerStatus = (Spinner) this.findViewById(R.id.spinnerStatus);
			
			/*xmlHelper = new XMLFileHelper();
			xmlHelper.setFileStorageLocation(extStorageDirectory);
			xmlHelper.setXmlFolder(xml_folder);
			xmlHelper.setXmlFileName("servicestatus.xml");
			*/
			/*ComplNo = (TextView) this.findViewById(R.id.ComplNo);
			xmlHelper = new XMLFileHelper();
			xmlHelper.setFileStorageLocation(extStorageDirectory);
			xmlHelper.setXmlFolder(xml_folder);
			xmlHelper.setXmlFileName("servicestatus.xml");*/
			
			/*if(xmlHelper.isXMLFileExists()){
				loadStatusList(xmlHelper);
			}else{
				new StatusListWebService().execute((String)null);
			}
			*/
			/*
			 * As diss this is not req. (Ratnesh & Kartik)
			 * 03 Apr 2013
			 */
			// set complaint category
			/*xmlHelper.setXmlFileName("complaintcat.xml");
			
			if(xmlHelper.isXMLFileExists()){
				mapComplaintCategory(xmlHelper);
			}else{
				new ComplaintCategoryListWebService().execute((String)null);
			}*/
			
			if(dbComplaint!=null){
				setScreenData(dbComplaint);
				complaintObj = dbComplaint;
				Utils.log("dbComplaint","is: "+complaintObj.getComplaintId());
				Utils.log("dbComplaint","is: "+complaintObj.getComplaintNo());
				
			}else{
				if(utils.isOnline(context))
					new ComplaintDetailsWebService().execute();
			}
			compComment = (EditText)findViewById(R.id.compComment);
			
			final Button btnBack = (Button) findViewById(R.id.backBtn);
			btnBack.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					complaintObj=null;
					ComplaintDetailsActivity.this.finish();
					Intent intent = new Intent(ComplaintDetailsActivity.this,ComplaintListActivity.class);
					startActivity(intent);
				}
			});
			
			/*final Button logoutBtn = (Button) findViewById(R.id.logoutBtn);
			logoutBtn.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					isLogout = true;
					finish();
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					startActivity(intent);
				}
			});*/
			
			
			
			/**
			 * Check File 
			 * If Exists Bind Spinner From SD CARD
			 * ELSE Fetch From Server Save File and Bind to Spinner  
			 * 
			 * **/
			
			if(isXMLFile(str_complaints_xml_file)){
				Utils.log("ComplaintsTypeList ","Exist");
				try{
				if(complaintObj!=null)					
				setComplaintsXMLPackageList(String.valueOf(complaintObj.getComplaintId()),String.valueOf(complaintObj.getComplaintCategory()));
					}
				catch(Exception e){
					Utils.log("Complaints","issue"+e);
				}
				new GetPackageAsyncTask().execute();
			}
			else{
				Utils.log("ComplaintsTypeList ","Not Exist");
				new BindComplaintTypeListAsyncTask().execute();
				
			}
			
			if(isXMLFile(str_resolution_xml_file)){
				Utils.log("Resolution ","Exist");
				try{
				if(complaintObj!=null)
				setResolutionXMLPackageList(String.valueOf(complaintObj.getComplaintId()));
				}
				catch(Exception e){
					Utils.log("Resolution ","issue"+e);
				}
			}
			else{
				Utils.log("Resolution ","Not Exist");
				new BindResolutionAsyncTask().execute();
			}
			
			
			spResolution.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					try{
					if(alResolutionId.size()>0){
						ResolutionId=alResolutionId.get(arg2);
					}
					}
					catch(Exception e){
						Utils.log("Resolution spinner","exception :"+e);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			spComplaints.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					try{
					if(setSelected){
						Utils.log("setSelected"," value"+setSelected);
					if(alComplaintId.size()>0){
						setResolutionXMLPackageList(alComplaintId.get(arg2));
						ComplaintId=alComplaintId.get(arg2);
						Utils.log("Selected","Complaint Type"+ComplaintId);
					}
					}
					else{
						Utils.log("setSelected"," value"+setSelected);
					}
					}
					catch(Exception e){
						Utils.log("complaints spinner","exception :"+e);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			final UpdateComplaintWebService updateComplService = new UpdateComplaintWebService();
			final Button updateBtn = (Button) findViewById(R.id.updateBtn);
			updateBtn.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					EditText compComment =(EditText)findViewById(R.id.compComment);
					if(compComment.getText().toString().length() == 0)
					{
						Toast.makeText(getApplicationContext(), "Please Enter Comment", Toast.LENGTH_SHORT).show();
					}
					else
					{
						if(Closed.isChecked()){
							
							Utils.log("ComplaintId", ":"+ComplaintId);
							if(ComplaintId.length()>0){
							if(ComplaintId.equalsIgnoreCase("0")){
								Toast.makeText(getApplicationContext(), "Please Select Valid Complaint Type", Toast.LENGTH_SHORT).show();
								
							}
							else{
								updateComplService.execute((String)null);
								}
							}
							else{
								Toast.makeText(getApplicationContext(), "Please Select Valid Complaint Type", Toast.LENGTH_SHORT).show();
							}
						}
						else{
							updateComplService.execute((String)null);
						}
					}
				}
			});
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
	}
	catch(IndexOutOfBoundsException e){
		Utils.log("Oncreate","issue"+e);
	}
	
		
	}
	
	 final OnCheckedChangeListener listener = new OnCheckedChangeListener() {
		 
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				
			if(isChecked){
				EditText compComment = (EditText)findViewById(R.id.compComment);
			switch(arg0.getId())
			  {
			
			    case R.id.Inprogress:
			    	progress.setChecked(true);
			    	Closed.setChecked(false);
			    	backtoareamanager.setChecked(false);
			    	doorlock.setChecked(false);
			    	unabletoresolve.setChecked(false);
			    	compComment.setText("");
			    	spResolution.setVisibility(View.INVISIBLE);
			    	spComplaints.setVisibility(View.INVISIBLE);
			    	
			         break;
			    case R.id.Closed:
			    	Closed.setChecked(true);
			    	progress.setChecked(false);
			    	backtoareamanager.setChecked(false);
			    	doorlock.setChecked(false);
			    	unabletoresolve.setChecked(false);
			    	compComment.setText("");
			    	spResolution.setVisibility(View.VISIBLE);
			    	spComplaints.setVisibility(View.VISIBLE);
			    	
			    	new BindResolutionAsyncTask().execute();
			    	new BindComplaintTypeListAsyncTask().execute();
			    	
			         break;
			   case R.id.backtoareamanager:
				   backtoareamanager.setChecked(true);
				   progress.setChecked(false);
				   Closed.setChecked(false);
				   doorlock.setChecked(false);
				   unabletoresolve.setChecked(false);
				   compComment.setText("");
				   spResolution.setVisibility(View.INVISIBLE);
				   spComplaints.setVisibility(View.INVISIBLE);
			        break;
			   case R.id.doorlock:
				   doorlock.setChecked(true);
				   progress.setChecked(false);
				   Closed.setChecked(false);
				   backtoareamanager.setChecked(false);
				   unabletoresolve.setChecked(false);
				   compComment.setText("Door Locked");
				   spResolution.setVisibility(View.INVISIBLE);
				   spComplaints.setVisibility(View.INVISIBLE);
				   break;
			   case R.id.unabletoresolve:
				   unabletoresolve.setChecked(true);
				   doorlock.setChecked(false);
				   progress.setChecked(false);
				   Closed.setChecked(false);
				   backtoareamanager.setChecked(false);
				   compComment.setText("Unable To Resolve");
				   spResolution.setVisibility(View.INVISIBLE);
				   spComplaints.setVisibility(View.INVISIBLE);
				   
			  }
			}
			else{
				if(arg0.getId()==R.id.Closed){
					 spResolution.setVisibility(View.INVISIBLE);
					 spComplaints.setVisibility(View.INVISIBLE);
				}
			}
			 
			}
			};
	
	protected class UpdateComplaintWebService extends AsyncTask<String, Void, Void>{
		private ProgressDialog Dialog = new ProgressDialog(ComplaintDetailsActivity.this);
		String strStatus = "";
		protected void onPreExecute() {
			Dialog.setMessage("Please Wait..");
			Dialog.show();
			
			
		}
		protected void onPostExecute(Void unused) {
			Dialog.dismiss();
			
			if (rslt.trim().equalsIgnoreCase("ok")) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage(Html.fromHtml("<font color='#FFA500'><b>"+responseMessage+"</b></font>"))
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(Html.fromHtml("<font color='#FFA500'>Complaint Status</font>"))
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
		
								finish();
								Intent intent = new Intent(ComplaintDetailsActivity.this, ComplaintListActivity.class);
								startActivity(intent);
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
				
			}
			if(sharedPreferences.getBoolean("location", false)){
			 Intent i = new Intent(ComplaintDetailsActivity.this,AlarmBroadcastReceiver.class);
	      	  
						i.putExtra("activity", "complaint");	
						
						Utils.log("Cheque activity","is: "+strStatus);
						sendBroadcast(i);
			}
		}
		@Override
		protected Void doInBackground(String... params) {
			
			try{
				
				ComplaintUpdateCaller caller = new ComplaintUpdateCaller(
						getApplicationContext().getResources().getString(
								R.string.WSDL_TARGET_NAMESPACE),
								Dynamic_Url, getApplicationContext()
								.getResources().getString(
										R.string.METHOD_UPDATE_COMPLAINT_STATUS),
										Authobj);
				ComplaintUpdateObj obj = new ComplaintUpdateObj();
				String strComm = "";
				if(doorlock.isChecked())
				{	
					String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
					strComm = "Door Locked at " + currentDateTimeString;
				}
				else
				{
				strComm = compComment.getText().toString();
				}
				//Log.i(" >>>strComm... ",strComm);
				
				obj.setComment(strComm);
				
				Calendar c = Calendar.getInstance();
				SimpleDateFormat viewDateFormatter = new SimpleDateFormat("ddMMyyyyHHmmss");
				String currentDate = viewDateFormatter.format(c.getTime());
				
				//Log.i(" >>>strComm... ",currentDate);
				obj.setComplaintDate(currentDate);
				
				obj.setComplaintNo(complaintObj.getComplaintNo());
				
				//String strStatus = spinnerStatus.getSelectedItem().toString();
				
				
				if(progress.isChecked())
				{
					strStatus = "InProgress";
				}
				if(Closed.isChecked())
				{
					strStatus = "Closed";
				}
				
				if(backtoareamanager.isChecked())
				{
					strStatus = "NotMyComplain";
				}
				
				if(doorlock.isChecked())
				{
					strStatus = "DoorLock";
					
				}
				if(unabletoresolve.isChecked())
				{
					strStatus = "UnableToResolve";
					
				}
				
				//Log.i(" >>>STATUS... ",strStatus);
				/*if(strStatus.equalsIgnoreCase("Select Status")){
					AlertsBoxFactory.showErrorAlert("Please select complaint status from list.",context );
					return null;
				}*/
				
				if(strStatus.equalsIgnoreCase("Closed"))
				{	
					deleteDataFromSQL(complaintObj.getComplaintNo());
				}
				else if (strStatus.equalsIgnoreCase("Created"))
				{
					updateDataFromSQL(complaintObj.getComplaintNo(),strStatus,strComm);
				}
				else
				{
				   updateDataFromSQL(complaintObj.getComplaintNo(),strStatus,strComm);
				}
				
				
				obj.setStatus(strStatus);
				obj.setUserId(Long.parseLong(utils.getAppUserId()));
				obj.setComplaintCategory(complaintObj.getComplaintCategory());
				obj.setComplaintId(complaintObj.getComplaintId());
				
				caller.setUpdateObj(obj);
				caller.setResolutionId(ResolutionId);
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
				e.printStackTrace();
				AlertsBoxFactory.showErrorAlert(e.toString(),context );
			}
			
			return null;
		}
		
	}
	
	protected void deleteDataFromSQL(String complaintNo){
		dbHandler.deleteComplaint(complaintNo);
	}
	protected void updateDataFromSQL(String complaintNo,String status,String comment){
		dbHandler.updateComplaint(complaintNo,status,comment);
	}
	
	protected class ComplaintDetailsWebService extends AsyncTask<String, Void, Void>{
		private ProgressDialog Dialog = new ProgressDialog(ComplaintDetailsActivity.this);

		protected void onPreExecute() {
			Dialog.setMessage("Please Wait..");
			Dialog.show();
		}
		protected void onPostExecute(Void unused) {
			Dialog.dismiss();
			
			if (rslt.trim().equalsIgnoreCase("ok")) {
				
				
				if(complaintUpdateObj !=null)
				{
					updateDataFromSQL(complaintUpdateObj.getComplaintNo(), complaintObj.getStatus(), complaintUpdateObj.getComment());
				}
				if(complaintObj != null){
					setScreenData(complaintObj);
					// add into DB
					dbHandler.addComplaint(complaintObj);
					
				}else{ // if data not found then
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage(Html.fromHtml("<font color='#FA0606'><b>Details Not Found.</b><br> Please Contact Administrator.</font>"))
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle(Html.fromHtml("<font color='#FA0606'>Complaint Details</font>"))
					       .setCancelable(false)
					       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
									finish();
									Intent intent = new Intent(ComplaintDetailsActivity.this, ComplaintListActivity.class);
									startActivity(intent);
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
				}
			
			}else {
				AlertsBoxFactory.showAlert(rslt,context );
				return;
			}
		}
		@Override
		protected Void doInBackground(String... params) {
			
			try{
				ComplaintDetailsCaller caller = new ComplaintDetailsCaller(
						getApplicationContext().getResources().getString(
								R.string.WSDL_TARGET_NAMESPACE),
								Dynamic_Url, getApplicationContext()
								.getResources().getString(
										R.string.METHOD_GET_COMPLAINT_DETAILS),
										Authobj);
				
				caller.setUserId(Long.parseLong(utils.getAppUserId()));
				caller.setComplaintNo(getStrComplaintNo());
				
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
				/*AlertsBoxFactory.showErrorAlert(e.toString(),context );*/
			}
			
			return null;
		
		}
	}
	
	/*
	 * 
	 * Load status list from web service
	 * 
	 */
	/*
	private class StatusListWebService extends AsyncTask<String, Void, Void> {

		private ProgressDialog Dialog = new ProgressDialog(ComplaintDetailsActivity.this);
		protected XMLFileHelper statusListXmlHelper;
		 
		protected void onPreExecute() {
			Dialog.setMessage("Please Wait.... Loading Status List.");
			Dialog.show();
			statusListXmlHelper = new XMLFileHelper();
			statusListXmlHelper.setFileStorageLocation(extStorageDirectory);
			statusListXmlHelper.setXmlFolder(xml_folder);
			statusListXmlHelper.setXmlFileName("servicestatus.xml");
		}
		
		protected void onPostExecute(Void unused) {
			Dialog.dismiss();
			
			if (rslt.trim().equalsIgnoreCase("ok")) {
				writeXMLFile(statusListXmlHelper,xmlStatusList);
				loadStatusList(statusListXmlHelper);
			} else {
				AlertsBoxFactory.showAlert("Invalid web-service response "
						+ rslt, context);
			}
			this.cancel(true);
		}
		
		@Override
		protected Void doInBackground(String... params) {
			
			try{
				StatusListCaller caller = new StatusListCaller(
						getApplicationContext().getResources().getString(
								R.string.WSDL_TARGET_NAMESPACE),
						getApplicationContext().getResources().getString(
								R.string.SOAP_URL), getApplicationContext()
								.getResources().getString(
										R.string.METHOD_GET_STATUS_LIST),
										Authobj);
				
				caller.setUserId(Long.parseLong(utils.getAppUserId()));
				caller.setStatusType("C");
				
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
				AlertsBoxFactory.showErrorAlert(e.toString(),context );
			}
			return null;
		}
		
	}
	*/
	/*
	 * 
	 * Load complaint category from web service
	 * 
	 */
	
	/*protected class ComplaintCategoryListWebService extends AsyncTask<String, Void, Void> {

		private ProgressDialog Dialog = new ProgressDialog(ComplaintDetailsActivity.this);
		protected XMLFileHelper complCatXmlHelper;
		
		protected void onPreExecute() {
			Dialog.setMessage("Please Wait.... Loading Complaint Category List.");
			Dialog.show();
			complCatXmlHelper = new XMLFileHelper();
			complCatXmlHelper.setFileStorageLocation(extStorageDirectory);
			complCatXmlHelper.setXmlFolder(xml_folder);
			complCatXmlHelper.setXmlFileName("complaintcat.xml");
		}
		
		protected void onPostExecute(Void unused) {
			Dialog.dismiss();
			
			if (rslt.trim().equalsIgnoreCase("ok")) {
				writeXMLFile(complCatXmlHelper,xmlComplCat);
				mapComplaintCategory(complCatXmlHelper);
			} else {
				AlertsBoxFactory.showAlert("Invalid web-service response "
						+ rslt, context);
			}
			this.cancel(true);
		}
		
		@Override
		protected Void doInBackground(String... params) {
			
			try{
				ComplaintCategoryCaller caller = new ComplaintCategoryCaller(
						getApplicationContext().getResources().getString(
								R.string.WSDL_TARGET_NAMESPACE),
						getApplicationContext().getResources().getString(
								R.string.SOAP_URL), getApplicationContext()
								.getResources().getString(
										R.string.METHOD_GET_COMPLAINT_CATEGORY),
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
				AlertsBoxFactory.showErrorAlert(e.toString(),context );
			}
			return null;
		}
		
	}*/
	
	/*
	 *  Load complaint type
	 * 
	 */
	/*protected class ComplaintTypeWebService extends AsyncTask<String, Void, Void> {

		private ProgressDialog Dialog = new ProgressDialog(ComplaintDetailsActivity.this);
		protected XMLFileHelper complTypeXmlHelper;
		
		protected void onPreExecute() {
			Dialog.setMessage("Please Wait.... Loading Complaint Type List.");
			Dialog.show();
			complTypeXmlHelper = new XMLFileHelper();
			complTypeXmlHelper.setFileStorageLocation(extStorageDirectory);
			complTypeXmlHelper.setXmlFolder(xml_folder);
			complTypeXmlHelper.setXmlFileName(getComplaintCategoryXMLFile());
		}
		
		protected void onPostExecute(Void unused) {
			Dialog.dismiss();
			
			if (rslt.trim().equalsIgnoreCase("ok")) {
				writeXMLFile(complTypeXmlHelper,xmlComplType);
				mapComplaintType(complTypeXmlHelper);
			} else {
				AlertsBoxFactory.showAlert("Invalid web-service response "
						+ rslt, context);
			}
			this.cancel(true);
		}
		
		@Override
		protected Void doInBackground(String... params) {
			
			try{
				ComplaintTypeCaller caller = new ComplaintTypeCaller(
						getApplicationContext().getResources().getString(
								R.string.WSDL_TARGET_NAMESPACE),
						getApplicationContext().getResources().getString(
								R.string.SOAP_URL), getApplicationContext()
								.getResources().getString(
										R.string.METHOD_GET_COMPLAINT_TYPE),
										Authobj);
				
				caller.setUserId(Long.parseLong(utils.getAppUserId()));
				caller.setComplaintCategoryId(complaintObj.getComplaintCategory());	
				
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
				AlertsBoxFactory.showErrorAlert(e.toString(),context );
			}
			return null;
		}
	}*/
	
	
	/*protected void mapComplaintType(XMLFileHelper xmlHelper){
		try {
			//String xmlData = xmlHelper.readXMLFile();
			ComplaintTypeParsing parsObj = new ComplaintTypeParsing( xmlHelper.readXMLFile());
			mapComplaintType = parsObj.getMapComplaintType();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(ComplaintDetailsActivity.this,
					"Complaint Type File Not Found.", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
			AlertsBoxFactory.showErrorAlert("Error in XML " + e.toString(),
					context);
		}
	}*/
	
	/*protected void mapComplaintCategory(XMLFileHelper xmlHelper){
		try {
			//String xmlData = xmlHelper.readXMLFile();
			ComplaintCategoryParsing parsObj = new ComplaintCategoryParsing( xmlHelper.readXMLFile());
			mapComplaintCategory = parsObj.getMapComplaintCategory();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(ComplaintDetailsActivity.this,
					"Complaint Category File Not Found.", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
			AlertsBoxFactory.showErrorAlert("Error in XML " + e.toString(),
					context);
		}
	}*/
	
	protected void loadStatusList(XMLFileHelper xmlHelper){
		try {
			//String xmlData = xmlHelper.readXMLFile();
			StatusListParsing statusList = new StatusListParsing( xmlHelper.readXMLFile());
			mapStatusList = statusList.getMapStatusList();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(ComplaintDetailsActivity.this,
					"StatusList File Not Found.", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
			AlertsBoxFactory.showErrorAlert("Error in XML " + e.toString(),
					context);
		}
		
		try {
			ArrayAdapter adapterForSpinner = new ArrayAdapter(
					ComplaintDetailsActivity.this,
					android.R.layout.simple_spinner_item);
			spinnerStatus.setAdapter(adapterForSpinner);
			Set<String> keys = mapStatusList.keySet();
			Iterator<String> i = keys.iterator();
			List<String> unsortList  = new ArrayList<String>();
			
			while (i.hasNext()) {
				String key = (String) i.next();
				StatusListObj pl = (StatusListObj) mapStatusList.get(key);
		
				//if(!pl.getPlanName().equals(CurrentPlan))
					unsortList.add( pl.getStatus());
	
			}
		
			//adapterForSpinner.add("Select Status");
			
			//sort the list
			Collections.sort(unsortList);
			for(String temp: unsortList){
				CharSequence textHolder = "" + temp;
				adapterForSpinner.add(textHolder);
			}
			int defaultPosition = adapterForSpinner.getPosition("InProgress");
			// set the default according to value
			spinnerStatus.setSelection(defaultPosition);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void writeXMLFile(XMLFileHelper xmlHelper,String xmlString){
		
		try {
			Log.i(" FILE PATH *********** ",""+xmlHelper.getFullFilePath());
			Log.i(" FILE DATA *********** ",""+xmlString);
			xmlHelper.writeXMLFile(xmlString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AlertsBoxFactory.showErrorAlert("Error XML " + e.toString(),context);
		}
		
	}
	
	private void setScreenData(ComplaintObj complaintObj){
		
		String[] monthName = {"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		
		
		TextView compcount = (TextView)findViewById(R.id.headerViewdata);
		compcount.setText(ComplaintCount);
		
		TextView viewObj = (TextView)findViewById(R.id.ComplNo);
		viewObj.setText(complaintObj.getComplaintNo());
		complaintNo=complaintObj.getComplaintNo();
		
		
		if(complaintObj.getComplaintDate() != null){
			
			//String DateString = "2013-05-24 10:14:03.677";
			//String DateString = complaintObj.getComplaintDate();
			String strDateTime = complaintObj.getComplaintDate();
			
			//String[] str_split = strDateTime.split("T");
			//String[] str_split_date = str_split[0].split("-");
			//String[] str_split_time = str_split[1].split(":");
			//String str_date = str_split_date[2]+"-"+monthName[Integer.parseInt(str_split_date[1])]+"-"+str_split_date[0] + " at " + str_split_time[0] +" : "+ str_split_time[1] + " : " + str_split_time[2].toString().replace("+05"," ");
			
			//String[] str_split_time = str_split[1].split(".");
			//String str_time = str_split_time.toString();
			
			
			//String [] DateString =  ShowDate.split("+");
			
			//String ShowMyDate = DateString[0];
			
			viewObj = (TextView)findViewById(R.id.ComplDate);
			viewObj.setText(strDateTime);
			
				
		}else{
			viewObj = (TextView)findViewById(R.id.ComplDate);
			viewObj.setText("-");
			
		}
		
		if(complaintObj.getAssignedDate() != null)
		{
			String strDateTime = complaintObj.getAssignedDate();
			viewObj = (TextView)findViewById(R.id.AssignedDate);
			viewObj.setText(strDateTime);
		}else{
			viewObj = (TextView)findViewById(R.id.AssignedDate);
			viewObj.setText("-");
			
		}
		
		
		
		viewObj = (TextView)findViewById(R.id.UserId);
		viewObj.setText(complaintObj.getUserId());
		
		viewObj = (TextView)findViewById(R.id.ComplName);
		viewObj.setText(complaintObj.getCustomerName());
		
		viewObj = (TextView)findViewById(R.id.Address);
		viewObj.setText(complaintObj.getCustomerAddress());
		
		viewObj = (TextView)findViewById(R.id.Phone);
		viewObj.setText(complaintObj.getPhone());
		
		//ArrayAdapter adapterForSpinner = (ArrayAdapter) spinnerStatus.getAdapter();
		//int defaultPosition = adapterForSpinner.getPosition(complaintObj.getStatus());
		// set the default according to value
		//spinnerStatus.setSelection(defaultPosition);
		
		if(complaintObj.getStatus().equals("Closed"))
		{
			Closed.setChecked(true);
		}
		
		else if(complaintObj.getStatus().equals("NotMyComplain"))
		{
			backtoareamanager.setChecked(true);
			
		}
		else if(complaintObj.getStatus().equals("DoorLock"))
		{
			doorlock.setChecked(true);
			
		}
		
		else if(complaintObj.getStatus().equals("UnableToResolve"))
		{
			unabletoresolve.setChecked(true);
			
		}
		else
		{
			progress.setChecked(true);
			
		}
			
		
		viewObj = (TextView)findViewById(R.id.Comment);
		viewObj.setText(complaintObj.getComments());
		
		// REMOVE after testing....
		//complaintObj.setComplaintCategory(1);
		//complaintObj.setComplaintId(33);
		
		
		//viewObj = (TextView)findViewById(R.id.ComplDesc);
		//viewObj.setText(Integer.toString(complaintObj.getComplaintCategory()));
		
		//Log.i("*********** COMPL CATE ... ",""+	complaintObj.getComplaintCategory());
		
/*		XMLFileHelper complTypeXmlHelper = new XMLFileHelper();
		complTypeXmlHelper.setFileStorageLocation(extStorageDirectory);
		complTypeXmlHelper.setXmlFolder(xml_folder);
		complTypeXmlHelper.setXmlFileName("complainttype"+complaintObj.getComplaintCategory()+".xml");
        
		if(complTypeXmlHelper.isXMLFileExists()){
 			mapComplaintType(complTypeXmlHelper);
        }else{
        	setComplaintCategoryXMLFile(complTypeXmlHelper.getXmlFileName());
 			new ComplaintTypeWebService().execute((String)null);
 		}*/
		
		/*
		Iterator iterator= mapComplaintCategory.entrySet().iterator();
	       while(iterator.hasNext())
	       {
	           Entry entry =(Entry)iterator.next();   
	           System.out.println(" entries= "+entry.getKey().toString());
	       }
	       
	       iterator= mapComplaintType.entrySet().iterator();
	       while(iterator.hasNext())
	       {
	           Entry entry =(Entry)iterator.next();   
	           System.out.println(" entries= "+entry.getKey().toString());
	       }*/
	/*
	 * As diss this option not requier 
	 * 03 Apr 2013	
	 */
		/*
		if(mapComplaintCategory != null){
			if(mapComplaintCategory.containsKey(Integer.toString(complaintObj.getComplaintCategory()))){
				
				ComplaintCategoryObj complaintCategoryObj = mapComplaintCategory.get(Integer.toString(complaintObj.getComplaintCategory()));
				StringBuffer sb = new StringBuffer();
				sb.append(complaintCategoryObj.getComplaintCategory());
				
				if(mapComplaintType.containsKey(complaintObj.getComplaintCategory()+"~"+complaintObj.getComplaintId())){
					ComplaintTypeObj complaintTypeObj = mapComplaintType.get(complaintObj.getComplaintCategory()+"~"+complaintObj.getComplaintId());
					sb.append(" - ");
					sb.append(complaintTypeObj.getComplaintName());
				}else{
					sb.append("  ");
				}
				
				viewObj = (TextView)findViewById(R.id.ComplDesc);
				viewObj.setText(sb.toString());
				sb = null;
				
			}else{
				viewObj = (TextView)findViewById(R.id.ComplDesc);
				viewObj.setText("--");
			}
		}else{
			viewObj = (TextView)findViewById(R.id.ComplDesc);
			viewObj.setText("--");
		}
		*/
		
	}
	private String ComplaintCategoryXMLFile;
	
	public void setComplaintCategoryXMLFile(String ComplaintCategoryXMLFile){
		this.ComplaintCategoryXMLFile = ComplaintCategoryXMLFile;
	}
	public String getComplaintCategoryXMLFile(){
		return ComplaintCategoryXMLFile;
	}
	 private void updateStatus(String complID){
	    	
	    	Log.i("*********** STATUS CHANGE... ",""+complID);
	    	
	    	
	    	try {
	    		ComplaintStatusUpdateSOAP soap = new ComplaintStatusUpdateSOAP(getApplicationContext().getResources().getString(
	    				R.string.WSDL_TARGET_NAMESPACE), Dynamic_Url,getApplicationContext()
	    						.getResources().getString(
	    								R.string.METHOD_UPDATE_STATUS));
	        	
	        	StatusUpdateHelper helper = new StatusUpdateHelper(soap,Long.parseLong(utils.getAppUserId()),complID,Authobj,"R",true);
	        	helper.updateStatus();
				
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
		if(!isFinish){
			finish();
			complaintObj=null;
		}
		
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
		AppConstants1.APP_OPEN=true;
		if(AppConstants1.GPS_AVAILABLE){
		 if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
	            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
	        }else{
	        	//  showGPSDisabledAlertToUser();
	        }
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_complaint_details, menu);
		return true;
	}
	/**
	 * @return the strComplaintNo
	 */
	public String getStrComplaintNo() {
		return strComplaintNo;
	}
	/**
	 * @param strComplaintNo the strComplaintNo to set
	 */
	public void setStrComplaintNo(String strComplaintNo) {
		this.strComplaintNo = strComplaintNo;
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
	    	//	 showGPSDisabledAlertToUser();}
	    }}
	    }
	};
	//*************************Bradcast receiver for GPS**************************ends

	/*
	 * METHOD
	 * Method Name: showGPSDisabledAlertToUser
	 * These Method is used to show 
	 * DialogBox when GPS is turn OFF
	 * 
	 *  
	 * */
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
 	
 /*
	 * 
	 * ASYNCTASK CLASS 
	 * These Class is used to 
	 * get All the complaintTypeList which consist of
	 * ComplaintId and ComplaintName 
	 * 
	 * SOAP USED IS: ResolutionSOAP
	 *  
	 * */
 public class BindResolutionAsyncTask extends AsyncTask<String, Void, Void>{
	 ProgressDialog prg;
	 String rslt="";
	 AuthenticationMobile Authobj = new AuthenticationMobile();
	 ResolutionSOAP	soap;
	 @Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		prg= new ProgressDialog(ComplaintDetailsActivity.this);
		prg.setMessage("Please Wait..");
		prg.setCancelable(false);
		prg.show();
		
		Authobj.setMobileNumber(utils.getMobileNumber());
		Authobj.setMobLoginId(utils.getMobLoginId());
		Authobj.setMobUserPass(utils.getMobUserPass());
		Authobj.setIMEINo(utils.getIMEINo());
		Authobj.setCliectAccessId(utils.getCliectAccessId());
		Authobj.setMacAddress(utils.getMacAddress());
		Authobj.setPhoneUniqueId(utils.getPhoneUniqueId());
		Authobj.setAppVersion(pInfo.versionName);
		alResolutionId.clear();
		alResolutionType.clear();
	}
	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
			soap = new ResolutionSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), Dynamic_Url,getString(R.string.METHOD_RESOLUTION_TYPE));
		try {
			rslt=soap.getResolutionType(Long.parseLong(utils.getAppUserId()), Authobj);
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
		prg.dismiss();
		if(rslt.length()>0){
			if(rslt.equalsIgnoreCase("ok")){
				try{
				writeXMLFile(str_resolution_xml_file,str_response_resolution);
			
				setResolutionXMLPackageList(String.valueOf(complaintObj.getComplaintId()));
				//alResolutionType=soap.resolutionType();
				//alResolutionId=soap.resolutionId();
				/*if(alResolutionType.size()>0){					
					ArrayAdapter<String> aaResolutionType= new ArrayAdapter<String>(ComplaintDetailsActivity.this, R.layout.spinner_item, alResolutionType);
					spResolution.setAdapter(aaResolutionType);
				}*/
				}
				catch(Exception e){
					
				}
			}
			else{
				
			}
		}
		else{
			
		}
	}
	 
 }
 	
 /*
	 * 
	 * ASYNCTASK CLASS 
	 * These Class is used to 
	 * get All the complaintTypeList which consist of
	 * ComplaintId and ComplaintName 
	 *  
	 *  SOAP USED IS: ComplaintTypeListSOAP
	 * */
 
 public class BindComplaintTypeListAsyncTask extends AsyncTask<String, Void, Void>{
	 ProgressDialog prgDialog;
	 String rslt="";
	 AuthenticationMobile Authobj = new AuthenticationMobile();
	 ComplaintTypeListSOAP	soap;
	 @Override
	protected void onPreExecute() {
	// TODO Auto-generated method stub
		super.onPreExecute();
		prgDialog= new ProgressDialog(ComplaintDetailsActivity.this);
		prgDialog.setMessage("Please wait...");
		prgDialog.setCancelable(false);
		prgDialog.show();
		
		Authobj.setMobileNumber(utils.getMobileNumber());
		Authobj.setMobLoginId(utils.getMobLoginId());
		Authobj.setMobUserPass(utils.getMobUserPass());
		Authobj.setIMEINo(utils.getIMEINo());
		Authobj.setCliectAccessId(utils.getCliectAccessId());
		Authobj.setMacAddress(utils.getMacAddress());
		Authobj.setPhoneUniqueId(utils.getPhoneUniqueId());
		Authobj.setAppVersion(pInfo.versionName);
		alResolutionId.clear();
		alResolutionType.clear();
	}
	 
	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		soap = new ComplaintTypeListSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), Dynamic_Url,getString(R.string.METHOD_COMPLAINTS_TYPE_LIST));
		try {
			rslt=soap.getComplaintTypeList(Long.parseLong(utils.getAppUserId()), Authobj);
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
		try{
			if(rslt.length()>0){
				if(rslt.equalsIgnoreCase("ok")){
					
					writeXMLFile(str_complaints_xml_file,str_response_complaints);
					setResolutionXMLPackageList(String.valueOf(complaintObj.getComplaintId()));
					//alResolutionType=soap.resolutionType();
					//alResolutionId=soap.resolutionId();
					/*if(alResolutionType.size()>0){					
						ArrayAdapter<String> aaResolutionType= new ArrayAdapter<String>(ComplaintDetailsActivity.this, R.layout.spinner_item, alResolutionType);
						spResolution.setAdapter(aaResolutionType);
					}*/
				}
				else{
					
				}
			}
			else{
				
			}
			prgDialog.dismiss();
			}catch(Exception e){
			prgDialog.dismiss();
			}
			
			
		}
	 
 }
	
 /*
  	 * METHOD
	 * CHECK XML File IS EXIST IN SD CARD 
	 * Method Name: setComplaintsXMLPackageList()
	 * 
	 * These Method Reads All Data from complaints_xml file stored
	 * in SD CARD and binds data to associated Spinner. 
	 * 
	 * */
 public void setComplaintsXMLPackageList(String setComplaintId,String GetComplaintCategoryId){
	try{	
	 Utils.log("oncreate","called: "+utils.getCliectAccessId());
		
		Utils.log("GetComplaintCategoryId","is"+GetComplaintCategoryId);
		alComplaintId.clear();
		alComplaintName.clear();
		Document doc = null;
		int position = 0;
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			 doc = db.parse(new File(TARGET_BASE_PATH + "/" + str_complaints_xml_file));
			doc.getDocumentElement().normalize();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NodeList nodeList = doc.getElementsByTagName("Table");
		Utils.log("Number of ComplaintListType",""+nodeList.getLength());
		for(int i=0;i<nodeList.getLength();i++)
		{
			Node node = nodeList.item(i);
			Element firstElmnt = (Element) node;
			NodeList nameList = firstElmnt.getElementsByTagName("complaintId");
			Element nameElement = (Element) nameList.item(0);
			nameList = nameElement.getChildNodes();
			String complaintId=((Node) nameList.item(0)).getNodeValue();
			
			Element secondElmnt = (Element) node;
			NodeList nameList1 = secondElmnt.getElementsByTagName("ComplaintName");
			Element nameElement1 = (Element) nameList1.item(0);
			nameList1 = nameElement1.getChildNodes();
			String ComplaintName=((Node) nameList1.item(0)).getNodeValue();
			
			
			Element thirdElmnt = (Element) node;
			NodeList nameList2 = thirdElmnt.getElementsByTagName("ComplaintCategoryId");
			Element nameElement2 = (Element) nameList2.item(0);
			nameList2 = nameElement2.getChildNodes();
			String ComplaintCategoryId=((Node) nameList2.item(0)).getNodeValue();
			//Utils.log("ComplaintCategoryId in XML", " "+ComplaintCategoryId);
			//Utils.log("GetComplaintCategoryId in XML", " "+GetComplaintCategoryId);
			
			if(!sharedPreferences.getBoolean("all_complaints", true)){
			//if(utils.getCliectAccessId().equalsIgnoreCase("CM000005LS")){
				if(GetComplaintCategoryId.equalsIgnoreCase("0")){
					alComplaintId.add(complaintId);
					alComplaintName.add(ComplaintName);
				}
				else{
				if(ComplaintCategoryId.equalsIgnoreCase(GetComplaintCategoryId)){
					
					//Utils.log("GetComplaintCategoryId if","is: "+GetComplaintCategoryId);
					alComplaintId.add(complaintId);
					alComplaintName.add(ComplaintName);
				}
				}
			}
			else{
				//Utils.log("GetComplaintCategoryId if","is: "+GetComplaintCategoryId);
			alComplaintId.add(complaintId);
			alComplaintName.add(ComplaintName);
			}
		//	Utils.log("Selection ","setComplaintId is:"+setComplaintId);
			if(setComplaintId.equalsIgnoreCase(complaintId)){
				for(int j=0;j<alComplaintId.size();j++){
					if(alComplaintId.contains(complaintId)){
						position=j;
					}
				}
				//position=i;
				//Utils.log("Selection ","Position is:"+position);
			}
			
		}
		Utils.log("show all", "Complaints "+sharedPreferences.getBoolean("all_complaints", true));
		ArrayAdapter<String> aaComplaintTypeList= new ArrayAdapter<String>(ComplaintDetailsActivity.this, R.layout.spinner_item, alComplaintName);
		spComplaints.setAdapter(aaComplaintTypeList);
		Utils.log("Check spinner",aaComplaintTypeList.getCount()+":"+position);
		spComplaints.setSelection(position);
   }

	catch(ArrayIndexOutOfBoundsException e){
		 Utils.log("Complaints","error"+e);
		 spComplaints.setSelection(0);
	 }
 }
 
 	/*
	 * METHOD
	 * CHECK XML File IS EXIST IN SD CARD 
	 * Method Name: setResolutionXMLPackageList()
	 * 
	 * These Method Reads All Data from resolution_xml file stored
	 * in SD CARD and binds data to associated Spinner. 
	 * 
	 * */
 
 public void setResolutionXMLPackageList(String SelectedComplaintId ){
		//Utils.log("setResolutionXMLPackageList","called"+str_response_resolution);
	 try{
		alResolutionType.clear();
		alResolutionId.clear();
		Document doc = null;
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//doc = db.parse(new InputSource(str_response_resolution));
		
			 doc = db.parse(new File(TARGET_BASE_PATH + "/" + str_resolution_xml_file));
			doc.getDocumentElement().normalize();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//NodeList nodeList1 = doc.getElementsByTagName("getResolutionTypeResult");
		NodeList nodeList = doc.getElementsByTagName("Table");
	//	Utils.log("Number of getResolutionTypeResult",""+nodeList1.getLength());
		Utils.log("Number of Resolutions",""+nodeList.getLength());
		for(int i=0;i<nodeList.getLength();i++)
		{
			Node node = nodeList.item(i);
			Element firstElmnt = (Element) node;
			NodeList nameList = firstElmnt.getElementsByTagName("ResolutionName");
			Element nameElement = (Element) nameList.item(0);
			nameList = nameElement.getChildNodes();
			String ResolutionName=((Node) nameList.item(0)).getNodeValue();
			
			Element secondElmnt = (Element) node;
			NodeList nameList1 = secondElmnt.getElementsByTagName("ResolutionID");
			Element nameElement1 = (Element) nameList1.item(0);
			nameList1 = nameElement1.getChildNodes();
			String ResolutionId=((Node) nameList1.item(0)).getNodeValue();
			
			Element thirdElemnet = (Element) node;
			NodeList nameList2 = thirdElemnet.getElementsByTagName("ComplaintId");
			Element nameElement2 = (Element) nameList2.item(0);
			nameList2 = nameElement2.getChildNodes();
			String ComplaintId=((Node) nameList2.item(0)).getNodeValue();
			
			//Utils.log("ResolutionName and ResolutionId and ComplaintId", ResolutionName+" "+ResolutionId+" "+ComplaintId );
			if(ComplaintId.equalsIgnoreCase(SelectedComplaintId)){
				alResolutionType.add(ResolutionName);
				alResolutionId.add(ResolutionId);
			}
		}
		Utils.log("alResolutionType"," size is:"+alResolutionType.size());
		if(alResolutionType.size()==0){
			alResolutionType.add("No Resolution");
			alResolutionId.add("0");
		}
		ArrayAdapter<String> aaResolutionType= new ArrayAdapter<String>(ComplaintDetailsActivity.this, R.layout.spinner_item, alResolutionType);
		spResolution.setAdapter(aaResolutionType);
		setSelected=true;
	 }
	 catch(Exception e){
		 Utils.log("Resolution","error"+e);
	 }
}

 
 	/*
	 * METHOD
	 * CHECK XML File IS EXIST IN SD CARD 
	 * Method Name: isXMLFile(String xml_file)
	 * Parameter 1: xml_file IS NAME OF FILE 
	 * 
	 * */
	private boolean isXMLFile(String xml_file) {
		
		File file = new File(TARGET_BASE_PATH,xml_file );
		boolean exists = file.exists();
		
		if (!exists) {
			// It returns false if File or directory does not exist
			if (!file.isFile()) {
				return false;
			} else {
				return true;
			}
		} else {
			// It returns true if File or directory exists
			return true;
		}
	}

	/*
	 * METHOD
	 * Write XML File in sd Card
	 * Method Name: writeXMLFile(String xml_file_name,String strXML)
	 * Parameter 1: xml_file_name IS NAME OF FILE 
	 * Parameter 2: strXML IS DATA TO BE WRITTEN IN XML FILE
	 * 
	 * */
	
	private void writeXMLFile(String xml_file_name,String strXML) {
		 Log.i(">>>XML <<< ", strXML);
		try {
			File direct = new File(extStorageDirectory + "/" + xml_folder);
			
			if (!direct.exists()) {
				direct.mkdirs(); // directory is created;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

		FileOutputStream fOut = null;
		OutputStreamWriter xmlOutWriter = null;

		try {
			File xmlFile = new File(TARGET_BASE_PATH+xml_file_name);
			xmlFile.createNewFile();
			fOut = new FileOutputStream(xmlFile);
			xmlOutWriter = new OutputStreamWriter(fOut);
			xmlOutWriter.append(strXML);
			Utils.log("String in","XML is "+strXML);
			Toast.makeText(getBaseContext(),
					"Done writing '/cnergee/'"+xml_file_name+" file.",
					Toast.LENGTH_LONG).show();
			// new ReadPackageListXML().execute();

		}catch(IOException io){
			io.printStackTrace();
			Toast.makeText(getBaseContext(), io.getMessage(), Toast.LENGTH_LONG)
			.show();
		}catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG)
					.show();
		} finally {
			try {
				if(xmlOutWriter != null){
					xmlOutWriter.flush();
					xmlOutWriter.close();
				}
				if(xmlOutWriter != null){
					fOut.flush();
					fOut.close();
				}

			} catch (IOException io) {
			}
			strXML = null;
		}
			if(xml_file_name.equalsIgnoreCase(str_complaints_xml_file)){
			setComplaintsXMLPackageList(String.valueOf(complaintObj.getComplaintId()),String.valueOf(complaintObj.getComplaintCategory()));
			}
			if(setSelected){
			if(xml_file_name.equalsIgnoreCase(str_resolution_xml_file)){
				setResolutionXMLPackageList(String.valueOf(complaintObj.getComplaintId()));
				}
			}
		
		
	}
	
	
	 public class GetPackageAsyncTask extends AsyncTask<String, Void, Void>{
			
		String getDataResult="";
		
		 @Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			 File direct = new File(extStorageDirectory + "/" + xml_folder);
				if(direct.exists()){
					direct.delete();
				}
			 prgDialog1= new ProgressDialog(ComplaintDetailsActivity.this);
			 prgDialog1.setMessage("Please wait...");
			 prgDialog1.setCancelable(false);
			 prgDialog1.show();
			 getUpdateDataString="";
		}
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			GetStatusSOAP getStatusSOAP= new GetStatusSOAP(
					getApplicationContext().getResources().getString(
							R.string.WSDL_TARGET_NAMESPACE),
							utils.getDynamic_Url(), getApplicationContext()
							.getResources().getString(
									R.string.METHOD_GET_STATUS)
									); 
			try {
				Utils.log("UserLoginName","is: "+utils.getAppUserName());
				
				getDataResult=getStatusSOAP.getPackageSOAP(Authobj,utils.getAppUserName(),"S");
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
				prgDialog1.dismiss();
			try{
				Utils.log("getDataResult","is: "+getDataResult);
				if(getDataResult.length()>0){
					if(getDataResult.equalsIgnoreCase("ok")){
						if(getUpdateDataString.length()>0){
							if(getUpdateDataString.equalsIgnoreCase("ResolutionUpdate")){
								
								Utils.log("PackageUpdate","is "+getUpdateDataString);
								
								new BindComplaintTypeListAsyncTask().execute();
								new BindResolutionAsyncTask().execute();
								new UpdatePackageAsyncTask().execute();		
							}
						}
					}
					else{
						//AlertsBoxFactory.showAlert("Please Try Again !!", AppSettingctivity.this);
					}
				}
				else{
						//AlertsBoxFactory.showAlert("Please Try Again !!", AppSettingctivity.this);
				}
				
				}catch(Exception e){
					
				}
				
				
			}
	 }
	 public class UpdatePackageAsyncTask extends AsyncTask<String, Void, Void>{
			
			String getDataResult="";
			
			 @Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
			
			}
			@Override
			protected Void doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				GetStatusSOAP getStatusSOAP= new GetStatusSOAP(
						getApplicationContext().getResources().getString(
								R.string.WSDL_TARGET_NAMESPACE),
								utils.getDynamic_Url(), getApplicationContext()
								.getResources().getString(
										R.string.METHOD_GET_STATUS)
										); 
				try {
					Utils.log("UserLoginName","is: "+utils.getAppUserName());
					
					getDataResult=getStatusSOAP.getPackageSOAP(Authobj,utils.getAppUserName(),"U");
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
					prgDialog1.dismiss();
				try{
					if(getDataResult.length()>0){
						if(getDataResult.equalsIgnoreCase("ok")){
							if(getUpdateDataString.length()>0){
								if(!getUpdateDataString.equalsIgnoreCase("ResolutionUpdate")){
									
									//new PackageListWebService().execute();
											
								}
							}
						}
						else{
							//AlertsBoxFactory.showAlert("Please Try Again !!", AppSettingctivity.this);
						}
					}
					else{
							//AlertsBoxFactory.showAlert("Please Try Again !!", AppSettingctivity.this);
					}
					
					}catch(Exception e){
						
					}
					
					
				}
		 }
}
