package com.cnergee.service;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.SOAP.ListViewSOAP;
import com.cnergee.service.adapter.TicketAdapter;
import com.cnergee.service.database.DatabaseHandler;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.util.Utils;

public class AllListActvity extends Activity {

	ListView lvEnquiry;
	EditText etSearch;
	TextView tvCount,tvHeader;
	Button btnBack;
	ImageView ivHome,ivPro;
	//ArrayAdapter<String> arrayAdappterList;
	TicketAdapter arrayAdappterList;
	ArrayList<String> alList;
	ArrayList<String> alSubscriberName;
	Utils utils;
	SharedPreferences sharedPreferences;
	AuthenticationMobile Authobj;
	PackageInfo pInfo;
	String Flag="";
	String TableName="";
	DatabaseHandler dbHandler;
	EditText etSeatrch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_enquiry);
		
		utils=new Utils();
		
		Intent i= getIntent();
		
		intializeControls();
		dbHandler= new DatabaseHandler(AllListActvity.this);
		
		Flag=i.getStringExtra("flag");
		Utils.log("Flag","is: "+Flag);
		if(Flag.equalsIgnoreCase("S")){
			tvHeader.setText("Shifting List");
			TableName=DatabaseHandler.TABLE_SHIFTING;
		}
		
		if(Flag.equalsIgnoreCase("D")){
			tvHeader.setText("Deactivation List");
			TableName=DatabaseHandler.TABLE_DEACTIVATION;
		}
		
		if(Flag.equalsIgnoreCase("P")){
			tvHeader.setText("Enquiry List");
			TableName=DatabaseHandler.TABLE_ENQUIRY;
		}
		 sharedPreferences = getApplicationContext()
				.getSharedPreferences(getString(R.string.shared_preferences_name), 0); // 0 - for private mode
		
		 
		utils.setSharedPreferences(sharedPreferences);
		
		alList=new ArrayList<String>();
		alSubscriberName=new ArrayList<String>();
		arrayAdappterList=new TicketAdapter(this, R.layout.custom_lv_textview, alList,alSubscriberName,TableName);
		lvEnquiry.setAdapter(arrayAdappterList);
		
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
		
		if(utils.isOnline(AllListActvity.this)){
		new GetEnquiryListAsyncTask().execute();
		}
		else{
			AlertDialog.Builder builder = new AlertDialog.Builder(AllListActvity.this);
			builder.setMessage(Html.fromHtml("<font color='#FA0606'>Unable to connect to the Internet.<br>Can't display the details because your device isn't connected to the Internet.</font>"))
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(Html.fromHtml("<font color='#FA0606'>Internet Connection</font>"))
			       .setCancelable(false)
			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   AllListActvity.this.finish();
							Intent DashboardIntent = new Intent(AllListActvity.this,
									DashboardActivity.class);				
							
							startActivity(DashboardIntent);
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
		
		btnBack.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AllListActvity.this.finish();
				Intent DashboardIntent = new Intent(AllListActvity.this,
						DashboardActivity.class);				
				
				startActivity(DashboardIntent);
			}
		});
		
		lvEnquiry.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(Flag.equalsIgnoreCase("P")){
					finish();
					Intent enqDetailsIntent= new Intent(AllListActvity.this,EnquiryDetailsActivity.class);
					enqDetailsIntent.putExtra("TicketNo", alList.get(arg2));
					enqDetailsIntent.putExtra("flag", Flag);
					startActivity(enqDetailsIntent);
				}
				
				if(Flag.equalsIgnoreCase("S")){
					finish();
					Intent enqDetailsIntent= new Intent(AllListActvity.this,ShiftingDetailsActivity.class);
					enqDetailsIntent.putExtra("TicketNo", alList.get(arg2));
					enqDetailsIntent.putExtra("flag", Flag);
					startActivity(enqDetailsIntent);
				}
				if(Flag.equalsIgnoreCase("D")){
					finish();
					Intent enqDetailsIntent= new Intent(AllListActvity.this,DeactivationDetailsActivity.class);
					enqDetailsIntent.putExtra("TicketNo", alList.get(arg2));
					enqDetailsIntent.putExtra("flag", Flag);
					startActivity(enqDetailsIntent);
				}
			}
		});
		
		ivHome.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AllListActvity.this.finish();
				Intent DashboardIntent = new Intent(AllListActvity.this,
						DashboardActivity.class);				
				
				startActivity(DashboardIntent);
			}
		});
		
	
		etSearch=(EditText) findViewById(R.id.etSearch);
		etSearch.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			 arrayAdappterList.getFilter().filter(s.toString());
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
	}
	
	public void intializeControls(){
		 lvEnquiry=(ListView)findViewById(R.id.complaintListView);
		 etSearch=(EditText) findViewById(R.id.etSearch);
		
		 tvCount=(TextView) findViewById(R.id.headerViewdata);
		 tvHeader=(TextView) findViewById(R.id.headerView);
		 btnBack=(Button) findViewById(R.id.btnBackEnquiry);;
		 ivHome=(ImageView) findViewById(R.id.ivHome);
		 ivPro=(ImageView) findViewById(R.id.inprogimg);		 
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		AllListActvity.this.finish();
		Intent DashboardIntent = new Intent(AllListActvity.this,
				DashboardActivity.class);				
		
		startActivity(DashboardIntent);
	}
	
	public class GetEnquiryListAsyncTask extends AsyncTask<String, Void, Void>{
		ProgressDialog prgDialog;
		ListViewSOAP listViewSOAP;
		String listResult="";
		String listJsonResponse="";
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			alList.clear();
			alSubscriberName.clear();
			prgDialog= new ProgressDialog(AllListActvity.this);
			prgDialog.setMessage("Please wait...");
			prgDialog.setCancelable(false);
			prgDialog.show();
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			listViewSOAP= new ListViewSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), getString(R.string.METHOD_GET_ALL_LIST));
			try {
				listResult=	listViewSOAP.getAllList(utils.getAppUserId(), Authobj,Flag);
				listJsonResponse=listViewSOAP.getListJsonResponse();
				convertListJson(listJsonResponse);
			Thread t=	new Thread() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						ArrayList<String> alTicketsDb=dbHandler.getAllTickets(TableName);
						if (alTicketsDb != null) {
							if (alTicketsDb.size() > 0) {
								for (int i = 0; i < alTicketsDb.size(); i++) {
									boolean flag=false;
									for (int j = 0; j < alList.size(); j++) {
										
										if (alTicketsDb.get(i).equalsIgnoreCase(alList.get(j))) {
											flag=true;
											break;
										} 
										else {
											
										}
									}
									if(!flag){
										Utils.log("Ticket NOT"," exist:"+alTicketsDb.get(i));
										 dbHandler.DeleteTicketRow(TableName,alTicketsDb.get(i));
									}
									else{
										Utils.log("Ticket"," exist:"+alTicketsDb.get(i));
									}
								}
							}
						}
					}
				};
				t.start();
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
			if(listResult.length()>0){
				if(listResult.equalsIgnoreCase("OK")){
					if(listJsonResponse.length()>0){
						arrayAdappterList.notifyDataSetChanged();
						lvEnquiry.setAdapter(arrayAdappterList);
						tvCount.setText(alList.size()+"");
						Utils.log("count","is:"+alList.size());
					}
				}
			}
			
			if(prgDialog!=null){
				if(prgDialog.isShowing()){
					prgDialog.dismiss();
				}
			}
		}
		
		public void convertListJson(String json){

			
			if (json != null) {
				if(json.length()>0){
				try {

					JSONObject mainJson = new JSONObject(json);
					JSONObject newDatasetJson = mainJson.getJSONObject("NewDataSet");
					
				/*
				 * JSON PARSE FRO COMPLAINT
				 * *********START HERE****************
				 * */	
					if(newDatasetJson.has("Table")){
						
					if (newDatasetJson.get("Table") instanceof JSONObject) {
						
						JSONObject complaintJsonObject = newDatasetJson.getJSONObject("Table");
						String ComplaintNo = complaintJsonObject.getString("ID");
						String SubscriberName = complaintJsonObject.getString("SubscriberName");
						Utils.log(""+Flag, "JSONObject");
						Utils.log(""+Flag, ":" + ComplaintNo);
						Utils.log(""+Flag, ":" + SubscriberName);
						alList.add(ComplaintNo);
						alSubscriberName.add(SubscriberName);
						
					}
					else if (newDatasetJson.get("Table") instanceof JSONArray) {
						
						
						JSONArray complaintJsonArray = newDatasetJson.getJSONArray("Table");
						Utils.log("ComplaintNo", "JSONArray");
						complaintJsonArray.length();
						
						for (int i = 0; i < complaintJsonArray.length(); i++) {
							JSONObject jObj = complaintJsonArray.getJSONObject(i);
							String ComplaintNo = jObj.getString("ID");
							String SubscriberName = jObj.getString("SubscriberName");
							Utils.log(""+Flag, ":" + ComplaintNo);
							Utils.log(""+Flag, ":" + SubscriberName);
							alList.add(ComplaintNo);
							alSubscriberName.add(SubscriberName);
						}
						
					}
					else {
						
					}
					}
					/*
					 * JSON PARSE FOR COMPLAINT
					 * *********ENDS HERE****************
					 * */
					
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		
		}
	}
}
