package com.cnergee.service;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.SOAP.AllTicketDetailsSOAP;
import com.cnergee.service.SOAP.GetStatusListSOAP;
import com.cnergee.service.SOAP.GetStatusSOAP;
import com.cnergee.service.SOAP.UpdateAllTicketDetailSOAP;
import com.cnergee.service.SOAP.UpdateNotificationServiceSOAP;
import com.cnergee.service.broadcast.AlarmBroadcastReceiver;
import com.cnergee.service.database.DatabaseHandler;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.util.Utils;

public class ShiftingDetailsActivity extends Activity {

	String TicketNo="",Flag="";
	ProgressDialog prgDialog;
	AuthenticationMobile Authobj;
	Utils utils;	
	SharedPreferences sharedPreferences;
	private String TAG="ShiftingDetailsActivity";
	PackageInfo pInfo;
	DatabaseHandler dbHandler;
	String xml_status="shifting_status";
	TextView tvShiftingNo,tvCustName,tvOldAddr,tvMobNo,tvNewAddr,tvMemLoginId,tvLastComm;
	TextView tvHeaderView;
	Spinner spStatus;
	Button btnBack,btnUpdate;
	EditText etComments;
	ImageView ivHome,ivLeftIcon;
	ArrayList<String> alStatusId;
	ArrayList<String> alStatusName;
	ArrayAdapter<String> aaStatus;
	String getUpdateDataString="";
	String currentStatus="0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shifting_details);
		Intent i= getIntent();
		TicketNo=i.getStringExtra("TicketNo");
		Flag=i.getStringExtra("flag");
		Utils.log("TicketNo",":"+TicketNo);
		
		initializeControls();
		tvShiftingNo.setText(TicketNo);
		utils=new Utils();
		 sharedPreferences = getApplicationContext()
					.getSharedPreferences(getString(R.string.shared_preferences_name), 0); // 0 - for private mode
			
		utils.setSharedPreferences(sharedPreferences);
		tvHeaderView.setText("Shifting Details");
		
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		alStatusId= new ArrayList<String>();
		alStatusName= new ArrayList<String>();
		
		aaStatus=new ArrayAdapter<String>(ShiftingDetailsActivity.this,	R.layout.spinner_item, alStatusName);
		spStatus.setAdapter(aaStatus);
		
		Authobj = new AuthenticationMobile();
		Authobj.setMobileNumber(utils.getMobileNumber());
		Authobj.setMobLoginId(utils.getMobLoginId());
		Authobj.setMobUserPass(utils.getMobUserPass());
		Authobj.setIMEINo(utils.getIMEINo());
		Authobj.setCliectAccessId(utils.getCliectAccessId());
		Authobj.setMacAddress(utils.getMacAddress());
		Authobj.setPhoneUniqueId(utils.getPhoneUniqueId());
		Authobj.setAppVersion(pInfo.versionName);
		dbHandler=new DatabaseHandler(ShiftingDetailsActivity.this);
		
		if(!dbHandler.isExist(DatabaseHandler.TABLE_SHIFTING, TicketNo)){
			dbHandler.insertTicketRow(DatabaseHandler.TABLE_SHIFTING, TicketNo);
			new UpdateNotificationServiceAsyncTask().execute();
		}
		//new UpdateEnquiryDetailsAsyncTask().execute();
		if(!utils.isXMLFile(xml_status)){
			if(utils.isOnline(ShiftingDetailsActivity.this)){
			 new GetStatusListAsyncTask().execute();
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
				builder.setMessage(Html.fromHtml("<font color='#FA0606'>Unable to connect to the Internet.<br>Can't display the details because your device isn't connected to the Internet.</font>"))
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(Html.fromHtml("<font color='#FA0606'>Internet Connection</font>"))
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   finish();
				        	   Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
								backIntent.putExtra("flag", Flag);
								startActivity(backIntent);
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		else{
			if(utils.isOnline(ShiftingDetailsActivity.this)){	
		new GetShiftingDetailsAsynTask().execute();
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
				builder.setMessage(Html.fromHtml("<font color='#FA0606'>Unable to connect to the Internet.<br>Can't display the details because your device isn't connected to the Internet.</font>"))
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(Html.fromHtml("<font color='#FA0606'>Internet Connection</font>"))
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   finish();
				        	   Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
								backIntent.putExtra("flag", Flag);
								startActivity(backIntent);
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
		new GetPackageAsyncTask().execute();
		bindStatusXML();
		}
		
		btnUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Utils.log("Update"," Clicked");
				if(!spStatus.getSelectedItem().toString().equalsIgnoreCase("Select Status")){
					if(etComments.getText().toString().length()>0){
						new UpdateShiftingDetailsAsyncTask().execute();
					}
					else{
						Toast.makeText(ShiftingDetailsActivity.this, "Please enter commment!!", Toast.LENGTH_LONG).show();
						Utils.log("Length","is:"+etComments.getText().length());
						
					}
				}
				else{
					Toast.makeText(ShiftingDetailsActivity.this, "Please enter status!!", Toast.LENGTH_LONG).show();
					Utils.log("Spinner","is:"+spStatus.getSelectedItem().toString());
					
				}
			}
		});
		
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
				backIntent.putExtra("flag", Flag);
				startActivity(backIntent);
			}
		});
		
		ivHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent homeIntent= new Intent(ShiftingDetailsActivity.this,DashboardActivity.class);					
				startActivity(homeIntent);
			}
		});
	}
	
	public void initializeControls(){
		
		tvShiftingNo=(TextView) findViewById(R.id.tvShiftingNo);
		tvCustName=(TextView) findViewById(R.id.tvShiftingCustName);
		tvOldAddr=(TextView) findViewById(R.id.tvShiftingOldAddress);
		tvMobNo=(TextView) findViewById(R.id.tvShiftingMobNo);
		tvNewAddr=(TextView) findViewById(R.id.tvShiftingNewAddress);
		tvMemLoginId=(TextView) findViewById(R.id.tvSMemberLoginId);
		tvLastComm=(TextView) findViewById(R.id.tvShiftingLastComment);
		
		tvHeaderView=(TextView) findViewById(R.id.headerView);
		
		spStatus=(Spinner) findViewById(R.id.spShiftingStatus);
		btnBack=(Button) findViewById(R.id.backBtn);
		btnUpdate=(Button) findViewById(R.id.updateBtn);
		ivHome=(ImageView) findViewById(R.id.ivHome);
		etComments=(EditText) findViewById(R.id.etComment);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	/*	finish();
		Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
		backIntent.putExtra("flag", Flag);
		startActivity(backIntent);*/
	}
	
	/*
	 * class: GetStatusListAsyncTask AyncTask
	 * Used For:These class used for Get All status for enquiry Deatils to bind with Spinner.
	 * @param:
	 * */
	
	/**
	 * @param AsyncTask used to get all status for enquiry Details to bind with Spinner.
	 */
	public class GetStatusListAsyncTask extends AsyncTask<String, Void, Void>{

		GetStatusListSOAP getStatusListSOAP;
		String getStatusListResult="";
		String xm_status_list_response="";
		ProgressDialog prgDialog1;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			alStatusId=new ArrayList<String>();
			alStatusName=new ArrayList<String>();
			
			prgDialog1= new ProgressDialog(ShiftingDetailsActivity.this);
			prgDialog1.setCancelable(false);
			prgDialog1.setMessage("Please Wait...");
			prgDialog1.show();
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			getStatusListSOAP=new GetStatusListSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), getString(R.string.METHOD_GET_ALL_STATUS_LIST));
			try {
				getStatusListResult=getStatusListSOAP.getStatusList(utils.getAppUserId(), "S", Authobj);
				xm_status_list_response=getStatusListSOAP.getXMLResponse();
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
			
			if(getStatusListResult.length()>0){
				if(getStatusListResult.equalsIgnoreCase("OK")){
					if(xm_status_list_response.length()>0){
						Utils.writeXMLFile(xml_status, xm_status_list_response, ShiftingDetailsActivity.this);
						bindStatusXML();
						new GetShiftingDetailsAsynTask().execute();
						
					}
					else{
						if(prgDialog1!=null){
							if(prgDialog1.isShowing()){
								prgDialog1.dismiss();
							}
						}
						/*
						  *  When xml response is null
						  * */
						AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
						builder.setMessage(Html.fromHtml("Problem with status list, Please Contact Admin"))
								.setIcon(android.R.drawable.ic_dialog_alert)
								.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
						       .setCancelable(false)
						       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						        	finish();
						       		Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
						       		backIntent.putExtra("flag", Flag);
						       		startActivity(backIntent);
						           }
						       });
						AlertDialog alert = builder.create();
						alert.show();
					}
				}
				else{
					if(prgDialog1!=null){
						if(prgDialog1.isShowing()){
							prgDialog1.dismiss();
						}
					}
					/*
					  *  When result is not returned as 'OK'
					  * */
					AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
					builder.setMessage(Html.fromHtml("Problem Fetching status data from server, Please Contact Admin"))
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
					       .setCancelable(false)
					       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   finish();
						       		Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
						       		backIntent.putExtra("flag", Flag);
						       		startActivity(backIntent);
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
				}
			}
			else{
			
				if(prgDialog1!=null){
					if(prgDialog1.isShowing()){
						prgDialog1.dismiss();
					}
				}
				
				/*
				 * When result is blank
				 * */
				AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
				builder.setMessage(Html.fromHtml("Error while retrieving status data from server, Please Contact Admin"))
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   finish();
					       		Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
					       		backIntent.putExtra("flag", Flag);
					       		startActivity(backIntent);
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
			
			if(prgDialog1!=null){
				if(prgDialog1.isShowing()){
					prgDialog1.dismiss();
				}
			}
		}
		
}
	
	
	/*
	 * class:GetEnquryDetailsAsynTask AyncTask
	 * Used For:These class used for Get Details for particular enquiry.
	 * @param:
	 * */
	
	/**
	 * @param AsyncTask   used to get all enquiry (Prospect) details for specific Ticket Number.
	 */
	public class GetShiftingDetailsAsynTask extends AsyncTask<String, Void, Void>{
		AllTicketDetailsSOAP allDetailsSOAP;
		String shiftingDeatilsResult="";
		String json_shift_details_response="";
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			prgDialog= new ProgressDialog(ShiftingDetailsActivity.this);
			prgDialog.setCancelable(false);
			prgDialog.setMessage("Please Wait...");
			prgDialog.show();
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			allDetailsSOAP= new AllTicketDetailsSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), getString(R.string.METHOD_GET_ALL_TICKET_DETAILS));
			try {
				shiftingDeatilsResult=allDetailsSOAP.getAllDetails(utils.getAppUserId(), TicketNo, "SD", Authobj);
				json_shift_details_response=allDetailsSOAP.getJsonResponse();
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
			Utils.log(TAG+"","Result: "+shiftingDeatilsResult);
			Utils.log(TAG+"","JSON RESPONSE: "+json_shift_details_response);
			if(prgDialog!=null){
				if(prgDialog.isShowing()){
					prgDialog.dismiss();
				}
			}
			
			if(shiftingDeatilsResult.length()>0){
				if(shiftingDeatilsResult.equalsIgnoreCase("OK")){
					if(json_shift_details_response.length()>0){
						conevrtShiftingDetailsFromJson(json_shift_details_response);
						bindStatusXML();
						new UpdateNotificationServiceAsyncTask().execute();
					}
					else{
						/*
						  *  When json response is null
						  * */
						AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
						builder.setMessage(Html.fromHtml("No data on server for this Subscriber, Please Contact Admin"))
								.setIcon(android.R.drawable.ic_dialog_alert)
								.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
						       .setCancelable(false)
						       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						        	finish();
						       		Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
						       		backIntent.putExtra("flag", Flag);
						       		startActivity(backIntent);
						           }
						       });
						AlertDialog alert = builder.create();
						alert.show();
					}
				}
				else{
					/*
					  *  When result is not returned as 'OK'
					  * */
					AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
					builder.setMessage(Html.fromHtml("Problem Fetching data from server, Please Contact Admin"))
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
					       .setCancelable(false)
					       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   finish();
						       		Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
						       		backIntent.putExtra("flag", Flag);
						       		startActivity(backIntent);
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
				}
			}
			else{
			
				/*
				 * When result is blank
				 * */
				AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
				builder.setMessage(Html.fromHtml("Error while retrieving data from server, Please Contact Admin"))
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   finish();
					       		Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
					       		backIntent.putExtra("flag", Flag);
					       		startActivity(backIntent);
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
			
		}
	}
	
	
	/*
	 * class: UpdateEnquiryDetailsAsyncTask AyncTask
	 * Used For:These class used to update the status and comment for particular Ticket Number
	 * @param:
	 * */
	
	/**
	 * @param AsyncTask used to update the status and comments for particular Ticket Number on server
	 */
	public class UpdateShiftingDetailsAsyncTask extends AsyncTask<String, Void, Void> {

	UpdateAllTicketDetailSOAP updateAllTicketDetailSOAP;
	String updateShiftDetailsResult="";
	String update_shift_details_response="";
	ProgressDialog prgDialog;
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		prgDialog= new ProgressDialog(ShiftingDetailsActivity.this);
		prgDialog.setCancelable(false);
		prgDialog.setMessage("Please Wait...");
		prgDialog.show();
	}
	
	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		updateAllTicketDetailSOAP=new UpdateAllTicketDetailSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), getString(R.string.METHOD_UPDATE_ALL_TICKET_DETAILS));
		try {
			updateShiftDetailsResult=updateAllTicketDetailSOAP.updateAllTicketDetails(utils.getAppUserId(), TicketNo, etComments.getText().toString(), alStatusId.get(spStatus.getSelectedItemPosition()), "Shifting",  Authobj);
			update_shift_details_response=updateAllTicketDetailSOAP.getJsonResponse();
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
		if(prgDialog!=null){
			if(prgDialog.isShowing()){
				prgDialog.dismiss();
			}
		}
		Utils.log("Result","is:"+updateShiftDetailsResult);
		if(updateShiftDetailsResult.length()>0){
			if(updateShiftDetailsResult.equalsIgnoreCase("OK")){
				if(update_shift_details_response.length()>0){
					AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
					builder.setMessage(update_shift_details_response)
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
					       .setCancelable(false)
					       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   finish();
						       		Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
						       		backIntent.putExtra("flag", Flag);
						       		startActivity(backIntent);
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
					
					if(sharedPreferences.getBoolean("location", false)){
						Intent i = new Intent(ShiftingDetailsActivity.this,AlarmBroadcastReceiver.class);
						i.putExtra("activity", "shifting");
						sendBroadcast(i);
					}
				}
				else{
					AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
					builder.setMessage("We are unable to process your request.")
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
					       .setCancelable(false)
					       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   finish();
						       		Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
						       		backIntent.putExtra("flag", Flag);
						       		startActivity(backIntent);
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
				}
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
				builder.setMessage(update_shift_details_response)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   finish();
					       		Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
					       		backIntent.putExtra("flag", Flag);
					       		startActivity(backIntent);
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		
	}
	
}
	
	
	/*
	 * class: UpdateNotificationServiceAsyncTask AyncTask
	 * Used For:These class used to update read status for specific Ticket Number on the server
	 * 
	 * */
	
	/**
	 * @param AsyncTask used to update read status for specific Ticket Number on the server
	 */
	public class UpdateNotificationServiceAsyncTask extends AsyncTask<String, Void, Void>{
		UpdateNotificationServiceSOAP updateNotificationServiceSOAP;
		String result="";
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
		//	Utils.log("ShiftingDetailsActivity PreExcute","started");
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
		//	Utils.log("ShiftingDetailsActivity doInBackground","started");
			updateNotificationServiceSOAP= new UpdateNotificationServiceSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), getString(R.string.METHOD_UPDATE_ALL_NOTIFICATION));
			try {
				updateNotificationServiceSOAP.updateNotifications(utils.getAppUserId(), "", "",TicketNo,"", "R", Authobj);
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
		//	Utils.log("ShiftingDetailsActivity onPostExecute","started");
			
		}
	}

	
	/**
	 * @param Method used to convert json response into string and print those string to respected textviews.
	 */
	public void conevrtShiftingDetailsFromJson(String json) {
		if (json != null) {
			if(json.length()>0){
			try {
				JSONObject mainJson = new JSONObject(json);
				
				if(mainJson.has("NewDataSet")){
				JSONObject newDatasetJson = mainJson.getJSONObject("NewDataSet");
				if (newDatasetJson.has("Table")) {

					if (newDatasetJson.get("Table") instanceof JSONObject) {

						JSONObject TableJsonObject = newDatasetJson.getJSONObject("Table");
						if(TableJsonObject.has("Comments")){
							tvLastComm.setText(TableJsonObject.getString("Comments"));
						}
						if(TableJsonObject.has("CustomerName")){
							tvCustName.setText(TableJsonObject.getString("CustomerName"));
						}
						if(TableJsonObject.has("MemberLoginID")){
							tvMemLoginId.setText(TableJsonObject.getString("MemberLoginID"));
						}
						if(TableJsonObject.has("MobileNoPrimary")){
							tvMobNo.setText(TableJsonObject.getString("MobileNoPrimary"));
						}
						if(TableJsonObject.has("NewAddress")){
							tvNewAddr.setText(TableJsonObject.getString("NewAddress"));
						}
						if(TableJsonObject.has("OldAddress")){
							tvOldAddr.setText(TableJsonObject.getString("OldAddress"));
						}
						if(TableJsonObject.has("StatusId")){
							currentStatus=TableJsonObject.getString("StatusId");
						}
				

						/*if(TableJsonObject.has("ProspectId")){
							tvEnquiryNo.setText(TableJsonObject.getString("ProspectId"));
						}*/
		
					}
					else{
						 /*
						  *  In JSON FORMAT 'Table' instance exist but not the JSONObject
						  * */
						
						AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
						builder.setMessage(Html.fromHtml("Problem with the data. Please contact Admin"))
								.setIcon(android.R.drawable.ic_dialog_alert)
								.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
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
				else{
					 /*
					  *  In JSON FORMAT 'Table' instance is not exist
					  * */
					
					AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
					builder.setMessage(Html.fromHtml("Problem with the server data. Please contact Admin"))
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
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
				else{
					
				}
			} catch (Exception e) {
				Utils.log("Printing Json","Error:"+e);
			}
			}
		}
	}

	/**
	 * @param Method used to bind status xml to spinner stored in sd card.
	 */
	public void bindStatusXML(){
		try{
			if(alStatusId!=null){
				alStatusId.clear();
			}
			if(alStatusName!=null){
				alStatusName.clear();
			}
			Document doc=null;
			try {
				
			
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			 doc = db.parse(new File(Utils.TARGET_BASE_PATH + "/" + xml_status));
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
			alStatusName.add("Select Status");
			alStatusId.add("0");
			for(int i=0;i<nodeList.getLength();i++)
			{
				Node node = nodeList.item(i);
				Element firstElmnt = (Element) node;
				NodeList nameList = firstElmnt.getElementsByTagName("Status");
				Element nameElement = (Element) nameList.item(0);
				nameList = nameElement.getChildNodes();
				String StatusName=((Node) nameList.item(0)).getNodeValue();
				
				Element secondElmnt = (Element) node;
				NodeList nameList1 = secondElmnt.getElementsByTagName("StatusId");
				Element nameElement1 = (Element) nameList1.item(0);
				nameList1 = nameElement1.getChildNodes();
				String StatusId=((Node) nameList1.item(0)).getNodeValue();
				 
				Utils.log("StatusName",":"+StatusName);
				Utils.log("StatusId",":"+StatusId);
				
				alStatusId.add(StatusId);
				alStatusName.add(StatusName);
			}
			aaStatus.notifyDataSetChanged();
			aaStatus=new ArrayAdapter<String>(ShiftingDetailsActivity.this,	R.layout.spinner_item, alStatusName);
			spStatus.setAdapter(aaStatus);
			
			for(int j=0;j<alStatusId.size();j++){
				if(currentStatus.equalsIgnoreCase(alStatusId.get(j))){
					spStatus.setSelection(j);
					break;
				}
				else{
					spStatus.setSelection(0);
				}
			}
			
			if(spStatus.getSelectedItem().toString().equalsIgnoreCase("Closed")){
				AlertDialog.Builder builder = new AlertDialog.Builder(ShiftingDetailsActivity.this);
				builder.setMessage(Html.fromHtml("This Enquiry has been closed."))
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	finish();
				       		Intent backIntent= new Intent(ShiftingDetailsActivity.this,AllListActvity.class);	
				       		backIntent.putExtra("flag", Flag);
				       		startActivity(backIntent);
				       		dbHandler.DeleteTicketRow(DatabaseHandler.TABLE_SHIFTING, TicketNo);
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		catch(Exception e){
			
		}
	}
	

	/*
	 * class: GetPackageAsyncTask AyncTask
	 * Used For:These class used to check if any change in the status list and based on result 
	 * delete XML file and bind new XML file to Spinner. 
	 * 
	 * */
	
	/**
	 * @param AsyncTask used to check if any change in the status list and based on result 
	 * delete XML file and bind new XML file to Spinner. 
	 */
	public class GetPackageAsyncTask extends AsyncTask<String, Void, Void>{
			
			String getDataResult="";
			ProgressDialog  prgDialog1;
			 @Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				 File direct = new File(Utils.extStorageDirectory + "/" + Utils.xml_folder);
					if(direct.exists()){
						direct.delete();
					}
				 prgDialog1= new ProgressDialog(ShiftingDetailsActivity.this);
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
					if(prgDialog1!=null){
						if(prgDialog1.isShowing())
						prgDialog1.dismiss();
					}
				try{
					Utils.log("getDataResult","is: "+getDataResult);
					if(getDataResult.length()>0){
						if(getDataResult.equalsIgnoreCase("ok")){
							if(getUpdateDataString.length()>0){
								if(getUpdateDataString.equalsIgnoreCase("ResolutionUpdate")){
									
									Utils.log("PackageUpdate","is "+getUpdateDataString);
																		
											
									new GetStatusListAsyncTask().execute();
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


	/*
	 * class: UpdatePackageAsyncTask AyncTask
	 * Used For:These class used to update read for status list
	 * 
	 * */
	
	/**
	 * @param AsyncTask used to update read for status list
	 */
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
					//prgDialog1.dismiss();
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
